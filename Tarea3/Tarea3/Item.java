package Tarea3;


/*
 * Clase abstracta Item que implementa a la interfaz Visible
 * (No tiene un metodo abstracto asi que es como medio inutil definirlo asi)
 * Sus campos privados la representacion del Item y su nombre 
 * El constructor recibe el nombre y la representacion del item
 */
public abstract class Item implements Visible{
    private char representacion;
    private String nombre;

    /*
     * Constructor de la clase Item
     * Asigna los campos privados
     */

    Item(String nombre, char representacion){
        this.nombre = nombre;
        this.representacion = representacion;

    }

    /*
     * Getters de la clase
     */

    public char getRepresentacion(){
        return this.representacion;
    }

    public String getnombre(){
        return this.nombre;
    }

    /*
     * Setters para el nombre de la clase
     */

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
}

