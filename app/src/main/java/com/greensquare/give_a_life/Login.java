package com.greensquare.give_a_life;

import androidx.appcompat.app.AppCompatActivity;

import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;

import com.google.android.material.snackbar.Snackbar;
import com.greensquare.give_a_life.models.Data;
import com.greensquare.give_a_life.models.User;
import com.greensquare.give_a_life.Utility.DataMaster;
import com.greensquare.give_a_life.widget.Shortcut;

public class Login extends AppCompatActivity {

    private Button login, signup;
    private EditText username, password;
    private DataMaster dm;
    private Data data;
    private User user;
    private ProgressBar loginPB;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        dm = new DataMaster(this);
        data = Data.getInstance();
        user = new User();

         loginPB = findViewById(R.id.progressBar_login);
         login = findViewById(R.id.login);
         signup = findViewById(R.id.signup);
         username = findViewById(R.id.email);
         password = findViewById(R.id.password);

    }

    @Override
    protected void onStart() {
        super.onStart();

        dm.internetConnection(new DataMaster.CheckInternetAccess() {
            @Override
            public void access(boolean connected) {
                if(!connected){
                    return;
                }

                login.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        loginPB.setVisibility(View.VISIBLE);
                        String usernameStr, passwordStr;
                        usernameStr = username.getText().toString();
                        passwordStr = password.getText().toString();

                        if(usernameStr.equals("") || passwordStr.equals("")){
                            loginPB.setVisibility(View.INVISIBLE);
                            Snackbar.make(login, R.string.error_missing_login, Snackbar.LENGTH_LONG).show();
                            return;
                        }

                        dm.login(usernameStr, passwordStr, new DataMaster.LoginUser() {
                            @Override
                            public void login(boolean exists, boolean verified, User user) {
                                loginPB.setVisibility(View.INVISIBLE);
                                login.setEnabled(false);
                                signup.setEnabled(false);
                                if(!exists){
                                    Snackbar.make(login,R.string.error_incorrect,Snackbar.LENGTH_LONG).show();
                                    login.setEnabled(true);
                                    signup.setEnabled(true);
                                }else if(!verified){
                                    login.setEnabled(true);
                                    signup.setEnabled(true);
                                    verfication();
                                }else{
                                    login.setEnabled(true);
                                    signup.setEnabled(true);
                                    data.setUser(user);

                                    Intent widgetIntent = new Intent(Login.this, Shortcut.class);
                                    widgetIntent.setAction(AppWidgetManager.ACTION_APPWIDGET_UPDATE);
                                    int[] ids = AppWidgetManager.
                                            getInstance(getApplication())
                                            .getAppWidgetIds(new ComponentName(getApplication()
                                                    , Shortcut.class));
                                    AppWidgetManager appWidgetManager1 = AppWidgetManager.getInstance(getApplicationContext());
                                    Shortcut.updateAppWidget(getApplicationContext(), appWidgetManager1 ,0);
                                    widgetIntent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_IDS,ids);
                                    widgetIntent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, 0);
                                    sendBroadcast(widgetIntent);


                                    widgetIntent = getIntent();
                                    boolean fromWidget = widgetIntent.getBooleanExtra("coming from widget", false);

                                    if(!fromWidget){
                                        login.setEnabled(true);
                                        signup.setEnabled(true);
                                        Intent intent = new Intent(Login.this, TabbedActivity.class);
                                        startActivity(intent);
                                    }
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
