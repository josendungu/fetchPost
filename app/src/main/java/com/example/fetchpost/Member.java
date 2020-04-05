package com.example.fetchpost;


import android.content.Context;
import android.os.AsyncTask;
import android.os.Build;
import android.util.Log;

import androidx.annotation.RequiresApi;

import org.json.JSONException;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

public class Member {
    private String member_id, username, email, phone, fname, lname;
    private Context mContext;
    String responce;

    private static final String TAG = "Member";

    Member(String username, Context context) throws JSONException {
        this.username = username;
        this.mContext = context;
        new MemberFetch().execute();
        //JSONObject obj = new JSONObject(responce);
        //Log.d(TAG, "Member: data: "+ obj.getJSONObject("fname"));

    }


    public void setMember_id(String member_id) {
        this.member_id = member_id;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public void setFname(String fname) {
        this.fname = fname;
    }

    public void setLname(String lname) {
        this.lname = lname;
    }

    public String getMember_id() {
        return member_id;
    }

    public String getUsername() {
        return username;
    }

    public String getEmail() {
        return email;
    }

    public String getPhone() {
        return phone;
    }

    public String getFname() {
        return fname;
    }

    public String getLname() {
        return lname;
    }

    public void setData() {

    }


    public class MemberFetch extends AsyncTask<String, String, String>{

        @RequiresApi(api = Build.VERSION_CODES.KITKAT)
        @Override
        protected String doInBackground(String... strings) {
            try {
                String data = URLEncoder.encode("username","UTF-8")+"="+URLEncoder.encode(username,"UTF-8");
                DB_con db = new DB_con(mContext, savedInfo.memberFetch, data);
                responce = db.getConnection();
                Log.d(TAG, "doInBackground: Responce : " +responce);
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
            return null;
        }
    }

    public class MemberFetchAll extends AsyncTask<String, String, String>{
        @Override
        protected String doInBackground(String... strings) {
            DB_con db = new DB_con(mContext, savedInfo.memberFetchAll);
            return null;
        }
    }










}
