package com.semeniuc.dmitrii.clientmanager.modules;

import android.content.Context;
import android.support.annotation.NonNull;

import com.semeniuc.dmitrii.clientmanager.data.local.DatabaseHelper;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class DatabaseHelperModule {

    @Provides
    @NonNull
    @Singleton
    public DatabaseHelper provideDatabaseHelperModule(Context context) {
        return new DatabaseHelper(context);
    }
}
