% Goodwin Benjamin
% TP4
% Pion

%ote(E,L,L1).
ote(E,[E|L1],L1).
ote(E,[X|L],L1):-
    ote(E,L,L2),
    L1=[X|L2].

ote2(E,[X|L],[X|L2]):-
    ote2(E,L,L2).
%









depl_n_gauche([_,_,_,v,n,_,_]):-
    [_,_,_,n,v,_,_].

depl_b_droite([_,_,b,v,_,_,_]):-
    [_,_,v,b,_,_,_].

saut_n_droite([_,n,b,v,_,_,_]):-
    [_,b,v,n,_,_,_].

saut_b_gauche([_,_,_,v,n,b,_]):-
    [_,_,_,b,v,n,_].


%2

%test
%
%
%endtest



%BBBN NN
%BBN BNN
%BBNN BN
%BBN NBN
%BN BNBN
%BNN BBN
%BN NBBN
%N BNBBN
%NN BBBN
%NNB BBN
%NNBB BN
%NNBBN B
%NNBN BB
%NNN BBB
