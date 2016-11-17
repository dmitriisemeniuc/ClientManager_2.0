package com.semeniuc.dmitrii.clientmanager.login;

import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.api.OptionalPendingResult;
import com.semeniuc.dmitrii.clientmanager.BaseView;

public interface LoginView extends BaseView<LoginPresenter> {
    void showProgress();

    void hideProgress();

    void setUsernameError();

    void setPasswordError();

    void navigateToHome();

    void setGoogleApiClient();

    void loginWithGoogle();

    void loginWithEmail();

    OptionalPendingResult<GoogleSignInResult> getOptionalPendingResult();

    void showLoginMessage();

    void showGoogleLoginError();

    void showNoInternetAccessMessage();

    void showUserSavingFailedMessage();

    void showInvalidCredentialsMessage();
}