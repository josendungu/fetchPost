package com.example.fetchpost;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

public class ForgotActivity extends AppCompatActivity {

    EditText etUsername, etPass, etPassConf;
    Button btnSubmit;
    String username, pass, passConf;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot);

        etUsername = (EditText)findViewById(R.id.username);
        etPass = (EditText)findViewById(R.id.password1);
        etPassConf = (EditText)findViewById(R.id.password2);
        btnSubmit = (Button)findViewById(R.id.submit);

        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                username = etUsername.getText().toString();
                pass = etPass.getText().toString();
                passConf = etPassConf.getText().toString();



            }
        });


    }
}
