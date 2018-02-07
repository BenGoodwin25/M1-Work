#ifndef MATH_SERV_PROTOCOL_H
#define MATH_SERV_PROTOCOL_H

typedef enum {
  OP_ADD,
  OP_SUB,
  OP_MULT,
  OP_DIV,
  OP_END
} TCodeReq;

typedef enum {
  RES_OK,
  RES_ERR_DIV0,
  RES_ERR_UNKNOW_TYPE,
  RES_ERR_UNKNOW
} TCodeErr;

typedef struct {
  TCodeReq codeReq;
  int op1,op2;
} TRequeteOp;

typedef struct {
  TCodeErr codeErr;
  double res;
} TResponse;

#endif //MATH_SERV_PROTOCOL_H
