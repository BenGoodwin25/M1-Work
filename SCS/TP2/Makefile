 #########
 #
 # Makefile for Token Ring
 #
 #########
 
all: ring

ring: ring.c fonctionTCP
	gcc -Wall -Wextra ring.c -o ring fonctionTCP.o

fonctionTCP:
	cp ../miniTCP/fonctionTCP.o .

clean:
	rm *~ ; rm -i \#* ; rm *.o; rm ring;
