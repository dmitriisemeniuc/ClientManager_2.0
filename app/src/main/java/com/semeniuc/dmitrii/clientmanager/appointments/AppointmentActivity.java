package com.semeniuc.dmitrii.clientmanager.appointments;

import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.Toast;

import com.semeniuc.dmitrii.clientmanager.App;
import com.semeniuc.dmitrii.clientmanager.BaseActivity;
import com.semeniuc.dmitrii.clientmanager.R;
import com.semeniuc.dmitrii.clientmanager.login.LoginInteractor;
import com.semeniuc.dmitrii.clientmanager.model.User;
import com.semeniuc.dmitrii.clientmanager.utils.ActivityUtils;
import com.semeniuc.dmitrii.clientmanager.utils.Constants;
import com.semeniuc.dmitrii.clientmanager.utils.GoogleAuthenticator;

import javax.inject.Inject;

public class AppointmentActivity extends BaseActivity implements LoginInteractor.OnLogoutListener {

    private static final String CURRENT_FILTERING_KEY = "CURRENT_FILTERING_KEY";
    private DrawerLayout drawerLayout;
    private AppointmentsPresenterImpl presenter;
    public static String phoneNumber;

    @Inject ActivityUtils utils;
    @Inject User user;
    @Inject GoogleAuthenticator authenticator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        ((App) getApplication()).getComponent().inject(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_appointments);
        initToolbar();
        initNavigationView();
        AppointmentsFragment fragment = addFragment();
        presenter = new AppointmentsPresenterImpl(fragment);
        fragment.setPresenter(presenter);
        presenter.setGoogleApiClient(authenticator, this, this);
        // Load previously saved state, if available.
        if (savedInstanceState != null) {
            AppointmentsFilterType currentFiltering =
                    (AppointmentsFilterType) savedInstanceState.getSerializable(CURRENT_FILTERING_KEY);
            presenter.setFiltering(currentFiltering);
        }
    }

    private void initToolbar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar ab = getSupportActionBar();
        assert ab != null;
        ab.setHomeAsUpIndicator(R.mipmap.ic_menu);
       /* ColorDrawable colorDrawable = new ColorDrawable(Color.parseColor("#B3FFFFFF"));
        ab.setBackgroundDrawable(colorDrawable);*/
        ab.setDisplayHomeAsUpEnabled(true);
    }

    private AppointmentsFragment addFragment() {
        AppointmentsFragment appointmentsFragment =
                (AppointmentsFragment) getSupportFragmentManager().findFragmentById(R.id.contentFrame);
        if (appointmentsFragment == null) {
            // Create the fragment
            appointmentsFragment = AppointmentsFragment.newInstance();
            ActivityUtils.addFragmentToActivity(
                    getSupportFragmentManager(), appointmentsFragment, R.id.contentFrame);
        }
        return appointmentsFragment;
    }

    private void initNavigationView() {
        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawerLayout.setStatusBarBackground(R.color.colorPrimaryDark);
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        if (navigationView != null) {
            setupDrawerContent(navigationView);
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putSerializable(CURRENT_FILTERING_KEY, presenter.getFiltering());

        super.onSaveInstanceState(outState);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                // Open the navigation drawer when the home icon is selected from the toolbar.
                drawerLayout.openDrawer(GravityCompat.START);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void setupDrawerContent(NavigationView navigationView) {
        navigationView.setNavigationItemSelectedListener(
                menuItem -> {
                    int id = menuItem.getItemId();
                    if (id == R.id.nav_gallery) {
                        // Open gallery
                    } else if (id == R.id.nav_statistics) {
                        // Show statistics
                    } else if (id == R.id.nav_manage) {
                        // Open settings
                    } else if (id == R.id.nav_logout) {
                        onLogout();
                    }
                    // Close the navigation drawer when an item is selected.
                    menuItem.setChecked(true);
                    drawerLayout.closeDrawers();
                    return true;
                });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String permissions[],
                                           @NonNull int[] grantResults) {
        switch (requestCode) {
            case Constants.PERMISSIONS_REQUEST_CALL_PHONE: {
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    presenter.requestCallPhonePermission(phoneNumber, this, this);
                } else {
                    Toast.makeText(this,
                            getResources().getString(R.string.grant_call_permission),
                            Toast.LENGTH_LONG).show();
                }
            }
        }
    }

    public void onLogout() {
        presenter.logout(authenticator);
    }

    @Override protected void onDestroy() {
        presenter.onDestroy();
        super.onDestroy();
    }
}
