package com.agh.riceitclient.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.agh.riceitclient.R;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class LoginActivity extends AppCompatActivity {

    TextView titleText;
    ImageView backBtn;
    Button loginBtn, createAccountBtn, forgetPasswordBtn;
    TextInputLayout usernameOrEmailInput, passwordInput;
    TextInputEditText usernameOrEmailEditText;


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
        usernameOrEmailEditText = findViewById(R.id.login_username_or_email_input_text);

        usernameOrEmailEditText.setText((String)getIntent().getSerializableExtra("username"));
    }



}