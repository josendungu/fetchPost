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

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

public class RegisterActivity extends AppCompatActivity {

    private EditText etFirstName, etLastName, etUsername, etPass, etPassConf, etPhone, etEmail;
    private String fName, lName, username, pass, passConf, email, responce, phone;
    private Context mContext;
    private static final String TAG = "RegisterActivity";
    Boolean usernameState  , emailState ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        mContext = this;

        etFirstName = (EditText)findViewById(R.id.FirstName);
        etLastName = (EditText)findViewById(R.id.LastName);
        etUsername = (EditText)findViewById(R.id.Username);
        etPhone = (EditText)findViewById(R.id.PhoneNumber);
        etEmail = (EditText)findViewById(R.id.email);
        etPass = (EditText)findViewById(R.id.password1);
        etPassConf = (EditText)findViewById(R.id.password2);
        Button btRegister = (Button) findViewById(R.id.register);
        TextView login_link = (TextView) findViewById(R.id.login_link);

        login_link.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(mContext,LoginActivity.class);
                startActivity(intent);
            }
        });

        btRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                etEmail.setError(null);
                etPass.setError(null);
                etLastName.setError(null);
                etFirstName.setError(null);
                etPassConf.setError(null);
                etPhone.setError(null);
                etUsername.setError(null);
                fName = etFirstName.getText().toString().trim();
                email = etEmail.getText().toString().trim();
                phone = etPhone.getText().toString().trim();
                lName = etLastName.getText().toString().trim();
                username = etUsername.getText().toString().trim();
                pass = etPass.getText().toString().trim();
                passConf = etPassConf.getText().toString().trim();

                usernameState = false;
                emailState = false;

                if(fName == null){
                    etFirstName.setError("Please enter your first name");
                } else if(email == null){
                    etEmail.setError("Please enter your email.");
                } else if(phone == null){
                    etPhone.setError("Please enter your phone number.");
                } else if(lName == null){
                    etLastName.setError("Please enter your email.");
                } else if(pass == null){
                    etPass.setError("Please a password for your account.");
                } else if(username == null){
                    etUsername.setError("Please enter un unique username.");
                } else if(passConf == null){
                    etEmail.setError("Please confirm the password entered.");
                } else {
                    try {
                        new ValEmail().execute();
                        new ValUsername().execute();
                    } catch (UnsupportedEncodingException e) {
                        e.printStackTrace();
                    }

                    if(usernameState){
                        etUsername.setError("Username already taken");
                    } else if(emailState){
                        etEmail.setError("Email already in use");
                    } else {
                        new RegisterTask().execute();
                    }
                }


            }
        });

    }

    public class RegisterTask extends AsyncTask<String,String,String> {

        @Override
        protected void onPostExecute(String s) {
            Log.d(TAG, "onPostExecute: "+responce);
            if (responce.equals(savedInfo.success)){
                Intent intent = new Intent(mContext, LoginActivity.class);
                intent.putExtra(LoginActivity.MESSAGE, username +" Your account was successfully created. Please login!");
                intent.putExtra(LoginActivity.USERNAME_PASSED, username);
                startActivity(intent);
            }

            super.onPostExecute(s);
        }

        @RequiresApi(api = Build.VERSION_CODES.KITKAT)
        @Override
        protected String doInBackground(String... strings) {
            try {
                String data = URLEncoder.encode("username","UTF-8")+"="+URLEncoder.encode(username,"UTF-8")
                        +"&&"+URLEncoder.encode("pass","UTF-8")+"="+URLEncoder.encode(pass,"UTF-8")
                        +"&&"+URLEncoder.encode("first_name","UTF-8")+"="+URLEncoder.encode(fName,"UTF-8")
                        +"&&"+URLEncoder.encode("last_name","UTF-8")+"="+URLEncoder.encode(lName,"UTF-8")
                        +"&&"+URLEncoder.encode("email","UTF-8")+"="+URLEncoder.encode(email,"UTF-8")
                        +"&&"+URLEncoder.encode("phone","UTF-8")+"="+URLEncoder.encode(phone,"UTF-8");
                DB_con db = new DB_con(mContext,savedInfo.add_member, data);
                responce = db.getConnection();
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }

            return responce;
        }
    }

    public class ValUsername extends AsyncTask<String, String,String>{
        String valResponce;
        String data = URLEncoder.encode("username","UTF-8")+"="+URLEncoder.encode(username,"UTF-8");

        public ValUsername() throws UnsupportedEncodingException {
        }

        @RequiresApi(api = Build.VERSION_CODES.KITKAT)
        @Override
        protected String doInBackground(String... strings) {
            DB_con db = new DB_con(mContext,savedInfo.validate, data);
            valResponce = db.getConnection();
            if(valResponce.equals(savedInfo.success)){
                Log.d(TAG, "doInBackground: saved: in");
                usernameState = true;
            }
            Log.d(TAG, "doInBackground: " + valResponce);
            return null;
        }
    }

    public class ValEmail extends AsyncTask<String, String,String>{
        String valResponce;
        String data = URLEncoder.encode("email","UTF-8")+"="+URLEncoder.encode(email,"UTF-8");

        public ValEmail() throws UnsupportedEncodingException {
        }

        @RequiresApi(api = Build.VERSION_CODES.KITKAT)
        @Override
        protected String doInBackground(String... strings) {
            DB_con db = new DB_con(mContext,savedInfo.validate, data);
            valResponce = db.getConnection();
            if(valResponce.equals(savedInfo.success)){
                emailState = true;
            }
            Log.d(TAG, "doInBackground2: " + valResponce);
            return null;


        }
    }


}
