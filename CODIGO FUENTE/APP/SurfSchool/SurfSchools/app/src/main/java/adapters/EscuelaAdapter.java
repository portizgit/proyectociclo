package adapters;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.pablo.surfschools.Escuela;
import com.pablo.surfschools.R;

import java.util.ArrayList;

/**
 * Created by Pablo on 04/05/2017.
 */

public class EscuelaAdapter extends ArrayAdapter<Escuela> {
    public EscuelaAdapter(Context context, ArrayList<Escuela> users) {
        super(context, 0, users);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        Escuela escuela = getItem(position);

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_escuela, parent, false);
        }
        // Lookup view for data population
        TextView txNombre = (TextView)convertView.findViewById(R.id.txAsunto);
        txNombre.setText(escuela.getNombre());
        TextView txDirecc = (TextView)convertView.findViewById(R.id.txDireccion);
        txDirecc.setText(escuela.getDireccion());
        TextView txPos = (TextView)convertView.findViewById(R.id.txSurfear);
        int posibilidad=escuela.getPosibilidadSurf();

        if(posibilidad==2){
            txPos.setTextColor(Color.parseColor("#f44336"));
            txPos.setText("NO");
        }else if(posibilidad==1){
            txPos.setTextColor(Color.parseColor("#00e676"));
            txPos.setText("SI");
        }else{
            txPos.setText("NO DEFINIDO");
        }

        return convertView;
    }
}