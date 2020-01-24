package com.example.fetchpost;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

public class DashboardActivity extends AppCompatActivity {
    public static final  String USERNAME_REF = "com.example.fetchpost.USERNAME_REF";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
    }
}
