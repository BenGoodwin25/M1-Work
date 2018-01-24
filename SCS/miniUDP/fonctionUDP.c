#include "fonctionUDP.h"

#define TAIL_BUF 100
#define T_NOM 20

int socketUDP(ushort nPort){
    int sock,               /* descripteur de la socket locale */
            port,               /* variables de lecture */
            sizeAddr, 	  /* taille de l'adresse d'une socket */
            err;                /* code d'erreur */
    char chaine[TAIL_BUF];

    char ipMachDest[T_NOM];   /* ip de la machine dest */

    struct sockaddr_in adrDest;     /* adresse de la socket distante */
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
    adrLocal.sin_port = htons(atoi(nPort));
    adrLocal.sin_addr.s_addr = INADDR_ANY;
    // INADDR_ANY : 0.0.0.0 (IPv4) donc htonl inutile ici, car pas d'effet
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

int adresseUDP(char* nomMachine, ushort nPort, struct sockaddrin* addr){

}