#!/usr/bin/env python3
import subprocess as sp
import time
import signal
from mcrcon import MCRcon
from shutil import copyfile

exit_timer = 600


def get_people_online():
    print("Getting list of players")
    with MCRcon("localhost", "ebbeh") as mcr:
        resp = mcr.command("/list")
        return int(resp.split(' ')[2])
    return 0

# Copy minecraft server and base configs to proper place in /minecraft foler
copyfile('/app/eula.txt','/minecraft/eula.txt')
copyfile('/app/server.properties','/minecraft/server.properties')
copyfile('/app/server.jar','/minecraft/server.jar')

# Starting Minecraft server with 2GB ram, and giving 2 min to finish start , before testing for users 
proc = sp.Popen(["java","-Xmx2048M","-Xms2048M","-jar","./server.jar","nogui"])
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
        proc.terminate()
        proc.wait()
        print("Finished App")
        break
# This place is run, after minecraft server was stopped
# Good place to call eventual backup to S3