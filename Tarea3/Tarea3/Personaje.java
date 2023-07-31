package Tarea3;

/*
 * Clase Personaje que implementa Visible
 * Sus campos son los enteros de hp y nivel
 * El constructor no recibe nada y "setea" 
 */

public class Personaje implements Visible{
    private Integer hp;
    private Integer nivel;

    /*
     * Constructor de la clase
     */

    Personaje(){
        this.hp = 100;
        this.nivel = 1;
    }

    /*
     * Metodo recibirDanio
     * Recibe un entero y luego se lo resta a la hp actual del Personaje
     */    

    public void recibirDanio(Integer dmg){
        this.hp = this.hp - dmg;
    }

    /*
     * Metodo calcularAtaque
     * Retorna un entero con el danio base del Personaje
     */

    public Integer calcularAtaque(){
        return nivel*5; //cambie esto de 3 a 10 y luego a 5
    }

    /*
     * Metodo subirNivel
     * Aumenta el nivel del Personaje en 1
     */

    public void subirNivel(){
        nivel += 1;
    }

    /*
     * Getters de la clase
     */

    public char getRepresentacion(){
        return 'O';
    }

    public Integer gethp(){
        return this.hp;
    }

    public Integer getNivel() {
        return nivel;
    }

    /*
     * Setters de la clase
     */

    public void setHp(Integer hp) {
        this.hp = hp;
    }
}