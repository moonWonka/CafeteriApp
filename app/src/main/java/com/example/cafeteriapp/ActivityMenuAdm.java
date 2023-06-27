package com.example.cafeteriapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import com.google.firebase.auth.FirebaseAuth;


public class ActivityMenuAdm extends AppCompatActivity {

    Button btnMantenedorProductos, btnMantenedorCategorias, btnMostrarProductos;

    private FirebaseAuth mAuth;
    ImageButton btnLogout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_adm);

        mAuth = FirebaseAuth.getInstance();

        btnMostrarProductos = findViewById(R.id.btnMostrarProductos);
        btnMostrarProductos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ActivityMenuAdm.this, MostrarProductos.class);
                startActivity(intent);
            }
        });

        btnMantenedorProductos = findViewById(R.id.btnMantenedorProductos);
        btnMantenedorProductos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ActivityMenuAdm.this, CreateItems.class);
                startActivity(intent);
            }
        });

        btnMantenedorCategorias = findViewById(R.id.btnMantenedorCategorias);
        btnMantenedorCategorias.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ActivityMenuAdm.this, CrearCategorias.class);
                startActivity(intent);
            }
        });


        btnLogout = findViewById(R.id.btnLogout);
        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mAuth.signOut();
                Intent intent = new Intent(ActivityMenuAdm.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }



}