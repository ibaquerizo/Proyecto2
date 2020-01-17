package com.amst.proyecto;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class PantallaPrincipal extends AppCompatActivity {
    private Button usuario,conductor;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pantalla_principal);
        usuario=(Button)findViewById(R.id.button2);
        conductor=(Button)findViewById(R.id.button);

        usuario.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                startActivity(new Intent(PantallaPrincipal.this, MainActivityUsuario.class));
            }
        });
        conductor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(PantallaPrincipal.this, MainActivity.class));
            }
        });
    }
}
