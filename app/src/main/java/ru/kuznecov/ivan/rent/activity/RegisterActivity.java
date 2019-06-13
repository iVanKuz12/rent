package ru.kuznecov.ivan.rent.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import ru.kuznecov.ivan.rent.R;
import ru.kuznecov.ivan.rent.fragment.EmailFragment;
import ru.kuznecov.ivan.rent.fragment.NameFragment;
import ru.kuznecov.ivan.rent.fragment.PhoneFragment;
import ru.kuznecov.ivan.rent.pojo.User;
import ru.kuznecov.ivan.rent.service.Network;
import ru.kuznecov.ivan.rent.service.DataBaseService;
import ru.kuznecov.ivan.rent.service.DataBaseServiceImpl;


public class RegisterActivity extends AppCompatActivity implements EmailFragment.ListenerEmail
        , PhoneFragment.ListenerPhone, NameFragment.ListenerName {
    private static final String TAG = "RegisterActivity";
    private static final String OK = "OK";
    private static final String ERROR = "ERROR";

    private FragmentManager mFragmentManager;
    private Fragment mFragment;
    private Handler mHandler;
    private Network<String, User> mNetReg;
    private DataBaseService mDataBaseService;
    private Context mContext;
    private User mUser;

    private String mEmail;
    private String mPhone;
    private String mName;
    private String mPassword;

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

        mHandler = new Handler();
        mNetReg = Network.getInstance();
        mUser = new User();
        mContext = this;
        mDataBaseService = new DataBaseServiceImpl(this);
        mNetReg.setRegisterListener(new Network.RegisterListener() {
            @Override
            public void validateEmail(String response) {
                if (response.equals(OK))
                    replaceFragment(new PhoneFragment());
                else
                    showToast("Email was registered");

            }

            @Override
            public void validatePhone(String response) {
                if (response.equals(OK))
                    replaceFragment(new NameFragment());
                else
                    showToast("Phone was registered");

            }

            @Override
            public void registerUser(User user) {
                if (user == null)
                    return;
                mDataBaseService.saveUser(user);
                Intent intent = ProfileActivity.newProfileActivityIntent(mContext);
                startActivity(intent);
                finish();
            }
        });


    }

    private void showToast(String str){
        Toast.makeText(this, str, Toast.LENGTH_LONG).show();
    }

    private void initFragment() {
        mFragmentManager = getSupportFragmentManager();
        mFragment = mFragmentManager.findFragmentById(R.id.frame_layout);
        if (mFragment == null){
            mFragment = new EmailFragment();
            mFragmentManager.beginTransaction()
                    .add(R.id.frame_layout, mFragment)
                    .commit();

        }
    }

    private void replaceFragment(Fragment fragment){
        mFragmentManager.beginTransaction().replace(R.id.frame_layout, fragment)
                .addToBackStack(null)
                .commit();
    }


    @Override
    public void onNextEmail(String email) {
        if (!email.isEmpty()){
            mUser.setEmail(email);
            mNetReg.queueMsg("email", mUser);

        }
    }

    @Override
    public void onNextPhone(String phone) {
        if (!phone.isEmpty()){
            mUser.setPhone(phone);
            mNetReg.queueMsg("phone", mUser);
        }
    }

    @Override
    public void registration(String name, String password) {
        if (!name.isEmpty() & !password.isEmpty()){
            mUser.setName(name);
            mUser.setPassword(password);
            mNetReg.queueMsg("register", mUser);
        }
    }


}

