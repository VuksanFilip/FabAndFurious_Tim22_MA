package com.example.uberapp_tim22.fragments;

import android.app.Fragment;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.uberapp_tim22.DTO.EmailDTO;
import com.example.uberapp_tim22.DTO.IdAndEmailDTO;
import com.example.uberapp_tim22.DTO.NewLocationDTO;
import com.example.uberapp_tim22.DTO.RideDTO;
import com.example.uberapp_tim22.R;
import com.example.uberapp_tim22.model.VehicleType;
import com.example.uberapp_tim22.model.enums.VehicleName;
import com.example.uberapp_tim22.service.ServiceUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class Stepper5Fragment extends Fragment {

    private Bundle bundle = new Bundle();
    private Button backButton, payButton;
    private List<NewLocationDTO> locations;
    private List<IdAndEmailDTO> passengers;
    private String scheduledTime;
    private boolean petTransport;
    private boolean babyTransport;
    private VehicleName vehicleName;
    private String myEmail;
    private TextView tvVehicleName, tvVehicleNameValue, tvPetTransport, tvPetTransportValue, tvBabyTransport, tvBabyTransportValue, tvScheduledTime, tvScheduledTimeValue, tvLocations, tvLocationsValue;
    private String departure;
    private String destination;
    private LinearLayout passengersLayout;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_stepper5, container, false);


        tvVehicleName = view.findViewById(R.id.tvVehicleName);
        tvVehicleNameValue = view.findViewById(R.id.tvVehicleNameValue);
        tvPetTransport = view.findViewById(R.id.tvPetTransport);
        tvPetTransportValue = view.findViewById(R.id.tvPetTransportValue);
        tvBabyTransport = view.findViewById(R.id.tvBabyTransport);
        tvBabyTransportValue = view.findViewById(R.id.tvBabyTransportValue);
        tvScheduledTime = view.findViewById(R.id.tvScheduledTime);
        tvScheduledTimeValue = view.findViewById(R.id.tvScheduledTimeValue);
        tvLocations = view.findViewById(R.id.tvLocations);
        tvLocationsValue = view.findViewById(R.id.tvLocationsValue);
        passengersLayout = view.findViewById(R.id.passengersLayout);


        payButton = view.findViewById(R.id.fragmentStepper5PayBtn);
        backButton = view.findViewById(R.id.fragmentStepper5BackBtn);
        bundle = getArguments();
        scheduledTime = getArguments().getString("date");
        petTransport = getArguments().getBoolean("petTranposrt");
        babyTransport = getArguments().getBoolean("babyTransport");
        locations = (List<NewLocationDTO>) getArguments().getSerializable("locations");
        passengers = (List<IdAndEmailDTO>) getArguments().getSerializable("passengers");
        vehicleName = (VehicleName) getArguments().getSerializable("vehicleName");
        myEmail = getArguments().getString("myEmail");
        departure = getArguments().getString("departure");
        destination = getArguments().getString("destination");


        tvVehicleNameValue.setText(vehicleName.toString());
        tvPetTransportValue.setText(String.valueOf(petTransport));
        tvBabyTransportValue.setText(String.valueOf(babyTransport));
        tvScheduledTimeValue.setText(scheduledTime);
        tvLocationsValue.setText(departure + " " + destination);

        for (IdAndEmailDTO passenger : passengers) {
            TextView passengerTextView = null;
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
                passengerTextView = new TextView(getContext());
            }
            passengerTextView.setLayoutParams(new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.WRAP_CONTENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT
            ));
            passengerTextView.setText(passenger.getEmail());
            passengersLayout.addView(passengerTextView);
        }


        RideDTO ride = new RideDTO(locations, passengers, vehicleName.toString(), babyTransport, petTransport, "2023-08-08T18:32:28Z");

        payButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                for (IdAndEmailDTO email : passengers) {
                    Log.i("Passengers", email.getId().toString());
                }
                for (NewLocationDTO location : locations) {
                    Log.i("Locations", location.getDestination().getAddress());
                }
                Log.i("Date", scheduledTime);
                Log.i("Pet transport", Boolean.toString(petTransport));
                Log.i("Baby transport", Boolean.toString(babyTransport));
                Log.i("Vehicle type", vehicleName.toString());

                createRide(ride);
            }
        });

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getFragmentManager().popBackStack();
            }
        });
        return view;
    }

    private void createRide(RideDTO ride) {
        Call<Void> call = ServiceUtils.rideService.createRide(ride);
        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(@NonNull Call<Void> call, @NonNull Response<Void> response) {
                if (!response.isSuccessful()) {
                    return;
                }

                if (response.code() == 204) {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                        Toast.makeText(getContext(), "Email not confirmed!", Toast.LENGTH_SHORT).show();
                    }
                    return;
                }
                if (response.code() == 400) {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                        Toast.makeText(getContext(), "OK!", Toast.LENGTH_SHORT).show();
                    }
                    return;
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
            }
        });
    }
}
