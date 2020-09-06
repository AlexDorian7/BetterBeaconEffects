"""
Front page. Will check for cookie, or authentication header
if none found will forward to Cognito Login Page.
"""

import json

def redirect_to_authentication():
    response = {}
    response["statusCode"]=302
    response["headers"]={'Location': 'https://www.google.com'}
    data = {}
    response["body"]=json.dumps(data)
    return response
    
    


def lambda_handler(event, context):
    print(event)
    # Check for Cookie

    auth_cookie = ""
    # Check for Auth
    auth_token = ""
    if auth_cookie == "" and auth_token == "":
        redirect_to_authentication()
    