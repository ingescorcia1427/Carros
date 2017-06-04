package com.example.cuc.carros;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;

import java.util.ArrayList;

public class Principal extends AppCompatActivity implements AdaptadorCarro.OnCarroClickListener{
    private RecyclerView listado;
    private ArrayList<Carro> carros;
    private AdaptadorCarro adapter;
    private LinearLayoutManager llm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_principal);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        listado = (RecyclerView)findViewById(R.id.lstOpciones);

        carros = Datos.traerCarros(getApplicationContext());
        adapter = new AdaptadorCarro(carros, this);

        llm = new LinearLayoutManager(this);
        llm.setOrientation(LinearLayoutManager.VERTICAL);

        listado.setLayoutManager(llm);
        listado.setAdapter(adapter);

    }


    public void agregar(View v){
        finish();
        Intent i = new Intent(Principal.this, AgregarCarro.class);
        startActivity(i);
    }


    @Override
    public void onCarroClick(Carro car) {
        Intent i = new Intent(Principal.this,DetalleCarro.class);
        Bundle b = new Bundle();
        b.putString("marca",car.getMarca());
        b.putString("color",car.getColor());
        b.putString("modelo",car.getModelo());
        b.putString("urlfoto",car.getUrlfoto());
        i.putExtra("datos",b);
        startActivity(i);
    }
}
