package com.semeniuc.dmitrii.clientmanager.registr;

import android.view.ViewGroup;

import com.semeniuc.dmitrii.clientmanager.BasePresenter;

public interface RegistrationPresenter extends BasePresenter{

   void onRegisterWithEmail(String email, String password, String confirmPassword);

   void hideKeyboard(ViewGroup layout);
}
