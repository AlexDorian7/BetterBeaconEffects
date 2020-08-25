
import boto3
import os
import json
import time


cluster = os.environ['cluster'] 
subnets = os.environ['subnets'].split(',')
security_groups = os.environ['security_groups'].split(',')
task_definition = os.environ['task_definition']
region = os.environ['region']
zone = os.environ['zone_id']
domain = os.environ['domain']

response = {
            "statusCode": 200
}


def get_minecraft_status():

    client = boto3.client('ecs',region_name=region)

    # Check if it is still running : 
    tasks = client.list_tasks(
        cluster = cluster,
        launchType = 'FARGATE'
    )
    running_tasks = len(tasks['taskArns'])

    if running_tasks == 0:
        return "Server Stopped"
    else:
        return "Server Started"


def start_minecraft():

    client = boto3.client('ecs',region_name=region)

    # Check if it is still running : 
    tasks = client.list_tasks(
        cluster = cluster,
        launchType = 'FARGATE'
    )
    running_tasks = len(tasks['taskArns'])

    if running_tasks == 0:
        response = client.run_task(
            cluster=cluster,
            count=1,
            launchType='FARGATE',
            networkConfiguration={
                'awsvpcConfiguration': {
                    'subnets': subnets,
                    'securityGroups': security_groups,
                    'assignPublicIp': 'ENABLED'
                }
            },
            platformVersion='1.4.0',
            taskDefinition=task_definition
        )
        task_arn = response['tasks'][0]['containers'][0]['taskArn']
        print(task_arn)

        nislen=0
        while nislen==0:        
            response = client.describe_tasks(cluster = cluster,tasks = [task_arn])
            eni = response['tasks'][0]['attachments'][0]['details']
            print(eni)

            niis = [id['value'] for id in eni if id['name']=='networkInterfaceId']
            nislen = len(niis)
            time.sleep(5)
            print("waiting for IP")
            
        nis = niis[0]
        

        ec2 = boto3.resource('ec2',region_name=region)
        network_interface = ec2.NetworkInterface(nis)
        public_ip = network_interface.association_attribute['PublicIp']
        print(public_ip)
        client = boto3.client('route53')
        response = client.change_resource_record_sets(
            HostedZoneId=zone,
            ChangeBatch={
                'Changes': [
                    {
                        'Action': 'UPSERT',
                        'ResourceRecordSet': {
                            'Name': f'minecraft.{domain}',
                            'Type': 'A',
                            'TTL': 60,
                            'ResourceRecords': [
                                {
                                    'Value': public_ip
                                },
                            ],
                        }
                    },
                ]
            }
        )

        
        return "Started"
    else:
        return "Already Running"


def lambda_handler(event, context):

    query=event.get('queryStringParameters',{'state':'status'})
    if query is None:
        query = {'state':'status'}
    state = query.get('state','status')

    if state == 'status':
        resp = get_minecraft_status()

    elif state == 'start': 
        resp = start_minecraft()
    else:
        resp = 'That should not happen'



    response['body'] =  json.dumps({'message':resp})    
    
    
    return response