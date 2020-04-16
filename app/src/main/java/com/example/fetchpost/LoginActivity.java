package com.example.fetchpost;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.google.android.material.snackbar.Snackbar;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

public class LoginActivity extends AppCompatActivity {

    private EditText EtUsername, EtPass;
    protected String TxtUsername, TxtPassword;
    private Context mContext;
    public boolean loginState;
    private String response;
    private Switch remember_login;
    private static final String TAG = "LoginActivity";
    public static final String MESSAGE = "com.example.fetchpost.MESSAGE";
    public static final String USERNAME_PASSED = "com.example.fetchpost.USERNAME";
    ConstraintLayout constraintLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mContext = this;
        EtUsername = (EditText) findViewById(R.id.username);
        EtPass = (EditText) findViewById(R.id.password);
        Button btSubmit = (Button) findViewById(R.id.submit);
        TextView register = (TextView) findViewById(R.id.link_reg);
        TextView tvForgot = (TextView) findViewById(R.id.forgot);
        constraintLayout = findViewById(R.id.view);
        remember_login = (Switch) findViewById(R.id.remember);


        checkLoginState();
        displayMessages();
        initializeUsername();


        btSubmit.setOnClickListener(new View.OnClickListener() {
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
                Intent intent = new Intent(mContext, RegisterActivity.class);
                startActivity(intent);
            }
        });

        tvForgot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(mContext, ForgotActivity.class);
                startActivity(intent);
            }
        });

    }

    private void checkLoginState() {
        SharedPreferences savedLoginData = mContext.getSharedPreferences(getString(R.string.preference_key), MODE_PRIVATE);
        loginState = savedLoginData.getBoolean(getString(R.string.login_state), false);
        if (loginState) {
            EtUsername.setText(savedLoginData.getString(getString(R.string.Username), ""));
            EtPass.setText(savedLoginData.getString(getString(R.string.Pass), ""));
            remember_login.setChecked(true);
        }
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
        if(message != null){
            Snackbar.make(constraintLayout,message,Snackbar.LENGTH_LONG)
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
                DB_con db = new DB_con(savedInfo.login, data);
                response = db.getConnection();
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }


            return response;
        }

        @Override
        protected void onPostExecute(String s) {

            if (response.equals(savedInfo.memDontExist)){
                Snackbar.make(constraintLayout,"Member doesn't exist. Please check and try again",Snackbar.LENGTH_LONG)
                        .show();
            } else if(response.equals(savedInfo.success)){

                SharedPreferences savingLoginData = mContext.getSharedPreferences(getString(R.string.preference_key), MODE_PRIVATE);
                SharedPreferences.Editor editor = savingLoginData.edit();
                if(remember_login.isChecked()) {
                    editor.putBoolean(getString(R.string.login_state), true);
                    editor.putString(getString(R.string.Username), TxtUsername);
                    editor.putString(getString(R.string.Pass), TxtPassword);
                    editor.apply();
                } else {
                    editor.clear();
                    editor.apply();
                }

                Intent intent = new Intent(mContext, DashboardActivity.class);
                intent.putExtra(DashboardActivity.USERNAME_REF, TxtUsername);
                startActivity(intent);
            } else if(response.equals(savedInfo.passDontMatch)){
                Snackbar.make(constraintLayout,"Incorrect password. Please check and try again",Snackbar.LENGTH_LONG)
                        .show();
            }
            super.onPostExecute(s);
        }
    }


}
