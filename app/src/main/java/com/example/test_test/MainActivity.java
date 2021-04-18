package com.example.test_test;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.security.SecureRandom;
import java.security.cert.X509Certificate;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import retrofit2.http.GET;





public class MainActivity extends AppCompatActivity {





    private Button getTask;
    private TextView textView;
    private  TextView text_text1;



    private Button allowPermission;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        String TAG = "234";



        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        allowPermission = findViewById(R.id.allowPermission);
        textView = findViewById(R.id.text_text);
        getTask = findViewById(R.id.getTasks);
        text_text1 = findViewById(R.id.text_text1);

        getTask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String texttext1 = "Тестовый запуск";
                text_text1.setText(texttext1);
                RequestQueue queue = Volley.newRequestQueue(MainActivity.this);
                String url ="https://accessibilitylistner.herokuapp.com/api?id=42";


                JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                        (Request.Method.GET,
                                url,
                                null,
                                new Response.Listener<JSONObject>() {

                                    @Override
                                    public void onResponse(JSONObject response) {
                                        try {
                                            textView.setText("Response: " + response.get("tasks"));
                                        } catch (JSONException e) {
                                            e.printStackTrace();
                                            Log.e(TAG,"te"+e);
                                        }


                                    }
                                },
                                new Response.ErrorListener() {

                                    @Override
                                    public void onErrorResponse(VolleyError error) {
                                        // TODO: Handle error
                                        textView.setText(R.string.test);
                                       Log.e(TAG,"t"+error);
                                    }

                                });


queue.add(jsonObjectRequest);

            }});

        allowPermission.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Settings.ACTION_ACCESSIBILITY_SETTINGS));
            }
        });
    }
}