package com.semeniuc.dmitrii.clientmanager.registr;

import android.view.ViewGroup;

import com.semeniuc.dmitrii.clientmanager.BasePresenter;
import com.semeniuc.dmitrii.clientmanager.BaseView;

public interface RegistrationContract {

    interface View extends BaseView<RegistrationPresenter> {

        void showEmailRegisteredErrorMessage();

        void navigateToHome();

        void showLoginMessage();

        void showUserSavingFailedMessage();

        void setUsernameError();

        void hideProgress();

        void setPasswordError();

        void setPasswordDoesNotMatchError();
    }

    interface Presenter extends BasePresenter {

        void onRegisterWithEmail(String email, String password, String confirmPassword);

        void hideKeyboard(ViewGroup layout);
    }

    interface Interactor {

        interface OnRegistrationFinishedListener {

            void onUserNameError();

            void onPasswordError();

            void onPasswordDoesNotMatch();

            void onSuccess();

            void onEmailRegisteredError();

            void onUserSavingFailed();
        }

        void registerWithEmail(String email, String password,
                               String confirmPassword, OnRegistrationFinishedListener listener);

        void hideKeyboard(ViewGroup layout);
    }
}
