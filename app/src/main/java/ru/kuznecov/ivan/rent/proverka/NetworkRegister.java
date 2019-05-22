package ru.kuznecov.ivan.rent.proverka;

import android.os.Handler;
import android.os.HandlerThread;
import android.os.Message;
import android.util.Log;


import java.io.IOException;
import java.net.MalformedURLException;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import ru.kuznecov.ivan.rent.model.User;
import ru.kuznecov.ivan.rent.network.ApiServer;
import ru.kuznecov.ivan.rent.network.ConnectMain;
import ru.kuznecov.ivan.rent.network.GsonConvert;

public class NetworkRegister<T, V> extends HandlerThread {

    private static NetworkRegister sNetworkRegister;
    private static final String TAG = "NetworkRegister";
    private static final int MESSAGE_DOWNLOAD = 0;

    private boolean mHasQuit = false;
    private Handler mRequestHandler;
    private Handler mResponseHandler;
    private RegisterListener mRegisterListener;
    private LoginListener mLoginListener;
    private EditProfileListener mEditProfileListener;
    private ConcurrentMap<T, V> mRequestMap = new ConcurrentHashMap<>();

    public static NetworkRegister getInstance(){
        if (sNetworkRegister == null){
            sNetworkRegister = new NetworkRegister<String, User>("BackGround");
        }
        return sNetworkRegister;
    }
    private NetworkRegister(String name) {
        super(name);
    }

    public void setMainHandler(Handler responseHandler){
        this.mResponseHandler = responseHandler;
    }

    public interface RegisterListener {
        void validateEmail(String response);
        void validatePhone(String response);
        void registerUser(User user);
    }
    public interface LoginListener {
        void validateLogin(User user);
    }
    public interface EditProfileListener{
        void updateUser(User user);

    }

    public void setRegisterListener(RegisterListener registerListener) {
        this.mRegisterListener = registerListener;
    }

    public void setLoginListener(LoginListener loginListener) {
        this.mLoginListener = loginListener;
    }

    public void setEditProfileListener(EditProfileListener editProfileListener){
        this.mEditProfileListener = editProfileListener;
    }

    @Override
    protected void onLooperPrepared() {
        mRequestHandler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                if (msg.what == MESSAGE_DOWNLOAD) {
                    T obj = (T) msg.obj;
                    handlerRequest(obj);

                }
            }
        };
    }

    private void handlerRequest(T obj) {
        User user = (User) mRequestMap.get(obj);
        if ("email".equals(obj)) {
            String email = user.getEmail();
            if (email == null)
                return;

            try {
                String response = new GsonConvert<String>().deserialization(new ConnectMain().getRequest(ApiServer.getEmail(email)), String.class);
                mResponseHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        if (mRequestMap.get(obj) != user || mHasQuit) {
                            return;
                        }
                        mRequestMap.remove(obj);
                        mRegisterListener.validateEmail(response);
                    }
                });
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }
        }
        if ("phone".equals(obj)) {
            String phone = user.getPhone();
            if (phone == null)
                return;

            try {
                String response = new GsonConvert<String>().deserialization(new ConnectMain().getRequest(ApiServer.getPhone(phone)), String.class);
                mResponseHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        if (mRequestMap.get(obj) != user || mHasQuit) {
                            return;
                        }
                        mRequestMap.remove(obj);
                        mRegisterListener.validatePhone(response);
                    }
                });
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }
        }
        if ("register".equals(obj)) {
            String name = user.getName();
            String password = user.getPassword();
            if (name == null | password == null)
                return;
            try {
                User getUser = new GsonConvert<User>().deserialization(new ConnectMain()
                        .postRequest(ApiServer.setUser(), new GsonConvert<User>().serialization(user)), User.class);
                mResponseHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        if (mRequestMap.get(obj) != user || mHasQuit) {
                            return;
                        }
                        mRequestMap.remove(obj);
                        mRegisterListener.registerUser(getUser);
                    }
                });
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        if ("update".equals(obj)) {
            String name = user.getName();
            String email = user.getEmail();
            String phone = user.getPhone();
            user.setDate(null);
            if (name.isEmpty() | email.isEmpty() | phone.isEmpty())
                return;
            try {

                User getUser = new GsonConvert<User>().deserialization(new ConnectMain()
                        .postRequest(ApiServer.updateUser(), new GsonConvert<User>().serialization(user)), User.class);
                mResponseHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        if (mRequestMap.get(obj) != user || mHasQuit) {
                            return;
                        }
                        mRequestMap.remove(obj);
                        mEditProfileListener.updateUser(getUser);
                    }
                });
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        if ("login".equals(obj)) {
            String email = user.getEmail();
            String password = user.getPassword();
            if (email == null | password == null)
                return;
            try {
                User getLogUser = new GsonConvert<User>().deserialization(new ConnectMain()
                        .postRequest(ApiServer.getLogIn(), new GsonConvert<User>().serialization(user)), User.class);
                mResponseHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        if (mRequestMap.get(obj) != user || mHasQuit) {
                            return;
                        }
                        mRequestMap.remove(obj);
                        mLoginListener.validateLogin(getLogUser);
                    }
                });
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }

    @Override
    public boolean quit() {
        mHasQuit = true;
        return super.quit();
    }

    public void queueMsg(T obj1, V obj2) {
        if (obj2 == null) {
            mRequestMap.remove(obj1);
        } else {
            try {
                mRequestMap.put(obj1, obj2);
                mRequestHandler.obtainMessage(MESSAGE_DOWNLOAD, obj1)
                        .sendToTarget();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void clearQueue() {
        mResponseHandler.removeMessages(MESSAGE_DOWNLOAD);
        mRequestMap.clear();
    }
}
