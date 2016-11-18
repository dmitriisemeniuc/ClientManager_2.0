package com.semeniuc.dmitrii.clientmanager.registr;

import android.view.ViewGroup;

public class RegistrationPresenterImpl implements RegistrationPresenter,
        RegistrationInteractor.OnRegistrationFinishedListener{

    private RegistrationView registrationView;
    private RegistrationInteractor registrationInteractor;

    public RegistrationPresenterImpl(RegistrationView registrationView) {
        this.registrationView = registrationView;
        this.registrationInteractor = new RegistrationInteractorImpl();
    }

    @Override public void start() {}

    @Override
    public void onRegisterWithEmail(String email, String password, String confirmPassword) {
        registrationInteractor.registerWithEmail(email, password, confirmPassword, this);
    }

    @Override public void hideKeyboard(ViewGroup layout) {
        registrationInteractor.hideKeyboard(layout);
    }

    @Override public void onUserNameError() {
        if (registrationView != null) {
            registrationView.setUsernameError();
            registrationView.hideProgress();
        }
    }

    @Override public void onPasswordError() {
        if (registrationView != null) {
            registrationView.setPasswordError();
            registrationView.hideProgress();
        }
    }

    @Override public void onPasswordDoesNotMatch() {
        if (registrationView != null) {
            registrationView.setPasswordDoesNotMatchError();
            registrationView.hideProgress();
        }
    }

    @Override public void onSuccess() {
        if(registrationView != null){
            registrationView.showLoginMessage();
            registrationView.navigateToHome();
        }
    }

    @Override public void onEmailRegisteredError() {
        if(registrationView != null) registrationView.showEmailRegisteredErrorMessage();
    }

    @Override public void onUserSavingFailed() {
        registrationView.showUserSavingFailedMessage();
    }
}
