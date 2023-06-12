package com.example.uberapp_tim22.fragments;

import android.app.AlertDialog;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.auth0.android.jwt.JWT;
import com.example.uberapp_tim22.DTO.ResponseLoginDTO;
import com.example.uberapp_tim22.DriverMainActivity;
import com.example.uberapp_tim22.PassengerMainActivity;
import com.example.uberapp_tim22.R;
import com.example.uberapp_tim22.UserLoginActivity;
import com.example.uberapp_tim22.service.ServiceUtils;
import com.google.android.material.textfield.TextInputEditText;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Stepper4Fragment extends Fragment {

    private TextInputEditText emailEditText;
    private LinearLayout layoutEmailList;
    private Bundle bundle = new Bundle();
    private Button buttonAdd, nextButton;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_stepper4, container, false);
        emailEditText = view.findViewById(R.id.EmailEditText);
        layoutEmailList = view.findViewById(R.id.layout_email_list);
        buttonAdd = view.findViewById(R.id.buttonAddEmail);
        nextButton = view.findViewById(R.id.fragmentStepper4NextBtn);
        bundle = getArguments();

        boolean bol = getArguments().getBoolean("petTransport");
        String date = getArguments().getString("date");
        Log.i("ASD", Boolean.toString(bol));
        Log.i("ASD", date);

        buttonAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addAddressToList();
            }
        });

        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setTitle("Waiting for others conformation")
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Fragment fragment = new Stepper5Fragment();
                                FragmentManager fragmentManager = getFragmentManager();
                                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                                fragmentTransaction.replace(R.id.fragmentStepper2, fragment);
                                fragmentTransaction.addToBackStack(null);
                                fragmentTransaction.commit();
                            }
                        })
                        .setNegativeButton("No", null)
                        .show();
            }
        });


        return view;
    }


    private void checkIfExist(){
        Call<ResponseLoginDTO> call = ServiceUtils.userService.login(loginDTO);
        call.enqueue(new Callback<ResponseLoginDTO>() {

            @Override
            public void onResponse(@NonNull Call<ResponseLoginDTO> call, @NonNull Response<ResponseLoginDTO> response) {

                if(!response.isSuccessful()) return;
                if(response.code() == 204){
                    Toast.makeText(UserLoginActivity.this, "Email not confirmed!", Toast.LENGTH_SHORT).show();
                    return;
                }

                ResponseLoginDTO loginResponse = response.body();
                JWT jwt = new JWT(loginResponse.getAccessToken());

                Long id = jwt.getClaim("id").asLong();
                String email = jwt.getClaim("sub").asString();
                String role = jwt.getClaim("role").asString();

                setToken(loginResponse);
                Log.e(role, role);
                if(role.equalsIgnoreCase("PASSENGER")){
                    setPreferences(id, email, role, loginResponse);
                    startActivity(new Intent(UserLoginActivity.this, PassengerMainActivity.class));
                }
                else if(role.equalsIgnoreCase("DRIVER")) {
                    setPreferences(id, email, role, loginResponse);
                    setTokenPreference(loginResponse.getAccessToken(), loginResponse.getRefreshToken());
                    startActivity(new Intent(UserLoginActivity.this, DriverMainActivity.class));
                }
            }

        }


    private void addAddressToList() {
        String address = emailEditText.getText().toString();
        if (!TextUtils.isEmpty(address)) {

            LinearLayout itemLayout = new LinearLayout(getActivity());
            itemLayout.setOrientation(LinearLayout.HORIZONTAL);
            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            itemLayout.setLayoutParams(layoutParams);

            TextView textView = new TextView(getActivity());
            textView.setText(address);
            textView.setTextSize(18);
            LinearLayout.LayoutParams textViewParams = new LinearLayout.LayoutParams(
                    0, ViewGroup.LayoutParams.WRAP_CONTENT, 1);
            textView.setLayoutParams(textViewParams);

            ImageView deleteButton = new ImageView(getActivity());
            deleteButton.setImageResource(R.drawable.ic_delete);
            LinearLayout.LayoutParams deleteButtonParams = new LinearLayout.LayoutParams(
                    ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            deleteButton.setLayoutParams(deleteButtonParams);

            deleteButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    layoutEmailList.removeView(itemLayout);
                }
            });

            itemLayout.addView(textView);
            itemLayout.addView(deleteButton);

            layoutEmailList.addView(itemLayout);

            emailEditText.setText("");
        }
    }

}
