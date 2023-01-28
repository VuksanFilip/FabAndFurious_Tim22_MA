package com.example.uberapp_tim22;

import static com.example.uberapp_tim22.fragments.MapFragment.MY_PERMISSIONS_REQUEST_LOCATION;

import androidx.annotation.NonNull;
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

import com.example.uberapp_tim22.DTO.ChangePasswordDTO;
import com.example.uberapp_tim22.DTO.DriverActivityDTO;
import com.example.uberapp_tim22.DTO.DriverDTO;
import com.example.uberapp_tim22.DTO.DriverRideDTO;
import com.example.uberapp_tim22.DTO.LoginDTO;
import com.example.uberapp_tim22.DTO.LoginResponseDTO;
import com.example.uberapp_tim22.DTO.MessageDTO;
import com.example.uberapp_tim22.DTO.PassengerDTO;
import com.example.uberapp_tim22.DTO.SendMessageDTO;
import com.example.uberapp_tim22.DTO.TokenDTO;
import com.example.uberapp_tim22.DTO.UserDTO;
import com.example.uberapp_tim22.DTO.VehicleDTO;
import com.example.uberapp_tim22.dialogs.LocationDialog;
import com.example.uberapp_tim22.fragments.DrawRouteFragment;
import com.example.uberapp_tim22.fragments.MapFragment;
import com.example.uberapp_tim22.service.Paginated;
import com.example.uberapp_tim22.service.ServiceUtils;
import com.example.uberapp_tim22.tools.FragmentTransition;

