/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package general;

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
/**
 * Esta clase contiene los métodos con los que se obtienen la entrada y salida del socket.
 * @author pablo
 */
public class General {
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
        } catch (IOException ex) {
            Logger.getLogger(General.class.getName()).log(Level.SEVERE, null, ex);
        }
        return in;
    }
}
