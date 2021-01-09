package com.agh.riceitclient.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.agh.riceitclient.R;
import com.agh.riceitclient.dto.LoginDTO;
import com.agh.riceitclient.dto.TokenDTO;
import com.agh.riceitclient.retrofit.AuthToken;
import com.agh.riceitclient.retrofit.ServiceGenerator;
import com.agh.riceitclient.service.AuthService;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class LoginActivity extends AppCompatActivity {

    TextView titleText;
    ImageView backBtn;
    Button loginBtn, createAccountBtn, forgetPasswordBtn;
    TextInputLayout usernameOrEmailInput, passwordInput;

    AuthService authService = ServiceGenerator.createService(AuthService.class);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        titleText = findViewById(R.id.login_title_text);
        backBtn = findViewById(R.id.login_back_button);
        loginBtn = findViewById(R.id.login_login_button);
        createAccountBtn = findViewById(R.id.login_create_account_button);
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
                    TokenDTO tokenDTO = response.body();
                    AuthToken.addToken(tokenDTO.getType(), tokenDTO.getToken());
                    Toast.makeText(LoginActivity.this, "You have been successfully logged in\nToken: " + AuthToken.getToken(), Toast.LENGTH_LONG).show();
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