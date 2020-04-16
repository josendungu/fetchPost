package com.example.fetchpost;

import android.content.Context;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class MemberViewActivity extends AppCompatActivity {
    public static final String MEMBER_USERNAME = "com.example.fetchpost.MEMBER_USERNAME";
    private static final String TAG = "MemberViewActivity";
    private TextView tvUsername ,tvName, tvLoggedUsername, tvEmail, tvPhone;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_member_view);

        tvEmail = (TextView)findViewById(R.id.email);
        tvName = (TextView)findViewById(R.id.name);
        tvPhone = (TextView)findViewById(R.id.PhoneNumber);
        tvUsername = (TextView)findViewById(R.id.username);
        tvLoggedUsername = (TextView)findViewById(R.id.loggedUsername);


        Context mContext = this;
        String username = getIntent().getStringExtra(MEMBER_USERNAME);
        Member member = new Member(username);

        String name = member.getFName()+" "+member.getLName();

        tvUsername.setText(member.getUsername().toUpperCase());
        tvPhone.setText(member.getPhone());
        tvName.setText(name);
        tvEmail.setText(member.getEmail());


    }
}
