from socket import *
import sys

try:
    mysocket = socket(AF_INET, SOCK_DGRAM, IPPROTO_UDP)
except:
    logging.exception("An exception was thrown! -> socket")
    sys.exit(-1)

try:
    mysocket.bind(('',1234))
except:
    logging.exception("An exception was thrown! -> mysocket.bind")
    sys.exit(-2)

try:
    (message, adresse_client) = mysocket.recvfrom(60)
except:
    logging.exception("An exception was thrown! -> mysocket.recvfrom(60)")
    sys.exit(-3)

try:
    mysocket.sendto(message.decode("utf-8").upper().encode("utf-8"), adresse_client)
except:
    logging.exception("An exception was thrown! -> sent")
    sys.exit(-4)

try:
    mysocket.close()
except:
    logging.exception("An exception was thrown! -> mysocket.close")
    sys.exit(-5)

sys.exit(0)
