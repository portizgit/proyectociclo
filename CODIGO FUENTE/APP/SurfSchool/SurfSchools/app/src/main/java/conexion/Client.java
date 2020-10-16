package conexion;

import android.content.Context;
import android.os.AsyncTask;

import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;

import conexion.ClientProtocol;
import conexion.Input;

/**
 * Created by Pablo on 23/04/2017.
 */

public class Client extends AsyncTask{
    /**
     * Esta clase es la que se conecta con el servidor y contiene los datos de conexión.
     */

    public static Socket socket;
    public static ClientProtocol ptl;
    public static String ip="192.168.0.202";
    public static int port=4444;
    public Context ctx;
    String resultado;

    public Client(Context ctx){
        this.ctx=ctx;
    }
    @Override
    protected Object doInBackground(Object[] params) {
        try {


            socket = new Socket(ip,port);
            Input io=new Input(socket);
            ptl=new ClientProtocol(socket);

        } catch (UnknownHostException e) {
            resultado="incorrecto";
            System.err.println("Don't know about host: taranis.");

        } catch (IOException e) {
            resultado="incorrecto";
            System.out.println("NO SE HA PODIDO CONECTAAAAAAAAAAAR !!!! \n\nLAKDJFSDÑKFJL");
            e.printStackTrace();



        }

        return true;
    }


    public static Socket getSocket(){
        return socket;
    }

    public static ClientProtocol getProtocol(){
        return ptl;
    }
    public void conexion(){

        try {


            socket = new Socket("192.168.0.202", 4444);
            Input io=new Input(socket);
            System.out.println(io.recibir());


        } catch (UnknownHostException e) {
            System.err.println("Don't know about host: taranis.");
            System.exit(1);
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(1);
        }
    }
}