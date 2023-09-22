package com.example.animalpicture;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity2 extends AppCompatActivity {

    private EditText editText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        editText = findViewById(R.id.editTextTextPersonName5);
    }

    public void Volver(View wiew) {
        Intent volver = new Intent(this, Inicio.class);
        startActivity(volver);
    }

    public void Buscar(View wiew){
        Intent buscar = new Intent(this, MostrarDatos.class);
        buscar.putExtra("nombre", editText.getText().toString());
        startActivity(buscar);
    }
}