package ru.gb.veber.homework_6_notes.java;

import android.app.Application;

public class AppContext extends Application {
    private  static AppContext instance;
    public static AppContext getInstance()
    {
        return instance;
    }
    @Override
    public void onCreate() {
        super.onCreate();
        instance =this;
    }
}
