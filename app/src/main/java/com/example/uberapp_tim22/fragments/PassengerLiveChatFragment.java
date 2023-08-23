package com.example.uberapp_tim22.fragments;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.uberapp_tim22.DTO.IdAndEmailDTO;
import com.example.uberapp_tim22.DTO.NewLocationDTO;
import com.example.uberapp_tim22.R;
import com.example.uberapp_tim22.adapters.MessageAdapter;
import com.example.uberapp_tim22.model.enums.VehicleName;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.WebSocket;
import okhttp3.WebSocketListener;
import okio.ByteString;

public class PassengerLiveChatFragment extends Fragment {

    private WebSocket webSocket;
    private MessageAdapter adapter;
    private ListView messsageList;
    private EditText messageBox;
    private TextView send;
    private Long driverId, rideId, myId;
    private Bundle bundle = new Bundle();
    private SharedPreferences sharedPreferences;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_live_chat, container, false);

        messsageList = view.findViewById(R.id.messageList);
        messageBox = view.findViewById(R.id.messageBox);
        send = view.findViewById(R.id.send);
        bundle = getArguments();

        sharedPreferences = getContext().getSharedPreferences("preferences", Context.MODE_PRIVATE);
        Long myId = sharedPreferences.getLong("pref_myId", 0);
        Log.d("MyIddddddd", String.valueOf(myId));


/*        myId = bundle.getLong("myId");
        driverId = bundle.getLong("driverId");
        rideId = bundle.getLong("rideId");*/
//
//        Log.d("MyId", String.valueOf(myId));
//        Log.d("DriverId", String.valueOf(driverId));
//        Log.d("RideId", String.valueOf(rideId));

        instantiateWebSocket();

        adapter = new MessageAdapter(getContext());
        messsageList.setAdapter(adapter);

        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String message = messageBox.getText().toString();
                if (!message.isEmpty()) {
                    webSocket.send(message);
                    messageBox.setText("");

                    JSONObject jsonObject = new JSONObject();
                    try {
                        jsonObject.put("message", message);
                        jsonObject.put("byServer", false);
                        adapter.addItem(jsonObject);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }
        });

        return view;
    }

    private void instantiateWebSocket() {
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder().url("ws://192.168.1.17:8084/socket/websocket").build();
        SocketListener socketListener = new SocketListener();
        webSocket = client.newWebSocket(request, socketListener);
    }

    private class SocketListener extends WebSocketListener {
        @Override
        public void onOpen(WebSocket webSocket, Response response) {
            getActivity().runOnUiThread(() -> {
                Toast.makeText(getContext(), "Connection Established!", Toast.LENGTH_LONG).show();
            });
        }

        @Override
        public void onMessage(WebSocket webSocket, final String text) {
            getActivity().runOnUiThread(() -> {
                JSONObject jsonObject = new JSONObject();
                try {
                    jsonObject.put("message", text);
                    jsonObject.put("byServer", true);
                    adapter.addItem(jsonObject);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            });        }

        @Override
        public void onMessage(WebSocket webSocket, ByteString bytes) {
            super.onMessage(webSocket, bytes);
        }

        @Override
        public void onClosing(WebSocket webSocket, int code, String reason) {
            super.onClosing(webSocket, code, reason);
        }

        @Override
        public void onClosed(WebSocket webSocket, int code, String reason) {
            if (webSocket != null) {
                webSocket.cancel();
            }        }

        @Override
        public void onFailure(WebSocket webSocket, final Throwable t, @Nullable final Response response) {
            super.onFailure(webSocket, t, response);
        }
    }
}


//        String serverUri = "ws://192.168.55.189:8084/socket/websocket";
//
//        OkHttpClient client = new OkHttpClient();
//        Request request = new Request.Builder().url(serverUri).build();
//        WebSocketListener webSocketListener = new WebSocketListener() {
//            @Override
//            public void onOpen(WebSocket webSocket, okhttp3.Response response) {
//                Log.d(TAG, "WebSocket connection opened");
//                // You can send messages here using webSocket.send("Your Message");
//            }
//
//            @Override
//            public void onMessage(WebSocket webSocket, String text) {
//                Log.d(TAG, "WebSocket message received: " + text);
//                // Handle incoming messages here.
//            }
//
//            @Override
//            public void onClosed(WebSocket webSocket, int code, String reason) {
//                Log.d(TAG, "WebSocket connection closed");
//            }
//
//            @Override
//            public void onFailure(WebSocket webSocket, Throwable t, okhttp3.Response response) {
//                Log.e(TAG, "WebSocket error occurred: " + t.getMessage());
//            }
//        };
//
//        webSocket = client.newWebSocket(request, webSocketListener);
//
//        return view;
//    }
//
//    @Override
//    public void onDestroyView() {
//        super.onDestroyView();
//        if (webSocket != null) {
//            webSocket.cancel();
//        }
//    }



