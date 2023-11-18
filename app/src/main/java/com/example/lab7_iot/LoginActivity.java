package com.example.lab7_iot;

import static android.content.ContentValues.TAG;

import androidx.activity.result.ActivityResultLauncher;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.example.lab7_iot.databinding.ActivityLoginBinding;
import com.example.lab7_iot.entity.GestorActivity;
import com.example.lab7_iot.entity.Usuario;
import com.firebase.ui.auth.AuthMethodPickerLayout;
import com.firebase.ui.auth.AuthUI;
import com.firebase.ui.auth.FirebaseAuthUIActivityResultContract;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Arrays;

public class LoginActivity extends AppCompatActivity {
    ActivityLoginBinding binding;

    FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();

        FirebaseUser currentUser = firebaseAuth.getCurrentUser();


        binding.button2.setOnClickListener(view -> {

            if (currentUser != null) { //user logged-in
                Intent intent = new Intent(LoginActivity.this, GestorActivity.class);
                //intent.putExtra("usuario",user);
                startActivity(intent);
                finish();
            } else { //cliente

                String correo = binding.textInputLayoutCorreo.getEditText().getText().toString();
                String contrasena = binding.textInputLayoutContrasena.getEditText().getText().toString();


                Usuario usuario  = new Usuario();
                usuario.setCorreo(correo);
                usuario.setContrasena(contrasena);
                usuario.setRol("Cliente");

                db.collection("usuarios").add(usuario).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        Log.d("msg-test","Data guardada exitosamente");
                    }
                }).addOnFailureListener(e -> e.printStackTrace());

                Intent intent = new Intent(LoginActivity.this, GestorActivity.class);
                //intent.putExtra("usuario",user);
                startActivity(intent);
                finish();


            }

        });










//        if (currentUser != null) { //user logged-in
//            db = FirebaseFirestore.getInstance(); //con firestore
//
//            Log.d("msg","uid: " + firebaseAuth.getCurrentUser().getUid());
//            Log.d("msg","name: " + firebaseAuth.getCurrentUser().getDisplayName());
//            Log.d("msg","email: " + firebaseAuth.getCurrentUser().getEmail());
//
//
//        } else {
//
//            Usuario usuario = new Usuario();
//            usuario.setCorreo(correo);
//            usuario.setContrasena(contrasena);
//
//            db.collection("usuarios")
//                    .add(usuario)
//                    .addOnSuccessListener(unused -> {
//                        Toast.makeText(this, "Usuario grabado", Toast.LENGTH_SHORT).show();
//                    })
//                    .addOnFailureListener(e -> {
//                        Toast.makeText(this, "Algo pasó al guardar ", Toast.LENGTH_SHORT).show();
//                    });
//
//        }

    }

    /* launchers tienen 2 partes {
        1: contrato,
        2: callback: que hacer luego de finalizado el contrato
    } */

    ActivityResultLauncher<Intent> signInLauncher = registerForActivityResult(
            new FirebaseAuthUIActivityResultContract(),
            result -> {
                if (result.getResultCode() == RESULT_OK) {
                    FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();

                    FirebaseUser user = firebaseAuth.getCurrentUser();
                    if (user != null) {
                        Log.d(TAG, "Firebase uid: " + user.getUid());
                        Log.d(TAG, "Display name: " + user.getDisplayName());
                        Log.d(TAG, "Email: " + user.getEmail());


                    } else {
                        Log.d(TAG, "user == null");
                    }
                } else {
                    Log.d(TAG, "Canceló el Log-in");
                }
                //binding.loginBtn.setEnabled(true);
            }
    );

}