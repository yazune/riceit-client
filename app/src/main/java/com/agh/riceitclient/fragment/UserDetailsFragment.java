package com.agh.riceitclient.fragment;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.agh.riceitclient.R;
import com.agh.riceitclient.dto.UserDetailsGetDTO;
import com.agh.riceitclient.dto.UserDetailsUpdateDTO;
import com.agh.riceitclient.model.UserDetails;
import com.agh.riceitclient.retrofit.ServiceGenerator;
import com.agh.riceitclient.service.UserDetailsService;
import com.agh.riceitclient.listener.UserDetailsListener;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UserDetailsFragment extends Fragment implements UserDetailsListener {

    String authToken;
    UserDetailsService userDetailsService = ServiceGenerator.createService(UserDetailsService.class);

    Button btnUpdate;

    TextView usernameText, emailText;
    TextView heightAmount,weightAmount, genderType, ageAmount, palAmount;

    UserDetails userDetails = new UserDetails();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v =  inflater.inflate(R.layout.fragment_details, container, false);

        usernameText = v.findViewById(R.id.details_username);
        emailText = v.findViewById(R.id.details_email);

        heightAmount = v.findViewById(R.id.details_height_amount);
        weightAmount = v.findViewById(R.id.details_weight_amount);
        genderType = v.findViewById(R.id.details_gender);
        ageAmount = v.findViewById(R.id.details_age_amount);
        palAmount = v.findViewById(R.id.details_k_amount);

        btnUpdate = v.findViewById(R.id.btn_details_update);

        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openUserDetailsUpdateFragment();
            }
        });

        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("RiceItClient", Context.MODE_PRIVATE);
        authToken = sharedPreferences.getString("TOKEN", null); //second parameter is default

        enqueueGetUserDetails();

        return v;
    }

    public void enqueueGetUserDetails(){
        Call<UserDetailsGetDTO> call = userDetailsService.getUserDetails(authToken);
        call.enqueue(new Callback<UserDetailsGetDTO>() {
            @Override
            public void onResponse(Call<UserDetailsGetDTO> call, Response<UserDetailsGetDTO> response) {
                if (response.isSuccessful()){
                    userDetails.fillWithData(response.body());
                    updateLayout();
                }
            }

            @Override
            public void onFailure(Call<UserDetailsGetDTO> call, Throwable t) {

            }
        });
    }

    public void updateLayout(){
        usernameText.setText(userDetails.getUsername());
        emailText.setText(userDetails.getEmail());
        heightAmount.setText(String.valueOf(userDetails.getHeight()));
        weightAmount.setText(String.valueOf(userDetails.getWeight()));
        genderType.setText(userDetails.getGender());
        ageAmount.setText(String.valueOf(userDetails.getAge()));
        palAmount.setText(String.valueOf(userDetails.getPal()));
    }

    public void openUserDetailsUpdateFragment(){
        Fragment userDetailsUpdateFragment = new UserDetailsUpdateFragment((UserDetailsListener) getActivity());
        UserDetailsUpdateDTO userDetailsUpdateDTO = new UserDetailsUpdateDTO();
        userDetailsUpdateDTO.fillWithData(userDetails);

        Bundle args = new Bundle();

        args.putSerializable("updateUserDetailsDTO", userDetailsUpdateDTO);
        userDetailsUpdateFragment.setArguments(args);
        getActivity().getSupportFragmentManager()
                .beginTransaction()
                .add(R.id.main_container, userDetailsUpdateFragment, "userDetailsUpdateFragment")
                .addToBackStack(null)
                .commit();
    }

    @Override
    public void enqueueUpdateUserDetails(UserDetailsUpdateDTO userDetailsUpdateDTO) {
        Call<Void> call = userDetailsService.updateUserDetails(authToken, userDetailsUpdateDTO);
        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.isSuccessful()){
                    enqueueGetUserDetails();
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {

            }
        });
    }
}
