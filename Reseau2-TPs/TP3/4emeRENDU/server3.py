from socket import *
import sys

messages_recus = {}

while True:
    try:
        mysocket = socket(AF_INET, SOCK_DGRAM, IPPROTO_UDP)
    except:
        logging.exception("Erreur lors de la création -> mysocket = socket.")
        sys.exit(-1)

    try:
        mysocket.bind(('', 1234))
    except:
        logging.exception("Erreur lors de la liaison -> mysocket.bind")
        sys.exit(-2)

    try:
        (message, client_addr) = mysocket.recvfrom(60)
        x = int.from_bytes(message[0:4], byteorder="big")
    except:
        logging.exception("Erreur lors de la réception -> mysocket.recvfrom")
        sys.exit(-3)

    if not(client_addr in messages_recus) :
        if isinstance(x, int):
            messages_recus[client_addr] = x
        else:
            logging.error("Le premier message reçu n'est pas un entier")
    else :
        if isinstance(x, int):
            messages_recus[client_addr] += x
            try:
                sent = mysocket.sendto(int.to_bytes(messages_recus[client_addr], 4, byteorder="big"), client_addr)
            except:
                logging.exception("Erreur lors de l'envoi du message -> mysocket.sendto")
                sys.exit(-4)
        else:
            logging.error("Le message reçu n'est pas un entier")

    try:
        mysocket.close()
    except:
        logging.exception("Erreur lors de la fermeture -> mysocket.close")
        sys.exit(-5)

