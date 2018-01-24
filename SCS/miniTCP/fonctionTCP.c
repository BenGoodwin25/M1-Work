#include "fonctionTCP.h"

int socketServeur(ushort nPort){
  int sockConx,
      err,
      sizeAddr;
  struct sockaddr_in addServ;

  sockConx = socket(AF_INET, SOCK_STREAM, 0);
  if(sockConx < 0){
    perror("(serveur TCP) erreur de socket");
    return -2;
  }
  addServ.sin_family = AF_INET;
  addServ.sin_port = htons(nPort);
  addServ.sin_addr.s_addr = INADDR_ANY;
  bzero(addServ.sin_zero, 8);

  sizeAddr = sizeof(struct sockaddr_in);

  err = bind(sockConx, (struct sockaddr *) &addServ, sizeAddr);
  if(err < 0){
    perror("(serverTCP) erreur sur le bind");
    close(sockConx);
    return -3;
  }

  err = listen(sockConx, 1);
  if(err < 0){
    perror("(serverTCP) erreur dans listen");
    close(sockConx);
    return -4;
  }

  /* solution with IPv4/IPv6 and DOMAIN NAME */
  return sockConx;
}

int socketClient(char* nomMachine, ushort nPort){
  int sock,
      err;
  struct sockaddr_in addSockServ;
  socklen_t sizeAdd;
  struct addrinfo hints;
  struct addrinfo *result;

  sock = socket(AF_INET, SOCK_STREAM, 0);
  if(sock < 0){
    perror("(client) erreur sur la creation de socket");
    return -2;
  }
  addSockServ.sin_family = AF_INET;
  err = inet_aton(nomMachine, &addSockServ.sin_addr);
  if(err == 0){
    perror("(client) erreur sur l'obtention IP serveur");
    close(sock);
    return -3;
  }
  addSockServ.sin_port = htons(nPort);
  bzero(addSockServ.sin_zero, 8);

  sizeAdd = sizeof(struct sockaddr_in);
  /*    ----  HOSTNAME && IPv6
  memset(&hints, 0, sizeof(struct addrinfo));
  hints.ai_family = AF_INET; //AF_INET6
  hints.ai_socktype = SOCK_STREAM;
  hints.ai_flags = 0;
  hints.ai_protocol = 0;

  err = getaddrinfo(nomMachine, nPort, &hints, &result);
  if(err != 0){
    perror("(client) erreur sur addrinfo");
    close(sock);
    return -3;
  }

  addSockServ = *(struct sockaddr_in*) result->ai_addr;
  sizeAdd = result->ai_addrlen;
  */
  err = connect(sock, (struct sockaddr *)&addSockServ, sizeAdd);

  if(err < 0){
    perror("(client) erreur a la connection de socket");
    close(sock);
    return -4;
  }

  return sock;
}
