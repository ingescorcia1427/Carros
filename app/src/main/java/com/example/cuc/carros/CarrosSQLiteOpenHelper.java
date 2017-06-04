package com.example.cuc.carros;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by CUC on 3/06/2017.
 */

public class CarrosSQLiteOpenHelper extends SQLiteOpenHelper {
    private String sql = "CREATE TABLE Carros(uuid text, urlfoto text, nchasis text, marca text, color text, modelo text, idfoto text)";
    private static int version=1;

    public CarrosSQLiteOpenHelper(Context contexto, String name, SQLiteDatabase.CursorFactory factory){
        super(contexto, name, factory,version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(sql);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS Carros");
        db.execSQL(sql);
    }
}
