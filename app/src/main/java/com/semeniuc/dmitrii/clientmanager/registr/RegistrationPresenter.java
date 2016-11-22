package com.semeniuc.dmitrii.clientmanager.registr;

import android.view.ViewGroup;

public class RegistrationPresenter implements RegistrationContract.Presenter,
        RegistrationInteractor.OnRegistrationFinishedListener{

    private RegistrationContract.View view;
    private RegistrationInteractor interactor;

    public RegistrationPresenter(RegistrationContract.View view) {
        this.view = view;
        this.interactor = new RegistrationInteractor();
    }

    @Override public void start() {}

    @Override
    public void onRegisterWithEmail(String email, String password, String confirmPassword) {
        interactor.registerWithEmail(email, password, confirmPassword, this);
    }

    @Override public void hideKeyboard(ViewGroup layout) {
        interactor.hideKeyboard(layout);
    }

    @Override public void onUserNameError() {
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

    @Override public void onPasswordDoesNotMatch() {
        if (view != null) {
            view.setPasswordDoesNotMatchError();
            view.hideProgress();
        }
    }

    @Override public void onSuccess() {
        if(view != null){
            view.showLoginMessage();
            view.navigateToHome();
        }
    }

    @Override public void onEmailRegisteredError() {
        if(view != null) view.showEmailRegisteredErrorMessage();
    }

    @Override public void onUserSavingFailed() {
        view.showUserSavingFailedMessage();
    }
}
