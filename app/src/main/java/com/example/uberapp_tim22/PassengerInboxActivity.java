package com.example.uberapp_tim22;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.uberapp_tim22.DTO.IdAndEmailDTO;
import com.example.uberapp_tim22.DTO.ResponseRideDTO;
import com.example.uberapp_tim22.adapters.ChatBoxListAdapter;
import com.example.uberapp_tim22.adapters.RideListAdapter;
import com.example.uberapp_tim22.service.ServiceUtils;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PassengerInboxActivity  extends AppCompatActivity implements ChatBoxListAdapter.ChatBoxItemClickListener{

    private RecyclerView chatBoxListRecyclerView;
    private ChatBoxListAdapter chatBoxListAdapter;
    private SharedPreferences sharedPreferences;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_passenger_inbox);

        Toolbar toolbar = findViewById(R.id.inboxToolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("FAB Car");

        chatBoxListRecyclerView = findViewById(R.id.listOfChats);
        chatBoxListAdapter = new ChatBoxListAdapter(this);;
        sharedPreferences = getSharedPreferences("preferences", MODE_PRIVATE);
        Long myId = sharedPreferences.getLong("pref_id", 0);

        chatBoxListRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        chatBoxListRecyclerView.setAdapter(chatBoxListAdapter);

        getPassengerRides(String.valueOf(myId));
    }

    private void getPassengerRides(String passengerId) {
        Call<List<ResponseRideDTO>> call = ServiceUtils.passengerService.getPassengerRides("2");

        call.enqueue(new Callback<List<ResponseRideDTO>>() {
            @Override
            public void onResponse(Call<List<ResponseRideDTO>> call, Response<List<ResponseRideDTO>> response) {
                if (response.isSuccessful()) {
                    List<ResponseRideDTO> rideList = response.body();
                    if (rideList != null) {
                        chatBoxListAdapter.setCheckBoxlist(rideList);
                    }
                } else {
                    onFailure(call, new Throwable("API call failed with status code: " + response.code()));
                }
            }

            @Override
            public void onFailure(Call<List<ResponseRideDTO>> call, Throwable t) {
                Log.e("PassangerRideHistory", "API call failed: " + t.getMessage());
                Toast.makeText(PassengerInboxActivity.this, "API call failed: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onChatBoxItemClick(ResponseRideDTO ride) {
        showPopup(ride);
    }

    public String passengersToString(List<IdAndEmailDTO> passengers){
        String passengerString = "";
        for(IdAndEmailDTO passenger : passengers){
            passengerString = passengerString + passenger.getEmail() + " ";
        }

        return passengerString;
    }

    private void showPopup(ResponseRideDTO ride) {
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(PassengerInboxActivity.this);
        LayoutInflater inflater = LayoutInflater.from(PassengerInboxActivity.this);
        View dialogView = inflater.inflate(R.layout.dialog_ride_details, null);
        dialogBuilder.setView(dialogView);

        TextView messageTextView = dialogView.findViewById(R.id.messageTextView);
        String message = "ID: " + ride.getId() + "\n" +
                "Driver: " + ride.getDriver().getEmail() + "\n" +
                "Passengers: " + passengersToString(ride.getPassengers()) + "\n" +
                "Rejection: " + (ride.getRejection() != null ? ride.getRejection().getReason() : "") + "\n" +
                "Total cost: " + ride.getTotalCost() + "\n" +
                "Start Time: " + ride.getStartTime() + "\n" +
                "End Time: " + ride.getEndTime();
        messageTextView.setText(message);
        messageTextView.setTextSize(18);

        AlertDialog alertDialog = dialogBuilder.create();
        alertDialog.show();
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

        if (itemId == R.id.menuOrder) {
            Intent intent = new Intent(this, PassengerMainActivity.class);
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
}