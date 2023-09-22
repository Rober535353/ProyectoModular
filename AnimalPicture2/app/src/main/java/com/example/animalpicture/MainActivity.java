package com.example.animalpicture;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.content.Intent;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        textView = findViewById(R.id.textView21);
    }

    public void Fotografia(View wiew){
        Intent fotografia = new Intent(this, EnviarImagen.class);
        textView.setText("fotografia");
        fotografia.putExtra("eleccion", textView.getText().toString());
        startActivity(fotografia);
    }
    public void Galeria(View wiew){
        Intent galeria = new Intent(this, EnviarImagen.class);
        textView.setText("galeria");
        galeria.putExtra("eleccion", textView.getText().toString());
        startActivity(galeria);
    }
    public void Volver(View wiew){
        Intent volver = new Intent(this, Inicio.class);
        startActivity(volver);
    }
}