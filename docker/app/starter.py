#Test3
import subprocess as sp
import time
import signal
from mcrcon import MCRcon

exit_timer = 120


def get_people_online():
    print("Getting list of players")
    with MCRcon("localhost", "ebbeh") as mcr:
        resp = mcr.command("/list")
        print(resp)
        return int(resp.split(' ')[2])
    return 0

# Tu odpalamy minecrafta , czekamy pewien czas i rozpoczynamy pętlę crontaba   
proc = sp.Popen(["java","-Xmx1024M","-Xms1024M","-jar","./server.jar","nogui"])
print("Started Minecraft, now waiting for crontab")
time.sleep(120)
while True:
    time.sleep(5)
    people_online = get_people_online()
    if people_online == 0:
        exit_timer -= 5
        print(f"No one online, decreasing timer to {exit_timer}")
    else:
        exit_timer = 30
    if exit_timer <= 0:
        proc.terminate()
        proc.wait()
        print("Finished App")
        break
# Tu ewentualnie robimy backup, zanim wyjdziemy z aplikacji
