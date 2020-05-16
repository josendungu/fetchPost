package com.example.fetchpost.Activities;

import android.annotation.SuppressLint;
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

import com.example.fetchpost.Helpers.DB_con;
import com.example.fetchpost.R;
import com.example.fetchpost.Helpers.savedInfo;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

public class RegisterActivity extends AppCompatActivity {

    private EditText etFirstName, etLastName, etUsername, etPass, etPassConf, etPhone, etEmail;
    private String fName, lName, username, pass, passConf, email, phone;
    private Context mContext;
    private Boolean loginSuccess = false, valUsername = false, valEmail = false;
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
                    Log.d(TAG, "onClick: first name null");
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
                        new valUsername().execute();
                    } catch (UnsupportedEncodingException e) {
                        e.printStackTrace();
                    }

                    if(usernameState){
                        etUsername.setError("Username already taken");
                    } else if(emailState){
                        etEmail.setError("Email already in use");
                    } else {

                        new RegisterTask().execute();
                        if(loginSuccess = true){
                            Intent intent = new Intent(mContext, LoginActivity.class);
                            intent.putExtra(LoginActivity.MESSAGE, username +" Your account was successfully created. Please login!");
                            intent.putExtra(LoginActivity.USERNAME_PASSED, username);
                            startActivity(intent);
                        }
                    }
                }
            }
        });
    }



    @SuppressLint("StaticFieldLeak")
    public class RegisterTask extends AsyncTask<String,String,String> {

        private String data;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            try {
                data = URLEncoder.encode("username","UTF-8")+"="+URLEncoder.encode(username,"UTF-8")
                        +"&&"+URLEncoder.encode("pass","UTF-8")+"="+URLEncoder.encode(pass,"UTF-8")
                        +"&&"+URLEncoder.encode("first_name","UTF-8")+"="+URLEncoder.encode(fName,"UTF-8")
                        +"&&"+URLEncoder.encode("last_name","UTF-8")+"="+URLEncoder.encode(lName,"UTF-8")
                        +"&&"+URLEncoder.encode("email","UTF-8")+"="+URLEncoder.encode(email,"UTF-8")
                        +"&&"+URLEncoder.encode("phone","UTF-8")+"="+URLEncoder.encode(phone,"UTF-8");


            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        }



        @Override
        protected void onPostExecute(String response) {
            if (response.equals(savedInfo.success)){
                loginSuccess = true;
            }

            super.onPostExecute(response);
        }

        @RequiresApi(api = Build.VERSION_CODES.KITKAT)
        @Override
        protected String doInBackground(String... strings) {

            DB_con db = new DB_con(savedInfo.add_member, data);
            return db.getConnection();
        }
    }


    public class valUsername extends AsyncTask<String, String, String>{

        private String data_username, response;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            try {
                data_username = URLEncoder.encode("email","UTF-8")+"="+URLEncoder.encode(email,"UTF-8");
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        }

        @Override
        protected void onPostExecute(String response) {
            super.onPostExecute(response);

            if (response == savedInfo.success){
                valUsername = true;
            }
        }

        @RequiresApi(api = Build.VERSION_CODES.KITKAT)
        @Override
        protected String doInBackground(String... strings) {
            DB_con db =  new DB_con(savedInfo.validate, data_username);
            return db.getConnection();
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
            DB_con db = new DB_con(savedInfo.validate, data);
            valResponce = db.getConnection();
            Log.d(TAG, "doInBackground: "+ valResponce);
            if(valResponce.equals(savedInfo.success)){
                emailState = true;
            }
            return null;


        }
    }


}
