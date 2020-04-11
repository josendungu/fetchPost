package com.example.fetchpost;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

public class MemberViewActivity extends AppCompatActivity {
    public static final String MEMBER_USERNAME = "com.example.fetchpost.MEMBER_USERNAME";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_member_view);


        String logged_username = getIntent().getStringExtra(MEMBER_USERNAME);
    }
}
