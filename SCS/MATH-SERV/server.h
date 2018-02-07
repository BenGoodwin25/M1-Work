#ifndef SERVER_H
#define SERVER_H

#include <assert.h>
#include <ctype.h>
#include <signal.h>
#include <stdbool.h>
#include <stdlib.h>
#include <stdio.h>

#include "protocol.h"

#include "../lib/fonctionTCP.h"

void isNumeric(char *str);

void isUsableNetworkPort(int port);

void isValidIpAddress(char *ipAddress);

ssize_t oneClientLoop(int sockTransServer, TRequeteOp req);

ssize_t multiClientIterativeLoop(int sockTransServer, TRequeteOp req);

ssize_t doRequest(TRequeteOp req, TResponse *response);

#endif //SERVER_H
