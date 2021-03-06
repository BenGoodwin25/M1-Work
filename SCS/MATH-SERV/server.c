#include "server.h"
static int sockConx;

void intHandler(int dummy) {
  printf("\nShutting down server Properly\n");
  shutdown(SHUT_RDWR,sockConx);
  close(sockConx);
  exit(EXIT_SUCCESS);
}

int main(int argc, char* argv[]) {

  if (argc < 3){
    printf(" please use %s local-port mode(0 -> MultiClient Client loop, 1 -> multiplex)\n", argv[0]);
    return(EXIT_FAILURE);
  }

  int sockTransServer;
  size_t sizeAddr;
  ssize_t err;

  isNumeric(argv[1]);
  int localPort = atoi(argv[1]);
  isUsableNetworkPort(localPort);

  int mode = atoi(argv[2]);

  struct sockaddr_in addClient;
  TRequeteOp req;
  TResponse response;

  bool closeup = false;

  signal(SIGINT, intHandler);

  printf("this is a math server the network port is : %d\n", localPort);




  // Request loop for one client
  err = 1;
  switch(mode){
    case 0:
      //Init
      //SockConn
      sockConx = socketServeur(localPort);

      //accept new client
      sizeAddr = sizeof(struct sockaddr_in);
      sockTransServer = accept(sockConx, (struct sockaddr *) &addClient, (socklen_t *) &sizeAddr);
      if (sockTransServer < 0) {
        perror("(serveurTCP) erreur sur accept");
        return -5;
      }
      printf("ServTransServ : %d\n", sockTransServer);

      //Run
      while(err >= 0) {
        err = oneClientLoop(sockTransServer, req);
        shutdown(sockTransServer,SHUT_RDWR);
        close(sockTransServer);
        sockTransServer = accept(sockConx, (struct sockaddr *) &addClient, (socklen_t *) &sizeAddr);
        if (sockTransServer < 0) {
          perror("(serveurTCP) erreur sur accept");
          return -5;
        }
        printf("Connecting client\n");
      }
    break;

    case 1:
      //SockConn
      sockConx = socketServeur(localPort);


      err = multiClientIterativeLoop(addClient, req); //Multiplexing
    break;

    /*case 2:
      err = multiClientParallelLoop(sockTransServer, req); //Fork
    break;

    case 3:
      err = multiClientParallelLoopPool(sockTransServer, req); //ForkPool
    break;*/

    default:
      assert(true);
  }
  //check err for later version

  shutdown(sockTransServer, SHUT_RDWR);
  close(sockTransServer);

  shutdown(sockConx,SHUT_RDWR);
  close(sockConx);

  //return err;
}

ssize_t oneClientLoop(int sockTransServer, TRequeteOp req){
  ssize_t err;
  bool closeup = false;
  TResponse response;
  ssize_t errLoop=0;
  while(!closeup) {
    // reception request
    err = recv(sockTransServer, &req, sizeof(TRequeteOp), 0);
    if (err < 0) {
      perror("(serveurTCP) erreur dans la reception");
      shutdown(sockTransServer, SHUT_RDWR);
      close(sockTransServer);
      return -6;
    }

    errLoop = doRequest(req,&response);

    if(errLoop == -1){
      closeup = true;
    }

    // Sending Math Answer
    if (!closeup) {
      err = send(sockTransServer, &response, sizeof(TResponse), 0);
      if (err < 0) {
        perror("(serverTCP) error on the send");
        shutdown(sockTransServer, SHUT_RDWR);
        close(sockTransServer);
        return -5;
      }
    }

  }
  return 0;
}

