package com.example.lab7_20190212;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

public class MainActivity extends AppCompatActivity {
    FirebaseAuth auth;
    FirebaseFirestore db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        auth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();

        if (auth.getCurrentUser() != null) {
            db.collection("usuario").whereEqualTo("authUID", auth.getCurrentUser().getUid())
                    .get().addOnCompleteListener(task -> {
                        if (task.isSuccessful() && !task.getResult().isEmpty()) {
                            DocumentSnapshot document = task.getResult().getDocuments().get(0);
                            String rol = document.getString("rol");
                            if(rol.equals("gestor")){
                                Intent intent = new Intent(this, Gestor.class);
                                startActivity(intent);
                            }else{
                                Intent intent = new Intent(this, Cliente.class);
                                startActivity(intent);
                            }
                        }
                    });
        }



        TextView crear = findViewById(R.id.registrar);
        crear.setOnClickListener(view -> {
            Intent intent = new Intent(this,Crea.class);
            startActivity(intent);
        });
        TextInputEditText correo =findViewById(R.id.correo);
        TextInputEditText contra =findViewById(R.id.password);
        Button boton = findViewById(R.id.login);
        boton.setOnClickListener(view -> {
            String corre = String.valueOf(correo.getText());
            String con = String.valueOf(contra.getText());

            Query query = db.collection("usuario").whereEqualTo("correo",corre);
            query.get().addOnCompleteListener(task ->{
                if(task.isSuccessful()) {
                    QuerySnapshot queryDocumentSnapshot = task.getResult();
                    if (!queryDocumentSnapshot.isEmpty()) {
                        try{
                            DocumentSnapshot document = queryDocumentSnapshot.getDocuments().get(0);
                            String rol = document.getString("rol");
                            if(rol.equals("gestor")){
                                if (!corre.isEmpty() && Patterns.EMAIL_ADDRESS.matcher(corre).matches()) {
                                    if (!con.isEmpty()) {
                                        auth.signInWithEmailAndPassword(corre, con)
                                                .addOnSuccessListener(authResult -> {
                                                    startActivity(new Intent(MainActivity.this, Gestor.class));
                                                    finish();
                                                })
                                                .addOnFailureListener(e -> {
                                                    Toast.makeText(MainActivity.this, "Validacion incorrecta", Toast.LENGTH_SHORT).show();
                                                });
                                    } else {
                                        Toast.makeText(MainActivity.this, "hay campos vacios", Toast.LENGTH_SHORT).show();
                                    }} else if (corre.isEmpty()) {
                                    Toast.makeText(MainActivity.this, "hay campos vacios", Toast.LENGTH_SHORT).show();
                                } else {
                                    Toast.makeText(MainActivity.this, "correo no registrado", Toast.LENGTH_SHORT).show();
                                }
                            }else{
                                if (!corre.isEmpty() && Patterns.EMAIL_ADDRESS.matcher(corre).matches()) {
                                    if (!con.isEmpty()) {
                                        auth.signInWithEmailAndPassword(corre, con)
                                                .addOnSuccessListener(authResult -> {
                                                    startActivity(new Intent(MainActivity.this, Cliente.class));
                                                    finish();
                                                })
                                                .addOnFailureListener(e -> {
                                                    Toast.makeText(MainActivity.this, "Validacion incorrecta", Toast.LENGTH_SHORT).show();
                                                });
                                    } else {
                                        Toast.makeText(MainActivity.this, "hay campos vacios", Toast.LENGTH_SHORT).show();
                                    }} else if (corre.isEmpty()) {
                                    Toast.makeText(MainActivity.this, "hay campos vacios", Toast.LENGTH_SHORT).show();
                                } else {
                                    Toast.makeText(MainActivity.this, "correo no registrado", Toast.LENGTH_SHORT).show();
                                }
                            }
                        }catch (Exception e) {
                            Toast.makeText(MainActivity.this, "Correo no registrado", Toast.LENGTH_SHORT).show();
                        }

                    }
                    else{
                        Toast.makeText(MainActivity.this, "Correo no registrado", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        });
    }

}