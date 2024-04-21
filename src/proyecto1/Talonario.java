/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package proyecto1;

import java.util.Random;

/**
 *
 * @author ja215
 */
public class Talonario {

    private int idTalonario;
    private int cantNumeros;
    private String nameOwner;
    private String description;
    private String prize;
    private int valueNumber;

    public static int randomWinner(int idTalonario, String nameOwner, java.util.List<Integer> numeros) {//usar un numeroRandom y luego buscarlo en la lista numeros y ese sera el ganador
        Random random = new Random();
        int randomNum = random.nextInt(100);
        int winner = 0;
        for (Integer num : numeros) {//mejor un foreach
            if (num == randomNum) {
                winner = num;
            }
        }
        return winner;

    }

}
