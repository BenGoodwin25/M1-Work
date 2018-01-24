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
#include <sys/types.h>

/* inclusions socket */
#include <sys/socket.h>
#include <netdb.h>
#include <arpa/inet.h>

#include "fonctionUDP.h"

#include <unistd.h>

#define TAIL_BUF 100
#define T_NOM 20

int main(int argc, char** argv) {
  int sock,               /* descripteur de la socket locale */
      port,               /* variables de lecture */
      sizeAddr, 	  /* taille de l'adresse d'une socket */
      err;                /* code d'erreur */
  char chaine[TAIL_BUF];
  
  char ipMachDest[T_NOM];   /* ip de la machine dest */
  
  struct sockaddr_in adrDest;     /* adresse de la socket distante */
  struct sockaddr_in adrLocal;	  /* adresse de la socket locale */

  
  if (argc != 2) {
    printf("usage : %s port\n", argv[0]);
    return -1;
  }

  sock = socketUDP(argv[1]);

  /*

  /* creation de la socket, protocole UDP
  sock = socket(AF_INET, SOCK_DGRAM, 0);
  if (sock < 0) {
    perror("(emetteur) erreur de socket");
    return -2;
  }



  /*
   * initialisation de l'adresse de la socket 
   *
  adrLocal.sin_family = AF_INET;
  adrLocal.sin_port = htons(atoi(argv[1]));
  adrLocal.sin_addr.s_addr = INADDR_ANY;
  	// INADDR_ANY : 0.0.0.0 (IPv4) donc htonl inutile ici, car pas d'effet
  bzero(adrLocal.sin_zero, 8);
 
  sizeAddr = sizeof(struct sockaddr_in);
 
  /* 
   * attribution de l'adresse a la socket
   *
  err = bind(sock, (struct sockaddr *)&adrLocal, sizeAddr);
  if (err < 0) {
    perror("(emetteur) erreur sur le bind");
    return -3;
  }
  */

  /*
   * saisie et initialisation de l'adresse du destinataire
   */
  printf("(emetteur) donner la machine dest (@ip en notation decimale a point) : ");
  scanf("%s", ipMachDest);
  printf("(emetteur) donner le port dest : ");
  scanf("%d", &port);
  printf("(emetteur) initaddr pour %s, %d\n",  ipMachDest, port);
  
  /* initialisation de l'adresse de la socket */

  adrDest.sin_family = AF_INET;
  err = inet_aton(ipMachDest, &adrDest.sin_addr);
  if (err == 0) { 
    perror("(emetteur) erreur obtention IP recepteur");
    return -4;
  }
  adrDest.sin_port = htons(port);
  bzero(adrDest.sin_zero, 8);
  
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
 

