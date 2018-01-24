/*
 **********************************************************
 *
 *  Programme : client.c
 *
 *  resume :    envoi une chaine de caracteres a un programme serveur
 *              dont le nom/IP de machine et le numero de port sont
 *              donnes comme argument en ligne de commandes
 *  remarque : deux solutions pour la connexion vers le serveur
 *             - inet_aton (uniquement IPv4 et @serveur = @IP)
 *             - getaddrinfo (IPv4/IPv6 et @serveur = @IP/nom machine)
 *
 *  date :      8 / 01 / 18
 *
 ***********************************************************
 */

#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <unistd.h>
#include <sys/types.h>
#include <sys/socket.h>
#include <netinet/in.h>
#include <arpa/inet.h>
#include <netdb.h>
#include <errno.h>

#include "fonctionTCP.h"

#define TAIL_BUF 20
int main(int argc, char **argv) {

  char chaine[TAIL_BUF];   /* buffer */
  int sock,                /* descripteur de la socket locale */
      port,                /* variables de lecture */
      err;                 /* code d'erreur */
  char* nomMachServ;       /* pour solution getaddrinfo */
  struct addrinfo hints;   /* parametre pour getaddrinfo */
  struct addrinfo *result; /* les adresses obtenues par getaddrinfo */

  /* verification des arguments */
  if (argc != 3) {
    printf("usage : %s nom/IPServ port\n", argv[0]);
    return -1;
  }

  nomMachServ = argv[1];
  port = atoi(argv[2]);

  /*
   * creation d'une socket, domaine AF_INET, protocole TCP
   */
  sock = socketClient(nomMachServ, port);
  if(sock < 0){
    //perror("test");
    return sock;
  }
  /*
   *  initialisation de l'adresse de la socket - version getaddrinfo
   */
  /*
  memset(&hints, 0, sizeof(struct addrinfo));
  hints.ai_family = AF_INET; // AF_INET / AF_INET6
  hints.ai_socktype = SOCK_STREAM;
  hints.ai_flags = 0;
  hints.ai_protocol = 0;


  // récupération de la liste des adresses corespondante au serveur

  err = getaddrinfo(nomMachServ, argv[2], &hints, &result);
  if (err != 0) {
    perror("(client) erreur sur getaddrinfo");
    close(sock);
    return -3;
  }

  addSockServ = *(struct sockaddr_in*) result->ai_addr;
  sizeAdd = result->ai_addrlen;
  */

  /*
   * saisie de la chaine
   */
  printf("(client) donner une chaine : ");
  scanf("%s", chaine);
  printf("(client) envoi de - %s - \n", chaine);

  /*
   * envoi de la chaine
   */
  err = send(sock, chaine, strlen(chaine) + 1, 0);
  if (err <= 0) { // if (err != strlen(chaine)+1) {
    perror("(client) erreur sur le send");
    shutdown(sock, SHUT_RDWR); close(sock);
    return -5;
  }
  printf("(client) envoi de %s realise\n", chaine);

  /*
   * fermeture de la connexion et de la socket
   */
  shutdown(sock, SHUT_RDWR);
  close(sock);

  return 0;
}


