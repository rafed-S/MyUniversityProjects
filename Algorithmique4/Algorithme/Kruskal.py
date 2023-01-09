import unionfind as UF

edges = sum([[(weight,u,v)
for (v,weight) in adj[u]]
for u in range(n)], [])
edges.sort()
# Phase 2:
uf = UF.create(n)
S = []
for (k,u,v) in edges:
if UF.find(uf, u) != UF.find(uf, v):
UF.union(uf, u,v)
S.append((u,v))
print S
