package conexion;

/**
 * Created by Pablo on 23/04/2017.
 */

import android.os.AsyncTask;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Input extends AsyncTask<Socket,Void,JSONObject>{
    /**
     * Este asynctask se encarga de leer de la tubería de salida del servidor y devolver lo que lee.
     */
    Socket socket;
    PrintWriter out;
    BufferedReader in;
    public Input(Socket socket){
        this.socket=socket;
        General gr=new General();

            in=gr.getBufferedReader(socket);


    }


    public JSONObject recibir(){
        String sjson=null;

        try {
            sjson=in.readLine();
        } catch (IOException e) {
            e.printStackTrace();
        }


        JSONObject json = null;
        try {
            json = new JSONObject(sjson);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return json;
    }

    @Override
    protected JSONObject doInBackground(Socket... params) {
        String sjson=null;
        try {
            sjson=in.readLine();
        } catch (IOException ex) {
            Logger.getLogger(Input.class.getName()).log(Level.SEVERE, null, ex);
        }

        JSONObject json = null;
        try {
            json = new JSONObject(sjson);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return json;
    }
}
