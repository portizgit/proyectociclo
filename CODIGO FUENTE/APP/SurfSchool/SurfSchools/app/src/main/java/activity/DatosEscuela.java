package activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import conexion.Client;
import com.pablo.surfschools.Escuela;
import com.pablo.surfschools.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.concurrent.ExecutionException;

import conexion.ClientProtocol;
import conexion.General;

public class DatosEscuela extends AppCompatActivity {
    Escuela e;
    Escuela ef;
    @Override
    /**
     * Este activity se muestra cuando pulsamos en el marker de la escuela en el mapa que muestra todas las escuelas. Muestra la información de la escuela
     * que se obtiene mediante una consulta a la BD y un botón para seguir a la escuela y que aparezca luego en MisEscuelas.
     */
    protected void onCreate(Bundle savedInstanceState) {
        Intent intent = this.getIntent();
        Bundle bundle = intent.getExtras();
        e=(Escuela) bundle.getSerializable("escuela");
        ef=(Escuela) bundle.getSerializable("escuela2");
        System.out.println("NOMBRE: "+ef.getNombre());
        System.out.println(e);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_datos_escuela);

        Button bSeguir=(Button)findViewById(R.id.bSeguir);
        TextView txNombre= (TextView) findViewById(R.id.txAsunto);
        TextView txDescripcion= (TextView) findViewById(R.id.txDescripcion);
        TextView txDireccion= (TextView) findViewById(R.id.txDireccion);
        txNombre.setText(ef.getNombre());
        txDescripcion.setText(ef.getDescripcion());
        txDireccion.setText(ef.getDireccion());

        bSeguir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                ClientProtocol cpl = new ClientProtocol(Client.getSocket());

                JSONObject respuesta = null;


                try {
                    respuesta = cpl.seguirEscuela(General.usuario,ef.getNombre());
                } catch (ExecutionException e) {
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                System.out.println("VUELTA PETICION " + respuesta.toString());
                String srespuesta = null;
                try {
                    srespuesta = respuesta.getString("resultado");
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                if (srespuesta.equals("correcto")) {
                    new AlertDialog.Builder(DatosEscuela.this)
                            .setTitle("Seguir")
                            .setMessage("Añadida a tus escuelas !")
                            .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {

                                }
                            })
                            .setIcon(android.R.drawable.ic_dialog_alert)
                            .show();

                } else if(srespuesta.equals("yaexiste")){
                    new AlertDialog.Builder(DatosEscuela.this)
                            .setTitle("Seguir")
                            .setMessage("Esta escuela ya forma parte de sus escuelas")

                            .setIcon(android.R.drawable.ic_dialog_alert)
                            .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    // continue with delete
                                }
                            })
                            .show();
                }else{
                    new AlertDialog.Builder(DatosEscuela.this)
                            .setTitle("Seguir")
                            .setMessage("Error al seguir")

                            .setIcon(android.R.drawable.ic_dialog_alert)
                            .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    // continue with delete
                                }
                            })
                            .show();
                }

            }
        });
    }
}
