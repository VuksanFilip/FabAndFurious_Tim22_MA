package com.example.uberapp_tim22;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.uberapp_tim22.DTO.ResponseRideDTO;
import com.example.uberapp_tim22.adapters.RideListAdapter;
import com.example.uberapp_tim22.model.BarChartView;
import com.example.uberapp_tim22.service.ServiceUtils;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class DriverReportsActivity extends AppCompatActivity {

    private RideListAdapter rideListAdapter;
    private Button prikaz;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_driver_reports);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("FAB Car");

//        sharedPreferences = getSharedPreferences("preferences", MODE_PRIVATE);
//        Long myId = sharedPreferences.getLong("pref_id", 0);

       // getDriverRides("5");

        prikaz = findViewById(R.id.prikaz);
        prikaz.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showPopup(new Date(), new Date());
            }
        });


//        getChart1();
//        getChart2();
//        getChart3();

    }
//    private void getDriverRides(String driverId) {
//        Call<List<ResponseRideDTO>> call = ServiceUtils.driverService.getDriverRides(driverId);
//
//        call.enqueue(new Callback<List<ResponseRideDTO>>() {
//            @Override
//            public void onResponse(Call<List<ResponseRideDTO>> call, Response<List<ResponseRideDTO>> response) {
//                if (response.isSuccessful()) {
//                    List<ResponseRideDTO> rideList = response.body();
//                    if (rideList != null) {
//                        rideListAdapter.setRideList(rideList);
//                        Log.i("123",rideListAdapter.toString());
//                    }
//                } else {
//                    onFailure(call, new Throwable("API call failed with status code: " + response.code()));
//                }
//            }
//
//            @Override
//            public void onFailure(Call<List<ResponseRideDTO>> call, Throwable t) {
//                Log.e("DriverRideHistory", "API call failed: " + t.getMessage());
//                Toast.makeText(DriverReportsActivity.this, "API call failed: " + t.getMessage(), Toast.LENGTH_SHORT).show();
//            }
//        });
//    }

    private void showPopup(Date datum1, Date datum2) {
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(DriverReportsActivity.this);
        LayoutInflater inflater = LayoutInflater.from(DriverReportsActivity.this);
        View dialogView = inflater.inflate(R.layout.dialog_statistics, null);
        dialogBuilder.setView(dialogView);

        TextView messageTextView = dialogView.findViewById(R.id.textView7);
        String message = "Kilometres per day";
        messageTextView.setText(message);
        messageTextView.setTextSize(18);

        AlertDialog alertDialog = dialogBuilder.create();
        alertDialog.show();
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
            Intent intent = new Intent(DriverReportsActivity.this, DriverAccountActivity.class);
            startActivity(intent);
        }
        if(id == R.id.item2){
            Intent intent = new Intent(DriverReportsActivity.this, DriverInboxActivity.class);
            startActivity(intent);
        }
        if(id == R.id.item3){
            Intent intent = new Intent(DriverReportsActivity.this, DriverRideHistory.class);
            startActivity(intent);
        }
        if(id == R.id.item4){
            Intent intent = new Intent(DriverReportsActivity.this, DriverMainActivity.class);
            startActivity(intent);
        }
        if(id == R.id.item5){
            Intent intent = new Intent(DriverReportsActivity.this, UserLoginActivity.class);
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

    public void getChart1(){
        List<Date> dates = new ArrayList<>();Calendar calendar = Calendar.getInstance();

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