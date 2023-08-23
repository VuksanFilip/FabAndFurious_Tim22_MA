package com.example.uberapp_tim22;

import static android.app.PendingIntent.getActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.uberapp_tim22.DTO.NewLocationDTO;
import com.example.uberapp_tim22.DTO.NewLocationWithAddressDTO;
import com.example.uberapp_tim22.fragments.ChatFragment;
import com.example.uberapp_tim22.fragments.DrawRouteFragment;
import com.example.uberapp_tim22.fragments.PassengerLiveChatFragment;
import com.example.uberapp_tim22.fragments.Stepper1Fragment;
import com.example.uberapp_tim22.tools.FragmentTransition;
import com.google.android.gms.maps.model.LatLng;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class PassengerMapActivity extends AppCompatActivity {

    private Button fragmentStepper1GetCoridnatesBtn;
    private DrawRouteFragment  drawRouteFragment;
    private List<NewLocationDTO> locations = new ArrayList<>();
    private String departureAddress, destinationAddress, driverVehicleAddress;
    private double doubleDepartureLat, doubleDepartureLong, doubleDestinationLat, doubleDestinationLong, doubleDriverLocationLat, doubleDriverLocationLong;
    private Long myId, driverId,rideId, myIdPreference;
    private FragmentManager fm = getSupportFragmentManager();
    private FragmentTransaction fragmentTransition = fm.beginTransaction();
    private PassengerLiveChatFragment chatFragment = new PassengerLiveChatFragment();
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor spEditor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_passenger_map);

        fragmentStepper1GetCoridnatesBtn = (Button) findViewById(R.id.getCoordinates);
        sharedPreferences = this.getSharedPreferences("preferences", Context.MODE_PRIVATE);
        spEditor = sharedPreferences.edit();


        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {


            driverVehicleAddress = bundle.getString("driverVehicleAddress");
            departureAddress = bundle.getString("departure");
            destinationAddress = bundle.getString("destination");
            myId = bundle.getLong("myId");
            driverId = bundle.getLong("driverId");
            rideId = bundle.getLong("rideId");

            Log.d("Departure", departureAddress);
            Log.d("Destination", destinationAddress);
            Log.d("Address", driverVehicleAddress);
            Log.d("MyId", String.valueOf(myId));
            Log.d("DriverId", String.valueOf(driverId));
            Log.d("RideId", String.valueOf(rideId));

            spEditor.putLong("pref_myId", myId);
            spEditor.putLong("pref_driverId", driverId);
            spEditor.putLong("pref_rideId", rideId);

        }

        Toolbar toolbar = findViewById(R.id.mapToolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("FAB Car");

        drawRouteFragment = DrawRouteFragment.newInstance();
        FragmentTransition.to(drawRouteFragment, this, false);

        fragmentTransition.add(R.id.fragmentChat, chatFragment);
        fragmentTransition.commit();
    }

    @Override
    protected void onResume() {

        super.onResume();
//        fragmentStepper1GetCoridnatesBtn = findViewById(R.id.getCoordinates);
//        fragmentStepper1GetCoridnatesBtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                buttonGetCoordinatesMap(v);
//            }
//        });
    }

    private void buttonGetCoordinatesMap(View view) {
        Geocoder geocoder = new Geocoder(this);
        try {
            GetDeparture(geocoder);
            GetDestination(geocoder);
            GetDriverLocation(geocoder);
            drawRouteFragment.drawLines();

        } catch (NullPointerException e) {
            showPopup("Error", "An error occurred while getting coordinates.");
            e.printStackTrace();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.login_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int itemId = item.getItemId();

        if (itemId == R.id.menuHistory) {
            Intent intent = new Intent(this, PassangerRideHistory.class);
            startActivity(intent);
            return true;
        }

        if (itemId == R.id.menuAccount) {
            Intent intent = new Intent(this, PassengerAccountActivity.class);
            startActivity(intent);
            return true;
        }

        if (itemId == R.id.menuLogOut) {
            deletePreferences();
            Intent intent = new Intent(this, UserLoginActivity.class);
            startActivity(intent);
            return true;

        }
        return super.onOptionsItemSelected(item);
    }

    private void deletePreferences(){
        SharedPreferences sharedPreferences = getSharedPreferences("preferences", Context.MODE_PRIVATE);
        SharedPreferences.Editor spEditor = sharedPreferences.edit();
        spEditor.clear().commit();
    }

    private void showPopup(String title, String message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(title)
                .setMessage(message)
                .setPositiveButton("OK", null)
                .show();
    }


    private void GetDeparture(Geocoder geocoder) {
        List<Address> departureAddressList;
        try {
            departureAddressList = geocoder.getFromLocationName(departureAddress, 1);

            if (departureAddressList != null && departureAddressList.size() > 0) {
                doubleDepartureLat = departureAddressList.get(0).getLatitude();
                doubleDepartureLong = departureAddressList.get(0).getLongitude();
                Log.i("Departure lat",String.valueOf(doubleDepartureLat));

                LatLng departureLocation = new LatLng(doubleDepartureLat, doubleDepartureLong);
                drawRouteFragment.addDepartureMarker(departureLocation);
                locations.add(new NewLocationDTO());
            } else {
                Toast.makeText(this, "No departure address found", Toast.LENGTH_SHORT).show();
            }
        } catch (IOException | SecurityException e) {
            e.printStackTrace();
        } catch (IndexOutOfBoundsException e) {
            e.printStackTrace();
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("Error")
                    .setMessage("IndexOutOfBoundsException occurred. Please check your inputs.")
                    .setPositiveButton("OK", null)
                    .show();
        }
    }


    private void GetDestination(Geocoder geocoder) {
        List<Address> destinationAddressList;
        try {
            destinationAddressList = geocoder.getFromLocationName(destinationAddress, 1);

            if (destinationAddressList != null && destinationAddressList.size() > 0) {
                doubleDestinationLat = destinationAddressList.get(0).getLatitude();
                doubleDestinationLong = destinationAddressList.get(0).getLongitude();

                LatLng destinationLocation = new LatLng(doubleDestinationLat, doubleDestinationLong);
                drawRouteFragment.addDestinationMarker(destinationLocation);
            } else {
                Toast.makeText(this, "No destination address found", Toast.LENGTH_SHORT).show();
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (IndexOutOfBoundsException e) {
            e.printStackTrace();
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("Error")
                    .setMessage("IndexOutOfBoundsException occurred. Please check your inputs.")
                    .setPositiveButton("OK", null)
                    .show();
        }
    }

    private void GetDriverLocation(Geocoder geocoder) {
        List<Address> departureAddressList;
        try {
            departureAddressList = geocoder.getFromLocationName(driverVehicleAddress, 1);

            if (departureAddressList != null && departureAddressList.size() > 0) {
                doubleDriverLocationLat = departureAddressList.get(0).getLatitude();
                doubleDriverLocationLong = departureAddressList.get(0).getLongitude();

                LatLng driverLocation = new LatLng(doubleDriverLocationLat, doubleDriverLocationLong);
                drawRouteFragment.addDriverLocationMarker(driverLocation);
                locations.add(new NewLocationDTO());
            } else {
                Toast.makeText(this, "No departure address found", Toast.LENGTH_SHORT).show();
            }
        } catch (IOException | SecurityException e) {
            e.printStackTrace();
        } catch (IndexOutOfBoundsException e) {
            e.printStackTrace();
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("Error")
                    .setMessage("IndexOutOfBoundsException occurred. Please check your inputs.")
                    .setPositiveButton("OK", null)
                    .show();
        }
    }


}