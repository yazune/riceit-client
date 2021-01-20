package com.agh.riceitclient.fragment;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.agh.riceitclient.R;
import com.agh.riceitclient.dto.UpdateGoalsDTO;
import com.agh.riceitclient.util.GoalsListener;
import com.google.android.material.textfield.TextInputLayout;

public class GoalsUpdateFragment extends Fragment {

    GoalsListener goalsListener;
    Button btnConfirm;
    TextInputLayout kcalInput, protInput, fatInput, carbInput;
    UpdateGoalsDTO updateGoalsDTO;

    public GoalsUpdateFragment(GoalsListener goalsListener){
        this.goalsListener = goalsListener;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        updateGoalsDTO = (UpdateGoalsDTO) getArguments().getSerializable("updateGoalsDTO");
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v =  inflater.inflate(R.layout.fragment_goals_update, container, false);

        kcalInput = v.findViewById(R.id.goals_update_kcal);
        protInput = v.findViewById(R.id.goals_update_prot);
        fatInput = v.findViewById(R.id.goals_update_fat);
        carbInput = v.findViewById(R.id.goals_update_carb);


        fillFormWithData();

        btnConfirm = v.findViewById(R.id.btn_goals_update_confirm);
        btnConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                confirmUpdatingGoals();
            }
        });

        return v;
    }

    public void fillFormWithData(){
        kcalInput.getEditText().setText(String.valueOf(updateGoalsDTO.getManKcal()));
        protInput.getEditText().setText(String.valueOf(updateGoalsDTO.getManProtein()));
        fatInput.getEditText().setText(String.valueOf(updateGoalsDTO.getManFat()));
        carbInput.getEditText().setText(String.valueOf(updateGoalsDTO.getManCarbohydrate()));
    }

    public void confirmUpdatingGoals(){

        String kcalStr = kcalInput.getEditText().getText().toString().trim();
        String protStr = protInput.getEditText().getText().toString().trim();
        String fatStr = fatInput.getEditText().getText().toString().trim();
        String carbStr = carbInput.getEditText().getText().toString().trim();


        double kcal = Double.parseDouble(kcalStr);
        double prot = Double.parseDouble(protStr);
        double fat = Double.parseDouble(fatStr);
        double carb = Double.parseDouble(carbStr);

        updateGoalsDTO.setManKcal(kcal);
        updateGoalsDTO.setManProtein(prot);
        updateGoalsDTO.setManFat(fat);
        updateGoalsDTO.setManCarbohydrate(carb);

        goalsListener.enqueueUpdateGoals(updateGoalsDTO);
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
