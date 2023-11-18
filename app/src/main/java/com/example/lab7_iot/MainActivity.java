package com.example.lab7_iot;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.example.lab7_iot.databinding.ActivityMainBinding;
import com.example.lab7_iot.entity.Usuario;
import com.firebase.ui.auth.AuthUI;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

public class MainActivity extends AppCompatActivity {

    ActivityMainBinding binding;
    FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        binding = ActivityMainBinding.inflate(getLayoutInflater());

        db = FirebaseFirestore.getInstance(); //con firestore
        binding.button.setOnClickListener(view -> {
            String nombre = binding.textFieldNombre.getEditText().getText().toString();
            String apellido = binding.textFieldApellido.getEditText().getText().toString();
            String edadStr = binding.textFieldEdad.getEditText().getText().toString();
            String dni = binding.textFieldDni.getEditText().getText().toString();

            Usuario usuario = new Usuario();
            usuario.setNombre(nombre);
            usuario.setApellido(apellido);
            usuario.setEdad(Integer.parseInt(edadStr));

            db.collection("usuarios")
                    .document(dni)
                    .set(usuario)
                    .addOnSuccessListener(unused -> {
                        Toast.makeText(this, "Usuario grabado", Toast.LENGTH_SHORT).show();
                    })
                    .addOnFailureListener(e -> {
                        Toast.makeText(this, "Algo pasó al guardar ", Toast.LENGTH_SHORT).show();
                    });
        });

//        binding.fabNextActivity.setOnClickListener(view -> {
//            Intent intent = new Intent(MainActivity.this, GatoActivity.class);
//            startActivity(intent);
//        });

        //binding.btnListarUsuarios.setOnClickListener(view -> {
            String dni = binding.textFieldDni.getEditText().getText().toString();

            if (!dni.isEmpty()) {
                //binding.btnListarUsuarios.setEnabled(false);
                db.collection("usuarios")
                        .document(dni)
                        .get()
                        .addOnCompleteListener(task -> {

                            if (task.isSuccessful()) {
                                DocumentSnapshot documentSnapshot = task.getResult();
                                if (documentSnapshot.exists()) {
                                    Log.d("msg-test", "DocumentSnapshot data: " + documentSnapshot.getData());

                                    Usuario usuario = documentSnapshot.toObject(Usuario.class);
                                    Toast.makeText(this, "Nombre: " + usuario.getNombre() + " | apellido: " + usuario.getApellido(), Toast.LENGTH_SHORT).show();

                                } else {
                                    Toast.makeText(this, "El usuario no existe", Toast.LENGTH_SHORT).show();
                                }
                            }

                            //binding.btnListarUsuarios.setEnabled(true);
                        });
            }

//            binding.floatingActionButton.setOnClickListener(view2 -> {
//                Intent intent = new Intent(MainActivity.this, MainActivity2.class);
//                startActivity(intent);
//            });

        //});

//        binding.btnTiempoReal.setOnClickListener(view -> {
//
//            snapshotListener = db.collection("usuarios")
//                    .addSnapshotListener((collection, error) -> {
//
//                        if (error != null) {
//                            Log.w("msg-test", "Listen failed.", error);
//                            return;
//                        }
//
//                        Log.d("msg-test", "---- Datos en tiempo real ----");
//                        for (QueryDocumentSnapshot doc : collection) {
//                            Usuario usuario = doc.toObject(Usuario.class);
//                            Log.d("msg-test", "Nombre: " + usuario.getNombre() + " | apellido: " + usuario.getApellido());
//                        }
//
//                    });
//        });

        binding.logoutBtn.setOnClickListener(view -> {
            AuthUI.getInstance().signOut(MainActivity.this)
                    .addOnCompleteListener(task -> {
                        Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                        startActivity(intent);
                        finish();
                    });
        });

//        binding.floatingActionButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                startActivity(new Intent(MainActivity.this, MainActivity2.class));
//            }
//        });

    }

//    @Override
//    protected void onPause() {
//        super.onPause();
//        if (snapshotListener != null)
//            snapshotListener.remove();
//    }

}