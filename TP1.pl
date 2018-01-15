%exemple 1
habite(frederic,vesoul).
habite(fabrice,marseille).
habite(fabien,belfort).
habite(jacques,vesoul).

meme_endroit(X,Y):-
  habite(X,V),
  habite(Y,V).


%exemple 2

appartient(X,[X|_Y]).
appartient(X,[_Z|Y]):-
  appartient(X,Y).

%exemple 3
% concatene(L1,L2,R) --> R is concat of L1,L2
concatene1([],L,L).
concatene1([X|L1],L2,R):-
  concatene1(L1,[X|L2],R).

concatene2a([],L,L).
concatene2a([X|L1],L2,R):-
  concatene2a(L1,L2,R1),
  R=[X|R1].

concatene2b([],L,L).
concatene2b([X|L1],L2,[X|R1]):-
  concatene2b(L1,L2,R1).


%exemple 4-5
double:-
  write('Entrez un nombre : '),
  read(A),
  Z is 2*A,
  nl,
  write('Le Double de '),
  write(A),
  write(' est '),
  write(Z).


%exemple 6
appartient1(X,[X|_]):-
  !.
appartient1(X,[_|Y]):-
  appartient1(X,Y).


%exemple 7a
pair1(L,L_Nombres_Pairs):-
  pair1(L,[],L_Nombres_Pairs).

pair1([],Acc,Acc).
pair1([E|L],Acc,L_Nombres_Pairs):-
  0 is E mod 2,
  pair1(L,[E|Acc],L_Nombres_Pairs).
pair1([E|L],Acc,L_Nombres_Pairs):-
  R is E mod 2,
  R\=0, % ou dif(R,O)
  pair1(L,Acc,L_Nombres_Pairs).
