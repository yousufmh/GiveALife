package com.greensquare.give_a_life.widget;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.View;
import android.widget.RemoteViews;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.greensquare.give_a_life.AddActivity;
import com.greensquare.give_a_life.Login;
import com.greensquare.give_a_life.R;
import com.greensquare.give_a_life.Utility.DataMaster;

/**
 * Implementation of App Widget functionality.
 */
public class Shortcut extends AppWidgetProvider {


    private final static String DONOR = "DONOR";
    private final static String REQUESTER = "REQUESTER";
    @Override
    public void onReceive(Context context, Intent intent) {
        super.onReceive(context, intent);

        Intent servicesIntent = new Intent(context,WidgetServices.class);
        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.shortcut);


        if(DONOR.equals(intent.getAction())){

            servicesIntent.putExtra("DorR", true);
            intent.setData(Uri.parse(intent.toUri(Intent.URI_INTENT_SCHEME)));
            views.setRemoteAdapter(R.id.list_widget,intent);

        }else if (REQUESTER.equals(intent.getAction())){
            servicesIntent.putExtra("DorR", false);
            intent.setData(Uri.parse(intent.toUri(Intent.URI_INTENT_SCHEME)));
            views.setRemoteAdapter(R.id.list_widget,intent);
        }



    }

    static void updateAppWidget(Context context, AppWidgetManager appWidgetManager,
                                int appWidgetId) {

        FirebaseAuth auth = FirebaseAuth.getInstance();
        FirebaseUser user = auth.getCurrentUser();
        boolean userExist = user!=null;

        // Construct the RemoteViews object
        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.shortcut);

        if(userExist){
            views.setTextViewText(R.id.name_widget, "Welcome "+user.getEmail());

            Intent addIntent = new Intent(context, AddActivity.class);
            Intent donorIntent = new Intent(context, Shortcut.class);
            Intent requesterIntent = new Intent(context, Shortcut.class);

            donorIntent.setAction(DONOR);
            requesterIntent.setAction(REQUESTER);

            PendingIntent pendingAdd = PendingIntent.getActivity(context,0,addIntent, PendingIntent.FLAG_UPDATE_CURRENT);
            PendingIntent pendingDonor = PendingIntent.getBroadcast(context, 0, donorIntent, 0);
            PendingIntent pendingRequester = PendingIntent.getBroadcast(context, 0, requesterIntent, 0);

            views.setOnClickPendingIntent(R.id.donor_btn,pendingDonor);
            views.setOnClickPendingIntent(R.id.add_widget, pendingAdd);
            views.setOnClickPendingIntent(R.id.requester_btn, pendingRequester);

        }else{
            views.setTextViewText(R.id.name_widget, "Welcome Guest Please Sign in to use the Widget");
            Intent signActivity = new Intent(context, Login.class);

            views.setViewVisibility(R.id.list_widget, View.INVISIBLE);
            views.setViewVisibility(R.id.login_widget, View.VISIBLE);
            PendingIntent pending = PendingIntent.getActivity(context,0,signActivity, PendingIntent.FLAG_UPDATE_CURRENT);

            views.setOnClickPendingIntent(R.id.donor_btn,pending);
            views.setOnClickPendingIntent(R.id.add_widget, pending);
            views.setOnClickPendingIntent(R.id.requester_btn, pending);
            views.setOnClickPendingIntent(R.id.name_widget, pending);
            views.setOnClickPendingIntent(R.id.login_widget,pending);

        }


        // Instruct the widget manager to update the widget
        appWidgetManager.updateAppWidget(appWidgetId, views);
    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        // There may be multiple widgets active, so update all of them
        for (int appWidgetId : appWidgetIds) {
            updateAppWidget(context, appWidgetManager, appWidgetId);
        }
    }

    @Override
    public void onEnabled(Context context) {
        // Enter relevant functionality for when the first widget is created
    }

    @Override
    public void onDisabled(Context context) {
        // Enter relevant functionality for when the last widget is disabled
    }
}

