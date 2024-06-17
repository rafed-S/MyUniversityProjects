from socket import *
import sys

if len(sys.argv) != 3:
    print("Le nombre d'argument != 3")
    print("Usage: python script.py <host> <port>")
    sys.exit(-1)


try:
    host = sys.argv[1]
    port = int(sys.argv[2])
    mysocket = socket(AF_INET, SOCK_STREAM, IPPROTO_TCP)
except:
    print("création du socket erreur")
    sys.exit(-2)

try:
    mysocket.connect((host, port))
except Exception as e:
    print("connexion erreur")
    print("message erreur :", e)
    sys.exit(-3)

x1 = int(input("1er entier : "))
x2 = int(input("2eme entier : "))

x1_bytes = int.to_bytes(x1, 4, byteorder="big")
x2_bytes = int.to_bytes(x2, 4, byteorder="big")

try:
    sent = mysocket.send(x1_bytes + x2_bytes)
    resultat_bytes = mysocket.recv(60)
    resultat = str(resultat_bytes, "utf-8")
    print(resultat)
    print("1er entier + 2eme entier = " + str(int.from_bytes(resultat_bytes, byteorder="big")))
except Exception as e:
    print("réception/envoi message erreur :",e)
    sys.exit(-4)

mysocket.close()
sys.exit(0)


