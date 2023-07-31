#include <stdlib.h>
#include <stdio.h>
#include <string.h>

#include "arbol.h"


/**
 * Funcion crear_lista
 * Recibe un entero con su largo maximo inicial, luego inicializa la lista 
 * retorna la lista recien inicializazda
*/
Lista* crear_lista(int largo_maximo_inicial) {
    Lista* lista = (Lista*) malloc(sizeof(Lista));  // Asigna memoria para la estructura Lista
    lista->largo_actual = 0;  // La lista inicia vacía
    lista->largo_maximo = largo_maximo_inicial;
    lista->arreglo = (Nodo*) malloc(sizeof(Nodo) * largo_maximo_inicial);  // Asigna memoria para el arreglo de nodos
    
    return lista;
}

/**
 * Funcion insertar_lista
 * Recibe una lista y un nodo
 * Agrega el nodo a la lista y si ya se alcanzo el largo_maximo duplica el tamaño de la lista
 * No retrona nada
*/
void insertar_lista(Lista* lista, Nodo* nodo) {
    if (lista->largo_actual < lista->largo_maximo) {  // Verifica si hay espacio disponible en la lista
        lista->arreglo[lista->largo_actual] = *nodo;  // Agrega el nuevo nodo al final del arreglo
        lista->largo_actual++;  // Incrementa el contador de elementos de la lista
    }
    else {
        lista = (Lista*) realloc(lista, 2*lista->largo_maximo);
        lista->largo_maximo = lista->largo_maximo*2;
        lista->arreglo[lista->largo_actual] = *nodo;
        lista->largo_actual++;
    }
}

/**
 * Funcion crear_nodo
 * Recibe el tipo y nombre de un nuevo nodo y su padre
 * Si tipo es "archivo", crea un nodo que contiene un archivo y lo retorna
 * Si tipo es "directorio", inicializa una lista para un Directorio y retorna el nuevo nodo
*/
Nodo* crear_nodo(Nodo* padre, char* tipo, char* nombre) {
    Nodo* nuevo_nodo = (Nodo*)malloc(sizeof(Nodo));
    if (nuevo_nodo == NULL) {
        return NULL; // Error: no se pudo asignar memoria para el nuevo nodo
    }
    nuevo_nodo->padre = padre;
    strcpy(nuevo_nodo->tipo, tipo);

    //crear nodo archivo
    if (strcmp(nuevo_nodo->tipo,"archivo") == 0){
        Archivo* nuevo_archivo = (Archivo*)malloc(sizeof(Archivo));
        strcpy(nuevo_archivo->nombre, nombre);
        nuevo_nodo->contenido = (Archivo*)malloc(sizeof(Archivo));
        nuevo_nodo->contenido = nuevo_archivo;
    }

    //crea nodo directorio
    else{
        Directorio* nuevo_dir = (Directorio*)malloc(sizeof(Directorio));
        strcpy(nuevo_dir->nombre, nombre);
        nuevo_dir->hijos = crear_lista(10);
        nuevo_nodo->contenido = (Directorio*)malloc(sizeof(Directorio));
        nuevo_nodo->contenido = nuevo_dir;
    }
    return nuevo_nodo;
}

/**
 * Funcion touch
 * Recibe el nombre de un archivo y un nodo (actual) y verifica que actual sea un Directorio
 * Si lo es, llama a crear_nodo ed tipo archivo y lo inserta en la lista de hijos de actual
 * Si actual no es Directorio, imprime un error por pantalla
 * No retorna nada
*/
void touch(Nodo* actual, char* nombre_archivo){
    if (strcmp(actual->tipo, "directorio") == 0){
        Directorio* dir = actual->contenido;
        //crear e insertar nodo
        Nodo* nuevo = crear_nodo(actual,"archivo", nombre_archivo);
        insertar_lista(dir->hijos, nuevo);
        //actualizar actual(directorio)
        actual->contenido = dir;
        return;
    }
    else{
        printf("El nodo actual no es Directorio");
        return;
    }   
    
}

