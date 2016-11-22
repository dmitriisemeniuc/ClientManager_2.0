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
import com.semeniuc.dmitrii.clientmanager.appointments.AppointmentActivity;
import com.semeniuc.dmitrii.clientmanager.login.LoginActivity;
import com.semeniuc.dmitrii.clientmanager.model.User;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class RegistrationActivity extends BaseActivity implements RegistrationContract.View {

    private RegistrationContract.Presenter presenter;
    private ProgressDialog progressDialog;

    @Inject User user;

    @BindView(R.id.registration_et_email) AppCompatEditText editTextEmail;
    @BindView(R.id.registration_et_password) AppCompatEditText editTextPassword;
    @BindView(R.id.registration_et_password_confirm) AppCompatEditText editTextConfirmPassword;
    @BindView(R.id.main_registration_layout) ViewGroup mainLayout;

    @OnClick(R.id.registration_button) void emailRegistration() {
        registerWithEmail();
    }

    @OnClick(R.id.registration_login_link) void login() {
        goToLoginActivity();
    }

    @Override protected void onCreate(@Nullable Bundle savedInstanceState) {
        ((App) getApplication()).getComponent().inject(this); // Dagger
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
        ButterKnife.bind(this); // Butterknife
        setPresenter(new RegistrationPresenter(this));
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
        startActivity(new Intent(this, AppointmentActivity.class));
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

    @Override public void setPresenter(RegistrationContract.Presenter presenter) {
            this.presenter = presenter;
    }
}
