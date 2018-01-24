/*
 **********************************************************
 *
 *  Programme : serveur.c
 *
 *  resume :    recoit une chaine de caracteres du programme client
 *
 *  date :      8 / 01 / 18
 *
 ***********************************************************
 */

#include <string.h>
#include <stdio.h>
#include <stdlib.h>
#include <unistd.h>
#include <sys/socket.h>
#include <netinet/in.h>
#include <errno.h>

#include "fonctionTCP.h"

/* taille du buffer de reception */
#define TAIL_BUF 20

int main(int argc, char** argv) {
  int  sockConx,        /* descripteur socket connexion */
       sockTrans,       /* descripteur socket transmission */
       port,            /* numero de port */
       sizeAddr,        /* taille de l'adresse d'une socket */
       err;	        /* code d'erreur */

  char buffer[TAIL_BUF]; /* buffer de reception */

  //struct sockaddr_in addServ;	/* adresse socket connex serveur */
  struct sockaddr_in addClient;	/* adresse de la socket client connectee */

  /*
   * verification des arguments
   */
  if (argc != 2) {
    printf ("usage : %s port\n", argv[0]);
    return -1;
  }
  port  = atoi(argv[1]);
  sockConx = socketServeur(port);
  if(sockConx < 0){
    return sockConx;
  }
  /*
   * attente de connexion
   */
  sizeAddr = sizeof(struct sockaddr_in);
  sockTrans = accept(sockConx,
		     (struct sockaddr *)&addClient,
		     (socklen_t *)&sizeAddr);
  if (sockTrans < 0) {
    perror("(serveurTCP) erreur sur accept");
    return -5;
  }

  /*
   * reception et affichage du message en provenance du client
   */
  err = recv(sockTrans, buffer, TAIL_BUF, 0);
  if (err <= 0) {
    perror("(serveurTCP) erreur dans la reception");
    shutdown(sockTrans, SHUT_RDWR); close(sockTrans);
    return -6;
  }
  printf("(serveurTCP) voila le message recu : %s\n", buffer);


  /*
   * arret de la connexion et fermeture
   */
  shutdown(sockTrans, SHUT_RDWR); close(sockTrans);
  close(sockConx);

  return 0;
}
