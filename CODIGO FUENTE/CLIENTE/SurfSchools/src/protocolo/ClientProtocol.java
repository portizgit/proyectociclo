/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package protocolo;

import java.math.BigDecimal;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.json.Json;
import javax.json.JsonObject;
import org.json.JSONException;
import org.json.JSONObject;
import salida.IO;

/**
 *
 * @author Pablo
 * 
 * Esta clase es el protocolo de la aplicación, el protocolo se encarga de crear el JSON que se enviará al servidor el cuál contiene el nombre
 * de la petición y los valores necesarios
 * 
 * Se utiliza la clase IO con sus métodos para enviar el JSON y recibir el JSON de vuelta del servidor
 */
public class ClientProtocol {
    Socket socket;
    IO io;
    public ClientProtocol(Socket socket){
        this.socket=socket;
        io=new IO(socket);
    }
    public JSONObject registro(String nombre, String gerente, String latitud, String longitud, String direccion, String descripcion, String usuario, String contrasena, boolean colectivas, boolean individuales, boolean excursiones, boolean alquilerkayak, boolean alquilersurf ){
         
        try {
            JSONObject jregistro = new JSONObject();
            jregistro.put("peticion", "registro-escuela");
            jregistro.put("nombre", nombre);
            jregistro.put("gerente", gerente);
            jregistro.put("latitud", latitud);
            jregistro.put("longitud", longitud);
            jregistro.put("direccion", direccion);
            jregistro.put("descripcion", descripcion);
            jregistro.put("usuarioescuela", usuario);
            jregistro.put("contrasena", contrasena);
            jregistro.put("colectivas", colectivas);
            jregistro.put("individuales", individuales);
            jregistro.put("excursiones", excursiones);
            jregistro.put("alquilerkayak",alquilerkayak);
            jregistro.put("alquilersurf",alquilersurf);
            
            io.enviar(jregistro);
            
            
        } catch (JSONException ex) {
            Logger.getLogger(ClientProtocol.class.getName()).log(Level.SEVERE, null, ex);
        }
        return io.recibir();
    }
    
     public JSONObject setAprobado(int idalquiler){
         try {
            JSONObject japrobado =new JSONObject();
            japrobado.put("peticion", "alquiler-aprobado");
            japrobado.put("idalquiler", idalquiler);
            
            
            io.enviar(japrobado);
            
           
        } catch (JSONException ex) {
            Logger.getLogger(ClientProtocol.class.getName()).log(Level.SEVERE, null, ex);
        }
         return io.recibir();
    }
     
      public JSONObject getAlertas(String escuela){
         try {
            JSONObject japrobado =new JSONObject();
            japrobado.put("peticion", "get-alertas");
            japrobado.put("escuela", escuela);
            
            
            io.enviar(japrobado);
            
           
        } catch (JSONException ex) {
            Logger.getLogger(ClientProtocol.class.getName()).log(Level.SEVERE, null, ex);
        }
         return io.recibir();
    }
     
     
     
      public JSONObject suspenderActividad(String escuela,int id,String mensaje,String asunto){
         try {
            JSONObject japrobado =new JSONObject();
            japrobado.put("peticion", "suspende-actividad");
            japrobado.put("escuela",escuela);
            japrobado.put("id",id);
            japrobado.put("asunto",asunto);
            japrobado.put("mensaje",mensaje);
            
            
            io.enviar(japrobado);
            
           
        } catch (JSONException ex) {
            Logger.getLogger(ClientProtocol.class.getName()).log(Level.SEVERE, null, ex);
        }
         return io.recibir();
    }
      
       public JSONObject enviarMsn(String escuela,int id,String mensaje,String asunto){
         try {
            JSONObject japrobado =new JSONObject();
            japrobado.put("peticion", "mensaje-actividad");
            japrobado.put("escuela",escuela);
            japrobado.put("id",id);
            japrobado.put("asunto",asunto);
            japrobado.put("mensaje",mensaje);
            
            
            io.enviar(japrobado);
            
           
        } catch (JSONException ex) {
            Logger.getLogger(ClientProtocol.class.getName()).log(Level.SEVERE, null, ex);
        }
         return io.recibir();
    }
    
