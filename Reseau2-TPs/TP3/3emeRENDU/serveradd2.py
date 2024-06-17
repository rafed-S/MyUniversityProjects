from socket import *
import sys

while True:

    try:
        mysocket = socket(AF_INET, SOCK_DGRAM, IPPROTO_UDP)
    except:
        logging.exception("An exception was thrown! -> lors de la création -> mysocket = socket")
        sys.exit(-1)

    try:
        mysocket.bind(('',1234))
    except:
        logging.exception("An exception was thrown! -> lors de la liaison -> mysocket.bind")
        sys.exit(-2)

    try:
        (message, adresse_client) = mysocket.recvfrom(60)
        print("Premier entier reçu")
        x = int.from_bytes(message[0:4], byteorder="big")
    except:
        logging.exception("An exception was thrown! -> lors de la 1er réception -> mysocket.recvfrom")
        sys.exit(-3)

    try:
        (message, adresse_client) = mysocket.recvfrom(60)
        print("Premier deuxième entier reçu")
        y = int.from_bytes(message[0:4], byteorder="big")
    except:
        logging.exception("An exception was thrown! -> lors de la 2eme réception -> mysocket.recvfrom")
        sys.exit(-4)


    try:
        sent = mysocket.sendto(int.to_bytes(x+y,4, byteorder="big"), adresse_client)
    except:
        logging.exception("An exception was thrown! -> lors de l'envoi -> mysocket.sendto")
        sys.exit(-5)

    try:
        mysocket.close()
    except:
        logging.exception("An exception was thrown! -> lors de la fermeture -> mysocket.close")
        sys.exit(-6)

sys.exit(0)
