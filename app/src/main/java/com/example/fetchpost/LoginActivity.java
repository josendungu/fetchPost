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
    private TextView tvDisp, tvforgot;
    private TextView register;
    private Button BtSubmit;
    protected String TxtUsername, TxtPassword;
    private Context mContext;
    private String member_id;
    private static final String TAG = "LoginActivity";
    public static final  String MESSAGE = "com.example.fetchpost.MESSAGE";
    private View view;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        displayMessages();


        mContext = this;

        EtUsername = (EditText)findViewById(R.id.username);
        EtPass = (EditText)findViewById(R.id.password);
        BtSubmit = (Button)findViewById(R.id.submit);
        tvDisp = (TextView)findViewById(R.id.textView);
        register = (TextView)findViewById(R.id.link_reg);
        tvforgot = (TextView)findViewById(R.id.forgot);
        view = (ConstraintLayout)findViewById(R.id.view);


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

    private void displayMessages() {
        String message = getIntent().getStringExtra(MESSAGE);
        Log.d(TAG, "displayMessages: entered"+message);
        if(message != null){
            Log.d(TAG, "displayMessages: "+message);
            Snackbar.make(view, message, Snackbar.LENGTH_LONG)
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
                member_id = db.getConnection();
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }


            return member_id;
        }

        @Override
        protected void onPostExecute(String s) {
            Log.d(TAG, "onPostExecute: Changing" + member_id);
            tvDisp.setText(member_id);
            super.onPostExecute(s);
        }
    }


}
