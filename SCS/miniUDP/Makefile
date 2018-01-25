 #########
 #
 # Makefile pour miniUDP
 #
 #########

all: emetteur recepteur

emetteur: emetteur.c fonctionUDP
	gcc emetteur.c -o emetteur fonctionUDP.o $(LD_FLAGS)

recepteur: recepteur.c fonctionUDP
	gcc recepteur.c -o recepteur fonctionUDP.o $(LD_FLAGS)

fonctionUDP: fonctionUDP.c fonctionUDP.h
	gcc -c fonctionUDP.c $(LD_FLAGS)

clean:
	rm *~ ; rm -i ; rm *.o; rm emetteur ; rm recepteur ; rm fonctionUDP
