package com.artistcode.contactos.recursos;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.artistcode.contactos.R;

import org.w3c.dom.Text;

import java.util.ArrayList;

import static com.artistcode.contactos.recursos.Constantes.BLUE;
import static com.artistcode.contactos.recursos.Constantes.GREEN;
import static com.artistcode.contactos.recursos.Constantes.ORANGE;
import static com.artistcode.contactos.recursos.Constantes.RED;
import static com.artistcode.contactos.recursos.Constantes.YELLOW;

/**
 * Created by cabel on 4/2/2019.
 */

public class ContactosAdapter extends BaseAdapter {

    private Activity activity;
    private ArrayList<Contacto> contactos;

    public ContactosAdapter(Activity activity, ArrayList<Contacto> contactos) {
        this.activity = activity;
        this.contactos = contactos;
    }

    @Override
    public int getCount() {
        return contactos.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View view = convertView;

        if (view == null) {
            LayoutInflater inflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.item_list_contacto, null);
        }

        Contacto contacto = contactos.get(position);

        TextView tvID = view.findViewById(R.id.tvID);
        TextView tvIcon = view.findViewById(R.id.tvIcon);
        TextView tvNombre = view.findViewById(R.id.tvNombre);
        TextView tvTelefono = view.findViewById(R.id.tvTelefono);

        tvID.setText(contacto.getId());
        tvTelefono.setText(contacto.getTelefono());

        tvIcon.setBackgroundColor(seleccionarColor(contacto.getColor()));

        if (contacto.getNombre().length() < 1) {
            tvIcon.setText("#");
            tvNombre.setText(contacto.getTelefono());
        } else {
            tvIcon.setText(""+contacto.getNombre().charAt(0));
            tvNombre.setText(contacto.getNombre());
        }

        return view;
    }

    private int seleccionarColor (String color){

        switch (color){

            case YELLOW:
                return activity.getResources().getColor(R.color.yellow);
            case BLUE:
                return activity.getResources().getColor(R.color.blue);
            case RED:
                return activity.getResources().getColor(R.color.red);
            case GREEN:
                return activity.getResources().getColor(R.color.green);
            case ORANGE:
                return activity.getResources().getColor(R.color.orange);
            default:
                return activity.getResources().getColor(R.color.orange);

        }

    }

}
