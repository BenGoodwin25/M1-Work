#include "client.h"

int main(int argc, char* argv[]) {

  if (argc < 3) {
    printf(" please use %s ipServer portServer\n", argv[0]);
    return(EXIT_FAILURE);
  }

  int sockTransClient;
  size_t sizeAddr;
  ssize_t err;

  TRequeteOp req;
  TResponse response;

  isNumeric(argv[2]);
  int distantPort = atoi(argv[2]);
  isUsableNetworkPort(distantPort);

  char *ipAddress = argv[1];
  isValidIpAddress(ipAddress);

  sockTransClient = socketClient(ipAddress,distantPort);
  if(sockTransClient < 0){
    return sockTransClient;
  }
  int i = 0;
  while(i < 5){
    req.codeReq = OP_ADD + i;
    req.op1 = 3;
    req.op2 = 3;
    //test
    switch (req.codeReq) {
      case OP_ADD:
        printf("Envoi de l'opération %d + %d\n", req.op1, req.op2);
        break;
      case OP_SUB:
        printf("Envoi de l'opération %d - %d\n", req.op1, req.op2);
        break;
      case OP_MULT:
        printf("Envoi de l'opération %d * %d\n", req.op1, req.op2);
        break;
      case OP_DIV:
        printf("Envoi de l'opération %d / %d\n", req.op1, req.op2);
        break;
      case OP_END:
        printf("Want to end it\n");
      default:assert(true);
    }
    //endtest
    err = doRequest(req, sockTransClient, response);
    if(err == 1){
      shutdown(sockTransClient, SHUT_RDWR);
      close(sockTransClient);
      printf("Disconnected\n");
      return 0;
    }
    if(err < 0) {
      perror("(clientTCP) erreur dans la reception");
      shutdown(sockTransClient, SHUT_RDWR);
      close(sockTransClient);
      return -6;
    }
    i++;
  }
  shutdown(sockTransClient,SHUT_RDWR);
  close(sockTransClient);
}

ssize_t doRequest(TRequeteOp req, int sockTransClient, TResponse response){
  ssize_t err;
  send(sockTransClient,&req, sizeof(TRequeteOp),0);
  if (err < 0) {
    perror("(serverTCP) error on the send");
    shutdown(sockTransClient, SHUT_RDWR);
    close(sockTransClient);
    return -5;
  }

  recv(sockTransClient,&response,sizeof(TResponse),0);
  if (err < 0) {
    return err;
  }
  switch (req.codeReq) {
    case OP_ADD:
      if (response.codeErr == RES_OK) {
        printf("Le resultat de l'opération %d + %d est %lf\n", req.op1, req.op2, response.res);
      }
      break;
    case OP_SUB:
      if (response.codeErr == RES_OK) {
        printf("Le resultat de l'opération %d - %d est %lf\n", req.op1, req.op2, response.res);
      }
      break;
    case OP_MULT:
      if (response.codeErr == RES_OK) {
        printf("Le resultat de l'opération %d * %d est %lf\n", req.op1, req.op2, response.res);
      }
      break;
    case OP_DIV:
      if (response.codeErr == RES_OK) {
        printf("Le resultat de l'opération %d / %d est %lf\n", req.op1, req.op2, response.res);
      }
      break;
    case OP_END:
      printf("Closing up\n");
      return 1;
    default:assert(true);

  }
  return 0;

}

void isNumeric(char *str) {
  int i=0;
  while(str[i]!='\0') {
    assert(isdigit(str[i]));
    i++;
  }
}

void isUsableNetworkPort(int port){
  assert(port >= 2000);
  assert(port <= 65535);
}

void isValidIpAddress(char *ipAddressString) {
  struct sockaddr_in sa;
  int correctIpAddress = 0;
  int ipAddress = inet_pton(AF_INET, ipAddressString, &(sa.sin_addr));
  assert(ipAddress != correctIpAddress);
}
