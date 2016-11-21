package com.semeniuc.dmitrii.clientmanager.appointments;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentActivity;

import com.semeniuc.dmitrii.clientmanager.BasePresenter;
import com.semeniuc.dmitrii.clientmanager.model.Appointment;
import com.semeniuc.dmitrii.clientmanager.utils.GoogleAuthenticator;

public interface AppointmentsPresenter extends BasePresenter{

    void result(int requestCode, int resultCode);

    void loadAppointments(boolean forceUpdate);

    void addNewAppointment();

    void openAppointmentDetails(@NonNull Appointment requestedAppointment);

    void completeAppointment(@NonNull Appointment completedAppointment);

    void activateAppointment(@NonNull Appointment activeAppointment);

    void clearCompletedAppointments();

    void setFiltering(AppointmentsFilterType requestType);

    AppointmentsFilterType getFiltering();

    void setGoogleApiClient(GoogleAuthenticator auth, Context context, FragmentActivity fragmentActivity);

    void logout(GoogleAuthenticator authenticator);

    void onDestroy();
}
