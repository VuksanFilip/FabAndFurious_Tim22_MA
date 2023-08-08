package com.example.uberapp_tim22.fragments;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.fragment.app.Fragment;

import com.example.uberapp_tim22.R;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.WebSocket;
import okhttp3.WebSocketListener;

public class PassengerLiveChatFragment extends Fragment {

    private static final String TAG = "WebSocketFragment";
    private WebSocket webSocket;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_passenger_livechat, container, false);

        String serverUri = "ws://your-server/your-websocket-endpoint";

        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder().url(serverUri).build();
        WebSocketListener webSocketListener = new WebSocketListener() {
            @Override
            public void onOpen(WebSocket webSocket, okhttp3.Response response) {
                Log.d(TAG, "WebSocket connection opened");
                // You can send messages here using webSocket.send("Your Message");
            }

            @Override
            public void onMessage(WebSocket webSocket, String text) {
                Log.d(TAG, "WebSocket message received: " + text);
                // Handle incoming messages here.
            }

            @Override
            public void onClosed(WebSocket webSocket, int code, String reason) {
                Log.d(TAG, "WebSocket connection closed");
            }

            @Override
            public void onFailure(WebSocket webSocket, Throwable t, okhttp3.Response response) {
                Log.e(TAG, "WebSocket error occurred: " + t.getMessage());
            }
        };

        webSocket = client.newWebSocket(request, webSocketListener);

        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (webSocket != null) {
            webSocket.cancel();
        }
    }
}
