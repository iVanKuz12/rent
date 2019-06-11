package ru.kuznecov.ivan.rent.activity;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import de.hdodenhof.circleimageview.CircleImageView;
import ru.kuznecov.ivan.rent.R;
import ru.kuznecov.ivan.rent.model.User;
import ru.kuznecov.ivan.rent.utils.FireBaseSaveAvatar;
import ru.kuznecov.ivan.rent.utils.MakePhoto;
import ru.kuznecov.ivan.rent.utils.NetworkRegister;
import ru.kuznecov.ivan.rent.service.DataBaseService;
import ru.kuznecov.ivan.rent.service.DataBaseServiceImpl;

public class EditProfileActivity extends AppCompatActivity implements View.OnClickListener {
    private static final String TAG = "EditProfileActivity";
    //Ui
    private CircleImageView profileImage;
    private TextView changePhoto;
    private EditText edTexName;
    private EditText edTexPhone;
    private EditText edTexEmail;
    private ImageView btnClose;
    private ImageView btnUpdateUser;
    private ProgressBar progressBar;
    //Class
    private NetworkRegister<String, User> mNetReg;
    private User user;
    private DataBaseService dataBaseService;
    private FireBaseSaveAvatar fireBaseSaveAvatar;
    private MakePhoto makePhoto;
    private Uri photoUri;

    public static Intent newEditProfileActivityIntent(Context context){
        Intent intent = new Intent(context, EditProfileActivity.class);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);

        dataBaseService = new DataBaseServiceImpl(this);
        fireBaseSaveAvatar = new FireBaseSaveAvatar();
        makePhoto = new MakePhoto(this);
        mNetReg = NetworkRegister.getInstance();
        user = dataBaseService.readUser();

        initUi();

    }

    @Override
    protected void onStart() {
        super.onStart();

        insertData();

        interfaceListeners();

    }

    private void interfaceListeners() {
        fireBaseSaveAvatar.setUriPhotoListener(new FireBaseSaveAvatar.UriPhotoListener() {
            @Override
            public void getStringPhoto(String photo) {
                user.setPhoto(photo);
                mNetReg.queueMsg("update", user);
            }
        });
        mNetReg.setEditProfileListener(new NetworkRegister.EditProfileListener() {
            @Override
            public void updateUser(User user) {
                progressBar.setVisibility(View.INVISIBLE);
                if (user == null) {
                    Toast
                            .makeText(EditProfileActivity.this
                            , "Попробуйте позже "
                            , Toast.LENGTH_LONG).show();
                    return;
                }
                dataBaseService.update(user);
                finish();
            }
        });
    }

    private void initUi() {
        profileImage = findViewById(R.id.profile_image);
        changePhoto = findViewById(R.id.change_profile_photo);
        progressBar = findViewById(R.id.edit_profile_progress_bar);
        edTexName = findViewById(R.id.name_input);
        edTexEmail = findViewById(R.id.email_input);
        edTexPhone = findViewById(R.id.phone_input);
        btnUpdateUser = findViewById(R.id.image_save);
        btnClose = findViewById(R.id.image_close);

        changePhoto.setOnClickListener(this);
        btnUpdateUser.setOnClickListener(this);
        btnClose.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.change_profile_photo :
                photoUri = makePhoto.dispatchTakePictureIntent();
                break;
            case R.id.image_save:
                updateUser();
                break;
            case R.id.image_close:
                finish();
                break;
            default:
                break;
        }
    }

    private void updateUser() {
        String name = edTexName.getText().toString();
        String email = edTexEmail.getText().toString();
        String phone = edTexPhone.getText().toString();
        if
                (!name.equals(user.getName())
                || !email.equals(user.getEmail())
                || !phone.equals(user.getPhone())){
            user.setName(name);
            user.setEmail(email);
            user.setPhone(phone);
            mNetReg.queueMsg("update", user);
            progressBar.setVisibility(View.VISIBLE);
        } else
            Toast
                    .makeText(EditProfileActivity.this
                    , "Данные не были изменены"
                    , Toast.LENGTH_LONG).show();
    }

    private void insertData() {
        edTexName.setText(user.getName());
        edTexEmail.setText(user.getEmail());
        edTexPhone.setText(user.getPhone());
        if (user.getPhoto() != null){
            if (user.getPhoto() != null){
                Glide
                        .with(this)
                        .load(user.getPhoto())
                        .placeholder(R.drawable.android_picture)
                        .into(profileImage);
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (requestCode == makePhoto.REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK){
            long id = user.getId();
            fireBaseSaveAvatar.saveAvatar(photoUri, "users/" + id + "/photo");
            progressBar.setVisibility(View.VISIBLE);

        }
    }
}
