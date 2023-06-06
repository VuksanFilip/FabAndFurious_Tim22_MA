package com.example.uberapp_tim22.fragments;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
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

import com.example.uberapp_tim22.R;
import com.google.android.material.textfield.TextInputEditText;

public class Stepper2Fragment extends Fragment {

    private TextInputEditText departureAddressEditText;
    private LinearLayout layoutList;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_stepper2, container, false);
        departureAddressEditText = view.findViewById(R.id.departureAddressEditText);
        layoutList = view.findViewById(R.id.layout_list);
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
                // Navigate to fragment_stepper3
                Fragment fragment = new Stepper3Fragment();
                FragmentManager fragmentManager = getFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.fragmentStepper2, fragment);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
            }
        });


        return view;
    }

    private void loadFragment(Fragment fragment) {
        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.fragmentStepper2, fragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }

    private void addAddressToList() {
        String address = departureAddressEditText.getText().toString();
        if (!TextUtils.isEmpty(address)) {
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
                    // Remove the corresponding address from the layout
                    layoutList.removeView(itemLayout);
                }
            });

            // Add the TextView and delete button to the itemLayout
            itemLayout.addView(textView);
            itemLayout.addView(deleteButton);

            // Add the itemLayout to the layoutList
            layoutList.addView(itemLayout);

            departureAddressEditText.setText("");
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
        bundle.size();
        String asd = bundle.getString("asd");
        Log.i("JBT", asd);
        Toast.makeText(getActivity(), "onAttach()", Toast.LENGTH_SHORT).show();
    }
}