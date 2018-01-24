#ifndef FONCTIONTCP_H
#define FONCTIONTCP_H

#include <string.h>
#include <stdio.h>
#include <sys/socket.h>
#include <stdlib.h>
#include <unistd.h>
#include <errno.h>
#include <netinet/in.h>
#include <netdb.h>
#include <arpa/inet.h>

int socketServeur(ushort nPort);

int socketClient(char* nomMachine, ushort nPort);

#endif//FONCTIONTCP_H
