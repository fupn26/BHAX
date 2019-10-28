férfi(nándi).
férfi(matyi).
férfi(norbi).
férfi(dodi).
férfi(joska).
nő(gréta).
nő(erika).
nő(kitti).
nő(marica).
gyereke(nándi, norbi).
gyereke(matyi, norbi).
gyereke(gréta, norbi).
gyereke(nándi, erika).
gyereke(matyi, erika).
gyereke(gréta, erika).
gyereke(norbi, dodi).
gyereke(norbi, kitti).
gyereke(erika, joska).
gyereke(erika, marica).
apa(X) :- férfi(X), gyereke(_Y, X).
apja(X, Y) :- férfi(X), gyereke(Y, X).
anya(X) :- nő(X), gyereke(_Y, X).
anyja(X, Y) :- nő(X), gyereke(Y, X).
nagyapa(X) :- apja(X, Y), (apja(Y, _U); anyja(Y, _Z)).
nagyapja(X, Z) :- apja(X, Y), (apja(Y, Z); anyja(Y, Z)).
szülő(X) :- apa(X); anya(X).
szülője(X, Y) :- apja(X, Y); anyja(X, Y).
testvér(X, Y) :- szülője(Z, X), szülője(Z, Y),
szülője(U, X), szülője(U, Y), X\=Y, U\=Z.
féltestvér(X, Y) :- szülője(Z, X), szülője(Z, Y), X\=Y,
\+ testvér(X, Y).
unokatestvér(X, Y) :- szülője(Z, X), szülője(U, Y), X\=Y,
(testvér(Z, U); féltestvér(Z, U)).
őse(X, Y) :- szülője(X, Y); (szülője(X, Z), őse(Z, Y)).