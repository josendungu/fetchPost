package com.example.fetchpost;


import android.content.Context;
import android.os.Build;

import androidx.annotation.RequiresApi;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class DB_con{

    private static final String TAG = "DB_con";
    private String Url;
    private String data;
    private String result = "";
    Context mContext;



    public DB_con(Context context, String url, String data) {
        this.data = data;
        this.Url = url;
        this.mContext = context;
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public String getConnection(){
        try {
            URL url = new URL(Url);
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setReadTimeout(10000);
            con.setConnectTimeout(15000);
            con.setRequestMethod("POST");
            con.setDoInput(true);
            con.setDoOutput(true);



            try (OutputStream os = con.getOutputStream()) {
                BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(os, "UTF-8"));
                writer.write(data);
                writer.flush();
                writer.close();
                os.close();

                InputStream ips = con.getInputStream();
                BufferedReader reader = new BufferedReader(new InputStreamReader(ips,"ISO-8859-1"));
                String line = "";
                while ((line = reader.readLine()) != null){
                    result += line;
                }
                reader.close();
                ips.close();
                con.disconnect();
                return result;
            }

        } catch (MalformedURLException e) {
            e.printStackTrace();
            //err = e.toString();
        } catch (IOException e) {
            e.printStackTrace();
            //err = e.toString();
        }
        return result;
    }

}
