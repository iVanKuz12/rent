package ru.kuznecov.ivan.rent.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import ru.kuznecov.ivan.rent.R;
import ru.kuznecov.ivan.rent.pojo.User;
import ru.kuznecov.ivan.rent.service.Network;
import ru.kuznecov.ivan.rent.service.DataBaseService;
import ru.kuznecov.ivan.rent.service.DataBaseServiceImpl;

public class LoginActivity extends AppCompatActivity {

    private static final String TAG = "LoginActivity";

//UI
    private Button btnLogIn;
    private EditText edTexEmail;
    private EditText edTexPassword;
    private TextView texViewCreateAcc;
    private ProgressBar progressBar;
//SIGN_IN
    private String email;
    private String password;

//Classes
    private Context context;
    private User user;
    private DataBaseService dataBaseService;
    private Network<String, User> mNetReg;

    public static Intent newLoginActivityIntent(Context context){
        Intent intent = new Intent(context, LoginActivity.class);
        return intent;
    }


    @Override
    protected void onStart() {
        super.onStart();
        //mHandler = new Handler();
        mNetReg = Network.getInstance();
        mNetReg.setLoginListener(new Network.LoginListener() {
            @Override
            public void validateLogin(User user) {
                progressBar.setVisibility(View.INVISIBLE);
                if (user == null) {
                    Toast.makeText(LoginActivity.this, "Ошибка регистрации ", Toast.LENGTH_LONG).show();
                    return;
                }
                dataBaseService.saveUser(user);
                Intent intent = ProfileActivity.newProfileActivityIntent(context);
                startActivity(intent);
                finish();
            }
        });
        initUiAndClick();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);


        context = this;
        dataBaseService = new DataBaseServiceImpl(this);
        user = new User();


    }

    private void initUiAndClick() {
        progressBar = findViewById(R.id.login_progress_bar);
        progressBar.setVisibility(View.INVISIBLE);
        edTexEmail = findViewById(R.id.login_email_input);
        edTexPassword = findViewById(R.id.login_password_input);
        texViewCreateAcc = findViewById(R.id.login_create_account);
        btnLogIn = findViewById(R.id.login_btn);
        btnLogIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                email = edTexEmail.getText().toString();
                password = edTexPassword.getText().toString();
                if (!email.isEmpty() & !password.isEmpty()){
                    user.setEmail(email);
                    user.setPassword(password);
                    mNetReg.queueMsg("login", user);
                    progressBar.setVisibility(View.VISIBLE);

                    /*if (user == null){
                        Toast.makeText(context, "Enter the correct data", LENGTH_LONG).show();
                        return;
                    */
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
