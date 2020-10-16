package conexion;

/**
 * Created by Pablo on 23/04/2017.
 */

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Pablo
 */
public class General {

    public static String usuario;


    public String getUsuario(){
        return usuario;
    }
    public PrintWriter getPrintWrinter(Socket socket){
        PrintWriter out=null;
        try {
            out=new PrintWriter(socket.getOutputStream(), true);
        } catch (IOException ex) {

            Logger.getLogger(General.class.getName()).log(Level.SEVERE, null, ex);
        }
        return out;
    }
    public BufferedReader getBufferedReader(Socket socket){
        BufferedReader in=null;
        try {
            in=new BufferedReader(new InputStreamReader(socket.getInputStream()));
        } catch (Exception ex) {

        }
        return in;
    }
}