import java.util.HashMap;
import java.util.List;

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
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.item1) {
            Intent intent = new Intent(PassengerMainActivity.this, PassengerAccountActivity.class);
            startActivity(intent);
        }
        if (id == R.id.item2) {
            Intent intent = new Intent(PassengerMainActivity.this, PassengerInboxActivity.class);
            startActivity(intent);
        }
        if (id == R.id.item3) {
            Intent intent = new Intent(PassengerMainActivity.this, PassangerRideHistory.class);
            startActivity(intent);
        }
        if (id == R.id.item4) {
            Intent intent = new Intent(PassengerMainActivity.this, PassengerMainActivity.class);
            startActivity(intent);
        }
        if (id == R.id.item5) {
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

        //getPassenger("8");
        //updatePassenger("8");
        getDriver("10");
        //updateDriver("9");
        //getDriverRides("9");
        //changeDriverActivity("9",false);
        //findByEmail("pera.peric@email.com");
        //findById("10");
        //sendMessage("10");
        //getMessages("10");
        login("pera.peric@email.com","12345678");

        //changeLocation("1");

        LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

        boolean gps = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
        boolean wifi = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
        Log.i("wwww", String.valueOf(gps));
        Log.i("wqqqq", String.valueOf(wifi));
    }

    /////////////////////////USER///////////////////////////////
    public void changePassword(String id) {

        Call<String> call = ServiceUtils.userService
                .changePassword(id,
                        new ChangePasswordDTO("12345678", "87654321"));
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {

                String message = response.body();
                Log.d("Atribut", message);
                }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                Log.d("FAIIIL", t.getMessage());
                Log.d("FAIIIL", "BLATRUC");
            }
        });
    }

    public void findByEmail(String email) {

        Call<UserDTO> call = ServiceUtils.userService.findByEmail(email);
        call.enqueue(new Callback<UserDTO>() {
            @Override
            public void onResponse(Call<UserDTO> call, Response<UserDTO> response) {
                assert  response != null;
                UserDTO user = response.body();
                //Log.d("Email", user.getEmail().toString());
            }

            @Override
            public void onFailure(Call<UserDTO> call, Throwable t) {
                Log.d("FAIIIL", t.getMessage());
                Log.d("FAIIIL", "BLATRUC");

            }
        });

        }

    public void findById(String id) {

        Call<UserDTO> call = ServiceUtils.userService.findById(id);
        call.enqueue(new Callback<UserDTO>() {
            @Override
            public void onResponse(Call<UserDTO> call, Response<UserDTO> response) {
                if(!response.isSuccessful()) return;
                UserDTO user = response.body();
                assert user != null;
            }

            @Override
            public void onFailure(Call<UserDTO> call, Throwable t) {
                Log.d("FAIIIL", t.getMessage());
                Log.d("FAIIIL", "BLATRUC");

            }
        });

    }

    public void sendMessage(String id) {

        SendMessageDTO sendMessageDTO = new SendMessageDTO();
        sendMessageDTO.setMessage("poruka");
        sendMessageDTO.setRideId("1");
        sendMessageDTO.setType("RIDE");

        Call<MessageDTO> call = ServiceUtils.userService.sendMessage(id, sendMessageDTO);
        call.enqueue(new Callback<MessageDTO>() {
            @Override
            public void onResponse(Call<MessageDTO> call, Response<MessageDTO> response) {
                MessageDTO messageDTO = response.body();
                assert messageDTO != null;
            }

            @Override
            public void onFailure(Call<MessageDTO> call, Throwable t) {
                Log.d("Messagge error", "Couldn't send message!");
            }
        });
    }

    public void getMessages(String id) {

        Call<Paginated<MessageDTO>> call = ServiceUtils.userService.getMessages(id);

        call.enqueue(new Callback<Paginated<MessageDTO>>() {
            @Override
            public void onResponse(Call<Paginated<MessageDTO>> call, Response<Paginated<MessageDTO>> response) {
                if(!response.isSuccessful()){
                    return;
                }
                assert response.body() != null;
                //List<MessageDTO> messages = response.body().getResults();

            }

            @Override
            public void onFailure(Call<Paginated<MessageDTO>> call, Throwable t) {
                Log.d("Messagge error", "Couldn't send message!");

            }
        });
    }

    public void login(String email, String password) {

        LoginDTO loginDTO = new LoginDTO(email, password);
        Call<LoginResponseDTO> call = ServiceUtils.userService.login(loginDTO);
        call.enqueue(new Callback<LoginResponseDTO>() {

            @Override
            public void onResponse(@NonNull Call<LoginResponseDTO> call, @NonNull Response<LoginResponseDTO> response) {
                if(!response.isSuccessful()) return;
                if(response.code() == 204){
                    return;
                }


                LoginResponseDTO loginResponse = response.body();
//                String userRole = "";
//                JWT jwt = new JWT(loginResponse.getToken());
//                List<HashMap> role =
//                        jwt.getClaim("role").asList(HashMap.class);
//                for (Object values: role.get(0).values()){
//                    userRole = values.toString();
//                    break;
//                }


//                String email = jwt.getClaim("sub").asString();
//                Long id = jwt.getClaim("id").asLong();
//                TokenDTO tokenDTO = TokenDTO.getInstance();
//                tokenDTO.setToken(loginResponse.getToken());
//                tokenDTO.setRefreshToken(loginResponse.getRefreshToken());
//                Intent intent;
//
//                if(userRole.equalsIgnoreCase("passenger")){
//                    setSharedPreferences("PASSENGER", email, id);
//                    setTokenPreference(loginResponse.getToken(), loginResponse.getRefreshToken());
//                    intent = new Intent(PassengerMainActivity.this, PassengerMainActivity.class);
//                    startActivity(intent);
//
//                }
//                else if(userRole.equalsIgnoreCase("driver")) {
//                    setSharedPreferences("DRIVER", email, id);
//                    setTokenPreference(loginResponse.getToken(), loginResponse.getRefreshToken());
//                    intent = new Intent(PassengerMainActivity.this, DriverMainActivity.class);
//                    startActivity(intent);
//
//                }

            }

            @Override
            public void onFailure(Call<LoginResponseDTO> call, Throwable t) {
                Log.d("Login Failed", t.getMessage());
            }
        });
    }


    /////////////////////////PASSENGER///////////////////////////////
    public void getPassenger(String id) {

        Call<PassengerDTO> call = ServiceUtils.passengerService.getPassenger(id);
        call.enqueue(new Callback<PassengerDTO>() {
            @Override
            public void onResponse(Call<PassengerDTO> call, Response<PassengerDTO> response) {
                if (!response.isSuccessful()) return;

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

    public void updatePassenger(String id) {
        PassengerDTO passenger = new PassengerDTO();
        passenger.setAddress("adressaaa");
        passenger.setTelephoneNumber("6543456576");
        passenger.setName("pera2");
        passenger.setSurname("peric2");
        Call<PassengerDTO> call = ServiceUtils.passengerService.updatePassenger(id, passenger);

        call.enqueue(new Callback<PassengerDTO>() {
            @Override
            public void onResponse(Call<PassengerDTO> call, Response<PassengerDTO> response) {
                if (!response.isSuccessful()) return;

                PassengerDTO passenger = response.body();
                Log.d("Atribut", passenger.getName().toString());

            }

            @Override
            public void onFailure(Call<PassengerDTO> call, Throwable t) {
                Log.d("FAIIIL", t.getMessage());
                Log.d("FAIIIL", "BLATRUC");
            }
        });
//        call.enqueue(new Callback<PassengerDTO>() {
//            @Override
//            public void onResponse(Call<PassengerDTO> call, Response<PassengerDTO> response) {
//                if (!response.isSuccessful()) {
//                    Toast.makeText(null, "Something went wrong!", Toast.LENGTH_SHORT).show();
//                }
//                PassengerDTO passengerDTO = response.body();
//                assert passengerDTO != null;
//                TextView tvName = getActivity().findViewById(R.id.passengerNameNavigation);
//                String fullName = passengerDTO.getName() + " " + passengerDTO.getSurname();
//                tvName.setText(fullName);
//
//                TextView tvPhoneNumber = getActivity().findViewById(R.id.passengerPhoneNavigation);
//                tvPhoneNumber.setText(passengerDTO.getTelephoneNumber());
//                CircleImageView cv = getActivity().findViewById(R.id.passengerProfilePictureNavigation);
//                String base64Image = passengerDTO.getProfilePicture().split(",")[1];
//                byte[] decodedString = Base64.decode(base64Image, Base64.DEFAULT);
//                Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
//                cv.setImageBitmap(decodedByte);
//
//                getActivity().getSupportFragmentManager().popBackStackImmediate();
//
//            }

//            @Override
//            public void onFailure(Call<PassengerDTO> call, Throwable t) {
//                Toast.makeText(null, t.getMessage(), Toast.LENGTH_SHORT).show();
//            }
//        });
    }

    /////////////////////////DRIVER///////////////////////////////
    public void getDriver(String id) {

        Call<DriverDTO> call = ServiceUtils.driverService.getDriver(id);
        call.enqueue(new Callback<DriverDTO>() {
            @Override
            public void onResponse(Call<DriverDTO> call, Response<DriverDTO> response) {
                if (!response.isSuccessful()) return;

                DriverDTO driverDTO = response.body();

            }

            @Override
            public void onFailure(Call<DriverDTO> call, Throwable t) {
                Log.d("FAIIIL", t.getMessage());
                Log.d("FAIIIL", "BLATRUC");
            }
        });
    }

    public void updateDriver(String id) {
        DriverDTO driver = new DriverDTO();
        driver.setAddress("adressaaa123");
        driver.setTelephoneNumber("6556576");
        driver.setName("pera2");
        driver.setSurname("peric2");
        Call<DriverDTO> call = ServiceUtils.driverService.updateDriver(id, driver);

        call.enqueue(new Callback<DriverDTO>() {
            @Override
            public void onResponse(Call<DriverDTO> call, Response<DriverDTO> response) {
                if (!response.isSuccessful()) return;

                DriverDTO driverDTO = response.body();
                Log.d("Atribut", driverDTO.getName().toString());

            }

            @Override
            public void onFailure(Call<DriverDTO> call, Throwable t) {
                Log.d("FAIIIL", t.getMessage());
                Log.d("FAIIIL", "BLATRUC");
            }
        });
    }

    public void getDriverRides(String id){ //ne radi mozda
        Call<Paginated<DriverRideDTO>> call = ServiceUtils.driverService.getRides(id, 0, 50, "timeOfStart,desc");
        call.enqueue(new Callback<Paginated<DriverRideDTO>>() {
            @Override
            public void onResponse(Call<Paginated<DriverRideDTO>> call, Response<Paginated<DriverRideDTO>> response) {
                if (!response.isSuccessful()) return;

                Paginated<DriverRideDTO> driverDTO = response.body();

            }

            @Override
            public void onFailure(Call<Paginated<DriverRideDTO>> call, Throwable t) {
                Log.d("FAIIIL", t.getMessage());
                Log.d("FAIIIL", "BLATRUC");
            }
        });

    }

    private void changeDriverActivity(String id, boolean isActive) {
        Call<String> call = ServiceUtils.driverService.changeActivity(id, new DriverActivityDTO(isActive));
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(@NonNull Call<String> call, @NonNull Response<String> response) {
                Toast.makeText(getApplicationContext(), "You are active!", Toast.LENGTH_SHORT).show();
                Log.d("Atribut", String.valueOf(isActive));
            }
            @Override
            public void onFailure(@NonNull Call<String> call, @NonNull Throwable t) {
                Log.d("Fail", t.getMessage());
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