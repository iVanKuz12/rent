package ru.kuznecov.ivan.rent.service;

import android.os.Handler;
import android.os.HandlerThread;
import android.os.Message;


import java.io.IOException;
import java.net.MalformedURLException;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import ru.kuznecov.ivan.rent.model.SingletonData;
import ru.kuznecov.ivan.rent.pojo.Category;
import ru.kuznecov.ivan.rent.pojo.City;
import ru.kuznecov.ivan.rent.pojo.District;
import ru.kuznecov.ivan.rent.pojo.SubCategory;
import ru.kuznecov.ivan.rent.pojo.Thing;
import ru.kuznecov.ivan.rent.pojo.User;
import ru.kuznecov.ivan.rent.network.ApiServer;
import ru.kuznecov.ivan.rent.network.ConnectMain;
import ru.kuznecov.ivan.rent.network.GsonConvert;

public class Network<T, V> extends HandlerThread {

    private static Network sNetwork;
    private static final String TAG = "Network";
    private static final int MESSAGE_DOWNLOAD = 0;
    private static final int MESSAGE_DOWNLOAD_CITY = 1;
    private static final int MESSAGE_DOWNLOAD_DISTRICT = 2;
    private static final int MESSAGE_DOWNLOAD_CATEGORY = 3;
    private static final int MESSAGE_DOWNLOAD_SUBCATEGORY = 4;
    private static final int MESSAGE_ADD_THING = 5;
    private static final int MESSAGE_GET_ALL_THING = 6;
    private static final int MESSAGE_SINGLETON = 7;
    private static final int MESSAGE_GET_MY_THING = 8;


    private boolean mHasQuit = false;
    private Handler mRequestHandler;
    private Handler mResponseHandler;
    private RegisterListener mRegisterListener;
    private LoginListener mLoginListener;
    private EditProfileListener mEditProfileListener;
    private AddThingListener mAddThingListener;
    private HomeListener mHomeListener;
    private ProfileListener mProfileListener;
    private ConcurrentMap<T, V> mRequestMap = new ConcurrentHashMap<>();
    private SingletonData singletonData;

    public static Network getInstance(){
        if (sNetwork == null){
            sNetwork = new Network<String, User>("BackGround");
        }
        return sNetwork;
    }
    private Network(String name) {
        super(name);
    }

    public void setMainHandler(Handler responseHandler){
        this.mResponseHandler = responseHandler;
    }

    public interface HomeListener{
        void getAllThing(List<Thing> list);
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

    public interface AddThingListener {
        void loadCity(List<City> cities);
        void loadCategory(List<Category> categories);
        void loadDistrict(List<District> districts);
        void loadSubCategory(List<SubCategory> subCategories);
        void getIdNewAddThing(long newId);
        void getResultFullAddThing(String res);
    }

    public interface ProfileListener{
        void loadMyThing(List<Thing> things);
    }

    public void setProfileListener(ProfileListener profileListener){
        this.mProfileListener = profileListener;
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

    public void setAddThingCityListener(AddThingListener addThingListener){
        this.mAddThingListener = addThingListener;
    }
    public void setHomeListener(HomeListener homeListener){
        this.mHomeListener = homeListener;
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
                if (msg.what == MESSAGE_DOWNLOAD_CITY) {
                    T obj = (T) msg.obj;
                    handlerRequestCity(obj);

                }
                if (msg.what == MESSAGE_DOWNLOAD_DISTRICT) {
                    Long obj = (Long) msg.obj;
                    handlerRequestDistrict(obj);
                }
                if (msg.what == MESSAGE_DOWNLOAD_CATEGORY) {
                    T obj = (T) msg.obj;
                    handlerRequestCategory(obj);
                }
                if (msg.what == MESSAGE_DOWNLOAD_SUBCATEGORY) {
                    Long obj = (Long) msg.obj;
                    handlerRequestSubCategory(obj);
                }
                if (msg.what == MESSAGE_ADD_THING){
                    Thing obj = (Thing) msg.obj;
                    handlerRequestAddThing(obj);
                }
                if (msg.what == MESSAGE_GET_ALL_THING){
                    handlerRequestGetAllThing();
                }
                if (msg.what == MESSAGE_SINGLETON){
                    handlerRequestSingleton();
                }
                if (msg.what == MESSAGE_GET_MY_THING){
                    Long id = (Long) msg.obj;
                    handlerRequestGetMyThing(id);
                }



            }
        };
    }

