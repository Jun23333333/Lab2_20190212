package com.example.lab2_20190212;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.SystemClock;
import android.view.View;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class Cronometro extends AppCompatActivity {

    private Chronometer chronometer;
    private Button btnIniciar, btnPausar, btnDetener, btnDespausar, btnLimpiar;
    private long pauseOffset;
    private boolean isRunning;

    private SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cronometro);
        chronometer = findViewById(R.id.chronometer);
        btnIniciar = findViewById(R.id.btnIniciar);
        btnPausar = findViewById(R.id.btnPausar);
        btnDetener = findViewById(R.id.btnDetener);
        btnDespausar = findViewById(R.id.btnDespausar);
        btnLimpiar = findViewById(R.id.btnLimpiar);
        sharedPreferences = getPreferences(Context.MODE_PRIVATE);

        if (isRunning) {
            chronometer.setBase(SystemClock.elapsedRealtime() - pauseOffset);
            chronometer.start();}

        btnDespausar.setOnClickListener(v -> despausarCronometro());

        btnIniciar.setOnClickListener(v -> iniciarCronometro());

        btnPausar.setOnClickListener(v -> pausarCronometro());

        btnDetener.setOnClickListener(v -> detenerCronometro());

        btnLimpiar.setOnClickListener(v -> limpiarCronometro());

        if(tengoInternet()){
            Toast.makeText(this, "Tienes internet", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "No tienes internet", Toast.LENGTH_SHORT).show();}

    }

    public boolean tengoInternet() {
        ConnectivityManager manager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = manager.getActiveNetworkInfo();
        boolean tieneInternet = activeNetworkInfo != null && activeNetworkInfo.isConnected();
        return tieneInternet;
    }

    private void iniciarCronometro() {
        if (!isRunning) {
            chronometer.setBase(SystemClock.elapsedRealtime() - pauseOffset);
            chronometer.start();
            isRunning = true;
        }
    }

    private void pausarCronometro() {
        if (isRunning) {
            chronometer.stop();
            pauseOffset = SystemClock.elapsedRealtime() - chronometer.getBase();
            isRunning = false;
        }
    }

    private void detenerCronometro() {
        chronometer.stop();
        if (isRunning) {
            pauseOffset = SystemClock.elapsedRealtime() - chronometer.getBase();
            isRunning = false;
        }
    }

    private void limpiarCronometro() {
        chronometer.setBase(SystemClock.elapsedRealtime());
        pauseOffset = 0;
        if (isRunning) {
            chronometer.stop();
            isRunning = false;
        }
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putBoolean("isRunning", isRunning);
        outState.putLong("pauseOffset", pauseOffset);
    }

    @Override
    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        isRunning = savedInstanceState.getBoolean("isRunning");
        pauseOffset = savedInstanceState.getLong("pauseOffset");

        if (isRunning) {
            chronometer.setBase(SystemClock.elapsedRealtime() - pauseOffset);
            chronometer.start();
        }
    }

    private void despausarCronometro() {
        if (!isRunning) {
            isRunning = true;
            long currentTime = SystemClock.elapsedRealtime(); // Obtiene el tiempo actual
            chronometer.setBase(currentTime - pauseOffset); // Restaura el tiempo base del cronómetro
            chronometer.start(); // Inicia el cronómetro
        }
    }

}
