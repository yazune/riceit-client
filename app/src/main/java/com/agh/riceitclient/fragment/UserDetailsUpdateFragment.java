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

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.agh.riceitclient.R;
import com.agh.riceitclient.dto.UpdateUserDetailsDTO;
import com.agh.riceitclient.util.DetailsListener;
import com.google.android.material.textfield.TextInputLayout;

public class UserDetailsUpdateFragment extends Fragment {

    TextInputLayout heightInput, weightInput, ageInput, kInput;
    RadioGroup genderRadioGroup;
    RadioButton maleRadio, femaleRadio;
    Button btnConfirm;

    UpdateUserDetailsDTO updateUserDetailsDTO;
    DetailsListener detailsListener;

    public UserDetailsUpdateFragment(DetailsListener detailsListener){
        this.detailsListener = detailsListener;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        updateUserDetailsDTO = (UpdateUserDetailsDTO) getArguments().getSerializable("updateUserDetailsDTO");
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v =  inflater.inflate(R.layout.fragment_details_update, container, false);

        heightInput = v.findViewById(R.id.details_update_height);
        weightInput = v.findViewById(R.id.details_update_weight);
        ageInput = v.findViewById(R.id.details_update_age);
        kInput = v.findViewById(R.id.details_update_k);
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
        heightInput.getEditText().setText(String.valueOf(updateUserDetailsDTO.getHeight()));
        weightInput.getEditText().setText(String.valueOf(updateUserDetailsDTO.getWeight()));
        ageInput.getEditText().setText(String.valueOf(updateUserDetailsDTO.getAge()));
        kInput.getEditText().setText(String.valueOf(updateUserDetailsDTO.getK()));

        if (updateUserDetailsDTO.getGender().equals("MALE")){
            maleRadio.toggle();
        } else {
            femaleRadio.toggle();
        }
    }

    public void confirmUpdatingDetails(){
        String heightStr = heightInput.getEditText().getText().toString().trim();
        String weightStr = weightInput.getEditText().getText().toString().trim();
        String ageStr = ageInput.getEditText().getText().toString().trim();
        String kStr = kInput.getEditText().getText().toString().trim();

        double height = Double.parseDouble(heightStr);
        double weight = Double.parseDouble(weightStr);
        int age = Integer.parseInt(ageStr);
        double k = Double.parseDouble(kStr);

        String gender;

        if(genderRadioGroup.getCheckedRadioButtonId() == 0){
            gender = "MALE";
        } else {
            gender = "FEMALE";
        }

        updateUserDetailsDTO.setHeight(height);
        updateUserDetailsDTO.setWeight(weight);
        updateUserDetailsDTO.setAge(age);
        updateUserDetailsDTO.setK(k);
        updateUserDetailsDTO.setGender(gender);

        detailsListener.enqueueUpdateUserDetails(updateUserDetailsDTO);
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
