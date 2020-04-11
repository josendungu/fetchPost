package com.example.fetchpost;


import android.content.Context;
import android.os.AsyncTask;
import android.os.Build;
import android.util.Log;

import androidx.annotation.RequiresApi;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

public class Member {
    private String member_id, username, email, phone, fname, lname;
    private Context mContext;
    String responce;

    private static final String TAG = "Member";

    Member(){}

    Member(String username, Context context) {
        this.username = username;
        this.mContext = context;
        new MemberFetch().execute();

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

    void setData(JSONObject json) throws JSONException {
        setMember_id(json.getString("member_id"));
        setUsername(json.getString("username"));
        setLname(json.getString("lname"));
        setFname(json.getString("fname"));
        setEmail(json.getString("email"));
        setPhone(json.getString("phone"));

    }


    public class MemberFetch extends AsyncTask<String, String, String>{

        @RequiresApi(api = Build.VERSION_CODES.KITKAT)
        @Override
        protected String doInBackground(String... strings) {
            try {
                String data = URLEncoder.encode("username","UTF-8")+"="+URLEncoder.encode(username,"UTF-8");
                DB_con db = new DB_con(mContext, savedInfo.memberFetch, data);
                responce = db.getConnection();
                JSONObject json_data = new JSONObject(responce);
                setData(json_data);
                Log.d(TAG, "doInBackground: json : " +json_data.getString("lname"));
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return null;
        }
    }












}
