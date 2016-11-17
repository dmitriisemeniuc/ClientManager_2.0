package com.semeniuc.dmitrii.clientmanager.login;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.view.ViewGroup;

import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.api.OptionalPendingResult;
import com.semeniuc.dmitrii.clientmanager.App;
import com.semeniuc.dmitrii.clientmanager.data.local.DatabaseTaskHelper;
import com.semeniuc.dmitrii.clientmanager.model.User;
import com.semeniuc.dmitrii.clientmanager.utils.ActivityUtils;
import com.semeniuc.dmitrii.clientmanager.utils.Constants;

import javax.inject.Inject;

import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class LoginInteractorImpl implements LoginInteractor {

    @Inject ActivityUtils utils;
    @Inject Context context;
    @Inject User user;
    @Inject DatabaseTaskHelper dbHelper;

    private String email;

    public LoginInteractorImpl(){
        App.getInstance().getComponent().inject(this);
    }

    @Override public void loginWithGoogle(GoogleSignInResult result, OnGoogleLoginFinishedListener listener) {
        if (result.isSuccess()) {
            boolean userCreated = setGoogleUserDetails(result);
            if(userCreated) saveGoogleUser(listener);
        }
        else if (!utils.isNetworkAvailable())
            listener.onNoInternetAccess();
        else
            listener.onGoogleLoginError();
    }

    @Override public void loginWithEmail(String email, String password, OnLoginFinishedListener listener) {
        if(!isSignInFieldsValid(email, password, listener)) return;
        User user = dbHelper.getUserByEmailAndPassword(email, password);
        if(user != null){
            this.user = user;
            utils.setUserInPrefs(Constants.REGISTERED_USER, this.user);
            listener.onSuccess();
        } else {
            listener.onInvalidCredentials();
        }
    }

    private boolean isSignInFieldsValid(String email, String password,OnLoginFinishedListener listener) {
        boolean valid = true;
        if(TextUtils.isEmpty(email)){
            listener.onUsernameError();
            valid = false;
        }
        if(TextUtils.isEmpty(password)){
            listener.onPasswordError();
            valid = false;
        }
        return valid;
    }

    /**
     * Identify the user type
     * It can be: user signed in with google account or logged via e-mail
     */
    @Override public void verifyUserType(final OnVerifyUserTypeFinishedListener listener) {
        String userType = utils.getUserFromPrefs(context);
        if (userType.equals(Constants.GOOGLE_USER)) {
            listener.onSetGoogleApiClient();
            SharedPreferences settings = utils.getSharedPreferences(Constants.LOGIN_PREFS);
            boolean loggedIn = settings.getBoolean(Constants.LOGGED, false);
            if (loggedIn) listener.onSilentSignInWithGoogle();
            return;
        }
        if (userType.equals(Constants.REGISTERED_USER)) {
            SharedPreferences settings = utils.getSharedPreferences(Constants.LOGIN_PREFS);
            boolean loggedIn = settings.getBoolean(Constants.LOGGED, false);
            if (loggedIn) {
                email = settings.getString(Constants.EMAIL, Constants.EMPTY);
                if (!TextUtils.isEmpty(email)) setGlobalUser(listener);
            }
            return;
        }
        if (userType.equals(Constants.NEW_USER)) listener.onSetGoogleApiClient();
    }

    @Override
    public void silentSignInWithGoogle(final OptionalPendingResult<GoogleSignInResult> opr,
                                                 final LoginPresenter presenter) {
        if (opr.isDone()) {
            GoogleSignInResult result = opr.get();
            handleGoogleSignInResult(result, presenter);
        } else {
            presenter.onShowProgressDialog();
            opr.setResultCallback(googleSignInResult -> {
                presenter.onHideProgressDialog();
                handleGoogleSignInResult(googleSignInResult, presenter);
            });
        }
    }

    /**
     * Handle Sign In result.
     * If result success -> go to Sign In Activity and finish this
     */
    private void handleGoogleSignInResult(GoogleSignInResult result,
                                          final LoginPresenter presenter) {
        if (!result.isSuccess()) return;
        GoogleSignInAccount account = result.getSignInAccount();
        if (account == null || dbHelper == null) return;
        user = dbHelper.getUserByEmail(account.getEmail());
        Uri photoUrl = account.getPhotoUrl();
        if (null != user) {
            if (null != photoUrl) user.setPhotoUrl(photoUrl.toString());
            presenter.onUpdateUI();
        }
    }

    /**
     * Sets the signed user to global User object
     */
    private boolean setGoogleUserDetails(@NonNull GoogleSignInResult result) {
        GoogleSignInAccount account = result.getSignInAccount();
        if (null == account) return false;
        user.setGoogleId(account.getId());
        user.setName(account.getDisplayName());
        user.setEmail(account.getEmail());
        return true;
    }

    /**
     * Set Google user to Global user and save it to DB
     */
    private void saveGoogleUser(final OnGoogleLoginFinishedListener listener) {
        saveGoogleUserObservable.subscribe(new Subscriber<Integer>() {

            @Override
            public void onNext(Integer result) {
                if (result == Constants.USER_SAVED || result == Constants.USER_EXISTS) {
                    utils.setUserInPrefs(Constants.GOOGLE_USER, user);
                    listener.onGoogleLoginSuccess();
                }
                if (result == Constants.USER_NOT_SAVED || result == Constants.NO_DB_RESULT)
                    listener.onUserSavingFailed();
            }

            @Override
            public void onCompleted() {
            }

            @Override
            public void onError(Throwable e) {
                listener.onGoogleLoginError();
                e.getMessage();
            }
        });
    }

    final Observable<Integer> saveGoogleUserObservable = Observable.create(new Observable.OnSubscribe<Integer>() {
        @Override
        public void call(Subscriber<? super Integer> subscriber) {
            subscriber.onNext(dbHelper.saveGoogleUser(user));
            subscriber.onCompleted();
        }
    }).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread());

    /**
     * Set global user for silent sign in
     */
    private void setGlobalUser(OnVerifyUserTypeFinishedListener listener) {
        setGlobalUserObservable.subscribe(new Subscriber<Integer>() {

            @Override
            public void onNext(Integer result) {
                if (result == Constants.USER_SAVED) {
                    listener.onUserSaved();
                }
                if (result == Constants.USER_NOT_SAVED || result == Constants.NO_DB_RESULT)
                    listener.onUserSavingFailed();
            }

            @Override
            public void onCompleted() {
            }

            @Override
            public void onError(Throwable e) {
                e.getMessage();
            }
        });
    }

    final Observable<Integer> setGlobalUserObservable = Observable.create(new Observable.OnSubscribe<Integer>() {
        @Override
        public void call(Subscriber<? super Integer> subscriber) {
            subscriber.onNext(dbHelper.setGlobalUserWithEmail(email));
            subscriber.onCompleted();
        }
    }).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread());

    @Override
    public void login(final String username, final String password, final OnLoginFinishedListener listener) {
        // Mock login. I'm creating a handler to delay the answer a couple of seconds
        new Handler().postDelayed(new Runnable() {
            @Override public void run() {
                boolean error = false;
                if (TextUtils.isEmpty(username)){
                    listener.onUsernameError();
                    error = true;
                }
                if (TextUtils.isEmpty(password)){
                    listener.onPasswordError();
                    error = true;
                }
                if (!error){
                    listener.onSuccess();
                }
            }
        }, 2000);
    }

    @Override public void hideKeyboard(ViewGroup layout) {
        utils.hideKeyboard(layout);
    }
}
