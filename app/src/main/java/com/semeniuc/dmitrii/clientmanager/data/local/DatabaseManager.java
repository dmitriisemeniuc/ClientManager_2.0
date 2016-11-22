package com.semeniuc.dmitrii.clientmanager.data.local;

import com.semeniuc.dmitrii.clientmanager.App;
import com.semeniuc.dmitrii.clientmanager.model.User;
import com.semeniuc.dmitrii.clientmanager.repository.UserRepository;
import com.semeniuc.dmitrii.clientmanager.utils.Constants;

import java.util.List;

import javax.inject.Inject;

public class DatabaseManager implements UserDataSource {

    @Inject User userGlobal;
    @Inject UserRepository userRepository;

    public DatabaseManager() {
        App.getInstance().getComponent().inject(this);
    }

    @Override public User getUserByEmail(String email) {
        List<User> users = userRepository.findByEmail(email);
        if (users != null && users.size() > 0) return users.get(0);
        else return null;
    }

    @Override public User getUserByEmailAndPassword(String email, String password) {
        List<User> users = userRepository.findByEmailAndPassword(email, password);
        if (users != null && users.size() > 0) return users.get(0);
        else return null;
    }

    @Override public Integer saveGoogleUser(User user) {
        List<User> users = userRepository.findByEmail(user.getEmail());
        if (null == users) return Constants.NO_DB_RESULT;
        if (users.size() == Constants.SIZE_EMPTY) {
            // CREATE USER
            int index = userRepository.create(user);
            if (index == 1) {
                userGlobal = userRepository.findByEmail(user.getEmail()).get(0);
                return Constants.USER_SAVED;
            }
            return Constants.USER_NOT_SAVED;
        } else {
            // USER EXISTS
            userGlobal = users.get(0);
            return Constants.USER_EXISTS;
        }
    }

    @Override public Integer saveRegisteredUser(User user) {
        int index = userRepository.create(user);
        if (index == 1) {
            List<User> users = userRepository.findByEmail(user.getEmail());
            userGlobal = users.get(0);
            return Constants.USER_SAVED;
        }
        return Constants.USER_NOT_SAVED;
    }

    @Override public Integer setGlobalUserWithEmail(String email) {
        List<User> users = userRepository.findByEmail(email);
        if (null == users) return Constants.NO_DB_RESULT;
        if (users.size() > 0) {
            userGlobal = users.get(0);
            return Constants.USER_SAVED;
        }
        return Constants.USER_NOT_SAVED;
    }

    /*public Integer saveAppointment(Appointment appointment){
        *//*ServiceRepository serviceRepo = ServiceRepository.getInstance();
        serviceRepo.create(appointment.getService());
        ToolsRepository toolsRepo = ToolsRepository.getInstance();
        toolsRepo.create(appointment.getTools());
        ContactRepository contactRepo = ContactRepository.getInstance();
        contactRepo.create(appointment.getClient().getContact());
        ClientRepository clientRepo = ClientRepository.getInstance();
        clientRepo.create(appointment.getClient());
        AppointmentRepository appointmentRepo = AppointmentRepository.getInstance();
        return appointmentRepo.create(appointment);*//*
    }*/

    /*public Integer updateAppointment(Appointment appointment){
        *//*ServiceRepository serviceRepo = ServiceRepository.getInstance();
        serviceRepo.update(appointment.getService());
        ToolsRepository toolsRepo = ToolsRepository.getInstance();
        toolsRepo.update(appointment.getTools());
        ContactRepository contactRepo = ContactRepository.getInstance();
        contactRepo.update(appointment.getClient().getContact());
        ClientRepository clientRepo = ClientRepository.getInstance();
        clientRepo.update(appointment.getClient());
        appointment.setUser(MyApplication.getInstance().getUser());
        AppointmentRepository appointmentRepo = AppointmentRepository.getInstance();
        return appointmentRepo.update(appointment);*//*
    }*/

    /*public Integer deleteAppointment(Appointment appointment){
        *//*ServiceRepository serviceRepo = ServiceRepository.getInstance();
        serviceRepo.delete(appointment.getService());
        ToolsRepository toolsRepo = ToolsRepository.getInstance();
        toolsRepo.delete(appointment.getTools());
        ContactRepository contactRepo = ContactRepository.getInstance();
        contactRepo.delete(appointment.getClient().getContact());
        ClientRepository clientRepo = ClientRepository.getInstance();
        clientRepo.delete(appointment.getClient());
        AppointmentRepository appointmentRepo = AppointmentRepository.getInstance();
        return appointmentRepo.delete(appointment);*//*
    }*/

    /*public List<Appointment> getAppointmentsOrderedByDate(){
        *//*AppointmentRepository appointmentRepo = AppointmentRepository.getInstance();
        List<Appointment> appointments = appointmentRepo.findAllOrderedByDate();
        if (appointments != null) {
            DatabaseHelper helper = new DatabaseHelper(MyApplication.getInstance().getApplicationContext());
            for (Appointment appointment : appointments) {
                try {
                    helper.getClientDao().refresh(appointment.getClient());
                    helper.getContactDao().refresh(appointment.getClient().getContact());
                    helper.getServiceDao().refresh(appointment.getService());
                    helper.getToolsDao().refresh(appointment.getTools());
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
        return appointments;*//*
    }*/

    /*public List<Appointment> getAppointmentsOrderedByClient(){
        *//*AppointmentRepository appointmentRepo = AppointmentRepository.getInstance();
        List<Appointment> appointments = appointmentRepo.findAllOrderedByClient();
        if (appointments != null) {
            DatabaseHelper helper = new DatabaseHelper(MyApplication.getInstance().getApplicationContext());
            for (Appointment appointment : appointments) {
                try {
                    helper.getClientDao().refresh(appointment.getClient());
                    helper.getContactDao().refresh(appointment.getClient().getContact());
                    helper.getServiceDao().refresh(appointment.getService());
                    helper.getToolsDao().refresh(appointment.getTools());
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
        return appointments;*//*
    }*/

    /*public List<Appointment> getDoneAndPaidAppointmentsOrderedByDate(){
        *//*AppointmentRepository appointmentRepo = AppointmentRepository.getInstance();
        List<Appointment> appointments = appointmentRepo.findDoneAndPaidOrderedByDate();
        if (appointments != null) {
            DatabaseHelper helper = new DatabaseHelper(MyApplication.getInstance().getApplicationContext());
            for (Appointment appointment : appointments) {
                try {
                    helper.getClientDao().refresh(appointment.getClient());
                    helper.getContactDao().refresh(appointment.getClient().getContact());
                    helper.getServiceDao().refresh(appointment.getService());
                    helper.getToolsDao().refresh(appointment.getTools());
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
        return appointments;*//*
    }*/

    /*public List<Appointment> getDoneAndPaidAppointmentsOrderedByClient(){
        *//*AppointmentRepository appointmentRepo = AppointmentRepository.getInstance();
        List<Appointment> appointments = appointmentRepo.findDoneAndPaidOrderedByClient();
        if (appointments != null) {
            DatabaseHelper helper = new DatabaseHelper(MyApplication.getInstance().getApplicationContext());
            for (Appointment appointment : appointments) {
                try {
                    helper.getClientDao().refresh(appointment.getClient());
                    helper.getContactDao().refresh(appointment.getClient().getContact());
                    helper.getServiceDao().refresh(appointment.getService());
                    helper.getToolsDao().refresh(appointment.getTools());
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
        return appointments;*//*
    }*/
}
