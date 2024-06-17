from socket import *
import sys

try:
    if(len(sys.argv) != 3):
        print("Number of arguments != 3")
        print("Number of arguments = ",len(sys.argv))
    else:
        mysocket = socket(AF_INET, SOCK_DGRAM, IPPROTO_UDP)
        message = "J'aime le cours de Réseaux 2"
        message_bytes = bytes(message, "utf-8")
        ip = sys.argv[1]
        print("ip :",ip)
        port = sys.argv[2]
        print("port :",port)
        sent = mysocket.sendto(message_bytes,(ip, int(port)))
        (resultat_bytes, adresse_serveur) = mysocket.recvfrom(60)
        resultat = str(resultat_bytes, "utf-8")
        print("message :",resultat)
        mysocket.close()

except:
    logging.exception("An exception was thrown!")

sys.exit(0)


