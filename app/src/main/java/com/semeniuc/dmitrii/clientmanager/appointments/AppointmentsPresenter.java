package com.semeniuc.dmitrii.clientmanager.appointments;

import android.support.annotation.NonNull;

import com.semeniuc.dmitrii.clientmanager.BasePresenter;
import com.semeniuc.dmitrii.clientmanager.model.Appointment;

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

}
