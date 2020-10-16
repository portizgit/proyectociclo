package adapters;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.pablo.surfschools.Individual;
import com.pablo.surfschools.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

import conexion.Client;
import conexion.ClientProtocol;

/**
 * Created by pablo on 07/06/2017.
 */

public class IndividualAdapter extends ArrayAdapter<Individual> {

    String elemento;
    Individual ind;
    Button bverde,brojo;
    String estado;
    public IndividualAdapter(Context context, ArrayList<Individual> users) {
        super(context, 0, users);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ind = getItem(position);

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_alquiler, parent, false);
        }


            elemento="CLASE INDIVIDUAL";

        estado="Activo";
        bverde= (Button)convertView.findViewById(R.id.bRealizado);
        bverde.setTag(ind.getId());
        brojo=(Button)convertView.findViewById(R.id.bCancelar);
        brojo.setTag(ind.getId());
        if(ind.getAprobado()==0&&ind.getCancelado()!=1){
            estado="Pendiente de aprobación";
            elemento+="-PENDIENTE";
            bverde.setEnabled(false);
        }

        if(ind.getCancelado()!=0){
            convertView.setBackgroundColor(Color.RED);
            elemento+="-CANCELADO";
            estado="Cancelado";
            System.out.println(ind.getId());
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


        // Lookup view for data population
        TextView txAlquiler = (TextView)convertView.findViewById(R.id.txAsunto);

        txAlquiler.setText(ind.getCantidad()+" "+elemento);
        TextView txEsc = (TextView)convertView.findViewById(R.id.atxEscuela);
        TextView txV = (TextView)convertView.findViewById(R.id.textView8);
        txV.setText("Estado: ");
        TextView txTipo=(TextView)convertView.findViewById(R.id.txTipo);
        txTipo.setText(estado);
        txEsc.setText(ind.getNombre());
        TextView txFecha = (TextView)convertView.findViewById(R.id.txFecha);
        txFecha.setText(ind.getFecha());

        return convertView;
    }
}