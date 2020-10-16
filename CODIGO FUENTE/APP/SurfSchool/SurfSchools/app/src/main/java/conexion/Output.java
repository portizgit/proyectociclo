package conexion;

import android.os.AsyncTask;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;


/**
 * Created by Pablo on 27/04/2017.
 */

public class Output extends AsyncTask<Socket,Void,Void>{
    /**
     * Este asynctask se encarga de mandar información a la entra del socket del servidor.
     */
    Socket socket;
    PrintWriter out;
    BufferedReader in;
    JSONObject jenviar;
    public Output(Socket socket,JSONObject jenviar){
        this.socket=socket;
        General gr=new General();
        out=gr.getPrintWrinter(socket);
        this.jenviar=jenviar;

    }

    public void enviar(JSONObject enviar){
        out.println(enviar.toString());
    }
    public JSONObject recibir(){
        String sjson=null;
        JSONObject json=null;
        try {
            sjson=in.readLine();


        json = new JSONObject(sjson);
        } catch (IOException ex) {
            Logger.getLogger(Input.class.getName()).log(Level.SEVERE, null, ex);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return json;
    }

    @Override
    protected Void doInBackground(Socket... params) {
        out.println(jenviar.toString());
        return null;
    }
}
