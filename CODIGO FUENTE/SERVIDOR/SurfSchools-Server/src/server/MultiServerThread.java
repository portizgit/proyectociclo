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
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonReader;
import javax.swing.JFrame;
import javax.swing.JTextArea;
import org.json.JSONException;
import org.json.JSONObject;

public class MultiServerThread extends Thread {
    private Socket socket = null;
/**
 * Esta clase gestiona cada socket de manera individual
 * @param socket 
 */
    public MultiServerThread(Socket socket) {
	super("MultiServerThread");
	this.socket = socket;
    }

    public void run() {

	try {
            /**
             * El sistema es, recibe un JSON, se lo pasa al protocolo, el protocolo realiza lo que haga falta en esa petición, y devuelve siempre
             * un JSON de vuelta que será el que esta clase envíe a la salida del socket y que llegará al cliente.
             */
	    PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
	    BufferedReader in = new BufferedReader(
				    new InputStreamReader(
				    socket.getInputStream()));
          JFrame jFrame = new JFrame();

      
            String inputLine, outputLine;
            Protocol ptl = new Protocol();
            
            
            while ((inputLine = in.readLine()) != null) {
                  try {
                JSONObject json = new JSONObject(inputLine);
              
                    if(json.getString("peticion").equals("salir")){
                        break;
                        
                    }
             
                System.out.println("DESDE CLIENTE"+inputLine);
                outputLine = ptl.processInput(json);
                System.out.println("HACIA CLIENTE"+outputLine);
                out.println(outputLine);
                   } catch (JSONException ex) {
                    Logger.getLogger(MultiServerThread.class.getName()).log(Level.SEVERE, null, ex);
                }
                
            }
            MultiServer.inactivo();
            out.close();
            in.close();
            socket.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}