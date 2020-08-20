#!/usr/bin/env python3
import boto3
import os

cluster = 'minecraft-ClusterEB0386A7-Z0d6m6gZQG45'
subnets = ['subnet-00bbb028597bb7277','subnet-03dca0e092b7fc087','subnet-068ae49f80d2c893e']
security_groups = ['sg-06940f5bd4a4b2c30']
task_definition = 'minecraftTaskDefA418B480:6'
region = 'eu-west-1'

def working():

    client = boto3.client('ecs',region_name=region)

    # Check if it is still running : 
    tasks = client.list_tasks(
        cluster = cluster,
        launchType = 'FARGATE'
    )
    running_tasks = len(tasks['taskArns'])
    
    if running_tasks == 0 :
        return 'No Tasks Running'

    task_arn = tasks['taskArns'][0]


    #Get IP of current Task: 
    response = client.describe_tasks(cluster = cluster,tasks = [task_arn])
    eni = response['tasks'][0]['attachments'][0]['details']
    nii = [id['value'] for id in eni if id['name']=='networkInterfaceId'][0]

    ec2 = boto3.resource('ec2',region_name=region)
    network_interface = ec2.NetworkInterface(nii)
    public_ip = network_interface.association_attribute['PublicIp']
    print(public_ip)


