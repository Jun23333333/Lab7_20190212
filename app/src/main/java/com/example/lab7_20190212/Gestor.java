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

public class Gestor extends AppCompatActivity {
    FirebaseFirestore db;
    List<Cita> cita = new ArrayList<>();
    private FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gestor);
        mAuth = FirebaseAuth.getInstance();
        Button salir = findViewById(R.id.salir);
        salir.setOnClickListener(view -> {
            mAuth.signOut();
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
        });

        db = FirebaseFirestore.getInstance();
        db.collection("cita").addSnapshotListener((collection, error) -> {
            if (error != null) {
                Log.d("lectura", "Error listening for document changes.");
                return;
            }
            if (collection != null && !collection.isEmpty()) {
                for (QueryDocumentSnapshot document : collection) {
                    Cita cit = document.toObject(Cita.class);
                    cita.add(cit);
                }
            }
        });

        if(cita.isEmpty()){
            Cita cita1 = null;
            cita1.setHora("");
            cita1.setCorreo("");
            cita1.setNombre("No hay cita registrada");
            cita.add(cita1);
        }
        Adaptador listaAdapter = new Adaptador(cita,this);
        RecyclerView recyclerView = findViewById(R.id.lista);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(listaAdapter);


    }


}