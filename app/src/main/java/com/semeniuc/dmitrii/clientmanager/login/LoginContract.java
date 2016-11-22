package com.semeniuc.dmitrii.clientmanager.login;

import android.content.Context;
import android.support.v4.app.FragmentActivity;
import android.view.ViewGroup;

import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.api.OptionalPendingResult;
import com.semeniuc.dmitrii.clientmanager.BasePresenter;
import com.semeniuc.dmitrii.clientmanager.BaseView;

public interface LoginContract {

    interface View extends BaseView<Presenter> {

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

    interface Presenter extends BasePresenter {

        void onUserSavingFailed();

        void onShowProgressDialog();

        void onHideProgressDialog();

        void onUpdateUI();

        void onLoginWithGoogle(GoogleSignInResult result);

        void onLoginWithEmail(String email, String password);

        void hideKeyboard(ViewGroup layout);

        void onDestroy();

        void verifyUserType(Context context, FragmentActivity activity);
    }

    interface Interactor {

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

            void onUserSaved();

            void onUserSavingFailed();
        }

        interface OnLogoutListener {

            void onLogout();
        }

        void loginWithGoogle(GoogleSignInResult result, OnGoogleLoginFinishedListener listener);

        void loginWithEmail(String email, String password, OnLoginFinishedListener listener);

        void verifyUserType(Context ctx, FragmentActivity activity,
                            OnVerifyUserTypeFinishedListener listener, LoginPresenter presenter);

        void silentSignInWithGoogle(OptionalPendingResult<GoogleSignInResult> opr,
                                    LoginPresenter presenter);

        void hideKeyboard(ViewGroup layout);
    }
}
