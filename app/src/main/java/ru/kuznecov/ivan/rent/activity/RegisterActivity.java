package ru.kuznecov.ivan.rent.activity;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import ru.kuznecov.ivan.rent.R;
import ru.kuznecov.ivan.rent.activity.fragment.EmailFragment;
import ru.kuznecov.ivan.rent.activity.fragment.PhoneFragment;


public class RegisterActivity extends AppCompatActivity implements EmailFragment.Listener {
    private static final String TAG = "RegisterActivity";

    private FragmentManager fragmentManager;
    private Fragment fragment;
    private String email;

    public static Intent newRegisterActivityIntent(Context context){
        Intent intent = new Intent(context, RegisterActivity.class);
        return intent;
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        if (savedInstanceState == null)
            initFragment();


    }

    private void initFragment() {
        fragmentManager = getSupportFragmentManager();
        fragment = fragmentManager.findFragmentById(R.id.frame_layout);
        if (fragment == null){
            fragment = new EmailFragment();
            fragmentManager.beginTransaction()
                    .add(R.id.frame_layout, fragment)
                    .commit();

        }
    }


    @Override
    public void onNext(String email) {
        if (!email.isEmpty()){
            this.email = email;
            fragmentManager.beginTransaction().replace(R.id.frame_layout, new PhoneFragment())
                    .addToBackStack(null)
                    .commit();
        }
    }
}

