package com.semeniuc.dmitrii.clientmanager;

import android.app.Application;

public class App extends Application {

    private AppComponent component;

    public AppComponent getComponent() {
        return component;
    }

    @Override
    public void onCreate(){
        super.onCreate();
        component = buildComponent();
    }

    public AppComponent buildComponent() {
        return DaggerAppComponent.builder()
                .appModule(new AppModule(this))
                .utilsModule(new UtilsModule(this))
                .build();
    }
}
