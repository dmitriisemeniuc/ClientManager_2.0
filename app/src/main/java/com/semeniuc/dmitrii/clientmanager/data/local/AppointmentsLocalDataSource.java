package com.semeniuc.dmitrii.clientmanager.data.local;

import android.support.annotation.NonNull;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.stmt.QueryBuilder;
import com.j256.ormlite.stmt.Where;
import com.semeniuc.dmitrii.clientmanager.App;
import com.semeniuc.dmitrii.clientmanager.model.Appointment;
import com.semeniuc.dmitrii.clientmanager.model.Client;
import com.semeniuc.dmitrii.clientmanager.model.Contact;
import com.semeniuc.dmitrii.clientmanager.model.Service;
import com.semeniuc.dmitrii.clientmanager.model.Tools;
import com.semeniuc.dmitrii.clientmanager.model.User;
import com.semeniuc.dmitrii.clientmanager.utils.Constants;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.inject.Inject;

public class AppointmentsLocalDataSource implements AppointmentsDataSource{

    @Inject User user;
    @Inject DatabaseHelper dbHelper;

    public AppointmentsLocalDataSource() {
        App.getInstance().getComponent().inject(this);
    }

    public void getAppointments(@NonNull AppointmentsDataSource.LoadAppointmentsCallback callback) {
        List<Appointment> appointments = null;
        if(Constants.DEBUG){
            appointments = getFakeData();
        } else {
            try {
                Dao<Appointment, Integer> appointmentDAO = dbHelper.getAppointmentDao();
                QueryBuilder<Appointment, Integer> queryBuilder = appointmentDAO.queryBuilder();
                Where<Appointment, Integer> where = queryBuilder.where();
                where.eq(Appointment.USER_FIELD_NAME, user.getId());
                queryBuilder.orderBy(Appointment.DATE_FIELD_NAME, true);
                appointments = appointmentDAO.query(queryBuilder.prepare());
            } catch (java.sql.SQLException e) {
                e.printStackTrace();
            }
        }
        callback.onAppointmentsLoaded(appointments);
    }

    @Override
    public void getAppointment(@NonNull Integer appointmentId, @NonNull GetAppointmentCallback callback) {
        Appointment appointment = null;
        try {
            appointment = dbHelper.getAppointmentDao().queryForId(appointmentId);
            if (appointment != null){
                callback.onAppointmentLoaded(appointment);
                return;
            }
            callback.onDataNotAvailable();
        } catch (java.sql.SQLException e) {
            e.printStackTrace();
        }
    }

    private List<Appointment> getFakeData() {
        List<Appointment> list = new ArrayList<>();
        for (int i = 1; i < 50; i++) {
            Client client = new Client(user);
            client.setName("Client " + i);
            Contact contact = new Contact();
            contact.setAddress("Address" + i);
            contact.setPhone("123456789" + i);
            client.setContact(contact);
            Service service = new Service();
            service.setName("Service " + i);
            Tools tools = new Tools();
            int result = i % 2;
            boolean paid = false;
            boolean completed = false;
            String info = "";
            if(i < 45){
                info = String.valueOf("info" + i);
                if (result == 0) {
                    tools.setTrimmer(true);
                    tools.setCutSet(true);
                } else {
                    tools.setBrush(true);
                    tools.setOxy(true);
                }
                paid = result == 0;
                completed = result != 0;
            }
            Appointment appointment = new Appointment(i, user, client, service, tools, info,
                    new Date(), "100 " + i, paid, completed
            );
            list.add(appointment);
        }
        return list;
    }


    public void saveAppointment(@NonNull Appointment appointment,
                                   @NonNull AppointmentsDataSource.SaveAppointmentCallBack callBack) {
        try {
            int index = dbHelper.getAppointmentDao().create(appointment);
            if(index == 1) callBack.onAppointmentSaved();
            else callBack.onAppointmentSavingFailed();
        } catch (java.sql.SQLException e) {
            callBack.onAppointmentSavingFailed();
            e.printStackTrace();
        }
    }

    @Override public void completeAppointment(@NonNull Appointment appointment) {

    }

    @Override public void completeAppointment(@NonNull String appointmentId) {

    }

    @Override public void activateAppointment(@NonNull Appointment appointment) {

    }

    @Override public void activateAppointment(@NonNull String appointmentId) {

    }

    @Override public void clearCompletedAppointments() {

    }

    @Override public void refreshAppointments() {

    }

    @Override public void deleteAllAppointments() {

    }

    @Override public void deleteAppointment(@NonNull String appointmentId) {

    }
}
