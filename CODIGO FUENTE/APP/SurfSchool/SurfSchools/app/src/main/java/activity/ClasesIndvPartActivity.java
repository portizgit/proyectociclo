package activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;
import android.widget.Toast;

import conexion.Client;
import com.pablo.surfschools.Individual;
import com.pablo.surfschools.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

import adapters.IndividualAdapter;
import conexion.ClientProtocol;
import conexion.General;

public class ClasesIndvPartActivity extends AppCompatActivity {
    ArrayList<Individual> clases;
    Individual obj;

    /**
     * Este activity muestra todas las clases individuales que tiene pendiente el cliente o aquellas que han sido canceladas pero no han sido marcadas
     * aún como vistas. Obtiene las clases mediante una petición al servidor, y las incluye en un ArrayList que posteriormente le pasa a un Adapter
     * que las muestra en un listView del layout del activity
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_clases_indv_part);

        ClientProtocol cpl=new ClientProtocol(Client.getSocket());
        JSONObject respuesta=null;
        clases=new ArrayList<Individual>();
        try{
            respuesta=cpl.getAlquileres(General.usuario,"clases");
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

        JSONArray array=null;
        try{
            array=respuesta.getJSONArray("alquileres");
            if(array.length()==0){
                Toast.makeText(ClasesIndvPartActivity.this, "No ha reservado ninguna clase",
                        Toast.LENGTH_LONG).show();
            }

            for(int i=0;i<array.length();i++){
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
                String descripcion=json_data.getString("descripcion");
                int realizado=json_data.getInt("realizado");
                if(visto!=1&&realizado!=1) {
                    Individual ind = new Individual(nombre, cantidad, fecha, hora, factura, tipo, id, aprobado, cancelado, visto,descripcion);
                    clases.add(ind);
                }
            }
            System.out.println(clases);
            IndividualAdapter adapter=new IndividualAdapter(ClasesIndvPartActivity.this,clases);
            ListView listInd=(ListView)findViewById(R.id.listviewindv);
            listInd.setAdapter(adapter);


        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
