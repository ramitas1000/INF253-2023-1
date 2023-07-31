package Tarea3;


/*
 * Clas Enemigo que extiende a Personaje
 * Es un un Personaje pero con getRepresentacion diferente
 */

public class Enemigo extends Personaje{

    /*
     * Constructor de la clase Enemigo
     */

    Enemigo(){
        super();
    }

    /*
     * Metodo getRepresentacion
     * retorna un char 'X' que representa a un enemigo
    */

    public char getRepresentacion() {
        return 'X';
    }
}
