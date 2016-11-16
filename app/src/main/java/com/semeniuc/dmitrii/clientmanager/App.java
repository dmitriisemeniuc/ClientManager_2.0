package com.semeniuc.dmitrii.clientmanager;

import android.app.Application;

public class App extends Application {

    private AppComponent component;
    private static App singleton;

    public App() {
    }

    @Override
    public void onCreate(){
        super.onCreate();
        singleton = this;
        component = buildComponent();
    }

    public AppComponent buildComponent() {
        return DaggerAppComponent.builder()
                .utilsModule(new UtilsModule(this))
                .authenticatorModule(new AuthenticatorModule())
                .build();
    }

    public static App getInstance() {
        return singleton;
    }

    public AppComponent getComponent() {
        return component;
    }
}
