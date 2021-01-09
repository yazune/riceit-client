package com.agh.riceitclient.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;
import android.util.Pair;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.agh.riceitclient.R;
import com.agh.riceitclient.dto.RegisterDTO;

import java.util.Calendar;

public class RegisterUserDetailsActivity extends AppCompatActivity {


    ImageView backBtn;
    Button nextBtn, loginBtn;
    TextView titleText;
    RadioGroup genderRadioGroup;
    DatePicker datePicker;

    RegisterDTO registerDTO;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_user_details);

        registerDTO = (RegisterDTO) getIntent().getSerializableExtra("registerDTO");

        backBtn = findViewById(R.id.register_back_button);
        nextBtn = findViewById(R.id.register_next_button);
        loginBtn = findViewById(R.id.register_login_button);
        titleText = findViewById(R.id.register_title_text);

        genderRadioGroup = findViewById(R.id.register_user_details_gender);
        datePicker = findViewById(R.id.register_user_details_age);
    }

    public void callLastRegisterScreen(View view){

        if(!validateGender() | !validateAge()){
            return;
        }

        Intent intent = new Intent(getApplicationContext(), RegisterUserDetails2Activity.class);
        intent.putExtra("registerDTO", registerDTO);

        Pair[] pairs = new Pair[4];
        pairs[0] = new Pair<View, String>(backBtn, "transition_back_arrow_btn");
        pairs[1] = new Pair<View, String>(titleText, "transition_title_text");
        pairs[2] = new Pair<View, String>(nextBtn, "transition_next_btn");
        pairs[3] = new Pair<View, String>(loginBtn, "transition_login_btn");

        ActivityOptions activityOptions = ActivityOptions.makeSceneTransitionAnimation(RegisterUserDetailsActivity.this, pairs);
        startActivity(intent, activityOptions.toBundle());

    }

    private boolean validateGender(){
        if(genderRadioGroup.getCheckedRadioButtonId() == -1){
            Toast.makeText(this, "Please Select Gender", Toast.LENGTH_SHORT).show();
            return false;
        } else if(genderRadioGroup.getCheckedRadioButtonId() == 0){
            registerDTO.setGender("MALE");
            return true;
        } else{
            registerDTO.setGender("FEMALE");
            return true;
        }
    }

    private boolean validateAge(){
        int currentYear = Calendar.getInstance().get(Calendar.YEAR);
        int yearOfBirth = datePicker.getYear();
        int userAge = currentYear - yearOfBirth;

        if(userAge < 0){
            Toast.makeText(this, "Please choose correct date of birth", Toast.LENGTH_SHORT).show();
            return false;
        } else {
            registerDTO.setAge(userAge);
            return true;
        }
    }
}