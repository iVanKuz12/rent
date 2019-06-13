package ru.kuznecov.ivan.rent.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import ru.kuznecov.ivan.rent.R;
import ru.kuznecov.ivan.rent.adapter.Adapter;
import ru.kuznecov.ivan.rent.pojo.Thing;
import ru.kuznecov.ivan.rent.pojo.User;
import ru.kuznecov.ivan.rent.service.DataBaseService;
import ru.kuznecov.ivan.rent.service.DataBaseServiceImpl;
import ru.kuznecov.ivan.rent.service.Network;


public class ProfileActivity extends BaseActivity {
    private static final String TAG = "ProfileActivity";


    //Ui
    private TextView texViewName;
    private TextView texViewPhone;
    private CircleImageView profileImage;
    private Button editProfile;
    private ImageView output;
    private RecyclerView recyclerView;
    //Class
    private Intent intent;
    private Context context;
    private DataBaseService dataBaseService;
    private User user;
    private Network network;
    private Adapter adapter;

    private List<Thing> thingList;


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

        initBottomNavigationView(2);

        initClass();
        validateLogIn();
        initRecyclerView();
        loadMyThing();
        networkListener();

    }

    private void initRecyclerView() {
        recyclerView = findViewById(R.id.profile_recycler);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new Adapter(this);
        recyclerView.setAdapter(adapter);
    }

    private void loadMyThing() {
        if (user != null)
            network.queueMsgGetThingUserId(user.getId());
    }

    private void validateLogIn() {
        user = dataBaseService.readUser();
        if (user == null) {
            intent = LoginActivity.newLoginActivityIntent(context);
            startActivity(intent);
            finish();
        }
    }

    private void initClass() {
        context = this;
        dataBaseService = new DataBaseServiceImpl(context);
        network = Network.getInstance();
    }

    private void networkListener() {
        network.setProfileListener(new Network.ProfileListener() {
            @Override
            public void loadMyThing(List<Thing> things) {
                thingList = things;
                adapter.setItems(thingList);
            }
        });
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
