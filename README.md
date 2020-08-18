### Fargated Minecraft
Project will use CDK to create all neccesary components : 
- ECS cluster
- Task Definition
- EFS to hold data
- Api Gateway 
- Lambda starting service
- Cloudwatch cron job to terminate unused service
- Backup job to keep backups of EFS

Things to check before : 
- How to expose Minecraft ports
- How to tie domain to minecraft

