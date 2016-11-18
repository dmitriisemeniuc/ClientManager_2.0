package com.semeniuc.dmitrii.clientmanager.modules;

import android.support.annotation.NonNull;

import com.semeniuc.dmitrii.clientmanager.model.User;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public final class UserModule {

    @Provides
    @NonNull
    @Singleton
    public User provideUser() {
        return new User();
    }
}
