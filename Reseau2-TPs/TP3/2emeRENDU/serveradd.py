from socket import *
import sys


try:
    mysocket = socket(AF_INET, SOCK_DGRAM, IPPROTO_UDP)
except:
    logging.exception("An exception was thrown! -> socket")
    sys.exit(-2)

try:
    mysocket.bind(('',1234))
except:
    logging.exception("An exception was thrown! -> mysocket.bind")
    sys.exit(-3)

try:
    (message, adresse_client) = mysocket.recvfrom(60)
except:
    logging.exception("An exception was thrown! -> mysocket.recvfrom(60)")
    sys.exit(-4)

print("IP et le port client : ")
print(adresse_client)
x = int.from_bytes(message[0:4], byteorder="big")
y = int.from_bytes(message[4:8], byteorder="big")

try:
    sent = mysocket.sendto(int.to_bytes(x+y,4, byteorder="big"), adresse_client)
except:
    logging.exception("An exception was thrown! -> sent")
    sys.exit(-5)

try:
    mysocket.close()
except:
    logging.exception("An exception was thrown! -> mysocket.close")
    sys.exit(-6)

sys.exit(0)
