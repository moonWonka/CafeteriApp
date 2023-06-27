package com.example.cafeteriapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {

    EditText etUser, etPass;
    Button btnLogin;
    private FirebaseAuth mAuth;

    private FirebaseUser user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mAuth = FirebaseAuth.getInstance();
        setupLoginButton();

        user = mAuth.getCurrentUser();
        if (user != null) {
            Intent intent = new Intent(MainActivity.this, ActivityMenuAdm.class);
            startActivity(intent);
            finish(); // Finaliza la actividad actual para que el usuario no pueda volver atrás
        } else {
            Toast.makeText(this, "No hay usuario autenticado", Toast.LENGTH_SHORT).show();
        }
    }

    private void setupLoginButton() {
        etUser = findViewById(R.id.etUser);
        etPass = findViewById(R.id.etPass);
        btnLogin = findViewById(R.id.btnLogin);

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = etUser.getText().toString();
                String password = etPass.getText().toString();

                if (email.isEmpty() || password.isEmpty()) {
                    Toast.makeText(MainActivity.this, "Por favor, complete todos los campos", Toast.LENGTH_SHORT).show();
                } else {
                    signInWithEmailAndPassword(email, password);
                }
            }
        });
    }

    private void signInWithEmailAndPassword(String email, String password) {
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Inicio de sesión exitoso, actualizar la interfaz de usuario con la información del usuario autenticado
                            FirebaseUser user = mAuth.getCurrentUser();
                            Toast.makeText(MainActivity.this, "Inicio de sesión exitoso", Toast.LENGTH_SHORT).show();
                            // Realizar la acción deseada después del inicio de sesión exitoso
                            // Redirigir al usuario a la siguiente actividad
                            Intent intent = new Intent(MainActivity.this, ActivityMenuAdm.class);
                            startActivity(intent);
                            finish(); // Finaliza la actividad actual para que el usuario no pueda volver atrás
                        } else {
                            // Si el inicio de sesión falla, mostrar un mensaje al usuario.
                            Toast.makeText(MainActivity.this, "Error al iniciar sesión. Por favor, comprueba tus credenciales", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }
}
