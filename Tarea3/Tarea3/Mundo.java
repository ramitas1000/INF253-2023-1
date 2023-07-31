package Tarea3;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/*
 * Clase Mundo
 * Contiene enteros con el nivel, alto, ancho, enemigos del mundo y posicion en los ejes de el jugador
 * Tambien Contiene una matriz para representar el mapa y un Random r para generar numeros aleatorios
 */

public class Mundo{
    private Integer nivel;
    private Integer alto;
    private Integer ancho;
    private Integer enemigos;
    private List < List < Visible > > mapa;
    private List < Visible > columna;
    private Random r;
    private Integer pos_jugador_i;
    private Integer pos_jugador_j;

    /*
     * Constructor de la clase
     * Recibe el ancho y el alto del mundo y un objeto Jugador
     * Asigna uno los valores de ancho, alto y el nivel del mundo,
     * crea un nuevo Random y llama al metodo crearMapa
     */

    Mundo(Integer ancho, Integer alto, Jugador player){

        this.nivel = 1;
        this.ancho = ancho;
        this.alto = alto;
        this.r = new Random();
        crearMapa(player);
    }

    /*
     * Metodo privado crearMapa
     * Recibe un objeto Jugador
     * Crea el mapa como un ArrayList de ArrayList de Visible
     * Luego asigna algun objeto Visible a cada casilla del mapa dependiendo del valor de r
     * El jugador comienza en la posicion 0,0
     */

    private void crearMapa(Jugador player){

        Arma arma;
        Equipamiento equip;
        Enemigo enemy;
        Vacio nada;
        Float f;
    
        this.enemigos = 0;

        mapa = new ArrayList<List< Visible >>(); //Matriz de ancho por alto
        for (int i = 0; i < this.alto;i++){
            columna = new ArrayList< Visible >(); //Columna de la matriz

            for (int j = 0; j < this.ancho; j++){ //Ciclo que agrega Visible a la columna
                f = r.nextFloat();
                if (i == 0 && j == 0){
                    columna.add(player);
                }
                else if(f <= Math.min(0.05+0.01*this.nivel,20.0)){
                    if (j%2 == 0){
                        arma = new Arma(0);
                        columna.add(arma);
                    }
                    else{
                        equip = new Equipamiento(0);
                        columna.add(equip);
                    }                  
                }
                else if(Math.min(0.05+0.01*this.nivel,20.0) < f && f <= Math.min(0.2+0.01*this.nivel,55)){
                    enemy = new Enemigo();
                    columna.add(enemy);
                    this.enemigos++;
                }
                else{
                    nada = new Vacio();
                    columna.add(nada);
                }
            }

            mapa.add(columna);//Agrega la columna al mapa
        }

        //Posicion del jugador
        pos_jugador_i = 0;
        pos_jugador_j = 0;
        
    }

    /*
     * Metodo mostrar
     * Imprime por pantalla el mapa
     */

    public void mostrar(){

        for (int i = 0; i < this.alto; i++){
            columna = mapa.get(i);
            for (int j = 0; j < this.ancho; j++){
                System.out.print(columna.get(j).getRepresentacion() + " ");
            }
            System.out.print("\n");
        }
    }

    /*
     * Metodo mover
     * Recive un Jugador y el movimiento que se quiere hacer
     * Mueve el Jugador a 1 posicion adyacenta a la que ya esta
     */

    public void mover(Jugador player, Integer mov){
        Visible aux;
        List< Item > inventario = player.getInventario();
        Integer i;

        //Se verifica que el movimiento no se salga del mapa

        if (mov == 1 && pos_jugador_i > 0){
            mapa.get(pos_jugador_i).set(pos_jugador_j, new Vacio());
            pos_jugador_i -= 1;
            aux = mapa.get(pos_jugador_i).get(pos_jugador_j);
        }
        else if(mov == 2 && pos_jugador_i < alto-1){
            mapa.get(pos_jugador_i).set(pos_jugador_j, new Vacio());
            pos_jugador_i +=1;
            aux = mapa.get(pos_jugador_i).get(pos_jugador_j);
        }
        else if(mov == 3 && pos_jugador_j > 0){
            mapa.get(pos_jugador_i).set(pos_jugador_j, new Vacio());
            pos_jugador_j -=1;
            aux = mapa.get(pos_jugador_i).get(pos_jugador_j);
        }
        else if(mov == 4 && pos_jugador_j < ancho-1){
            mapa.get(pos_jugador_i).set(pos_jugador_j, new Vacio());
            pos_jugador_j +=1;
            aux = mapa.get(pos_jugador_i).get(pos_jugador_j);
        }
        else{
            System.out.println("Error al mover\n");
            return;
        }

        //Luego, se verifica que hay en la nueva posicion de jugador
        //si es un Item se recoge, si es un enemigo se inflingen danio mutuamente
        //haste que uno muera y si la posicion es vacia no pasa nada extra

        if(aux.getRepresentacion() == 'E'){
            i = r.nextInt(6);
            inventario.add(new Equipamiento(i));
            mapa.get(pos_jugador_i).set(pos_jugador_j, player);
        }
        else if(aux.getRepresentacion() == 'A'){
            i = r.nextInt(4);
            inventario.add(new Arma(i));
            mapa.get(pos_jugador_i).set(pos_jugador_j, player);
        }
        else if(aux.getRepresentacion() == 'X'){
            Enemigo a = new Enemigo();
            Integer dmg_arma = Math.round(player.getArma().calcularAtaque(player.calcularStr(), player.calcularInt())); 
            while(a.gethp() > 0)
                a.recibirDanio(player.calcularAtaque() + dmg_arma);
                player.recibirDanio(a.calcularAtaque());
                if (player.gethp() < 0){
                    return;
                } 
            this.enemigos -= 1;
            player.ganarXp(10);
            mapa.get(pos_jugador_i).set(pos_jugador_j, player);
        }
        else{
            mapa.get(pos_jugador_i).set(pos_jugador_j, player);
        }         
    }

    /*
     * Metodo nuevoNivel
     * Recibe un Jugador
     * Aumenta el nivel del mundo y del jugador
     * luego crea un nuevo mapa
     */

    public void nuevoNivel(Jugador player){
        this.nivel += 1;
        crearMapa(player);
        player.setHp(player.gethp() + 50 + 5*player.getNivel()); //nivel de player no de mundo
        player.subirNivel();
    }

    /*
     * Getters de la clase
     */

    public Integer getEnemigos() {
        return enemigos;
    }
}
