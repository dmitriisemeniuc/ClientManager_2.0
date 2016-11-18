package com.semeniuc.dmitrii.clientmanager.registr;

import android.view.ViewGroup;

public interface RegistrationInteractor {

    interface OnRegistrationFinishedListener {

        void onUserNameError();

        void onPasswordError();

        void onPasswordDoesNotMatch();

        void onSuccess();

        void onEmailRegisteredError();

        void onUserSavingFailed();
    }

    void registerWithEmail(String email, String password,
                           String confirmPassword, OnRegistrationFinishedListener listener);

    void hideKeyboard(ViewGroup layout);
}
