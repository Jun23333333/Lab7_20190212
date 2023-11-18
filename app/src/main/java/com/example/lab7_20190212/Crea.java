package com.example.lab7_20190212;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.auth.FirebaseAuth;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class Crea extends AppCompatActivity {
    FirebaseFirestore db;
    FirebaseAuth auth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crea);
        db = FirebaseFirestore.getInstance();
        auth=FirebaseAuth.getInstance();
        Button boton = findViewById(R.id.button);
        TextInputEditText correo =findViewById(R.id.correo);
        TextInputEditText contra =findViewById(R.id.password);

        boton.setOnClickListener(view -> {
            String corre = String.valueOf(correo.getText());
            String con = String.valueOf(contra.getText());
            auth.fetchSignInMethodsForEmail(corre)
                    .addOnCompleteListener(task -> {
                        if (task.isSuccessful() && task.getResult() != null && task.getResult().getSignInMethods() != null && task.getResult().getSignInMethods().size() > 0) {

                            Toast.makeText(this, "El correo electrónico ya está en uso", Toast.LENGTH_SHORT).show();
                        } else {
                            auth.createUserWithEmailAndPassword(corre, con)
                                    .addOnCompleteListener(task1 -> {
                                        if (task1.isSuccessful()) {
                                            String authUID = Objects.requireNonNull(auth.getCurrentUser()).getUid();
                                            Map<String, Object> user = new HashMap<>();
                                            user.put("correo", corre);
                                            user.put("rol", "cliente");
                                            user.put("authUID", authUID);
                                            Log.d("asdff","aa");
                                            // Añadir datos en Firestore
                                            db.collection("usuario").document(authUID).set(user)
                                                    .addOnSuccessListener(aVoid -> {
                                                        Log.d("asdff","ab");
                                                        Toast.makeText(this, "Usuario registrado con éxito", Toast.LENGTH_SHORT).show();
                                                        Intent intent = new Intent(this, MainActivity.class);
                                                        startActivity(intent);
                                                    });
                                            }

                                        });
                                    }});


                        });
        }

    }
