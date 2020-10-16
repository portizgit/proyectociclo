package activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import conexion.Client;
import com.pablo.surfschools.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.concurrent.ExecutionException;

import conexion.ClientProtocol;

public class RegistroActivity extends AppCompatActivity {
    /**
     * Este activity gestiona el registro de los nuevos usuarios, recogiendo los datos introducidos por el usuario, validándolos y si
     * son correctos realizando una petición al servidor para darlos de alta si no existe aún ya ese usuario.
     */
    String susuario,scontraseña,snombre,stelefono,semail;
    EditText usuario,nombre,contraseña,email,telefono;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro);
        Button bRegistrarse = (Button) findViewById(R.id.bRegistrarse);
        Button bLimpiar = (Button) findViewById(R.id.bLimpiar);
        usuario=(EditText) findViewById(R.id.txUsuario);
        nombre=(EditText) findViewById(R.id.txAsunto);
        contraseña=(EditText) findViewById(R.id.txContraseña);
        email=(EditText) findViewById(R.id.txEmail);
        telefono=(EditText) findViewById(R.id.txMovil);
        bRegistrarse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                susuario=usuario.getText().toString();
                scontraseña=contraseña.getText().toString();
                snombre=nombre.getText().toString();
                semail=email.getText().toString();
                stelefono=telefono.getText().toString();
                if(susuario.equals("")||scontraseña.equals("")||snombre.equals("")||semail.equals("")||stelefono.equals("")){
                    new AlertDialog.Builder(RegistroActivity.this)
                            .setTitle("Registro")
                            .setMessage("No puede dejar ningún campo en blanco")
                            .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {

                                }
                            })
                            .setIcon(android.R.drawable.ic_dialog_alert)
                            .show();
                }else if((semail.indexOf("@")==-1)||(semail.indexOf(".com")==-1)){
                    new AlertDialog.Builder(RegistroActivity.this)
                            .setTitle("Registro")
                            .setMessage("Email no válido.")
                            .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {


                                }
                            })
                            .setIcon(android.R.drawable.ic_dialog_alert)
                            .show();
                }else {

                    ClientProtocol cpl = new ClientProtocol(Client.getSocket());
                    System.out.println("DESPUES CREACIÓN PROTOCOL");

                    JSONObject respuesta = null;


                    try {
                        respuesta = cpl.registro(susuario, scontraseña, snombre, semail, stelefono);
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
                        new AlertDialog.Builder(RegistroActivity.this)
                                .setTitle("Registro")
                                .setMessage("Registro correcto !")
                                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int which) {

                                        Intent mainIntent = new Intent().setClass(
                                                RegistroActivity.this, LoginActivity.class);


                                        startActivity(mainIntent);
                                    }
                                })
                                .setIcon(android.R.drawable.ic_dialog_alert)
                                .show();

                    } else {
                        new AlertDialog.Builder(RegistroActivity.this)
                                .setTitle("Registro")
                                .setMessage("Registro incorrecto ! Usuario ya usado")

                                .setIcon(android.R.drawable.ic_dialog_alert)
                                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int which) {
                                        // continue with delete
                                    }
                                })
                                .show();
                    }
                }

            }
        });

        bLimpiar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                usuario.setText("");
                contraseña.setText("");
                telefono.setText("");
                nombre.setText("");
                email.setText("");
            }
        });
    }
}