    private void handlerRequestGetMyThing(Long id) {

        try {
            List<Thing> things = new GsonConvert<Thing>().getList(new ConnectMain()
                    .getRequest(ApiServer.getAllThingUserId(id)), Thing.class);
            mResponseHandler.post(new Runnable() {
                @Override
                public void run() {
                    mProfileListener.loadMyThing(things);
                }
            });
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
    }

    private void handlerRequestSingleton() {
        singletonData = SingletonData.getInstance();
        try {
            List<City> cities = new GsonConvert<City>().getList(new ConnectMain()
                    .getRequest(ApiServer.getAllCity()), City.class);
            List<District> districts = new GsonConvert<District>().getList(new ConnectMain()
                    .getRequest(ApiServer.getAllDistrict()), District.class);
            List<Category> categories = new GsonConvert<Category>().getList(new ConnectMain()
                    .getRequest(ApiServer.getAllCateory()), Category.class);
            List<SubCategory> subCategories = new GsonConvert<SubCategory>().getList(new ConnectMain()
                    .getRequest(ApiServer.getAllSubCategory()), SubCategory.class);
            singletonData.setCities(cities);
            singletonData.setDistricts(districts);
            singletonData.setCategories(categories);
            singletonData.setSubCategories(subCategories);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
    }

    private void handlerRequestGetAllThing() {
        try {
            List<Thing> things = new GsonConvert<Thing>().getList(new ConnectMain().getRequest(ApiServer.getAllThings()), Thing.class);
            mResponseHandler.post(new Runnable() {
                @Override
                public void run() {
                 mHomeListener.getAllThing(things);
                }
            });
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
    }

    private void handlerRequestAddThing(Thing obj) {
        if (obj.getPhoto() == null) {
            try {

                String json = new GsonConvert<Thing>().serialization(obj);
                long id = new GsonConvert<String>().deserialization(new ConnectMain().postRequest(ApiServer.addThing()
                        , json), Long.class);
                mResponseHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        mAddThingListener.getIdNewAddThing(id);
                    }
                });
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else{
            try {
                String json = new GsonConvert<Thing>().serialization(obj);
                String result = new GsonConvert<String>().deserialization(new ConnectMain()
                        .postRequest(ApiServer.addPhotoThing(), json), String.class);
                mResponseHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        mAddThingListener.getResultFullAddThing(result);
                    }
                });
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }

    private void handlerRequestCategory(T obj) {
        if ("loadCategory".equals(obj)){
            try {
                List<Category> categories = new GsonConvert<Category>().getList(new ConnectMain().getRequest(ApiServer.getAllCateory()), Category.class);
                mResponseHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        mAddThingListener.loadCategory(categories);
                    }
                });
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }
        }
    }

    private void handlerRequestCity(T obj){
        if ("loadCity".equals(obj)) {
            try {
                List<City> cityList = new GsonConvert<City>().getList(new ConnectMain().getRequest(ApiServer.getAllCity()), City.class);
                mResponseHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        mAddThingListener.loadCity(cityList);
                    }
                });
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }
        }
    }

    private void handlerRequestDistrict(Long obj){
                try {
                    String res = new ConnectMain().getRequest(ApiServer.getAllDistrictParentId(obj));
                    List<District> districtList = new GsonConvert<District>().getList(res, District.class);
                    mResponseHandler.post(new Runnable() {
                        @Override
                        public void run() {
                            mAddThingListener.loadDistrict(districtList);
                        }
                    });
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                }

        }
    private void handlerRequestSubCategory(Long obj) {
        try {
            List<SubCategory> subCategoryList = new GsonConvert<SubCategory>().getList(
                    new ConnectMain().getRequest(ApiServer.getAllSubCategoryParentId(obj))
                    , SubCategory.class);
            mResponseHandler.post(new Runnable() {
                @Override
                public void run() {
                    mAddThingListener.loadSubCategory(subCategoryList);
                }
            });

        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
    }

    private void handlerRequest(T obj) {
        User user = (User) mRequestMap.get(obj);
        if ("email".equals(obj)) {
            String email = user.getEmail();
            if (email == null)
                return;

            try {
                String response = new GsonConvert<String>().deserialization(new ConnectMain()
                        .getRequest(ApiServer.getEmail(email)), String.class);
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


    public void queueMsgCity(T obj1) {
        mRequestHandler.obtainMessage(MESSAGE_DOWNLOAD_CITY, obj1).sendToTarget();
    }
    public void queueMsgCategory(T obj1) {
        mRequestHandler.obtainMessage(MESSAGE_DOWNLOAD_CATEGORY, obj1).sendToTarget();
    }
    public void queueMsgDistrict(Long obj1) {
        mRequestHandler.obtainMessage(MESSAGE_DOWNLOAD_DISTRICT, obj1).sendToTarget();
    }
    public void queueMsgSubCategory(Long obj1) {
        mRequestHandler.obtainMessage(MESSAGE_DOWNLOAD_SUBCATEGORY, obj1).sendToTarget();
    }
    public void queueMsgAddThing(Thing thing) {
        mRequestHandler.obtainMessage(MESSAGE_ADD_THING, thing).sendToTarget();
    }

    public void queueMsgGetAllThing(){
        mRequestHandler.obtainMessage(MESSAGE_GET_ALL_THING).sendToTarget();
    }

    public void queueMsgSingleton(){
        mRequestHandler.obtainMessage(MESSAGE_SINGLETON).sendToTarget();
    }

    public void queueMsgGetThingUserId(Long id){
        mRequestHandler.obtainMessage(MESSAGE_GET_MY_THING, id).sendToTarget();
    }



    public void clearQueue() {
        mResponseHandler.removeMessages(MESSAGE_DOWNLOAD);
        mRequestMap.clear();
    }
}
