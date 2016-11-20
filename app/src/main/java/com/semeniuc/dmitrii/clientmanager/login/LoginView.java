package com.semeniuc.dmitrii.clientmanager.login;

import com.semeniuc.dmitrii.clientmanager.BaseView;

public interface LoginView extends BaseView<LoginPresenter> {

    void showProgress();

    void hideProgress();

    void setUsernameError();

    void setPasswordError();

    void navigateToHome();

    void loginWithGoogle();

    void loginWithEmail();

    void showLoginMessage();

    void showGoogleLoginError();

    void showNoInternetAccessMessage();

    void showUserSavingFailedMessage();

    void showInvalidCredentialsMessage();
}