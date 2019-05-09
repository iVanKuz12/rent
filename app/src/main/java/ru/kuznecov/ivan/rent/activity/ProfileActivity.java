package ru.kuznecov.ivan.rent.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import ru.kuznecov.ivan.rent.R;
import ru.kuznecov.ivan.rent.model.User;
import ru.kuznecov.ivan.rent.service.DataBaseService;
import ru.kuznecov.ivan.rent.service.DataBaseServiceImpl;


public class ProfileActivity extends BaseActivity {
    private static final String TAG = "ProfileActivity";


    //Ui
    private TextView texViewName;
    private TextView texViewphone;
    private Button editProfile;
    //Class
    private Intent intent;
    private Context context;
    private DataBaseService dataBaseService;
    private User user;


    public static Intent newProfileActivityIntent(Context context){
        Intent intent = new Intent(context, ProfileActivity.class);
        return intent;
    }

    @Override
    protected void onStart() {
        super.onStart();
        context = this;
        dataBaseService = new DataBaseServiceImpl(context);
        user = dataBaseService.readUser();
        if (user == null) {
            intent = LoginActivity.newLoginActivityIntent(context);
            startActivity(intent);
            finish();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        initBottomNavigationView(2);
        initUiAndClick();


    }

    private void initUiAndClick() {
        texViewName = (TextView)findViewById(R.id.name);
        texViewphone = (TextView)findViewById(R.id.phone);
        editProfile = (Button) findViewById(R.id.btn_edit_profile);
        //texViewName.setText(user.getName());
        //texViewphone.setText(user.getPhone());
        editProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openEditProfileActivity();
            }
        });

    }

    private void openEditProfileActivity() {
        Intent intent = EditProfileActivity.newEditProfileActivityIntent(context);
        startActivity(intent);
    }
}
