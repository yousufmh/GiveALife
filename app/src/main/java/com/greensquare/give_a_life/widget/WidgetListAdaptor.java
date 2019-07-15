package com.greensquare.give_a_life.widget;

import android.appwidget.AppWidgetManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.greensquare.give_a_life.R;
import com.greensquare.give_a_life.models.Request;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executor;

public class WidgetListAdaptor implements RemoteViewsService.RemoteViewsFactory {

    private Context context;
    private ArrayList<Request> requests;
    private Request request;
    private boolean DorR;
    private int widgetID;
    private FirebaseDatabase DB = FirebaseDatabase.getInstance();



    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    public WidgetListAdaptor(Context applicationContext, Intent intent) {
        context = applicationContext;
        Bundle extras = intent.getExtras();

        if (extras != null) {
            DorR = (boolean)extras.get("DorR");
        }else{
            widgetID = AppWidgetManager.INVALID_APPWIDGET_ID;
        }

    }

    private void fetchData(){

        db.collection("requests").whereEqualTo("donor", DorR).get().addOnCompleteListener((Executor) this, new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {

                if(task.isSuccessful()){

                    Request temp;
                    List<DocumentSnapshot> documentSnapshot = task.getResult().getDocuments();

                    for(int i=0; i<documentSnapshot.size();i++){
                        DocumentSnapshot doc = documentSnapshot.get(i);
                        temp = doc.toObject(Request.class);
                        requests.add(temp);
                    }

                }

            }
        });

    }


    @Override
    public void onCreate() {
        requests = new ArrayList<>();
        fetchData();

    }

    @Override
    public void onDataSetChanged() {

        requests.clear();
        fetchData();

    }

    @Override
    public void onDestroy() {
        requests.clear();
    }

    @Override
    public int getCount() {
        return requests.size();
    }

    @Override
    public RemoteViews getViewAt(int i) {
        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.item_row);
        request = requests.get(i);

        views.setTextViewText(R.id.name, request.getName());
        views.setTextViewText(R.id.date, request.getPostDate());
        views.setTextViewText(R.id.blood_type, request.getBloodType());

        return views;
    }

    @Override
    public RemoteViews getLoadingView() {
        return null;
    }

    @Override
    public int getViewTypeCount() {
        return 1;
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }
}
