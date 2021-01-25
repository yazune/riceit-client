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

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.agh.riceitclient.R;
import com.agh.riceitclient.dto.UpdateSportDTO;
import com.agh.riceitclient.dto.UpdateUserDetailsDTO;
import com.agh.riceitclient.util.SportConstants;
import com.agh.riceitclient.util.SportsListener;
import com.google.android.material.textfield.TextInputLayout;

import java.util.HashMap;

public class SportsUpdateFragment extends Fragment {

    SportsListener sportsListener;
    UpdateSportDTO updateSportDTO;

    TextInputLayout nameInput, durationInput, burntInput, typeInput;
    Button btnConfirm;

    AutoCompleteTextView dropdownText;

    HashMap<String, String> sportTypesMap = SportConstants.generateMapWithNamesAsKeys();

    public SportsUpdateFragment(SportsListener sportsListener){
        this.sportsListener = sportsListener;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        updateSportDTO = (UpdateSportDTO) getArguments().getSerializable("updateSportDTO");
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

        btnConfirm = v.findViewById(R.id.btn_sports_update_confirm);
        btnConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                confirmUpdatingSport();
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
        nameInput.getEditText().setText(String.valueOf(updateSportDTO.getName()));
        durationInput.getEditText().setText(String.valueOf(updateSportDTO.getDuration()));
        burntInput.getEditText().setText(String.valueOf(updateSportDTO.getKcalBurnt()));
    }


    public void confirmUpdatingSport(){
        String nameStr = nameInput.getEditText().getText().toString().trim();
        String durationStr = durationInput.getEditText().getText().toString().trim();
        String burntStr = burntInput.getEditText().getText().toString().trim();
        String typeStr = typeInput.getEditText().getText().toString().trim();

        updateSportDTO.setName(nameStr);
        updateSportDTO.setDuration(Integer.parseInt(durationStr));
        updateSportDTO.setKcalBurnt(Double.parseDouble(burntStr));
        updateSportDTO.setSportType(sportTypesMap.get(typeStr));

        sportsListener.enqueueUpdateSport(updateSportDTO);
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
