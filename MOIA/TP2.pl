%%  MOIA
%%  TP2
%%  Goodwin Benjamin

%%  List

% 1.1
premier(X,[A|_]):-
  X = A.

:-begin_tests(chp0).
test('premier(X,[2,4,6])',[true(X==2)]):-
  premier(X,[2,4,6]).
test('premier(X,[])',[fail]):-
  premier(x,[]).
:-end_tests(chp0).
% 1.2
dernier(X,[X]).
dernier(X,[_|L]):-
  dernier(X,L).

:-begin_tests(chp1).
test('dernier(X,[2,4,6])',[true(X==6)]):-
  dernier(X,[2,4,6]).
:-end_tests(chp1).

% 1.3
ajout_debut(X,L,R):-
  R = [X|L].

:-begin_tests(chp2).
test('ajout_debut(1,[2,3,4],R)',[true(R==[1,2,3,4])]):-
  ajout_debut(1,[2,3,4],R).
:-end_tests(chp2).


% 1.4
ajout_fin(X,[],[X]).
ajout_fin(X,[A|L],[A|R]):-
  ajout_fin(X,L,R).

:-begin_tests(chp3).
test('ajout_fin(8,[2,4,6],R)',[true(R==[2,4,6,8])]):-
  ajout_fin(8,[2,4,6],R).
:-end_tests(chp3).


% 1.5
appartient(X,[X|_y]).
appartient(X,[_Z|Y]):-
  appartient(X,Y).

inclus([],_).
inclus([X|L],M):-
  appartient(X,M),
  inclus(L,M).

:-begin_tests(chp4).
test('inclus([2,4],[2,4,6])',[true]):-
  inclus([2,4],[2,4,6]).
:-end_tests(chp4).


% 1.6
% Not working interly to much data in the end.
renverse_liste([],[]).
renverse_liste([X|L],LR):-
  renverse_liste(L,R),
  %% I would prefer use ajout_debut but it adds too much "[","]"
  append(R,[X],LR).

:-begin_tests(chp5).
test('renverse_liste([2,4,6],R)',[true(R==[6,4,2])]):-
  renverse_liste([2,4,6],R).
:-end_tests(chp5).


%1.7
%Not Implemented
sous_liste_gauche(L,K).
%sous_liste_gauche():-

%1.8
%Not Implemented
sous_liste_droite(L,K).
%sous_liste_droite():-

%%  Number

% 2.1
longueur([],0).
longueur([_|L],A):-
  longueur(L,Atemp),
  A is Atemp + 1.

:-begin_tests(chp6).
test('longueur([2,4,6],Size)',[true(Size==3)]):-
  longueur([2,4,6], Size).
:-end_tests(chp6).



% 2.2
somme([],0).
somme([X|L],A):-
  somme(L,Atemp),
  A is Atemp + X.

:-begin_tests(chp7).
test('somme([2,4,6],Sum)',[true(Sum==12)]):-
  somme([2,4,6],Sum).
:-end_tests(chp7).

