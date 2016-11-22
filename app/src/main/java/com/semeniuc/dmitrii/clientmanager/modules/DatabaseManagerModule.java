package com.semeniuc.dmitrii.clientmanager.modules;

import android.support.annotation.NonNull;

import com.semeniuc.dmitrii.clientmanager.data.local.DatabaseManager;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class DatabaseManagerModule {

    @Provides
    @NonNull
    @Singleton
    public DatabaseManager provideDatabaseManager() {
        return new DatabaseManager();
    }
}
