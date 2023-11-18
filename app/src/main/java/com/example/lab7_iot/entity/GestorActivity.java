package com.example.lab7_iot.entity;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.os.Bundle;

import com.example.lab7_iot.R;
import com.example.lab7_iot.databinding.ActivityGestorBinding;

public class GestorActivity extends AppCompatActivity {

    ActivityGestorBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityGestorBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());



    }
}