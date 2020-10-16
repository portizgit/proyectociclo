package adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.pablo.surfschools.Excursion;
import com.pablo.surfschools.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

import conexion.Client;
import conexion.ClientProtocol;
import conexion.General;

/**
 * Created by pablo on 25/05/2017.
 */


public class ExcursionAdapter extends ArrayAdapter<Excursion> {

    Excursion ex;
    Button bverde;
    Context ctx;
    public ExcursionAdapter(Context context, ArrayList<Excursion> users) {

        super(context, 0, users);
        this.ctx=context;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ex = getItem(position);

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_excursion, parent, false);
        }


        TextView txNombre = (TextView)convertView.findViewById(R.id.txAsunto);

        txNombre.setText(ex.getNombre());
        TextView txDesc = (TextView)convertView.findViewById(R.id.txDescrip);
        txDesc.setText(ex.getDescripcion());
        TextView txFecha = (TextView)convertView.findViewById(R.id.txFecha);
        txFecha.setText(ex.getFecha());

        bverde=(Button)convertView.findViewById(R.id.bApuntar);
        bverde.setTag(ex.getId());

        bverde.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int id=(int)view.getTag();
                ClientProtocol cpl=new ClientProtocol(Client.getSocket());
                JSONObject respuesta=null;
                try {
                    respuesta = cpl.apuntarActividad(General.usuario,id);
                    if(respuesta.getString("resultado").equals("correcto")) {
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