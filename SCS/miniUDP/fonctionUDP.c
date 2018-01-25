#include "fonctionUDP.h"

#define TAIL_BUF 100
#define T_NOM 20

int socketUDP(int nPort){
  int sock,               /* descripteur de la socket locale */
      sizeAddr,           /* taille de l'adresse d'une socket */
      err;                /* code d'erreur */

  struct sockaddr_in adrLocal;	  /* adresse de la socket locale */
  /* creation de la socket, protocole UDP */
  sock = socket(AF_INET, SOCK_DGRAM, 0);
  if (sock < 0) {
    perror("(recepteur) erreur de socket");
    return -2;
  }

  /*
  * initialisation de l'adresse de la socket
  */
  adrLocal.sin_family = AF_INET;
  adrLocal.sin_port = htons(nPort);
  adrLocal.sin_addr.s_addr = INADDR_ANY;
  bzero(adrLocal.sin_zero, 8);

  sizeAddr = sizeof(struct sockaddr_in);

  /*
  * attribution de l'adresse a la socket
  */
  err = bind(sock, (struct sockaddr *)&adrLocal, sizeAddr);
  if (err < 0) {
    perror("(emetteur) erreur sur le bind");
    return -3;
  }

  return sock;
}


int adresseUDP(char* nomMachine, ushort nPort, struct sockaddr_in* adrDest){

  int err;
  // initialisation de l'adresse de la socket

  adrDest->sin_family = AF_INET;
  err = inet_aton(nomMachine, &adrDest->sin_addr);
  if (err == 0) {
    perror("(emetteur) erreur obtention IP recepteur");
    return -4;
  }
  adrDest->sin_port = htons(nPort);
  bzero(adrDest->sin_zero, 8);
  return err;
}
// Not Working
struct sockaddr_in* initAddr(char* nomMachine, ushort nPort){

  struct sockaddr_in *adrDest;
  adrDest = (struct sockaddr_in*) malloc(sizeof(struct sockadd_in*));
  // initialisation de l'adresse de la socket

  adrDest->sin_family = AF_INET;
  inet_aton(nomMachine, &adrDest->sin_addr);
  adrDest->sin_port = htons(nPort);
  bzero(adrDest->sin_zero, 8);
  return &adrDest;
}