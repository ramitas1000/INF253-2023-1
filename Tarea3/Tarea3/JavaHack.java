package Tarea3;

import java.util.Scanner;

/*
 * Clase JavaHack
 * Contiene el metodo main
 */

public class JavaHack {
    public static void main(String[] argv){
        System.out.println("----JavaHack----");
        
        //Scanner para recibir inputs por consola
        Scanner input = new Scanner(System.in);

        //Se pide el nombre de el personaje
        System.out.println("Ingrese el nombre de su personaje");
        String nombre = input.nextLine();
        Integer ancho,alto;

        //Ancho y alto del mundo
        System.out.println("Ingrese ancho del mundo");
        ancho = input.nextInt();        
        System.out.println("Ingrese alto del mundo");
        alto = input.nextInt();

        System.out.println("");

        //Crea un Jugaor con el nombre dado y el mundo con las dimensiones especificadas
        Jugador j = new Jugador(nombre);
        Mundo m = new Mundo(ancho, alto, j);

        //Imprime un breve tutorial del juego
        System.out.println("Presiona 1 para Mover");
        System.out.println("Presiona 2 para ver stats");
        System.out.println("Presiona 3 para ver inventario");
        System.out.println("Presiona 4 para equipar Items");
        System.out.println("Presiona 5 para tutorial");
        System.out.println("Presiona 6 para terminar el juego\n");

        //Variables para los inputs del usuario
        Integer comando;
        Integer mov_comando;
        Integer indice;
        Arma arma;
        Equipamiento equip;

        //Ciclo Principal del juego
        //En cada iteracion muestra el mapa por pantalla y verifica que el jugador este vivo
        //Si el jugador esta vivo, pide un input por consola el cual debe ser alguno de los mostrados por consola 
        //en el tutorial
        
        while(true){
            m.mostrar();
            if(j.gethp() <= 0){
                System.out.println("----HAS MUERTO----");
                break;
            }
            System.out.println("\n----¿Que deseas hacer?----");
            comando = input.nextInt();
            if(comando == 1){
                System.out.println("----¿En que direccion?----");
                mov_comando = input.nextInt();
                m.mover(j, mov_comando);
            }
            else if(comando == 2){
                j.mostrar_stats();
            }
            else if(comando == 3){
                j.mostrar_inventario();
            }
            else if(comando == 5){  //El tutorial lo escribi antes de equipar items
                System.out.println("Presiona 1 para Mover");
                System.out.println("Presiona 2 para ver stats");
                System.out.println("Presiona 3 para ver inventario");
                System.out.println("Presiona 4 para equipar Items");
                System.out.println("Presiona 5 para tutorial");
                System.out.println("Presiona 6 para terminar el juego\n");
                System.out.println("Comados para mover:");
                System.out.println("1 = arriba | 2 = abajo | 3 = izguierda | 4 = derecha\n");
            }
            else if(comando == 4){
                j.mostrar_inventario();
                System.out.println("¿Que item deseas equipar?");
                indice = input.nextInt();
                if (j.getInventario().size() > 0 && indice < j.getInventario().size()){
                    if (j.getInventario().get(indice).getRepresentacion() == 'A'){
                        arma = (Arma) j.getInventario().get(indice);
                        j.equipar(arma,indice);
                    }
                    else if (j.getInventario().get(indice).getRepresentacion() == 'E'){
                        equip = (Equipamiento) j.getInventario().get(indice);
                        j.equipar(equip, indice);
                    }
                    else{
                        System.out.println("----Error al equipar Item----\n");
                    }                    
                }
                else{
                    System.out.println("----Inventario vacio----\n");
                }
                
                
            }
            else if(comando == 6){//Fin del juego
                System.out.println("----GG----");
                break;
            }
            else{//Error de input
                System.out.println("----Comando incorrecto----");
            }
            if (m.getEnemigos() == 0){//Si no hay enemigos se crea un nuevo nivel
                m.nuevoNivel(j);
                System.out.println("----Subes de nivel----");
                System.out.println("----Generando nuevo mapa----");
            }
        }       



        input.close(); //Se cierra el Scanner
    }


}
