package com.semeniuc.dmitrii.clientmanager.login;

import android.content.Context;
import android.support.v4.app.FragmentActivity;
import android.view.ViewGroup;

import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.api.OptionalPendingResult;

public interface LoginInteractor {

    interface OnLoginListener {

        void onUsernameError();

        void onPasswordError();

        void onSuccess();

        void onInvalidCredentials();
    }

    interface OnGoogleLoginListener {

        void onLoginSuccess();

        void onLoginError();

        void onNoInternetAccess();

        void onUserSavingFailed();

    }

    interface OnVerifyUserTypeListener {

        void onUserSavingSuccess();

        void onUserSavingFailed();
    }

    interface OnLogoutListener {

        void onLogout();
    }

    void loginWithGoogle(GoogleSignInResult result, OnGoogleLoginListener listener);

    void loginWithEmail(String email, String password, OnLoginListener listener);

    void verifyUserType(Context ctx, FragmentActivity activity,
                        OnVerifyUserTypeListener listener, LoginPresenter presenter);

    void silentSignInWithGoogle(OptionalPendingResult<GoogleSignInResult> opr,
                                LoginPresenter presenter);

    void hideKeyboard(ViewGroup layout);
}
