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
import android.widget.RadioButton;

import com.example.uberapp_tim22.MainActivity;
import com.example.uberapp_tim22.R;
import com.example.uberapp_tim22.model.enums.VehicleName;

public class Stepper3Fragment extends Fragment {

    View view;
    Button nextButton;
    RadioButton standardTypeRB, luxuryTypeRB, vanTypeRB, petTransportYesRB, petTransportNoRB, babyTransportYesRB, babyTransportNoRB;
    VehicleName vehicleName;
    boolean petTransport;
    boolean babyTransport;
    Bundle bundle = new Bundle();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_stepper3, container, false);
        standardTypeRB = view.findViewById(R.id.fragmentStepper3StandardTypeRB);
        luxuryTypeRB = view.findViewById(R.id.fragmentStepper3LuxuryTypeRB);
        vanTypeRB = view.findViewById(R.id.fragmentStepper3VanTypeRB);
        petTransportYesRB = view.findViewById(R.id.fragmentStepper3PetTransportYesRB);
        petTransportNoRB = view.findViewById(R.id.fragmentStepper3PetTransportNoRB);
        babyTransportYesRB = view.findViewById(R.id.fragmentStepper3BabyTransportYesRB);
        babyTransportYesRB = view.findViewById(R.id.fragmentStepper3BabyTransportYesRB);
        bundle = getArguments();

        nextButton = view.findViewById(R.id.fragmentStepper3NextBtn);

        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (standardTypeRB.isChecked()) {
                    vehicleName = VehicleName.STANDARD;
                } else if (luxuryTypeRB.isChecked()) {
                    vehicleName = VehicleName.LUXURY;
                } else if (vanTypeRB.isChecked()){
                    vehicleName = VehicleName.VAN;
                }

                if (petTransportYesRB.isChecked()) {
                    petTransport = true;
                } else if (petTransportNoRB.isChecked()) {
                    petTransport = false;
                }

                if (babyTransportYesRB.isChecked()) {
                    babyTransport = true;
                } else if (babyTransportNoRB.isChecked()) {
                    babyTransport = false;
                }

                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setTitle("Confirmation")
                        .setMessage("Price: 1000din, time: 5h")
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                bundle.putSerializable("vehicleType", vehicleName);
                                bundle.putBoolean("petTransport", petTransport);
                                bundle.putBoolean("babyTransport", babyTransport);
                                Fragment fragment = new Stepper4Fragment();
                                FragmentManager fragmentManager = getFragmentManager();
                                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                                fragment.setArguments(bundle);
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
                getFragmentManager().popBackStack();
            }
        });

        return view;
    }

}