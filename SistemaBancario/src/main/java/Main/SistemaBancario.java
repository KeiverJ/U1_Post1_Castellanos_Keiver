/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package Main;

import Modelo.Solicitante;

/**
 *
 * @author Keiver
 */
public class SistemaBancario {

    public static void main(String[] args) {
        Solicitante solicitante = new Solicitante("Keiver", 20, 700, true, 6, 100000);
        System.out.println(solicitante.toString());
    }
}
