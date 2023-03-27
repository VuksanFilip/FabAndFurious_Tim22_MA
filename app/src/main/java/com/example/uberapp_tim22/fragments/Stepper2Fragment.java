package com.example.uberapp_tim22.fragments;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import android.app.Fragment;
import android.widget.Toast;

import com.example.uberapp_tim22.R;

public class Stepper2Fragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        Toast.makeText(getActivity(), "onActivityCreated()", Toast.LENGTH_SHORT).show();
        Log.i("JBT", "JBT");
        return inflater.inflate(R.layout.fragment_stepper2, container, false);

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
        Log.i("JBT", "JBG");
        Toast.makeText(getActivity(), "onAttach()", Toast.LENGTH_SHORT).show();
    }
}