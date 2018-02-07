#include "ringUDP.h"

int main(int argc, char* argv[]) {
// ***************************   CHECK-PARAM   *************************** //
  if (argc < 3) {
    printf(" please use %s local-port distant-address distant-address (first)\n", argv[0]);
  }
  bool first = false;
  if (argc == 5 && strcmp(argv[4], "first") == 0) {
    first = true;
  }
  int localPort = atoi(argv[1]);
  int distantPort = atoi(argv[3]);

  size_t buffer = 0; /* buffer de reception */
  char *ipAddress = argv[2];
  int sock,
      receiver,
      sender,
      token;

  ssize_t err;

  sock = socketUDP(localPort);

  receiver = adresseUDP(ipAddress, distantPort, sock);
  sender = adresseUDP(ipAddress, localPort, sock);

  if(first){
    token = 0;
    err = sendto(sock, token, sizeof(int), 0, (struct sockaddr*)&receiver, sizeof(struct sockaddr_in));
    if (err != sizeof(int)) {
      perror("(emetteur) erreur sur sendto");
      return -5;
    }
  }
// ***************************   LOOP   *************************** //

  while(buffer < 9) {
    // ***************************   RECEPTION   *************************** //

    // reception et affichage du message en provenance du client
    err = recvfrom(sender, &buffer, sizeof(size_t), 0, (struct sockaddr*)&sender, sizeof(struct sockaddr_in));
    if (err <= 0) {
      perror("(recepteurUDP) erreur dans la reception");
      shutdown(sender, SHUT_RDWR);
      close(sender);
      return -6;
    }
    printf("(recepteurUDP) voila le message recu : %ld\n", buffer);

    // ***************************   PROCESSING   *************************** //

    buffer += 1;

    // ***************************   TRANSMISSION   *************************** //

    err = sendto(sock, buffer, sizeof(int), 0, (struct sockaddr*)&receiver, sizeof(struct sockaddr_in));
    if (err <= 0) { // if (err != strlen(chaine)+1) {
      perror("(emetteur) erreur sur le send");
      shutdown(receiver, SHUT_RDWR);
      close(receiver);
      return -5;
    }
    printf("(emetteur) envoi de %ld realise\n", buffer);

  }
}
