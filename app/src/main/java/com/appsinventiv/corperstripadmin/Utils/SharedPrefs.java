package com.appsinventiv.corperstripadmin.Utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by AliAh on 29/03/2018.
 */

public class SharedPrefs {
    Context context;

    private SharedPrefs() {

    }

    public static void setName(String username) {
        preferenceSetter("name", username);
    }

    public static String getName() {
        return preferenceGetter("name");
    }


    public static void setPicUrl(String username) {
        preferenceSetter("picUrl", username);
    }

    public static String getPicUrl() {
        return preferenceGetter("picUrl");
    }


    public static String getUsername() {
        return preferenceGetter("Username");
    }

    public static void setUsername(String username) {
        preferenceSetter("Username", username);
    }


    public static void setIsLoggedIn(String value) {

        preferenceSetter("isLoggedIn", value);
    }

    public static String getIsLoggedIn() {
        return preferenceGetter("isLoggedIn");
    }

    public static void setUserCity(String value) {

        preferenceSetter("city", value);
    }

    public static String getUserCity() {
        return preferenceGetter("city");
    }

    public static void setFcmKey(String fcmKey) {
        preferenceSetter("fcmKey", fcmKey);
    }

    public static String getFcmKey() {
        return preferenceGetter("fcmKey");
    }


    public static void preferenceSetter(String key, String value) {
        SharedPreferences pref = ApplicationClass.getInstance().getApplicationContext().getSharedPreferences("user", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();
        editor.putString(key, value);
        editor.apply();
    }

    public static String preferenceGetter(String key) {
        SharedPreferences pref;
        String value = "";
        pref = ApplicationClass.getInstance().getApplicationContext().getSharedPreferences("user", Context.MODE_PRIVATE);
        value = pref.getString(key, "");
        return value;
    }

    public static void saveArray(ArrayList<String> list) {
        SharedPreferences pref = ApplicationClass.getInstance().getApplicationContext().getSharedPreferences("user", Context.MODE_PRIVATE);

        SharedPreferences.Editor mEdit1 = pref.edit();
    /* sKey is an array */
        mEdit1.putInt("Status_size", list.size());

        for (int i = 0; i < list.size(); i++) {
            mEdit1.remove("Status_" + i);
            mEdit1.putString("Status_" + i, list.get(i));
        }
        mEdit1.commit();

    }

    public static ArrayList<String> loadArray() {
        ArrayList<String> sKey = new ArrayList<>();
        SharedPreferences mSharedPreference1 = ApplicationClass.getInstance().getApplicationContext().getSharedPreferences("user", Context.MODE_PRIVATE);
        sKey.clear();
        int size = mSharedPreference1.getInt("Status_size", 0);

        for (int i = 0; i < size; i++) {
            sKey.add(mSharedPreference1.getString("Status_" + i, null));
        }
        return sKey;

    }

    public static void clearApp() {
        SharedPreferences pref = ApplicationClass.getInstance().getApplicationContext().getSharedPreferences("user", Context.MODE_PRIVATE);
        pref.edit().clear().commit();


    }


}
