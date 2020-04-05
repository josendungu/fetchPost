package com.example.fetchpost;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import org.json.JSONException;

public class DashboardActivity extends AppCompatActivity {
    public static final  String USERNAME_REF = "com.example.fetchpost.USERNAME_REF";
    private static final String TAG = "DashboardActivity";

    private Member member;
    private Context mContext;
    private RecyclerView mRecyclerItems;
    private LinearLayoutManager mMemberLayoutManager;
    private MemberRecyclerAdapter mMemberRecyclerAdapter;
    private TextView tv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        mContext =  this;

        String username = getIntent().getStringExtra(USERNAME_REF);
        Log.d(TAG, "onCreate: " + username);
        try {
            member = new Member(username, mContext);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        mRecyclerItems = (RecyclerView) findViewById(R.id.member_list);
        mMemberLayoutManager = new LinearLayoutManager(this);
        mMemberRecyclerAdapter = new MemberRecyclerAdapter(this,null);


    }
}
