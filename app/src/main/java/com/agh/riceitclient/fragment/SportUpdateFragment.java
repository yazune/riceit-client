package com.agh.riceitclient.fragment;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.agh.riceitclient.R;
import com.agh.riceitclient.util.SportUpdateTransfer;
import com.agh.riceitclient.util.SportConstants;
import com.agh.riceitclient.listener.SportListener;
import com.google.android.material.textfield.TextInputLayout;

import java.util.HashMap;

public class SportUpdateFragment extends Fragment {

    SportListener sportListener;
    SportUpdateTransfer sportUpdateTransfer;

    TextInputLayout nameInput, durationInput, burntInput, typeInput;
    Button btnConfirm;
    CheckBox manualCheck;

    AutoCompleteTextView dropdownText;

    HashMap<String, String> sportTypesMap = SportConstants.generateMapWithNamesAsKeys();

    public SportUpdateFragment(SportListener sportListener){
        this.sportListener = sportListener;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        sportUpdateTransfer = (SportUpdateTransfer) getArguments().getSerializable("sportUpdateTransfer");
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View v =  inflater.inflate(R.layout.fragment_sports_update, container, false);
        nameInput = v.findViewById(R.id.sports_update_name);
        durationInput = v.findViewById(R.id.sports_update_duration);
        burntInput = v.findViewById(R.id.sports_update_burnt);
        typeInput = v.findViewById(R.id.sports_update_type);
        dropdownText = v.findViewById(R.id.sports_update_dropdown);

        manualCheck = v.findViewById(R.id.check_manual);

        btnConfirm = v.findViewById(R.id.btn_sports_update_confirm);
        btnConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                confirmUpdatingSport();
            }
        });

        manualCheck.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b) {
                    burntInput.setVisibility(View.VISIBLE);
                } else
                    burntInput.setVisibility(View.GONE);
            }
        });

        String[] items = SportConstants.generateArrayWithNames();

        ArrayAdapter<String> adapter = new ArrayAdapter<>(
                getActivity(),
                R.layout.dropdown_item,
                items
        );

        dropdownText.setAdapter(adapter);

        fillFormWithData();

        return v;
    }

    public void fillFormWithData(){
        nameInput.getEditText().setText(String.valueOf(sportUpdateTransfer.getName()));
        durationInput.getEditText().setText(String.valueOf(sportUpdateTransfer.getDuration()));
        burntInput.getEditText().setText(String.valueOf(sportUpdateTransfer.getKcalBurnt()));
    }


    public void confirmUpdatingSport(){
        String nameStr = nameInput.getEditText().getText().toString().trim();
        String durationStr = durationInput.getEditText().getText().toString().trim();
        String typeStr = typeInput.getEditText().getText().toString().trim();

        sportUpdateTransfer.setName(nameStr);
        sportUpdateTransfer.setDuration(Integer.parseInt(durationStr));
        sportUpdateTransfer.setSportType(sportTypesMap.get(typeStr));

        if (manualCheck.isChecked()){
            String burntStr = burntInput.getEditText().getText().toString().trim();
            sportUpdateTransfer.setKcalBurnt(Double.parseDouble(burntStr));
        } else
            sportUpdateTransfer.setKcalBurnt(-1);


        sportListener.enqueueUpdateSport(sportUpdateTransfer);
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
