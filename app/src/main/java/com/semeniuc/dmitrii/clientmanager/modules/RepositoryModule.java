package com.semeniuc.dmitrii.clientmanager.modules;

import android.support.annotation.NonNull;

import com.semeniuc.dmitrii.clientmanager.data.local.DatabaseHelper;
import com.semeniuc.dmitrii.clientmanager.repository.UserRepository;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class RepositoryModule {

    @Provides
    @NonNull
    @Singleton
    public UserRepository provideUserRepository(DatabaseHelper dbHelper) {
        return new UserRepository(dbHelper);
    }
}
