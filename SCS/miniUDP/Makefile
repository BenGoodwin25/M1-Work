 #########
 #
 # Makefile pour miniUDP
 #
 #########

 # For Solaris
 #LD_FLAGS = -lsocket -lnsl

 # For Linux
 LD_FLAGS =

 all: emetteur recepteur

 emetteur: emetteur.c fonctionUDP
     gcc -Wall -Wextra emetteur.c -o emetteur fonctionUDP.o $(LD_FLAGS)

 recepteur: recepteur.c fonctionUDP
     gcc -Wall -Wextra recepteur.c -o recepteur fonctionUDP.o $(LD_FLAGS)

 fonctionUDP: fonctionUDP.c fonctionUDP.h
     gcc -Wall -Wextra -c fonctionUDP.c $(LD_FLAGS)

 clean:
     rm *~ ; rm -i \#* ; rm *.o; \
         rm emetteur ; rm recepteur
 ~
