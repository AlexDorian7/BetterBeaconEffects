import boto3
import os


cluster = os.environ['cluster'] 
subnets = os.environ['subnets'].split(',')
security_groups = os.environ['security_groups'].split(',')
task_definition = os.environ['task_definition']
region = os.environ['region']
response = {
            "statusCode": 200
}

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
        return "ok"
    else:
        return "already running"


def lambda_handler(event, context):
    resp = start_minecraft()

    response['body'] = f'{{"Status":"{resp}"}}'
    
    
    return response