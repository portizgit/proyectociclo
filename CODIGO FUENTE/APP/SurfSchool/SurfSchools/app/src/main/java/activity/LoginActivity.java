package activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.EditorInfo;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import conexion.Client;
import com.pablo.surfschools.Escuela;
import com.pablo.surfschools.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

import conexion.ClientProtocol;
import conexion.General;

/**
 * A login screen that offers login via email/password.
 */
public class LoginActivity extends AppCompatActivity  {

    /**
     * Este activity es el primero que se ejecuta al inicio despues del SplashCreen y es en el que el cliente hace login, y también tiene la
     * posibilidad de acceder al registro o cambiar el servidor.
     */
    private AutoCompleteTextView mUserView;
    private EditText mPasswordView;
    private View mProgressView;
    private View mLoginFormView;
    private String usuario;
    private String contraseña;
    ArrayList<Escuela> escuelas;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // Set up the login form.
        mUserView = (AutoCompleteTextView) findViewById(R.id.email);
        Client cl=new Client(getApplicationContext());

           cl.execute();

        mPasswordView = (EditText) findViewById(R.id.password);
        mPasswordView.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int id, KeyEvent keyEvent) {
                if (id == R.id.login || id == EditorInfo.IME_NULL) {

                    return true;
                }
                return false;
            }
        });

        Button mEmailSignInButton = (Button) findViewById(R.id.email_sign_in_button);
        mEmailSignInButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                String usuario=mUserView.getText().toString();

                String contraseña = String.valueOf(mPasswordView.getText());

                ClientProtocol cpl=new ClientProtocol(Client.getSocket());
                System.out.println("DESPUES CREACIÓN PROTOCOL");

                JSONObject respuesta= null;


                try {
                    respuesta = cpl.login(usuario,contraseña);
                } catch (ExecutionException e) {
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                System.out.println("VUELTA PETICION "+respuesta.toString());
                String srespuesta= null;
                try {
                    srespuesta = respuesta.getString("resultado");
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                if (srespuesta.equals("correcto")) {
                    General.usuario=usuario;


                    try {
                        escuelas=new ArrayList<Escuela>();
                         respuesta = cpl.getEscuelasSeguidor(General.usuario);
                        JSONArray array= respuesta.getJSONArray("escuelas");
                        int nescuelas=0;
                        for(int i=0; i<array.length(); i++){
                            nescuelas++;
                            JSONObject json_data = array.getJSONObject(i);
                            String nombre= json_data.getString("nombre");
                            String descripcion=json_data.getString("descripcion");
                            String latitud=json_data.getString("latitud");
                            String longitud=json_data.getString("longitud");
                            String direccion=json_data.getString("direccion");
                            int posibilidadsurfear=json_data.getInt("posibilidad");
                            Escuela e=new Escuela(nombre,descripcion,latitud,longitud,direccion,posibilidadsurfear);
                            escuelas.add(e);

                        }

                        if(nescuelas==0){
                            Toast toast1 =
                                    Toast.makeText(getApplicationContext(),
                                            "No tiene ninguna escuela todavía. Utilice el mapa para seguir a alguna", Toast.LENGTH_SHORT);

                            toast1.show();
                        }

                    } catch (ExecutionException e) {
                        e.printStackTrace();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    Intent mainIntent = new Intent().setClass(
                            LoginActivity.this, MisEscuelas.class);
                    Bundle bundle = new Bundle();
                    bundle.putSerializable("arraylist",escuelas);
                    mainIntent.putExtras(bundle);

                    startActivity(mainIntent);



                }else{
                    new AlertDialog.Builder(LoginActivity.this)
                            .setTitle("Inicio de sesión")
                            .setMessage("Inicio de sesión incorrecto !")

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
        );

        Button bRegistrarse = (Button) findViewById(R.id.bRegistro);
        bRegistrarse.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent mainIntent = new Intent().setClass(
                        LoginActivity.this, RegistroActivity.class);


                startActivity(mainIntent);

            }
        });

        mLoginFormView = findViewById(R.id.login_form);
        mProgressView = findViewById(R.id.login_progress);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.menulogin,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        if (id == R.id.servidor) {

            Intent mainIntent = new Intent().setClass(
                    LoginActivity.this, CambioServidor.class);
            Bundle bundle = new Bundle();
            bundle.putSerializable("arraylist",escuelas);
            mainIntent.putExtras(bundle);

            startActivity(mainIntent);

        }





        return super.onOptionsItemSelected(item);
    }

}


