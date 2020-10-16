package activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;

import com.pablo.surfschools.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.concurrent.ExecutionException;

import conexion.Client;
import conexion.ClientProtocol;

import static conexion.General.usuario;

public class ConfiguracionPerfil extends AppCompatActivity {
    /**
     * Este activity se encarga de la configuración del perfil, es decir, permite al usuario ver los datos de su perfil y modificarlos.
     */
    EditText etnombre,ettelefono,etemail,etcontraseña;
    CheckBox cbNombre, cbTelefono, cbEmail, cbContraseña;
    String nombre,telefono,contraseña,email;
    String contraseñaactual;
    Button bguardar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_configuracion_perfil);
        setTitle("Configuracion del perfil");
        etnombre=(EditText)findViewById(R.id.etNombre);
        ettelefono=(EditText)findViewById(R.id.etTelefono);
        etemail=(EditText)findViewById(R.id.etEmail);
        etcontraseña=(EditText)findViewById(R.id.etContraseña);

        cbNombre=(CheckBox)findViewById(R.id.cbNombre);
        cbTelefono=(CheckBox)findViewById(R.id.cbTelefono);
        cbEmail=(CheckBox)findViewById(R.id.cbEmail);
        cbContraseña=(CheckBox)findViewById(R.id.cbContraseña);

        bguardar=(Button)findViewById(R.id.bGuardar);

        etnombre.setEnabled(false);
        ettelefono.setEnabled(false);
        etemail.setEnabled(false);
        etcontraseña.setEnabled(false);

        ClientProtocol cpl=new ClientProtocol(Client.getSocket());

        JSONObject respuesta= null;


        try {
            respuesta = cpl.getDatos(usuario);

            etnombre.setText(respuesta.getString("nombre"));
            ettelefono.setText(respuesta.getString("telefono"));
            etemail.setText(respuesta.getString("email"));
            contraseñaactual=respuesta.getString("contrasena");

        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }

        cbNombre.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

                                              @Override
                                              public void onCheckedChanged(CompoundButton buttonView,boolean isChecked) {
                                                etnombre.setEnabled(true);
                                              }
                                          }
        );
        cbTelefono.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

                                                @Override
                                                public void onCheckedChanged(CompoundButton buttonView,boolean isChecked) {
                                                    ettelefono.setEnabled(true);
                                                }
                                            }
        );
        cbContraseña.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

                                                @Override
                                                public void onCheckedChanged(CompoundButton buttonView,boolean isChecked) {
                                                    etcontraseña.setEnabled(true);
                                                }
                                            }
        );
        cbEmail.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

                                                @Override
                                                public void onCheckedChanged(CompoundButton buttonView,boolean isChecked) {
                                                    etemail.setEnabled(true);
                                                }
                                            }
        );

        bguardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ClientProtocol cpl=new ClientProtocol(Client.getSocket());
                nombre=etnombre.getText().toString();
                telefono=ettelefono.getText().toString();
                email=etemail.getText().toString();
                contraseña=etcontraseña.getText().toString();

                if(nombre.equals("")||telefono.equals("")||email.equals("")){
                    new AlertDialog.Builder(ConfiguracionPerfil.this)
                            .setTitle("Error")
                            .setMessage("El único campo que puede dejar vacío es contraseña y se mantendrá su contraseña actual")

                            .setIcon(android.R.drawable.ic_dialog_alert)
                            .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    // continue with delete
                                }
                            })
                            .show();
                }else {

                    if(contraseña.equals("")){
                        contraseña=contraseñaactual;
                    }
                    JSONObject respuesta = null;
                    try {
                        respuesta = cpl.actualizaUsuario(usuario, nombre, telefono, email, contraseña);
                        if (respuesta.getString("resultado").equals("correcto")) {
                            new AlertDialog.Builder(ConfiguracionPerfil.this)
                                    .setTitle("Actualizado correctamente")
                                    .setMessage("Actualizado correctamente")

                                    .setIcon(android.R.drawable.ic_dialog_alert)
                                    .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int which) {

                                        }
                                    })
                                    .show();
                        }

                    } catch (ExecutionException e) {
                        e.printStackTrace();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }

            }
        });


    }
}
