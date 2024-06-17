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
    mysocket.bind(('', port))
    mysocket.listen(1)
    (socket2, adresse_client) = mysocket.accept()
except:
    print("création du socket erreur")
    sys.exit(-2)

try:
    message = socket2.recv(60)
    print("IP et le port du client : ",adresse_client)
except Exception as e:
    print("réception erreur")
    print("message erreur :", e)
    sys.exit(-3)


try:
    x1 = int.from_bytes(message[0:4], byteorder="big")
    x2 = int.from_bytes(message[4:8], byteorder="big")
    socket2.send(int.to_bytes(x1+x2,4, byteorder="big"))
    socket2.close()
except Exception as e:
    print("envoi message erreur :",e)
    sys.exit(-4)

mysocket.close()
sys.exit(0)



