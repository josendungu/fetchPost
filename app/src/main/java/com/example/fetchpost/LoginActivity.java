package com.example.fetchpost;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.google.android.material.snackbar.Snackbar;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

public class LoginActivity extends AppCompatActivity {

    private EditText EtUsername, EtPass;
    private TextView  tvforgot;
    private TextView register;
    private Button BtSubmit;
    protected String TxtUsername, TxtPassword;
    private Context mContext;
    private String responce;
    private static final String TAG = "LoginActivity";
    public static final  String MESSAGE = "com.example.fetchpost.MESSAGE";
    public static final  String USERNAME_PASSED = "com.example.fetchpost.USERNAME";
    ConstraintLayout constraintLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        displayMessages();
        initializeUsername();

        mContext = this;

        EtUsername = (EditText)findViewById(R.id.username);
        EtPass = (EditText)findViewById(R.id.password);
        BtSubmit = (Button)findViewById(R.id.submit);
        register = (TextView)findViewById(R.id.link_reg);
        tvforgot = (TextView)findViewById(R.id.forgot);
        constraintLayout = findViewById(R.id.view);


        BtSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TxtUsername = EtUsername.getText().toString().trim();
                TxtPassword = EtPass.getText().toString().trim();

                new LoginTask().execute();



            }
        });

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(mContext,RegisterActivity.class);
                startActivity(intent);
            }
        });

        tvforgot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(mContext, ForgotActivity.class);
                startActivity(intent);
            }
        });
    }

    private void initializeUsername() {
        String username = getIntent().getStringExtra(USERNAME_PASSED);
        if(username != null){
            Log.d(TAG, "initializeUsername: setting username");
            EtUsername.setText(username);
        }
    }

    private void displayMessages() {
        String message = getIntent().getStringExtra(MESSAGE);
        Log.d(TAG, "displayMessages: entered"+message);
        if(message != null){
            Log.d(TAG, "displayMessages: "+message);
            Snackbar.make(constraintLayout, message, Snackbar.LENGTH_LONG)
                    .show();
        }


    }

    public class LoginTask extends AsyncTask<String,String,String> {

        @RequiresApi(api = Build.VERSION_CODES.KITKAT)
        @Override
        protected String doInBackground(String... strings) {
            try {
                String data = URLEncoder.encode("username","UTF-8")+"="+URLEncoder.encode(TxtUsername,"UTF-8")
                        +"&&"+URLEncoder.encode("pass","UTF-8")+"="+URLEncoder.encode(TxtPassword,"UTF-8");
                DB_con db = new DB_con(mContext,savedInfo.baseUrl+savedInfo.login, data);
                responce = db.getConnection();
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }


            return responce;
        }

        @Override
        protected void onPostExecute(String s) {

            if (responce.equals(savedInfo.memDontExist)){
                Log.d(TAG, "onPostExecute: in");
                Snackbar.make(constraintLayout,"Member doesn't exist. Please check and try again",Snackbar.LENGTH_LONG)
                        .show();
            } else if(responce.equals(savedInfo.success)){
                Intent intent = new Intent(mContext, DashboardActivity.class);
                intent.putExtra(DashboardActivity.USERNAME_REF, TxtUsername);
                startActivity(intent);
            } else if(responce.equals(savedInfo.passDontMatch)){
                Snackbar.make(constraintLayout,"Incorrect password. Please check and try again",Snackbar.LENGTH_LONG)
                        .show();
            }
            Log.d(TAG, "onPostExecute: Changing" + responce);
            super.onPostExecute(s);
        }
    }


}
