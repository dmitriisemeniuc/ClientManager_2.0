package com.semeniuc.dmitrii.clientmanager.appointments;

import com.semeniuc.dmitrii.clientmanager.BaseView;
import com.semeniuc.dmitrii.clientmanager.model.Appointment;

import java.util.List;

public interface AppointmentsView extends BaseView<AppointmentsPresenterImpl> {

    void setLoadingIndicator(boolean active);

    void showAppointments(List<Appointment> appointments);

    void showAddAppointment();

    void showAppointmentDetailsUi(String appointmentId);

    void showAppointmentMarkedComplete();

    void showAppointmentMarkedActive();

    void showCompletedAppointmentCleared();

    void showNoAppointments();

    void showActiveFilterLabel();

    void showCompletedFilterLabel();

    void showAllFilterLabel();

    void showNoActiveAppointments();

    void showNoCompletedAppointments();

    void showSuccessfullySavedMessage();

    boolean isActive();

    void showFilteringPopUpMenu();

    void showLoadingAppointmentsError();

    void showCompletedAppointmentsCleared();

    void goToLoginActivity();

    void makeCallToNumber(String phoneNumber);

}
