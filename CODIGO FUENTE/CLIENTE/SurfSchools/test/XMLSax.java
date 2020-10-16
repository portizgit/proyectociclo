/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author pablo
 */
import java.io.File;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import org.w3c.dom.Document;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;
   
/**
 * Use SAX Parser to display all employees: id, apellido, dep, salario
 */
public class XMLSax {
   private String currentElement;
   private int bookCount = 1;
   String usuario="&affiliate_id=51ltbazhjl94";
   String url="";
   public ArrayList<String> urls=new ArrayList();
   
   // Constructor
   public XMLSax(String surl) {
      try {
         SAXParserFactory factory = SAXParserFactory.newInstance();
         SAXParser saxParser = factory.newSAXParser();
           URL url = new URL(surl+usuario);
        URLConnection connection = url.openConnection();
         saxParser.parse(connection.getInputStream(), new MyHandler());
      } catch (Exception e) {
         e.printStackTrace();
      }
   }
   
   // Entry main method
    public ArrayList<String> getUrls(){
        return urls;
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
          if (currentElement.equals("url")) {
            url+=(new String(chars, start, length));
            
         } 
      }

   
      // Callback to handle element end tag
      @Override
      public void endElement(String uri, String localName, String qName)
            throws SAXException {
           if(qName.equals("data")){
              urls.add(url);
              System.out.println("URL: "+url);
              url="";
          }
         currentElement = "";
      }
   
      // Callback to handle the character text data inside an element
      
         
      
   }
}
