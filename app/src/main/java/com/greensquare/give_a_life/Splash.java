package com.greensquare.give_a_life;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.greensquare.give_a_life.Models.Data;
import com.greensquare.give_a_life.Models.Request;
import com.greensquare.give_a_life.Utility.DataMaster;

import java.util.ArrayList;

public class Splash extends AppCompatActivity {

    private Data data;
    private DataMaster DM;
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

        DM.checkConnection();

    }
}
