package com.example.animalpicture;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class IniciarSesion extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_iniciar_sesion);
    }

    public void Ingresar(View wiew){
        Intent ingresar = new Intent(this, Inicio.class);
        startActivity(ingresar);
    }
    public void Registrarse(View wiew){
        Intent registrarse = new Intent(this, Registrase.class);
        startActivity(registrarse);
    }
}