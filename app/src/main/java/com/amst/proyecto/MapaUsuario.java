package com.amst.proyecto;

import androidx.fragment.app.FragmentActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.UiSettings;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MapaUsuario extends FragmentActivity implements OnMapReadyCallback {
    private Button btncerrar;
    private GoogleMap mMap;
    private FirebaseAuth mAuth;
    DatabaseReference db_reference;
    Double latVal;
    Double longVal;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mapa_usuario);

        mAuth= FirebaseAuth.getInstance();
        btncerrar= (Button) findViewById(R.id.btncerrar);
        btncerrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mAuth.signOut();
                startActivity(new Intent(MapaUsuario.this, MainActivity.class));
                finish();
            }
        });
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        db_reference = FirebaseDatabase.getInstance().getReference().child("Dispotivo");
        leerRegistros();
    }

    private void leerRegistros() {
        db_reference.addValueEventListener(new ValueEventListener() { @Override
        public void onDataChange(DataSnapshot dataSnapshot) {
            for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                mostrarRegistrosPorPantalla(snapshot);
            }
        }
            @Override
            public void onCancelled(DatabaseError error) { System.out.println(error.toException());
            } });
    }



    public void mostrarRegistrosPorPantalla(DataSnapshot snapshot){
        String latVal1 = String.valueOf(snapshot.child("Latitud").getValue());
        String longVal1 = String.valueOf(snapshot.child("Longitud").getValue());
        latVal=  Double.parseDouble(latVal1);
        longVal= Double.parseDouble(longVal1);

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
        mMap.setMapType(GoogleMap.MAP_TYPE_SATELLITE);
        UiSettings uiSettings= mMap.getUiSettings();
        uiSettings.setZoomControlsEnabled(true);
        // Add a marker in Sydney and move the camera

        LatLng sydney = new LatLng(latVal, longVal);
        mMap.addMarker(new MarkerOptions().position(sydney).title("Marker"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
    }
}
