package com.artistcode.contactos;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.artistcode.contactos.recursos.Contacto;
import com.artistcode.contactos.recursos.ContactosAdapter;
import com.artistcode.contactos.recursos.DBHelper;

import java.util.ArrayList;

import static com.artistcode.contactos.recursos.Constantes.ACTION_CREATE;
import static com.artistcode.contactos.recursos.Constantes.CREATE_EDIT_ACTION;
import static com.artistcode.contactos.recursos.DBHelper.CONTACTOS;
import static com.artistcode.contactos.recursos.DBHelper.ID;

public class MainActivity extends AppCompatActivity {

    private SQLiteDatabase db;
    private ListView lvContactos;
    private View layoutContactosVacios;
    private ArrayList<Contacto> listaContactos;
    private ContactosAdapter contactosAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        db = new DBHelper(this, DBHelper.DB_NAME, null, DBHelper.DB_VERSION).getWritableDatabase();

        lvContactos = findViewById(R.id.lvContactos);
        layoutContactosVacios = findViewById(R.id.layoutContactosVacios);
        listaContactos = new ArrayList<>();
        contactosAdapter = new ContactosAdapter(this, listaContactos);

        lvContactos.setAdapter(contactosAdapter);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(MainActivity.this, CrearEditarContactoActivity.class);
                intent.putExtra(CREATE_EDIT_ACTION, ACTION_CREATE);
                startActivity(intent);

            }
        });

        lvContactos.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                TextView tvID = view.findViewById(R.id.tvID);
                String idContacto = tvID.getText().toString();

                Intent intent = new Intent (MainActivity.this, DetalleActivity.class);
                intent.putExtra(ID, idContacto);
                startActivity(intent);

            }
        });

    }

    @Override
    protected void onResume() {
        super.onResume();
        cargarContactos();
    }

    private void cargarContactos() {

        listaContactos.clear();
        contactosAdapter.notifyDataSetChanged();

        String query = "SELECT * FROM " + CONTACTOS;

        Cursor cursor = db.rawQuery(query, null);

        if (cursor.moveToFirst()) {

            do {

                String id = cursor.getString(0);
                String nombre = cursor.getString(1);
                String telefono = cursor.getString(2);
                String color = cursor.getString(4);

                listaContactos.add(new Contacto(id, nombre, telefono, color));
                contactosAdapter.notifyDataSetChanged();

            } while (cursor.moveToNext());

            layoutContactosVacios.setVisibility(View.GONE);

        } else {
            layoutContactosVacios.setVisibility(View.VISIBLE);

        }

    }

    private void eliminarContacto () {

        db.delete(CONTACTOS, null, null);
        Toast.makeText(this, getString(R.string.contactos_eliminado), Toast.LENGTH_SHORT).show();
        cargarContactos();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_eliminar_todo) {

            AlertDialog.Builder alerta = new AlertDialog.Builder(MainActivity.this);

            alerta.setTitle(getString(R.string.eliminar));
            alerta.setMessage(getString(R.string.mensaje_eliminar_todo));
            alerta.setPositiveButton(getString(R.string.yes), new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    eliminarContacto();
                }
            });
            alerta.setNegativeButton(getString(R.string.cancelar), new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    //El usuario presionó cancelar
                }
            });
            alerta.show();

            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
