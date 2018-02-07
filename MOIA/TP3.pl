% Goodwin Benjamin
% TP3
% 3 cannibals and 3 missionary


passagers_bateau(1,0).
passagers_bateau(2,0).
passagers_bateau(1,1).
passagers_bateau(0,1).
passagers_bateau(0,2).

%test

berge([_M,_C,_B]).
berge([3,3,0]).
berge([0,0,1]).

%test

deplacement_par_bateau([M, C, 0], MR, CR):-
    %passagers_bateau(X,Y),
    %deplacement_legal(X,Y),
    MR is M - X,
    CR is C - Y,
    missionaires_saufs(MR,CR).

deplacement_par_bateau([M, C, 1], MR, CR):-
    %passagers_bateau(X,Y),
    %deplacement_legal(X,Y),
    MR is M - X,
    CR is C - Y,
    missionaires_saufs(MR,CR).

%test

deplacement_legal(M, C):-
    M >= 0,
    C >= 0,
    M =< 3,
    C =< 3.
    % between(0,3,M) -> true, ...

%test
:-begin_tests(chp6).
test('deplacement_legal(-1,0)',[fail]):-
    deplacement_legal(-1,0).
test('deplacement_legal(0,4)',[fail]):-
    deplacement_legal(0,-1).
test('deplacement_legal(3,0)',[true]):-
    deplacement_legal(3,0).
test('deplacement_legal(777,999)',[fail]):-
    deplacement_legal(-777,999).
:-end_tests(chp6).


missionaires_saufs(0,0).
missionaires_saufs(1,0).
missionaires_saufs(1,1).
missionaires_saufs(2,0).
missionaires_saufs(2,1).
missionaires_saufs(2,2).
missionaires_saufs(3,0).
missionaires_saufs(3,1).
missionaires_saufs(3,2).
missionaires_saufs(3,3).

ms1(0,_).
ms1(M,C):-
    M >= C,
    diff(M,0).

missionaires_saufs(M,C):-
    ms1(M,C),
    M1 is 3-M,
    C1 is 3-C,
    ms1(M1,C1).

%Not very usefull test ...
:-begin_tests(chp7).
test('missionnaires_saufs(0,-1)',[fail]):-
    missionaires_saufs(0,-1).
:-end_tests(chp7).

%test

traversee([M1,C1,B],[M2,C2,B2]):-
    deplacement_par_bateau([M1,C1,0],M2,C2),
    passagers_bateau(M2,C2),
    deplacement_legal(M2,C2),
    B2 is 1 - B.

%traversee([M1,C1,1],[M2,C2,0]):-
%    deplacement_par_bateau([M1,C1,1],M2,C2).

%test

profondeur([[0,0,1]|L],[[0,0,1]|L]).

profondeur([X|L1],Sol):-
    traversee(X,Y),
    \+member(Y,L1),
    profondeur([Y|[X|L1]],Sol).

%test

cm(L):-
    profondeur(X,Sol),
    reverse(Sol,L).
