package ru.kuznecov.ivan.rent.activity;

import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import com.google.firebase.auth.FirebaseAuth;
import ru.kuznecov.ivan.rent.R;
import ru.kuznecov.ivan.rent.model.User;
import ru.kuznecov.ivan.rent.proverka.NetworkRegister;


public class HomeActivity extends BaseActivity {

    private static final String TAG = "HomeActiv";
    private NetworkRegister<String, User> networkRegister;
    private Handler mainHandler;

    FirebaseAuth mAuth = FirebaseAuth .getInstance();

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

    }

    @Override
    protected void onStart() {
        super.onStart();
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
