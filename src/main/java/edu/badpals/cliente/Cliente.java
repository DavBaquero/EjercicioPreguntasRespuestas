package edu.badpals.cliente;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class Cliente {
    public static void main(String[] args) throws IOException {
        Socket cliente = new Socket("localhost", 6000); // Creamos la clase Socket para que comunique con el servidor

        PrintWriter salida = new PrintWriter(cliente.getOutputStream(), true); // CREO FLUJO DE SALIDA AL SERVIDOR

        BufferedReader entrada = new BufferedReader(new InputStreamReader(cliente.getInputStream())); // CREO FLUJO DE ENTRADA AL SERVIDOR

        BufferedReader in = new BufferedReader(new InputStreamReader(System.in)); // FLUJO PARA  MI ENTRADA ESTANDAR
        String pregunta, respuesta = "";

        while (!respuesta.equals("SALIR")) {
            System.out.println("PREGUNTAME ALGO (Para finalizar escribe SALIR):");

            pregunta = in.readLine();
            salida.println(pregunta); // leo la pregunta y se la mando al servidor

            respuesta = entrada.readLine(); // INTENTO LEER LA RESPUESTA DEL SERVIDOR
            if (respuesta != null) {
                System.out.println(respuesta); // ENSEÑO LA RESPUESTA
            }
        }
        salida.close(); // CIERRA LOS FLUJOS
        entrada.close();
        in.close();
        cliente.close();
        System.out.println("Fin del proceso con el servidor... ");
    }
}
