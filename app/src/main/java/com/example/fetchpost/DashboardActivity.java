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
    private RecyclerView mRecyclerItems;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        mContext = this;
        TextView tvUsername = (TextView) findViewById(R.id.username);
        mRecyclerItems = (RecyclerView) findViewById(R.id.member_list);

        String logged_username = getIntent().getStringExtra(USERNAME_REF);
        Member logged_member = new Member(logged_username, mContext);
        String welcome_text = "Welcome " + logged_member.getUsername().toUpperCase();
        tvUsername.setText(welcome_text);
        new MemberFetchAll().execute();



    }

    public void initializeDisplay(List<Member> memberList){
        LinearLayoutManager mMemberLayoutManager = new LinearLayoutManager(this);
        MemberRecyclerAdapter mMemberRecyclerAdapter = new MemberRecyclerAdapter(mContext, memberList);
        mRecyclerItems.setLayoutManager(mMemberLayoutManager);
        mRecyclerItems.setAdapter(mMemberRecyclerAdapter);
        mMemberRecyclerAdapter.notifyDataSetChanged();

    }

    public class MemberFetchAll extends AsyncTask<String,String,String>{


        private String mResponse;
        private List<Member> memberList = new ArrayList<>();
        private JSONArray mJsonArray;

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
                DB_con db = new DB_con(savedInfo.memberFetchAll, data);
                mResponse = db.getConnection();


            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            Log.d(TAG, "onPostExecute: response: "+ mResponse);
            try {
                mJsonArray = new JSONArray(mResponse);
                int i = 0;
                while(i < mJsonArray.length()){
                    JSONObject myObj = mJsonArray.getJSONObject(i);

                    Member member = new Member();
                    member.setData(myObj);
                    memberList.add(member);
                    Log.d(TAG, "onPostExecute: size" + memberList.size());
                    i++;
                }
                initializeDisplay(memberList);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }
}
