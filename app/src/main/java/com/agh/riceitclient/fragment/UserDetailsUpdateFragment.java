package com.agh.riceitclient.fragment;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.agh.riceitclient.R;
import com.agh.riceitclient.dto.UserDetailsUpdateDTO;
import com.agh.riceitclient.listener.UserDetailsListener;
import com.google.android.material.textfield.TextInputLayout;

public class UserDetailsUpdateFragment extends Fragment {

    TextInputLayout heightInput, weightInput, ageInput, palInput;
    RadioGroup genderRadioGroup;
    RadioButton maleRadio, femaleRadio;
    Button btnConfirm;

    UserDetailsUpdateDTO userDetailsUpdateDTO;
    UserDetailsListener userDetailsListener;

    public UserDetailsUpdateFragment(UserDetailsListener userDetailsListener){
        this.userDetailsListener = userDetailsListener;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        userDetailsUpdateDTO = (UserDetailsUpdateDTO) getArguments().getSerializable("updateUserDetailsDTO");
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v =  inflater.inflate(R.layout.fragment_details_update, container, false);

        heightInput = v.findViewById(R.id.details_update_height);
        weightInput = v.findViewById(R.id.details_update_weight);
        ageInput = v.findViewById(R.id.details_update_age);
        palInput = v.findViewById(R.id.details_update_k);
        genderRadioGroup = v.findViewById(R.id.details_update_gender_group);
        maleRadio = v.findViewById(R.id.details_update_radio_male);
        femaleRadio = v.findViewById(R.id.details_update_radio_female);

        fillFormWithData();

        btnConfirm = v.findViewById(R.id.btn_details_update_confirm);

        btnConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                confirmUpdatingDetails();
            }
        });

        return v;
    }

    public void fillFormWithData(){
        heightInput.getEditText().setText(String.valueOf(userDetailsUpdateDTO.getHeight()));
        weightInput.getEditText().setText(String.valueOf(userDetailsUpdateDTO.getWeight()));
        ageInput.getEditText().setText(String.valueOf(userDetailsUpdateDTO.getAge()));
        palInput.getEditText().setText(String.valueOf(userDetailsUpdateDTO.getPal()));

        if (userDetailsUpdateDTO.getGender().equals("MALE")){
            maleRadio.setChecked(true);
            femaleRadio.setChecked(false);
        } else {
            maleRadio.setChecked(false);
            femaleRadio.setChecked(true);
        }
    }

    public void confirmUpdatingDetails(){
        UserDetailsUpdateDTO userDetailsUpdateDTO = new UserDetailsUpdateDTO();
        String heightStr = heightInput.getEditText().getText().toString().trim();
        String weightStr = weightInput.getEditText().getText().toString().trim();
        String ageStr = ageInput.getEditText().getText().toString().trim();
        String palStr = palInput.getEditText().getText().toString().trim();

        double height = Double.parseDouble(heightStr);
        double weight = Double.parseDouble(weightStr);
        int age = Integer.parseInt(ageStr);
        double pal = Double.parseDouble(palStr);


        if (genderRadioGroup.getCheckedRadioButtonId() == -1){
            Toast.makeText(getActivity(), "Please Select Gender", Toast.LENGTH_SHORT).show();
        } else if(maleRadio.isChecked()){
            userDetailsUpdateDTO.setGender("MALE");
        } else{
            userDetailsUpdateDTO.setGender("FEMALE");
        }


        userDetailsUpdateDTO.setHeight(height);
        userDetailsUpdateDTO.setWeight(weight);
        userDetailsUpdateDTO.setAge(age);
        userDetailsUpdateDTO.setPal(pal);


        userDetailsListener.enqueueUpdateUserDetails(userDetailsUpdateDTO);
        hideKeyboard();
        getActivity().getSupportFragmentManager().popBackStack();
    }

    public void hideKeyboard() {
        View view = getActivity().getCurrentFocus();
        if (view != null) {
            InputMethodManager inputManager = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
            inputManager.hideSoftInputFromWindow(view.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
        }
    }
}
