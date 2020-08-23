import json
from string import Template
import os
import time

url = os.environ['url']

def lambda_handler(event, context):
    
    query=event.get('queryStringParameters',{'state':'page'})
    if query is None:
        query = {'state':'page'}
    state = query.get('state','page')
    if state == 'get_state':
        return {
            "statusCode": 200,
            "body":  json.dumps({'message':time.strftime('%H:%M:%S',time.gmtime(time.time()+7200))})          
        }
        

    with open("index.html") as reader:
        idx = reader.read()

    src = idx.replace("#url#",url)

    return {
        "statusCode": 200,
        "body":  src,            
        "headers": {
                "Content-Type": "text/html; charset=utf-8"
            }
    }