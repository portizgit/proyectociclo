/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package server;

/**
 *
 * @author pablo
 */
import java.net.*;
import java.io.*;
import javax.swing.JOptionPane;


public class MultiServer {
    /**
     * Esta clase es la que inicia el servidor, por cada petición de socket que la llega la acepta y crea un MultiServerThread para gestionar cada hilo 
     * de forma independiente
     */
    public static int activos=0;
    public static void main(String[] args) throws IOException {
        ServerSocket serverSocket = null;
        boolean listening = true;

        try {
            serverSocket = new ServerSocket(4444);
        } catch (IOException e) {
            System.err.println("Could not listen on port: 4444.");
            System.exit(-1);
        }
        
         JOptionPane.showMessageDialog(null, "Servidor iniciado", "INFO", JOptionPane.INFORMATION_MESSAGE);
        while (listening){
	    new MultiServerThread(serverSocket.accept()).start();
            activos++;
            System.out.println("Activos: "+activos);
        }
        serverSocket.close();
    }
    
    public static void inactivo(){
        activos--;
        System.out.println("Activos: "+activos);
    }
}