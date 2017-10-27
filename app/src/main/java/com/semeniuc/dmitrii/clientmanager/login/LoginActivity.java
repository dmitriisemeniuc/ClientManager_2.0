package com.semeniuc.dmitrii.clientmanager.login;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.AppCompatEditText;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.android.gms.auth.api.Auth;
import com.semeniuc.dmitrii.clientmanager.App;
import com.semeniuc.dmitrii.clientmanager.BaseActivity;
import com.semeniuc.dmitrii.clientmanager.R;
import com.semeniuc.dmitrii.clientmanager.main.MainActivity;
import com.semeniuc.dmitrii.clientmanager.model.User;
import com.semeniuc.dmitrii.clientmanager.registr.RegistrationActivity;
import com.semeniuc.dmitrii.clientmanager.utils.ActivityUtils;
import com.semeniuc.dmitrii.clientmanager.utils.Const;
import com.semeniuc.dmitrii.clientmanager.utils.GoogleAuthenticator;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class LoginActivity extends BaseActivity implements LoginView {

    private LoginPresenter presenter;
    private ProgressDialog progressDialog;

    @Inject ActivityUtils utils;
    @Inject GoogleAuthenticator googleAuthenticator;
    @Inject User user;

    @BindView(R.id.login_email_et) AppCompatEditText editTextEmail;
    @BindView(R.id.login_password_et) AppCompatEditText editTextPassword;
    @BindView(R.id.main_login_layout) ViewGroup mainLayout;

    @OnClick(R.id.login_with_google_btn)
    void googleLogin() {
        loginWithGoogle();
    }

    @OnClick(R.id.login_btn)
    void emailLogin() {
        loginWithEmail();
    }
    @OnClick(R.id.login_registration_link_tv)
    void register() {
        goToRegistrationActivity();
    }

    @Override protected void onCreate(Bundle savedInstanceState) {
        ((App) getApplication()).getComponent().inject(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
        setPresenter(new LoginPresenterImpl(this));
        presenter.verifyUserType(this, this);
    }

    @Override public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == Const.Action.SIGN_IN) {
            presenter.onLoginWithGoogle(Auth.GoogleSignInApi.getSignInResultFromIntent(data));
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

    @Override protected void onDestroy() {
        presenter.onDestroy();
        super.onDestroy();
    }

    @Override public void loginWithGoogle() {
        Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(googleAuthenticator.getApiClient());
        startActivityForResult(signInIntent, Const.Action.SIGN_IN);
    }

    @Override public void loginWithEmail() {
        presenter.hideKeyboard(mainLayout);
        presenter.onLoginWithEmail(editTextEmail.getText().toString(),
                editTextPassword.getText().toString());
    }

    @Override public void showLoginMessage() {
        Toast.makeText(this, getResources().getString(R.string.logged_as) + ": "
                + user.getEmail(), Toast.LENGTH_SHORT).show();
    }

    @Override public void showGoogleLoginError() {
        Toast.makeText(this, getResources().getString(R.string.something_wrong),
                Toast.LENGTH_LONG).show();
    }

    @Override public void showNoInternetAccessMessage() {
        Toast.makeText(this, getResources().getString(R.string.no_internet_access),
                Toast.LENGTH_LONG).show();
    }

    @Override public void showUserSavingFailedMessage() {
        Toast.makeText(this, getResources().getString(R.string.user_saving_failed),
                Toast.LENGTH_LONG).show();
    }

    @Override public void showInvalidCredentialsMessage() {
        Toast.makeText(this, getResources().getString(R.string.invalid_credentials),
                Toast.LENGTH_LONG).show();
    }

    private void goToRegistrationActivity() {
        startActivity(new Intent(this, RegistrationActivity.class));
        finish();
    }
}