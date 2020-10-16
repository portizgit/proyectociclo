package server;


import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.TreeMap;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author pablo
 */
public class CityName {
    /**
     * Esta clase obtiene la localidad de la dirección recibida.
     * Se usa a la hora de obtener la notificaciones de tiempo de cada escuela.
     */
    TreeMap<String,String>municipios=new TreeMap<>();
     String usuario="&affiliate_id=51ltbazhjl94";
   String url="",nombre;
   String currentElement;
   String nfinal;
   
   // Constructor
   public CityName(String address) {
      try {
         SAXParserFactory factory = SAXParserFactory.newInstance();
         SAXParser saxParser = factory.newSAXParser();
          String api = "http://maps.googleapis.com/maps/api/geocode/xml?address=" + URLEncoder.encode(address, "UTF-8") + "&components=country:ES&sensor=true";
    URL url = new URL(api);
        URLConnection connection = url.openConnection();
         saxParser.parse(connection.getInputStream(), new MyHandler());
      } catch (Exception e) {
         e.printStackTrace();
      }
   }
   public String getCity(){
       return nfinal;
   }
   // Entry main method
   
    
   
   /*
    * Inner class for the Callback Handlers.
    */
   class MyHandler extends DefaultHandler {
       boolean dentroAddress=false;
       boolean correcto=true;
       String tipo="",nombre="";
       int cont=0;
      // Callback to handle element start tag
      @Override
      public void startElement(String uri, String localName, String qName,
                               Attributes attributes) throws SAXException {
         currentElement = qName;
      
         
         if (currentElement.equals("address_component")) {
           dentroAddress=true;
         }

      }
      
       @Override
      public void characters(char[] chars, int start, int length) throws SAXException {
         
          if (currentElement.equals("long_name")) {
              if(dentroAddress){
            nombre+=(new String(chars, start, length));
           // System.out.println(nombre);
              }
         } 
         if (currentElement.equals("type")) {
             if(new String(chars,start,length).equals("locality")){
                 correcto=true;
                 tipo="locality";
                 System.out.println("Nombre: "+nombre);
                 nfinal=nombre;
            System.out.println("Tipo: "+tipo);
             }
           
       
            
         } 
         
         
         
      }

   
      // Callback to handle element end tag
      @Override
      public void endElement(String uri, String localName, String qName)
            throws SAXException {
          if(qName.equals("type")&&tipo.equals("locality")){
           //   System.out.println("Nombre "+nombre);
          }
          
          if(qName.equals("address_component")){
              dentroAddress=false;
             // System.out.println("DENTRO FINAL ADDRESS_COMPONENT");
              if(correcto){
               //   System.out.println("hola");
                //  System.out.println(nombre);
              }
             nombre="";
          }
          
      }
   
      // Callback to handle the character text data inside an element
      
         
      
   }
}
