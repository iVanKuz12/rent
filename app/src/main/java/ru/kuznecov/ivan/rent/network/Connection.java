package ru.kuznecov.ivan.rent.network;

import android.content.Context;

import java.io.IOException;
import java.net.URL;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class Connection {

    public static final MediaType JSON = MediaType.get("application/json; charset=utf-8");

    private OkHttpClient client = new OkHttpClient();
    private ConnectionListener connectionListener;

    public interface ConnectionListener{
        void onFailure(Call call, IOException e);
        void onResponse(String resp);
    }

    public Connection(ConnectionListener connectionListener) {
        this.connectionListener = connectionListener;
    }

   /* public void setListener(ConnectionListener connectionListener){
        this.connectionListener = connectionListener;
    }*/

    public String postRequest(URL url, String json) throws IOException {
        RequestBody body = RequestBody.create(JSON, json);
        Request request = new Request.Builder()
                .url(url)
                .post(body)
                .build();
        try (Response response = client.newCall(request).execute()) {
            return response.body().string();
        }
    }

    public String getRequest(URL url){
        Request request = new Request.Builder()
                .url(url)
                .build();
        try (Response response = client.newCall(request).execute()) {
            return response.body().string();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void getRequestBackGround(URL url){
        Request request = new Request.Builder()
                .url(url)
                .build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (connectionListener != null)
                    connectionListener.onResponse(response.body().string());
            }
        });
    }
}
