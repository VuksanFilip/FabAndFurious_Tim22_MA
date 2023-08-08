package com.example.uberapp_tim22.fragments;

import static androidx.core.content.ContentProviderCompat.requireContext;

import android.app.AlertDialog;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import android.app.Fragment;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.Toast;

import com.example.uberapp_tim22.DTO.AssumptionDTO;
import com.example.uberapp_tim22.DTO.LocationDTO;
import com.example.uberapp_tim22.DTO.NewLocationDTO;
import com.example.uberapp_tim22.DTO.UnregisteredUserDTO;
import com.example.uberapp_tim22.MainActivity;
import com.example.uberapp_tim22.R;
import com.example.uberapp_tim22.model.enums.VehicleName;
import com.example.uberapp_tim22.service.ServiceUtils;
import com.google.android.gms.common.api.internal.UnregisterListenerMethod;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Stepper3Fragment extends Fragment {

    private View view;
    private Button nextButton;
    private RadioButton standardTypeRB, luxuryTypeRB, vanTypeRB, petTransportYesRB, petTransportNoRB, babyTransportYesRB, babyTransportNoRB;
    private VehicleName vehicleName;
    private boolean petTransport;
    private boolean babyTransport;
    private Bundle bundle = new Bundle();
    private List<LocationDTO> locations;
    private String date;
    private AssumptionDTO assumptionDTO;

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
        nextButton = view.findViewById(R.id.fragmentStepper3NextBtn);


        bundle = getArguments();
        date = getArguments().getString("date");
        locations = (List<LocationDTO>) getArguments().getSerializable("locations");

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
                String price = "Price: " + "1000";
                String time = "Time: " + "1000";
                builder.setTitle("Confirmation")
                        .setMessage(price + ", " + time)
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                bundle.putSerializable("vehicleName", vehicleName);
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

//    @Override
//    public void onActivityCreated(Bundle savedInstanceState) {
//        super.onActivityCreated(savedInstanceState);
//        Toast.makeText(getActivity(), "onActivityCreated()", Toast.LENGTH_SHORT).show();
//    }
//
//    @Override
//    public void onAttach(Context context) {
//        super.onAttach(context);
//        Toast.makeText(getActivity(), "onAttach()", Toast.LENGTH_SHORT).show();
//    }

}

