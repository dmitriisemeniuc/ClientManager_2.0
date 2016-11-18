package com.semeniuc.dmitrii.clientmanager;

import com.semeniuc.dmitrii.clientmanager.data.local.DatabaseTaskHelper;
import com.semeniuc.dmitrii.clientmanager.login.LoginActivity;
import com.semeniuc.dmitrii.clientmanager.login.LoginInteractorImpl;
import com.semeniuc.dmitrii.clientmanager.login.LoginPresenterImpl;
import com.semeniuc.dmitrii.clientmanager.main.MainActivity;
import com.semeniuc.dmitrii.clientmanager.modules.AppModule;
import com.semeniuc.dmitrii.clientmanager.modules.AuthenticatorModule;
import com.semeniuc.dmitrii.clientmanager.modules.DatabaseHelperModule;
import com.semeniuc.dmitrii.clientmanager.modules.DatabaseTaskHelperModule;
import com.semeniuc.dmitrii.clientmanager.modules.RepositoryModule;
import com.semeniuc.dmitrii.clientmanager.modules.UserModule;
import com.semeniuc.dmitrii.clientmanager.modules.UtilsModule;
import com.semeniuc.dmitrii.clientmanager.registr.RegistrationActivity;
import com.semeniuc.dmitrii.clientmanager.registr.RegistrationInteractorImpl;
import com.semeniuc.dmitrii.clientmanager.registr.RegistrationPresenterImpl;

import javax.inject.Singleton;

import dagger.Component;

@Component(
        modules = {
                AppModule.class,
                UtilsModule.class,
                AuthenticatorModule.class,
                UserModule.class,
                DatabaseHelperModule.class,
                DatabaseTaskHelperModule.class,
                RepositoryModule.class
        }
)
@Singleton
public interface AppComponent {

    void inject(LoginActivity loginActivity);
    void inject(LoginInteractorImpl loginInteractor);
    void inject(LoginPresenterImpl loginPresenter);
    void inject(RegistrationActivity registrationActivity);
    void inject(RegistrationInteractorImpl registrationInteractor);
    void inject(RegistrationPresenterImpl registrationPresenter);
    void inject(MainActivity mainActivity);
    void inject(DatabaseTaskHelper dbTaskHelper);
}
