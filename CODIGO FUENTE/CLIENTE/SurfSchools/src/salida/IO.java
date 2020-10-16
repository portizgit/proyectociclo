/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package salida;

import general.General;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringReader;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonReader;
import javax.swing.JOptionPane;
import org.json.JSONException;
import org.json.JSONObject;

/**
 *
 * @author Pablo
 * 
 * Esta clase se encarga de enviar y recibir la información del servidor. Con ayuda de la clase General de la que obtiene la entrada y salida.
 */
public class IO {
    
    Socket socket;
    PrintWriter out;
    BufferedReader in;
    public IO(Socket socket){
        this.socket=socket;
        General gr=new General();
        out=gr.getPrintWrinter(socket);
        in=gr.getBufferedReader(socket);
    }
    
    public void enviar(JSONObject enviar){
        out.println(enviar.toString());
    }
     public JSONObject recibir(){
        JSONObject json=null;
        try {
            String sjson=null;
            try {
                sjson=in.readLine();
            } catch (IOException ex) {
               JOptionPane.showMessageDialog(null, "Error de conexión se cerrará la aplicación", "Error", JOptionPane.ERROR_MESSAGE);
               System.exit(0);
            }
            
            json = new JSONObject(sjson);
            
        } catch (JSONException ex) {
            Logger.getLogger(IO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return json;
    }
}
