package com.greensquare.give_a_life;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.tabs.TabLayout;
import com.greensquare.give_a_life.models.Data;
import com.greensquare.give_a_life.Utility.DataMaster;
import com.greensquare.give_a_life.Utility.TabAdaptor;
import com.greensquare.give_a_life.widget.Shortcut;

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

        Intent fromWidget = getIntent();

        boolean DorR = fromWidget.getBooleanExtra("DorR", true);

        pager = findViewById(R.id.viewpager_main);
        layout = findViewById(R.id.tabs_main);
        signout = findViewById(R.id.sign_out);
        profile = findViewById(R.id.profile);
        add = findViewById(R.id.add);

        pager.setAdapter(adaptor);
        layout.setupWithViewPager(pager);

        if(DorR){
            pager.setCurrentItem(0);
        }else{
            pager.setCurrentItem(1);
        }

    }

    @Override
    protected void onStart() {
        super.onStart();
        signout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dm.getAuth().signOut();

                Intent widgetIntent = new Intent(TabbedActivity.this, Shortcut.class);
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
