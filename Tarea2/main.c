#include <stdlib.h>
#include <stdio.h>
#include <string.h>

#include "arbol.h"

int main() {

    //Se crea un nodo de tipo directorio con crear_nodo()
    Nodo * raiz = crear_nodo(NULL,"directorio", "/");
      
    //Variables para entradas por consola
    char comando[10];
    char nombre[128];
    char contenido[256];    


    //Ciclo principal del programa para leer los comandos por consola,
    //para terminarlo, hay que usar el comando break
    while(1){
        scanf("%s", comando);

        //comando = break
        //Solo termina el ciclo, sin liberar memoria
        if(strcmp(comando,"break") == 0){
            printf("Fin del programa\n");
            break;
        }

        //comando = touch
        else if (strcmp(comando,"touch") == 0){
                scanf("%s", nombre);
                touch(raiz, nombre);
        }
        

        //comando = ls
        else if (strcmp(comando,"ls") == 0){
            ls(raiz);
        }


        //comando = write
        else if (strcmp(comando,"write") == 0){
            scanf("%s", nombre);
            fgets(contenido,256,stdin); //Con esto el contenido puede incluir espacios
            write(raiz,nombre,contenido);
        }

        //comando = cat
        else if (strcmp(comando,"cat") == 0){
            scanf("%s", nombre);
            cat(raiz,nombre);
        }

        //comando = mkdir
        else if (strcmp(comando,"mkdir") == 0){
            scanf("%s", nombre);
            mkdir(raiz, nombre);
        }

        //error al escribir comando
        //Su utilidad es para saber hay un error al tipear
        else { 
          printf("comando incorrecto\n");
        }
    }
    
    return 0;
}
