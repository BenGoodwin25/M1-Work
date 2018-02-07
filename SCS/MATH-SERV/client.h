#ifndef CLIENT_H
#define CLIENT_H

#include <assert.h>
#include <ctype.h>
#include <stdbool.h>
#include <stdlib.h>
#include <stdio.h>

#include "protocol.h"

#include "../lib/fonctionTCP.h"

void isNumeric(char *str);

void isUsableNetworkPort(int port);

void isValidIpAddress(char *ipAddressString);

ssize_t doRequest(TRequeteOp req, int sockTransClient, TResponse response);

#endif //CLIENT_H
