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

message = "J'aime le cours de Réseaux 2"
message_bytes = bytes(message, "utf-8")

try:
    sent = mysocket.send(message_bytes)
    resultat_bytes = mysocket.recv(60)
    resultat = str(resultat_bytes, "utf-8")
    print(resultat)
except Exception as e:
    print("réception/envoi message erreur :",e)
    sys.exit(-4)

mysocket.close()
sys.exit(0)


