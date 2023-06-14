package com.example.uberapp_tim22;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.uberapp_tim22.DTO.ResponseRideDTO;
import com.example.uberapp_tim22.model.BarChartView;
import com.example.uberapp_tim22.service.ServiceUtils;
import com.google.android.material.navigation.NavigationView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PassengerReportsActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    DrawerLayout drawerLayout;
    NavigationView navigationView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_passenger_reports);

        navigationView.findViewById(R.id.nav_view);
        drawerLayout.findViewById(R.id.drawer_layout);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("FAB Car");

        navigationView.bringToFront();
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.open_nav_drawer, R.string.close_nav_drawer);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);
        getPassengerRides("1");
        getChart1();
        getChart2();
        getChart3();

    }
    private void getPassengerRides(String driverId) {
        Call<List<ResponseRideDTO>> call = ServiceUtils.passengerService.getPassengerRides(driverId);

        BarChartView barChart = findViewById(R.id.barChart);

        call.enqueue(new Callback<List<ResponseRideDTO>>() {
            @Override
            public void onResponse(Call<List<ResponseRideDTO>> call, Response<List<ResponseRideDTO>> response) {
                if (response.isSuccessful()) {
                    List<ResponseRideDTO> rideList = response.body();
                    List<Date> dates = new ArrayList<>();

                    for (ResponseRideDTO ride :rideList){
                        dates.add(new Date());
                    }
                    Collections.sort(dates);
                    SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy");
                    List<String> labels = new ArrayList<>();

                    for (Date date : dates) {
                        String label = sdf.format(date);
                        labels.add(label);
                    }

                    Log.i("probaaa",rideList.get(0).toString());
                    List<String> labelss = labels;
                    List<Integer> values = Arrays.asList(5, 8, 3, 6, 4, 9, 7);

                    barChart.setData(labels, values);
                } else {
                    onFailure(call, new Throwable("API call failed with status code: " + response.code()));
                }
            }

            @Override
            public void onFailure(Call<List<ResponseRideDTO>> call, Throwable t) {
                Log.e("DriverRideHistory", "API call failed: " + t.getMessage());
                Toast.makeText(PassengerReportsActivity.this, "API call failed: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
    @Override
    public void onBackPressed(){
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        }else{
            super.onBackPressed();
        }
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
            Intent intent = new Intent(PassengerReportsActivity.this, PassengerAccountActivity.class);
            startActivity(intent);
        }
        if(id == R.id.item2){
            Intent intent = new Intent(PassengerReportsActivity.this, PassengerInboxActivity.class);
            startActivity(intent);
        }
        if(id == R.id.item3){
            Intent intent = new Intent(PassengerReportsActivity.this, PassangerRideHistory.class);
            startActivity(intent);
        }
        if(id == R.id.item4){
            Intent intent = new Intent(PassengerReportsActivity.this, PassengerMainActivity.class);
            startActivity(intent);
        }
        if(id == R.id.item5){
            Intent intent = new Intent(PassengerReportsActivity.this, UserLoginActivity.class);
            startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem){

        switch (menuItem.getItemId()){
            case R.id.profle:
                Intent intent = new Intent(PassengerReportsActivity.this, PassengerAccountActivity.class);
                startActivity(intent);
                break;
            case R.id.reports:
                Intent intent1 = new Intent(PassengerReportsActivity.this, PassengerReportsActivity.class);
                startActivity(intent1);
                break;
            case R.id.favorites:
                Intent intent2 = new Intent(PassengerReportsActivity.this, PassengerFavoriteRidesActivity.class);
                startActivity(intent2);

        }
        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
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
    public void getChart1(){
        List<Date> dates = new ArrayList<>();
        Calendar calendar = Calendar.getInstance();

        BarChartView barChart = findViewById(R.id.barChart);
        // Set the desired year, month, and day
        calendar.set(Calendar.YEAR, 2022);
        calendar.set(Calendar.MONTH, Calendar.SEPTEMBER);
        calendar.set(Calendar.DAY_OF_MONTH, 6);
        // Get a Date object from the Calendar instance
        Date date3 = calendar.getTime();

        calendar.set(Calendar.YEAR, 2023);
        calendar.set(Calendar.MONTH, Calendar.JUNE);
        calendar.set(Calendar.DAY_OF_MONTH, 13);
        Date date4 = calendar.getTime();
        calendar.set(Calendar.YEAR, 2023);
        calendar.set(Calendar.MONTH, Calendar.JUNE);
        calendar.set(Calendar.DAY_OF_MONTH, 14);
        Date date5 = calendar.getTime();
        dates.add(date3);
        dates.add(date4);
        dates.add(date5);

        Collections.sort(dates);
        SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy");
        List<String> labels = new ArrayList<>();

        for (Date date : dates) {
            String label = sdf.format(date);
            labels.add(label);
        }

        List<String> labelss = labels;
        List<Integer> values = Arrays.asList(1, 1, 2, 1);

        barChart.setData(labelss, values);
    }

    public void getChart2(){
        List<Date> dates = new ArrayList<>();Calendar calendar = Calendar.getInstance();

        BarChartView barChart = findViewById(R.id.barChart3);
        // Set the desired year, month, and day
        calendar.set(Calendar.YEAR, 2022);
        calendar.set(Calendar.MONTH, Calendar.SEPTEMBER);
        calendar.set(Calendar.DAY_OF_MONTH, 6);
        // Get a Date object from the Calendar instance
        Date date3 = calendar.getTime();

        calendar.set(Calendar.YEAR, 2023);
        calendar.set(Calendar.MONTH, Calendar.JUNE);
        calendar.set(Calendar.DAY_OF_MONTH, 13);
        Date date4 = calendar.getTime();
        calendar.set(Calendar.YEAR, 2023);
        calendar.set(Calendar.MONTH, Calendar.JUNE);
        calendar.set(Calendar.DAY_OF_MONTH, 14);
        Date date5 = calendar.getTime();
        dates.add(date3);
        dates.add(date4);
        dates.add(date5);

        Collections.sort(dates);
        SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy");
        List<String> labels = new ArrayList<>();

        for (Date date : dates) {
            String label = sdf.format(date);
            labels.add(label);
        }

        List<String> labelss = labels;
        List<Integer> values = Arrays.asList(4, 26, 3);

        barChart.setData(labelss, values);
    }
    public void getChart3(){
        List<Date> dates = new ArrayList<>();Calendar calendar = Calendar.getInstance();

        BarChartView barChart = findViewById(R.id.barChart2);
        // Set the desired year, month, and day
        calendar.set(Calendar.YEAR, 2022);
        calendar.set(Calendar.MONTH, Calendar.SEPTEMBER);
        calendar.set(Calendar.DAY_OF_MONTH, 6);
        // Get a Date object from the Calendar instance
        Date date3 = calendar.getTime();

        calendar.set(Calendar.YEAR, 2023);
        calendar.set(Calendar.MONTH, Calendar.JUNE);
        calendar.set(Calendar.DAY_OF_MONTH, 13);
        Date date4 = calendar.getTime();
        calendar.set(Calendar.YEAR, 2023);
        calendar.set(Calendar.MONTH, Calendar.JUNE);
        calendar.set(Calendar.DAY_OF_MONTH, 14);
        Date date5 = calendar.getTime();
        dates.add(date3);
        dates.add(date4);
        dates.add(date5);

        Collections.sort(dates);
        SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy");
        List<String> labels = new ArrayList<>();

        for (Date date : dates) {
            String label = sdf.format(date);
            labels.add(label);
        }

        List<String> labelss = labels;
        List<Integer> values = Arrays.asList(300, 1250, 700);

        barChart.setData(labelss, values);
    }

}