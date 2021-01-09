package com.agh.riceitclient.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;
import android.util.Pair;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.agh.riceitclient.R;
import com.agh.riceitclient.dto.RegisterDTO;
import com.google.android.material.textfield.TextInputLayout;

public class RegisterUserDetails2Activity extends AppCompatActivity {

    ImageView backBtn;
    Button registerBtn, loginBtn;
    TextView titleText;
    TextInputLayout heightInput, weightInput, kInput;

    RegisterDTO registerDTO;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_user_details2);

        registerDTO = (RegisterDTO) getIntent().getSerializableExtra("registerDTO");

        backBtn = findViewById(R.id.register_back_button);
        registerBtn = findViewById(R.id.register_end_button);
        loginBtn = findViewById(R.id.register_login_button);
        titleText = findViewById(R.id.register_title_text);

        heightInput = findViewById(R.id.register_user_details_2_height);
        weightInput = findViewById(R.id.register_user_details_2_weight);
        kInput = findViewById(R.id.register_user_details_2_k);
    }

    public void callLoginScreen(View view) {

        if(!validateHeight() | !validateWeight() | !validateK()){
            return;
        }

        Intent intent = new Intent(getApplicationContext(), RegisterActivity.class);

        Pair[] pairs = new Pair[4];
        pairs[0] = new Pair<View, String>(backBtn, "transition_back_arrow_btn");
        pairs[1] = new Pair<View, String>(titleText, "transition_title_text");
        pairs[2] = new Pair<View, String>(registerBtn, "transition_register_btn");
        pairs[3] = new Pair<View, String>(loginBtn, "transition_login_btn");

        ActivityOptions activityOptions = ActivityOptions.makeSceneTransitionAnimation(RegisterUserDetails2Activity.this, pairs);
        startActivity(intent, activityOptions.toBundle());

    }

    private boolean validateHeight(){
        String val = heightInput.getEditText().getText().toString().trim();

        if(val.isEmpty()){
            heightInput.setError("Field can not be empty");
            return false;
        } else if(Double.parseDouble(val) < 0) {
            heightInput.setError("Height can not be lower than 0 cm");
            return false;
        } else if(Double.parseDouble(val) > 300) {
            heightInput.setError("Height can not be bigger than 300 cm");
            return false;
        } else {
            heightInput.setError(null);
            heightInput.setErrorEnabled(false);
            return true;
        }
    }

    private boolean validateWeight(){
        String val = weightInput.getEditText().getText().toString().trim();

        if(val.isEmpty()){
            weightInput.setError("Field can not be empty");
            return false;
        } else if(Double.parseDouble(val) < 0) {
            weightInput.setError("Weight can not be lower than 0 kg");
            return false;
        } else if(Double.parseDouble(val) > 1000) {
            weightInput.setError("Weight can not be bigger than 1000 kg");
            return false;
        } else {
            weightInput.setError(null);
            weightInput.setErrorEnabled(false);
            return true;
        }
    }

    private boolean validateK(){
        String val = kInput.getEditText().getText().toString().trim();

        if(val.isEmpty()){
            kInput.setError("Field can not be empty");
            return false;
        } else if(Double.parseDouble(val) < 1.0) {
            kInput.setError("K can not be lower than 1.0");
            return false;
        } else if(Double.parseDouble(val) > 2.0) {
            kInput.setError("K can not be bigger than 2.0");
            return false;
        } else {
            kInput.setError(null);
            kInput.setErrorEnabled(false);
            return true;
        }
    }

}