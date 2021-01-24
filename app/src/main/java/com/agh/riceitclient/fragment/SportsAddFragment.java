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
import android.widget.Spinner;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.agh.riceitclient.R;
import com.agh.riceitclient.dto.AddSportManDTO;
import com.agh.riceitclient.dto.UpdateUserDetailsDTO;
import com.agh.riceitclient.util.SportsListener;
import com.google.android.material.textfield.TextInputLayout;

public class SportsAddFragment extends Fragment {

    TextInputLayout nameInput, durationInput, burntInput, typeInput;
    Button btnConfirm;

    private AutoCompleteTextView dropdownText;

    private SportsListener sportsListener;
    private AddSportManDTO addSportManDTO;
    private static final String[] paths = {"item 1", "item 2", "item 3"};

    public SportsAddFragment(SportsListener sportsListener){
        this.sportsListener = sportsListener;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addSportManDTO = (AddSportManDTO) getArguments().getSerializable("addSportManDTO");
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

        btnConfirm = v.findViewById(R.id.btn_sports_add_confirm);
        btnConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                confirmSportAdding();
            }
        });


        String[] items = new String[] {
                "Item 1",
                "Longer Item 2",
                "Example of Item 3",
                "RUNNING_6",
                "Others"
        };

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
        String burntStr = burntInput.getEditText().getText().toString().trim();
        String typeStr = typeInput.getEditText().getText().toString().trim();


        addSportManDTO.setName(nameStr);
        addSportManDTO.setDuration(Integer.parseInt(durationStr));
        addSportManDTO.setKcalBurnt(Double.parseDouble(burntStr));
        addSportManDTO.setSportType(typeStr);

        sportsListener.enqueueAddSportManually(addSportManDTO);
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
