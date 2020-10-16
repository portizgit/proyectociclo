package activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import conexion.Client;
import com.pablo.surfschools.Escuela;
import com.pablo.surfschools.MapsActivity;
import com.pablo.surfschools.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

import adapters.EscuelaAdapter;
import conexion.ClientProtocol;

import static conexion.General.usuario;

public class MisEscuelas extends AppCompatActivity {
    /**
     * Este es el activity principal de la app, en el podemos ver todas las escuelas a las que sigue el usuario y acceder a ellas.
     * También tiene un menú vertical en el que podemos acceder a las diferentes opciones de a la app (Mensajes, Alquileres, Configuración..)
     * Las escuelas las obtiene mediante una petición al servidor y las incluye en un ArrayList que mediante un Adapter se muestra en un ListView
     */
    ArrayList<Escuela>escuelas=new ArrayList<>();
    ArrayList<Escuela>escuelasSeguidor;
    TextView tNombre,txMsn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Intent intent = this.getIntent();
        Bundle bundle = intent.getExtras();
        escuelasSeguidor=(ArrayList<Escuela>) bundle.getSerializable("arraylist");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mis_escuelas);
        ClientProtocol cpl=new ClientProtocol(Client.getSocket());

        JSONObject respuesta= null;
        txMsn=(TextView) findViewById(R.id.txMsn);

        try {
            respuesta = cpl.getContadorMensajes(usuario);

            txMsn.setText(respuesta.getInt("contador")+"");

        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        if(escuelasSeguidor.size()==0){
            Toast.makeText(MisEscuelas.this, "Aún no sigue a ninguna escuela, utilice el mapa del menú superior para seguir a alguna",
                    Toast.LENGTH_LONG).show();
        }
        setTitle("Mis escuelas");
        EscuelaAdapter adapter = new EscuelaAdapter(getApplicationContext(), escuelasSeguidor);

        ListView listView = (ListView) findViewById(R.id.listViewEscuelas);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {


             String nombre= ((TextView)view.findViewById(R.id.txAsunto)).getText().toString();
                System.out.println("---"+nombre+"---");
                ClientProtocol cpl=new ClientProtocol(Client.getSocket());

                JSONObject respuesta= null;


                try {
                    respuesta = cpl.datosEscuela(nombre);

                    Intent mainIntent = new Intent().setClass(
                            MisEscuelas.this, EscuelaUsuario.class);
                    mainIntent.putExtra("json",respuesta.toString());


                    startActivity(mainIntent);
                } catch (ExecutionException e) {
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }


        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.menuescuelas,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        if (id == R.id.ver_mapa) {
            ClientProtocol cpl=new ClientProtocol(Client.getSocket());



            try {
                JSONObject respuesta = cpl.getEscuelas();
                JSONArray array= respuesta.getJSONArray("escuelas");
                for(int i=0; i<array.length(); i++){
                    JSONObject json_data = array.getJSONObject(i);
                    String nombre= json_data.getString("nombre");
                    String descripcion=json_data.getString("descripcion");
                    String latitud=json_data.getString("latitud");
                    String longitud=json_data.getString("longitud");
                    String direccion=json_data.getString("direccion");
                    Escuela e=new Escuela(nombre,descripcion,latitud,longitud,direccion);
                    escuelas.add(e);

                }

            } catch (ExecutionException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            }
            Intent mainIntent = new Intent().setClass(
                    MisEscuelas.this, MapsActivity.class);
            Bundle bundle = new Bundle();
            bundle.putSerializable("arraylist",escuelas);
            mainIntent.putExtras(bundle);

            startActivity(mainIntent);

        }

        if (id == R.id.cerrar_sesion) {

            new AlertDialog.Builder(MisEscuelas.this)
                    .setTitle("Cerrar sesión")
                    .setMessage("¿Esta seguro que desea cerrar sesión?")
                    .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {

                            Intent mainIntent = new Intent().setClass(
                                    MisEscuelas.this, LoginActivity.class);


                            startActivity(mainIntent);
                        }
                    })
                    .setNegativeButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {

                            Intent mainIntent = new Intent().setClass(
                                    MisEscuelas.this, LoginActivity.class);


                            startActivity(mainIntent);
                        }
                    })
                    .setIcon(android.R.drawable.ic_dialog_alert)
                    .show();


        }

        if (id == R.id.confperfil) {


            Intent mainIntent = new Intent().setClass(
                    MisEscuelas.this, ConfiguracionPerfil.class);


            startActivity(mainIntent);

        }

        if (id == R.id.misalquileres) {


            Intent mainIntent = new Intent().setClass(
                    MisEscuelas.this, Alquileres.class);


            startActivity(mainIntent);

        }

        if (id == R.id.misind) {


            Intent mainIntent = new Intent().setClass(
                    MisEscuelas.this, ClasesIndvPartActivity.class);


            startActivity(mainIntent);

        }

        if (id == R.id.mensajes) {


            Intent mainIntent = new Intent().setClass(
                    MisEscuelas.this, MensajesActivity.class);


            startActivity(mainIntent);

        }

        if (id == R.id.miscolectivas) {


            Intent mainIntent = new Intent().setClass(
                    MisEscuelas.this, ColectivasPartActivity.class);


            startActivity(mainIntent);

        }

        if (id == R.id.misexcursiones) {


            Intent mainIntent = new Intent().setClass(
                    MisEscuelas.this, ExcursionesPartActivity.class);


            startActivity(mainIntent);

        }



        if (id == R.id.actualizar) {
                    ClientProtocol cpl= new ClientProtocol(Client.getSocket());
            try {
                escuelasSeguidor=new ArrayList<>();
                JSONObject respuesta = cpl.getEscuelasSeguidor(usuario);
                JSONArray array= respuesta.getJSONArray("escuelas");
                if(array.length()==0){
                    Toast toast1 =
                            Toast.makeText(getApplicationContext(),
                                    "No tiene ninguna escuela todavía. Utilice el mapa para seguir a alguna", Toast.LENGTH_SHORT);

                    toast1.show();
                }
                for(int i=0; i<array.length(); i++){
                    JSONObject json_data = array.getJSONObject(i);
                    String nombre= json_data.getString("nombre");
                    String descripcion=json_data.getString("descripcion");
                    String latitud=json_data.getString("latitud");
                    String longitud=json_data.getString("longitud");
                    String direccion=json_data.getString("direccion");
                    int posibilidadsurfear=json_data.getInt("posibilidad");
                    Escuela e=new Escuela(nombre,descripcion,latitud,longitud,direccion,posibilidadsurfear);
                    escuelasSeguidor.add(e);
                    EscuelaAdapter adapter = new EscuelaAdapter(getApplicationContext(), escuelasSeguidor);

                    System.out.println("DENTRO DE ACTUALIZAR");
                    Intent mainIntent = new Intent().setClass(
                            MisEscuelas.this, MisEscuelas.class);
                    Bundle bundle = new Bundle();
                    bundle.putSerializable("arraylist",escuelasSeguidor);
                    mainIntent.putExtras(bundle);

                    startActivity(mainIntent);

                }

            } catch (ExecutionException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            }


        }

        return super.onOptionsItemSelected(item);
    }


}
