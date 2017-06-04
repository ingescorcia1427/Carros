package com.example.cuc.carros;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.util.Base64;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Adapter;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

import com.example.cuc.carros.R;

import org.w3c.dom.Text;

import java.io.ByteArrayOutputStream;

public class AgregarCarro extends AppCompatActivity {
    private EditText cajaNchasis;
    private Spinner opc_marca, opc_color, opc_modelo;
    private boolean guardado;
    private TextInputLayout icajaNchasis, icajaMarca, icajaColor, icajaModelo;
    private String[] opmar, opcol, opmod;
    private Resources res;
    private ArrayAdapter adapter1, adapter2, adapter3;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agregar_carro);

        cajaNchasis = (EditText)findViewById(R.id.txtNchasis);

        opc_marca = (Spinner)findViewById(R.id.spnMarca);
        opmar = getResources().getStringArray(R.array.opcMarca);
        adapter1 = new ArrayAdapter(this, android.R.layout.simple_spinner_item, opmar);
        opc_marca.setAdapter(adapter1);

        opc_color = (Spinner)findViewById(R.id.spnColor);
        opcol = getResources().getStringArray(R.array.opcColor);
        adapter2 = new ArrayAdapter(this, android.R.layout.simple_spinner_item, opcol);
        opc_color.setAdapter(adapter2);

        opc_modelo = (Spinner)findViewById(R.id.spnModelo);
        opmod =getResources().getStringArray(R.array.opcModelo);
        adapter3 = new ArrayAdapter(this, android.R.layout.simple_spinner_item, opmod);
        opc_modelo.setAdapter(adapter3);


        icajaNchasis = (TextInputLayout)findViewById(R.id.numChasis);
        icajaMarca = (TextInputLayout)findViewById(R.id.marcaCarro);
        icajaColor = (TextInputLayout)findViewById(R.id.colorCarro);
        icajaModelo = (TextInputLayout)findViewById(R.id.modeloCarro);
        guardado = false;

        cajaNchasis.addTextChangedListener(new TextWatcherPersonalizado(icajaNchasis,getResources().getString(R.string.mensaje_error_nchasis)) {
            @Override
            public boolean estaVacio(Editable s) {
                if(TextUtils.isEmpty(s) && !guardado) return true;
                else return false;
            }
        });
    }

    public int fotoAleatoria(){
        int fotos[] = {R.drawable.img1, R.drawable.img2, R.drawable.img3, R.drawable.img4, R.drawable.img5};
        int numero = (int)(Math.random() * 5);
        return fotos[numero];
    }


    public void guardar(View v)  {
        String urlfoto, nchasis, marca, color, modelo, idfoto;
        Carro car;
        int foto;

        if(validarTodo()){
            nchasis = cajaNchasis.getText().toString();
            marca = opc_marca.getSelectedItem().toString().trim();
            color = opc_color.getSelectedItem().toString().trim();
            modelo = opc_modelo.getSelectedItem().toString().trim();

            foto = fotoAleatoria();
            Bitmap bitmap = BitmapFactory.decodeResource(this.getResources(),foto);
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.PNG,100,baos);
            byte[] imagenBytes = baos.toByteArray();
            urlfoto = Base64.encodeToString(imagenBytes,Base64.DEFAULT);
            try {
                baos.close();
            }catch (Exception e){

            }
            idfoto=String.valueOf(foto);
            car = new Carro(urlfoto,nchasis,marca,color,modelo,idfoto);
            car.guardar(getApplicationContext());

            InputMethodManager imp =(InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
            imp.hideSoftInputFromWindow(cajaNchasis.getWindowToken(),0);
            Snackbar.make(v,getResources().getString(R.string.mensaje_exitoso_guardar),Snackbar.LENGTH_SHORT).show();
            guardado= true;
            limpiar();
        }
    }

    public boolean validarTodo(){
        if(cajaNchasis.getText().toString().isEmpty()){
            icajaNchasis.setError(getResources().getString(R.string.mensaje_error_nchasis));
            cajaNchasis.requestFocus();
            return false;
        }
        return true;
    }

    public  void limpiar (){
        cajaNchasis.setText("");
        cajaNchasis.requestFocus();
        guardado = false;
    }

    public void onBackPressed(){
        finish();
        Intent i = new Intent(AgregarCarro.this,Principal.class);
        startActivity(i);
    }
}
