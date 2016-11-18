package com.semeniuc.dmitrii.clientmanager.registr;

import com.semeniuc.dmitrii.clientmanager.BasePresenter;

public interface RegistrationPresenter extends BasePresenter{

   void onRegisterWithEmail(String email, String password, String confirmPassword);
}
