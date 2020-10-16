package adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import conexion.Client;
import com.pablo.surfschools.Colectiva;
import com.pablo.surfschools.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

import conexion.ClientProtocol;
import conexion.General;

/**
 * Created by pablo on 06/06/2017.
 */

public class ColectivaPartAdapter extends ArrayAdapter<Colectiva> {

    Colectiva c;
    Button brojo;
    Context ctx;
    public ColectivaPartAdapter(Context context, ArrayList<Colectiva> users) {
        super(context, 0, users);
        ctx=context;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        c = getItem(position);

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_excursion_part, parent, false);
        }


        TextView txNombre = (TextView)convertView.findViewById(R.id.txAsunto);

        txNombre.setText(c.getNivel());
        TextView txEsc = (TextView)convertView.findViewById(R.id.txEscuela);
        txEsc.setText(c.getEscuela());
        TextView txFecha = (TextView)convertView.findViewById(R.id.txFecha);
        txFecha.setText(c.getFecha());

        brojo=(Button)convertView.findViewById(R.id.bCancelar);
        brojo.setTag(c.getId());

        brojo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int id=(int)view.getTag();
                ClientProtocol cpl=new ClientProtocol(Client.getSocket());
                JSONObject respuesta=null;
                try {
                    respuesta = cpl.salirActividad(General.usuario,id);
                    if(respuesta.getString("resultado").equals("correcto")) {
                        Toast.makeText(ctx.getApplicationContext(), "Se ha cancelado tu participación",
                                Toast.LENGTH_LONG).show();
                        Button b=(Button)view;
                        b.setText("cancelado");
                        b.setEnabled(false);
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

        return convertView;
    }
}