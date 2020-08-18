### CDK to create Minecraft server
The task of this CDK is to create all resources neccesary to create Minecraft Server on Fargate.
To minimise costs, minecraft server will shut down after certain period of time, if there is no players on it


# To use CDK : 
```
$ source .env/bin/activate
$ pip install -r requirements.txt
$ cdk diff
$ cdk deploy
```

# Resources created by CDK
- ECS server
