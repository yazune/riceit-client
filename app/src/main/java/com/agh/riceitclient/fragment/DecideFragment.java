package com.agh.riceitclient.fragment;

import android.app.ActionBar;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.agh.riceitclient.R;
import com.agh.riceitclient.listener.MealListener;

public class DecideFragment extends DialogFragment {
    TextView titleText, descriptionText;
    Button cancelBtn, confirmBtn;
    private long dataToRemove;
    private MealListener mealListener;
    private String dataType;


    public DecideFragment(MealListener mealListener){
        this.mealListener = mealListener;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.dialog_decide, container, false);
        titleText = v.findViewById(R.id.dialog_decide_title);
        descriptionText = v.findViewById(R.id.dialog_decide_description);
        cancelBtn = v.findViewById(R.id.dialog_decide_button_cancel);
        confirmBtn = v.findViewById(R.id.dialog_decide_button_confirm);


        cancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getDialog().dismiss();
            }
        });

        confirmBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boolean isMeal = dataType.equals("meal");
                mealListener.removeMealOrFood(isMeal, dataToRemove);
                getDialog().dismiss();
            }
        });

        return v;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        dataToRemove = getArguments().getLong("dataToRemove");
        dataType = getArguments().getString("dataType");
    }

    @Override
    public void onStart() {
        super.onStart();
        getDialog().getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT, ActionBar.LayoutParams.WRAP_CONTENT);
    }
}
