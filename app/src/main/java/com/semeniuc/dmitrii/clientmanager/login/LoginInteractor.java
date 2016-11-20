package com.semeniuc.dmitrii.clientmanager.login;

import android.content.Context;
import android.support.v4.app.FragmentActivity;
import android.view.ViewGroup;

import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.api.OptionalPendingResult;

public interface LoginInteractor {

    interface OnLoginFinishedListener {

        void onUsernameError();

        void onPasswordError();

        void onSuccess();

        void onInvalidCredentials();
    }

    interface OnGoogleLoginFinishedListener {

        void onGoogleLoginSuccess();

        void onGoogleLoginError();

        void onNoInternetAccess();

        void onUserSavingFailed();

    }

    interface OnVerifyUserTypeFinishedListener {

        void onUserSaved();

        void onUserSavingFailed();
    }

    interface OnLogoutListener {

        void onLogout();
    }

    void loginWithGoogle(GoogleSignInResult result, OnGoogleLoginFinishedListener listener);

    void loginWithEmail(String email, String password, OnLoginFinishedListener listener);

    void verifyUserType(Context ctx, FragmentActivity activity,
                        OnVerifyUserTypeFinishedListener listener, LoginPresenter presenter);

    void silentSignInWithGoogle(OptionalPendingResult<GoogleSignInResult> opr,
                                LoginPresenter presenter);

    void hideKeyboard(ViewGroup layout);
}
