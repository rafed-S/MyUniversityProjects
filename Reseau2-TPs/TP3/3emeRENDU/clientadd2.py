from socket import *
import sys

while True:

    if(len(sys.argv) != 3):
        print("Number of arguments != 3")
        print("Number of arguments = ",len(sys.argv))
        sys.exit(-1)
    else:
        try:
            mysocket = socket(AF_INET, SOCK_DGRAM, IPPROTO_UDP)
        except:
            print("Erreur lors de la création -> mysocket = socket.")
            sys.exit(-2)

        try:
            x = int(input("Donner un entier : "))
            x_bytes = int.to_bytes(x, 4, byteorder="big")
            sent = mysocket.sendto(x_bytes,(sys.argv[1], int(sys.argv[2])))
        except:
            print("Erreur lors de l'envoi du premier message -> mysocket.sendto.")
            sys.exit(-3)

        try:
            x = int(input("Donner un entier : "))
            x_bytes = int.to_bytes(x, 4, byteorder="big")
            sent = mysocket.sendto(x_bytes,(sys.argv[1], int(sys.argv[2])))
        except:
            print("Erreur lors de l'envoi du deuxième message -> mysocket.sendto.")
            sys.exit(-3)

        try:
            (resultat_bytes, adresse_serveur) = mysocket.recvfrom(60)
        except:
            print("Erreur lors de la réception -> mysocket.recvfrom .")
            sys.exit(-4)

    print("Voici le résultat : " + str(int.from_bytes(resultat_bytes, byteorder="big")))
    try:
        mysocket.close()
    except:
        print("Erreur lors de la fermeture -> mysocket.close .")
        sys.exit(-5)

sys.exit(0)
