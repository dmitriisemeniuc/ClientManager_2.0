package com.semeniuc.dmitrii.clientmanager.login;

import android.content.Context;
import android.support.v4.app.FragmentActivity;

import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.semeniuc.dmitrii.clientmanager.BasePresenter;

public interface LoginPresenter extends BasePresenter{

    void onUserSavingFailed();

    void onShowProgressDialog();

    void onHideProgressDialog();

    void onUpdateUI();

    void onLoginWithGoogle(GoogleSignInResult result);

    void onLoginWithEmail(String email, String password);

    void onDestroy();

    void verifyUserType(Context context, FragmentActivity activity);
}