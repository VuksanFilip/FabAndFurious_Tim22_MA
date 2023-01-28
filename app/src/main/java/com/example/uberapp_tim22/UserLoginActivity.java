package com.example.uberapp_tim22;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.auth0.android.jwt.JWT;
import com.example.uberapp_tim22.DTO.RequestLoginDTO;
import com.example.uberapp_tim22.DTO.ResponseLoginDTO;
import com.example.uberapp_tim22.DTO.TokenDTO;
import com.example.uberapp_tim22.service.ServiceUtils;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

import org.jetbrains.annotations.NotNull;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UserLoginActivity extends AppCompatActivity {

    private SharedPreferences sharedPreferences;
    private Intent intent;

    EditText email;
    EditText password;
    Button loginBtn;
    Button signupBtn;
    Button forgotBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_login);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("FAB Car");

        email = findViewById(R.id.emailLogIn);
        password = findViewById(R.id.passwordLogIn);
        loginBtn = findViewById(R.id.login_button);
        signupBtn = findViewById(R.id.signup_btn);
        forgotBtn = findViewById(R.id.forgot_button);

        forgotBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String em= email.getText().toString();
                FirebaseAuth.getInstance().sendPasswordResetEmail(em).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull @NotNull Task<Void> task) {
                        if (task.isSuccessful()){
                            Toast.makeText(UserLoginActivity.this, "send mail", Toast.LENGTH_SHORT).show();
                            System.out.println("t");
                        }
                        else{
                            Toast.makeText(UserLoginActivity.this, "Fail", Toast.LENGTH_SHORT).show();
                            System.out.println("n");
                        }
                    }
                });
            }
        });

        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String getEmail= email.getText().toString();
                String getPassword = password.getText().toString();
                login(getEmail, getPassword);
            }
        });

        signupBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(UserLoginActivity.this, PassengerRegisterActivity.class);
                startActivity(intent);
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.login_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    public void login(String email, String password){
        RequestLoginDTO loginDTO = new RequestLoginDTO(email, password);
        Call<ResponseLoginDTO> call = ServiceUtils.userService.login(loginDTO);
        call.enqueue(new Callback<ResponseLoginDTO>() {

            @Override
            public void onResponse(@NonNull Call<ResponseLoginDTO> call, @NonNull Response<ResponseLoginDTO> response) {
                if(!response.isSuccessful()) return;
                if(response.code() == 204){
                    Toast.makeText(UserLoginActivity.this, "Email not confirmed!", Toast.LENGTH_SHORT).show();
                    return;
                }

                ResponseLoginDTO loginResponse = response.body();
                JWT jwt = new JWT(loginResponse.getAccessToken());

                Long id = jwt.getClaim("id").asLong();
                String email = jwt.getClaim("sub").asString();
                String role = jwt.getClaim("role").asString();

                setToken(loginResponse);

                if(role.equalsIgnoreCase("PASSENGER")){
                    setPreferences(id, email, role, loginResponse);
                    startActivity(new Intent(UserLoginActivity.this, PassengerMainActivity.class));
                }
                else if(role.equalsIgnoreCase("DRIVER")) {
                    setPreferences(id, email, role, loginResponse);
                    setTokenPreference(loginResponse.getAccessToken(), loginResponse.getRefreshToken());
                    startActivity(new Intent(UserLoginActivity.this, DriverMainActivity.class));
                }
           }

            @Override
            public void onFailure(Call<ResponseLoginDTO> call, Throwable t) {
                Log.d("Login Failed", t.getMessage());
            }
        });
    }

    private void setToken(ResponseLoginDTO loginResponse) {
        TokenDTO tokenDTO = TokenDTO.getInstance();
        tokenDTO.setAccessToken(loginResponse.getAccessToken());
        tokenDTO.setRefreshToken(loginResponse.getRefreshToken());
    }

    private void deleteTokenPreferences() {
        TokenDTO tokenDTO = TokenDTO.getInstance();
        tokenDTO.setAccessToken(null);
        tokenDTO.setRefreshToken(null);
        this.sharedPreferences = getSharedPreferences("preferences", Context.MODE_PRIVATE);
        SharedPreferences.Editor spEditor = this.sharedPreferences.edit();
        spEditor.clear().commit();
    }

    private void setTokenPreference(String token, String refreshToken) {
        this.sharedPreferences = getSharedPreferences("preferences", Context.MODE_PRIVATE);
        SharedPreferences.Editor spEditor = this.sharedPreferences.edit();
        spEditor.putString("pref_accessToken", token);
        spEditor.putString("pref_refreshToken", refreshToken);
    }

    private void setSharedPreferences(Long id, String email, String role){
        this.sharedPreferences = getSharedPreferences("preferences", Context.MODE_PRIVATE);
        SharedPreferences.Editor spEditor = this.sharedPreferences.edit();
        spEditor.putLong("pref_id", id);
        spEditor.putString("pref_email", email);
        spEditor.putString("pref_role", role);
        spEditor.apply();
    }

    private void setPreferences(Long id, String email, String role, ResponseLoginDTO loginResponse){
        setSharedPreferences(id, email, role);
        setTokenPreference(loginResponse.getAccessToken(), loginResponse.getRefreshToken());
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