package com.semeniuc.dmitrii.clientmanager.registr;

import com.semeniuc.dmitrii.clientmanager.BaseView;

public interface RegistrationView extends BaseView<RegistrationPresenter>{
    
    void showEmailRegisteredErrorMessage();

    void navigateToHome();

    void showLoginMessage();

    void showUserSavingFailedMessage();

    void setUsernameError();

    void hideProgress();

    void setPasswordError();

    void setPasswordDoesNotMatchError();
}
