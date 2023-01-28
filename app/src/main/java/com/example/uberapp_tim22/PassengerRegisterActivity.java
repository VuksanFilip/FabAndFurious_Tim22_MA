package com.example.uberapp_tim22;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.uberapp_tim22.DTO.PassengerDTO;
import com.example.uberapp_tim22.DTO.RequestPassengerDTO;
import com.example.uberapp_tim22.service.ServiceUtils;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PassengerRegisterActivity extends AppCompatActivity {

    Button loginBtn;
    Button registerBtn;
    EditText firstNameTxt;
    EditText lastNameTxt;
    EditText emailTxt;
    EditText phoneTxt;
    EditText addressTxt;
    EditText passwordTxt;
    EditText confirmPasswordTxt;
    String firstName;
    String lastName;
    String email;
    String phone;
    String address;
    String password;
    String confirmPassword;
    String profilePicture = "Avatar";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_passenger_register);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("FAB Car");

        registerBtn = findViewById(R.id.registerBtn);

        registerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                register();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.login_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    public void register(){

        findViewsById();
        getDataByViews();

        if(password != confirmPassword){
            Log.i("Registration error", "Confirm password does not match with a password");
        }

        RequestPassengerDTO request = new RequestPassengerDTO(firstName, lastName, profilePicture, email, phone, address, password);
        putPassenger(request);
    }

    public void putPassenger(RequestPassengerDTO request){
        Call<PassengerDTO> call = ServiceUtils.passengerService.createPassenger(request);
        call.enqueue(new Callback<PassengerDTO>() {
            @Override
            public void onResponse(Call<PassengerDTO> call, Response<PassengerDTO> response) {
                if(!response.isSuccessful()) return;
                Log.d("Success" ,"Successfully registered passenger");
                Intent intent = new Intent(PassengerRegisterActivity.this, PassengerMainActivity.class);
                startActivity(intent);
            }

            @Override
            public void onFailure(Call<PassengerDTO> call, Throwable t) {
                Log.d("FAIL", t.getMessage());
            }
        });
    }

    public void findViewsById(){
        firstNameTxt = findViewById(R.id.registrationFirstName);
        lastNameTxt = findViewById(R.id.registrationLastName);
        emailTxt = findViewById(R.id.registrationEmail);
        phoneTxt = findViewById(R.id.registrationPhone);
        addressTxt = findViewById(R.id.registrationAddress);
        passwordTxt = findViewById(R.id.registrationPassword);
        confirmPasswordTxt = findViewById(R.id.registrationConfirmPassword);
    }

    public void getDataByViews(){
        firstName = firstNameTxt.getText().toString();
        lastName = lastNameTxt.getText().toString();
        email = emailTxt.getText().toString();
        phone = phoneTxt.getText().toString();
        address = addressTxt.getText().toString();
        password = passwordTxt.getText().toString();
        confirmPassword = confirmPasswordTxt.getText().toString();
    }


    @Override
    protected void onResume() {
        super.onResume();
        Toast.makeText(this, "onResume()",Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onStart() {
        super.onStart();
        Toast.makeText(this, "onStart()", Toast.LENGTH_SHORT).show();
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