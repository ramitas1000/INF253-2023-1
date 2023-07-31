
/**
 * Estructura Nodo
 * Permite guardar Archivo o Directorio
 * Tiene un puntero a padre (Directorio), un string tipo
 * el cual determina el tipo de contenido de void* contenido;
*/
typedef struct Nodo{
    struct Nodo* padre;
    char tipo[64];
    void* contenido;    
} Nodo;


/**
 * Estructura Lista
 * Lista basada en arreglos para guardar Nodos
 * Contiene un largo_maximo inicial, largo_actual
 * y el arreglo de nodos
*/
typedef struct{
    int largo_actual;
    int largo_maximo;
    Nodo* arreglo;
} Lista;


/**
 * Estructura Directorio
 * Es el contenido de un nodo tipo Directorio
 * Tiene nombre y una Lista con hijos (mas nodos)
*/
typedef struct {
    char nombre[128];
    Lista* hijos;
} Directorio;


/**
 * Estructura Archivo
 * Contenido de un nodo tipo Archivo
 * Tiene nombre y un string texto
*/
typedef struct{
    char nombre[128];
    char contenido[256];
} Archivo;


//Las funciones estan comentadas en arbol.c

//Funciones implementadas
Lista* crear_lista(int largo_maximo_inicial);
void insertar_lista(Lista* lista, Nodo* nodo);
Nodo* buscar_directorio(Directorio* actual, char* nombre);
Nodo* buscar_archivo(Directorio* actual, char* nombre);
Nodo* crear_nodo(Nodo* padre, char* tipo, char* nombre);
void mkdir(Nodo* actual, char* nombre_directorio);
void touch(Nodo* actual, char* nombre_archivo);
void write(Nodo* actual, char* nombre_archivo, char* contenido);
void cat(Nodo* actual, char* nombre_archivo);
void ls(Nodo* actual);

//Funciones no implementadas
void ls_dir(Nodo* actual, char* nombre_directorio);
void mapdir (Nodo* actual, void(*instruccion)(Nodo*,char*), char* parametro_instruccion);

