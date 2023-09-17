package com.example.lab2_20190212;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class Contador extends AppCompatActivity {


    TextView tvContador;
    Button btnIniciar;
    int contador = 104;

    boolean click = false;


    int delay = 10000;
    Handler handler = new Handler(Looper.getMainLooper());
    Runnable contadorRunnable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contador);

        tvContador = findViewById(R.id.tvContador);

        actualizarContador();

        btnIniciar = findViewById(R.id.btnIniciar);
        btnIniciar.setOnClickListener(v -> {
            click = true;
        });
        contadorRunnable = new Runnable() {
            @Override
            public void run() {
                contador++;

                if (contador > 226) {
                    contador = 104;
                }
                actualizarContador();
                if(click == true){
                    delay = delay - 1000;
                    click = false;
                }
                handler.postDelayed(this, delay);
            }
        };
        handler.postDelayed(contadorRunnable, delay);

        if(tengoInternet()){
            Toast.makeText(this, "Tienes internet", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "No tienes internet", Toast.LENGTH_SHORT).show();}
    }

    private void actualizarContador() {
        tvContador.setText(String.valueOf(contador));
    }

    public boolean tengoInternet() {
        ConnectivityManager manager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = manager.getActiveNetworkInfo();
        boolean tieneInternet = activeNetworkInfo != null && activeNetworkInfo.isConnected();
        Log.d("msg-test", "Internet: " + tieneInternet);
        return tieneInternet;
    }
}