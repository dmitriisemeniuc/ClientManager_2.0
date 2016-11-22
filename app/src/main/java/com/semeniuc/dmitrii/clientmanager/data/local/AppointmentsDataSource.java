package com.semeniuc.dmitrii.clientmanager.data.local;

import android.support.annotation.NonNull;

import com.semeniuc.dmitrii.clientmanager.model.Appointment;

import java.util.List;

public interface AppointmentsDataSource {

    interface LoadAppointmentsCallback {

        void onAppointmentsLoaded(List<Appointment> appointments);

        void onDataNotAvailable();
    }

    interface GetAppointmentCallback {

        void onAppointmentLoaded(Appointment appointment);

        void onDataNotAvailable();
    }

    interface SaveAppointmentCallBack{

        void onAppointmentSavingFailed();

        void onAppointmentSaved();
    }

    void getAppointments(@NonNull LoadAppointmentsCallback callback);

    void getAppointment(@NonNull Integer appointmentId, @NonNull GetAppointmentCallback callback);

    void saveAppointment(@NonNull Appointment appointment, @NonNull SaveAppointmentCallBack callback);

    void completeAppointment(@NonNull Appointment appointment);

    void completeAppointment(@NonNull String appointmentId);

    void activateAppointment(@NonNull Appointment appointment);

    void activateAppointment(@NonNull String appointmentId);

    void clearCompletedAppointments();

    void refreshAppointments();

    void deleteAllAppointments();

    void deleteAppointment(@NonNull String appointmentId);
}
