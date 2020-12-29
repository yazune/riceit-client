package com.agh.riceitclient;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;
import android.util.Pair;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class RegisterUserDetails2Activity extends AppCompatActivity {

    ImageView backBtn;
    Button registerBtn, loginBtn;
    TextView titleText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_register_user_details2);

        // Hooks
        backBtn = findViewById(R.id.register_back_button);
        registerBtn = findViewById(R.id.register_end_button);
        loginBtn = findViewById(R.id.register_login_button);
        titleText = findViewById(R.id.register_title_text);

    }

    public void callLoginScreen(View view) {

        // hardcoded RegisterActivity.class because I still don't have a LoginActivity.class
        Intent intent = new Intent(getApplicationContext(), RegisterActivity.class);

        //Add Transition
        //todo - check if Pair should be a basic one or androixX
        Pair[] pairs = new Pair[4];
        pairs[0] = new Pair<View, String>(backBtn, "transition_back_arrow_btn");
        pairs[1] = new Pair<View, String>(titleText, "transition_title_text");
        pairs[2] = new Pair<View, String>(registerBtn, "transition_register_btn");
        pairs[3] = new Pair<View, String>(loginBtn, "transition_login_btn");

        //todo - it should be handled by if/else with checking Build.Version_Codes.LOLLIPOP
        ActivityOptions activityOptions = ActivityOptions.makeSceneTransitionAnimation(RegisterUserDetails2Activity.this, pairs);
        startActivity(intent, activityOptions.toBundle());

    }
}