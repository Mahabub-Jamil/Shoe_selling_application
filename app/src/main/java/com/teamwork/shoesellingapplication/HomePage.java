package com.teamwork.shoesellingapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

public class HomePage extends AppCompatActivity {

    TextView textView;
    Button button;
    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);
        textView = findViewById(R.id.text1);
        button = findViewById(R.id.buttonok);
        progressBar = findViewById(R.id.progressBar);
        progressBar.setVisibility(View.GONE);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressBar.setVisibility(View.VISIBLE);
                Parsing();
            }
        });


    }

    private void Parsing() {
        StringRequest stringRequest = new StringRequest(Request.Method.GET, "https://mj01861.000webhostapp.com/Shoe%20Store/app.php",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        textView.setText(response);
                        progressBar.setVisibility(View.GONE);
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                textView.setText("Invalid");
                progressBar.setVisibility(View.GONE);
            }
        }
        );
        RequestQueue requestQueue = Volley.newRequestQueue(HomePage.this);
        requestQueue.add(stringRequest);

    }
}