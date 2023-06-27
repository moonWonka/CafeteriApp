package com.example.cafeteriapp.modelo;

import com.google.firebase.firestore.ServerTimestamp;

import java.util.Date;

public class Producto {
    private String nombre, categoria;
    private int precio;


    public Producto() {
        // Constructor vacío requerido para Firebase Firestore
    }

    public Producto(String nombre, String categoria, int precio) {
        this.nombre = nombre;
        this.categoria = categoria;
        this.precio = precio;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public int getPrecio() {
        return precio;
    }

    public void setPrecio(int precio) {
        this.precio = precio;
    }
}