package Tarea3;


/*
 * Clase Arma que extiende a Item
 * Sus campos privados son los multiplicadores de fuerza (mul_str) e inteligencia (mul_int)
 * El constructor recibe un entero i el cual se podria asociar a la id del arma
 */

public class Arma extends Item{
    private Float mul_str;
    private Float mul_int;
    

    /*
     * Constructor de la clase Arma
     * Asigna los multiplicadores y nombre dependiendo de la id i
     */

    Arma(Integer i){
            super("Espada basica",'A');
            
            if(i == 0){
                this.mul_str = 0.5f;
                this.mul_int = 0.25f;
            }
            else if(i == 1){
                setNombre("Varita basica");
                this.mul_str = 0.1f;
                this.mul_int = 1.0f;
            }
            else if(i == 2){//ojala a nadie se le ocurra este nombre
                setNombre("Espada de las mil verdades");
                this.mul_str = 5.0f;
                this.mul_int = 1.0f;
            }
            else if(i == 3){//lo mismo aca
                setNombre("Vara de sabio de cristal");
                this.mul_str = 1.0f;
                this.mul_int = 5.0f;
            }
        }
        

    /*
     * Metodo calcularAtaque que recibe 2 enteros que representan la fuerza e inteligencia de el personaje
     * Retorna un Float con el daño que tendria un arma epecifica con las estadisticas del jugador
     */

    public Float calcularAtaque(Integer str, Integer intel){
        return str*this.mul_str + intel*this.mul_int;
    }
    
    
}