package com.example.cuc.carros;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

/**
 * Created by CUC on 3/06/2017.
 */

public class Datos {

    public static ArrayList<Carro> traerCarros(Context contexto){
        ArrayList<Carro> carros = new ArrayList<>();

        //Declarar variables
        SQLiteDatabase db;
        String sql, uuid, urlfoto, nchasis, marca, color, modelo, idfoto;
        Carro car;
        //Abrir conexión de lectura
        CarrosSQLiteOpenHelper aux = new CarrosSQLiteOpenHelper(contexto,"DBCarros",null);
        db = aux.getReadableDatabase();

        //Cursor
        sql ="select * from Carros";
        Cursor c = db.rawQuery(sql,null);

        //Recorrido del cursor
        if(c.moveToFirst()){
            do{
                uuid = c.getString(0);
                urlfoto=c.getString(1);
                nchasis = c.getString(2);
                marca =c.getString(3);
                color = c.getString(4);
                modelo = c.getString(5);
                idfoto = c.getColumnName(6);

                car = new Carro(uuid,urlfoto,nchasis,marca,color,modelo,idfoto);
                carros.add(car);

            }while (c.moveToNext());
        }

        db.close();

        return carros;

    }

    public static Carro buscarCarro(Context contexto, String chasis){

        //Declarar variables
        SQLiteDatabase db;
        String sql, uuid, urlfoto, nchasis, marca, color, modelo, idfoto;
        Carro car=null;
        //Abrir conexión de lectura
        CarrosSQLiteOpenHelper aux = new CarrosSQLiteOpenHelper(contexto,"DBCarros",null);
        db = aux.getReadableDatabase();

        //Cursor
        sql ="select * from Carros where nchasis ='"+chasis+"'";
        Cursor c = db.rawQuery(sql,null);

        //Recorrido del cursor
        if(c.moveToFirst()){

            uuid = c.getString(0);
            urlfoto=c.getString(1);
            nchasis = c.getString(2);
            marca =c.getString(3);
            color = c.getString(4);
            modelo = c.getString(5);
            idfoto = c.getColumnName(6);

            car = new Carro(uuid,urlfoto,nchasis,marca,color,modelo,idfoto);
        }

        db.close();
        return car;
    }
}
