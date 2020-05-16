package com.example.fetchpost.Activities;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.fetchpost.Classes.Member;
import com.example.fetchpost.R;
import com.example.fetchpost.Helpers.savedInfo;

public class MemberViewActivity extends AppCompatActivity {
    public static final String MEMBER_USERNAME = "com.example.fetchpost.MEMBER_USERNAME";
    private static final String TAG = "MemberViewActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_member_view);

        TextView tvEmail = (TextView) findViewById(R.id.email);
        TextView tvName = (TextView) findViewById(R.id.name);
        TextView tvPhone = (TextView) findViewById(R.id.PhoneNumber);
        TextView tvUsername = (TextView) findViewById(R.id.username);
        TextView tvLoggedUsername = (TextView) findViewById(R.id.loggedUsername);


        Context mContext = this;
        String username = getIntent().getStringExtra(MEMBER_USERNAME);
        Member member = new Member(username);
        String name = member.getFName()+" "+member.getLName();

        Log.d(TAG, "onCreate: " + member.getUsername());

        tvLoggedUsername.setText(savedInfo.loggedUsername);

        tvUsername.setText(member.getUsername().toUpperCase());
        tvName.setText(member.getFName());
        tvPhone.setText(member.getPhone());
        tvEmail.setText(member.getEmail());


    }
}