/**
 * Funcion ls
 * Recibe un nodo y verifica que sea del tipo Directorio
 * Luego recorre los hijos (nodos) de actual e imprime su nombre
 * y su tipo (indicado con F para archivos y D para directorios)
 * No retorna nada
*/
void ls(Nodo* actual){
    if (strcmp(actual->tipo, "directorio") == 0){
        Directorio* dir = actual->contenido; // directorio a recorrer
        Directorio* subdir;
        Archivo* subarchivo;
        Nodo* nodo;
        printf("Contenido de %s:\n", dir->nombre);
        for (int i = 0; i < dir->hijos->largo_actual; i++){
            nodo = &(dir->hijos->arreglo[i]);
            if (strcmp(nodo->tipo,"archivo") == 0){
                subarchivo = nodo->contenido;
                printf("F %s\n", subarchivo->nombre);
            }
            else{
                subdir = nodo->contenido;
                printf("D %s\n", subdir->nombre);
            }

        }
    }
}

/**
 * Funcion buscar_archivo
 * Busca un nodo tipo archivo en los hijos de un directorio, dado el nombre de este nodo
 * Si lo encuentra, retorna el nodo, si no lo encuentra retorna NULL
*/
Nodo* buscar_archivo(Directorio* actual, char* nombre){
    Nodo* nodo;
    Archivo* aux;
    for (int i = 0; i < actual->hijos->largo_actual; i++){
        nodo = &(actual->hijos->arreglo[i]);
        if (strcmp(nodo->tipo, "archivo") == 0){
            aux = nodo->contenido;
            if(strcmp(aux->nombre, nombre) == 0){
                return nodo;
            }
        }
    }
    return NULL;
}

/**
 * Funcion write
 * Usa buscar_archivo para encontrar un nodo, dado el nombre de este, la info a escribir y 
 * un nodo de tipo irectorio
 * Luego escribe texto en el contenido del nodo encontrado
 * No retorna nada
*/
void write(Nodo* actual, char* nombre_archivo, char* contenido){
    if (strcmp(actual->tipo,"directorio") == 0){
        Directorio* dir = actual->contenido;
        Nodo* nodo = buscar_archivo(dir, nombre_archivo);
        if (nodo == NULL){
            //no se encontro
            printf("No se encontro el archivo\n");
            return;
            
        }
        else{
            //si se encuentra el nodo
            Archivo* aux = nodo->contenido;
            strcpy(aux->contenido, contenido);
            return;
        }
    }
    
}

/**
 * Funcion cat
 * Usa buscar_archivo para encontrar un nodo, dado el nombre de este y un nodo de tipo directorio
 * Imprime por pantalla el contenido de un archivo dado su nombre
 * No retorna nada
*/
void cat(Nodo* actual, char* nombre_archivo){
    if (strcmp(actual->tipo,"directorio") == 0){
            Directorio* dir = actual->contenido;
            Nodo* nodo = buscar_archivo(dir, nombre_archivo);
            if (nodo == NULL){
                //no se encontro
                printf("Error en cat\n");
                return;            
            }
            else{
                //se encontro
                Archivo* aux = nodo->contenido;
                printf("%s\n",aux->contenido);
            }
        }


}

/**
 * Funcion mkdir
 * Crea un nodo tipo directorio dado su nombre y un nodo actual (padre)
 * Tien un funcionamenti muy similar a touch, solo que para nodos de tipo Directorio
 * No retorna nada
*/
void mkdir(Nodo* actual, char* nombre_directorio){
    if (strcmp(actual->tipo, "directorio") == 0){
        Directorio* dir = actual->contenido;
        //crear e insertar nodo
        Nodo* nuevo = crear_nodo(actual,"directorio", nombre_directorio);
        insertar_lista(dir->hijos, nuevo);
        //actualizar actual(directorio)
        actual->contenido = dir;
        return;
    }
    else{
        printf("El nodo actual no es Directorio");
        return;
    }   
}

/**
 * Funcion buscar_directorio
 * Al igual que buscar_archivo, busca un nodo pero de tipo directorio dados su nombre y padre
 * Retorna el nodo si lo encuentra y si no, retorna NULL
*/
Nodo* buscar_directorio(Directorio* actual, char* nombre){
    Nodo* nodo;
    Directorio* dir;
    for (int i = 0; i < actual->hijos->largo_actual; i++){
        nodo = &(actual->hijos->arreglo[i]);
        if (strcmp(nodo->tipo, "directorio") == 0){
            dir = nodo->contenido;
            if(strcmp(dir->nombre, nombre) == 0){
                return nodo;
            }
        }
    }
    return NULL;

}