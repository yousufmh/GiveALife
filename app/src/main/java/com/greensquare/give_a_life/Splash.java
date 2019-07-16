package com.greensquare.give_a_life;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.google.android.material.snackbar.Snackbar;
import com.greensquare.give_a_life.models.Data;
import com.greensquare.give_a_life.Utility.DataMaster;

public class Splash extends AppCompatActivity {

    private Data data;
    private DataMaster DM;
    private final static String TAG = "SPLASH";

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_spalsh);

        data = Data.getInstance();
        DM = new DataMaster(this);


    }

    @Override
    protected void onStart() {
        super.onStart();

        DM.internetConnection(new DataMaster.CheckInternetAccess() {
            @Override
            public void access(boolean connected) {
                Log.d(TAG, "The internet Access is within SPLASH "+connected);
                if(!connected){
                    Snackbar.make(findViewById(R.id.imageView), "No Internet Connection", Snackbar.LENGTH_INDEFINITE)
                            .show();
                    return;
                }
                if(DM.getAuth().getCurrentUser()==null) {

                    Intent intent = new Intent(Splash.this, Login.class);
                    startActivity(intent);
                }else{
                    Intent intent = new Intent(Splash.this, TabbedActivity.class);
                    startActivity(intent);
                }
                finish();

            }
        });

    }
    }

