package com.example.vezbe5.service;

import android.app.Service;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import android.util.Log;

import com.example.vezbe5.MainActivity;
import com.example.vezbe5.sync.SyncTask;
import com.example.vezbe5.tools.ReviewerTools;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class SyncService extends Service {

    public static String RESULT_CODE = "RESULT_CODE";

    ExecutorService executor = Executors.newSingleThreadExecutor(); //kreira samo jedan thread
    Handler handler = new Handler(Looper.getMainLooper()); //handler koji upravlja glavim thread-om od aplikacije (applications main thread)

    /*
     * Metoda koja se poziva prilikom izvrsavanja zadatka servisa
     * Koristeci Intent mozemo prilikom startovanja servisa proslediti
     * odredjene parametre.
     * */
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.i("REZ", "SyncService onStartCommand");

        /*
         * Provericemo trenutnu povezanost sa mrezom.
         * Za ovo koristimo dostupne pozive android operativnog sistema
         * */
        int status = ReviewerTools.getConnectivityStatus(getApplicationContext());
//        Log.i("STATUS MOJ", "STATUS JE " + status);
        /*
         * Primer poziva asinhronog zadatka ako ima veze ka mrezi
         * npr. sinhronizacija mail-ova fotografija, muzike dokumenata isl.
         * */
        if(status == ReviewerTools.TYPE_WIFI || status == ReviewerTools.TYPE_MOBILE){
            // Alternativa za SyncTask
            executor.execute(() -> {
                //Background work here
                Log.i("REZ", "Background work here");
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                handler.post(() -> {
                    //UI Thread work here
                    Log.i("REZ", "UI Thread work here");
                    Intent ints = new Intent(MainActivity.SYNC_DATA);
                    int intsStatus = ReviewerTools.getConnectivityStatus(getApplicationContext());
                    ints.putExtra(RESULT_CODE, intsStatus);
                    getApplicationContext().sendBroadcast(ints);
                });
            });
//          u starijim verzijama
//          new SyncTask(getApplicationContext()).execute();
        }

        /*
         * Zaustaviti servis nakon obavljenog pokretanja asinhronog zadatka.
         * Ovu metodu nije potrebno pozvati ako zelimo da nasa aplikacija
         * konstantno osluskuje na neke izmene (npr. novi email, viber poruka isl)
         * */
        stopSelf();

        /*
         * Ako iz nekog razloga operativni sistem ubije servis
         * ne kreirati novi.
         * */
        return START_NOT_STICKY;
    }

    @Override
    public IBinder onBind(Intent arg0) {
        return null;
    }

}
