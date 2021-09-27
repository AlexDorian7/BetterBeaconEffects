#!/usr/bin/env python3
import subprocess as sp
import time
import signal
import sys
from mcrcon import MCRcon
from shutil import copyfile

exit_timer = 600

ram = 4096
port = 25565
instance = ""

if len(sys.argv) >= 4:
    try:
        ram = int(sys.argv[3])
    except:
        ram = 4096
if len(sys.argv) < 3:
    print("ERROR: Syntax: starter.py [instance id] [port] <ram amount in MB> \n[] = reqired, <> = optional")
    quit()
else:
    try:
        if int(sys.argv[2]) < 0 or int(sys.argv[2]) > 65535:
            print("ERROR: Port out of range!")
            quit()
        port = int(sys.argv[2])
    except:
        print("ERROR: port is not a number!")
        quit()
    instance = sys.argv[1]

def get_people_online():
    print("Getting list of players")
    with MCRcon("localhost", "ebbeh", port) as mcr:
        resp = mcr.command("/list")
        return int(resp.split(' ')[2])
    return 0

def use_stop_cmd():
    print("Stopping server " + instance + "!")
    with MCRcon("localhost", "ebbeh", port) as mcr:
        mcr.command("/save-all")
        mcr.command("/stop")
    return

# Copy minecraft server and base configs to proper place in /minecraft foler
copyfile('/app/eula.txt','/minecraft/eula.txt')
copyfile('/app/server.properties','/minecraft/server.properties')
copyfile('/app/ops.json','/minecraft/ops.json')
copyfile('/app/banned-ips.json','/minecraft/banned-ips.json')
copyfile('/app/banned-players.json','/minecraft/banned-players.json')
copyfile('/app/paper.yml','/minecraft/paper.yml')
copyfile('/app/spigot.yml','/minecraft/spigot.yml')
copyfile('/app/bukkit.yml','/minecraft/bukkit.yml')
copyfile('/app/server.jar','/minecraft/server.jar')

# Starting Minecraft server with 4GB ram, and giving 2 min to finish start , before testing for users
# TODO: Make port reflect in server.properties
proc = sp.Popen(["java","-Xmx" + ram + "M","-Xms256M","-jar","./server.jar","nogui"])
print("Started Minecraft, now waiting for crontab")
time.sleep(300)
while True:
    time.sleep(60)
    people_online = get_people_online()
    if people_online == 0:
        exit_timer -= 60
        print(f"No one online, decreasing timer to {exit_timer}")
    else:
        exit_timer = 600
    if exit_timer <= 0:
        use_stop_cmd()
        # proc.terminate()
        proc.wait()
        print("Finished App")
        break
# This place is run, after minecraft server was stopped
# Good place to call eventual backup to S3