package com.agh.riceitclient.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ActivityOptions;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Pair;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.agh.riceitclient.R;
import com.agh.riceitclient.dto.LoginDTO;
import com.agh.riceitclient.dto.TokenDTO;
import com.agh.riceitclient.retrofit.ServiceGenerator;
import com.agh.riceitclient.service.AuthService;
import com.google.android.material.textfield.TextInputLayout;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {

    TextView titleText;
    Button loginBtn, forgetPasswordBtn;
    TextInputLayout usernameOrEmailInput, passwordInput;

    AuthService authService = ServiceGenerator.createService(AuthService.class);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        titleText = findViewById(R.id.login_title_text);
        loginBtn = findViewById(R.id.login_login_button);
        forgetPasswordBtn = findViewById(R.id.login_forget_password_button);

        usernameOrEmailInput = findViewById(R.id.login_username_or_email_input);
        passwordInput = findViewById(R.id.login_password_input);

        String username = (String) getIntent().getSerializableExtra("username");
        usernameOrEmailInput.getEditText().setText(username);
    }

    public void sendLoginRequest(View view){

        String usernameOrEmailVal = usernameOrEmailInput.getEditText().getText().toString().trim();
        String passwordVal = passwordInput.getEditText().getText().toString().trim();

        LoginDTO loginDTO = new LoginDTO(usernameOrEmailVal, passwordVal);
        Call<TokenDTO> call = authService.login(loginDTO);

        call.enqueue(new Callback<TokenDTO>() {
            @Override
            public void onResponse(Call<TokenDTO> call, Response<TokenDTO> response) {
                if(response.isSuccessful()){
                    String authToken = response.body().getType() + " " + response.body().getToken();
                    SharedPreferences sharedPreferences = getSharedPreferences("RiceItClient", Context.MODE_PRIVATE);
                    sharedPreferences.edit().putString("TOKEN", authToken).apply();

//                    Intent intent = new Intent(getApplicationContext(), MealsActivity.class);
//                    startActivity(intent);

                      Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                      startActivity(intent);
                }
                else {
                    Toast.makeText(LoginActivity.this, "Wrong credentials!", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<TokenDTO> call, Throwable t) {

            }
        });
    }





}