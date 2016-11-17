package com.semeniuc.dmitrii.clientmanager.login;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.AppCompatEditText;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.api.OptionalPendingResult;
import com.semeniuc.dmitrii.clientmanager.App;
import com.semeniuc.dmitrii.clientmanager.BaseActivity;
import com.semeniuc.dmitrii.clientmanager.R;
import com.semeniuc.dmitrii.clientmanager.main.MainActivity;
import com.semeniuc.dmitrii.clientmanager.model.User;
import com.semeniuc.dmitrii.clientmanager.utils.ActivityUtils;
import com.semeniuc.dmitrii.clientmanager.utils.Constants;
import com.semeniuc.dmitrii.clientmanager.utils.GoogleAuthenticator;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class LoginActivity extends BaseActivity implements LoginView, View.OnClickListener {

    private LoginPresenter presenter;
    private ProgressDialog progressDialog;

    @Inject ActivityUtils utils;
    @Inject GoogleAuthenticator googleAuthenticator;
    @Inject User user;

    @BindView(R.id.login_et_email) AppCompatEditText editTextEmail;
    @BindView(R.id.login_et_password) AppCompatEditText editTextPassword;
    @BindView(R.id.main_login_layout) ViewGroup mainLayout;

    @OnClick(R.id.login_with_google_button) void googleLogin() {
        loginWithGoogle();
    }
    //@OnClick(R.id.login_button) void emailLogin() { loginWithEmail(); }
    //@OnClick(R.id.login_registration_link) void register() { goToRegistrationActivity(); }

    @Override protected void onCreate(Bundle savedInstanceState) {
        ((App) getApplication()).getComponent().inject(this); // Dagger
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this); // Butterknife
        setPresenter(new LoginPresenterImpl(this));
        presenter.verifyUserType();
    }

    @Override public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == Constants.RC_SIGN_IN) {
            presenter.loginWithGoogle(Auth.GoogleSignInApi.getSignInResultFromIntent(data));
        }
    }

    @Override public void setPresenter(LoginPresenter presenter) {
        this.presenter = presenter;
    }

    @Override public void showProgress() {
        if (null == progressDialog) {
            progressDialog = new ProgressDialog(this);
            progressDialog.setMessage(getString(R.string.please_wait));
            progressDialog.setIndeterminate(true);
        }
        progressDialog.show();
    }

    @Override public void hideProgress() {
        if (null != progressDialog && progressDialog.isShowing()) {
            progressDialog.dismiss();
        }
    }

    @Override public void setUsernameError() {
        editTextEmail.setError(getString(R.string.username_error));
    }

    @Override public void setPasswordError() {
        editTextPassword.setError(getString(R.string.password_error));
    }

    @Override public void navigateToHome() {
        startActivity(new Intent(this, MainActivity.class));
        finish();
    }

    @Override public void setGoogleApiClient() {
        googleAuthenticator.setGoogleApiClient(getApplicationContext(), this);
    }

    @Override public void onClick(View v) {
        presenter.validateCredentials(editTextEmail.getText().toString(),
                editTextPassword.getText().toString());
    }

    @Override protected void onDestroy() {
        presenter.onDestroy();
        super.onDestroy();
    }

    private void loginWithGoogle() {
        Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(googleAuthenticator.getApiClient());
        startActivityForResult(signInIntent, Constants.RC_SIGN_IN);
    }

    @Override public OptionalPendingResult<GoogleSignInResult> getOptionalPendingResult() {
        return googleAuthenticator.getOptionalPendingResult();
    }

    @Override public void showLoginMessage() {
        Toast.makeText(this, getResources().getString(R.string.logged_as) + ": "
                + user.getEmail(), Toast.LENGTH_LONG).show();
    }

    @Override public void showGoogleLoginError() {
        Toast.makeText(this, getResources().getString(R.string.something_wrong),
                Toast.LENGTH_SHORT).show();
    }

    @Override public void showNoInternetAccessMessage() {
        Toast.makeText(this, getResources().getString(R.string.no_internet_access),
                Toast.LENGTH_SHORT).show();
    }

    @Override public void showUserSavingFailedMessage() {
        Toast.makeText(this, getResources().getString(R.string.user_saving_failed),
                Toast.LENGTH_SHORT).show();
    }

    @Override public void updateUI(boolean update) {
        if (!update) return;
        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
        startActivity(intent);
    }
}