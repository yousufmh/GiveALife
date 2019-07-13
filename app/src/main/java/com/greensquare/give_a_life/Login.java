package com.greensquare.give_a_life;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.android.material.snackbar.Snackbar;
import com.greensquare.give_a_life.models.Data;
import com.greensquare.give_a_life.models.User;
import com.greensquare.give_a_life.Utility.DataMaster;

public class Login extends AppCompatActivity {

    private Button login, signup;
    private EditText username, password;
    private DataMaster dm;
    private Data data;
    private User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        dm = new DataMaster(this);
        data = Data.getInstance();
        user = new User();

         login = findViewById(R.id.login);
         signup = findViewById(R.id.signup);
         username = findViewById(R.id.email);
         password = findViewById(R.id.password);

    }

    @Override
    protected void onStart() {
        super.onStart();

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String usernameStr, passwordStr;
                usernameStr = username.getText().toString();
                passwordStr = password.getText().toString();

                dm.login(usernameStr, passwordStr, new DataMaster.LoginUser() {
                    @Override
                    public void login(boolean exists, boolean verified, User user) {
                        if(!exists){
                            Snackbar.make(login,R.string.error_incorrect,Snackbar.LENGTH_LONG).show();
                        }else if(!verified){
                            verfication();
                        }else{
                            data.setUser(user);
                            Intent intent = new Intent(Login.this, TabbedActivity.class);
                            startActivity(intent);
                            finish();
                        }
                    }
                });


            }
        });

        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Login.this, Signup.class);
                startActivity(intent);
                finish();
            }
        });


    }

    private void verfication(){
        Snackbar.make(login,
                R.string.not_verfied,
                Snackbar.LENGTH_INDEFINITE)
                .setAction(R.string.verfiy,
                        new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                dm.verfyEmail();
                                Snackbar.make(login,R.string.email_sent,Snackbar.LENGTH_LONG).show();
                            }
                        }).show();
    }
}
