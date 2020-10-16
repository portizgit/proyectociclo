
import surfschools.server.NewSax;
import cliente.Client;
import static com.oracle.jrockit.jfr.ContentType.Address;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathFactory;
import org.json.JSONObject;
import org.w3c.dom.Document;
import protocolo.ClientProtocol;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author pablo
 */
public class ObtenerCiudad {
       
      static Connection connection;
    static String driver = "com.mysql.jdbc.Driver";
    static String user = "surfschool";
    static String password = "surfschool";
    static String url = "jdbc:mysql://pablocadista.hopto.org:3306/surfschools";
    public static void main(String[]args){
        try {
           
             NewSax ns=new NewSax("Playa El Palmar ");
             String scity=ns.getCity();
             String city="";
             if(!scity.equals("")||city!=null){
                 city=scity.trim();
             }
             System.out.println("*"+city+"*");
             connection = DriverManager.getConnection(url, user, password);
            Class.forName(driver);
            String query = "SELECT * FROM api WHERE ciudad=?";

            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, city);
            
            ResultSet rs = preparedStatement.executeQuery();
            if (rs.next()) {
                System.out.println(rs.getString(1)+" "+rs.getString(2));
            }
             
                 
                 
                 
               
        } catch (Exception ex) {
            Logger.getLogger(ObtenerCiudad.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
