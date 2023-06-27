package com.example.cafeteriapp;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.cafeteriapp.modelo.Producto;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;


public class CrearItemFragment extends DialogFragment {

    Button btnAgregar;
    EditText editTextNombre;
    EditText editTextPrecio;
    Spinner spinnerCategoria;
    FirebaseFirestore db;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_crear_item, container, false);
        db = FirebaseFirestore.getInstance();

        btnAgregar = v.findViewById(R.id.btnFragmentAgregar);
        editTextNombre = v.findViewById(R.id.editTextNombre);
        editTextPrecio = v.findViewById(R.id.editTextPrecio);
        spinnerCategoria = v.findViewById(R.id.spinnerCategorias);

        String[] categorias = {"Café", "Té", "Bebida", "Comidas", "Postres", "Otros"};

        ArrayAdapter<String> adapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_item, categorias);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerCategoria.setAdapter(adapter);
        btnAgregar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                agregarProducto();
            }
        });

        return  v;
    }

    private void agregarProducto() {
        String nombre = editTextNombre.getText().toString();
        String categoria = spinnerCategoria.getSelectedItem().toString();
        int precio;

        try {
            precio = Integer.parseInt(editTextPrecio.getText().toString());
        } catch (NumberFormatException e) {
            Toast.makeText(getContext(), "Error al agregar producto", Toast.LENGTH_SHORT).show();
            return;
        }

        if (nombre.isEmpty() || categoria.isEmpty()) {
            Toast.makeText(getContext(), "Ingrese nombre y categoría", Toast.LENGTH_SHORT).show();
            return;
        }

        if (precio <= 0) {
            Toast.makeText(getContext(), "Ingrese precio", Toast.LENGTH_SHORT).show();
            return;
        }

        Producto producto = new Producto(nombre, precio, categoria);

        db.collection("productos").add(producto)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        Toast.makeText(getContext(), "Producto agregado", Toast.LENGTH_SHORT).show();
                        getDialog().dismiss();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(getContext(), "Error al agregar producto", Toast.LENGTH_SHORT).show();
                    }
                });
    }

}