package com.artistcode.contactos.recursos;

/**
 * Created by cabel on 4/2/2019.
 */

public class Contacto {

    private String id, nombre, telefono, color;

    public Contacto (String id, String nombre, String telefono, String color){
        this.id = id;
        this.nombre = nombre;
        this.telefono = telefono;
        this.color = color;
    }

    public String getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }

    public String getTelefono() {
        return telefono;
    }

    public String getColor() {
        return color;
    }

}
