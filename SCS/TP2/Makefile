 #########
 #
 # Makefile for Token Ring
 #
 #########
 
all: ring ringUDP

ring: ring.c fonctionTCP
	gcc -Wall -Wextra ring.c -o ring fonctionTCP.o

ringUDP: ringUDP.c fonctionUDP
    gcc -Wall -Wextra ringUDP.c -o ring fonctionUDP.o

fonctionTCP:
	cp ../miniTCP/fonctionTCP.o .

fonctionUDP:
	cp ../miniUDP/fonctionUDP.o .

clean:
	rm *~ ; rm -i \#* ; rm *.o; rm ring;
