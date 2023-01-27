package com.example.uberapp_tim22;

import static com.example.uberapp_tim22.fragments.MapFragment.MY_PERMISSIONS_REQUEST_LOCATION;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.uberapp_tim22.DTO.DriverDTO;
import com.example.uberapp_tim22.DTO.PassengerDTO;
import com.example.uberapp_tim22.DTO.VehicleDTO;
import com.example.uberapp_tim22.dialogs.LocationDialog;
import com.example.uberapp_tim22.fragments.DrawRouteFragment;
import com.example.uberapp_tim22.fragments.MapFragment;
import com.example.uberapp_tim22.service.ServiceUtils;
import com.example.uberapp_tim22.tools.FragmentTransition;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class PassengerMainActivity extends AppCompatActivity {
    private CharSequence mTitle;
    private AlertDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_passenger_main);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("FAB Car");

//        FragmentTransition.to(MapFragment.newInstance(), this, false);

        Button findDriverBtn = findViewById(R.id.button);
        Button favorites = findViewById(R.id.button3);

        findDriverBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PassengerMainActivity.this, PassengerAccountActivity.class);
                startActivity(intent);
            }
        });

        favorites.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(PassengerMainActivity.this, PassengerFavoriteRidesActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.example_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        int id = item.getItemId();

        if(id == R.id.item1){
            Intent intent = new Intent(PassengerMainActivity.this, PassengerAccountActivity.class);
            startActivity(intent);
        }
        if(id == R.id.item2){
            Intent intent = new Intent(PassengerMainActivity.this, PassengerInboxActivity.class);
            startActivity(intent);
        }
        if(id == R.id.item3){
            Intent intent = new Intent(PassengerMainActivity.this, PassangerRideHistory.class);
            startActivity(intent);
        }
        if(id == R.id.item4){
            Intent intent = new Intent(PassengerMainActivity.this, PassengerMainActivity.class);
            startActivity(intent);
        }
        if(id == R.id.item5){
            Intent intent = new Intent(PassengerMainActivity.this, UserLoginActivity.class);
            startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_LOCATION: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    // permission was granted, yay! Do the
                    // location-related task you need to do.
                    if (ContextCompat.checkSelfPermission(this,
                            android.Manifest.permission.ACCESS_FINE_LOCATION)
                            == PackageManager.PERMISSION_GRANTED) {
                        Toast.makeText(this, "LOCATION GRANTED", Toast.LENGTH_SHORT).show();
                    }

                } else if (grantResults.length > 0
                        && grantResults[1] == PackageManager.PERMISSION_GRANTED) {

                    if (ContextCompat.checkSelfPermission(this,
                            Manifest.permission.ACCESS_COARSE_LOCATION)
                            == PackageManager.PERMISSION_GRANTED) {
                        Toast.makeText(this, "LOCATION GRANTED", Toast.LENGTH_SHORT).show();

                    }

                }
            }

        }
    }


    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);

    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);

    }

    @Override
    protected void onPause() {
        super.onPause();

    }

    @Override
    protected void onResume() {
        super.onResume();

        getPassenger("2");
        getDriver("5");
       //changeLocation("1");

        LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

        boolean gps = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
        boolean wifi = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
        Log.i("wwww", String.valueOf(gps));
        Log.i("wqqqq", String.valueOf(wifi));
    }

    /////////////////////////PASSENGER///////////////////////////////
    public void getPassenger(String id){

        Call<PassengerDTO> call = ServiceUtils.passengerService.getPassenger(id);
        call.enqueue(new Callback<PassengerDTO>() {
            @Override
            public void onResponse(Call<PassengerDTO> call, Response<PassengerDTO> response) {
                if(!response.isSuccessful()) return;

                PassengerDTO passenger = response.body();
                Log.d("Atribut", passenger.getName().toString());

            }

            @Override
            public void onFailure(Call<PassengerDTO> call, Throwable t) {
                Log.d("FAIIIL", t.getMessage());
                Log.d("FAIIIL", "BLATRUC");
            }
        });
    }

    /////////////////////////DRIVER///////////////////////////////
    public void getDriver(String id){

        Call<DriverDTO> call = ServiceUtils.driverService.getDriver(id);
        call.enqueue(new Callback<DriverDTO>() {
            @Override
            public void onResponse(Call<DriverDTO> call, Response<DriverDTO> response) {
                if(!response.isSuccessful()) return;

                DriverDTO driverDTO = response.body();

            }

            @Override
            public void onFailure(Call<DriverDTO> call, Throwable t) {
                Log.d("FAIIIL", t.getMessage());
                Log.d("FAIIIL", "BLATRUC");
            }
        });
    }

    /////////////////////////VEHICLE///////////////////////////////
//    public void changeLocation(String id){
//
//        Call<VehicleDTO> call = ServiceUtils.vehicleService.changeLocation(id);
//        call.enqueue(new Callback<VehicleDTO>() {
//            @Override
//            public void onResponse(Call<VehicleDTO> call, Response<VehicleDTO> response) {
//                if(!response.isSuccessful()) return;
//
//                VehicleDTO vehicleDTO = response.body();
//
//            }
//            @Override
//            public void onFailure(Call<PassengerDTO> call, Throwable t) {
//                Log.d("FAIIIL", t.getMessage());
//                Log.d("FAIIIL", "BLATRUC");
//            }
//        });
//    }
}