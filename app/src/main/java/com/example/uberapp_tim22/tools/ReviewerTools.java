package com.example.uberapp_tim22.tools;

import android.content.Context;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.net.NetworkCapabilities;
import android.net.NetworkInfo;
import android.os.Build;
import android.provider.Settings;
import android.util.Log;


public class ReviewerTools {
    public static int TYPE_WIFI = 1;
    public static int TYPE_MOBILE = 2;
    public static int TYPE_NOT_CONNECTED = 0;

    public static int getConnectivityStatus(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            NetworkCapabilities capabilities = cm.getNetworkCapabilities(cm.getActiveNetwork());
            if (capabilities != null) {
                if (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI)) {
                    Log.i("REZ", "Povezan na WIFI");
                    return TYPE_WIFI;
                } else if (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR)) {
                    Log.i("REZ", "Povezan na celularnu mrezu");
                    return TYPE_MOBILE;
                }
            }
        }else{

            NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
            if (null != activeNetwork) {
                if (activeNetwork.getType() == ConnectivityManager.TYPE_WIFI)
                    return TYPE_WIFI;

                if (activeNetwork.getType() == ConnectivityManager.TYPE_MOBILE)
                    return TYPE_MOBILE;
            }
        }

        Log.i("REZ", "Nije povezan na internet");
        return TYPE_NOT_CONNECTED;
    }

//    @SuppressWarnings("deprecation")
//    public static Boolean isLocationEnabled(Context context) {
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
//            LocationManager lm = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
//            return lm.isLocationEnabled();
//        } else {
//            int mode = Settings.Secure.getInt(context.getContentResolver(), Settings.Secure.LOCATION_MODE,
//                    Settings.Secure.LOCATION_MODE_OFF);
//            return (mode != Settings.Secure.LOCATION_MODE_OFF);
//        }
//    }

    public static int calculateTimeTillNextSync(int minutes){
        return 1000 * 60 * minutes;
    }




}
