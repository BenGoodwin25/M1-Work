% Goodwin Benjamin
% TP3
% 3 cannibals and 3 missionary
% J'ai 2 doublons, mais je ne sais pas pourquoi ...

passagers_bateau(1,0).
passagers_bateau(2,0).
passagers_bateau(1,1).
passagers_bateau(0,1).
passagers_bateau(0,2).

%test
:-begin_tests(chp1).
test('passagers_bateau(1,-1)',[fail]):-
    passagers_bateau(1,-1).
test('passagers_bateau(3,0)',[fail]):-
    passagers_bateau(3,0).
:-end_tests(chp1).
%end_tests

berge([_M,_C,_B]).
berge([3,3,0]).
berge([0,0,1]).

deplacement_par_bateau([M, C, 0], MR, CR):-
    passagers_bateau(X,Y),
    MR is M - X,
    CR is C - Y.

deplacement_par_bateau([M, C, 1], MR, CR):-
    passagers_bateau(X,Y),
    MR is M - X,
    CR is C - Y.

%Impossible test
%:-begin_tests(chp2).
%test('passagers_bateau([3,3,0],MR,CR)',[true(MR==1), true(CR==1)]):-
%    passagers_bateau([3,3,0],MR,CR).
%:-end_tests(chp2).
%:-begin_tests(chp2).
%test('passagers_bateau([1,1,1],MR,CR)',[true(MR==1), true(CR==1)]):-
%    passagers_bateau([1,1,1],MR,CR).
%:-end_tests(chp2).
%end_tests

deplacement_legal(M, C):-
    M >= 0,
    C >= 0,
    M =< 3,
    C =< 3.

%test
:-begin_tests(chp3).
test('deplacement_legal(-1,0)',[fail]):-
    deplacement_legal(-1,0).
test('deplacement_legal(0,4)',[fail]):-
    deplacement_legal(0,-1).
test('deplacement_legal(3,0)',[true]):-
    deplacement_legal(3,0).
test('deplacement_legal(777,999)',[fail]):-
    deplacement_legal(-777,999).
:-end_tests(chp3).
%end_tests

ms1(0,_).
ms1(M,C):-
    dif(M,0),
    M >= C.

missionaires_saufs(M,C):-
    ms1(M,C),
    M1 is 3-M,
    C1 is 3-C,
    ms1(M1,C1).

%test
:-begin_tests(chp4).
test('missionnaires_saufs(0,-1)',[fail]):-
    missionaires_saufs(0,-1).
test('missionnaires_saufs(4,0)',[fail]):-
    missionaires_saufs(4,0).
:-end_tests(chp4).
%end_tests

traversee([M1,C1,B],[M2,C2,B2]):-
    deplacement_par_bateau([M1,C1,B],M2,C2),
    missionaires_saufs(M2,C2),
    deplacement_legal(M2,C2),
    B2 is 1 - B.
%test
:-begin_tests(chp5).
test('traversee([3,2,0],[1,2,1])',[fail]):-
    traversee([3,2,0],[1,2,1]).
test('traversee([3,2,0],[2,1,1])',[true]):-
    traversee([3,2,0],[3,0,1]).
test('traversee([3,2,0],[2,2,1])',[fail]):-
    traversee([3,2,0],[1,2,1]).
test('traversee([3,2,0],[3,2,1])',[fail]):-
    traversee([3,2,0],[1,2,1]).
:-end_tests(chp5).
%end_tests

profondeur([[0,0,1]|L],[[0,0,1]|L]).

profondeur([X|L1],Sol):-
    traversee(X,Y),
    \+member(Y,L1),
    profondeur([Y|[X|L1]],Sol).

cm(Sol):-
    profondeur([[3,3,0]],Los),
    reverse(Los,Sol).
