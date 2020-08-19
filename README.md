### Fargated Minecraft
# Project will use CDK to create all neccesary components : 
- ECS cluster
- Task Definition
- EFS to hold data
- Api Gateway 
- Lambda starting service
- Backup job to keep backups of EFS
# Docker itself is kept in dockehub, contains :
- Minecraft Server
- Python script that terminates docker after certain time of inactivity

Folder /minecraft contains all data and settings, it should be added as volumen.
Minecraft port is set to default 25565

As I want to keept it as simple as possible, server.properties, server.jar and eula is already kept in app folder, 
and copied to /minecraft folder on start. Python script terminates docker after 10min of 0 users online. 
Since script can respont do sigterm, one can add backup at end of script . 


