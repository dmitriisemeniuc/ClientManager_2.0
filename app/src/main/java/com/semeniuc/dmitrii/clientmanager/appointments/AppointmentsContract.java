package com.semeniuc.dmitrii.clientmanager.appointments;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentActivity;

import com.semeniuc.dmitrii.clientmanager.BasePresenter;
import com.semeniuc.dmitrii.clientmanager.BaseView;
import com.semeniuc.dmitrii.clientmanager.model.Appointment;
import com.semeniuc.dmitrii.clientmanager.utils.GoogleAuthenticator;

import java.util.List;

public interface AppointmentsContract {

    interface View extends BaseView<Presenter> {

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

    interface Presenter extends BasePresenter {

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

        void requestCallPhonePermission(String phoneNumber, Context context, Activity activity);
    }
}
