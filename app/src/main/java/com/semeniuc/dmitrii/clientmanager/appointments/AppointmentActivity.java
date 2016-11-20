package com.semeniuc.dmitrii.clientmanager.appointments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.semeniuc.dmitrii.clientmanager.App;
import com.semeniuc.dmitrii.clientmanager.BaseActivity;
import com.semeniuc.dmitrii.clientmanager.R;
import com.semeniuc.dmitrii.clientmanager.login.LoginInteractor;
import com.semeniuc.dmitrii.clientmanager.model.Appointment;
import com.semeniuc.dmitrii.clientmanager.model.User;
import com.semeniuc.dmitrii.clientmanager.utils.ActivityUtils;
import com.semeniuc.dmitrii.clientmanager.utils.GoogleAuthenticator;

import java.util.List;

import javax.inject.Inject;

public class AppointmentActivity extends BaseActivity implements AppointmentsView,
        NavigationView.OnNavigationItemSelectedListener, LoginInteractor.OnLogoutListener {

    private static final String CURRENT_FILTERING_KEY = "CURRENT_FILTERING_KEY";
    private DrawerLayout drawerLayout;
    private AppointmentsPresenterImpl appointmentsPresenter;

    @Inject ActivityUtils utils;
    @Inject User user;
    @Inject GoogleAuthenticator googleAuthenticator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        ((App) getApplication()).getComponent().inject(this); // Dagger
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_appointments);

        googleAuthenticator.setGoogleApiClient(getApplicationContext(), this);

        // Set up the toolbar.
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar ab = getSupportActionBar();
        assert ab != null;
        ab.setHomeAsUpIndicator(R.mipmap.ic_menu);
        ab.setDisplayHomeAsUpEnabled(true);

        // Set up the navigation drawer.
        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawerLayout.setStatusBarBackground(R.color.colorPrimaryDark);
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        if (navigationView != null) {
            setupDrawerContent(navigationView);
        }

        // Create the presenter
        appointmentsPresenter = new AppointmentsPresenterImpl(this);

        AppointmentsFragment appointmentsFragment =
                (AppointmentsFragment) getSupportFragmentManager().findFragmentById(R.id.contentFrame);
        if (appointmentsFragment == null) {
            // Create the fragment
            appointmentsFragment = AppointmentsFragment.newInstance();
            appointmentsFragment.setPresenter(appointmentsPresenter);
            ActivityUtils.addFragmentToActivity(
                    getSupportFragmentManager(), appointmentsFragment, R.id.contentFrame);
        }

        // Load previously saved state, if available.
        if (savedInstanceState != null) {
            AppointmentsFilterType currentFiltering =
                    (AppointmentsFilterType) savedInstanceState.getSerializable(CURRENT_FILTERING_KEY);
            appointmentsPresenter.setFiltering(currentFiltering);
        }
    }

    @Override public void setPresenter(AppointmentsPresenterImpl presenter) {
        appointmentsPresenter = presenter;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putSerializable(CURRENT_FILTERING_KEY, appointmentsPresenter.getFiltering());

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

    @Override public void onLogout() {

    }

    @Override public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        return false;
    }

    @Override public void setLoadingIndicator(boolean active) {

    }

    @Override public void showAppointments(List<Appointment> appointments) {

    }

    @Override public void showAddAppointment() {

    }

    @Override public void showAppointmentDetailsUi(String appointmentId) {

    }

    @Override public void showAppointmentMarkedComplete() {

    }

    @Override public void showAppointmentMarkedActive() {

    }

    @Override public void showCompletedAppointmentCleared() {

    }

    @Override public void showNoAppointments() {

    }

    @Override public void showActiveFilterLabel() {

    }

    @Override public void showCompletedFilterLabel() {

    }

    @Override public void showAllFilterLabel() {

    }

    @Override public void showNoActiveAppointments() {

    }

    @Override public void showNoCompletedAppointments() {

    }

    @Override public void showSuccessfullySavedMessage() {

    }

    @Override public boolean isActive() {
        return false;
    }


    @Override public void showFilteringPopUpMenu() {

    }

    @Override public void showLoadingAppointmentsError() {

    }

    @Override public void showCompletedAppointmentsCleared() {

    }


}
