package com.greensquare.give_a_life;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class TabbedActivity extends AppCompatActivity {

    private Button signout,profile;
    private FloatingActionButton add;
    private ViewPager pager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tabbed);

        signout = findViewById(R.id.sign_out);
        profile = findViewById(R.id.profile);
        add = findViewById(R.id.add);

    }

    @Override
    protected void onStart() {
        super.onStart();
        signout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(TabbedActivity.this, Login.class);
                startActivity(intent);
            }
        });
        profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(TabbedActivity.this, Profile.class);
                startActivity(intent);
            }
        });
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(TabbedActivity.this, AddActivity.class);
                startActivity(intent);
            }
        });
    }
}
