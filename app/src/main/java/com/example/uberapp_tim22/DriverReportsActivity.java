package com.example.uberapp_tim22;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
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
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.Toast;

import com.example.uberapp_tim22.DTO.ResponseRideDTO;
import com.example.uberapp_tim22.adapters.RideListAdapter;
import com.example.uberapp_tim22.model.BarChartView;
import com.example.uberapp_tim22.model.Ride;
import com.example.uberapp_tim22.service.ServiceUtils;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
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
    private RecyclerView rideListRecyclerView;
    private Button prikaz;
    private List<ResponseRideDTO> rides;
    private Date from;
    private Date to;
    private BarChartView barChart;
    private BarChartView barChart2;
    private BarChartView barChart3;

    private Button selectDateButton;
    private TextView selectedDateTextView;
    private Button selectDateButtonTo;
    private TextView selectedDateTextViewTo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_driver_reports);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("FAB Car");

        rides = new ArrayList<ResponseRideDTO>();

//        sharedPreferences = getSharedPreferences("preferences", MODE_PRIVATE);
//        Long myId = sharedPreferences.getLong("pref_id", 0);
//        rideListRecyclerView.setLayoutManager(new LinearLayoutManager(this));
//        rideListRecyclerView.setAdapter(rideListAdapter);


        prikaz = findViewById(R.id.prikaz);
        prikaz.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showPopup();
            }
        });

        selectDateButton = findViewById(R.id.select_date_button);
        selectedDateTextView = findViewById(R.id.selected_date_text_view);

        selectDateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDatePickerDialog();
            }
        });

        selectDateButtonTo = findViewById(R.id.select_date_button2);
        selectedDateTextViewTo = findViewById(R.id.selected_date_text_view2);

        selectDateButtonTo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDatePickerDialog2();
            }
        });


    }

    private void showDatePickerDialog() {
        final Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(this,
                new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int selectedYear, int selectedMonth, int selectedDay) {
                        Calendar selectedCalendar = Calendar.getInstance();
                        selectedCalendar.set(selectedYear, selectedMonth, selectedDay);

                        // Convert the Calendar object to a Date object
                        from = selectedCalendar.getTime();

                        // Now, you have the selectedDate as a Date object
                        // You can use it as needed
                        selectedDateTextView.setText("Selected Date: " + from);
                    }
                }, year, month, day);

        datePickerDialog.show();
    }
    private void showDatePickerDialog2() {
        final Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(this,
                new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int selectedYear, int selectedMonth, int selectedDay) {
                        Calendar selectedCalendar = Calendar.getInstance();
                        selectedCalendar.set(selectedYear, selectedMonth, selectedDay);

                        // Convert the Calendar object to a Date object
                        to = selectedCalendar.getTime();

                        // Now, you have the selectedDate as a Date object
                        // You can use it as needed
                        selectedDateTextViewTo.setText("Selected Date: " + to);
                    }
                }, year, month, day);

        datePickerDialog.show();
    }
    private void getDriverRides(String driverId) {
        Call<List<ResponseRideDTO>> call = ServiceUtils.driverService.getDriverRides("5");

        call.enqueue(new Callback<List<ResponseRideDTO>>() {
            @Override
            public void onResponse(Call<List<ResponseRideDTO>> call, Response<List<ResponseRideDTO>> response) {
                if (response.isSuccessful()) {
                    List<ResponseRideDTO> rideList = response.body();
                    for (ResponseRideDTO r: rideList) {
                        if (r.getStartTime().after(from) && r.getStartTime().before(to)){
                            rides.add(r);
                        }
                        }
                    Log.i("voznjiceeee", String.valueOf(rides.size()));

                } else {
                    onFailure(call, new Throwable("API call failed with status code: " + response.code()));
                }
            }

            @Override
            public void onFailure(Call<List<ResponseRideDTO>> call, Throwable t) {
                Log.e("DriverRideHistory", "API call failed: " + t.getMessage());
                Toast.makeText(DriverReportsActivity.this, "API call failed: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void showPopup() {
//        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(DriverReportsActivity.this);
//        LayoutInflater inflater = LayoutInflater.from(DriverReportsActivity.this);
//        View dialogView = inflater.inflate(R.layout.dialog_statistics, null);
//        dialogBuilder.setView(dialogView);


        getDriverRides("5");
        barChart3 = findViewById(R.id.barChart3);
        getChart3();
        barChart2 = findViewById(R.id.barChart2);
        getChart2();
        barChart = findViewById(R.id.barChart1);
        getChart1();

//        TextView messageTextView = dialogView.findViewById(R.id.textView7);
//        String message = "Kilometres per day";
//        messageTextView.setText(message);
//        messageTextView.setTextSize(18);

//        AlertDialog alertDialog = dialogBuilder.create();
//        alertDialog.show();
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
        List<Date> dates = new ArrayList<>();

        for (ResponseRideDTO r:rides){
            dates.add(r.getStartTime());
        }

        Collections.sort(dates);
        SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy");
        List<String> labels = new ArrayList<>();

        for (Date date : dates) {
            String label = sdf.format(date);
            labels.add(label);
        }

        List<String> labelss = labels;
        List<Integer> values = new ArrayList<>();

        for (ResponseRideDTO r:rides){
            values.add(r.getTotalCost()+300);
        }
        //List<Integer> values = Arrays.asList(1, 1, 2, 1);
        //todo ako je isti dan
//        barChart=null;
        barChart.setData(labelss, values);
    }

    public void getChart2(){
        List<Date> dates = new ArrayList<>();Calendar calendar = Calendar.getInstance();

        for (ResponseRideDTO r:rides){
            dates.add(r.getStartTime());
        }

        Collections.sort(dates);
        SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy");
        List<String> labels = new ArrayList<>();

        for (Date date : dates) {
            String label = sdf.format(date);
            labels.add(label);
        }

        List<String> labelss = labels;
        List<Integer> values = new ArrayList<>();

        for (ResponseRideDTO r:rides){
            values.add(r.getTotalCost());
        }
        //List<Integer> values = Arrays.asList(1, 1, 2, 1);
        //todo ako je isti dan
//        barChart2=null;
        barChart2.setData(labelss, values);
    }
    public void getChart3(){
        List<Date> dates = new ArrayList<>();

        for (ResponseRideDTO r:rides){
            dates.add(r.getStartTime());
        }

        Collections.sort(dates);
        SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy");
        List<String> labels = new ArrayList<>();

        for (Date date : dates) {
            String label = sdf.format(date);
            labels.add(label);
        }

        List<String> labelss = labels;
        List<Integer> values = new ArrayList<>();

        for (ResponseRideDTO r:rides){
            values.add(1);
        }
        //List<Integer> values = Arrays.asList(1, 1, 2, 1);
        //todo ako je isti dan
//        barChart3=null;
        barChart3.setData(labelss, values);
    }
}