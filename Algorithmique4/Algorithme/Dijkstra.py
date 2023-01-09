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
nodes[0].key = 0
for node in nodes:
H = heap.insert(H, node)
#Algorithme principal
for i in range(n):
(H,elem) = heap.extractmin(H)
v = elem.name
u = elem.parent
visited[v] = True
for (w,cost) in adj[v]:
if not visited[w]:
if nodes[w].key > cost+nodes[v].key:
heap.decreaseKey(H,nodes[w], cost+nodes[v].key)
nodes[w].parent = v
for u in nodes:
print u.name, u.key
