#!/usr/bin/env python3

from aws_cdk import core

from cdk.cdk_stack import CdkStack

import configparser

config = configparser.ConfigParser()
config.read('config.ini')

app_envs = {
    'env':core.Environment(
        account=config.get('MAIN','account_id'),
        region=config.get('MAIN','region')
    )
}


app = core.App()
CdkStack(app, config.get('MAIN','stack_name'),**app_envs)

app.synth()
