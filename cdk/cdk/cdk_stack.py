from aws_cdk import (
    core,
    aws_ecs as ecs,
    aws_ec2 as ec2,
    aws_efs as efs

)


class CdkStack(core.Stack):

    def __init__(self, scope: core.Construct, id: str, **kwargs) -> None:
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

        ecsService = ecs.FargateService(self,'Service',
            cluster = cluster,
            task_definition = task_definition,
            assign_public_ip = True,
            security_group = sg,
            platform_version=ecs.FargatePlatformVersion.VERSION1_4
        )

        fs.connections.allow_default_port_from(sg)