package ru.kuznecov.ivan.rent.service;

import android.os.AsyncTask;
import android.util.Log;

import java.io.IOException;
import java.util.concurrent.ExecutionException;

import ru.kuznecov.ivan.rent.model.User;
import ru.kuznecov.ivan.rent.network.ApiServer;
import ru.kuznecov.ivan.rent.network.Connection;
import ru.kuznecov.ivan.rent.network.GsonConvert;

public class NetworkServiceImpl implements NetworkService {
    private BackGround backGround;
    private User user;
    private String email;
    private String password;



    @Override
    public User signIn(String email,String password) {
        this.email = email;
        this.password = password;
        backGround = new BackGround();
        backGround.execute();
        try {
            user = backGround.get();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return user;
    }
    private class BackGround extends AsyncTask<Void, Void, User>{
        @Override
        protected User doInBackground(Void... voids) {
            try {
                user = new GsonConvert<User>().deserialization(new Connection()
                        .getRequest(ApiServer
                                .getLogIn(email, password)), User.class);
            } catch (IOException e) {
                e.printStackTrace();
            }
            return user;
        }
    }

}
