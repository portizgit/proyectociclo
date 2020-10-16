package activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
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

public class AlquilerKayak extends AppCompatActivity {
    String nombreescuela;
    int preciohora;
    int preciodia;
    String fecha;
    String hora="0";
    String factura;
    String usuario;
    int cantidad=0;
    EditText ttablas,thoras,txfecha;
    CheckBox cbhora;
    TextView ttotal,thora;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = this.getIntent();
        nombreescuela=intent.getStringExtra("nombre");
        setContentView(R.layout.activity_alquiler_kayak);
        setTitle("Alquiler de kayaks");



        ClientProtocol cpl = new ClientProtocol(Client.getSocket());
        boolean correcto=true;
        JSONObject respuesta=null;
        try {
            respuesta = cpl.getPrecios(nombreescuela);
            String shora=respuesta.getString("kayakhora");
            String sdia=respuesta.getString("kayakdia");
            if(shora.equals("0")||sdia.equals("0")){
                Toast.makeText(AlquilerKayak.this, "Esta escuela no ha definido aún los precios para el alquiler de kayaks",
                        Toast.LENGTH_LONG).show();

                correcto=false;

            }


        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        if(correcto) {


            thora =   (TextView) findViewById(R.id.txPrecio);

            thoras = (EditText) findViewById(R.id.ktxHour);
            thoras.setEnabled(false);
            txfecha = (EditText) findViewById(R.id.txFecha);
            TextView tdia = (TextView) findViewById(R.id.ktxDia);

            ttotal = (TextView) findViewById(R.id.ktxTotal);
            ttablas = (EditText) findViewById(R.id.ktxTablas);
            cbhora = (CheckBox) findViewById(R.id.kcbHora);
            Button balquilar = (Button) findViewById(R.id.kbalquilar);
            try {
                preciohora = Integer.parseInt(respuesta.getString("kayakhora"));

                preciodia = Integer.parseInt(respuesta.getString("kayakdia"));
                thora.setText(preciohora + " €");
                tdia.setText(preciodia + " €");
            } catch (JSONException e) {
                e.printStackTrace();
            }

            balquilar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    factura = ttotal.getText().toString();
                    fecha = txfecha.getText().toString();

                    usuario = General.usuario;

                    if (fecha.equals("") || factura.equals("") || cantidad == 0 || fecha.equals("DD/MM/AAAA")) {
                        new AlertDialog.Builder(AlquilerKayak.this)

                                .setTitle("Error")
                                .setMessage("No puede dejar ningún campo vacío")
                                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int which) {


                                    }
                                })
                                .setIcon(android.R.drawable.ic_dialog_alert)
                                .show();

                    } else if (!isFechaValida(fecha)) {
                        new AlertDialog.Builder(AlquilerKayak.this)

                                .setTitle("Error")
                                .setMessage("La fecha introducida no tiene el formato correcto")
                                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int which) {


                                    }
                                })
                                .setIcon(android.R.drawable.ic_dialog_alert)
                                .show();
                    } else if (cbhora.isChecked()) {
                        System.out.println("+"+thoras.getText().toString()+"*");
                        if (thoras.getText().toString().equals("") || thoras.getText().toString().equals("Hour (16:00)")) {
                            new AlertDialog.Builder(AlquilerKayak.this)

                                    .setTitle("Error")
                                    .setMessage("Si selecciona el alquiler por horas, no puede dejar el campo de hora vacío")
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
                                    new AlertDialog.Builder(AlquilerKayak.this)

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
                                    respuesta = cpl.newAlquiler(fecha, cantidad, factura, usuario, "kayak", thora.getText().toString(), nombreescuela, "");
                                } catch (ExecutionException e) {
                                    e.printStackTrace();
                                } catch (InterruptedException e) {
                                    e.printStackTrace();
                                }
                                try {
                                    if (respuesta.getString("resultado").equals("correcto")) {
                                        new AlertDialog.Builder(AlquilerKayak.this)

                                                .setTitle("Alquiler")
                                                .setMessage("Alquiler reservado correctamente")
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
                    } else { boolean correcto = true;
                        SimpleDateFormat formatoFecha = new SimpleDateFormat("dd-MM-yyy", Locale.getDefault());
                        try {
                            Date fechaprg = formatoFecha.parse(fecha);
                            String factual = formatoFecha.format(new Date());
                            Date actual = formatoFecha.parse(factual);
                            if (fechaprg.before(actual)) {
                                correcto = false;
                                new AlertDialog.Builder(AlquilerKayak.this)

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
                                respuesta = cpl.newAlquiler(fecha, cantidad, factura, usuario, "kayak", hora, nombreescuela, "");
                            } catch (ExecutionException e) {
                                e.printStackTrace();
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                            try {
                                if (respuesta.getString("resultado").equals("correcto")) {
                                    new AlertDialog.Builder(AlquilerKayak.this)

                                            .setTitle("Alquiler")
                                            .setMessage("Alquiler reservado correctamente")
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

            cbhora.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

                                                  @Override
                                                  public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                                                      if (isChecked) {
                                                          thoras.setEnabled(true);

                                                      } else {
                                                          thoras.setEnabled(false);
                                                      }
                                                  }
                                              }
            );
            cantidad = 0;


            ttablas.addTextChangedListener(new TextWatcher() {

                public void afterTextChanged(Editable s) {
                    if (!ttablas.getText().toString().equals("")) {
                        cantidad = Integer.parseInt(ttablas.getText().toString());
                        if (cbhora.isChecked()) {
                            ttotal.setText((cantidad * preciohora) + " €");
                        } else {
                            ttotal.setText((cantidad * preciodia) + " €");
                        }
                    }

                }

                public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                }

                public void onTextChanged(CharSequence s, int start, int before, int count) {
                }
            });
        }else{
            Button balquilar = (Button) findViewById(R.id.kbalquilar);
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