#ifndef FONCTIONUDP_H
#define FONCTIONUDP_H


/* inclusions standards */
#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <sys/types.h>

/* inclusions socket */
#include <sys/socket.h>
#include <netdb.h>
#include <arpa/inet.h>

#include <unistd.h>

int socketUDP(ushort nPort);

int adresseUDP(char* nomMachine, ushort nPort, struct sockaddrin* addr);

struct sockaddrin* initAddr(char* nomMachine, ushort nPort);

#endif// FONCTIONUDP_H