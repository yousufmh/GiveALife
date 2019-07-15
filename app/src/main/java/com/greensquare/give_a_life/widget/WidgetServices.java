package com.greensquare.give_a_life.widget;

import android.content.Intent;
import android.widget.RemoteViewsService;

public class WidgetServices extends RemoteViewsService {

    public WidgetServices(){

    }

    @Override
    public RemoteViewsFactory onGetViewFactory(Intent intent) {

        return new WidgetListAdaptor(getApplicationContext(),intent);
    }
}
