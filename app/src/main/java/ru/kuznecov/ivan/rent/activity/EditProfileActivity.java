package ru.kuznecov.ivan.rent.activity;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import ru.kuznecov.ivan.rent.R;
import ru.kuznecov.ivan.rent.model.User;

public class EditProfileActivity extends AppCompatActivity {
    private static final String TAG = "EditProfileActivity";

    //Ui
    private EditText edTexName;
    private EditText edTexNumber;
    private EditText edTexEmail;
    private ImageView btnClose;

    //Class
    private User user;


    public static Intent newEditProfileActivityIntent(Context context){
        Intent intent = new Intent(context, EditProfileActivity.class);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);


        btnClose = (ImageView) findViewById(R.id.close_image);
        btnClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

}
