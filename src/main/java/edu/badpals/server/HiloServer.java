package edu.badpals.server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.List;
import java.util.Random;

public class HiloServer extends Thread{
    BufferedReader entrada;
    PrintWriter salida;
    Socket socket;
    SuppBD bd;
    Random random;

    //el hilo recibe el socket conectado al cliente
    public HiloServer(Socket s) throws IOException {
        socket = s;

        // el hilo se encarga de crear flujos de entrada y salida para el socket
        salida = new PrintWriter(socket.getOutputStream(), true);
        entrada = new BufferedReader(new InputStreamReader(socket.getInputStream()));

        // creamos las instancias para manejar la bd y para tener el random
        bd = new SuppBD();
        random = new Random();
    }

    public void run() {
        String cadena = "";
        System.out.println("Comunicandose con: " + socket.toString());


        try {
            cadena = entrada.readLine();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        while (!cadena.equals("SALIR")) {
            // le paso la respuesta random a la pregunta
            List<String> respuestas = bd.obtenerRespuestas(cadena);
            String respuesta = "NO DISPONGO DE LA RESPUESTA A LA PREGUNTA";

            if (!respuestas.isEmpty()) { // si encuentra alguna respuesta cogemos una random
                respuesta = (respuestas).get(random.nextInt(respuestas.size()));
            }
            //pasamos al cliente la respuesta del servidor
            salida.println(respuesta);
            try {
                cadena = entrada.readLine();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        try {
            entrada.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        salida.close();

        try {
            socket.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }


}
