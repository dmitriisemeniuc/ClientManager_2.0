package com.semeniuc.dmitrii.clientmanager;

import android.support.annotation.NonNull;

import com.semeniuc.dmitrii.clientmanager.utils.GoogleAuthenticator;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public final class AuthenticatorModule {

    @Provides
    @NonNull
    @Singleton
    public GoogleAuthenticator provideGoogleAuthenticator() {
        return new GoogleAuthenticator();
    }
}
