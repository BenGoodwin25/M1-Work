#########
#
# Makefile pour miniTCP
#
#########

# For Solaris
#LD_FLAGS = -lsocket -lnsl

# For Linux
LD_FLAGS =

all: clientTCP serveurTCP

clientTCP: clientTCP.c fonctionTCP
	gcc -Wall -Wextra clientTCP.c -o clientTCP fonctionTCP.o $(LD_FLAGS)

serveurTCP: serveurTCP.c fonctionTCP
	gcc -Wall -Wextra serveurTCP.c -o serveurTCP fonctionTCP.o $(LD_FLAGS)

fonctionTCP: fonctionTCP.c fonctionTCP.h
	gcc -Wall -Wextra -c fonctionTCP.c $(LD_FLAGS)

clean:
	rm *~ ; rm -i \#* ; rm *.o; \
        rm clientTCP ; rm serveurTCP
