package com.semeniuc.dmitrii.clientmanager.modules;

import android.support.annotation.NonNull;

import com.semeniuc.dmitrii.clientmanager.data.local.AppointmentsLocalDataSource;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class AppointmentsLocalDataSourceModule {

    @Provides
    @NonNull
    @Singleton
    public AppointmentsLocalDataSource provideAppointmentsLocalDataSourceModule() {
        return new AppointmentsLocalDataSource();
    }
}
