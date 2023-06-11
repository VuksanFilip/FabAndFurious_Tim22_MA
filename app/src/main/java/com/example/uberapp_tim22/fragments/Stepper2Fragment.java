package com.example.uberapp_tim22.fragments;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import android.app.Fragment;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.uberapp_tim22.DTO.NewLocationDTO;
import com.example.uberapp_tim22.DTO.NewLocationWithAddressDTO;
import com.example.uberapp_tim22.DTO.NewRideDTO;
import com.example.uberapp_tim22.R;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.material.textfield.TextInputEditText;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Stepper2Fragment extends Fragment {

    private TextInputEditText departureAddressEditText;
    private LinearLayout layoutList;
    private List<NewLocationDTO> locations;
    private NewLocationWithAddressDTO newDeparture;
    private NewLocationWithAddressDTO newDestination = new NewLocationWithAddressDTO();
    private double doubleDestinationLong;
    private double doubleDestinationLat;
//    private NewLocationDTO location;
    private Geocoder geocoder;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_stepper2, container, false);
        departureAddressEditText = view.findViewById(R.id.departureAddressEditText);
        layoutList = view.findViewById(R.id.layout_list);
        locations = (List<NewLocationDTO>) getArguments().getSerializable("locations");
        geocoder = new Geocoder(getActivity());

        Button buttonAdd = view.findViewById(R.id.buttonAdd);

        buttonAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addAddressToList();
            }
        });

        Button nextButton = view.findViewById(R.id.fragmentStepper2NextBtn);
        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment fragment = new Stepper3Fragment();
                FragmentManager fragmentManager = getFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.fragmentStepper2, fragment);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
            }
        });

        Button backButton = view.findViewById(R.id.fragmentStepper2BackBtn);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getFragmentManager().popBackStack();
            }
        });


        return view;
    }

    private LinearLayout lastAddedItemLayout;

    private void addAddressToList() {
        String address = departureAddressEditText.getText().toString();
        Log.i("address", address);
        if (!TextUtils.isEmpty(address)) {
            GetDestination(geocoder);

            NewLocationWithAddressDTO njuDeparture = locations.get(locations.size()-1).getDestination();
            NewLocationWithAddressDTO njuDestination = new NewLocationWithAddressDTO(address, doubleDestinationLat, doubleDestinationLong);
            NewLocationDTO location = new NewLocationDTO(njuDeparture, njuDestination);
            locations.add(location);

            // Create a new LinearLayout to hold the address and delete button
            LinearLayout itemLayout = new LinearLayout(getActivity());
            itemLayout.setOrientation(LinearLayout.HORIZONTAL);
            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            itemLayout.setLayoutParams(layoutParams);

            // Create a new TextView to display the address
            TextView textView = new TextView(getActivity());
            textView.setText(address);
            textView.setTextSize(18); // Set the desired text size in SP
            LinearLayout.LayoutParams textViewParams = new LinearLayout.LayoutParams(
                    0, ViewGroup.LayoutParams.WRAP_CONTENT, 1);
            textView.setLayoutParams(textViewParams);

            // Create a new ImageView for the X button
            ImageView deleteButton = new ImageView(getActivity());
            deleteButton.setImageResource(R.drawable.ic_delete); // Replace with the appropriate image resource
            LinearLayout.LayoutParams deleteButtonParams = new LinearLayout.LayoutParams(
                    ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            deleteButton.setLayoutParams(deleteButtonParams);

            // Set click listener for the delete button
            deleteButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // Remove the last added item from the layout
                    if (itemLayout == lastAddedItemLayout) {
                        layoutList.removeView(itemLayout);
                        locations.remove(locations.size()-1);
                        for(NewLocationDTO loc : locations){
                            Log.i("asd", loc.getDeparture().getAddress() + " " + loc.getDestination().getAddress());
                        }
                        // Update the reference to the new last added item
                        int childCount = layoutList.getChildCount();
                        if (childCount > 0) {
                            View lastChild = layoutList.getChildAt(childCount - 1);
                            if (lastChild instanceof LinearLayout) {
                                lastAddedItemLayout = (LinearLayout) lastChild;
                            }
                        } else {
                            lastAddedItemLayout = null;
                        }
                    } else {
                        // Notify the user that only the last added item can be deleted
                        Toast.makeText(getActivity(), "You can only delete the last added item.", Toast.LENGTH_SHORT).show();
                    }
                }
            });

            // Add the TextView and delete button to the itemLayout
            itemLayout.addView(textView);
            itemLayout.addView(deleteButton);

            // Add the itemLayout to the layoutList
            layoutList.addView(itemLayout);

            // Update the reference to the last added item
            lastAddedItemLayout = itemLayout;

            departureAddressEditText.setText("");
        }
    }

    private void GetDestination(Geocoder geocoder){
        List<Address> destinationAddressList;
        try {
            destinationAddressList = geocoder.getFromLocationName(departureAddressEditText.getText().toString(), 1);

            if (destinationAddressList != null) {

                doubleDestinationLat = destinationAddressList.get(0).getLatitude();
                doubleDestinationLong = destinationAddressList.get(0).getLongitude();

            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }



    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        Log.i("JBT", "JBL");
        Toast.makeText(getActivity(), "onActivityCreated()", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        Bundle bundle = getArguments();

        //        bundle.size();
//        String asd = bundle.getString("date");
//        Log.i("JBT", asd);
        Toast.makeText(getActivity(), "onAttach()", Toast.LENGTH_SHORT).show();
    }
}