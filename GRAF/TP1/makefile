CC=gcc
CFLAGS=-g -Wall
SRC=$(wildcard *.c)
OBJ=$(SRC:.c=.o)
EXEC=exec_$(shell basename $(CURDIR))

all: $(EXEC)

$(EXEC): $(OBJ)
	$(CC) -o $@ $(OBJ) $(CFLAGS)

%.o: %.c %.h
	$(CC) -o $@ $(CFLAGS) -c $<

clean:
	rm *.o; rm $(EXEC)
