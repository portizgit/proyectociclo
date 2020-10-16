package adapters;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import conexion.Client;
import com.pablo.surfschools.Mensaje;
import com.pablo.surfschools.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

import conexion.ClientProtocol;

/**
 * Created by pablo on 07/06/2017.
 */

public class MensajeAdapter extends ArrayAdapter<Mensaje> {

    Mensaje m;
    Button bverde;
    Context ctx;
    public MensajeAdapter(Context context, ArrayList<Mensaje> users) {
        super(context, 0, users);
        ctx=context;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        m = getItem(position);

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_mensaje, parent, false);
        }


        TextView txAsunto = (TextView)convertView.findViewById(R.id.txAsunto);

        txAsunto.setText(m.getAsunto());
        TextView txEsc = (TextView)convertView.findViewById(R.id.txEscuela);
        txEsc.setText(m.getEscuela());
        TextView txFecha = (TextView)convertView.findViewById(R.id.txFecha);
        txFecha.setText(m.getFecha());
        TextView txContenido = (TextView)convertView.findViewById(R.id.txContenido);
        txContenido.setText(m.getContenido());
        bverde=(Button)convertView.findViewById(R.id.bVisto);
        bverde.setTag(m.getId());
        if(m.getVisto()==1){
            bverde.setEnabled(false);
        }else{
            convertView.setBackgroundColor(Color.RED);
        }

        bverde.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int id=(int)view.getTag();
                ClientProtocol cpl=new ClientProtocol(Client.getSocket());
                JSONObject respuesta=null;
                try {
                    respuesta = cpl.setMensajeVisto(id);
                    if(respuesta.getString("resultado").equals("correcto")) {
                        Toast.makeText(ctx.getApplicationContext(), "Se ha marcado como visto",
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