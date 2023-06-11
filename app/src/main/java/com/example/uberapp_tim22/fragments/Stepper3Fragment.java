package com.example.uberapp_tim22.fragments;

import static androidx.core.content.ContentProviderCompat.requireContext;

import android.app.AlertDialog;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import android.app.Fragment;
import android.widget.Button;

import com.example.uberapp_tim22.MainActivity;
import com.example.uberapp_tim22.R;

public class Stepper3Fragment extends Fragment {


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_stepper3, container, false);
        Button nextButton = view.findViewById(R.id.fragmentStepper3NextBtn);
        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setTitle("Confirmation")
                        .setMessage("Price: 1000din, time: 5h")
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Fragment fragment = new Stepper4Fragment();
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

        Button backButton = view.findViewById(R.id.fragmentStepper3BackBtn);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Navigate back to the previous fragment
                getFragmentManager().popBackStack();
            }
        });


        return view;
    }

}