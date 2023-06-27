package com.example.cafeteriapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.HashMap;
import java.util.Map;


public class CrearCategorias extends AppCompatActivity {

    EditText etCategoriaNombre;
    Button btnGuardar;
    private FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crear_categorias);

        etCategoriaNombre = findViewById(R.id.etCategoriaNombre);
        btnGuardar = findViewById(R.id.btnGuardar);

        db = FirebaseFirestore.getInstance();

        btnGuardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                agregarCategoria();
            }
        });

        LinearLayout linearLayoutCategorias = findViewById(R.id.linearlayoutCategorias);

        mostrarCategorias(linearLayoutCategorias);
    }

    private void mostrarCategorias(LinearLayout linearLayoutCategorias) {
        db.collection("categorias").get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                for (QueryDocumentSnapshot documentSnapshot : queryDocumentSnapshots) {
                    String nombre = documentSnapshot.getString("nombre");

                    TextView textViewCategoria = new TextView(CrearCategorias.this);
                    textViewCategoria.setText(nombre);
                    linearLayoutCategorias.addView(textViewCategoria);

                    // Agrega cualquier otro elemento necesario para mostrar los detalles de la categoría
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(CrearCategorias.this, "Error al obtener categorías", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void agregarCategoria() {
        String nombre = etCategoriaNombre.getText().toString();

        Map<String, Object> categoria = new HashMap<>();
        categoria.put("nombre", nombre);

        db.collection("categorias").document(nombre).set(categoria).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                Toast.makeText(CrearCategorias.this, "Categoría agregada", Toast.LENGTH_SHORT).show();
                etCategoriaNombre.setText("");
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(CrearCategorias.this, "Error al agregar categoría", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