    public JSONObject setCancelado(int idalquiler){
         try {
            JSONObject jcancelar =new JSONObject();
            jcancelar.put("peticion", "alquiler-cancelado");
            jcancelar.put("idalquiler", idalquiler);
            
            
            io.enviar(jcancelar);
            
           
        } catch (JSONException ex) {
            Logger.getLogger(ClientProtocol.class.getName()).log(Level.SEVERE, null, ex);
        }
         return io.recibir();
    }
    public JSONObject cancelarActividad(int id){
         try {
            JSONObject jcancelar =new JSONObject();
            jcancelar.put("peticion", "cancelar-actividad");
            jcancelar.put("id", id);
            
            
            io.enviar(jcancelar);
            
           
        } catch (JSONException ex) {
            Logger.getLogger(ClientProtocol.class.getName()).log(Level.SEVERE, null, ex);
        }
         return io.recibir();
    }
      public JSONObject getParticipantes(int id){
         try {
            JSONObject jcancelar =new JSONObject();
            jcancelar.put("peticion", "get-participantes");
            jcancelar.put("id", id);
            
            
            io.enviar(jcancelar);
            
           
        } catch (JSONException ex) {
            Logger.getLogger(ClientProtocol.class.getName()).log(Level.SEVERE, null, ex);
        }
         return io.recibir();
    }
    
    public JSONObject programarActividad(int idactividad, String fecha, String participantes){
         try {
            JSONObject jproact =new JSONObject();
            jproact.put("peticion", "programar-actividad");
            jproact.put("idactividad", idactividad);
            jproact.put("fecha",fecha);
            jproact.put("participantes",participantes);
            
            
            io.enviar(jproact);
            
           
        } catch (JSONException ex) {
            Logger.getLogger(ClientProtocol.class.getName()).log(Level.SEVERE, null, ex);
        }
         return io.recibir();
    }
    
      public JSONObject getActividadesProgramadas(String nombreescuela,String tipo){
         try {
            JSONObject jproact =new JSONObject();
            jproact.put("peticion", "actividades-programadas-escuela");
            jproact.put("nombre",nombreescuela);
            jproact.put("tipo",tipo);
            
            
            io.enviar(jproact);
            
           
        } catch (JSONException ex) {
            Logger.getLogger(ClientProtocol.class.getName()).log(Level.SEVERE, null, ex);
        }
         return io.recibir();
    }
    
    
    public JSONObject newExcursion(String nombre, String descripcion, String vientomax, String lluvia, String usuario){
         try {
            JSONObject jexcursion =new JSONObject();
            jexcursion.put("peticion", "nueva-excursion");
            jexcursion.put("nombre", nombre);
            jexcursion.put("descripcion",descripcion);
            jexcursion.put("vientomax",vientomax);
            jexcursion.put("lluvia",lluvia);
            jexcursion.put("usuario",usuario);
            
            
            io.enviar(jexcursion);
            
           
        } catch (JSONException ex) {
            Logger.getLogger(ClientProtocol.class.getName()).log(Level.SEVERE, null, ex);
        }
         return io.recibir();
    }
     public JSONObject newColectiva(String nivel, String descripcion, String precio, String usuario){
         try {
            JSONObject jcolectiva =new JSONObject();
            jcolectiva.put("peticion", "nueva-colectiva");
            jcolectiva.put("descripcion",descripcion);
            jcolectiva.put("nivel",nivel);
            jcolectiva.put("precio",precio);
            jcolectiva.put("usuario",usuario);
            
            
            io.enviar(jcolectiva);
            
           
        } catch (JSONException ex) {
            Logger.getLogger(ClientProtocol.class.getName()).log(Level.SEVERE, null, ex);
        }
         return io.recibir();
    }
    
       public JSONObject getExcursiones(String nombreescuela){
        try {
            JSONObject jexcursiones = new JSONObject();
            jexcursiones.put("peticion", "excursiones-escuela");
            jexcursiones.put("nombreescuela", nombreescuela);
            
            
            io.enviar(jexcursiones);
            
           
        } catch (JSONException ex) {
            Logger.getLogger(ClientProtocol.class.getName()).log(Level.SEVERE, null, ex);
        }
         return io.recibir();
    }
       
       public JSONObject getColectivas(String nombreescuela){
        try {
            JSONObject jexcursiones = new JSONObject();
            jexcursiones.put("peticion", "colectivas-escuela");
            jexcursiones.put("nombreescuela", nombreescuela);
            
            
            io.enviar(jexcursiones);
            
           
        } catch (JSONException ex) {
            Logger.getLogger(ClientProtocol.class.getName()).log(Level.SEVERE, null, ex);
        }
         return io.recibir();
    }
    
