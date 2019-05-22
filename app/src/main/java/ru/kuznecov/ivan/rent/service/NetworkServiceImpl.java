package ru.kuznecov.ivan.rent.service;

import android.content.Context;
import android.os.AsyncTask;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.concurrent.ExecutionException;

import okhttp3.Call;
import ru.kuznecov.ivan.rent.model.User;
import ru.kuznecov.ivan.rent.network.ApiServer;
import ru.kuznecov.ivan.rent.network.Connection;
import ru.kuznecov.ivan.rent.network.GsonConvert;

public class NetworkServiceImpl implements Connection.ConnectionListener {

    private static final String TAG = "NetworkServiceImpl";

    private Listener listener;
    private Connection connect;

    private User user;
    private String email;
    private String password;

    public interface Listener{
        void getResult(String response);
    }
    @Override
    public void onFailure(Call call, IOException e) {

    }
    @Override
    public void onResponse(String response) {
        listener.getResult(response);
    }

    public NetworkServiceImpl(Listener listener) {
        this.listener = listener;
        connect = new Connection(this);
    }

    public void create() throws MalformedURLException {
        connect.getRequestBackGround(ApiServer.getAllUser());
    }

    public void load() throws MalformedURLException {
        connect.getRequestBackGround(ApiServer.getUser(1));
    }

    public void getEmail(String email) throws MalformedURLException {
        connect.getRequestBackGround(ApiServer.getEmail(email));
    }


}
