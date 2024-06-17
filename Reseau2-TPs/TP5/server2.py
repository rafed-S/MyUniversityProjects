from socket import *
import sys
import select


def handle_new_connection(socket, clients, sockets, liste_clients):
    (nouvellesocket, adr) = socket.accept()
    clients.append(nouvellesocket)
    sockets.append(nouvellesocket)
    liste_clients[nouvellesocket] = adr
    print(f"{adr[0]} connecte.")
    message = f"{adr[0]} connecté."
    return message


def handle_disconnection(socket, clients, sockets, liste_clients):
    socket.close()
    clients.remove(socket)
    sockets.remove(socket)
    print(f"{liste_clients[socket][0]} débranche.")
    message = f"{liste_clients[socket][0]} débranche."
    return message


def handle_message(socket, message_recu):
    print(f"{liste_clients[socket][0]}: {message_recu}")
    message = f"{liste_clients[socket][0]}: {message_recu}"
    return message


port = int(sys.argv[1])
mysocket = socket(AF_INET, SOCK_STREAM, IPPROTO_TCP)
mysocket.bind(('', port))
mysocket.listen(10)

sockets = [mysocket]
clients = []
liste_clients = dict()

while True:
    [veutmeparler, _, _] = select.select(sockets, [], [])
    for socket in veutmeparler:
        if socket == mysocket:
            message = handle_new_connection(socket, clients, sockets, liste_clients)
        else:
            message_recu = socket.recv(1024).decode('utf-8')
            if len(message_recu) == 0 or message_recu == 'FIN':
                message = handle_disconnection(socket, clients, sockets, liste_clients)
            else:
                message = handle_message(socket, message_recu)

        message_bytes = bytes(message, "utf-8")

        for client in clients:
            if client != socket:
                client.send(message_bytes)
