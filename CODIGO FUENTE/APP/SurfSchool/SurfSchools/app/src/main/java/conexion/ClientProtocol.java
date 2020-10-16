package conexion; /**
 * Created by Pablo on 23/04/2017.
 */

import org.json.JSONException;
import org.json.JSONObject;

import java.net.Socket;
import java.util.concurrent.ExecutionException;

import javax.json.JsonObject;


/**
 *
 * @author Pablo
 */
public class ClientProtocol{
    Socket socket;
    Input io;
    String peticion;
    JsonObject respuesta;

    /**
     * Esta clase es el protocolo del ciente, encargado de transformar lo que quieren los activity de la app, en objetos JSON que contienen la peticion a realizar
     * y los valores extras que son necesarios para la petición.
     *
     * Utiliza dos asynctask (Input y Output) para enviar y recibir del servidor.
     * @param socket
     */
    public ClientProtocol(Socket socket){
        System.out.println("CONSTRUCTOR ASYNCTASK "+this.peticion);
        this.socket=socket;
        io=new Input(socket);
    }
    public JSONObject registro(String usuario, String contrasena, String nombre, String email, String telefono) throws ExecutionException, InterruptedException {

        JSONObject jregistro = new JSONObject();
        JSONObject respuesta=null;
        try {
            jregistro.put("peticion", "registro-cliente");

             jregistro.put("nombre", nombre);
                jregistro.put("usuario", usuario);
                jregistro.put("contrasena", contrasena);
                jregistro.put("email",email);
                jregistro.put("telefono",telefono);

        System.out.println(jregistro);
        Output op=new Output(Client.socket,jregistro);
        op.execute();

        Input ip=new Input(Client.socket);
        respuesta=ip.execute().get();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return respuesta;

    }

    public JSONObject login(String nombre, String contrasena) throws ExecutionException, InterruptedException {
        
        JSONObject jlogin = new JSONObject();
        JSONObject respuesta=null;
        try{
                jlogin.put("peticion", "login-cliente");
                jlogin.put("usuario", nombre);
                jlogin.put("contrasena", contrasena);


        Output op=new Output(Client.socket,jlogin);
        op.execute();

        Input ip=new Input(Client.socket);
        respuesta=ip.execute().get();

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return respuesta;
    }

    public JSONObject datosEscuela(String nombre) throws ExecutionException, InterruptedException {

        JSONObject jdatos = new JSONObject();
        JSONObject respuesta=null;
        try{
            jdatos.put("peticion", "datosescuela-nombre");
            jdatos.put("nombre", nombre);


            Output op=new Output(Client.socket,jdatos);
            op.execute();

            Input ip=new Input(Client.socket);
            respuesta=ip.execute().get();

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return respuesta;
    }

    public JSONObject seguirEscuela(String usuario, String nombreescuela) throws ExecutionException, InterruptedException {

        JSONObject jseguir = new JSONObject();
        JSONObject respuesta=null;
        try{
            jseguir.put("peticion", "seguir-escuela");
            jseguir.put("usuario", usuario);
            jseguir.put("escuela", nombreescuela);


            Output op=new Output(Client.socket,jseguir);
            op.execute();

            Input ip=new Input(Client.socket);
            respuesta=ip.execute().get();

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return respuesta;
    }

    public JSONObject getEscuelas() throws ExecutionException, InterruptedException {

        JSONObject jescuelas = new JSONObject();
        JSONObject respuesta=null;
        try{
            jescuelas.put("peticion", "todas-escuelas");


            Output op=new Output(Client.socket,jescuelas);
            op.execute();

            Input ip=new Input(Client.socket);
            respuesta=ip.execute().get();

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return respuesta;
    }

    public JSONObject getMensajes(String usuario) throws ExecutionException, InterruptedException {

        JSONObject jmensajes = new JSONObject();
        JSONObject respuesta=null;
        try{
            jmensajes.put("peticion", "mensajes-cliente");
            jmensajes.put("usuario",usuario);


            Output op=new Output(Client.socket,jmensajes);
            op.execute();

            Input ip=new Input(Client.socket);
            respuesta=ip.execute().get();

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return respuesta;
    }

    public JSONObject getEscuelasSeguidor(String usuario) throws ExecutionException, InterruptedException {

        JSONObject jescuelas = new JSONObject();
        JSONObject respuesta=null;
        try{
            jescuelas.put("peticion", "escuelas-seguidor");
            jescuelas.put("usuario",usuario);

            Output op=new Output(Client.socket,jescuelas);
            op.execute();

            Input ip=new Input(Client.socket);
            respuesta=ip.execute().get();

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return respuesta;
    }

    public JSONObject unfollow(String nombreescuela,String usuario) throws ExecutionException, InterruptedException {

        JSONObject jescuelas = new JSONObject();
        JSONObject respuesta=null;
        try{
            jescuelas.put("peticion", "unfollow-escuela");
            jescuelas.put("nombre",nombreescuela);
            jescuelas.put("usuario",usuario);

            Output op=new Output(Client.socket,jescuelas);
            op.execute();

            Input ip=new Input(Client.socket);
            respuesta=ip.execute().get();

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return respuesta;
    }

    public JSONObject getPrecios(String nombreescuela) throws ExecutionException, InterruptedException {

        JSONObject jescuelas = new JSONObject();
        JSONObject respuesta=null;
        try{
            jescuelas.put("peticion", "precios-escuela-cliente");
            jescuelas.put("nombre",nombreescuela);

            Output op=new Output(Client.socket,jescuelas);
            op.execute();

            Input ip=new Input(Client.socket);
            respuesta=ip.execute().get();

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return respuesta;
    }

    public JSONObject setVisto(int idalquiler) throws ExecutionException, InterruptedException {

        JSONObject jescuelas = new JSONObject();
        JSONObject respuesta=null;
        try{
            jescuelas.put("peticion", "alquiler-visto");
            jescuelas.put("id",idalquiler);

            Output op=new Output(Client.socket,jescuelas);
            op.execute();

            Input ip=new Input(Client.socket);
            respuesta=ip.execute().get();

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return respuesta;
    }

    public JSONObject setCancelado(int idalquiler) throws ExecutionException, InterruptedException {

        JSONObject jescuelas = new JSONObject();
        JSONObject respuesta=null;
        try{
            jescuelas.put("peticion", "alquiler-cancelado");
            jescuelas.put("idalquiler",idalquiler);

            Output op=new Output(Client.socket,jescuelas);
            op.execute();

            Input ip=new Input(Client.socket);
            respuesta=ip.execute().get();

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return respuesta;
    }

    public JSONObject setMensajeVisto(int id) throws ExecutionException, InterruptedException {

        JSONObject jescuelas = new JSONObject();
        JSONObject respuesta=null;
        try{
            jescuelas.put("peticion", "mensaje-visto");
            jescuelas.put("id",id);

            Output op=new Output(Client.socket,jescuelas);
            op.execute();

            Input ip=new Input(Client.socket);
            respuesta=ip.execute().get();

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return respuesta;
    }



    public JSONObject setRealizado(int idalquiler) throws ExecutionException, InterruptedException {

        JSONObject jescuelas = new JSONObject();
        JSONObject respuesta=null;
        try{
            jescuelas.put("peticion", "alquiler-realizado");
            jescuelas.put("id",idalquiler);

            Output op=new Output(Client.socket,jescuelas);
            op.execute();

            Input ip=new Input(Client.socket);
            respuesta=ip.execute().get();

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return respuesta;
    }

    public JSONObject getContadorMensajes(String usuario) throws ExecutionException, InterruptedException {

        JSONObject jescuelas = new JSONObject();
        JSONObject respuesta=null;
        try{
            jescuelas.put("peticion", "contador-mensajes");
            jescuelas.put("usuario",usuario);

            Output op=new Output(Client.socket,jescuelas);
            op.execute();

            Input ip=new Input(Client.socket);
            respuesta=ip.execute().get();

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return respuesta;
    }

    public JSONObject getDatos(String usuario) throws ExecutionException, InterruptedException {

        JSONObject jescuelas = new JSONObject();
        JSONObject respuesta=null;
        try{
            jescuelas.put("peticion", "datos-usuario");
            jescuelas.put("nombre",usuario);

            Output op=new Output(Client.socket,jescuelas);
            op.execute();

            Input ip=new Input(Client.socket);
            respuesta=ip.execute().get();

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return respuesta;
    }

    public JSONObject getAlquileres(String usuario, String tipo) throws ExecutionException, InterruptedException {

        JSONObject jalquileres = new JSONObject();
        JSONObject respuesta=null;
        try{
            jalquileres.put("peticion", "get-alquiler-cliente");
            jalquileres.put("usuario",usuario);
            jalquileres.put("tipo",tipo);

            Output op=new Output(Client.socket,jalquileres);
            op.execute();

            Input ip=new Input(Client.socket);
            respuesta=ip.execute().get();

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return respuesta;
    }

    public JSONObject getActProgramadas(String nombre, String tipo, String usuario) throws ExecutionException, InterruptedException {

        JSONObject jexcursiones = new JSONObject();
        JSONObject respuesta=null;
        try{
            jexcursiones.put("peticion", "actividades-cliente");
            jexcursiones.put("nombre",nombre);
            jexcursiones.put("tipo",tipo);
            jexcursiones.put("usuario",usuario);

            Output op=new Output(Client.socket,jexcursiones);
            op.execute();

            Input ip=new Input(Client.socket);
            respuesta=ip.execute().get();

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return respuesta;
    }

    public JSONObject getActParticipa(String usuario, String tipo) throws ExecutionException, InterruptedException {

        JSONObject jexcursiones = new JSONObject();
        JSONObject respuesta=null;
        try{
            jexcursiones.put("peticion", "actividades-cliente-participa");
            jexcursiones.put("usuario",usuario);
            jexcursiones.put("tipo",tipo);

            Output op=new Output(Client.socket,jexcursiones);
            op.execute();

            Input ip=new Input(Client.socket);
            respuesta=ip.execute().get();

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return respuesta;
    }

    public JSONObject getProgramadas(String nombre, String tipo) throws ExecutionException, InterruptedException {

        JSONObject jexcursiones = new JSONObject();
        JSONObject respuesta=null;
        try{
            jexcursiones.put("peticion", "actividades-cliente");
            jexcursiones.put("nombre",nombre);
            jexcursiones.put("tipo",tipo);

            Output op=new Output(Client.socket,jexcursiones);
            op.execute();

            Input ip=new Input(Client.socket);
            respuesta=ip.execute().get();

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return respuesta;
    }




    public JSONObject apuntarActividad(String usuario, int id) throws ExecutionException, InterruptedException {

        JSONObject jexcursiones = new JSONObject();
        JSONObject respuesta=null;
        try{
            jexcursiones.put("peticion", "apuntar-actividad-cliente");
            jexcursiones.put("usuario",usuario);
            jexcursiones.put("idactividad",id);

            Output op=new Output(Client.socket,jexcursiones);
            op.execute();

            Input ip=new Input(Client.socket);
            respuesta=ip.execute().get();

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return respuesta;
    }

    public JSONObject salirActividad(String usuario, int id) throws ExecutionException, InterruptedException {

        JSONObject jexcursiones = new JSONObject();
        JSONObject respuesta=null;
        try{
            jexcursiones.put("peticion", "salir-actividad-cliente");
            jexcursiones.put("usuario",usuario);
            jexcursiones.put("idactividad",id);

            Output op=new Output(Client.socket,jexcursiones);
            op.execute();

            Input ip=new Input(Client.socket);
            respuesta=ip.execute().get();

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return respuesta;
    }


    public JSONObject actualizaUsuario(String usuario,String nombre, String telefono, String email, String contrasena) throws ExecutionException, InterruptedException {

        JSONObject jescuelas = new JSONObject();
        JSONObject respuesta=null;
        try{
            jescuelas.put("peticion", "actualiza-usuario");
            jescuelas.put("usuario",usuario);
            jescuelas.put("nombre",nombre);
            jescuelas.put("telefono",telefono);
            jescuelas.put("email",email);
            jescuelas.put("contrasena",contrasena);
            Output op=new Output(Client.socket,jescuelas);
            op.execute();

            Input ip=new Input(Client.socket);
            respuesta=ip.execute().get();

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return respuesta;
    }

    public JSONObject newAlquiler(String fecha,int cantidad, String factura, String usuario, String material,String hora,String nombreescuela, String notas) throws ExecutionException, InterruptedException {

        JSONObject jescuelas = new JSONObject();
        JSONObject respuesta=null;
        try{
            jescuelas.put("peticion", "alquiler-cliente");
            jescuelas.put("nombre",nombreescuela);
            jescuelas.put("fecha",fecha);
            jescuelas.put("cantidad",cantidad);
            jescuelas.put("factura",factura);
            jescuelas.put("usuario",usuario);
            jescuelas.put("material",material);
            jescuelas.put("hora",hora);
            jescuelas.put("notas",notas);

            Output op=new Output(Client.socket,jescuelas);
            op.execute();

            Input ip=new Input(Client.socket);
            respuesta=ip.execute().get();

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return respuesta;
    }


}