
import surfschools.server.XMLSaxMunicipios;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.logging.Level;
import java.util.logging.Logger;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author pablo
 */
public class Fechas {
    static String fecha="17-06-2017";
    public static void main(String[]args){
        try {
            SimpleDateFormat formatoFecha = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault());
            System.out.println(fecha);
            Date fechaprg = formatoFecha.parse(fecha);
            SimpleDateFormat formato2=new SimpleDateFormat("yyyyMMdd");
            String fechaformat=formato2.format(fechaprg);
            System.out.println(fechaformat);
            
            XMLSaxMunicipios xsm=new XMLSaxMunicipios("313",fechaformat);
            String[]result=xsm.getResultado();
            if(result[0]==null){
                System.out.println("No se puede obtener la meteorología para esa fecha");
            }else{
            System.out.println("Lluvia: "+result[1]);
            System.out.println("Viento: "+result[0]);
            
            }
        } catch (ParseException ex) {
            Logger.getLogger(Fechas.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
