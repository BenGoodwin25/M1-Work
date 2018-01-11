#include <avr/io.h>
#define F_CPU 16000000UL
#include <util/delay.h>

int main(void){
  DDRB |= 1<<PORTB5;
  DDRE |= 1<<PORTB6;
  PORTB &= 1<<PORTB5;
  PORTE &= ~(1<<PORTE6);

  while(1){
    PORTB^=1<<PORTB5;
    PORTE^=1<<PORTE6;
    _delay_ms(10);
  }
  return(0);
}
