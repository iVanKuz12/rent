package ru.kuznecov.ivan.rent.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import de.hdodenhof.circleimageview.CircleImageView;
import ru.kuznecov.ivan.rent.R;
import ru.kuznecov.ivan.rent.model.User;
import ru.kuznecov.ivan.rent.service.DataBaseService;
import ru.kuznecov.ivan.rent.service.DataBaseServiceImpl;


public class ProfileActivity extends BaseActivity {
    private static final String TAG = "ProfileActivity";


    //Ui
    private TextView texViewName;
    private TextView texViewPhone;
    private CircleImageView profileImage;
    private Button editProfile;
    private ImageView output;
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
        initUiAndClick();

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        context = this;
        dataBaseService = new DataBaseServiceImpl(context);
        user = dataBaseService.readUser();
        if (user == null) {
            intent = LoginActivity.newLoginActivityIntent(context);
            startActivity(intent);
            finish();
        }


        initBottomNavigationView(2);

    }

    @Override
    protected void onResume() {
        super.onResume();
        user = dataBaseService.readUser();
        insertData();
    }

    private void initUiAndClick() {
        profileImage = findViewById(R.id.image_profile);
        output = findViewById(R.id.output);
        texViewName = findViewById(R.id.name);
        texViewPhone = findViewById(R.id.phone);
        editProfile =  findViewById(R.id.btn_edit_profile);
        insertData();
        editProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = EditProfileActivity.newEditProfileActivityIntent(context);
                startActivity(intent);
            }
        });
        output.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dataBaseService.deleteUser();
                finish();
            }
        });

    }

    private void insertData() {
        texViewName.setText(user.getName());
        texViewPhone.setText(user.getPhone());
        if (user.getPhoto() != null){
            Glide
                    .with(this)
                    .load(user.getPhoto())
                    .placeholder(R.drawable.android_picture)
                    .into(profileImage);
        }
    }
}
