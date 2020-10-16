package activity;

import android.content.Intent;
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

import adapters.ColectivaAdapter;
import conexion.Client;
import conexion.ClientProtocol;
import conexion.General;

public class ColectivasEscuelaActivity extends AppCompatActivity {
    /**
     * Este activity muestra las clases colectivas que tiene programada cada escuela. Se muestra en el ámbito de cada escuela en particular.
     * Obtiene las clases mediante una petición al servidor, las guarda en un ArrayList que posteriormente le pasa al Adapter y este las muestra
     * en un listview
     */
    String nombreescuela;
    ArrayList<Colectiva>colectivas;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_colectivas_escuela);

        Intent intent = this.getIntent();
        nombreescuela=intent.getStringExtra("nombre");


        colectivas=new ArrayList<Colectiva>();
        ClientProtocol cpl=new ClientProtocol(Client.getSocket());
        JSONObject respuesta=null;
        try {
            respuesta = cpl.getActProgramadas(nombreescuela,"colectiva", General.usuario);
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        JSONArray array= null;
        try {
            array = respuesta.getJSONArray("actividades");

            int nescuelas=0;
            if(array.length()==0){
                Toast.makeText(ColectivasEscuelaActivity.this, "Esta escuela no tiene ninguna clase colectiva programada todavía",
                        Toast.LENGTH_LONG).show();
            }
            for(int i=0; i<array.length(); i++){
                nescuelas++;
                JSONObject json_data = array.getJSONObject(i);
                String precio= json_data.getString("precio");
                String fecha=json_data.getString("fecha");
                String descripcion=json_data.getString("descripcion");
                int id=json_data.getInt("id");
                String nivel=json_data.getString("nivel");
                if(json_data.getInt("activa")!=1 && json_data.getInt("activaprog")!=1) {
                    Colectiva c = new Colectiva(precio, descripcion, fecha, nivel, id);
                    colectivas.add(c);
                }

            }

            ColectivaAdapter adapterCol = new ColectivaAdapter(ColectivasEscuelaActivity.this, colectivas);

            ListView listColec = (ListView) findViewById(R.id.listacolectivas);
            listColec.setAdapter(adapterCol);



        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

}
