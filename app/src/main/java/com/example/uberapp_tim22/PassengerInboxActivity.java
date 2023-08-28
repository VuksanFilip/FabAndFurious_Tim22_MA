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
import com.example.uberapp_tim22.DTO.ResponseChatDTO;
import com.example.uberapp_tim22.DTO.ResponseRideDTO;
import com.example.uberapp_tim22.adapters.ChatBoxListAdapter;
import com.example.uberapp_tim22.adapters.RideListAdapter;
import com.example.uberapp_tim22.fragments.ChatFragment;
import com.example.uberapp_tim22.fragments.InboxChatFragment;
import com.example.uberapp_tim22.fragments.LiveChatFragment;
import com.example.uberapp_tim22.fragments.PassengerLiveChatFragment;
import com.example.uberapp_tim22.service.ServiceUtils;
import com.example.uberapp_tim22.service.UserService;

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

        Bundle bundle = getIntent().getExtras();
        Long otherId = (Long) bundle.getSerializable("otherIdd");
        Long myId = (Long) bundle.getSerializable("myIdd");

        Intent intentUserService = new Intent(getApplicationContext(), UserService.class);
        intentUserService.putExtra("method", "getChats");
        intentUserService.putExtra("myId", myId);
        intentUserService.putExtra("otherId", otherId);

        startService(intentUserService);
        Log.i("MOJ ID", String.valueOf(myId));
        getChats(myId);
        chatBoxListRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        chatBoxListRecyclerView.setAdapter(chatBoxListAdapter);
    }


    private void getChats(Long myId) {
        Call<List<ResponseChatDTO>> call = ServiceUtils.chatService.getChatsOfUser(myId);
        Log.i("TU CHATTT", "USAO");
        call.enqueue(new Callback<List<ResponseChatDTO>>() {
            @Override
            public void onResponse(Call<List<ResponseChatDTO>> call, Response<List<ResponseChatDTO>> response){
                Log.i("TOO", "USAO");


                if (response.isSuccessful()) {
                    List<ResponseChatDTO> responseChats = response.body();
                    Log.i("USAO CHATTT", String.valueOf(responseChats.size()));
                    chatBoxListAdapter.setCheckBoxlist(responseChats);

                } else {
                    onFailure(call, new Throwable("API call failed with status code: " + response.code()));
                }
            }

            @Override
            public void onFailure(Call<List<ResponseChatDTO>> call, Throwable t) {
                Log.e("PassangerRideHistory", "API call failed: " + t.getMessage());
                Toast.makeText(PassengerInboxActivity.this, "API call failed: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onChatBoxItemClick(ResponseChatDTO chat) {
        InboxChatFragment fragment = new InboxChatFragment();

//        Intent intent = new Intent(getApplicationContext(), PassengerInboxActivity.class);
//        intent.putExtra("responseChat", chat);
//        intent.putExtra("myIdd", chat.getMyId());
//        intent.putExtra("otherIdd", chat.getOtherId());
//        intent.putExtra("rideIdd", 5L);


        Log.i("CLICK CHAT", chat.getOtherName());
        Bundle args = new Bundle();
        args.putSerializable("responseChat", chat);
        args.putLong("myIdd", chat.getMyId());
        args.putLong("otherIdd", chat.getOtherId());
        args.putLong("rideIdd", 5L);
        fragment.setArguments(args);

        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.chat, fragment)
                .addToBackStack(null)
                .commit();
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