package com.semeniuc.dmitrii.clientmanager.registr;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.AppCompatEditText;
import android.view.ViewGroup;
import android.widget.Toast;

import com.semeniuc.dmitrii.clientmanager.App;
import com.semeniuc.dmitrii.clientmanager.BaseActivity;
import com.semeniuc.dmitrii.clientmanager.R;
import com.semeniuc.dmitrii.clientmanager.login.LoginActivity;
import com.semeniuc.dmitrii.clientmanager.main.MainActivity;
import com.semeniuc.dmitrii.clientmanager.model.User;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class RegistrationActivity extends BaseActivity implements RegistrationView {

    private RegistrationPresenter presenter;
    private ProgressDialog progressDialog;

    @Inject User user;

    @BindView(R.id.registration_email_et) AppCompatEditText editTextEmail;
    @BindView(R.id.registration_password_et) AppCompatEditText editTextPassword;
    @BindView(R.id.registration_password_confirm_et) AppCompatEditText editTextConfirmPassword;
    @BindView(R.id.main_registration_layout) ViewGroup mainLayout;

    @OnClick(R.id.registration_btn) void emailRegistration() {
        registerWithEmail();
    }

    @OnClick(R.id.registration_login_link_tv) void login() {
        goToLoginActivity();
    }

    @Override protected void onCreate(@Nullable Bundle savedInstanceState) {
        ((App) getApplication()).getComponent().inject(this); // Dagger
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
        ButterKnife.bind(this); // Butterknife
        setPresenter(new RegistrationPresenterImpl(this));
    }

    @Override public void setPresenter(RegistrationPresenter presenter) {
        this.presenter = presenter;
    }

    private void registerWithEmail() {
        presenter.hideKeyboard(mainLayout);
        presenter.onRegisterWithEmail(editTextEmail.getText().toString(),
                editTextPassword.getText().toString(), editTextConfirmPassword.getText().toString());
    }

    private void goToLoginActivity() {
        startActivity(new Intent(this, LoginActivity.class));
        finish();
    }

    @Override public void showEmailRegisteredErrorMessage() {
        editTextEmail.setError(getResources().getString(R.string.email_registered));
    }

    @Override public void navigateToHome() {
        startActivity(new Intent(this, MainActivity.class));
        finish();
    }

    @Override public void showLoginMessage() {
        Toast.makeText(this, getResources().getString(R.string.logged_as) + ": "
                + user.getEmail(), Toast.LENGTH_SHORT).show();
    }

    @Override public void showUserSavingFailedMessage() {
        Toast.makeText(this, getResources().getString(R.string.user_saving_failed),
                Toast.LENGTH_LONG).show();
    }

    @Override public void setUsernameError() {
        editTextEmail.setError(getString(R.string.username_error));
    }

    @Override public void setPasswordError() {
        editTextPassword.setError(getString(R.string.password_error));
    }

    @Override public void setPasswordDoesNotMatchError() {
        editTextConfirmPassword.setError(getString(R.string.password_does_not_match));
    }

    @Override public void hideProgress() {
        if (null != progressDialog && progressDialog.isShowing()) {
            progressDialog.dismiss();
        }
    }
}
