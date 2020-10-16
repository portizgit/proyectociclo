package activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.pablo.surfschools.Escuela;
import com.pablo.surfschools.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

import adapters.EscuelaAdapter;
import conexion.Client;
import conexion.ClientProtocol;

import static conexion.General.usuario;

public class EscuelaUsuario extends AppCompatActivity {
    /**
     * Este activity gestiona la escuela que seleccione el usuario en la lista de sus escuelas, en este activity se gestiona el dejar de seguir,
     * y muestra los servicios que ofrece la escuela para poder acceder a ellos.
     * También muestra la posibilidad de surfear en esa escuela, la cuál cada escuela gestiona desde su aplicación escritorio.
     */
    String nombre;
    int posibilidadsurfear,colectivas,individuales,excursiones,kayak,surf;
    JSONObject respuesta;
    ArrayList<Escuela>escuelasSeguidor;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Intent intent = this.getIntent();
        String json=intent.getStringExtra("json");
        try {
            respuesta= new JSONObject(json);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        setContentView(R.layout.activity_escuela_usuario);
        try {
            nombre=respuesta.getString("nombre");
            posibilidadsurfear=respuesta.getInt("posibilidadsurfear");
            colectivas=respuesta.getInt("colectivas");
            individuales=respuesta.getInt("individuales");
            excursiones=respuesta.getInt("excursiones");
            kayak=respuesta.getInt("alquilerkayak");
            surf=respuesta.getInt("alquilersurf");
        } catch (JSONException e) {
            e.printStackTrace();
        }

        setTitle(nombre);
        TextView txpos=(TextView)findViewById(R.id.txPosibilidad);
        Button bkayak=(Button)findViewById(R.id.bKayak);
        Button bsurf=(Button)findViewById(R.id.bSurf);
        Button bexc=(Button)findViewById(R.id.bExc);
        Button bclases=(Button)findViewById(R.id.bClases);
        Button bunfollow=(Button)findViewById(R.id.bDejarSeguir);
        Button bindv=(Button)findViewById(R.id.bIndv);



        bsurf.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent mainIntent = new Intent().setClass(
                        EscuelaUsuario.this, AlquilerSurf.class);
                mainIntent.putExtra("nombre",nombre);


                startActivity(mainIntent);

            }
        });

        bkayak.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent mainIntent = new Intent().setClass(
                        EscuelaUsuario.this, AlquilerKayak.class);
                mainIntent.putExtra("nombre",nombre);


                startActivity(mainIntent);

            }
        });

        bindv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent mainIntent = new Intent().setClass(
                        EscuelaUsuario.this, ClasesIndvActivity.class);
                mainIntent.putExtra("nombre",nombre);


                startActivity(mainIntent);

            }
        });

        bexc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent mainIntent = new Intent().setClass(
                        EscuelaUsuario.this, ExcursionesActivity.class);
                mainIntent.putExtra("nombre",nombre);


                startActivity(mainIntent);

            }
        });

        bclases.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent mainIntent = new Intent().setClass(
                        EscuelaUsuario.this, ColectivasEscuelaActivity.class);
                mainIntent.putExtra("nombre",nombre);


                startActivity(mainIntent);

            }
        });

        bunfollow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                ClientProtocol cpl= new ClientProtocol(Client.getSocket());


                try {
                    escuelasSeguidor=new ArrayList<>();
                    JSONObject respuesta = cpl.unfollow(nombre,usuario);
                    if(respuesta.getString("resultado").equals("correcto")){
                        new AlertDialog.Builder(EscuelaUsuario.this)
                                .setTitle("Dejar de seguir")
                                .setMessage("Se ha eliminado de sus escuelas con éxito!")

                                .setIcon(android.R.drawable.ic_dialog_alert)
                                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int which) {
                                        // continue with delete
                                    }
                                })
                                .show();
                    }
                   respuesta = cpl.getEscuelasSeguidor(usuario);

                    JSONArray array= respuesta.getJSONArray("escuelas");
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
                                EscuelaUsuario.this, MisEscuelas.class);
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
        });

        if(posibilidadsurfear==1){
            txpos.setText("SE PUEDE SURFEAR");
            txpos.setTextColor(Color.parseColor("#00e676"));
        }
        else if(posibilidadsurfear==2){
            txpos.setText("NO SE PUEDE SURFEAR");
            txpos.setTextColor(Color.parseColor("#f44336"));

        }else{
            txpos.setText("NO DEFINIDO");
        }

        if(kayak==0) {
            bkayak.setEnabled(false);
        }
        if(surf==0) {
            bsurf.setEnabled(false);
        }
        if(excursiones==0) {
            bexc.setEnabled(false);
        }
        if(individuales==0) {
            bindv.setEnabled(false);
        }
        if(colectivas==0) {
            bclases.setEnabled(false);
        }
    }
}
