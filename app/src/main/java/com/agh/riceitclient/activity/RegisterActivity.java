package com.agh.riceitclient.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;
import android.util.Pair;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.agh.riceitclient.R;
import com.agh.riceitclient.dto.ExistsEmailDTO;
import com.agh.riceitclient.dto.RegisterDTO;
import com.agh.riceitclient.retrofit.ServiceGenerator;
import com.agh.riceitclient.service.UserService;
import com.agh.riceitclient.dto.BooleanDTO;
import com.agh.riceitclient.dto.ExistsUsernameDTO;
import com.google.android.material.textfield.TextInputLayout;


import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegisterActivity extends AppCompatActivity {

    ImageView backBtn;
    Button nextBtn, loginBtn;
    TextView titleText;
    TextInputLayout usernameInput, emailInput, passwordInput;


    RegisterDTO registerDTO;

    UserService userService = ServiceGenerator.createService(UserService.class);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        registerDTO = new RegisterDTO();

        backBtn = findViewById(R.id.register_back_button);
        nextBtn = findViewById(R.id.register_next_button);
        loginBtn = findViewById(R.id.register_login_button);
        titleText = findViewById(R.id.register_title_text);

        usernameInput = findViewById(R.id.register_username_input);
        emailInput = findViewById(R.id.register_email_input);
        passwordInput = findViewById(R.id.register_password_input);
    }

    public void checkUsernameAvailability(View view) {

        if (!validateUsername() | !validateEmail() | !validatePassword()){
            return;
        }

        String val = usernameInput.getEditText().getText().toString().trim();

        ExistsUsernameDTO existsUsernameDTO = new ExistsUsernameDTO(val);
        Call<BooleanDTO> call = userService.existsByUsername(existsUsernameDTO);

        call.enqueue(new Callback<BooleanDTO>() {

            @Override
            public void onResponse(Call<BooleanDTO> call, Response<BooleanDTO> response) {
                if (response.isSuccessful()){
                    if(response.body().getBool()){
                        usernameInput.setError("Username is already in use");
                        return;
                    }
                    else{
                        usernameInput.setError(null);
                        usernameInput.setErrorEnabled(false);
                        registerDTO.setUsername(val);
                        checkEmailAvailability(view);
                    }
                } else {
                    Toast.makeText(RegisterActivity.this, "username check failure", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<BooleanDTO> call, Throwable t) {
                Toast.makeText(RegisterActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void checkEmailAvailability(View view) {
        String val = emailInput.getEditText().getText().toString().trim();

        ExistsEmailDTO existsEmailDTO = new ExistsEmailDTO(val);
        Call<BooleanDTO> call = userService.existsByEmail(existsEmailDTO);

        call.enqueue(new Callback<BooleanDTO>() {

            @Override
            public void onResponse(Call<BooleanDTO> call, Response<BooleanDTO> response) {
                if (response.isSuccessful()){
                    if(response.body().getBool()){
                        emailInput.setError("Email is already in use");
                        return;
                    }
                    else{
                        emailInput.setError(null);
                        emailInput.setErrorEnabled(false);
                        registerDTO.setEmail(val);
                        callNextRegisterScreen(view);
                    }
                } else {
                    Toast.makeText(RegisterActivity.this, "email check failure", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<BooleanDTO> call, Throwable t) {
                Toast.makeText(RegisterActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private boolean validateUsername(){
        String val = usernameInput.getEditText().getText().toString().trim();

        if(val.isEmpty()){
            usernameInput.setError("Field can not be empty");
            return false;
        } else if(val.length() > 20) {
            usernameInput.setError("Username is too large");
            return false;
        } else {
            usernameInput.setError(null);
            usernameInput.setErrorEnabled(false);
            return true;
        }
    }
    private boolean validateEmail(){
        String val = emailInput.getEditText().getText().toString().trim();

        if(val.isEmpty()){
            emailInput.setError("Field can not be empty");
            return false;
        } else if(!Patterns.EMAIL_ADDRESS.matcher(val).matches()) {
            emailInput.setError("Incorrect email format");
            return false;
        } else {
            emailInput.setError(null);
            emailInput.setErrorEnabled(false);
            return true;
        }
    }
    private boolean validatePassword(){
        String val = passwordInput.getEditText().getText().toString().trim();

        if(val.isEmpty()){
            passwordInput.setError("Field can not be empty");
            return false;
        } else if(val.length() < 7) {
            passwordInput.setError("Password should have at least 8 characters");
            return false;
        } else {
            passwordInput.setError(null);
            passwordInput.setErrorEnabled(false);
            registerDTO.setPassword(val);
            return true;
        }
    }

    private void callNextRegisterScreen(View view){
        Intent intent = new Intent(getApplicationContext(), RegisterUserDetailsActivity.class);
        intent.putExtra("registerDTO", registerDTO);

        Pair[] pairs = new Pair[4];
        pairs[0] = new Pair<View, String>(backBtn, "transition_back_arrow_btn");
        pairs[1] = new Pair<View, String>(titleText, "transition_title_text");
        pairs[2] = new Pair<View, String>(nextBtn, "transition_next_btn");
        pairs[3] = new Pair<View, String>(loginBtn, "transition_login_btn");

        ActivityOptions activityOptions = ActivityOptions.makeSceneTransitionAnimation(RegisterActivity.this, pairs);
        startActivity(intent, activityOptions.toBundle());
    }

}