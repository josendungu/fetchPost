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
    private String fName, lName, username, pass, passConf, email, responce, error, phone;
    private Context mContext;
    private static final String TAG = "RegisterActivity";


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
                //check if already registered(email exists)
                //check if username already exists
                //check if phone number is  already used
                //check if passwords match

                fName = etFirstName.getText().toString().trim();
                email = etEmail.getText().toString().trim();
                phone = etPhone.getText().toString().trim();
                lName = etLastName.getText().toString().trim();
                username = etUsername.getText().toString().trim();
                pass = etPass.getText().toString().trim();
                passConf = etPassConf.getText().toString().trim();

                Member member = new Member(username, email,phone,mContext);
                new RegisterTask().execute();

//                try {
//                    if (!member.emailExists()){
//                        Log.d(TAG, "onClick: email  exists");
//                            if (!member.usernameExists()){
//                                Log.d(TAG, "onClick: username");
//                                if(!member.phoneExists()){
//                                    if(pass.equals(passConf)){
//                                        Log.d(TAG, "onClick: passwords");
//                                        new RegisterTask().execute();
//                                    }else{
//                                        error = "Passwords do not match! Please re-enter and try again.";
//                                    }
//                                }else{
//                                    //TODO: Produce dialog to alert phone number exists:: Choose to still use it
//                                }
//                            } else {
//                                error = "The username entered is already in use by another member! Enter another username and retry";
//                            }
//                    } else {
//                        error = "The email entered already exists! Please enter another and retry";
//                    }
//                } catch (UnsupportedEncodingException e) {
//                    e.printStackTrace();
//                }

            }
        });

    }

    public class RegisterTask extends AsyncTask<String,String,String> {

        @Override
        protected void onPostExecute(String s) {
            Log.d(TAG, "onPostExecute: "+responce);
            if (responce.equals(savedInfo.success)){
                Intent intent = new Intent(mContext, LoginActivity.class);
                intent.putExtra(LoginActivity.MESSAGE, username +" your account was successfully created. Please login!");
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
                        +"&&"+URLEncoder.encode("email","UTF-8")+"="+URLEncoder.encode(email,"UTF-8");
//                        +"&&"+URLEncoder.encode("phone","UTF-8")+"="+URLEncoder.encode(Phone,"UTF-8");TODO: add phone after converting to integer
                DB_con db = new DB_con(mContext,savedInfo.baseUrl+savedInfo.add_member, data);
                responce = db.getConnection();
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }

            return responce;
        }
    }


}
