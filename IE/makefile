MCU=atmega32u4

#TARGET=TP0
#TARGET=TP1b
#TARGET=TP2
#TARGET=TP2b
TARGET=TP3
USB=VirtualSerial/
LUFA=lufa-LUFA-140928/
DF_USB=16000000UL

all: $(TARGET).hex

$(TARGET).hex: $(TARGET).out
	avr-objdump -dSt $(TARGET).out > $(TARGET).lst

$(TARGET).out: $(TARGET).o
	avr-gcc -L$(USB) -lVirtualSerial -mmcu=$(MCU) -o $(TARGET).out $(TARGET).o

$(TARGET).o: $(TARGET).c
	avr-gcc -Wall -Wextra -I $(USB) -I $(LUFA) -DF_USB=$(DF_USB) -mmcu=$(MCU) -std=gnu99 -Os -c $(TARGET).c
	avr-gcc -Wall -Wextra -I $(USB) -I $(LUFA) -DF_USB=$(DF_USB) -mmcu=$(MCU) -std=gnu99 -Os -S $(TARGET).c

flash: $(TARGET).out
	avrdude -c avr109 -b57600 -D -p $(MCU) -P /dev/ttyACM0 -e -U flash:w:$(TARGET).out

clean:
	rm *.o $(TARGET).out

blank: TP0.out
	avrdude -c avr109 -b57600 -D -p $(MCU) -P /dev/ttyACM0 -e -U flash:w:TP0.out
