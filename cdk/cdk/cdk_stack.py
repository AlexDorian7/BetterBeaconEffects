from aws_cdk import (
    core,
    aws_ecs as ecs,
    aws_ec2 as ec2,
    aws_efs as efs,
    aws_lambda as _lambda,
    aws_iam as iam

)


class CdkStack(core.Stack):

    def __init__(self, scope: core.Construct, id: str,region, **kwargs) -> None:
        super().__init__(scope, id, **kwargs)

        # VPC , we need one for ECS cluster ( sadly )
        vpc = ec2.Vpc.from_lookup(self,'vpc',is_default=True)

        cluster = ecs.Cluster(self,'Cluster', vpc= vpc)

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

        fs.connections.allow_default_port_from(sg)

        subnets =     ",".join(vpc.select_subnets().subnet_ids)  


        # Lambda Part 
        starter = _lambda.Function(self,'Starter',
            runtime = _lambda.Runtime.PYTHON_3_8,
            handler = 'index.lambda_handler',
            code = _lambda.Code.asset('lambda/starter'),
            timeout=core.Duration.seconds(300),
            environment={
                'cluster' : cluster.cluster_name, #'minecraft-ClusterEB0386A7-Z0d6m6gZQG45'
                'subnets' : subnets, #['subnet-00bbb028597bb7277','subnet-03dca0e092b7fc087','subnet-068ae49f80d2c893e']
                'security_groups' : sg.security_group_id, #['sg-06940f5bd4a4b2c30']
                'task_definition' : task_definition.task_definition_arn, #'minecraftTaskDefA418B480:6'
                'region' : region
            }
        )

        starter.add_to_role_policy(
            iam.PolicyStatement(
                effect = iam.Effect.ALLOW,
                resources = ["*"],
                actions = ["ecs:ListTasks"]
            )
        )
        starter.add_to_role_policy(
            iam.PolicyStatement(
                effect = iam.Effect.ALLOW,
                resources = [task_definition.task_definition_arn],
                actions = ["ecs:RunTask"]
            )
        )
        starter.add_to_role_policy(
            iam.PolicyStatement(
                effect = iam.Effect.ALLOW,
                resources = [task_definition.task_role.role_arn,task_definition.execution_role.role_arn],
                actions = ["iam:PassRole"]
            )
        )

        # To Do Now : 
        # - Lambda that can launch Task : https://docs.aws.amazon.com/AmazonECS/latest/APIReference/API_RunTask.html
        # - Nice to have - Said lambda getting IP and setting it as new DNS 
        # - Route53 zone 
        # - Route53 Record, pointing to minecraft server
        # - ApiGW that shows status of server , and allows to start it . Prefferably Ajax
