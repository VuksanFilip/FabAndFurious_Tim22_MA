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
import java.io.Serializable;
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
    Bundle bundle = new Bundle();
    private Geocoder geocoder;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_stepper2, container, false);
        departureAddressEditText = view.findViewById(R.id.departureAddressEditText);
        layoutList = view.findViewById(R.id.layout_list);
        bundle = getArguments();
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
                bundle.putSerializable("locations", (Serializable) locations);
                fragment.setArguments(bundle);
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
        if (!TextUtils.isEmpty(address)) {
            GetDestination(geocoder);

            NewLocationWithAddressDTO njuDeparture = locations.get(locations.size()-1).getDestination();
            NewLocationWithAddressDTO njuDestination = new NewLocationWithAddressDTO(address, doubleDestinationLat, doubleDestinationLong);
            NewLocationDTO location = new NewLocationDTO(njuDeparture, njuDestination);
            locations.add(location);

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
                    if (itemLayout == lastAddedItemLayout) {
                        layoutList.removeView(itemLayout);
                        locations.remove(locations.size()-1);
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
                        Toast.makeText(getActivity(), "You can only delete the last added item.", Toast.LENGTH_SHORT).show();
                    }
                }
            });

            itemLayout.addView(textView);
            itemLayout.addView(deleteButton);

            layoutList.addView(itemLayout);
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
        Toast.makeText(getActivity(), "onActivityCreated()", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        Toast.makeText(getActivity(), "onAttach()", Toast.LENGTH_SHORT).show();
    }
}