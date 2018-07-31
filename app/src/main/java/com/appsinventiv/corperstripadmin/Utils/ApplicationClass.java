package com.appsinventiv.corperstripadmin.Utils;

import android.app.Application;
import android.os.StrictMode;

import com.google.firebase.database.FirebaseDatabase;

/**
 * Created by AliAh on 09/07/2018.
 */

public class ApplicationClass extends Application{

    private static  ApplicationClass instance;


    public static ApplicationClass getInstance(){
        return instance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        instance=this;
        StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
        StrictMode.setVmPolicy(builder.build());

    }
}
