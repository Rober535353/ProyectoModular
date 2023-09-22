package com.example.animalpicture;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

public class Registrase extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registrase);
    }

    public void Registrarse(View wiew){
        Toast.makeText(this, "CUENTA CREADA CORRECTAMENTE", Toast.LENGTH_LONG).show();
        Intent registrarse = new Intent(this, IniciarSesion.class);
        startActivity(registrarse);
    }

    public void Volver(View wiew){
        Intent volver = new Intent(this, IniciarSesion.class);
        startActivity(volver);
    }
}