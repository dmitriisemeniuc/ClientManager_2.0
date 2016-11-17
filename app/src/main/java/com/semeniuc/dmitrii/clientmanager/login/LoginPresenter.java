package com.semeniuc.dmitrii.clientmanager.login;

import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.semeniuc.dmitrii.clientmanager.BasePresenter;

public interface LoginPresenter extends BasePresenter{
    void validateCredentials(String username, String password);

    void onUserSavingFailed();

    void onShowProgressDialog();

    void onHideProgressDialog();

    void onUpdateUI();

    void loginWithGoogle(GoogleSignInResult result);

    void onDestroy();

    void verifyUserType();
}