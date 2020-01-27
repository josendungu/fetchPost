package com.example.fetchpost;


import android.content.Context;
import android.os.AsyncTask;
import android.os.Build;

import androidx.annotation.RequiresApi;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

public class Member {
    private String member_id, username, email;
    private Integer phone;
    private Context context;
    private String data, responce;


    public Member(String member_id) {
        this.member_id = member_id;
    }

    public Member(String username, String email, Integer phone, Context context) {
        this.username = username;
        this.email = email;
        this.context = context;
        this.phone = phone;
    }




    public boolean usernameExists() throws UnsupportedEncodingException {
        data = URLEncoder.encode("username","UTF-8")+"="+URLEncoder.encode(username,"UTF-8");
        return responce.equals(savedInfo.success);

    }

    public boolean emailExists() throws UnsupportedEncodingException {
        data = URLEncoder.encode("email","UTF-8")+"="+URLEncoder.encode(email,"UTF-8");
        return responce.equals(savedInfo.success);
    }

    public boolean phoneExists() throws UnsupportedEncodingException {
        data = URLEncoder.encode("phone","UTF-8")+"="+URLEncoder.encode(String.valueOf(phone),"UTF-8");
        return responce.equals(savedInfo.success);
    }

    public class validate extends AsyncTask<String,String,String>{

        @RequiresApi(api = Build.VERSION_CODES.KITKAT)
        @Override
        protected String doInBackground(String... strings) {

            DB_con db = new DB_con(context,savedInfo.baseUrl+savedInfo.validate, data);
            responce = db.getConnection();
            return null;
        }
    }
}
