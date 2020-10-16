package activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.Toast;

import conexion.Client;
import com.pablo.surfschools.Mensaje;
import com.pablo.surfschools.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

import adapters.MensajeAdapter;
import conexion.ClientProtocol;
import conexion.General;

public class MensajesActivity extends AppCompatActivity {
    ArrayList<Mensaje> mensajes;
    Mensaje m;

    /**
     * En este activity se muestran los mensajes que el usuario recibe de las diferentes escuelas, se obtienen mediante una petición al servidor y
     * se incluyen en un ArrayList que es utilizado por el Adapter para mostrarlos posteriormente en un listview.
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mensajes);
        mensajes=new ArrayList<Mensaje>();

        ClientProtocol cpl=new ClientProtocol(Client.getSocket());
        JSONObject respuesta=null;
        try{
            respuesta=cpl.getMensajes(General.usuario);

        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

        JSONArray array=null;
        try{
            array=respuesta.getJSONArray("mensajes");
            if(array.length()==0){
                Toast.makeText(MensajesActivity.this, "No ha recibido ningún mensaje",
                        Toast.LENGTH_LONG).show();
            }
            for(int i=0;i<array.length();i++){
                JSONObject data=array.getJSONObject(i);
                String fecha=data.getString("fecha");
                String asunto=data.getString("asunto");
                String contenido=data.getString("contenido");
                int id=data.getInt("id");
                String escuela=data.getString("escuela");
                int visto=data.getInt("visto");

                Mensaje m= new Mensaje(fecha,contenido,asunto,escuela,visto,id);
                mensajes.add(m);
            }


            MensajeAdapter adp = new MensajeAdapter(MensajesActivity.this, mensajes);

            ListView listV = (ListView) findViewById(R.id.listamsn);
            listV.setAdapter(adp);

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
