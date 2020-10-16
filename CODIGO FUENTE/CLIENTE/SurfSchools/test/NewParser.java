
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;
import org.w3c.dom.Document;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author pablo
 */
public class NewParser {
    
    
    public String[] parser() throws Exception{
        String latlong[]=new String[2];
        int responseCode = 0;
        try{
         String api = "http://maps.googleapis.com/maps/api/geocode/xml?address=" + URLEncoder.encode("Pico del Reloj", "UTF-8") + "&sensor=true";
    URL url = new URL(api);
    HttpURLConnection httpConnection = (HttpURLConnection)url.openConnection();
    httpConnection.connect();
    responseCode = httpConnection.getResponseCode();
    if(responseCode == 200)
    {
      DocumentBuilder builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();;
      Document document = builder.parse(httpConnection.getInputStream());
      XPathFactory xPathfactory = XPathFactory.newInstance();
      XPath xpath = xPathfactory.newXPath();
      XPathExpression expr = xpath.compile("/GeocodeResponse/status");
      String status = (String)expr.evaluate(document, XPathConstants.STRING);
      if(status.equals("OK"))
      {
         
              expr = xpath.compile("//geometry/location/lat");
              String latitude = (String)expr.evaluate(document, XPathConstants.STRING);
              expr = xpath.compile("//geometry/location/lng");
              String longitude = (String)expr.evaluate(document, XPathConstants.STRING);
              System.out.println(latitude+" "+longitude);
              expr=xpath.compile("//address_component");
              System.out.println((String)expr.evaluate(document, XPathConstants.STRING));
              latlong[0]=latitude;
              latlong[1]=longitude;
              
              expr = xpath.compile("/GeocodeResponse/status/address_component");
              return latlong;
         
         
         
      }
      else
      {
          throw new Exception("Error al obtener la latitud y longitud");
      
      }
    }
    return latlong;
     } catch (XPathExpressionException ex) {
              Logger.getLogger(NewParser.class.getName()).log(Level.SEVERE, null, ex);
          }
        return latlong;
    }
    
}
