package com.semeniuc.dmitrii.clientmanager;

import com.semeniuc.dmitrii.clientmanager.login.LoginActivity;
import com.semeniuc.dmitrii.clientmanager.main.MainActivity;

import javax.inject.Singleton;

import dagger.Component;

@Component(modules = {UtilsModule.class, AuthenticatorModule.class})
@Singleton
public interface AppComponent {

    void inject(LoginActivity loginActivity);
    void inject(MainActivity mainActivity);
}
