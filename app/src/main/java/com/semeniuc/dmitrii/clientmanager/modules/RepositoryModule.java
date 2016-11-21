package com.semeniuc.dmitrii.clientmanager.modules;

import android.support.annotation.NonNull;

import com.semeniuc.dmitrii.clientmanager.data.local.AppointmentsLocalDataSource;
import com.semeniuc.dmitrii.clientmanager.data.local.DatabaseHelper;
import com.semeniuc.dmitrii.clientmanager.repository.AppointmentRepository;
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

    @Provides
    @NonNull
    @Singleton
    public AppointmentRepository provideAppointmentRepository(DatabaseHelper dbHelper,
                                                              AppointmentsLocalDataSource localDataSource) {
        return new AppointmentRepository(dbHelper, localDataSource);
    }
}