    public JSONObject login(String nombre, String contrasena){
        try {
            JSONObject jlogin =new JSONObject();
            jlogin.put("peticion", "login-escuela");
            jlogin.put("nombre", nombre);
            jlogin.put("contrasena", contrasena);
            
            
            io.enviar(jlogin);
            
           
        } catch (JSONException ex) {
            Logger.getLogger(ClientProtocol.class.getName()).log(Level.SEVERE, null, ex);
        }
         return io.recibir();
    }
    
    public JSONObject getPrecios(String usuario){
        try {
            JSONObject jprecios = new JSONObject();
            jprecios.put("peticion", "precios-escuela");
            jprecios.put("usuario", usuario);
            
            
            io.enviar(jprecios);
            
           
        } catch (JSONException ex) {
            Logger.getLogger(ClientProtocol.class.getName()).log(Level.SEVERE, null, ex);
        }
         return io.recibir();
    }
    
      public JSONObject getAlquileres(String usuario,String tipoalquiler){
        try {
            JSONObject jalquileres = new JSONObject();
            jalquileres.put("peticion", "alquileres-escuela");
            jalquileres.put("usuario", usuario);
            jalquileres.put("tipoalquiler",tipoalquiler);
            
            
            io.enviar(jalquileres);
            
           
        } catch (JSONException ex) {
            Logger.getLogger(ClientProtocol.class.getName()).log(Level.SEVERE, null, ex);
        }
         return io.recibir();
    }
    
     public JSONObject setPosibilidadSurfear(String posibilidad, String usuario){
        try {
            JSONObject jposibilidad=new JSONObject();
            jposibilidad.put("peticion","set-posibilidad-surfear");
            jposibilidad.put("valor",posibilidad);
            jposibilidad.put("usuario",usuario);
            
            
            io.enviar(jposibilidad);
            
          
        } catch (JSONException ex) {
            Logger.getLogger(ClientProtocol.class.getName()).log(Level.SEVERE, null, ex);
        }
          return io.recibir();
     }
    
     public JSONObject datosEscuela(String nombre){
        try {
            JSONObject jdatos = new JSONObject();
            jdatos.put("peticion", "datos-escuela");
            jdatos.put("nombre", nombre);
            
            io.enviar(jdatos);
            

        } catch (JSONException ex) {
            Logger.getLogger(ClientProtocol.class.getName()).log(Level.SEVERE, null, ex);
        }
           return io.recibir();
    }
     
    public JSONObject actualiza(String nombre, String gerente, String latitud, String longitud, String direccion, String descripcion, String usuario, boolean colectivas, boolean individuales, boolean excursiones, boolean alquilerkayak, boolean alquilersurf ){
         
        try {
            JSONObject jactualiza = new JSONObject();
            jactualiza.put("peticion", "actualiza-escuela");
            jactualiza.put("nombre", nombre);
            jactualiza.put("gerente", gerente);
            jactualiza.put("latitud", latitud);
            jactualiza.put("longitud", longitud);
            jactualiza.put("direccion", direccion);
            jactualiza.put("descripcion", descripcion);
            jactualiza.put("usuarioescuela", usuario);
            jactualiza.put("colectivas", colectivas);
            jactualiza.put("individuales", individuales);
            jactualiza.put("excursiones", excursiones);
            jactualiza.put("alquilerkayak",alquilerkayak);
            jactualiza.put("alquilersurf",alquilersurf);
            
            io.enviar(jactualiza);
            
            
        } catch (JSONException ex) {
            Logger.getLogger(ClientProtocol.class.getName()).log(Level.SEVERE, null, ex);
        }
        return io.recibir();
    }

      public JSONObject setPrecios(String usuario,String preciohora,String preciodia, String alquiler){
        try {
            JSONObject jprecios = new JSONObject();
            jprecios.put("peticion", "setprecios-escuela");
            jprecios.put("usuario", usuario);
            jprecios.put("preciohora",preciohora);
            jprecios.put("preciodia",preciodia);
            jprecios.put("alquiler",alquiler);
            
            
            io.enviar(jprecios);
            
           
        } catch (JSONException ex) {
            Logger.getLogger(ClientProtocol.class.getName()).log(Level.SEVERE, null, ex);
        }
        
         return io.recibir();
    }
    
    public void salir(){
        JSONObject jsalir = new JSONObject();
        try {
            jsalir.put("peticion", "salir");
        } catch (JSONException ex) {
            Logger.getLogger(ClientProtocol.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        io.enviar(jsalir);
     
    }
}