ssize_t multiClientIterativeLoop(struct sockaddr_in addClient, TRequeteOp req){
  fd_set readSet;

  FD_ZERO(&readSet);
  FD_SET(sockConx,&readSet);
  int nfsd = FD_SETSIZE;

  int client[MAXCLIENT];
  int nbOfClient = 0;
  int indexOfClient = 0;

  TResponse response;
  ssize_t err;

  int sizeAddr = sizeof(struct sockaddr_in);


  if(err < 0) {
    perror("(serveurSelect) erreur dans select");
    shutdown(SHUT_RDWR,sockConx);
    close(sockConx);
    return -5;
  }
  while(1){
    err = select(nfsd,&readSet,NULL,NULL,NULL);
    if(FD_ISSET(sockConx,&readSet)){
      //accept new client
      client[nbOfClient] = accept(sockConx, (struct sockaddr *) &addClient, (socklen_t *) &sizeAddr);
      if (client[nbOfClient] < 0) {
        perror("(serveurTCP) erreur sur accept");
        return -5;
      } else {
        printf("Connexion of new client\n");
        FD_SET(client[nbOfClient],&readSet);
        nbOfClient++;
      }
    }
    while(indexOfClient < nbOfClient){
      printf("While %d < %d\n",indexOfClient,nbOfClient);
      if(FD_ISSET(client[indexOfClient],&readSet)){
        err = recv(client[indexOfClient], &req, sizeof(TRequeteOp), 0);
        if (err < 0) {
          perror("(serveurTCP) erreur dans la reception");
          shutdown(client[indexOfClient], SHUT_RDWR);
          close(client[indexOfClient]);
          return -6;
        }
        printf("Received new request\n");

        doRequest(req,&response);

        // Sending Math Answer
        err = send(client[indexOfClient], &response, sizeof(TResponse), 0);
        printf("sended\n");
        if (err < 0) {
          perror("(serverTCP) error on the send");
          shutdown(client[indexOfClient], SHUT_RDWR);
          close(client[indexOfClient]);//shtdwn + close all
          memmove((void*)(&client[indexOfClient]), (void*)(&client[indexOfClient+1]), (nbOfClient-indexOfClient)*sizeof(int));
          return -5;
        }
        if(response.codeErr==OP_END){
          shutdown(client[indexOfClient], SHUT_RDWR);
          close(client[indexOfClient]);
          memmove((void*)(&client[indexOfClient]), (void*)(&client[indexOfClient+1]), (nbOfClient-indexOfClient)*sizeof(int));
          nbOfClient--;
        }
      }
      indexOfClient++;
    }
    indexOfClient = 0;
  }
}

ssize_t doRequest(TRequeteOp req, TResponse *response){
  // Processing Request
  switch (req.codeReq) {
    case OP_ADD:
      printf("(serverTCP) this is the operation to do : %i + %i\n", req.op1, req.op2);
      response->res = (req.op1 + req.op2);
      response->codeErr = RES_OK;
      break;
    case OP_SUB:
      printf("(serverTCP) this is the operation to do : %i - %i\n", req.op1, req.op2);
      response->res = (req.op1 - req.op2);
      response->codeErr = RES_OK;
      break;
    case OP_MULT:
      printf("(serverTCP) this is the operation to do : %i * %i\n", req.op1, req.op2);
      response->res = (req.op1 * req.op2);
      response->codeErr = RES_OK;
      break;
    case OP_DIV:
      printf("(serverTCP) this is the operation to do : %i / %i\n", req.op1, req.op2);
      if (req.op2 == 0) {
        printf("(ServerTCP) Division by Zero\n");
        response->res = 0;
        response->codeErr = RES_ERR_DIV0;
      } else {
        response->res = (req.op1 / req.op2);
        response->codeErr = RES_OK;
      }
      break;
    case OP_END:
      printf("Disconnecting client\n");
      return -1;
    default :
      response->codeErr = RES_ERR_UNKNOW_TYPE;
      response->res = 0;
      break;
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

void isValidIpAddress(char *ipAddress) {
  struct sockaddr_in sa;
  int result = inet_pton(AF_INET, ipAddress, &(sa.sin_addr));
  assert(result == 0);
}


