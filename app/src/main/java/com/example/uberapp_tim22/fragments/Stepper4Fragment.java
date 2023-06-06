package com.example.uberapp_tim22.fragments;

import android.app.AlertDialog;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.DialogInterface;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.example.uberapp_tim22.R;
import com.google.android.material.textfield.TextInputEditText;

public class Stepper4Fragment extends Fragment {

    private TextInputEditText emailEditText;
    private LinearLayout layoutEmailList;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_stepper4, container, false);
        emailEditText = view.findViewById(R.id.EmailEditText);
        layoutEmailList = view.findViewById(R.id.layout_email_list);
        Button buttonAdd = view.findViewById(R.id.buttonAddEmail);
        buttonAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addAddressToList();
            }
        });

        Button nextButton = view.findViewById(R.id.fragmentStepper4NextBtn);
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


    private void loadFragment(Fragment fragment) {
        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.fragmentStepper2, fragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
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
