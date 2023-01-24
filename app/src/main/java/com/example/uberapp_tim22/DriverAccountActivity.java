package com.example.uberapp_tim22;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;

public class DriverAccountActivity extends AppCompatActivity {

//    DrawerLayout drawerLayout;
//    NavigationView navigationView;
//    Button current_ride;
    
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
}