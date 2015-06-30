package org.scystl.todolist;

import android.app.Application;

import com.activeandroid.ActiveAndroid;

/**
 * Created by Andrea on 27/06/2015.
 */
public class MainApplication extends com.activeandroid.app.Application{
    @Override
    public void onCreate() {
        ActiveAndroid.initialize(this);
    }
}
