package com.example.fetchpost.Fragments;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.fetchpost.Adapters.MemberRecyclerAdapter;
import com.example.fetchpost.Classes.Member;
import com.example.fetchpost.Helpers.DB_con;
import com.example.fetchpost.Helpers.savedInfo;
import com.example.fetchpost.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

public class MembersFragment extends Fragment {

    private static final  String USERNAME_REF = "com.example.fetchpost.USERNAME_REF";
    private RecyclerView mRecyclerView;
    private Context mContext;

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view  = inflater.inflate(R.layout.fragment_members, container,false);
        mRecyclerView = (RecyclerView) view.findViewById(R.id.member_list);
        new MemberFetchAll().execute();
        return view;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        mContext =  context;
    }

    private void initializeDisplay(List<Member> memberList){
        LinearLayoutManager mMemberLayoutManager = new LinearLayoutManager(mContext);
        MemberRecyclerAdapter mMemberRecyclerAdapter = new MemberRecyclerAdapter(mContext, memberList);
        mRecyclerView.setLayoutManager(mMemberLayoutManager);
        mRecyclerView.setAdapter(mMemberRecyclerAdapter);
        mMemberRecyclerAdapter.notifyDataSetChanged();

    }


    public class MemberFetchAll extends AsyncTask<String,String,String> {

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
            try {
                mJsonArray = new JSONArray(mResponse);
                int i = 0;
                while(i < mJsonArray.length()){
                    JSONObject myObj = mJsonArray.getJSONObject(i);

                    Member member = new Member();
                    member.setData(myObj);
                    memberList.add(member);
                    i++;
                }
                initializeDisplay(memberList);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }
}
