package ru.kuznecov.ivan.rent.activity;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

import ru.kuznecov.ivan.rent.R;

public abstract class BaseActivity extends AppCompatActivity {

    private BottomNavigationView navView;
    private Intent intent;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {

            switch (item.getItemId()) {
                case R.id.menu_add_thing:
                    openActivity(AddThingActivity.class);
                    return true;
                case R.id.menu_home:
                    openActivity(HomeActivity.class);
                    return true;
                case R.id.menu_profile:
                    openActivity(ProfileActivity.class);
                    return true;
            }
            return false;
        }
    };

    protected void initBottomNavigationView(int itemNumber) {
        navView = findViewById(R.id.bottom_nav_view);
        navView.setItemIconSize(90);
        navView.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        navView.getMenu().getItem(itemNumber).setChecked(true);


    }
    private void openActivity(Class clazz){
        intent = new Intent(this, clazz);
        intent.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
        startActivity(intent);
        //overridePendingTransition(0, 0);
    }

}
