package com.example.fetchpost;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

public class RegisterActivity extends AppCompatActivity {

    EditText etFirstName, etLastName, etUsername, EtPass, etPassConf, etPhone, etEmail;
    Button btRegister;
    String FName, LName, Username, Pass, PassConf, Email, Phone, member_id;
    TextView login_link;
    Context mContext;
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
        EtPass = (EditText)findViewById(R.id.password1);
        etPassConf = (EditText)findViewById(R.id.password2);
        btRegister = (Button)findViewById(R.id.register);
        login_link = (TextView)findViewById(R.id.login_link);

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

                FName = etFirstName.getText().toString();
                Email = etEmail.getText().toString();
                Phone = etPhone.getText().toString();
                LName = etLastName.getText().toString();
                Username = etUsername.getText().toString();
                Pass = EtPass.getText().toString();
                PassConf = etPassConf.getText().toString();

//                if (emailExists()){
//
//
//                } else if(usernameExists()){
//
//
//                } else if(phoneExists()){
//
//                } else if(passMatch()){
//
//                }

                new RegisterTask().execute();





            }
        });

    }

    public class RegisterTask extends AsyncTask<String,String,String> {

        @RequiresApi(api = Build.VERSION_CODES.KITKAT)
        @Override
        protected String doInBackground(String... strings) {
            try {
                String data = URLEncoder.encode("username","UTF-8")+"="+URLEncoder.encode(Username,"UTF-8")
                        +"&&"+URLEncoder.encode("pass","UTF-8")+"="+URLEncoder.encode(Pass,"UTF-8")
                        +"&&"+URLEncoder.encode("first_name","UTF-8")+"="+URLEncoder.encode(FName,"UTF-8")
                        +"&&"+URLEncoder.encode("last_name","UTF-8")+"="+URLEncoder.encode(LName,"UTF-8")
                        +"&&"+URLEncoder.encode("email","UTF-8")+"="+URLEncoder.encode(Email,"UTF-8")
                        +"&&"+URLEncoder.encode("phone","UTF-8")+"="+URLEncoder.encode(Phone,"UTF-8");
                DB_con db = new DB_con(mContext,savedInfo.baseUrl+savedInfo.add_member, data);
                member_id = db.getConnection();
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }

            return member_id;
        }
    }

    private boolean passMatch() {
        return false;
    }

    private boolean phoneExists() {

        return true;
    }

    private boolean usernameExists() {

        return true;

    }

    private boolean emailExists() {
        return true;
    }
}