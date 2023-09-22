package com.example.animalpicture;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class Inicio extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inicio);
    }

    public void Manual(View wiew){
        Intent manual = new Intent(this, MainActivity2.class);
        startActivity(manual);
    }

    public void IA(View wiew) {
        Intent ia = new Intent(this, MainActivity.class);
        startActivity(ia);
    }
}