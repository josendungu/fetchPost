package com.example.fetchpost.Classes;


import android.os.AsyncTask;
import android.os.Build;
import android.util.Log;

import androidx.annotation.RequiresApi;

import com.example.fetchpost.Helpers.DB_con;
import com.example.fetchpost.Helpers.savedInfo;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

public class Member {
    private String member_id, username, email, phone, fName, lName;
    private static final String TAG = "Member";


    public Member(){}

    public Member(String username) {
        this.username = username;
        new MemberFetch().execute();
        Log.d(TAG, "Member: initialized" + username );

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

    public void setFName(String fName) {
        this.fName = fName;
    }

    public void setLName(String lName) {
        this.lName = lName;
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

    public String getFName() {
        return fName;
    }

    public String getLName() {
        return lName;
    }

    public void setData(JSONObject json) throws JSONException {
        Log.d(TAG, "setData: Logging" + json.getString("lname"));
        setMember_id(json.getString("member_id"));
        setUsername(json.getString("username"));
        setLName(json.getString("lname"));
        setFName(json.getString("fname"));
        setEmail(json.getString("email"));
        setPhone(json.getString("phone"));

        Log.d(TAG, "setData: " + getLName());

    }

    public void setLoggedData() {
        savedInfo.loggedUsername = this.username;
        savedInfo.loggedFirstName = this.fName;
        savedInfo.loggedLastName = this.lName;
        savedInfo.loggedEmail = this.email;
        savedInfo.loggedPhoneNumber = this.phone;

    }


    public class MemberFetch extends AsyncTask<String, String, String>{

        @RequiresApi(api = Build.VERSION_CODES.KITKAT)
        @Override
        protected String doInBackground(String... strings) {
            try {
                String data = URLEncoder.encode("username","UTF-8")+"="+URLEncoder.encode(username,"UTF-8");
                DB_con db = new DB_con(savedInfo.memberFetch, data);
                String response = db.getConnection();

                Log.d(TAG, "doInBackground: " + username + response);
                JSONObject json_data = new JSONObject(response);
                setData(json_data);

            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return null;
        }
    }












}
