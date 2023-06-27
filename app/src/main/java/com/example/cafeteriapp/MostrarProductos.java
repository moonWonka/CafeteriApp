package com.example.cafeteriapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import com.example.cafeteriapp.adaptadorProducto.AdaptadorProductos;
import com.example.cafeteriapp.modelo.Producto;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

public class MostrarProductos extends AppCompatActivity {

    FirebaseFirestore db;
    RecyclerView mRecyclerView;
    AdaptadorProductos mAdapter;

    Button btnFragment;

    Spinner spinnerCategoria;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mostrar_productos);

        db = FirebaseFirestore.getInstance();

        mRecyclerView = findViewById(R.id.recyclerViewProductos);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        btnFragment = findViewById(R.id.btnFragmentAgregar);
        btnFragment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CrearItemFragment fm = new CrearItemFragment();
                fm.show(getSupportFragmentManager(), "CrearItemFragment");
            }
        });

        spinnerCategoria = findViewById(R.id.selecionCategorias);

        String[] categorias = {"Todos","Café", "Té", "Bebida", "Comidas", "Postres", "Otros"};

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, categorias);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerCategoria.setAdapter(adapter);

        spinnerCategoria.setSelection(adapter.getPosition("Todos")); // Establecer "Otros" como la opción predeterminada

        spinnerCategoria.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String categoriaSeleccionada = categorias[position];
                if (categoriaSeleccionada.equals("Todos")) {
                    cargarProductos(); // Cargar todos los productos si se selecciona "Todos"
                } else {
                    cargarProductosPorCategoria(categoriaSeleccionada); // Cargar productos por categoría
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // No se ha seleccionado nada
            }
        });

        cargarProductos();
    }

    private void cargarProductos() {
        Query query = db.collection("productos")
                .orderBy("categoria")
                .orderBy("timestamp");

        FirestoreRecyclerOptions<Producto> firestoreRecyclerOptions = new FirestoreRecyclerOptions.Builder<Producto>()
                .setQuery(query, Producto.class)
                .build();

        mAdapter = new AdaptadorProductos(firestoreRecyclerOptions);

        mRecyclerView.setAdapter(mAdapter);
        mAdapter.startListening();
    }

    private void cargarProductosPorCategoria(String categoria) {
        Query query = db.collection("productos").whereEqualTo("categoria", categoria)
                .orderBy("timestamp");

        FirestoreRecyclerOptions<Producto> firestoreRecyclerOptions = new FirestoreRecyclerOptions.Builder<Producto>()
                .setQuery(query, Producto.class)
                .build();

        mAdapter.stopListening(); // Detener la escucha del adaptador actual
        mAdapter = new AdaptadorProductos(firestoreRecyclerOptions);
        mRecyclerView.setAdapter(mAdapter);
        mAdapter.startListening(); // Iniciar la escucha con el nuevo adaptador
    }


    @Override
    protected void onStart() {
        super.onStart();
        if (mAdapter != null) {
            mAdapter.startListening();
        } else {
            cargarProductos();
        }
    }


    @Override
    protected void onStop() {
        super.onStop();
        mAdapter.stopListening();
    }
}
