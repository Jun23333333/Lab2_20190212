package com.example.lab2_20190212;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

public class Menu extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        if(tengoInternet()==true){
            Toast.makeText(this, "Tienes internet", Toast.LENGTH_SHORT).show();
        }else{
            Toast.makeText(this, "No tienes internet", Toast.LENGTH_SHORT).show();
        }

        Intent intent = getIntent();
        String fullName = intent.getStringExtra("full");
        String user = intent.getStringExtra("user");
        String photoUrl = intent.getStringExtra("url_foto");
        ImageView imageView = findViewById(R.id.imageView);
        Picasso.get().load(photoUrl).into(imageView);
        TextView nombre = findViewById(R.id.textView5);
        TextView username = findViewById(R.id.textView4);
        nombre.setText(fullName);
        username.setText(user);

        Button cronometro = findViewById(R.id.button3);
        Button contador = findViewById(R.id.button4);

        cronometro.setOnClickListener(view ->{
            Intent intent1 = new Intent(Menu.this, Cronometro.class);
            startActivity(intent1);
        });

        contador.setOnClickListener(view ->{
            Intent intent2 = new Intent(Menu.this, Contador.class);
            startActivity(intent2);
        });
    }


    public boolean tengoInternet() {
        ConnectivityManager manager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo activeNetworkInfo = manager.getActiveNetworkInfo();
        boolean tieneInternet = activeNetworkInfo != null && activeNetworkInfo.isConnected();

        Log.d("msg-test", "Internet: " + tieneInternet);

        return tieneInternet;
    }
}