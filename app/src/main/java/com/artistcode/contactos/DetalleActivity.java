package com.artistcode.contactos;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.artistcode.contactos.recursos.DBHelper;

import static com.artistcode.contactos.recursos.Constantes.ACTION_CREATE;
import static com.artistcode.contactos.recursos.Constantes.ACTION_EDIT;
import static com.artistcode.contactos.recursos.Constantes.BLUE;
import static com.artistcode.contactos.recursos.Constantes.CREATE_EDIT_ACTION;
import static com.artistcode.contactos.recursos.Constantes.GREEN;
import static com.artistcode.contactos.recursos.Constantes.ORANGE;
import static com.artistcode.contactos.recursos.Constantes.RED;
import static com.artistcode.contactos.recursos.Constantes.YELLOW;
import static com.artistcode.contactos.recursos.DBHelper.CONTACTOS;
import static com.artistcode.contactos.recursos.DBHelper.DB_NAME;
import static com.artistcode.contactos.recursos.DBHelper.DB_VERSION;
import static com.artistcode.contactos.recursos.DBHelper.ID;

public class DetalleActivity extends AppCompatActivity {

    private TextView tvNombre, tvTelefono, tvEmail;
    private ImageView ivNombre, ivTelefono, ivEmail;

    private String id, nombre, telefono, email, color;

    private SQLiteDatabase db;

    private AppBarLayout header;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalle);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        if (Build.VERSION.SDK_INT >= 21){
            getWindow().setStatusBarColor(getResources().getColor(android.R.color.transparent));
        }

        if (getIntent().getExtras() != null){

            id = getIntent().getExtras().getString(ID);

        } else {
            finish();
        }

        db = new DBHelper(this, DB_NAME, null, DB_VERSION).getWritableDatabase();

        tvNombre = findViewById(R.id.tvNombre);
        tvTelefono = findViewById(R.id.tvTelefono);
        tvEmail = findViewById(R.id.tvEmail);

        ivNombre = findViewById(R.id.ivNombre);
        ivTelefono = findViewById(R.id.ivTelefono);
        ivEmail = findViewById(R.id.ivEmail);

        header = findViewById(R.id.app_bar);

    }

    @Override
    protected void onResume() {
        super.onResume();
        cargarInformacionContacto ();
    }

    private void cargarInformacionContacto () {

        String query = "SELECT * FROM "+CONTACTOS +" WHERE "+ID +"=?";

        Cursor cursor = db.rawQuery(query, new String[] {id});

        if (cursor.moveToFirst()) {

            String nombre = cursor.getString(1);
            String telefono = cursor.getString(2);
            String email = cursor.getString(3);
            String color = cursor.getString(4);

            tvNombre.setText(nombre);
            tvTelefono.setText(telefono);
            tvEmail.setText(email);

            ivNombre.setColorFilter(seleccionarColor(color));
            ivTelefono.setColorFilter(seleccionarColor(color));
            ivEmail.setColorFilter(seleccionarColor(color));

            header.setBackgroundColor(seleccionarColor(color));

        }

    }

    private int seleccionarColor (String color){

        switch (color){

            case YELLOW:
                return getResources().getColor(R.color.yellow);
            case BLUE:
                return getResources().getColor(R.color.blue);
            case RED:
                return getResources().getColor(R.color.red);
            case GREEN:
                return getResources().getColor(R.color.green);
            case ORANGE:
                return getResources().getColor(R.color.orange);
            default:
                return getResources().getColor(R.color.orange);

        }

    }

    private void eliminarContacto () {

        db.delete(CONTACTOS, "id=?", new String[]{id});
        Toast.makeText(this, getString(R.string.contacto_eliminado), Toast.LENGTH_SHORT).show();
        finish();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_detalle, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_modificar) {

            Intent intent = new Intent(DetalleActivity.this, CrearEditarContactoActivity.class);
            intent.putExtra(ID, this.id);
            intent.putExtra(CREATE_EDIT_ACTION, ACTION_EDIT);
            startActivity(intent);

            return true;

        } else if (id == R.id.action_eliminar){

            AlertDialog.Builder alerta = new AlertDialog.Builder(DetalleActivity.this);

            alerta.setTitle(getString(R.string.eliminar));
            alerta.setMessage(getString(R.string.mensaje_eliminar));
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

        }

        return super.onOptionsItemSelected(item);
    }

}
