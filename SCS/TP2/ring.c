#include "ring.h"

int main(int argc, char* argv[]){
// ***************************   CHECK-PARAM   *************************** //
  if (argc < 3){
      printf(" please use %s local-port distant-address distant-address (first)\n", argv[0]);
  }
  bool first = false;
  if(argc == 5 && strcmp(argv[4], "first") == 0){
    first = true;
  }

// ***************************   INIT   *************************** //

  int sockConx,
      sockTransServ,
      sockTransClient;
  size_t token;
  size_t sizeAddr;
  ssize_t err;

  int localPort = atoi(argv[1]);
  int distantPort = atoi(argv[3]);

  size_t buffer = 0; /* buffer de reception */
  char *ipAddress = argv[2];

  struct sockaddr_in addClient;


// ***************************   CONNECTION   *************************** //

  //SockConn
  sockConx = socketServeur(localPort);

  if(first){ // Use of Linux Listen Buffer
    //connect client
    sockTransClient = socketClient(ipAddress,distantPort);
    if (sockTransClient < 0) {
      perror("(serveurTCP) erreur sur accept");
      return -5;
    }
    printf("ServTransClient : %d\n",sockTransClient);

    //accept
    sizeAddr = sizeof(struct sockaddr_in);
    sockTransServ = accept(sockConx, (struct sockaddr *) &addClient, (socklen_t *)&sizeAddr);
    if (sockTransServ < 0) {
      perror("(serveurTCP) erreur sur accept");
      return -5;
    }
    printf("ServTransServ : %d\n",sockTransServ);
  } else {
    //accept
    sizeAddr = sizeof(struct sockaddr_in);
    sockTransServ = accept(sockConx, (struct sockaddr *) &addClient, (socklen_t *)&sizeAddr);
    if (sockTransServ < 0) {
      perror("(serveurTCP) erreur sur accept");
      return -5;
    }
    printf("ServTransServ : %d\n",sockTransServ);

    //connect client
    sockTransClient = socketClient(ipAddress,distantPort);
    if (sockTransClient < 0) {
      perror("(serveurTCP) erreur sur accept");
      return -5;
    }
    printf("ServTransClient : %d\n",sockTransClient);

  }
// ***************************   FIRST-TRANSMISSION   *************************** //

  if(first){
    token = 0;
    err = send(sockTransClient, &token, sizeof(size_t), 0);
    if (err <= 0) {
      perror("(client) erreur sur le send");
      shutdown(sockTransClient, SHUT_RDWR); close(sockTransClient);
      return -5;
    }
    printf("(client) envoi de %ld realise\n", token);
  }
// ***************************   LOOP   *************************** //

  while(buffer < 9) {
    // ***************************   RECEPTION   *************************** //

    // reception et affichage du message en provenance du client
    err = recv(sockTransServ, &buffer, sizeof(size_t), 0);
    if (err <= 0) {
      perror("(serveurTCP) erreur dans la reception");
      shutdown(sockTransServ, SHUT_RDWR);
      close(sockTransServ);
      return -6;
    }
    printf("(serveurTCP) voila le message recu : %ld\n", buffer);

    // ***************************   PROCESSING   *************************** //

    buffer += 1;

    // ***************************   TRANSMISSION   *************************** //

    err = send(sockTransClient, &buffer, sizeof(size_t), 0);
    if (err <= 0) { // if (err != strlen(chaine)+1) {
      perror("(client) erreur sur le send");
      shutdown(sockTransClient, SHUT_RDWR);
      close(sockTransClient);
      return -5;
    }
    printf("(client) envoi de %ld realise\n", buffer);
  }

// ***************************   CLOSE-UP   *************************** //

  shutdown(sockTransServ,SHUT_RDWR);
  close(sockTransServ);
  shutdown(sockTransClient,SHUT_RDWR);
  close(sockTransClient);

  shutdown(sockConx,SHUT_RDWR);
  close(sockConx);

  return(EXIT_SUCCESS);
}
