package ru.kuznecov.ivan.rent.utils;

import android.os.Handler;
import android.os.HandlerThread;
import android.os.Message;
import android.util.Log;

import java.net.MalformedURLException;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import ru.kuznecov.ivan.rent.network.ApiServer;
import ru.kuznecov.ivan.rent.network.ConnectMain;
import ru.kuznecov.ivan.rent.network.GsonConvert;

public class Original<T> extends HandlerThread {

    private static final String TAG = "NetworkRegister";
    private static final int MESSAGE_DOWNLOAD = 0;

    private boolean mHasQuit = false;
    private Handler mRequestHandler;
    private Handler mResponseHandler;
    private RegisterListener<T> mRegisterListener;
    private ConcurrentMap<T, String> mRequestMap = new ConcurrentHashMap<>();

    public interface RegisterListener<T>{
        void validateEmail(String response);
        void validatePhone(String response);
        void registerUser(String response);
    }
    public void setRegisterListener(RegisterListener<T> registerListener){
        this.mRegisterListener = registerListener;
    }

    public Original(String name, Handler responseHandler) {
        super(name);
        this.mResponseHandler = responseHandler;
    }

    @Override
    protected void onLooperPrepared() {
        mRequestHandler = new Handler(){
            @Override
            public void handleMessage(Message msg) {
                if (msg.what == MESSAGE_DOWNLOAD){
                    T target =(T) msg.obj;
                    Log.i(TAG, "NetworkRegister: Get request URL " + mRequestMap.get(target));
                    handlerRequestEmail(target);
                }
            }
        };
    }

    private void handlerRequestEmail(T target) {
        String email = mRequestMap.get(target);
        if (email == null)
            return;

        try {
            String otvet = new GsonConvert<String>().deserialization(new ConnectMain().getRequest(ApiServer.getEmail(email)),String.class);
            mResponseHandler.post(new Runnable() {
                @Override
                public void run() {
                    if (mRequestMap.get(target) != email || mHasQuit){
                        return;
                    }
                    mRequestMap.remove(target);
                    mRegisterListener.validateEmail(otvet);
                }
            });
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

    }

    @Override
    public boolean quit() {
        mHasQuit = true;
        return super.quit();
    }

    public void queueDownloader(T target, String url){
        Log.i(TAG, "NetworkRegister:" + target + url);

        if (url == null) {
            mRequestMap.remove(target);
        } else {
            try {
                mRequestMap.put(target, url);
                if (mRequestHandler == null)
                    Log.i(TAG, "NetworkRegister: hundler == null");
                mRequestHandler.obtainMessage(MESSAGE_DOWNLOAD, target)
                        .sendToTarget();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
    public void clearQueue(){
        mResponseHandler.removeMessages(MESSAGE_DOWNLOAD);
        mRequestMap.clear();
    }
}
