package surfschools.server;


import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
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
public class XMLSaxMunicipios {
    TreeMap<String,String>municipios=new TreeMap<>();
     String usuario="&affiliate_id=51ltbazhjl94";
   String lluvia="",viento="",fecha;
   
   String currentElement;
   String resultado[]=new String[2];
   boolean dentro=true;
   
   // Constructor
   public XMLSaxMunicipios(String codigo, String fecha) {
      try {
          this.fecha=fecha;
         SAXParserFactory factory = SAXParserFactory.newInstance();
         SAXParser saxParser = factory.newSAXParser();
         URL url = new URL("http://api.tiempo.com/index.php?api_lang=es&localidad="+codigo+usuario+"&v=2.0");
         URLConnection connection = url.openConnection();
         saxParser.parse(connection.getInputStream(), new MyHandler());
      } catch (Exception e) {
         e.printStackTrace();
      }
   }
   
   // Entry main method
    public String[] getResultado(){
        return resultado;
    }
   
   /*
    * Inner class for the Callback Handlers.
    */
   class MyHandler extends DefaultHandler {
      // Callback to handle element start tag
      @Override
      public void startElement(String uri, String localName, String qName,
                               Attributes attributes) throws SAXException {
         currentElement = qName;
      
         if(currentElement.equals("day")){
             
             if(attributes.getValue("value").equals(fecha)){
                 dentro=true;
             }else{
                 dentro=false;
             }
         }
         if(currentElement.equals("wind")){
             if(dentro){
                 resultado[0]=attributes.getValue("value");
                 
             }
         }
         if(currentElement.equals("rain")){
             if(dentro){
                 resultado[1]=attributes.getValue("value");
                 
             }
         }
         if(currentElement.equals("hour")){
             dentro=false;
         }
         /*
         if (currentElement.equals("empleado")) {
            System.out.println("Empleado " + bookCount);
            bookCount++;
            
            String id = attributes.getValue("id");
            System.out.println("\tId (atributo):\t" + id);
            
            //'apellido' no tiene atributos, as� que se mostrar� 'null'
            String apellido = attributes.getValue("apellido");
            System.out.println("\tApellido (atributo):\t" + apellido);
            
         }
*/
      }
      
       @Override
      public void characters(char[] chars, int start, int length) throws SAXException {
         
        
         
         
         
      }

   
      // Callback to handle element end tag
      @Override
      public void endElement(String uri, String localName, String qName)
            throws SAXException {
         
      }
   
      // Callback to handle the character text data inside an element
      
         
      
   }
}
