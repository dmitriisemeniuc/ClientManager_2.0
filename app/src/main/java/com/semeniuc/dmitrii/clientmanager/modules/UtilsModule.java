package com.semeniuc.dmitrii.clientmanager.modules;

import android.content.Context;
import android.support.annotation.NonNull;

import com.semeniuc.dmitrii.clientmanager.utils.ActivityUtils;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public final class UtilsModule {

    @Provides
    @NonNull
    @Singleton
    public ActivityUtils provideUtils(Context context){
        return new ActivityUtils(context);
    }
}