//package com.example.uberapp_tim22.fragments;
//
//import android.app.AlertDialog;
//import android.app.Fragment;
//import android.app.FragmentManager;
//import android.app.FragmentTransaction;
//import android.content.DialogInterface;
//import android.os.Bundle;
//import android.util.Log;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.Button;
//import android.widget.RadioButton;
//import android.widget.Toast;
//
//import androidx.annotation.NonNull;
//import androidx.annotation.Nullable;
//
//import com.example.uberapp_tim22.DTO.AssumptionDTO;
//import com.example.uberapp_tim22.DTO.LocationDTO;
//import com.example.uberapp_tim22.DTO.NewLocationDTO;
//import com.example.uberapp_tim22.DTO.UnregisteredUserDTO;
//import com.example.uberapp_tim22.MainActivity;
//import com.example.uberapp_tim22.R;
//import com.example.uberapp_tim22.model.enums.VehicleName;
//import com.example.uberapp_tim22.service.ServiceUtils;
//
//import java.util.List;
//
//import retrofit2.Call;
//import retrofit2.Callback;
//import retrofit2.Response;
//
//public class Stepper3Fragment extends Fragment {
//
//    private View view;
//    private RadioButton standardTypeRB, luxuryTypeRB, vanTypeRB, petTransportYesRB, petTransportNoRB, babyTransportYesRB, babyTransportNoRB;
//    private Button nextButton;
//    private VehicleName vehicleName;
//    private boolean petTransport;
//    private boolean babyTransport;
//    private List<NewLocationDTO> locations;
//    private String date;
//
//    @Nullable
//    @Override
//    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
//        view = inflater.inflate(R.layout.fragment_stepper3, container, false);
//
//        // Initialize views
//        standardTypeRB = view.findViewById(R.id.fragmentStepper3StandardTypeRB);
//        luxuryTypeRB = view.findViewById(R.id.fragmentStepper3LuxuryTypeRB);
//        vanTypeRB = view.findViewById(R.id.fragmentStepper3VanTypeRB);
//        petTransportYesRB = view.findViewById(R.id.fragmentStepper3PetTransportYesRB);
//        petTransportNoRB = view.findViewById(R.id.fragmentStepper3PetTransportNoRB);
//        babyTransportYesRB = view.findViewById(R.id.fragmentStepper3BabyTransportYesRB);
//        babyTransportNoRB = view.findViewById(R.id.fragmentStepper3BabyTransportNoRB);
//        nextButton = view.findViewById(R.id.fragmentStepper3NextBtn);
//
//        // Retrieve data from previous fragment
//        Bundle bundle = getArguments();
//        if (bundle != null) {
//            date = bundle.getString("date");
//            locations = (List<NewLocationDTO>) bundle.getSerializable("locations");
//            Log.i("ASD", Integer.toString(locations.size()));
//        }
//
//        nextButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                // Get selected vehicle name
//                if (standardTypeRB.isChecked()) {
//                    vehicleName = VehicleName.STANDARD;
//                } else if (luxuryTypeRB.isChecked()) {
//                    vehicleName = VehicleName.LUXURY;
//                } else if (vanTypeRB.isChecked()) {
//                    vehicleName = VehicleName.VAN;
//                }
//
//                // Get selected pet transport option
//                petTransport = petTransportYesRB.isChecked();
//
//                // Get selected baby transport option
//                babyTransport = babyTransportYesRB.isChecked();
//
//                // Create UnregisteredUserDTO object
//                UnregisteredUserDTO unregisteredUserDTO = new UnregisteredUserDTO(locations, vehicleName.toString(), babyTransport, petTransport);
//
//                // Make network call to fetch AssumptionDTO
//                Call<AssumptionDTO> call = ServiceUtils.unregisteredUserService.getAssumption(unregisteredUserDTO);
//                call.enqueue(new Callback<AssumptionDTO>() {
//                    @Override
//                    public void onResponse(@NonNull Call<AssumptionDTO> call, @NonNull Response<AssumptionDTO> response) {
//                        if (response.isSuccessful()) {
//                            AssumptionDTO assumptionDTO = response.body();
//                            showConfirmationDialog(assumptionDTO);
//                        } else {
//                            // Handle unsuccessful response
//                            Toast.makeText(getActivity(), "Failed to fetch assumption data", Toast.LENGTH_SHORT).show();
//                        }
//                    }
//
//                    @Override
//                    public void onFailure(Call<AssumptionDTO> call, Throwable t) {
//                        // Handle network call failure
//                        Toast.makeText(getActivity(), "Network call failed: " + t.getMessage(), Toast.LENGTH_SHORT).show();
//                    }
//                });
//            }
//        });
//
//        Button backButton = view.findViewById(R.id.fragmentStepper3BackBtn);
//        backButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                getFragmentManager().popBackStack();
//            }
//        });
//
//        return view;
//    }
//
//    private void showConfirmationDialog(AssumptionDTO assumptionDTO) {
//        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
//        String price = "Cost: " + assumptionDTO.getEstimatedCost();
//        String time = "Time: " + assumptionDTO.getEstimatedTimeInMinutes();
//        builder.setTitle("Confirmation")
//                .setMessage(price + ", " + time)
//                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialog, int which) {
//                        Bundle bundle = new Bundle();
//                        bundle.putSerializable("vehicleName", vehicleName);
//                        bundle.putBoolean("petTransport", petTransport);
//                        bundle.putBoolean("babyTransport", babyTransport);
//                        bundle.putString("scheduledTime", Integer.toString(assumptionDTO.getEstimatedTimeInMinutes()));
//                        bundle.putString("cost", Integer.toString(assumptionDTO.getEstimatedCost()));
//
//
//                        Fragment fragment = new Stepper4Fragment();
//                        fragment.setArguments(bundle);
//
//                        FragmentManager fragmentManager = getFragmentManager();
//                        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
//                        fragmentTransaction.replace(R.id.fragmentStepper2, fragment);
//                        fragmentTransaction.addToBackStack(null);
//                        fragmentTransaction.commit();
//                    }
//                })
//                .setNegativeButton("No", null)
//                .show();
//    }
//}
//
//
////    @Override
////    public void onActivityCreated(Bundle savedInstanceState) {
////        super.onActivityCreated(savedInstanceState);
////        Toast.makeText(getActivity(), "onActivityCreated()", Toast.LENGTH_SHORT).show();
////    }
////
////    @Override
////    public void onAttach(Context context) {
////        super.onAttach(context);
////        Toast.makeText(getActivity(), "onAttach()", Toast.LENGTH_SHORT).show();
////    }