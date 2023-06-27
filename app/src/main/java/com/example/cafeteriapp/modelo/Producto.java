package com.example.cafeteriapp.modelo;

import com.google.firebase.firestore.ServerTimestamp;

import java.util.Date;

public class Producto {
    private String nombre, categoria;
    private int precio;

    private Date mTimestamp;

    public Producto() {
        // Constructor vacío requerido para Firebase Firestore
    }

    public Producto(String nombre, int precio, String categoria) {
        this.nombre = nombre;
        this.precio = precio;
        this.categoria = categoria;
    }

    public String getNombre() {
        return nombre;
    }

    public String getCategoria() {
        return categoria;
    }

    public int getPrecio() {
        return precio;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public void setPrecio(int precio) {
        this.precio = precio;
    }

    @ServerTimestamp
    public Date getTimestamp() {
        return mTimestamp;
    }

    public void setTimestamp(Date timestamp) {
        mTimestamp = timestamp;
    }
}
