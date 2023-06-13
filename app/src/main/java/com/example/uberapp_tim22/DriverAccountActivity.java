package com.example.uberapp_tim22;

import static android.app.PendingIntent.getActivity;
import static com.example.uberapp_tim22.service.ServiceUtils.driverService;
import static com.example.uberapp_tim22.service.ServiceUtils.userService;

import android.app.Fragment;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.uberapp_tim22.DTO.DriverDTO;
import com.example.uberapp_tim22.model.Driver;
import com.google.android.material.navigation.NavigationView;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import android.app.FragmentManager;
import android.app.FragmentTransaction;

import org.w3c.dom.Text;

public class DriverAccountActivity extends AppCompatActivity {

//    DrawerLayout drawerLayout;
//    NavigationView navigationView;
//    Button current_ride;
    private Driver driver;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_driver_account);

//        navigationView.findViewById(R.id.nav_view);
//        drawerLayout.findViewById(R.id.drawer_layout);
//        current_ride.findViewById(R.id.button11);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("FAB Car");

        SharedPreferences preferences = getSharedPreferences("preferences", Context.MODE_PRIVATE);
        int id = preferences.getInt("p_id", 0);

        getDriver(id);
        Button proba = findViewById(R.id.button9);
        proba.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(DriverAccountActivity.this, DriverStatisticsActivity.class);
                startActivity(intent);
            }
        });

//        navigationView.bringToFront();
//        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.open_nav_drawer, R.string.close_nav_drawer);
//        drawerLayout.addDrawerListener(toggle);
//        toggle.syncState();

//        current_ride.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent intent = new Intent(DriverAccountActivity.this, CurrentRideDriver.class);
//                startActivity(intent);
//            }
//        });
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
            Intent intent = new Intent(DriverAccountActivity.this, DriverAccountActivity.class);
            startActivity(intent);
        }
        if(id == R.id.item2){
            Intent intent = new Intent(DriverAccountActivity.this, DriverInboxActivity.class);
            startActivity(intent);
        }
        if(id == R.id.item3){
            Intent intent = new Intent(DriverAccountActivity.this, DriverRideHistory.class);
            startActivity(intent);
        }
        if(id == R.id.item4){
            Intent intent = new Intent(DriverAccountActivity.this, DriverMainActivity.class);
            startActivity(intent);
        }
        if(id == R.id.item5){
            Intent intent = new Intent(DriverAccountActivity.this, UserLoginActivity.class);
            startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onStart() {
        super.onStart();
        Toast.makeText(this, "onStart()", Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onResume() {
        super.onResume();
        Toast.makeText(this, "onResume()",Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onPause() {
        super.onPause();
        Toast.makeText(this, "onPause()",Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onStop() {
        super.onStop();
        Toast.makeText(this, "onStop()",Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Toast.makeText(this, "onDestroy()",Toast.LENGTH_SHORT).show();
    }

    public void getDriver(int id){
        Call<Driver> call = driverService.getDriver("5");
        call.enqueue(new Callback<Driver>() {
            @Override
            public void onResponse(Call<Driver> call, Response<Driver> response) {
                if (response.isSuccessful()) {
                    assert response.body() != null;
                    driver = new Driver(response.body());
                    Log.i("asd", driver.getAddress());
                    setDriverData(driver.getFirstName());
                }
            }

            @Override
            public void onFailure(Call<Driver> call, Throwable t) {
                Log.d("Adding Failed", t.getMessage());
            }
        });
    }

    private void setDriverData(String driver) {
//        String user = driver.getFirstName() + " " + driver.getSurname();
//
//        TextView email;
//        email = findViewById(R.id.editTextTextPersonName4);
//        email.setText(driver.getFirstName());
//        Log.i("aaa",email.toString());
        ((TextView) findViewById(R.id.editTextTextPersonName4)).setText(driver);
        Log.i("ime", driver);
//        ((TextView) findViewById(R.id.txtEmail)).setText(driver.getEmail());
//        ((TextView) findViewById(R.id.txtPhone)).setText(driver.getTelephoneNumber());
//        ((TextView) findViewById(R.id.txtAddress)).setText(driver.getAddress());
        }
}