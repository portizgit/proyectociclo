package adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.pablo.surfschools.Colectiva;
import com.pablo.surfschools.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

import conexion.Client;
import conexion.ClientProtocol;
import conexion.General;

/**
 * Created by pablo on 01/06/2017.
 */

public class ColectivaAdapter extends ArrayAdapter<Colectiva> {

    Colectiva c;
    Button bverde;

    public ColectivaAdapter(Context context, ArrayList<Colectiva> users) {
        super(context, 0, users);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        c = getItem(position);

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_colectiva, parent, false);
        }


        TextView txNombre = (TextView) convertView.findViewById(R.id.txAsunto);

        txNombre.setText("NIVEL: "+c.getNivel());
        TextView txDesc = (TextView) convertView.findViewById(R.id.txDescrip);
        txDesc.setText(c.getDescripcion());
        TextView txFecha = (TextView) convertView.findViewById(R.id.txFecha);
        txFecha.setText(c.getFecha());
        TextView txPrecio = (TextView) convertView.findViewById(R.id.txPrecio);
        txPrecio.setText(c.getPrecio());

        bverde = (Button) convertView.findViewById(R.id.bApuntar);
        bverde.setTag(c.getId());

        bverde.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int id = (int) view.getTag();
                ClientProtocol cpl = new ClientProtocol(Client.getSocket());
                JSONObject respuesta = null;
                try {
                    respuesta = cpl.apuntarActividad(General.usuario, id);
                    if (respuesta.getString("resultado").equals("correcto")) {
                        Button b = (Button) view;
                        b.setText("apuntado");
                        b.setEnabled(false);
                     }else if(respuesta.getString("resultado").equals("maxpart")){
                    Toast.makeText(getContext(), "Número max de participantes alcanzado",
                            Toast.LENGTH_LONG).show();
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