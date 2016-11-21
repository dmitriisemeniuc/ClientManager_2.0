package com.semeniuc.dmitrii.clientmanager;

import android.app.Application;

import com.semeniuc.dmitrii.clientmanager.modules.AppModule;
import com.semeniuc.dmitrii.clientmanager.modules.AppointmentsLocalDataSourceModule;
import com.semeniuc.dmitrii.clientmanager.modules.AuthenticatorModule;
import com.semeniuc.dmitrii.clientmanager.modules.DatabaseHelperModule;
import com.semeniuc.dmitrii.clientmanager.modules.DatabaseTaskHelperModule;
import com.semeniuc.dmitrii.clientmanager.modules.RepositoryModule;
import com.semeniuc.dmitrii.clientmanager.modules.UserModule;
import com.semeniuc.dmitrii.clientmanager.modules.UtilsModule;

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
                .appModule(new AppModule(this))
                .utilsModule(new UtilsModule())
                .authenticatorModule(new AuthenticatorModule())
                .userModule(new UserModule())
                .databaseHelperModule(new DatabaseHelperModule())
                .databaseTaskHelperModule(new DatabaseTaskHelperModule())
                .appointmentsLocalDataSourceModule(new AppointmentsLocalDataSourceModule())
                .repositoryModule(new RepositoryModule())
                .build();
    }

    public static App getInstance() {
        return singleton;
    }

    public AppComponent getComponent() {
        return component;
    }
}
