from aws_cdk import (
    core,
    aws_ecs as ecs,
    aws_ec2 as ec2,
    aws_efs as efs,
    aws_lambda as _lambda,
    aws_iam as iam,
    aws_s3 as  s3,
    aws_certificatemanager as  acm,
    aws_route53 as  dns,
    aws_s3_deployment as s3d,
    aws_apigateway as api,
    aws_route53_targets as targets,
    aws_cognito as cognito

)


class CdkStack(core.Stack):

    def __init__(self, scope: core.Construct, id: str,region,domain, **kwargs) -> None:
        super().__init__(scope, id, **kwargs)

        # VPC , we need one for ECS cluster ( sadly )
        vpc = ec2.Vpc.from_lookup(self,'vpc',is_default=True)

        cluster = ecs.Cluster(self,'Cluster', vpc= vpc)

        # Route53 & SSL Certificate
        zone = dns.HostedZone(self,"dns",
            zone_name=domain
        )

        dns.ARecord(self,'MinecraftRecord',
            zone=zone,
            record_name='minecraft',
            target=dns.RecordTarget(values=['1.2.3.4'])
        )

        cert = acm.Certificate(
            self,'cert',
            domain_name=f'*.{domain}',
            validation=acm.CertificateValidation.from_dns(zone)
        )

        # ECS ( Cluster, EFS, Task Def)
        fs = efs.FileSystem(self,'EFS',
            vpc=vpc,
            removal_policy = core.RemovalPolicy.DESTROY
        
        )

        task_definition = ecs.FargateTaskDefinition(self,'TaskDef',
            memory_limit_mib=4096,
            cpu=1024
        )

        container = task_definition.add_container('MinecraftDocker',
            image = ecs.ContainerImage.from_registry('darevee/minecraft-aws'),
            logging = ecs.AwsLogDriver(stream_prefix='Minecraf'),
            cpu = 1024,
            memory_limit_mib=4096
        )
        container.add_mount_points(ecs.MountPoint(container_path='/minecraft',source_volume='efs',read_only=False))
        cfn_task = container.task_definition.node.default_child
        cfn_task.add_property_override(
            "Volumes", [{
                "EFSVolumeConfiguration": {
                    "FilesystemId": fs.file_system_id
            },
            "Name": "efs"
        }])

        container.add_port_mappings(
            ecs.PortMapping(container_port = 25565)
        )
        
        sg = ec2.SecurityGroup(self,'sg',
            vpc = vpc
        )
        sg.add_ingress_rule(
            peer=ec2.Peer.any_ipv4(),
            connection=ec2.Port.tcp(25565),
            description='Minecraft Access'
        )
        sg.add_ingress_rule(
            peer=ec2.Peer.any_ipv4(),
            connection=ec2.Port.tcp(25575),
            description='RCONN Access'
        )

        fs.connections.allow_default_port_from(sg)

        subnets =     ",".join(vpc.select_subnets().subnet_ids)  

        # Cognito ( For ApiGW Authentication)
        userpool = cognito.UserPool(self,'UserPool',
            user_invitation=cognito.UserInvitationConfig(
                email_body="""No cześć {username}, zostałeś zaproszony do naszego Minecraft!
                Twoje tymczasowe hasło to {####}
                """,
                email_subject="Zaproszenie do minecrafta"
                )
        )

        # APIGW (Gateway, Lambdas, S3 Static content)
        
        # Lambda Starter 
        starter = _lambda.Function(self,'Starter',
            runtime = _lambda.Runtime.PYTHON_3_8,
            handler = 'index.lambda_handler',
            code = _lambda.Code.asset('lambda/starter'),
            timeout=core.Duration.seconds(300),
            environment={
                'cluster' : cluster.cluster_name, 
                'subnets' : subnets, 
                'security_groups' : sg.security_group_id, 
                'task_definition' : task_definition.task_definition_arn, 
                'region' : region,
                'zone_id' : zone.hosted_zone_id,
                'domain' : domain
            }
        )

        starter.add_to_role_policy(
            iam.PolicyStatement(
                effect = iam.Effect.ALLOW,
                resources = ["*"],
                actions = ["ecs:ListTasks","ecs:DescribeTasks","ec2:DescribeNetworkInterfaces"]
            )
        )
        starter.add_to_role_policy(
            iam.PolicyStatement(
                effect = iam.Effect.ALLOW,
                resources = [task_definition.task_definition_arn],
                actions = ["ecs:RunTask","ecs:DescribeTasks"]
            )
        )
        starter.add_to_role_policy(
            iam.PolicyStatement(
                effect = iam.Effect.ALLOW,
                resources = [task_definition.task_role.role_arn,task_definition.execution_role.role_arn],
                actions = ["iam:PassRole"]
            )
        )

        starter.add_to_role_policy(
            iam.PolicyStatement(
                effect = iam.Effect.ALLOW,
                resources = [zone.hosted_zone_arn],
                actions = ["route53:ChangeResourceRecordSets"]
            )
        )

        # S3 static webpage
        bucket = s3.Bucket(self,"S3WWW",
            public_read_access=True,
            removal_policy=core.RemovalPolicy.DESTROY,
            website_index_document="index.html"
        )
        s3d.BucketDeployment(self,"S3Deploy",
            destination_bucket = bucket,
            sources = [s3d.Source.asset("static_page")]
        )

        status = _lambda.Function(self,'Status',
            runtime = _lambda.Runtime.PYTHON_3_8,
            handler = 'index.lambda_handler',
            code = _lambda.Code.asset('lambda/status'),
            environment={
                'url' : f"https://minecrafter.{domain}",
                'domain' : domain


            }
        )





        # ApiGW 
        apigw = api.LambdaRestApi(self,'ApiGW',
            handler = status,
            proxy=False,
            domain_name={
                "domain_name": f'minecrafter.{domain}',
                "certificate": cert
            } ,
                default_cors_preflight_options={
                    "allow_origins": api.Cors.ALL_ORIGINS,
                    "allow_methods": api.Cors.ALL_METHODS
            }
        )

        start = apigw.root.add_resource('start')
        start.add_method('ANY',integration=api.LambdaIntegration(starter))


        apigw.root.add_method('ANY')

        dns.ARecord(self,'PointDNSToApiGW',

            zone= zone,
            target=dns.RecordTarget.from_alias(targets.ApiGateway(apigw)),
            record_name=f"minecrafter.{domain}"
        )



