package activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.pablo.surfschools.R;

import conexion.Client;

public class CambioServidor extends AppCompatActivity {
    /**
     * Este activity es el que se encarga del cambio de servidor, opción que se encuentra en el login de la APP, se pide una ip y puerto nuevo
     * y se reinicia la aplicación con los nuevos parámetros
     */
    EditText txIp;
    EditText txPort;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cambio_servidor);
        setTitle("Cambiar servidor");

        txIp=(EditText)findViewById(R.id.txIP);
        txPort=(EditText)findViewById(R.id.txPORT);
        txIp.setText(Client.ip);
        txPort.setText(Client.port+"");

        Button bReiniciar=(Button)findViewById(R.id.bReiniciar);
        String ip;
        String port;
        bReiniciar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Client.ip=txIp.getText().toString();
                String port=txPort.getText().toString();
                Client.port=Integer.parseInt(port);

                Intent mainIntent = new Intent().setClass(
                        CambioServidor.this, LoginActivity.class);

                startActivity(mainIntent);
            }
        });
    }
}
