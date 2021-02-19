package com.agh.riceitclient.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;
import android.util.Pair;
import android.view.View;
import android.widget.Button;

import com.agh.riceitclient.R;

public class WelcomeScreenActivity extends AppCompatActivity {

    Button loginBtn, registerBtn, additionalBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome_screen);

        loginBtn = findViewById(R.id.welcome_login_button);
        registerBtn = findViewById(R.id.welcome_register_button);
    }

    public void callLoginScreen(View view){
        Intent intent = new Intent(getApplicationContext(), LoginActivity.class);

        Pair[] pairs = new Pair[1];
        pairs[0] = new Pair<View, String>(findViewById(R.id.welcome_login_button),"transition_login");

        ActivityOptions activityOptions = ActivityOptions.makeSceneTransitionAnimation(WelcomeScreenActivity.this, pairs);
        startActivity(intent, activityOptions.toBundle());
    }

    public void callRegisterScreen(View view){
        Intent intent = new Intent(getApplicationContext(), RegisterActivity.class);

        Pair[] pairs = new Pair[1];
        pairs[0] = new Pair<View, String>(findViewById(R.id.welcome_register_button),"transition_register");

        ActivityOptions activityOptions = ActivityOptions.makeSceneTransitionAnimation(WelcomeScreenActivity.this, pairs);
        startActivity(intent, activityOptions.toBundle());
    }
}