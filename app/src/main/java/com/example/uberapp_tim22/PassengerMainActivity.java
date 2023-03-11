package com.example.uberapp_tim22;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import android.app.Fragment;

import android.animation.LayoutTransition;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.uberapp_tim22.fragments.DrawRouteFragment;
import com.example.uberapp_tim22.fragments.Stepper1Fragment;
import com.example.uberapp_tim22.fragments.Stepper2Fragment;
import com.example.uberapp_tim22.tools.FragmentTransition;

import java.time.LocalDate;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class PassengerMainActivity extends AppCompatActivity {

    private CharSequence mTitle;
    private AlertDialog dialog;
    private ImageView popUpView;
    private LayoutTransition layoutTransition;
    private Button stepperDateBtn, fragmentStepper1NextBtn, fragmentStepper1TimeBtn;
    private RadioGroup fragmentStepper1RG;
    private RadioButton fragmentStepper1NowRB, fragmentStepper1ScheduleRB;
    private Date currentTime;
    private TextView fragmentStepper1TextView;
    private int hour, minute;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_passenger_main);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("FAB Car");

        DrawRouteFragment drawRouteFragment = DrawRouteFragment.newInstance();
        FragmentTransition.to(drawRouteFragment, this, false);

        FragmentManager fm = getFragmentManager();
        FragmentTransaction fragmentTransition = fm.beginTransaction();
        Stepper1Fragment stepper1Fragment = new Stepper1Fragment();
        fragmentTransition.add(R.id.fragmentStepper2, stepper1Fragment);
        fragmentTransition.commit();
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

        fragmentStepper1NextBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager fm = getFragmentManager();
                FragmentTransaction fragmentTransition = fm.beginTransaction();
                Stepper2Fragment fragmentStepper2 = new Stepper2Fragment();
                fragmentTransition.replace(R.id.fragmentStepper2, fragmentStepper2);
                fragmentTransition.commit();
            }
        });

        fragmentStepper1RG.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {

                if (fragmentStepper1ScheduleRB.isChecked()) {
                    fragmentStepper1TimeBtn.setEnabled(true);
                }
                if (fragmentStepper1NowRB.isChecked()) {
                    fragmentStepper1TimeBtn.setEnabled(false);
                    fragmentStepper1TimeBtn.setTextSize(10);
                    fragmentStepper1TimeBtn.setText("SELECT TIME");
                    fragmentStepper1TextView.setText("");
                }
            }
        });
    }


    public void popTimePicker(View view)
    {
        TimePickerDialog.OnTimeSetListener onTimeSetListener = new TimePickerDialog.OnTimeSetListener()
        {
            @Override
            public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute)
            {
                hour = selectedHour;
                minute = selectedMinute;

                currentTime = Calendar.getInstance().getTime();
                if((hour < currentTime.getHours())){
                    fragmentStepper1TimeBtn.setTextSize(10);
                    fragmentStepper1TimeBtn.setText("SELECT TIME");
                    fragmentStepper1TextView.setText("Cannot select date in the past");
                }
                else if(hour == currentTime.getHours() && (minute <= currentTime.getMinutes())){
                    fragmentStepper1TimeBtn.setTextSize(10);
                    fragmentStepper1TimeBtn.setText("SELECT TIME");
                    fragmentStepper1TextView.setText("Cannot select date in the past");
                }
                else if((hour - currentTime.getHours()) > 5){
                    fragmentStepper1TimeBtn.setTextSize(10);
                    fragmentStepper1TimeBtn.setText("SELECT TIME");
                    fragmentStepper1TextView.setText("Exceeded 5 hours limit");
                }
                else {
                    fragmentStepper1TimeBtn.setText(String.format(Locale.getDefault(), "%02d:%02d", hour, minute));
                    fragmentStepper1TimeBtn.setTextSize(17);
                    fragmentStepper1TextView.setText("");
                }
            }
        };

         int style = android.app.AlertDialog.THEME_HOLO_DARK;

        TimePickerDialog timePickerDialog = new TimePickerDialog(this, style, onTimeSetListener, hour, minute, true);

        timePickerDialog.setTitle("Select Time");
        timePickerDialog.show();
    }

