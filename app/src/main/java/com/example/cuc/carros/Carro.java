package com.example.cuc.carros;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.UUID;

/**
 * Created by CUC on 3/06/2017.
 */

public class Carro {
    private String uuid, urlfoto, nchasis, marca, color, modelo, idfoto;


    public Carro(){

    }

    public Carro(String urlfoto, String nchasis, String marca, String color, String modelo, String idfoto) {
        this.uuid = UUID.randomUUID().toString();
        this.urlfoto = urlfoto;
        this.nchasis = nchasis;
        this.marca = marca;
        this.color = color;
        this.modelo = modelo;
        this.idfoto = idfoto;
    }


    public Carro(String uuid, String urlfoto, String nchasis, String marca, String color, String modelo, String idfoto) {
        this.uuid = uuid;
        this.urlfoto = urlfoto;
        this.nchasis = nchasis;
        this.marca = marca;
        this.color = color;
        this.modelo = modelo;
        this.idfoto = idfoto;
    }


    public String getUuid() {
        return uuid;
    }

    public String getUrlfoto() {
        return urlfoto;
    }

    public String getNchasis() {
        return nchasis;
    }

    public String getMarca() {
        return marca;
    }

    public String getColor() {
        return color;
    }

    public String getModelo() {
        return modelo;
    }

    public String getIdfoto() {
        return idfoto;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public void setUrlfoto(String urlfoto) {
        this.urlfoto = urlfoto;
    }

    public void setNchasis(String nchasis) {
        this.nchasis = nchasis;
    }

    public void setMarca(String marca) {
        this.marca = marca;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public void setModelo(String modelo) {
        this.modelo = modelo;
    }

    public void setIdfoto(String idfoto) {
        this.idfoto = idfoto;
    }


    public  void guardar(Context contexto){
        guardarRemoto(contexto);
    }

    public void guardarRemoto(final Context contexto){
        new AsyncTask<Void, Void, String>(){

            @Override
            protected String doInBackground(Void... params) {
                HttpURLConnection conexion = null;

                try {
                    URL url = new URL("http://34.224.168.6/guardar.php");
                    conexion =(HttpURLConnection)url.openConnection();
                    conexion.setConnectTimeout(30000);
                    conexion.setReadTimeout(30000);

                    //Configuracion de envío de datos via POST
                    conexion.setRequestMethod("POST");
                    conexion.setDoOutput(true);
                    conexion.setRequestProperty("Content-Type","application/x-www-form-urlencoded");

                    //Crear consulta con los datos

                    StringBuilder builder = new StringBuilder();
                    builder.append("id");
                    builder.append("=");
                    builder.append(URLEncoder.encode(uuid,"UTF-8"));
                    builder.append("&");
                    builder.append("foto");
                    builder.append("=");
                    builder.append(URLEncoder.encode(getUrlfoto(),"UTF-8"));
                    builder.append("&");
                    builder.append("nchasis");
                    builder.append("=");
                    builder.append(URLEncoder.encode(nchasis,"UTF-8"));
                    builder.append("&");
                    builder.append("marca");
                    builder.append("=");
                    builder.append(URLEncoder.encode(marca,"UTF-8"));
                    builder.append("&");
                    builder.append("color");
                    builder.append("=");
                    builder.append(URLEncoder.encode(color,"UTF-8"));
                    builder.append("&");
                    builder.append("modelo");
                    builder.append("=");
                    builder.append(URLEncoder.encode(modelo,"UTF-8"));
                    builder.append("&");
                    builder.append("idfoto");
                    builder.append("=");
                    builder.append(URLEncoder.encode(idfoto,"UTF-8"));

                    String query = builder.toString();

                    conexion.setFixedLengthStreamingMode(query.getBytes("UTF-8").length);

                    OutputStream outputStream = conexion.getOutputStream();
                    OutputStreamWriter outputStreamWriter = new OutputStreamWriter(outputStream,"UTF-8");
                    BufferedWriter bufferedWriter = new BufferedWriter(outputStreamWriter);
                    bufferedWriter.write(query);
                    bufferedWriter.flush();
                    bufferedWriter.close();

                    //Conectar
                    conexion.connect();

                    //Leer Respuesta del servidor

                    InputStream inputStream = conexion.getInputStream();
                    InputStreamReader inputStreamReader = new InputStreamReader(inputStream,"UTF-8");
                    BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
                    StringBuilder datos = new StringBuilder();
                    String linea;
                    while((linea =bufferedReader.readLine())!=null){
                        datos.append(linea);
                    }

                    bufferedReader.close();
                    return datos.toString();

                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                return null;

            }

            @Override
            protected void onPostExecute(String s){
                super.onPostExecute(s);
                try {
                    JSONObject jsonObject = new JSONObject(s);
                    boolean success = jsonObject.getBoolean("success");
                    if(success){
                        urlfoto = jsonObject.getString("foto");
                        guardarLocal(contexto);

                    }



                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }.execute();
    }


    public void guardarLocal(Context contexto){
        //declarar las variables
        SQLiteDatabase db;
        String sql;

        //Abrir la conexion de base datos en modo escritura
        CarrosSQLiteOpenHelper  aux = new CarrosSQLiteOpenHelper(contexto,"DBPersonas",null);
        db = aux.getWritableDatabase();

        //insertar forma 1
        sql = "INSERT INTO Personas values('"
                +this.getUuid()+"','"
                +this.getUrlfoto()+"','"
                +this.getNchasis()+"','"
                +this.getMarca()+"','"
                +this.getColor()+"','"
                +this.getModelo()+"','"
                +this.getIdfoto()+"')";

        db.execSQL(sql);

        //insert forma 2

      /*  ContentValues nuevoRegistro = new ContentValues();
        nuevoRegistro.put("foto",this.getFoto());
        nuevoRegistro.put("cedula",this.getCedula());
        nuevoRegistro.put("nombre",this.getNombre());
        nuevoRegistro.put("apellido",this.getApellido());
        nuevoRegistro.put("sexo",this.getSexo());
        nuevoRegistro.put("pasatiempo",this.getPasatiempo());

        db.insert("Personas",null,nuevoRegistro);
*/
        //cerrar conexion
        db.close();
    }

}
