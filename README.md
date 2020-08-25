### Fargated Minecraft
Based on AWS Infrastructure Minecraft server.
It uses Lambda with Api Gateway to start and oversee minecraft session,
ECS Fargate and EFS  , to run said session
Docker Image is kept on Dockerhub, and Github is proviging code for it. 

Version 1.0
- Webpage allows to start service, and informs when Server is Ready


Project is using CDK to maintain infrastructure in AWS. 

Docker consist of python scripts that runs minecraf.jar, and checks for people online.
If server is not used for 10 min, it is shut down. 


