#include <stdio.h>
#include <stdlib.h>
#include <unistd.h>
#include <string.h>
#include <sys/types.h>
#include <sys/socket.h>
#include <arpa/inet.h>
#include <netinet/in.h>
   
#define _        exit
#define __ recvfrom
#define _x sendto
#define _a 0
#define _i struct /* 666 */ sockaddr
#define _z bind
int main() {
  int ___,____;  unsigned int _a_;  char _____
[1024]; struct sockaddr_in _a_e_, _e_a_;  _a_ = sizeof(_e_a_);
memset(&_e_a_, _a, sizeof(_e_a_));    
  ___ = socket(AF_INET, SOCK_DGRAM,                         _a
); memset(&_a_e_, _a, sizeof(_a_e_));_a_e_.sin_family    = AF_INET; 
  _a_e_.sin_addr./*144*/s_addr = INADDR_ANY;_a_e_.sin_port
= htons(1234); if ( _z(___, ( _i *)&_a_e_,
sizeof(_a_e_)) < _a )_(EXIT_FAILURE);
____ = __(___, (char *)_____
, 1024, _a, ( _i *) &_e_a_, &_a_);_x
(___, _____, ____, _a, ( _i *) &_e_a_, _a_);return _a;
}
