package com.greensquare.give_a_life;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.tabs.TabLayout;
import com.greensquare.give_a_life.models.Data;
import com.greensquare.give_a_life.Utility.DataMaster;
import com.greensquare.give_a_life.Utility.TabAdaptor;

public class TabbedActivity extends AppCompatActivity {

    private Button signout,profile;
    private FloatingActionButton add;
    private ViewPager pager;
    private TabAdaptor adaptor;
    private TabLayout layout;
    private Data data;
    private DataMaster dm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tabbed);

        data = Data.getInstance();
        dm = new DataMaster(this);
        adaptor = new TabAdaptor(getSupportFragmentManager());

        pager = findViewById(R.id.viewpager_main);
        layout = findViewById(R.id.tabs_main);
        signout = findViewById(R.id.sign_out);
        profile = findViewById(R.id.profile);
        add = findViewById(R.id.add);

        pager.setAdapter(adaptor);
        layout.setupWithViewPager(pager);

    }

    @Override
    protected void onStart() {
        super.onStart();
        signout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dm.getAuth().signOut();
                Intent intent = new Intent(TabbedActivity.this, Login.class);
                startActivity(intent);
                finish();
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
