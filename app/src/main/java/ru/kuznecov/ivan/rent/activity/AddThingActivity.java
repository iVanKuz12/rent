package ru.kuznecov.ivan.rent.activity;

import android.os.Bundle;
import android.util.Log;

import ru.kuznecov.ivan.rent.R;


public class AddThingActivity extends BaseActivity {
    private static final String TAG = "AddThingActivity";



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        initBottomNavigationView(0);
        Log.i("Search", "Hi!");

    }





}
