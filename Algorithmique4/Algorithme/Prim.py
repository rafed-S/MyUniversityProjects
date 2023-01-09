import binomial as heap

class Node(heap.Element):
def __init__(self,name):
heap.Element.__init__(self,sys.maxint)
self.name = name
self.parent = -1
visited = [ False for i in range(n)]
# Initialisation du tas
H = []
nodes = [Node(i) for i in range(n)]
for node in nodes:
H = heap.insert(H, node)
# On visite le sommet 0 et on traite ses voisins.
visited[0] = True
for (v,weight) in adj[0]:
heap.decreaseKey(H,nodes[v], weight)
nodes[v].parent = 0
S = []
#Algorithme principal
for i in range(n-1):
(H,elem) = heap.extractmin(H)
v = elem.name
u = elem.parent
S.append((u, v))
visited[v] = True
for (w,weight) in adj[v]:
if not visited[w]:
if nodes[w].key > weight:
heap.decreaseKey(H,nodes[w], weight)
nodes[w].parent = v
print S
