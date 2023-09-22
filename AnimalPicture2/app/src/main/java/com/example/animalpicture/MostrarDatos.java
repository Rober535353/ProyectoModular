package com.example.animalpicture;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

public class MostrarDatos extends AppCompatActivity {
    private TextView editAnimal;
    private TextView editNombre;
    private TextView editAlimentacion;
    private TextView editPeso;
    private TextView editAltura;
    private TextView editHabitat;
    private TextView editEspecie;
    private TextView editDato;
    final static String url = "https://animalpicture.000webhostapp.com/";
    RequestQueue requestQueue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mostrar_datos);

        editAnimal = (TextView)findViewById(R.id.textView8);
        String dato = getIntent().getStringExtra("nombre");
        editAnimal.setText(dato);
        editNombre = findViewById(R.id.textView10);
        editAlimentacion = findViewById(R.id.textView12);
        editPeso = findViewById(R.id.textView16);
        editAltura = findViewById(R.id.textView18);
        editHabitat = findViewById(R.id.textView20);
        editEspecie = findViewById(R.id.textView14);
        editDato = findViewById(R.id.textView22);

        requestQueue= Volley.newRequestQueue(this);
        buscar();
    }

    public void buscar(){
        String animal=editAnimal.getText().toString().trim();
        String buscar="buscar.php?animal="+animal;
        String urlBuscar=url+buscar;
        JsonObjectRequest jsonObjectRequest=new JsonObjectRequest(
                Request.Method.GET,
                urlBuscar,
                null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        String nombre;
                        String alimentacion;
                        String peso;
                        String altura;
                        String habitat;
                        String especie;
                        String dato;
                        try {
                            nombre=response.getString("nombreCientifico");
                            alimentacion=response.getString("alimentacion");
                            peso=response.getString("peso");
                            altura=response.getString("altura");
                            habitat=response.getString("habitat");
                            especie=response.getString("especie");
                            dato=response.getString("datoCurioso");

                            editNombre.setText(nombre);
                            editAlimentacion.setText(alimentacion);
                            editPeso.setText(peso);
                            editAltura.setText(altura);
                            editHabitat.setText(habitat);
                            editEspecie.setText(especie);
                            editDato.setText(dato);
                        }catch (JSONException e){
                            Toast.makeText(MostrarDatos.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                        }

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        editDato.setText(error.getMessage());
                    }
                }
        );
        requestQueue.add(jsonObjectRequest);
    }

    public void Home(View wiew){
        Intent home = new Intent(this, Inicio.class);
        startActivity(home);
    }
}