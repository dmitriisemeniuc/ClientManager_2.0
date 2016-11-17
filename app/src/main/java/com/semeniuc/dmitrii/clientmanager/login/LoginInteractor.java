package com.semeniuc.dmitrii.clientmanager.login;

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

        void onSilentSignInWithGoogle();

        void onSetGoogleApiClient();

        void onUserSaved();

        void onUserSavingFailed();
    }

    interface OnLogoutListener {

        void onLogout();
    }

    void loginWithGoogle(GoogleSignInResult result, OnGoogleLoginFinishedListener listener);

    void loginWithEmail(String email, String password, OnLoginFinishedListener listener);

    void login(String username, String password, OnLoginFinishedListener listener);

    void verifyUserType(OnVerifyUserTypeFinishedListener listener);

    void silentSignInWithGoogle(OptionalPendingResult<GoogleSignInResult> opr,
                                LoginPresenter presenter);

    void hideKeyboard(ViewGroup layout);
}
