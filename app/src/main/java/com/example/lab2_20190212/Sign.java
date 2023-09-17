package com.example.lab2_20190212;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.concurrent.atomic.AtomicReference;

public class Sign extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign);

        AtomicReference<String> fullname = new AtomicReference<>("");
        AtomicReference<String> user = new AtomicReference<>("");
        AtomicReference<String> foto = new AtomicReference<>("");
        EditText nombre = findViewById(R.id.editText2);
        EditText apellido = findViewById(R.id.editText);
        EditText correo = findViewById(R.id.editText3);
        EditText contra = findViewById(R.id.editText4);
        String url = "https://randomuser.me/api/";

        StringRequest postRequest = new StringRequest(Request.Method.GET, url, response -> {
            try {
                JSONObject jsonObject = new JSONObject(response);
                JSONArray resultsArray = jsonObject.getJSONArray("results");
                JSONObject firstResult = resultsArray.getJSONObject(0);

                String firstName = firstResult.getJSONObject("name").getString("first");
                String lastName = firstResult.getJSONObject("name").getString("last");
                String email = firstResult.getString("email");
                fullname.set(firstName + " " + lastName);

                JSONObject loginObject = firstResult.getJSONObject("login");
                String password = loginObject.getString("password");
                String username = loginObject.getString("username");

                user.set(username);

                JSONObject pictureObject = firstResult.getJSONObject("picture");
                String photoUrl = pictureObject.getString("large");

                foto.set(photoUrl);

                nombre.setText(firstName);
                apellido.setText(lastName);
                correo.setText(email);
                contra.setText(password);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }, error -> contra.setText("error"));

        Volley.newRequestQueue(this).add(postRequest);
        if(tengoInternet()==true){
            Toast.makeText(this, "Tienes internet", Toast.LENGTH_SHORT).show();
        }else{
            Toast.makeText(this, "No tienes internet", Toast.LENGTH_SHORT).show();
        }

        CheckBox checkBox = findViewById(R.id.checkBox);
        Button button = findViewById(R.id.button2);
        checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                button.setEnabled(isChecked);
            }
        });
        button.setOnClickListener(view ->{
            Intent intent = new Intent(Sign.this, Menu.class);
            intent.putExtra("full", fullname.get());
            intent.putExtra("user", user.get());
            intent.putExtra("url_foto", foto.get());
            startActivity(intent);
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