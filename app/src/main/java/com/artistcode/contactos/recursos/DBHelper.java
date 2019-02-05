package com.artistcode.contactos.recursos;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by cabel on 4/2/2019.
 */

public class DBHelper extends SQLiteOpenHelper {

    private static final String TAG = "DBHelper";
    public static final String DB_NAME = "db_contactos";
    public static final int DB_VERSION = 1;

    public static final String CONTACTOS = "contactos";

    public static final String ID = "id";
    public static final String NOMBRE = "nombre";
    public static final String TELEFONO = "telefono";
    public static final String EMAIL = "email";
    public static final String COLOR = "color";

    private String QUERY_CONTACTOS = "CREATE TABLE " + CONTACTOS + " ("
            + "'" + ID + "' INTEGER PRIMARY KEY AUTOINCREMENT,"
            + "'" + NOMBRE + "' TEXT,"
            + "'" + TELEFONO + "' TEXT,"
            + "'" + EMAIL + "' TEXT,"
            + "'" + COLOR + "' TEXT);";

    public DBHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(QUERY_CONTACTOS);
        Log.i(TAG, "Base de datos creada");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
