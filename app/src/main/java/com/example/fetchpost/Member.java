package com.example.fetchpost;


import android.os.AsyncTask;
import android.os.Build;

import androidx.annotation.RequiresApi;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

public class Member {
    private String member_id, username, email, phone, fName, lName;


    Member(){}

    Member(String username) {
        this.username = username;
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

    void setData(JSONObject json) throws JSONException {
        setMember_id(json.getString("member_id"));
        setUsername(json.getString("username"));
        setLName(json.getString("lname"));
        setFName(json.getString("fname"));
        setEmail(json.getString("email"));
        setPhone(json.getString("phone"));

    }

    void setLoggedData() {
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
