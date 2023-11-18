package com.example.lab7_20190212;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;
import java.util.List;

public class Cliente extends AppCompatActivity {
    private FirebaseAuth mAuth;
    FirebaseFirestore db;
    List<Cita> cita = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cliente);
        mAuth = FirebaseAuth.getInstance();
        Button salir = findViewById(R.id.salir);
        salir.setOnClickListener(view -> {
            mAuth.signOut();
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
        });

        db = FirebaseFirestore.getInstance();

    }
}