/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package server;

import java.sql.Connection;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.json.JSONException;
import org.json.JSONObject;
/**
 * Esta clase es el protocolo del servidor. Recibe un json que es el que recibe el servidor del cliente , obtiene el nombre de la petición y hace un
 * switchcase, entra en el case que corresponda, obtiene los datos extra que contenga el JSON y lo envía a la clase que gestiona la base de datos para que realice la operación
 * necesaria. Seguidamente, recupera lo que devuelva ese método de la clase que gestiona la bd y lo devuelve al hilo del servidor para que lo envíe al cliente.
 * @author pablo
 */
public class Protocol {

    String user = "surfschool";
    String password = "surfschool";
    String url = "jdbc:mysql://pablocadista.hopto.org:2222/surfschools";
    Connection connection;
    GestionaBD bd;

    public void Protocol() {

        bd = new GestionaBD();
        System.out.println("CREACION PROTOCOLO");
    }

    public String processInput(JSONObject theInput) {
        JSONObject output = null;
        try {
            String nombreescuela, gerente, latitud, longitud, direccion, descripcion, usuarioescuela, contraescuela, posibilidad, usuariocliente, contracliente, nombrecliente, tlfncliente, emailcliente, phora, pdia, alquiler, tipoalquiler, fecha, hora, factura, material, nombre, lluvia, vientomax, participantes, usuario, tipo, nivel, precio,asunto,mensaje,notas;
            boolean colectivas, individuales, excursiones, alquilerkayak, alquilersurf;
            int cantidad, idalquiler, idactividad, id;
            bd = new GestionaBD();

            String peticion = theInput.getString("peticion");
            switch (peticion) {

                /**
                 * DATOS ESCUELA
                 */
                
                
                case "registro-escuela":

                    nombreescuela = theInput.getString("nombre");
                    gerente = theInput.getString("gerente");
                    latitud = theInput.getString("latitud");
                    longitud = theInput.getString("longitud");
                    direccion = theInput.getString("direccion");
                    descripcion = theInput.getString("descripcion");
                    usuarioescuela = theInput.getString("usuarioescuela");
                    contraescuela = theInput.getString("contrasena");
                    colectivas = theInput.getBoolean("colectivas");
                    individuales = theInput.getBoolean("individuales");
                    excursiones = theInput.getBoolean("excursiones");
                    alquilerkayak = theInput.getBoolean("alquilerkayak");
                    alquilersurf = theInput.getBoolean("alquilersurf");
                    output = bd.registroescuela(nombreescuela, gerente, latitud, longitud, direccion, descripcion, usuarioescuela, contraescuela, colectivas, individuales, excursiones, alquilerkayak, alquilersurf);
                    break;

                case "login-escuela":
                    nombreescuela = theInput.getString("nombre");
                    contraescuela = theInput.getString("contrasena");
                    output = bd.loginescuela(nombreescuela, contraescuela);

                    break;
                    
                case "get-alertas":
                    nombreescuela=theInput.getString("escuela");
                    output=bd.getAlertas(nombreescuela);
                    
                    break;
                    
                 case "contador-mensajes":
                    usuario = theInput.getString("usuario");
                    output = bd.getContadorMensajes(usuario);

                    break;

                case "datos-usuario":
                    nombre = theInput.getString("nombre");
                    output = bd.datosUsuario(nombre);

                    break;
                    
                 case "get-participantes":
                    id = theInput.getInt("id");
                    output = bd.getParticipantes(id);

                    break;
                    
                 case "alquiler-visto":
                    id = theInput.getInt("id");
                    output = bd.setAlquilerVisto(id);

                    break;
                    
                        
                 case "alquiler-realizado":
                    id = theInput.getInt("id");
                    output = bd.setAlquilerRealizado(id);

                    break;
                    
                case "get-alquiler-cliente":
                    usuario = theInput.getString("usuario");
                    tipo = theInput.getString("tipo");
                    output = bd.getAlquilerCliente(usuario, tipo);

                    break;

                case "apuntar-actividad-cliente":
                    usuario = theInput.getString("usuario");
                    id = theInput.getInt("idactividad");
                    output = bd.apuntarActividad(usuario, id);

                    break;

                case "programar-actividad":
                    idactividad = theInput.getInt("idactividad");
                    participantes = theInput.getString("participantes");
                    fecha = theInput.getString("fecha");
                    output = bd.programarActividad(idactividad, participantes, fecha);

                    break;

                case "actividades-programadas-escuela":
                    nombreescuela = theInput.getString("nombre");
                    tipo = theInput.getString("tipo");
                    output = bd.getActividadesProgramadas(nombreescuela, tipo);

                    break;
                
                  case "salir-actividad-cliente":
                    id = theInput.getInt("idactividad");
                    nombre = theInput.getString("usuario");
                    output = bd.cancelarActividadCliente(nombre,id);

                    break;

                case "actividades-cliente":
                    nombreescuela = theInput.getString("nombre");
                    usuario = theInput.getString("usuario");
                    tipo = theInput.getString("tipo");
                    output = bd.getActividadesProgramadasCliente(usuario, nombreescuela, tipo);

                    break;
                    
                case "suspende-actividad":
                    nombreescuela = theInput.getString("escuela");
                    asunto = theInput.getString("asunto");
                    mensaje = theInput.getString("mensaje");
                    id= theInput.getInt("id");
                    output = bd.suspenderActividad(nombreescuela,id,mensaje,asunto);

                    break;
                    
                 case "mensaje-actividad":
                    nombreescuela = theInput.getString("escuela");
                    asunto = theInput.getString("asunto");
                    mensaje = theInput.getString("mensaje");
                    id= theInput.getInt("id");
                    output = bd.enviarMsn(nombreescuela,id,mensaje,asunto);

                    break;

                case "actividades-cliente-participa":
                    usuario = theInput.getString("usuario");
                    tipo = theInput.getString("tipo");
                    output = bd.getActividadesProgramadasParticipaCliente(usuario, tipo);

                    break;

                case "precios-escuela":
                    usuarioescuela = theInput.getString("usuario");
                    output = bd.getPrecios(usuarioescuela);

                    break;

                case "alquileres-escuela":
                    usuarioescuela = theInput.getString("usuario");
                    tipoalquiler = theInput.getString("tipoalquiler");
                    output = bd.getAlquiler(usuarioescuela, tipoalquiler);

                    break;

                case "participantes-actividad":
                    id = theInput.getInt("idactividad");
                    output = bd.getParticipantes(id);

                    break;

                case "setprecios-escuela":
                    usuarioescuela = theInput.getString("usuario");
                    phora = theInput.getString("preciohora");
                    pdia = theInput.getString("preciodia");
                    alquiler = theInput.getString("alquiler");
                    output = bd.setPrecios(usuarioescuela, phora, pdia, alquiler);

                    break;

                case "set-posibilidad-surfear":
                    posibilidad = theInput.getString("valor");
                    usuarioescuela = theInput.getString("usuario");
                    output = bd.setPosibilidadSurfear(posibilidad, usuarioescuela);

                    break;

                case "datos-escuela":
                    nombreescuela = theInput.getString("nombre");
                    output = bd.datosescuela(nombreescuela);
                    break;

                case "nueva-colectiva":

                    descripcion = theInput.getString("descripcion");
                    nivel = theInput.getString("nivel");
                    precio = theInput.getString("precio");
                    usuarioescuela = theInput.getString("usuario");
                    output = bd.newColectiva(nivel, descripcion, precio, usuarioescuela);
                    break;

                case "colectivas-escuela":
                    nombreescuela = theInput.getString("nombreescuela");
                    output = bd.getColectivas(nombreescuela);
                    break;

                case "excursiones-escuela":
                    nombreescuela = theInput.getString("nombreescuela");
                    output = bd.getExcursiones(nombreescuela);
                    break;

                case "nueva-excursion":
                    nombre = theInput.getString("nombre");
                    descripcion = theInput.getString("descripcion");
                    lluvia = theInput.getString("lluvia");
                    vientomax = theInput.getString("vientomax");
                    usuarioescuela = theInput.getString("usuario");
                    output = bd.newExcursion(nombre, descripcion, lluvia, vientomax, usuarioescuela);
                    break;

                case "actualiza-escuela":

                    nombreescuela = theInput.getString("nombre");
                    gerente = theInput.getString("gerente");
                    latitud = theInput.getString("latitud");
                    longitud = theInput.getString("longitud");
                    direccion = theInput.getString("direccion");
                    descripcion = theInput.getString("descripcion");
                    usuarioescuela = theInput.getString("usuarioescuela");
                    colectivas = theInput.getBoolean("colectivas");
                    individuales = theInput.getBoolean("individuales");
                    excursiones = theInput.getBoolean("excursiones");
                    alquilerkayak = theInput.getBoolean("alquilerkayak");
                    alquilersurf = theInput.getBoolean("alquilersurf");
                    output = bd.actualizaescuela(nombreescuela, gerente, latitud, longitud, direccion, descripcion, usuarioescuela, colectivas, individuales, excursiones, alquilerkayak, alquilersurf);
                    break;

                /**
                 * DATOS CLIENTE
                 */
                case "alquiler-cliente":
                    usuariocliente = theInput.getString("usuario");
                    nombreescuela = theInput.getString("nombre");
                    cantidad = theInput.getInt("cantidad");
                    fecha = theInput.getString("fecha");
                    hora = theInput.getString("hora");
                    material = theInput.getString("material");
                    factura = theInput.getString("factura");
                    notas=theInput.getString("notas");
                    output = bd.setAlquiler(usuariocliente, nombreescuela, cantidad, fecha, hora, material, factura,notas);
                    break;

                case "datosescuela-nombre":
                    nombreescuela = theInput.getString("nombre");
                    output = bd.datosescuelaNombre(nombreescuela);
                    break;
                
                 case "mensajes-cliente":
                    usuariocliente = theInput.getString("usuario");
                    output = bd.getMensajes(usuariocliente);
                    break;
                    
                 case "mensaje-visto":
                     id=theInput.getInt("id");
                     output=bd.setMensajeVisto(id);
                    break;

                case "login-cliente":
                    usuariocliente = theInput.getString("usuario");
                    contracliente = theInput.getString("contrasena");
                    output = bd.logincliente(usuariocliente, contracliente);

                    break;

                case "unfollow-escuela":
                    nombreescuela = theInput.getString("nombre");
                    usuariocliente = theInput.getString("usuario");
                    output = bd.dejarSeguir(nombreescuela, usuariocliente);

                    break;

                case "precios-escuela-cliente":
                    nombreescuela = theInput.getString("nombre");
                    output = bd.getPreciosCliente(nombreescuela);

                    break;

                case "actualiza-usuario":
                    usuario = theInput.getString("usuario");
                    nombre = theInput.getString("nombre");
                    tlfncliente = theInput.getString("telefono");
                    emailcliente = theInput.getString("email");
                    contracliente = theInput.getString("contrasena");
                    output = bd.actualizausuario(usuario, nombre, tlfncliente, emailcliente, contracliente);

                    break;

                case "alquiler-aprobado":
                    idalquiler = theInput.getInt("idalquiler");
                    output = bd.setAprobado(idalquiler);
                    break;

                case "alquiler-cancelado":
                    idalquiler = theInput.getInt("idalquiler");
                    output = bd.setCancelado(idalquiler);
                    break;

                case "cancelar-actividad":
                    id = theInput.getInt("id");
                    output = bd.cancelarActividad(id);
                    break;

                case "seguir-escuela":
                    usuariocliente = theInput.getString("usuario");
                    nombreescuela = theInput.getString("escuela");
                    output = bd.seguirEscuela(usuariocliente, nombreescuela);

                    break;

                case "escuelas-seguidor":
                    usuariocliente = theInput.getString("usuario");
                    output = bd.getEscuelasSeguidor(usuariocliente);

                    break;

                case "todas-escuelas":
                    output = bd.getEscuelas();
                    break;

                case "registro-cliente":

                    usuariocliente = theInput.getString("usuario");
                    nombrecliente = theInput.getString("nombre");
                    tlfncliente = theInput.getString("telefono");
                    emailcliente = theInput.getString("email");
                    contracliente = theInput.getString("contrasena");

                    output = bd.registrocliente(usuariocliente, nombrecliente, tlfncliente, emailcliente, contracliente);
                    break;
            }
        } catch (JSONException ex) {
            Logger.getLogger(Protocol.class.getName()).log(Level.SEVERE, null, ex);
        }
        return output.toString();
    }
}
