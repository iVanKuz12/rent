package ru.kuznecov.ivan.rent.activity;

import android.os.Bundle;
import android.os.Handler;
import android.util.Log;

import com.google.firebase.auth.FirebaseAuth;

import java.util.List;

import ru.kuznecov.ivan.rent.R;
import ru.kuznecov.ivan.rent.model.City;
import ru.kuznecov.ivan.rent.model.Thing;
import ru.kuznecov.ivan.rent.model.User;
import ru.kuznecov.ivan.rent.utils.NetworkRegister;


public class HomeActivity extends BaseActivity {

    private static final String TAG = "HomeActiv";
    private NetworkRegister<String, User> networkRegister;
    private Handler mainHandler;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        initBottomNavigationView(1);
        if (mainHandler == null){
            networkRegister = NetworkRegister.getInstance();
            mainHandler = new Handler();
            try {
                networkRegister.start();
            } catch (IllegalThreadStateException e) {
                e.printStackTrace();
            }
            networkRegister.setMainHandler(mainHandler);
            networkRegister.getLooper();
        }

        homeListener();


    }

    private void homeListener() {
        networkRegister.setHomeListener(new NetworkRegister.HomeListener() {
            @Override
            public void getAllThing(List<Thing> list) {
                for (Thing thing: list){
                    Log.i(TAG, "looki" + thing);
                }
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        networkRegister.queueMsgGetAllThing();

    }

    @Override
    protected void onResume() {
        super.onResume();
        setChekedItem(1);
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        networkRegister.clearQueue();
        networkRegister.quit();
    }

}
