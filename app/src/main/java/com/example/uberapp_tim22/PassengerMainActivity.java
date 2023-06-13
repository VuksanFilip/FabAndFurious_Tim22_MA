package com.example.uberapp_tim22;

import androidx.annotation.ColorInt;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatSpinner;
import androidx.appcompat.widget.Toolbar;
import android.app.Fragment;

import android.animation.LayoutTransition;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.uberapp_tim22.DTO.NewLocationDTO;
import com.example.uberapp_tim22.DTO.NewLocationWithAddressDTO;
import com.example.uberapp_tim22.fragments.DrawRouteFragment;
import com.example.uberapp_tim22.fragments.MapFragment;
import com.example.uberapp_tim22.fragments.Stepper1Fragment;
import com.example.uberapp_tim22.fragments.Stepper2Fragment;
import com.example.uberapp_tim22.fragments.Stepper3Fragment;
import com.example.uberapp_tim22.tools.FragmentTransition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.material.textfield.TextInputEditText;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.ExecutionException;

public class PassengerMainActivity extends AppCompatActivity {

    private Button fragmentStepper1NextBtn, fragmentStepper1TimeBtn, fragmentStepper1GetCoridnatesBtn;
    private EditText departureAddressEditText, destinationAddressEditText;
    private RadioGroup fragmentStepper1RG;
    private RadioButton fragmentStepper1NowRB, fragmentStepper1ScheduleRB;
    private TextView fragmentStepper1TextView;
    private DrawRouteFragment drawRouteFragment;
    private LinearLayout layoutList;
    private List<String> teamList = new ArrayList<>();
    private List<NewLocationDTO> locations = new ArrayList<>();
    private NewLocationWithAddressDTO departure = new NewLocationWithAddressDTO();
    private NewLocationWithAddressDTO destination = new NewLocationWithAddressDTO();
    private NewLocationDTO location = new NewLocationDTO();
    private double doubleDepartureLat;
    private double doubleDepartureLong;
    private double doubleDestinationLat;
    private double doubleDestinationLong;
    FragmentManager fm = getFragmentManager();
    FragmentTransaction fragmentTransition = fm.beginTransaction();
    Stepper1Fragment stepper1Fragment = new Stepper1Fragment();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_passenger_main);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("FAB Car");

        drawRouteFragment = DrawRouteFragment.newInstance();
        FragmentTransition.to(drawRouteFragment, this, false);
        SharedPreferences sharedPreferences = getSharedPreferences("preferences", Context.MODE_PRIVATE);
        String myEmail = sharedPreferences.getString("pref_email", "");

//        FragmentManager fm = getFragmentManager();
//        FragmentTransaction fragmentTransition = fm.beginTransaction();
//        Stepper1Fragment stepper1Fragment = new Stepper1Fragment();
        fragmentTransition.add(R.id.fragmentStepper2, stepper1Fragment);
        fragmentTransition.commit();

        teamList.add("Team");
        teamList.add("India");
        teamList.add("Australia");
        teamList.add("England");
    }

    @Override
    protected void onResume() {
        super.onResume();
        fragmentStepper1NextBtn=(Button) findViewById(R.id.fragmentStepper1NextBtn);
        fragmentStepper1TimeBtn = (Button) findViewById(R.id.fragmentStepper1TimeBtn);
        fragmentStepper1RG = (RadioGroup) findViewById(R.id.fragmentStepper1RG);
        fragmentStepper1NowRB = (RadioButton) findViewById(R.id.fragmentStepper1NowRB);
        fragmentStepper1ScheduleRB = (RadioButton) findViewById(R.id.fragmentStepper1ScheduleRB);
        fragmentStepper1TextView = (TextView) findViewById(R.id.fragmentStepper1TextView);

        layoutList = findViewById(R.id.layout_list);

        departureAddressEditText = (EditText) findViewById(R.id.departureAddressEditText);
        destinationAddressEditText = (EditText) findViewById(R.id.destinationAddressEditText);
        fragmentStepper1GetCoridnatesBtn = (Button) findViewById(R.id.fragmentStepper1GetCoridnatesBtn);
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

        return super.onOptionsItemSelected(item);
    }

    public void buttonGetCoordinates(View view) {
        Geocoder geocoder = new Geocoder(this);
        try {
            GetDeparture(geocoder);
            GetDestination(geocoder);
            drawRouteFragment.drawLines();

            departure.setAddress(departureAddressEditText.getText().toString());
            departure.setLatitude(doubleDepartureLat);
            departure.setLongitude(doubleDepartureLong);

            destination.setAddress(destinationAddressEditText.getText().toString());
            destination.setLatitude(doubleDestinationLat);
            destination.setLongitude(doubleDestinationLong);

            location.setDeparture(departure);
            location.setDestination(destination);

            locations.clear();
            locations.add(location);
            stepper1Fragment.setLocations(locations);
        } catch (NullPointerException e) {
            showPopup("Error", "An error occurred while getting coordinates.");
            e.printStackTrace();
        }
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
            departureAddressList = geocoder.getFromLocationName(departureAddressEditText.getText().toString(), 1);

            if (departureAddressList != null && departureAddressList.size() > 0) {
                doubleDepartureLat = departureAddressList.get(0).getLatitude();
                doubleDepartureLong = departureAddressList.get(0).getLongitude();

                LatLng departureLocation = new LatLng(doubleDepartureLat, doubleDepartureLong);
                drawRouteFragment.addDepartureMarker(departureLocation);
                locations.add(new NewLocationDTO());
            } else {
                // Display a popup or toast indicating that no address was found
                Toast.makeText(this, "No departure address found", Toast.LENGTH_SHORT).show();
            }
        } catch (IOException | SecurityException e) {
            e.printStackTrace();
        } catch (IndexOutOfBoundsException e) {
            e.printStackTrace();
            // Display a popup or dialog indicating the error
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
            destinationAddressList = geocoder.getFromLocationName(destinationAddressEditText.getText().toString(), 1);

            if (destinationAddressList != null && destinationAddressList.size() > 0) {
                doubleDestinationLat = destinationAddressList.get(0).getLatitude();
                doubleDestinationLong = destinationAddressList.get(0).getLongitude();

                LatLng destinationLocation = new LatLng(doubleDestinationLat, doubleDestinationLong);
                drawRouteFragment.addDestinationMarker(destinationLocation);
            } else {
                // Display a popup or toast indicating that no address was found
                Toast.makeText(this, "No destination address found", Toast.LENGTH_SHORT).show();
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (IndexOutOfBoundsException e) {
            e.printStackTrace();
            // Display a popup or dialog indicating the error
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("Error")
                    .setMessage("IndexOutOfBoundsException occurred. Please check your inputs.")
                    .setPositiveButton("OK", null)
                    .show();
        }
    }

}