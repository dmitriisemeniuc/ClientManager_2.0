package com.semeniuc.dmitrii.clientmanager;

import android.content.Context;
import android.support.annotation.NonNull;

import com.semeniuc.dmitrii.clientmanager.utils.ActivityUtils;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public final class UtilsModule {

    private final Context context;

    UtilsModule(Context context) {
        this.context = context;
    }

    @Provides
    @NonNull
    @Singleton
    public ActivityUtils provideUtils(){
        return new ActivityUtils(context);
    }
}
