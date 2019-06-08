package ru.kuznecov.ivan.rent.presenter;

import android.app.Activity;
import android.net.Uri;

import ru.kuznecov.ivan.rent.activity.AddThingView;
import ru.kuznecov.ivan.rent.utils.MakePhoto;

public class AddThingPresenter {

    private AddThingView view;

    public AddThingPresenter() {
    }

    void attachView(AddThingView addThingView){
        this.view = addThingView;
    }
    void detach(){
        view = null;
    }

}
