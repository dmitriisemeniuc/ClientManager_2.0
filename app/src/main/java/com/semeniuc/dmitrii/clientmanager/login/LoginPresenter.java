package com.semeniuc.dmitrii.clientmanager.login;

import android.content.Context;
import android.support.v4.app.FragmentActivity;
import android.view.ViewGroup;

import com.google.android.gms.auth.api.signin.GoogleSignInResult;

public class LoginPresenter implements LoginContract.Presenter, LoginInteractor.OnLoginFinishedListener,
        LoginInteractor.OnGoogleLoginFinishedListener, LoginInteractor.OnVerifyUserTypeFinishedListener {

    private LoginContract.View view;
    private LoginInteractor interactor;

    public LoginPresenter(LoginContract.View view) {
        this.view = view;
        this.interactor = new LoginInteractor();
    }

    @Override public void onLoginWithGoogle(GoogleSignInResult result) {
        interactor.loginWithGoogle(result, this);
    }

    @Override public void onLoginWithEmail(String email, String password) {
        interactor.loginWithEmail(email, password, this);
    }

    @Override public void onDestroy() {
        view = null;
    }

    // It can be: user signed in with google or registered with e-mail
    @Override public void verifyUserType(Context context, FragmentActivity activity) {
        interactor.verifyUserType(context, activity, this, this);
    }

    @Override public void onUsernameError() {
        if (view != null) {
            view.setUsernameError();
            view.hideProgress();
        }
    }

    @Override public void onPasswordError() {
        if (view != null) {
            view.setPasswordError();
            view.hideProgress();
        }
    }

    @Override public void onSuccess() {
        if (view != null){
            view.navigateToHome();
            view.showLoginMessage();
        }
    }

    @Override public void onInvalidCredentials() {
        if (view != null) view.showInvalidCredentialsMessage();
    }

    @Override
    public void start() {

    }

    @Override public void hideKeyboard(ViewGroup layout) {
        interactor.hideKeyboard(layout);
    }

    @Override public void onGoogleLoginSuccess() {
        view.navigateToHome();
        view.showLoginMessage();
    }

    @Override public void onGoogleLoginError() {
        view.showGoogleLoginError();
    }

    @Override public void onNoInternetAccess() {
        view.showNoInternetAccessMessage();
    }

    @Override public void onUserSavingFailed() {
        view.showUserSavingFailedMessage();
    }

    @Override public void onShowProgressDialog() {
        view.showProgress();
    }

    @Override public void onHideProgressDialog() {
        view.hideProgress();
    }

    @Override public void onUpdateUI() {
        view.navigateToHome();
        view.showLoginMessage();
    }

    @Override public void onUserSaved() {
        view.showLoginMessage();
        view.navigateToHome();
    }
}
