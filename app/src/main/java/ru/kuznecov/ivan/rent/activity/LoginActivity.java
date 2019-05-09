package ru.kuznecov.ivan.rent.activity;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import ru.kuznecov.ivan.rent.R;
import ru.kuznecov.ivan.rent.model.User;
import ru.kuznecov.ivan.rent.service.DataBaseService;
import ru.kuznecov.ivan.rent.service.DataBaseServiceImpl;
import ru.kuznecov.ivan.rent.service.NetworkService;
import ru.kuznecov.ivan.rent.service.NetworkServiceImpl;

import static android.widget.Toast.LENGTH_LONG;

public class LoginActivity extends AppCompatActivity {
    private static final String TAG = "LoginActivity";

//UI
    private Button btnLogIn;
    private EditText edTexEmail;
    private EditText edTexPassword;
    private TextView texViewCreateAcc;

//SIGN_IN
    private String email;
    private String password;

//Classes
    private Context context;
    private User user;
    private NetworkService service;
    private DataBaseService dataBaseService;

    public static Intent newLoginActivityIntent(Context context){
        Intent intent = new Intent(context, LoginActivity.class);
        return intent;
    }


    @Override
    protected void onStart() {
        super.onStart();
        context = this;
        service = new NetworkServiceImpl();
        dataBaseService = new DataBaseServiceImpl(this);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        initUiAndClick();

    }

    private void initUiAndClick() {
        edTexEmail = (EditText) findViewById(R.id.login_email_input);
        edTexPassword = (EditText) findViewById(R.id.login_password_input);
        texViewCreateAcc = (TextView) findViewById(R.id.login_create_account);
        btnLogIn = (Button)findViewById(R.id.login_btn);
        btnLogIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                email = edTexEmail.getText().toString();
                password = edTexPassword.getText().toString();
                if (email != null & password != null){

                    user = service.signIn(email, password);
                    if (user == null){
                        Toast.makeText(context, "Enter the correct data", LENGTH_LONG).show();
                        return;
                    }
                    dataBaseService.saveUser(user);
                    Intent intent = ProfileActivity.newProfileActivityIntent(context);
                    startActivity(intent);
                    finish();
                }

            }
        });
        texViewCreateAcc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = RegisterActivity.newRegisterActivityIntent(context);
                startActivity(intent);
            }
        });


    }
}
