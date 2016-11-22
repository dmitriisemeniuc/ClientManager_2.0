package com.semeniuc.dmitrii.clientmanager.addeditappointment;

import android.support.annotation.NonNull;

import com.semeniuc.dmitrii.clientmanager.App;
import com.semeniuc.dmitrii.clientmanager.data.local.AppointmentsDataSource;
import com.semeniuc.dmitrii.clientmanager.model.Appointment;
import com.semeniuc.dmitrii.clientmanager.repository.AppointmentRepository;

import javax.inject.Inject;

import static com.google.common.base.Preconditions.checkNotNull;

public class AddEditAppointmentPresenter implements AddEditAppointmentContract.Presenter,
        AppointmentsDataSource.GetAppointmentCallback, AppointmentsDataSource.SaveAppointmentCallBack{

    @NonNull private final AddEditAppointmentContract.View view;

    private int appointmentId;

    @Inject AppointmentRepository appointmentRepository;

    public AddEditAppointmentPresenter(@NonNull AddEditAppointmentContract.View view) {
        this.view = checkNotNull(view);

        view.setPresenter(this);
        App.getInstance().getComponent().inject(this);
    }

    @Override public void start() {
        if (!isNewAppointment()) {
            populateAppointment();
        }
    }

    @Override public void populateAppointment() {
        if (isNewAppointment()) {
            throw new RuntimeException("populateAppointment() was called but appointment is new.");
        }
        appointmentRepository.getAppointment(appointmentId, this);
    }

    private boolean isNewAppointment() {
        return appointmentId == 0;
    }

    @Override public void onAppointmentLoaded(Appointment appointment) {
        // The view may not be able to handle UI updates anymore
        if (view.isActive()) {
            view.setClient(appointment.getClient().getName());
            view.setAddress(appointment.getClientContactAddress());
            view.setPhone(appointment.getClientContactPhone());
            view.setService(appointment.getService().getName());
            view.setDate(appointment.getDate());
            view.setTools(appointment.getTools());
            view.setInfo(appointment.getInfo());
        }
    }

    public void setAppointmentId(int appointmentId) {
        this.appointmentId = appointmentId;
    }

    @Override public void onDataNotAvailable() {
        view.showDataNotAvailableMessage();
    }

    @Override public void onAppointmentSavingFailed() {
        view.showAppointmentSavingFailedMessage();
    }

    @Override public void onAppointmentSaved() {
        view.showAppointmentSavedMessage();
    }
}
