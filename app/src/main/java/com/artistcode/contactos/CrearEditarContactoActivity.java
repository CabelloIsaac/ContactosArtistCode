package com.artistcode.contactos;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.artistcode.contactos.recursos.DBHelper;

import java.util.ArrayList;
import java.util.Random;

import static com.artistcode.contactos.recursos.Constantes.ACTION_CREATE;
import static com.artistcode.contactos.recursos.Constantes.BLUE;
import static com.artistcode.contactos.recursos.Constantes.CREATE_EDIT_ACTION;
import static com.artistcode.contactos.recursos.Constantes.GREEN;
import static com.artistcode.contactos.recursos.Constantes.ORANGE;
import static com.artistcode.contactos.recursos.Constantes.RED;
import static com.artistcode.contactos.recursos.Constantes.YELLOW;
import static com.artistcode.contactos.recursos.DBHelper.COLOR;
import static com.artistcode.contactos.recursos.DBHelper.CONTACTOS;
import static com.artistcode.contactos.recursos.DBHelper.EMAIL;
import static com.artistcode.contactos.recursos.DBHelper.ID;
import static com.artistcode.contactos.recursos.DBHelper.NOMBRE;
import static com.artistcode.contactos.recursos.DBHelper.TELEFONO;

public class CrearEditarContactoActivity extends AppCompatActivity {

    private String id, nombre, telefono, email;
    private TextInputEditText etNombre, etTelefono, etEmail;
    private int ACTION;
    private SQLiteDatabase db;

    private String[] listaColores = {YELLOW, BLUE, RED, GREEN, ORANGE};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crear_editar_contacto);

        db = new DBHelper(this, DBHelper.DB_NAME, null, DBHelper.DB_VERSION).getWritableDatabase();
        etNombre = findViewById(R.id.etNombre);
        etTelefono = findViewById(R.id.etTelefono);
        etEmail = findViewById(R.id.etEmail);

        if (getIntent().getExtras() != null) {

            ACTION = getIntent().getExtras().getInt(CREATE_EDIT_ACTION);

            if (ACTION == ACTION_CREATE) {
                //Creando contacto
                setTitle(getString(R.string.agregar_contacto));
            } else {
                setTitle(getString(R.string.modificar_contacto));
                //Modificando contacto

                id = getIntent().getExtras().getString(ID);
                cargarInformacionContacto();

            }

        }

    }


    private Boolean obtenerValoresCampos() {

        nombre = etNombre.getText().toString();
        telefono = etTelefono.getText().toString();
        email = etEmail.getText().toString();

        if (telefono.length() < 1) {
            Toast.makeText(this, getString(R.string.telefono_vacio), Toast.LENGTH_SHORT).show();
            etTelefono.requestFocus();
            return false;
        }

        return true;
    }

    private void crearContacto() {

        ContentValues values = new ContentValues();
        values.put(NOMBRE, nombre);
        values.put(TELEFONO, telefono);
        values.put(EMAIL, email);
        values.put(COLOR, generarColorAleatorio());

        db.insert(CONTACTOS, null, values);

        Toast.makeText(this, getString(R.string.contacto_creado), Toast.LENGTH_SHORT).show();

        finish();

    }

    private void cargarInformacionContacto() {

        String query = "SELECT * FROM " + CONTACTOS + " WHERE " + ID + "=?";

        Cursor cursor = db.rawQuery(query, new String[]{id});

        if (cursor.moveToFirst()) {

            String nombre = cursor.getString(1);
            String telefono = cursor.getString(2);
            String email = cursor.getString(3);

            etNombre.setText(nombre);
            etTelefono.setText(telefono);
            etEmail.setText(email);

        }

    }

    private void modificarContacto() {

        ContentValues values = new ContentValues();
        values.put(NOMBRE, nombre);
        values.put(TELEFONO, telefono);
        values.put(EMAIL, email);

        db.update(CONTACTOS, values, "id=?", new String[]{id});
        Toast.makeText(this, getString(R.string.contacto_modificado), Toast.LENGTH_SHORT).show();

        finish();

    }

    private String generarColorAleatorio() {

        Random aleatorio = new Random(System.currentTimeMillis());

        int intAleatorio = aleatorio.nextInt(5);

        return listaColores[intAleatorio];
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_crear, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_save) {

            if (ACTION == ACTION_CREATE) {
                //Creando contacto

                if (obtenerValoresCampos()) {
                    crearContacto();
                }

            } else {
                //Modificando contacto

                if (obtenerValoresCampos()) {
                    modificarContacto();
                }
            }

            return true;
        }

        return super.onOptionsItemSelected(item);
    }

}
