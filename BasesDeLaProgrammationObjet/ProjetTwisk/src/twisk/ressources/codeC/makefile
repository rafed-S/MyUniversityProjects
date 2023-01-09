main.o : main.c def.h libTwisk.so
	gcc -Wall -L. main.c -o main -lTwisk

libTwisk.so : client.o programmeC.o
	gcc -shared programmeC.o client.o -o libTwisk.so

client.o : client.c def.h
	gcc -o client.o -c client.c -Wall -O

clean :
	rm -f *.o core
mrproper : clean
	rm -f mon_executable
