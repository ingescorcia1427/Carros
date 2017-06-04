package com.example.cuc.carros;

import android.content.Intent;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.cuc.carros.R;
import com.squareup.picasso.Picasso;

public class DetalleCarro extends AppCompatActivity {
    private CollapsingToolbarLayout collapsingToolbarLayout;
    private TextView tviewNchasis, tviewColor, tviewModelo;
    private Carro car;
    private String nchasis, marca,color, modelo, urlfoto;
    private Bundle b;
    private Intent i;
    private ImageView foto;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalle_carro);

        Toolbar toolbar= (Toolbar)findViewById(R.id.toolbar2);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDefaultDisplayHomeAsUpEnabled(true);

        tviewNchasis = (TextView)findViewById(R.id.txviewNchasis);
        tviewColor = (TextView)findViewById(R.id.txviewColor);
        tviewModelo = (TextView)findViewById(R.id.txviewModelo);

        i = getIntent();
        b=i.getBundleExtra("datos");
        nchasis = b.getString("nchasis");
        marca = b.getString("marca");
        color = b.getString("color");
        modelo = b.getString("modelo");
        urlfoto = b.getString("urlfoto");

        collapsingToolbarLayout = (CollapsingToolbarLayout)findViewById(R.id.collapsing_toolbar);
        foto = (ImageView)findViewById(R.id.fotoCarro);

        Picasso.with(getApplicationContext()).load(urlfoto).into(foto);
        collapsingToolbarLayout.setTitle(marca);
        tviewNchasis.setText(getResources().getString(R.string.nchasis) + ":  " + nchasis);
        tviewColor.setText(getResources().getString(R.string.color) + ":  " + color);
        tviewModelo.setText(getResources().getString(R.string.modelo) + ":  " +  modelo);
    }
}
