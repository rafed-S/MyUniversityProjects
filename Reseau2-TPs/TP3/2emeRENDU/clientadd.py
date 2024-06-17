from socket import *
import sys

try:
    if(len(sys.argv) != 3):
        print("Number of arguments != 3")
        print("Number of arguments = ",len(sys.argv))
    else:
        mysocket = socket(AF_INET, SOCK_DGRAM, IPPROTO_UDP)
        ip = sys.argv[1]
        print("ip :",ip)
        port = sys.argv[2]
        print("port :",port)
        x = int(input("Donner un entier : "))
        y = int(input("Donner un deuxième entier : "))
        x_bytes = int.to_bytes(x, 4, byteorder="big")
        y_bytes = int.to_bytes(y, 4, byteorder="big")
        sent = mysocket.sendto(x_bytes + y_bytes, (sys.argv[1], int(sys.argv[2])))
        (resultat_bytes, adresse_serveur) = mysocket.recvfrom(60)
        print("Voici le résultat : " + str(int.from_bytes(resultat_bytes, byteorder="big")))
        mysocket.close()

except:
    logging.exception("An exception was thrown!")

sys.exit(0)
