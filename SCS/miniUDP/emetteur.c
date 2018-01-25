/*
 **********************************************************
 *
 *  Programme : emetteur.c
 * 
 *  resume : envoi d'une chaine de caractère en mode non-connecté
 *           (version avec fonction inet_aton - 
 *             IPv4+@ip en décimal à point)
 *
 *  date :      08 / 01 / 18
 *
 ***********************************************************
 */

/* inclusions standards */
#include <stdio.h>
#include <stdlib.h>
#include <string.h>

/* inclusions socket */
#include <sys/socket.h>
#include <netdb.h>
#include <arpa/inet.h>

#include "fonctionUDP.h"

#define TAIL_BUF 100
#define T_NOM 20

int main(int argc, char** argv) {
  int sock,               /* descripteur de la socket locale */
      port,               /* variables de lecture */
      err;                /* code d'erreur */
  char chaine[TAIL_BUF];
  
  char ipMachDest[T_NOM];   /* ip de la machine dest */
  
  struct sockaddr_in* adrDest;     /* adresse de la socket distante */

  
  if (argc != 2) {
    printf("usage : %s port\n", argv[0]);
    return -1;
  }

  port = atoi(argv[1]);
  sock = socketUDP(port);

  // saisie et initialisation de l'adresse du destinataire
  printf("(emetteur) donner la machine dest (@ip en notation decimale a point) : ");
  scanf("%s", ipMachDest);
  printf("(emetteur) donner le port dest : ");
  scanf("%d", &port);
  printf("(emetteur) initaddr pour %s, %d\n", ipMachDest, port);

  err = adresseUDP(ipMachDest,port,&adrDest);
  //adrDest = initAddr(ipMachDest,port);
  
  /* 
   * saisie et envoi de la chaine 
   */
  printf("(emetteur) donner la chaine : ");
  scanf("%s", chaine);
  printf("(emetteur) envoi de - %s - \n", chaine);
  
  err = sendto(sock, chaine, strlen(chaine)+1, 0, 
               (struct sockaddr*)&adrDest, sizeof(struct sockaddr_in));
  if (err != strlen(chaine)+1) {
      perror("(emetteur) erreur sur sendto");
      return -5;
  }

  printf("(emetteur) chaine envoyee\n");

  /* fermeture de la socket */
  close(sock);
  
  return 0;
}
 

