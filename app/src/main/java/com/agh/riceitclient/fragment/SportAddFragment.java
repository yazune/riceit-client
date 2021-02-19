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
import com.agh.riceitclient.dto.SportAddDTO;
import com.agh.riceitclient.util.SportConstants;
import com.agh.riceitclient.listener.SportListener;
import com.google.android.material.textfield.TextInputLayout;

import java.util.HashMap;

public class SportAddFragment extends Fragment {

    TextInputLayout nameInput, durationInput, burntInput, typeInput;
    Button btnConfirm;
    CheckBox manualCheck;

    private AutoCompleteTextView dropdownText;

    private SportListener sportListener;
    private SportAddDTO sportAddDTO;

    HashMap<String, String> sportsTypeMap = SportConstants.generateMapWithNamesAsKeys();

    public SportAddFragment(SportListener sportListener){
        this.sportListener = sportListener;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        sportAddDTO = (SportAddDTO) getArguments().getSerializable("sportAddDTO");
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v =  inflater.inflate(R.layout.fragment_sports_add, container, false);
        nameInput = v.findViewById(R.id.sports_add_name);
        durationInput = v.findViewById(R.id.sports_add_duration);
        burntInput = v.findViewById(R.id.sports_add_burnt);
        typeInput = v.findViewById(R.id.sports_add_type);
        dropdownText = v.findViewById(R.id.sports_add_dropdown);
        manualCheck = v.findViewById(R.id.check_manual);

        btnConfirm = v.findViewById(R.id.btn_sports_add_confirm);

        if (sportAddDTO.getKcalBurnt() >= 0){
            manualCheck.setChecked(true);
            burntInput.setVisibility(View.VISIBLE);
        } else {
            manualCheck.setChecked(false);
            burntInput.setVisibility(View.GONE);
        }

        btnConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                confirmSportAdding();
            }
        });

        manualCheck.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b) {
                    burntInput.setVisibility(View.VISIBLE);
                } else burntInput.setVisibility(View.GONE);
            }
        });


        String[] items = SportConstants.generateArrayWithNames();

        ArrayAdapter<String> adapter = new ArrayAdapter<>(
                getActivity(),
                R.layout.dropdown_item,
                items
        );

        dropdownText.setAdapter(adapter);

        return v;
    }

    public void confirmSportAdding(){
        String nameStr = nameInput.getEditText().getText().toString().trim();
        String durationStr = durationInput.getEditText().getText().toString().trim();
        String typeStr = typeInput.getEditText().getText().toString().trim();

        sportAddDTO.setName(nameStr);
        sportAddDTO.setDuration(Integer.parseInt(durationStr));
        sportAddDTO.setSportType(sportsTypeMap.get(typeStr));

        if (manualCheck.isChecked()){
            String burntStr = burntInput.getEditText().getText().toString().trim();
            sportAddDTO.setKcalBurnt(Double.parseDouble(burntStr));
        } else {
            sportAddDTO.setKcalBurnt(-1);
        }

        sportListener.enqueueAddSport(sportAddDTO);
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
