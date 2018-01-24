/*
 **********************************************************
 *
 *  Programme : recepteur.c
 *
 *  resume :    recoit une chaine de caracteres du programme emetteur
 *              en mode non connecte
 *
 *  date :      08 / 01 / 18
 *
 ***********************************************************
 */

/* include standards */
#include <string.h>
#include <stdio.h>
#include <stdlib.h>
#include <errno.h>

/* include socket */
#include <sys/socket.h>
#include <netinet/in.h>

#include <unistd.h>

/* taille du buffeur de reception */
#define TAIL_BUF 100


int main(int argc, char** argv) {
    int             sock,	        /* descripteur de socket locale */
                    err;	        /* code d'erreur */

    struct sockaddr_in adrRecep,	/* adresse de la socket */
                       adrEmet;         /* adresse emetteur */

    char buffer[TAIL_BUF];       	/* buffer de reception */
                  
    int sizeAddr;	                /* taille de l'adresse d'une socket */

   
    if (argc != 2) {
      printf("usage : %s port\n", argv[0]);
      return -1;
    }

   /* creation de la socket, protocole UDP */
    sock = socket(AF_INET, SOCK_DGRAM, 0);
    if (sock < 0) {
	perror("(recepteur) erreur de socket");
	return -2;
    }

    /* 
     * initialisation de l'adresse de la socket 
     */
    adrRecep.sin_family = AF_INET;
    adrRecep.sin_port = htons(atoi(argv[1]));
    adrRecep.sin_addr.s_addr = INADDR_ANY;
	// INADDR_ANY : 0.0.0.0 (IPv4) donc htonl inutile ici, car pas d'effet
    bzero(adrRecep.sin_zero, 8);

    sizeAddr = sizeof(struct sockaddr_in);

    /* 
     * attribution de l'adresse a la socket
     */
    err = bind(sock, (struct sockaddr *)&adrRecep, sizeAddr);
    if (err < 0) {
	perror("(recepteur) erreur sur le bind");
	close(sock);
	return -3;
    }

    /*
     * reception et affichage du message en provenance de l'emetteur
     */
    sizeAddr = sizeof(struct sockaddr_in);
    err = recvfrom(sock, buffer, TAIL_BUF, 0, (struct sockaddr*)&adrEmet, 
		    (socklen_t *)&sizeAddr);
    if (err <= 0) {
        perror("(recepteur) erreur dans la reception");
	close(sock);
        return -4;
    }
    printf("(recepteur) Le message recu : %s\n", buffer);

    /* fermeture de la socket */
    close(sock);

    return 0;
}
