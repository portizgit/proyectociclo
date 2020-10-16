/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author pablo
 */
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Map;
import java.util.TreeMap;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.NodeList;

public class TestTiempo      
     
{
    
    static Connection connection;
    static String driver = "com.mysql.jdbc.Driver";
    static String user = "surfschool";
    static String password = "surfschool";
    static String url = "jdbc:mysql://pablocadista.hopto.org:3306/surfschools";
   static ArrayList<String>urls;
   static TreeMap<String,String>municipios;
    public static void main(String[] args) throws ClassNotFoundException 
    {   
        
         try {
              connection = DriverManager.getConnection(url, user, password);
            Class.forName(driver);
             XMLSax xs=new XMLSax("http://api.tiempo.com/index.php?api_lang=es&pais=18");
             urls=xs.getUrls();
             PreparedStatement datosInicio = connection.prepareStatement("INSERT INTO api(ciudad,codigo)  VALUES(?,?)");
           
             
             for(int i=0;i<urls.size();i++){
              //   XMLSaxMunicipios xsm = new XMLSaxMunicipios(urls.get(i));
               //  municipios=xsm.getUrls();
                 
                 for (Map.Entry<String, String> entry : municipios.entrySet()) {
                     String key = entry.getKey();
                     String value = entry.getValue();
                       
                      datosInicio.setString(1, key);
                         datosInicio.setString(2, value);
                      datosInicio.executeUpdate();
                     
                     System.out.println(key + " => " + value);
                 }
                 
                 
                 
                 
             }    } catch (SQLException ex) {
             Logger.getLogger(TestTiempo.class.getName()).log(Level.SEVERE, null, ex);
         }

    }
}
