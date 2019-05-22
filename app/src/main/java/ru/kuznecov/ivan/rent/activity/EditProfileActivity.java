package ru.kuznecov.ivan.rent.activity;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import ru.kuznecov.ivan.rent.R;
import ru.kuznecov.ivan.rent.model.User;
import ru.kuznecov.ivan.rent.proverka.FireBaseSaveAvatar;
import ru.kuznecov.ivan.rent.proverka.NetworkRegister;
import ru.kuznecov.ivan.rent.service.DataBaseService;
import ru.kuznecov.ivan.rent.service.DataBaseServiceImpl;

public class EditProfileActivity extends AppCompatActivity {
    private static final String TAG = "EditProfileActivity";

    static final int REQUEST_IMAGE_CAPTURE = 1;

    private NetworkRegister<String, User> mNetReg;
    //Ui
    private TextView changePhoto;
    private EditText edTexName;
    private EditText edTexPhone;
    private EditText edTexEmail;
    private ImageView btnClose;
    private ImageView btnUpdateUser;
    private ProgressBar progressBar;
    //Class
    private User user;
    private DataBaseService dataBaseService;
    private FireBaseSaveAvatar fireBaseSaveAvatar;
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
        user = dataBaseService.readUser();
        fireBaseSaveAvatar = new FireBaseSaveAvatar();
        fireBaseSaveAvatar.setUriPhotoListener(new FireBaseSaveAvatar.UriPhotoListener() {
            @Override
            public void getStringPhoto(String photo) {
                user.setPhoto(photo);
                mNetReg.queueMsg("update", user);
            }
        });
        initUi();


    }

    @Override
    protected void onStart() {
        super.onStart();

        insertEditText();

        mNetReg = NetworkRegister.getInstance();
        mNetReg.setEditProfileListener(new NetworkRegister.EditProfileListener() {
            @Override
            public void updateUser(User user) {
                progressBar.setVisibility(View.INVISIBLE);
                if (user == null) {
                    Toast.makeText(EditProfileActivity.this, "Попробуйте позже ", Toast.LENGTH_LONG).show();
                    return;
                }
                dataBaseService.update(user);
                finish();
            }
        });

    }

    private void initUi() {
        changePhoto = findViewById(R.id.change_profile_photo);
        progressBar = findViewById(R.id.edit_profile_progress_bar);
        progressBar.setVisibility(View.INVISIBLE);
        edTexName = findViewById(R.id.name_input);
        edTexEmail = findViewById(R.id.email_input);
        edTexPhone = findViewById(R.id.phone_input);
        btnUpdateUser = findViewById(R.id.save_image);
        btnClose = findViewById(R.id.close_image);
        btnClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        btnUpdateUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = edTexName.getText().toString();
                String email = edTexEmail.getText().toString();
                String phone = edTexPhone.getText().toString();
                if (!name.equals(user.getName()) || !email.equals(user.getEmail()) || !phone.equals(user.getPhone())){
                    user.setName(name);
                    user.setEmail(email);
                    user.setPhone(phone);
                    mNetReg.queueMsg("update", user);
                    progressBar.setVisibility(View.VISIBLE);
                } else
                    Toast.makeText(EditProfileActivity.this, "Данные не были изменены", Toast.LENGTH_LONG).show();
            }
        });
        changePhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dispatchTakePictureIntent();
            }
        });
    }

    private void insertEditText() {
        edTexName.setText(user.getName());
        edTexEmail.setText(user.getEmail());
        edTexPhone.setText(user.getPhone());
    }


    private void dispatchTakePictureIntent() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            File image = null;
            try {
                image = createImageFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
            photoUri = FileProvider.getUriForFile(this,
                    "ru.kuznecov.ivan.rent.fileprovider",
                    image);
            takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoUri);
            startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK){
            long id = user.getId();

            fireBaseSaveAvatar.saveAvatar(id, photoUri);
            progressBar.setVisibility(View.VISIBLE);
        }
    }

    private File createImageFile() throws IOException {
        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(
                imageFileName,  /* prefix */
                ".jpg",         /* suffix */
                storageDir      /* directory */
        );
        // Save a file: path for use with ACTION_VIEW intents
        return image;
    }

}
