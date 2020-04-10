package com.example.fetchpost;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

public class DashboardActivity extends AppCompatActivity {
    public static final  String USERNAME_REF = "com.example.fetchpost.USERNAME_REF";
    private static final String TAG = "DashboardActivity";

    private Context mContext;
    private List<Member> memberList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        mContext = this;
        TextView tvUsername = (TextView) findViewById(R.id.username);

        String username = getIntent().getStringExtra(USERNAME_REF);
        Member member = new Member(username, mContext);

        tvUsername.setText(member.getUsername().toUpperCase());
        new MemberFetchAll().execute();

        RecyclerView mRecyclerItems = (RecyclerView) findViewById(R.id.member_list);
        LinearLayoutManager mMemberLayoutManager = new LinearLayoutManager(this);
        MemberRecyclerAdapter mMemberRecyclerAdapter = new MemberRecyclerAdapter(this, memberList);
        mRecyclerItems.setLayoutManager(mMemberLayoutManager);
        mRecyclerItems.setAdapter(mMemberRecyclerAdapter);
        mMemberRecyclerAdapter.notifyDataSetChanged();



    }

    public class MemberFetchAll extends AsyncTask<String,String,String>{

        @Override
        protected void onPreExecute() {

            //TODO: add progress bar
        }

        @RequiresApi(api = Build.VERSION_CODES.KITKAT)
        @Override
        protected String doInBackground(String... strings) {
            try {
                String token = "token";
                String data = URLEncoder.encode("username","UTF-8")+"="+URLEncoder.encode(token,"UTF-8");
                DB_con db = new DB_con(mContext, savedInfo.memberFetchAll, data);
                String responce = db.getConnection();
                JSONArray jsonArray = new JSONArray(responce);
                int i = 0;
                while(i < jsonArray.length()){
                    JSONObject myObj = jsonArray.getJSONObject(i);
                    String member_id = myObj.getString("member_id");
                    String username = myObj.getString("username");
                    String fName = myObj.getString("fname");
                    String lName = myObj.getString("lname");

                    Member member = new Member(username, fName,lName, member_id);
                    memberList.add(member);
                    Log.d(TAG, "doInBackground: individual "+ myObj);
                    i++;
                }
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return null;
        }
    }
}
