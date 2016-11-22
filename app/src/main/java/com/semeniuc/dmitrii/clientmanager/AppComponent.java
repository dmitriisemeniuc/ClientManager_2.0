package com.semeniuc.dmitrii.clientmanager;

import com.semeniuc.dmitrii.clientmanager.appointments.AppointmentActivity;
import com.semeniuc.dmitrii.clientmanager.appointments.AppointmentsPresenter;
import com.semeniuc.dmitrii.clientmanager.data.local.AppointmentsLocalDataSource;
import com.semeniuc.dmitrii.clientmanager.data.local.DatabaseManager;
import com.semeniuc.dmitrii.clientmanager.login.LoginActivity;
import com.semeniuc.dmitrii.clientmanager.login.LoginInteractor;
import com.semeniuc.dmitrii.clientmanager.login.LoginPresenter;
import com.semeniuc.dmitrii.clientmanager.modules.AppModule;
import com.semeniuc.dmitrii.clientmanager.modules.AppointmentsLocalDataSourceModule;
import com.semeniuc.dmitrii.clientmanager.modules.AuthenticatorModule;
import com.semeniuc.dmitrii.clientmanager.modules.DatabaseHelperModule;
import com.semeniuc.dmitrii.clientmanager.modules.DatabaseManagerModule;
import com.semeniuc.dmitrii.clientmanager.modules.RepositoryModule;
import com.semeniuc.dmitrii.clientmanager.modules.UserModule;
import com.semeniuc.dmitrii.clientmanager.modules.UtilsModule;
import com.semeniuc.dmitrii.clientmanager.registr.RegistrationActivity;
import com.semeniuc.dmitrii.clientmanager.registr.RegistrationInteractor;
import com.semeniuc.dmitrii.clientmanager.registr.RegistrationPresenter;

import javax.inject.Singleton;

import dagger.Component;

@Component(
        modules = {
                AppModule.class,
                UtilsModule.class,
                AuthenticatorModule.class,
                UserModule.class,
                DatabaseHelperModule.class,
                DatabaseManagerModule.class,
                AppointmentsLocalDataSourceModule.class,
                RepositoryModule.class
        }
)
@Singleton
public interface AppComponent {
    // Login
    void inject(LoginActivity loginActivity);
    void inject(LoginInteractor loginInteractor);
    void inject(LoginPresenter loginPresenter);
    // Registration
    void inject(RegistrationActivity registrationActivity);
    void inject(RegistrationInteractor registrationInteractor);
    void inject(RegistrationPresenter registrationPresenter);
    // Appointment
    void inject(AppointmentActivity appointmentctivity);
    void inject(AppointmentsPresenter appointmentsPresenter);
    // Data
    void inject(DatabaseManager dbManager);
    void inject(AppointmentsLocalDataSource localDataSource);
}
