package Tarea3;

import java.util.List;
import java.util.ArrayList;
import java.util.HashMap;

/*
 * Clase Jugador que extiende a Personaje
 * Contiene el nombre la xp el inventario, el equipamiento y el arma del Jugador
 */

public class Jugador extends Personaje{
    private String nombre;
    private Integer xp;
    private List< Item > inventario;
    private HashMap <String , Equipamiento> equipamiento;
    private Arma arma;

    /*
     * Constructor de la clase
     * Recibe el nombre del personaje e inicializa todos sus campos
     *  
     */

    Jugador(String nombre){
        super();
        this.nombre = nombre;
        this.arma = new Arma(0);
        this.xp = 0;
        this.equipamiento = new HashMap< String , Equipamiento >();
        this.inventario = new ArrayList< Item >();
        Equipamiento armadura = new Equipamiento(0);
        Equipamiento botas = new Equipamiento(1);
        Equipamiento amuleto = new Equipamiento(2);
        this.equipamiento.put(armadura.gettipo(), armadura);
        this.equipamiento.put(botas.gettipo(), botas);
        this.equipamiento.put(amuleto.gettipo(), amuleto);
    }
    
    /*
     * Metodo ganarXp
     * Recibe un entero y se lo suma a la experiencia total del pj
     */

    public void ganarXp(Integer xp){
        this.xp += xp;
    }

    /*
     * Metodo equipar
     * Cambia el arma actual del personaje por una del inventario
     * Si el inventario esta vacio este metodo no se puede llamar
     * (La verificacion se hace antes) 
     */

    public void equipar(Arma arma, int i){
        inventario.remove(i);
        this.inventario.add(this.arma);
        this.arma = arma;
    }

    /*
     * Metodo equipar
     * Cambia el equipamiento del jugador por uno del inventario
     * Si el inventario esta vacio este metodo no se puede llamar
     * (La verificacion se hace antes)
     */

    public void equipar(Equipamiento equip, int i){
        inventario.remove(i);
        inventario.add(equipamiento.get(equip.gettipo()));
        equipamiento.remove(equip.gettipo());
        equipamiento.put(equip.gettipo(), equip);
    }

    /*
     * Metodo mostrar_stats
     * Imprime por pantalla los stats del jugador
     */

    public void mostrar_stats(){
        Integer dmg_arma = Math.round(this.arma.calcularAtaque(calcularStr(), calcularInt())); 
        System.out.println("---STATS---");
        System.out.println("Nombre: " + nombre);
        System.out.println("Nivel: " + getNivel());
        System.out.println("Xp: " + xp);
        System.out.println("Hp: " + gethp());
        System.out.println("Ataque: " + calcularAtaque());
        System.out.println("Ataque del arma: " + dmg_arma);
        System.out.println("Armadura: " + equipamiento.get("Armadura").getnombre());
        System.out.println("Botas: " + equipamiento.get("Botas").getnombre());
        System.out.println("Amuleto: " + equipamiento.get("Amuleto").getnombre());
        System.out.println("Arma: " + arma.getnombre() + "\n");
    }

    /*
     * Metodo mostrar_inventario
     * Imprime por pantalla el inventario del Jugador
     */
    
    public void mostrar_inventario(){
        System.out.println("----INVENTARIO----");
        for (Integer i = 0; i < inventario.size(); i++){
            System.out.println(i + " " + inventario.get(i).getnombre());
        }
        System.out.println("");
    }

    /*
     * Metodo calcularInt
     * Calcula la inteligencia del Personaje y la retorna
     */

    public Integer calcularInt(){
        Integer intel = 0;
        if (equipamiento.size() > 0){
            intel += equipamiento.get("Armadura").getintel();
            intel += equipamiento.get("Botas").getintel();
            intel += equipamiento.get("Amuleto").getintel();
        }
        
        return intel;
    }

    /*
     * Metodo calcularStr
     * Calcula la fuerza del Personaje y la retorna
     */
    
    public Integer calcularStr(){
        Integer str = 0;
        if (equipamiento.size() > 0){            
            str += equipamiento.get("Armadura").getstr();
            str += equipamiento.get("Botas").getstr();
            str += equipamiento.get("Amuleto").getstr();
        }
        return str;
    }
    
    /*
     * Getters de la clase
     */

    public HashMap<String, Equipamiento> getEquipamiento() {
        return equipamiento;
    }

    public char getRepresentacion(){
        return 'J';
    }

    public Arma getArma() {
        return arma;
    }

    public List<Item> getInventario() {
        return inventario;
    }

}