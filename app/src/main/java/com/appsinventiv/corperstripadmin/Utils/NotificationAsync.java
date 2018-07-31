package com.appsinventiv.corperstripadmin.Utils;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by AliAh on 01/03/2018.
 */

public class NotificationAsync extends AsyncTask<String, String, String> {
    String output = "";

    public static String status = "";
    Context context;


    public final static String AUTH_KEY_FCM_LIVE = "AAAAoHMIczM:APA91bEY4Ina3W3q77AxtM6FTtGkKs9ha-B6GGpIeOAMd2XfkbFQM7XytGszjV6EqVj85Eplmk1wMgF466d_vkjEtYa_aMNUJsZ2G1bZ-HvQM-aDq5AmSTnwo2kVDOZntWPQuAhdtrHnLRK65SJDU_AEWnYPYx0LYA";
    public final static String API_URL_FCM = "https://fcm.googleapis.com/fcm/send";

    public NotificationAsync(Context context) {
        this.context = context;
    }

    @Override
    protected String doInBackground(String... params) {
        URL url;
        String param1 = params[0];
        String sendTo = params[1];
        String NotificationTitle = params[2];
        String NotificationMessage = params[3];
        String Type = params[4];
        String GroupId = params[5];
        String GroupName = params[6];




        try {
            url = new URL(API_URL_FCM);


            HttpURLConnection conn = (HttpURLConnection) url.openConnection();


            conn.setUseCaches(false);
            conn.setDoInput(true);
            conn.setDoOutput(true);
            conn.setConnectTimeout(60000);

            conn.setRequestMethod("POST");
            conn.setRequestProperty("Authorization", "key=" + AUTH_KEY_FCM_LIVE);
            conn.setRequestProperty("Content-Type", "application/json");


            JSONObject json = new JSONObject();

            JSONObject jsonObject = new JSONObject();
            jsonObject.put("Title", NotificationTitle);
            jsonObject.put("Message", NotificationMessage);
            jsonObject.put("Type", Type);
            jsonObject.put("GroupId", GroupId);
            jsonObject.put("GroupName", GroupName);



            json.put("data", jsonObject);
            json.put("to", sendTo);
            json.put("priority", "high");


            Log.d("json", "" + json);


            OutputStreamWriter wr = new OutputStreamWriter(
                    conn.getOutputStream());
            wr.write(json.toString());
            wr.flush();

            BufferedReader br = new BufferedReader(new InputStreamReader(
                    (conn.getInputStream())));


            while ((output = br.readLine()) != null) {
//            Toast.makeText(context, ""+output, Toast.LENGTH_SHORT).show();
                Log.d("output", output);

            }

        } catch (Exception e) {
//        Toast.makeText(context, "erroor "+e, Toast.LENGTH_SHORT).show();
            Log.d("exception", "" + e);

        }

        return null;
    }
}
