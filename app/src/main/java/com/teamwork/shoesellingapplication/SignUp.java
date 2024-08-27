package com.teamwork.shoesellingapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.teamwork.shoesellingapplication.databinding.ActivitySignUpBinding;

import java.util.HashMap;
import java.util.Map;

public class SignUp extends AppCompatActivity {

    String name;
    String email;
    String phone;
    String address;
    String password;
    ProgressBar progressBar;

    ActivitySignUpBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivitySignUpBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        binding.signinbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SignUp.this,SignIn.class));
            }
        });

        binding.signupbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                name=binding.edittextname.getText().toString();
                email=binding.edittextemail.getText().toString();
                password=binding.edittextpass.getText().toString();
                phone=binding.edittextphone.getText().toString();
                address=binding.edittextlocation.getText().toString();
                binding.progressbar.setVisibility(View.VISIBLE);
                if (email.isEmpty() || !Patterns.EMAIL_ADDRESS.matcher(email).matches()){
                    binding.edittextemail.setError("Valid Email Required");
                    binding.edittextemail.requestFocus();
                    binding.progressbar.setVisibility(View.GONE);

                } else if (password.length() < 6){
                    binding.edittextpass.setError("Password Must be at least six characters long");
                    binding.edittextpass.requestFocus();
                    binding.progressbar.setVisibility(View.GONE);

                } else if (phone.isEmpty() || phone.length() != 10){
                    binding.edittextphone.setError("Valid Phone Number Required");
                    binding.edittextphone.requestFocus();
                    binding.progressbar.setVisibility(View.GONE);

                } else if (name.isEmpty()){
                    binding.edittextname.setError("Fullname Required");
                    binding.edittextname.requestFocus();
                    binding.progressbar.setVisibility(View.GONE);
                } else if (address.isEmpty()){
                    binding.edittextlocation.setError("Address Required");
                    binding.edittextlocation.requestFocus();
                    binding.progressbar.setVisibility(View.GONE);
                }
                else{
                    RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
                    String url ="http://192.168.0.109/ShoeStore/Signin/register.php";

                    StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                            new Response.Listener<String>() {
                                @Override
                                public void onResponse(String response) {
                                    binding.progressbar.setVisibility(View.GONE);
                                    if (response.equals("Success")){
                                        //custom toast message
                                        Toast.makeText(SignUp.this, "Register Succesfull",
                                                Toast.LENGTH_LONG).show();
                                        ///////////////////

                                        startActivity(new Intent(SignUp.this, SignIn.class));
                                        finish();
                                    }
                                    else if (response.equals("emailphone")) {
                                        binding.edittextemail.setError("Email already exists");
                                        binding.edittextemail.requestFocus();
                                        binding.edittextemail.setError("Phone number already exists");
                                        binding.edittextemail.requestFocus();
                                    }
                                    else if (response.equals("email")) {
                                        binding.edittextemail.setError("Email already exists");
                                        binding.edittextemail.requestFocus();
                                    }
                                    else if(response.equals("phone")){
                                        binding.edittextphone.setError("Phone number already exists");
                                        binding.edittextphone.requestFocus();
                                    }

                                    else {
                                        Toast.makeText(SignUp.this, response, Toast.LENGTH_SHORT).show();
                                    }
                                }
                            }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Log.e("Volley Error", "Error occurred: " + error.getMessage());
                            Toast.makeText(SignUp.this, "Registration Error. Please try again later.", Toast.LENGTH_SHORT).show();
                            binding.progressbar.setVisibility(View.GONE);
                        }
                    }){
                        protected Map<String, String> getParams(){
                            Map<String, String> paramV = new HashMap<>();
                            paramV.put("name", name);
                            paramV.put("email", email);
                            paramV.put("phone", phone);
                            paramV.put("password", password);
                            paramV.put("address", address);

                            return paramV;
                        }
                    };
                    queue.add(stringRequest);

                }

            }
        });






        binding.signinbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SignUp.this,SignIn.class));
                finish();
            }

        });
    }
}