package com.example.fetchpost.Activities;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.example.fetchpost.Helpers.DB_con;
import com.example.fetchpost.R;
import com.example.fetchpost.Helpers.savedInfo;
import com.google.android.material.snackbar.Snackbar;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

public class ForgotActivity extends AppCompatActivity {

    EditText etUsername, etPass, etPassConf;
    Button btnSubmit;
    String username, pass, passConf;
    View view;
    Context mContext;
    private static final String TAG = "ForgotActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot);

        mContext = this;
        etUsername = (EditText)findViewById(R.id.username);
        etPass = (EditText)findViewById(R.id.password1);
        etPassConf = (EditText)findViewById(R.id.password2);
        btnSubmit = (Button)findViewById(R.id.submit);
        view = (View)findViewById(R.id.view);

        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                username = etUsername.getText().toString().trim();
                pass = etPass.getText().toString().trim();
                passConf = etPassConf.getText().toString().trim();

                if(pass.equals(passConf)){
                    new ForgotTask().execute();
                } else {
                    Log.d(TAG, "onClick: Pass don't match");
                }
            }
        });


    }

    public class ForgotTask extends AsyncTask<String,String,String>{

        String result;

        @Override
        protected void onPostExecute(String s) {
            Log.d(TAG, "onPostExecute: "+result);
            if(result.equals(savedInfo.success)){
                Intent intent = new Intent(mContext, LoginActivity.class);
                intent.putExtra(LoginActivity.MESSAGE, "Successfully changed your password. Enter your details and login.");
                startActivity(intent);
            } else if(result.equals(savedInfo.memDontExist)) {
                Snackbar.make(view, "The username doesn't exist. Please check and try again!",Snackbar.LENGTH_LONG).show();
            } else {
                Snackbar.make(view, result,Snackbar.LENGTH_LONG).show();
            }

            super.onPostExecute(s);
        }

        @RequiresApi(api = Build.VERSION_CODES.KITKAT)
        @Override
        protected String doInBackground(String... strings) {
            try {
                String data = URLEncoder.encode("username","UTF-8")+"="+URLEncoder.encode(username,"UTF-8")
                        +"&&"+URLEncoder.encode("pass","UTF-8")+"="+URLEncoder.encode(pass,"UTF-8");
                DB_con db = new DB_con(savedInfo.forgot, data);
                result = db.getConnection();
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }


            return null;
        }
    }
}
