package activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;
import android.widget.Toast;

import com.pablo.surfschools.Excursion;
import com.pablo.surfschools.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

import adapters.ExcursionPartAdapter;
import conexion.Client;
import conexion.ClientProtocol;
import conexion.General;

public class ExcursionesPartActivity extends AppCompatActivity {

    ArrayList<Excursion>excursiones;

    /**
     * Este activity obtiene mediante una petición al servidor las excursiones en las que participa el usuario y las incluye en un ArrayList el cuál
     * utiliza el Adapter para mostrarlas en un listview. Desde esta activity también se permite que el cliente cancele su participación.
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_excursiones);
        excursiones=new ArrayList<>();
        ClientProtocol cpl=new ClientProtocol(Client.getSocket());
        JSONObject respuesta=null;
        try {
            respuesta = cpl.getActParticipa(General.usuario,"excursion");
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        JSONArray array= null;
        try {
            array = respuesta.getJSONArray("actividades");
            if(array.length()==0){
                Toast.makeText(ExcursionesPartActivity.this, "No participas en ninguna excursión todavía",
                        Toast.LENGTH_LONG).show();
            }
            for(int i=0; i<array.length(); i++){
                JSONObject json_data = array.getJSONObject(i);
                String nombre= json_data.getString("nombre");
                String fecha=json_data.getString("fecha");
                String descripcion=json_data.getString("descripcion");
                int id=json_data.getInt("id");
                int participantes=json_data.getInt("participantes");
                String escuela=json_data.getString("escuela");
                if(json_data.getInt("activa")!=1&&json_data.getInt("activaprog")!=1) {
                    Excursion ex = new Excursion(nombre, fecha, descripcion, id, participantes, escuela);
                    excursiones.add(ex);
                }


            }

            ExcursionPartAdapter adapter = new ExcursionPartAdapter(ExcursionesPartActivity.this, excursiones);

            ListView listView = (ListView) findViewById(R.id.listaexc);
            listView.setAdapter(adapter);



        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
