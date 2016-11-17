package com.semeniuc.dmitrii.clientmanager.modules;

import android.support.annotation.NonNull;

import com.semeniuc.dmitrii.clientmanager.data.local.DatabaseTaskHelper;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class DatabaseTaskHelperModule {

    @Provides
    @NonNull
    @Singleton
    public DatabaseTaskHelper provideDatabaseTaskHelper() {
        return new DatabaseTaskHelper();
    }
}
