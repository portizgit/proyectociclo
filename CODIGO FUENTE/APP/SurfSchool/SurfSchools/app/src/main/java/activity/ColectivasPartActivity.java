package activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;
import android.widget.Toast;

import com.pablo.surfschools.Colectiva;
import com.pablo.surfschools.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

import adapters.ColectivaPartAdapter;
import conexion.Client;
import conexion.ClientProtocol;
import conexion.General;

public class ColectivasPartActivity extends AppCompatActivity {
    /**
     * Este activity muestra las clases colectivas en las que participa el cliente sin depender de ninguna escuela. Las obtiene mediante
     * una petición al servidor y las guarda en un ArrayList que posteriormente las pasa al Adapter y se muestran en un listview.
     */
    ArrayList<Colectiva> colectivas;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_colectivas_part);
        colectivas=new ArrayList<>();
        setTitle("Clases colectivas");
        ClientProtocol cpl=new ClientProtocol(Client.getSocket());
        JSONObject respuesta=null;
        try {
            respuesta = cpl.getActParticipa(General.usuario,"colectiva");
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        JSONArray array= null;
        try {
            array = respuesta.getJSONArray("actividades");
            if(array.length()==0){
                Toast.makeText(ColectivasPartActivity.this, "No participas en ninguna clase colectiva todavía",
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
                String nivel=json_data.getString("nivel");
                if(json_data.getInt("activa")!=1&&json_data.getInt("activaprog")!=1) {
                    Colectiva c = new Colectiva(" ", descripcion, fecha, nivel,id, escuela);
                    colectivas.add(c);
                }


            }

            ColectivaPartAdapter adp = new ColectivaPartAdapter(ColectivasPartActivity.this, colectivas);

            ListView listV = (ListView) findViewById(R.id.listacolect);
            listV.setAdapter(adp);



        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
