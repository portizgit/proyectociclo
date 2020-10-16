package activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;
import android.widget.Toast;

import com.pablo.surfschools.Alquiler;
import conexion.Client;
import com.pablo.surfschools.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

import adapters.AlquilerAdapter;
import conexion.ClientProtocol;
import conexion.General;

public class Alquileres extends AppCompatActivity {
    /**
     * Este activity muestra los alquileres pendientes por el cliente y los cuáles siguen activos o han sido cancelados pero no vistos por el cliente.
     * Obtiene los alquileres mediante una petición al servidor, construye un ArrayList con ellos y se lo pasa al Adapter que luego utiliza un listView para mostrarlos.
     */
    ArrayList<Alquiler>alquileres;
    Alquiler obj;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alquileres);

        alquileres=new ArrayList<Alquiler>();
        ClientProtocol cpl=new ClientProtocol(Client.getSocket());
        JSONObject respuesta=null;
        try {
            respuesta = cpl.getAlquileres(General.usuario,"all");
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        JSONArray array= null;
        try {
            array = respuesta.getJSONArray("alquileres");
            if(array.length()==0){
                Toast.makeText(Alquileres.this, "No ha realizado aún ningún alquiler",
                        Toast.LENGTH_LONG).show();
            }
        int nescuelas=0;
        for(int i=0; i<array.length(); i++){
            nescuelas++;
            JSONObject json_data = array.getJSONObject(i);
            String nombre= json_data.getString("nombre");
            int cantidad=json_data.getInt("cantidad");
            String fecha=json_data.getString("fecha");
            String hora=json_data.getString("hora");
            String factura=json_data.getString("factura");
            String tipo=json_data.getString("tipo");
            int id=json_data.getInt("idalquiler");
            int aprobado=json_data.getInt("aprobado");
            int cancelado=json_data.getInt("cancelado");
            int visto=json_data.getInt("visto");
            String material=json_data.getString("material");
            int realizado=json_data.getInt("realizado");

            if(visto!=1&&!material.equals("clases")&&realizado!=1) {
                Alquiler a = new Alquiler(nombre, cantidad, fecha, hora, factura, tipo, id, aprobado, cancelado, visto,material);
                alquileres.add(a);
            }

        }

            AlquilerAdapter adapter = new AlquilerAdapter(getApplicationContext(), alquileres);

            ListView listAlq = (ListView) findViewById(R.id.listviewalquileres);
            listAlq.setAdapter(adapter);


        } catch (JSONException e) {
            e.printStackTrace();
        }

    }




}
