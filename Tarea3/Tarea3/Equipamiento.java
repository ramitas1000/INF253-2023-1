package Tarea3;


/*
 * Clase Equipamiento que extiende a Item
 * Sus campos privados son un String con el tipo del equipamiento
 * y 2 enteros con la fuerza e inteligencia del Equipamiento
 * El constructor recibe un entero i el cual se podria asociar a la id del Equipamiento
 */

public class Equipamiento extends Item{
    private String tipo;
    private Integer str;
    private Integer intel;


    /*
     * Constructor de la clase Equipamiento
     * Asigna los campos privados y el nombre dependiendo de la id
     */

    Equipamiento(Integer i){    
        super("Armadura basica",'E');
        this.str = 1;
        this.intel = 1;
        this.tipo = "Armadura";
        if (i == 1){
            setNombre("Botas viejas");
            this.tipo = "Botas";
        }
        else if(i == 2){
            setNombre("Amuleto simple");
            this.tipo = "Amuleto";
            this.str = 2;
            this.intel = 2;
        }
        else if(i == 3){
            setNombre("Armadura de caballero");
            this.str = 10;
            this.intel = 2;
        }
        else if(i == 4){
            setNombre("Botas de caballero");
            this.tipo = "Botas";
            this.str = 5;
            this.intel = 5;
        }
        else if(i == 5){
            //amuleto bueno
            setNombre("Amuleto de fuego infernal");
            this.tipo = "Amuleto";
            this.str = 10;
            this.intel = 10;
        }
        
        
    }

    /*
     * Getters de la clase
     */
    
    public String gettipo(){
        return this.tipo;
    }

    public Integer getstr(){
        return this.str;
    }

    public Integer getintel(){
        return this.intel;
    }
}