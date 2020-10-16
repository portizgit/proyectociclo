package adapters;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.pablo.surfschools.Alquiler;
import com.pablo.surfschools.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

import conexion.Client;
import conexion.ClientProtocol;

/**
 * Created by pablo on 24/05/2017.
 */

public class AlquilerAdapter extends ArrayAdapter<Alquiler> {

    String elemento;
    Alquiler al;
    Button bverde,brojo;
    public AlquilerAdapter(Context context, ArrayList<Alquiler> users) {
        super(context, 0, users);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        al = getItem(position);

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_alquiler, parent, false);
        }

        if(al.getMaterial().equals("tabla surf")){
            elemento="TABLAS";
        }else if(al.getMaterial().equals("kayak")) {
            elemento = "KAYAK";
        }
        bverde= (Button)convertView.findViewById(R.id.bRealizado);
        bverde.setTag(al.getId());
        brojo=(Button)convertView.findViewById(R.id.bCancelar);
        brojo.setTag(al.getId());
        if(al.getAprobado()==0&&al.getCancelado()!=1){
            elemento+="-PENDIENTE";
            bverde.setEnabled(false);
        }

        if(al.getCancelado()!=0){
            convertView.setBackgroundColor(Color.RED);
            elemento+="-CANCELADO";
            System.out.println(al.getId());
            brojo.setEnabled(false);
            bverde.setText("Ok");
            bverde.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    ClientProtocol cpl=new ClientProtocol(Client.getSocket());
                    JSONObject respuesta=null;
                    try {
                        respuesta = cpl.setVisto((int)view.getTag());
                        if(respuesta.getString("resultado").equals("correcto")){
                            Button b=(Button)view;
                            b.setText("Visto");
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



        }else{
            bverde.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    ClientProtocol cpl=new ClientProtocol(Client.getSocket());
                    JSONObject respuesta=null;
                    try {
                        respuesta = cpl.setRealizado((int)view.getTag());
                        if(respuesta.getString("resultado").equals("correcto")){
                            Button b=(Button)view;
                            b.setEnabled(false);
                            b.setText("realizado");
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
        }

        brojo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ClientProtocol cpl=new ClientProtocol(Client.getSocket());
                JSONObject respuesta=null;
                try {
                    respuesta = cpl.setCancelado((int)view.getTag());
                    if(respuesta.getString("resultado").equals("correcto")){
                        Button b=(Button)view;
                        b.setText("Cancelado");
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

        if(al.getAprobado()==1 && al.getCancelado()!=1){
            bverde.setEnabled(true);
        }
        // Lookup view for data population
        TextView txAlquiler = (TextView)convertView.findViewById(R.id.txAsunto);
        TextView txTip=(TextView)convertView.findViewById(R.id.txTipo);
        txTip.setText(al.getTipo());
        txAlquiler.setText(al.getCantidad()+" "+ elemento);
        TextView txEsc = (TextView)convertView.findViewById(R.id.atxEscuela);
        txEsc.setText(al.getNombre());
        String fecha=al.getFecha();
        if(!al.getHora().equals("0")){
            fecha+=" "+al.getHora();
        }
        TextView txFecha = (TextView)convertView.findViewById(R.id.txFecha);
       txFecha.setText(fecha);

        return convertView;
    }
}