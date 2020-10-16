
package cliente;
import interfaz.PrincipalFrame;
import java.io.*;
import java.net.*;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import protocolo.ClientProtocol;

public class Client {
    /**
     * Esta clase es la que inicia el cliente.
     * Se conecta al servidor, si no consigue conectarse muestra un mensaje de error, y muestra la primera interfaz, la de login.
     */
   
    public static Socket socket;
    public static ClientProtocol ptl;
    public static String nombreescuela;
    public static Properties propiedades;
    public static File fprop;
    
    public static void cambiaConf(String ip, int port){
        propiedades.setProperty("ip", ip);
        propiedades.setProperty("port", port+"");
        try {
          FileOutputStream os=new FileOutputStream(fprop);
        
            propiedades.store(os, "Fichero de Propiedades de la Web");
        } catch (IOException ex) {
            Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    public static void main(String[] args) throws IOException {

        try {
            propiedades = new Properties();
  
    try {

        File jarPath=new File(Client.class.getProtectionDomain().getCodeSource().getLocation().getPath());
        String propertiesPath=jarPath.getParentFile().getAbsolutePath();
        System.out.println(" propertiesPath-"+propertiesPath);
        fprop=new File(propertiesPath+"/conf.properties");
        propiedades.load(new FileInputStream(fprop));
    } catch (IOException e1) {
        e1.printStackTrace();
    }
 
   /**Obtenemos los parametros definidos en el archivo*/
   String ip = propiedades.getProperty("ip");
   String port=propiedades.getProperty("port");
   System.out.println(port);
   int iport = Integer.parseInt(port);
   System.out.println("IP: "+ip+" Port: "+port);
       
           
            
            PrincipalFrame pf=new PrincipalFrame();
            socket = new Socket(ip, iport);
            
            ptl=new ClientProtocol(socket);
        } catch (UnknownHostException e) {
            System.err.println("No se reconoce el host");
        } catch (IOException e) {
            System.out.println(e.getMessage());
            JOptionPane.showMessageDialog(null, "No se ha podido conectar con el servidor", "Error", JOptionPane.ERROR_MESSAGE);
            
        }

    }
    

    public static ClientProtocol getClientProtocol(){
        return ptl;
    }
    
    public static Socket getClientSocket(){
        return socket;
    }
}