//    public void onRadioButtonClicked(View view) {
//
//        boolean checked = ((RadioButton) view).isChecked();
//
//        switch (view.getId()) {
//            case R.id.fragmentStepper1NowRB:
//                if (checked) {
//                    fragmentStepper1TimeBtn.setEnabled(true);
//                } else
//
//                    break;
//        }
//    }


//        String[] arraySpinner = new String[] {
//                "1", "2", "3", "4", "5", "6", "7"
//        };
////        Spinner s = (Spinner) findViewById(R.id.spinner2);
////        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
////                android.R.layout.simple_spinner_item, arraySpinner);
////        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
////        s.setAdapter(adapter);
//
////        Button findDriverBtn = findViewById(R.id.button);
////        Button favorites = findViewById(R.id.button3);
//
//    }
//
//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        MenuInflater inflater = getMenuInflater();
//        inflater.inflate(R.menu.example_menu, menu);
//        return true;
//    }
//
//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        int id = item.getItemId();
//
//        if (id == R.id.item1) {
//            Intent intent = new Intent(PassengerMainActivity.this, PassengerAccountActivity.class);
//            startActivity(intent);
//        }
//        if (id == R.id.item2) {
//            Intent intent = new Intent(PassengerMainActivity.this, PassengerInboxActivity.class);
//            startActivity(intent);
//        }
//        if (id == R.id.item3) {
//            Intent intent = new Intent(PassengerMainActivity.this, PassangerRideHistory.class);
//            startActivity(intent);
//        }
//        if (id == R.id.item4) {
//            Intent intent = new Intent(PassengerMainActivity.this, PassengerMainActivity.class);
//            startActivity(intent);
//        }
//        if (id == R.id.item5) {
//            Intent intent = new Intent(PassengerMainActivity.this, UserLoginActivity.class);
//            startActivity(intent);
//        }
//        return super.onOptionsItemSelected(item);
//    }
//
//    @Override
//    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
//        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
//        switch (requestCode) {
//            case MY_PERMISSIONS_REQUEST_LOCATION: {
//                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
//
//                    if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
//                        Toast.makeText(this, "LOCATION GRANTED", Toast.LENGTH_SHORT).show();
//                    }
//
//                } else if (grantResults.length > 0
//                        && grantResults[1] == PackageManager.PERMISSION_GRANTED) {
//
//                    if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
//                        Toast.makeText(this, "LOCATION GRANTED", Toast.LENGTH_SHORT).show();
//                    }
//                }
//            }
//        }
//    }
//
//
//    @Override
//    protected void onPostCreate(Bundle savedInstanceState) {
//        super.onPostCreate(savedInstanceState);
//
//    }
//
//    @Override
//    public void onConfigurationChanged(Configuration newConfig) {
//        super.onConfigurationChanged(newConfig);
//
//    }
//
//    @Override
//    protected void onPause() {
//        super.onPause();
//
//    }
//
//    @Override
//    protected void onResume() {
//        super.onResume();
//
//        //getPassenger("8");
//        //updatePassenger("8");
//        getDriver("10");
//        //updateDriver("9");
//        //getDriverRides("9");
//        //changeDriverActivity("9",false);
//        //findByEmail("pera.peric@email.com");
//        //findById("10");
//        //sendMessage("10");
//        //getMessages("10");
////        login("pera.peric@email.com","12345678");
//
//        //changeLocation("1");
//
//        LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
//
//        boolean gps = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
//        boolean wifi = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
//        Log.i("wwww", String.valueOf(gps));
//        Log.i("wqqqq", String.valueOf(wifi));
//    }
//
//    /////////////////////////USER///////////////////////////////
//    public void changePassword(String id) {
//
//        Call<String> call = ServiceUtils.userService
//                .changePassword(id,
//                        new ChangePasswordDTO("12345678", "87654321"));
//        call.enqueue(new Callback<String>() {
//            @Override
//            public void onResponse(Call<String> call, Response<String> response) {
//
//                String message = response.body();
//                Log.d("Atribut", message);
//                }
//
//            @Override
//            public void onFailure(Call<String> call, Throwable t) {
//                Log.d("FAIIIL", t.getMessage());
//                Log.d("FAIIIL", "BLATRUC");
//            }
//        });
//    }
//
//    public void findByEmail(String email) {
//
//        Call<UserDTO> call = ServiceUtils.userService.findByEmail(email);
//        call.enqueue(new Callback<UserDTO>() {
//            @Override
//            public void onResponse(Call<UserDTO> call, Response<UserDTO> response) {
//                assert  response != null;
//                UserDTO user = response.body();
//                //Log.d("Email", user.getEmail().toString());
//            }
//
//            @Override
//            public void onFailure(Call<UserDTO> call, Throwable t) {
//                Log.d("FAIIIL", t.getMessage());
//                Log.d("FAIIIL", "BLATRUC");
//
//            }
//        });
//
//        }
//
//    public void findById(String id) {
//
//        Call<UserDTO> call = ServiceUtils.userService.findById(id);
//        call.enqueue(new Callback<UserDTO>() {
//            @Override
//            public void onResponse(Call<UserDTO> call, Response<UserDTO> response) {
//                if(!response.isSuccessful()) return;
//                UserDTO user = response.body();
//                assert user != null;
//            }
//
//            @Override
//            public void onFailure(Call<UserDTO> call, Throwable t) {
//                Log.d("FAIIIL", t.getMessage());
//                Log.d("FAIIIL", "BLATRUC");
//
//            }
//        });
//
//    }
//
//    public void sendMessage(String id) {
//
//        SendMessageDTO sendMessageDTO = new SendMessageDTO();
//        sendMessageDTO.setMessage("poruka");
//        sendMessageDTO.setRideId("1");
//        sendMessageDTO.setType("RIDE");
//
//        Call<MessageDTO> call = ServiceUtils.userService.sendMessage(id, sendMessageDTO);
//        call.enqueue(new Callback<MessageDTO>() {
//            @Override
//            public void onResponse(Call<MessageDTO> call, Response<MessageDTO> response) {
//                MessageDTO messageDTO = response.body();
//                assert messageDTO != null;
//            }
//
//            @Override
//            public void onFailure(Call<MessageDTO> call, Throwable t) {
//                Log.d("Messagge error", "Couldn't send message!");
//            }
//        });
//    }
//
//    public void getMessages(String id) {
//
//        Call<Paginated<MessageDTO>> call = ServiceUtils.userService.getMessages(id);
//
//        call.enqueue(new Callback<Paginated<MessageDTO>>() {
//            @Override
//            public void onResponse(Call<Paginated<MessageDTO>> call, Response<Paginated<MessageDTO>> response) {
//                if(!response.isSuccessful()){
//                    return;
//                }
//                assert response.body() != null;
//                //List<MessageDTO> messages = response.body().getResults();
//
//            }
//
//            @Override
//            public void onFailure(Call<Paginated<MessageDTO>> call, Throwable t) {
//                Log.d("Messagge error", "Couldn't send message!");
//
//            }
//        });
//    }
//
////    public void login(String email, String password) {
////
////        LoginDTO loginDTO = new LoginDTO(email, password);
////        Call<LoginResponseDTO> call = ServiceUtils.userService.login(loginDTO);
////        call.enqueue(new Callback<LoginResponseDTO>() {
////
////            @Override
////            public void onResponse(@NonNull Call<LoginResponseDTO> call, @NonNull Response<LoginResponseDTO> response) {
////                if(!response.isSuccessful()) return;
////                if(response.code() == 204){
////                    return;
////                }
////
////
////                LoginResponseDTO loginResponse = response.body();
//////                String userRole = "";
//////                JWT jwt = new JWT(loginResponse.getToken());
//////                List<HashMap> role =
//////                        jwt.getClaim("role").asList(HashMap.class);
//////                for (Object values: role.get(0).values()){
//////                    userRole = values.toString();
//////                    break;
//////                }
////
////
//////                String email = jwt.getClaim("sub").asString();
//////                Long id = jwt.getClaim("id").asLong();
//////                TokenDTO tokenDTO = TokenDTO.getInstance();
//////                tokenDTO.setToken(loginResponse.getToken());
//////                tokenDTO.setRefreshToken(loginResponse.getRefreshToken());
//////                Intent intent;
//////
//////                if(userRole.equalsIgnoreCase("passenger")){
//////                    setSharedPreferences("PASSENGER", email, id);
//////                    setTokenPreference(loginResponse.getToken(), loginResponse.getRefreshToken());
//////                    intent = new Intent(PassengerMainActivity.this, PassengerMainActivity.class);
//////                    startActivity(intent);
//////
//////                }
//////                else if(userRole.equalsIgnoreCase("driver")) {
//////                    setSharedPreferences("DRIVER", email, id);
//////                    setTokenPreference(loginResponse.getToken(), loginResponse.getRefreshToken());
//////                    intent = new Intent(PassengerMainActivity.this, DriverMainActivity.class);
//////                    startActivity(intent);
//////
//////                }
////
////            }
////
////            @Override
////            public void onFailure(Call<LoginResponseDTO> call, Throwable t) {
////                Log.d("Login Failed", t.getMessage());
////            }
////        });
////    }
//
//
//    /////////////////////////PASSENGER///////////////////////////////
//    public void getPassenger(String id) {
//
//        Call<PassengerDTO> call = ServiceUtils.passengerService.getPassenger(id);
//        call.enqueue(new Callback<PassengerDTO>() {
//            @Override
//            public void onResponse(Call<PassengerDTO> call, Response<PassengerDTO> response) {
//                if (!response.isSuccessful()) return;
//
//                PassengerDTO passenger = response.body();
//                Log.d("Atribut", passenger.getName().toString());
//
//            }
//
//            @Override
//            public void onFailure(Call<PassengerDTO> call, Throwable t) {
//                Log.d("FAIIIL", t.getMessage());
//                Log.d("FAIIIL", "BLATRUC");
//            }
//        });
//    }
//
//    public void updatePassenger(String id) {
//        PassengerDTO passenger = new PassengerDTO();
//        passenger.setAddress("adressaaa");
//        passenger.setTelephoneNumber("6543456576");
//        passenger.setName("pera2");
//        passenger.setSurname("peric2");
//        Call<PassengerDTO> call = ServiceUtils.passengerService.updatePassenger(id, passenger);
//
//        call.enqueue(new Callback<PassengerDTO>() {
//            @Override
//            public void onResponse(Call<PassengerDTO> call, Response<PassengerDTO> response) {
//                if (!response.isSuccessful()) return;
//
//                PassengerDTO passenger = response.body();
//                Log.d("Atribut", passenger.getName().toString());
//
//            }
//
//            @Override
//            public void onFailure(Call<PassengerDTO> call, Throwable t) {
//                Log.d("FAIIIL", t.getMessage());
//                Log.d("FAIIIL", "BLATRUC");
//            }
//        });
////        call.enqueue(new Callback<PassengerDTO>() {
////            @Override
////            public void onResponse(Call<PassengerDTO> call, Response<PassengerDTO> response) {
////                if (!response.isSuccessful()) {
////                    Toast.makeText(null, "Something went wrong!", Toast.LENGTH_SHORT).show();
////                }
////                PassengerDTO passengerDTO = response.body();
////                assert passengerDTO != null;
////                TextView tvName = getActivity().findViewById(R.id.passengerNameNavigation);
////                String fullName = passengerDTO.getName() + " " + passengerDTO.getSurname();
////                tvName.setText(fullName);
////
////                TextView tvPhoneNumber = getActivity().findViewById(R.id.passengerPhoneNavigation);
////                tvPhoneNumber.setText(passengerDTO.getTelephoneNumber());
////                CircleImageView cv = getActivity().findViewById(R.id.passengerProfilePictureNavigation);
////                String base64Image = passengerDTO.getProfilePicture().split(",")[1];
////                byte[] decodedString = Base64.decode(base64Image, Base64.DEFAULT);
////                Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
////                cv.setImageBitmap(decodedByte);
////
////                getActivity().getSupportFragmentManager().popBackStackImmediate();
////
////            }
//
////            @Override
////            public void onFailure(Call<PassengerDTO> call, Throwable t) {
////                Toast.makeText(null, t.getMessage(), Toast.LENGTH_SHORT).show();
////            }
////        });
//    }
//
//    /////////////////////////DRIVER///////////////////////////////
//    public void getDriver(String id) {
//
//        Call<DriverDTO> call = ServiceUtils.driverService.getDriver(id);
//        call.enqueue(new Callback<DriverDTO>() {
//            @Override
//            public void onResponse(Call<DriverDTO> call, Response<DriverDTO> response) {
//                if (!response.isSuccessful()) return;
//
//                DriverDTO driverDTO = response.body();
//
//            }
//
//            @Override
//            public void onFailure(Call<DriverDTO> call, Throwable t) {
//                Log.d("FAIIIL", t.getMessage());
//                Log.d("FAIIIL", "BLATRUC");
//            }
//        });
//    }
//
//    public void updateDriver(String id) {
//        DriverDTO driver = new DriverDTO();
//        driver.setAddress("adressaaa123");
//        driver.setTelephoneNumber("6556576");
//        driver.setName("pera2");
//        driver.setSurname("peric2");
//        Call<DriverDTO> call = ServiceUtils.driverService.updateDriver(id, driver);
//
//        call.enqueue(new Callback<DriverDTO>() {
//            @Override
//            public void onResponse(Call<DriverDTO> call, Response<DriverDTO> response) {
//                if (!response.isSuccessful()) return;
//
//                DriverDTO driverDTO = response.body();
//                Log.d("Atribut", driverDTO.getName().toString());
//
//            }
//
//            @Override
//            public void onFailure(Call<DriverDTO> call, Throwable t) {
//                Log.d("FAIIIL", t.getMessage());
//                Log.d("FAIIIL", "BLATRUC");
//            }
//        });
//    }
//
//    public void getDriverRides(String id){ //ne radi mozda
//        Call<Paginated<DriverRideDTO>> call = ServiceUtils.driverService.getRides(id, 0, 50, "timeOfStart,desc");
//        call.enqueue(new Callback<Paginated<DriverRideDTO>>() {
//            @Override
//            public void onResponse(Call<Paginated<DriverRideDTO>> call, Response<Paginated<DriverRideDTO>> response) {
//                if (!response.isSuccessful()) return;
//
//                Paginated<DriverRideDTO> driverDTO = response.body();
//
//            }
//
//            @Override
//            public void onFailure(Call<Paginated<DriverRideDTO>> call, Throwable t) {
//                Log.d("FAIIIL", t.getMessage());
//                Log.d("FAIIIL", "BLATRUC");
//            }
//        });
//
//    }
//
//    private void changeDriverActivity(String id, boolean isActive) {
//        Call<String> call = ServiceUtils.driverService.changeActivity(id, new DriverActivityDTO(isActive));
//        call.enqueue(new Callback<String>() {
//            @Override
//            public void onResponse(@NonNull Call<String> call, @NonNull Response<String> response) {
//                Toast.makeText(getApplicationContext(), "You are active!", Toast.LENGTH_SHORT).show();
//                Log.d("Atribut", String.valueOf(isActive));
//            }
//            @Override
//            public void onFailure(@NonNull Call<String> call, @NonNull Throwable t) {
//                Log.d("Fail", t.getMessage());
//            }
//        });
//    }

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