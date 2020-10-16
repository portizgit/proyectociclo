package activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.pablo.surfschools.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.concurrent.ExecutionException;

import conexion.Client;
import conexion.ClientProtocol;
import conexion.General;

public class ClasesIndvActivity extends AppCompatActivity {
String nombreescuela;
    int precio;
    String fecha;
    String hora="0";
    String factura;
    String notas;
    int cantidad=1;
    EditText tcantidad,tfecha,tnotas;
    Button bReserva;
    TextView tprecio;
    String usuario;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        /**
         * Este activity se utiliza para que el cliente reserve una clase individual en una determinada escuela, tiene que rellenar los datos necesarios,
         * que serán validados una vez decida pulsar reservar, y que si son correctos se relizará una petición al servidor.
         *
         */
        super.onCreate(savedInstanceState);
        Intent intent=this.getIntent();
        nombreescuela=intent.getStringExtra("nombre");
        setContentView(R.layout.activity_clases_indv);
        setTitle("Clases individuales");

        ClientProtocol cpl=new ClientProtocol(Client.getSocket());
        boolean correcto=true;
        JSONObject respuesta=null;
        try{
            respuesta=cpl.getPrecios(nombreescuela);
            String sprecio=respuesta.getString("pclases");
            if(sprecio.equals("0")){
                Toast.makeText(ClasesIndvActivity.this, "Esta escuela no ha definido aún los precios para las clases individuales",
                        Toast.LENGTH_LONG).show();

                correcto=false;
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }

        if(correcto){


            tprecio=(TextView) findViewById(R.id.txPrecio);
            tfecha=(EditText)findViewById(R.id.txFecha);
            tnotas=(EditText)findViewById(R.id.txNotas);
            bReserva=(Button)findViewById(R.id.bReservar);
            tcantidad=(EditText)findViewById(R.id.txCantidad);

            try{
                precio=Integer.parseInt(respuesta.getString("pclases"));
                tprecio.setText(precio+" €");
            } catch (JSONException e) {
                e.printStackTrace();
            }

            bReserva.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    cantidad=Integer.parseInt(tcantidad.getText().toString());
                    factura = (cantidad*precio)+"";
                    fecha = tfecha.getText().toString();
                    notas= tnotas.getText().toString();
                    usuario = General.usuario;

                    if (fecha.equals("") || tcantidad.toString().equals("") || fecha.equals("DD/MM/AAAA")) {
                        new AlertDialog.Builder(ClasesIndvActivity.this)

                                .setTitle("Error")
                                .setMessage("No puede dejar ningún campo vacío")
                                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int which) {


                                    }
                                })
                                .setIcon(android.R.drawable.ic_dialog_alert)
                                .show();

                    } else if (!isFechaValida(fecha)) {
                        new AlertDialog.Builder(ClasesIndvActivity.this)

                                .setTitle("Error")
                                .setMessage("La fecha introducida no tiene el formato correcto")
                                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int which) {


                                    }
                                })
                                .setIcon(android.R.drawable.ic_dialog_alert)
                                .show();

                        } else {

                        boolean correcto = true;
                        SimpleDateFormat formatoFecha = new SimpleDateFormat("dd-MM-yyy", Locale.getDefault());
                        try {
                            Date fechaprg = formatoFecha.parse(fecha);
                            String factual = formatoFecha.format(new Date());
                            Date actual = formatoFecha.parse(factual);
                            if (fechaprg.before(actual)) {
                                correcto = false;
                                new AlertDialog.Builder(ClasesIndvActivity.this)

                                        .setTitle("Error")
                                        .setMessage("La fecha introducida es anterior a la actual")
                                        .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                                            public void onClick(DialogInterface dialog, int which) {


                                            }
                                        })
                                        .setIcon(android.R.drawable.ic_dialog_alert)
                                        .show();
                            }
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }

                        if (correcto) {
                            ClientProtocol cpl = new ClientProtocol(Client.getSocket());

                            JSONObject respuesta = null;


                            try {
                                respuesta = cpl.newAlquiler(fecha, cantidad, factura, usuario, "clases", hora, nombreescuela, notas);
                            } catch (ExecutionException e) {
                                e.printStackTrace();
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                            try {
                                if (respuesta.getString("resultado").equals("correcto")) {
                                    new AlertDialog.Builder(ClasesIndvActivity.this)

                                            .setTitle("Clase")
                                            .setMessage("Clase reservada correctamente")
                                            .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                                                public void onClick(DialogInterface dialog, int which) {


                                                }
                                            })
                                            .setIcon(android.R.drawable.ic_dialog_alert)
                                            .show();
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

                        }

                    }
                }
            });

        }else{
            Button balquilar = (Button) findViewById(R.id.bReservar);
            balquilar.setEnabled(false);
        }
    }
    public static boolean isFechaValida(String fecha) {
        try {
            SimpleDateFormat formatoFecha = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault());
            formatoFecha.setLenient(false);
            formatoFecha.parse(fecha);
        } catch (ParseException e) {
            return false;
        }
        return true;
    }
}
