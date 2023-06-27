package com.example.cafeteriapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
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

public class CreateItems extends AppCompatActivity {

    Button btnAgregar;
    EditText editTextNombre;
    EditText editTextPrecio;
    Spinner spinnerCategoria;
    FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_items);

        btnAgregar = findViewById(R.id.btnFragmentAgregar);
        editTextNombre = findViewById(R.id.editTextNombre);
        editTextPrecio = findViewById(R.id.editTextPrecio);
        spinnerCategoria = findViewById(R.id.spinnerCategorias);

        String[] categorias = {"Café", "Té", "Bebida", "Comidas", "Postres", "Otros"};

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, categorias);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerCategoria.setAdapter(adapter);

        db = FirebaseFirestore.getInstance();

        btnAgregar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                agregarProducto();
            }
        });
    }

    private void agregarProducto() {
        String nombre = editTextNombre.getText().toString();
        String categoria = spinnerCategoria.getSelectedItem().toString();
        int precio;

        try {
            precio = Integer.parseInt(editTextPrecio.getText().toString());
        } catch (NumberFormatException e) {
            Toast.makeText(this, "Error al agregar producto", Toast.LENGTH_SHORT).show();
            return;
        }

        if (nombre.isEmpty() || categoria.isEmpty()) {
            Toast.makeText(this, "Ingrese nombre y categoría", Toast.LENGTH_SHORT).show();
            return;
        }

        if (precio <= 0) {
            Toast.makeText(this, "Ingrese precio", Toast.LENGTH_SHORT).show();
            return;
        }

        Producto producto = new Producto(nombre, categoria, precio);

        db.collection("productos").add(producto)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        Toast.makeText(CreateItems.this, "Producto agregado", Toast.LENGTH_SHORT).show();
                        finish();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(CreateItems.this, "Error al agregar producto", Toast.LENGTH_SHORT).show();
                    }
                });
    }
}
