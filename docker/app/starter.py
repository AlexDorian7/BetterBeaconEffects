#Test3
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

# Wrzucamy server minecraft tam gdzie być musi
copyfile('/app/eula.txt','/minecraft/eula.txt')
copyfile('/app/server.properties','/minecraft/server.properties')
copyfile('/app/server.jar','/minecraft/server.jar')

# Tu odpalamy minecrafta , czekamy pewien czas i rozpoczynamy pętlę crontaba   
proc = sp.Popen(["java","-Xmx2048M","-Xms2048M","-jar","./server.jar","nogui"])
print("Started Minecraft, now waiting for crontab")
time.sleep(120)
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
# Tu ewentualnie robimy backup, zanim wyjdziemy z aplikacji
