from socket import *
import sys
import select


def handle_received_message(socket):
    msg = socket.recv(1024).decode('utf-8')
    if not msg:
        return None
    else:
        print(msg)
        return msg


def handle_user_input():
    message = sys.stdin.readline().strip()
    sys.stdout.flush()
    sys.stdin.flush()
    if message == "FIN":
        print("Vous vous etes deconnecte.")
        return None
    else:
        message_bytes = bytes(message, "utf-8")
        return message_bytes


host = sys.argv[1]
port = int(sys.argv[2])
(fam, typ, pro, _, adr) = getaddrinfo(host, port)[0]
mysocket = socket(fam, typ, pro)
mysocket.connect(adr)

arret = False

while not arret:
    sockets = [mysocket, sys.stdin]
    veutmeparler, _, _ = select.select(sockets, [], [])
    for socket in veutmeparler:
        if socket == mysocket:
            received_message = handle_received_message(socket)
            if received_message is None:
                arret = True
                sockets.remove(socket)
        else:
            user_input = handle_user_input()
            if user_input is None:
                arret = True
            else:
                sent = mysocket.send(user_input)

mysocket.close()
