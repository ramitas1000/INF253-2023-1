
%Grafo del enunciado (Hechos)
vecino(a,b).
vecino(a,c).
vecino(a,e).
vecino(b,c).
vecino(c,d).
vecino(c,e).
vecino(e,b).
vecino(f,b).
vecino(f,e).
vecino(f,g).
vecino(g,e).
vecino(h,g).
vecino(h,i).
vecino(h,j).
vecino(i,g).
vecino(j,g).
vecino(j,i).


%Predicado auxiliar para puedellegar

%Caso base, X es vecino de Y
llegara(X, Y, _) :-
    vecino(X, Y).

%Caso recursivo, para evitar loops infinitos
%Se verifica si Z ha sido visitado y si no lo ha sido
%se hace el llamado recursivo
llegara(X, Y, Visited) :-
    vecino(X, Z),
    member(Z, Visited),
    llegara(Z, Y, [Z|Visited]).

%Predicado puedellegar para los casos (X,nodo)
%Se encuentran todos nodos a los que se puede llegar
%Se eliminan los nodos repetidos con sort
%Se muestran con member
puedellegar(X, Y) :-
    findall(X, llegara(X, Y, []), Nodes),
    sort(Nodes, UniqueNodes),
    member(X, UniqueNodes).

%Predicado puedellegar para los casos (nodo,X)
%Funcionamiento similar al anterior puede llegar
puedellegar(X, Y) :-
    findall(Z, llegara(X, Z, []), Paths),
    sort(Paths, UniquePaths),
    member(Y, UniquePaths).

%Predicado vecinos
%Encuentra todos los vecinos del nodo X y los almacena en L
%usando findall
vecinos(X,L):-
    findall(Y, vecino(X,Y), L).


%Predicado camino valido
%Dada una lista de nodos, verifica que sean un camino posible en el grafo
%
% Caso base, una lista vacía.

%2do caso base, Lista con un solo elemento
caminovalido([_]).

%Caso recursivo
%Consigue nodos adyacentes en la lista y verifica que sean vecinos
%Si lo son se realiza el llamado recursivo
caminovalido([X, Y | Resto]) :- 
    vecino(X, Y),       
    caminovalido([Y | Resto]). 



%Predicado auxiliar corto implementado como bfs

%Caso base, se encontro Y en el camino actual
corto([[Y|Resto]|_], Y, [Y|Resto]). 

%Se encuantran varios caminos, asegurandose que la cabeza sea X y el ultimo nodo sea Y
%El pirmer camino es el mas corto por como esta implementado 
%Entrega el camino pero invertido
corto([[X|Resto]|Cola], Y, Camino) :-
    findall([Z,X|Resto], (vecino(X,Z), \+ member(Z,[X|Resto])), NuevosCaminos),
    append(Cola, NuevosCaminos, NuevaCola), 
    corto(NuevaCola, Y, Camino). 


%Predicado caminomascorto, llama al predicado auxiliar corto e invierte la lista entregado por el
caminomascorto(X, Y, Camino) :- corto([[X]], Y, Invertido), reverse(Invertido, Camino).



