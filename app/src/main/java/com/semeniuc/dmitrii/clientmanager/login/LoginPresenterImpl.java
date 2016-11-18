package com.semeniuc.dmitrii.clientmanager.login;

import android.view.ViewGroup;

import com.google.android.gms.auth.api.signin.GoogleSignInResult;

public class LoginPresenterImpl implements LoginPresenter, LoginInteractor.OnLoginFinishedListener,
        LoginInteractor.OnGoogleLoginFinishedListener, LoginInteractor.OnVerifyUserTypeFinishedListener {

    private LoginView loginView;
    private LoginInteractor loginInteractor;

    public LoginPresenterImpl(LoginView loginView) {
        this.loginView = loginView;
        this.loginInteractor = new LoginInteractorImpl();
    }

    @Override public void onLoginWithGoogle(GoogleSignInResult result) {
        loginInteractor.loginWithGoogle(result, this);
    }

    @Override public void onLoginWithEmail(String email, String password) {
        loginInteractor.loginWithEmail(email, password, this);
    }

    @Override public void onDestroy() {
        loginView = null;
    }

    // It can be: user signed in with google or registered with e-mail
    @Override public void verifyUserType() {
        loginInteractor.verifyUserType(this);
    }

    @Override public void onUsernameError() {
        if (loginView != null) {
            loginView.setUsernameError();
            loginView.hideProgress();
        }
    }

    @Override public void onPasswordError() {
        if (loginView != null) {
            loginView.setPasswordError();
            loginView.hideProgress();
        }
    }

    @Override public void onSuccess() {
        if (loginView != null){
            loginView.navigateToHome();
            loginView.showLoginMessage();
        }
    }

    @Override public void onInvalidCredentials() {
        if (loginView != null) loginView.showInvalidCredentialsMessage();
    }

    @Override
    public void start() {

    }

    @Override public void hideKeyboard(ViewGroup layout) {
        loginInteractor.hideKeyboard(layout);
    }

    @Override public void onGoogleLoginSuccess() {
        loginView.navigateToHome();
        loginView.showLoginMessage();
    }

    @Override public void onGoogleLoginError() {
        loginView.showGoogleLoginError();
    }

    @Override public void onNoInternetAccess() {
        loginView.showNoInternetAccessMessage();
    }

    @Override public void onUserSavingFailed() {
        loginView.showUserSavingFailedMessage();
    }

    @Override public void onShowProgressDialog() {
        loginView.showProgress();
    }

    @Override public void onHideProgressDialog() {
        loginView.hideProgress();
    }

    @Override public void onUpdateUI() {
        loginView.navigateToHome();
        loginView.showLoginMessage();
    }

    @Override public void onSilentSignInWithGoogle() {
        loginInteractor.silentSignInWithGoogle(loginView.getOptionalPendingResult(), this);
    }

    @Override public void onSetGoogleApiClient() {
        loginView.setGoogleApiClient();
    }

    @Override public void onUserSaved() {
        loginView.showLoginMessage();
        loginView.navigateToHome();
    }
}
