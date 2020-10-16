package com.pablo.surfschools;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;

import activity.DatosEscuela;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {
    /**
     * Este activity es el encargado de mostrar el mapa de Google y colocar los markers de las diferentes escuelas.
     */
    private GoogleMap mMap;
    public ArrayList<Escuela>escuelas;
    public Escuela e;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = this.getIntent();
        Bundle bundle = intent.getExtras();
        escuelas=(ArrayList<Escuela>) bundle.getSerializable("arraylist");
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        LatLng inicial = new LatLng(40.4168, -3.7038);

        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom((inicial), 5));
        LatLng latlong = new LatLng(-34, 151);
        Escuela e;
        Double latitud;
        Double longitud;
        for(int i=0;i<escuelas.size();i++){

            e=escuelas.get(i);
            latitud=Double.parseDouble(e.getLatitud());
            longitud=Double.parseDouble(e.getLongitud());
            latlong=new LatLng(latitud,longitud);
            mMap.addMarker(new MarkerOptions().position(latlong).title(e.nombre).snippet("Pulsa para ver más"));
        }

        mMap.moveCamera(CameraUpdateFactory.newLatLng(inicial));

        mMap.setOnInfoWindowClickListener(new GoogleMap.OnInfoWindowClickListener() {
            @Override
            public void onInfoWindowClick(Marker marker) {
                 Escuela ef;
                String nombre=marker.getTitle();
                for(int i=0;i<escuelas.size();i++){

                    ef=escuelas.get(i);
                    if(ef.getNombre().equals(nombre)){
                        Log.v("NOMBRE",ef.getNombre());
                        Intent mainIntent = new Intent().setClass(
                                MapsActivity.this, DatosEscuela.class);
                        Bundle bundle = new Bundle();
                        bundle.putSerializable("escuela2",ef);
                        System.out.println(ef);
                        mainIntent.putExtras(bundle);

                        startActivity(mainIntent);
                        break;
                    }
                }

            }

        });
    }
}
