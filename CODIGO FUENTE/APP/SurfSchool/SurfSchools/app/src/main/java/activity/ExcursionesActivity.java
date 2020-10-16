package activity;

import android.content.Intent;
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

import adapters.ExcursionAdapter;
import conexion.Client;
import conexion.ClientProtocol;
import conexion.General;

public class ExcursionesActivity extends AppCompatActivity {
    String nombreescuela;
    ArrayList<Excursion> excursiones;
    Excursion ex;

    /**
     * Este activity muestra las excursiones que tiene programada una escuela determinada, permitiendo al usuario apuntarse a ellas.
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_excursiones);
        Intent intent = this.getIntent();
        nombreescuela=intent.getStringExtra("nombre");


        excursiones=new ArrayList<Excursion>();
        ClientProtocol cpl=new ClientProtocol(Client.getSocket());
        JSONObject respuesta=null;
        try {
            respuesta = cpl.getActProgramadas(nombreescuela,"excursion",General.usuario);
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        JSONArray array= null;
        try {
            array = respuesta.getJSONArray("actividades");
            if(array.length()==0){
                Toast.makeText(ExcursionesActivity.this, "Esta escuela no tiene todavía ninguna excursión",
                        Toast.LENGTH_LONG).show();
            }
            for(int i=0; i<array.length(); i++){
                JSONObject json_data = array.getJSONObject(i);
                String nombre= json_data.getString("nombre");
                String fecha=json_data.getString("fecha");
                String descripcion=json_data.getString("descripcion");
                int id=json_data.getInt("id");
                int participantes=json_data.getInt("participantes");

                if(json_data.getInt("activa")!=1 && json_data.getInt("activaprog")!=1) {
                    Excursion ex = new Excursion(nombre, fecha, descripcion, id, participantes);
                    excursiones.add(ex);
                }

            }

            ExcursionAdapter adapter = new ExcursionAdapter(getApplicationContext(), excursiones);

            ListView listView = (ListView) findViewById(R.id.listaexc);
            listView.setAdapter(adapter);



        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
