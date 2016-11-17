package com.semeniuc.dmitrii.clientmanager.data.local;

import com.semeniuc.dmitrii.clientmanager.App;
import com.semeniuc.dmitrii.clientmanager.model.User;
import com.semeniuc.dmitrii.clientmanager.repository.UserRepository;
import com.semeniuc.dmitrii.clientmanager.utils.Constants;

import java.util.List;

import javax.inject.Inject;

public class DatabaseTaskHelper {

    @Inject User userGlobal;

    public DatabaseTaskHelper(){
        App.getInstance().getComponent().inject(this);
    }

    public User getUserByEmail(String email) {
        UserRepository userRepo = UserRepository.getInstance();
        List<User> users = userRepo.findByEmail(email);
        if (users != null && users.size() > 0) return users.get(0); else return null;
    }

    public User getUserByEmailAndPassword(String email, String password) {
        UserRepository userRepo = UserRepository.getInstance();
        List<User> users = userRepo.findByEmailAndPassword(email, password);
        if (users != null && users.size() > 0) return users.get(0); else return null;
    }

    public Integer saveGoogleUser(User user) {
        UserRepository userRepo = UserRepository.getInstance();
        List<User> users = userRepo.findByEmail(user.getEmail());
        if (null != users) {
            if (users.size() == Constants.SIZE_EMPTY) {
                // CREATE USER
                int index = userRepo.create(user);
                if (index == 1) {
                    userGlobal = userRepo.findByEmail(user.getEmail()).get(0);
                    return Constants.USER_SAVED;
                } else {
                    return Constants.USER_NOT_SAVED;
                }
            } else {
                // USER EXISTS
                userGlobal = users.get(0);
                return Constants.USER_EXISTS;
            }
        } else {
            return Constants.NO_DB_RESULT;
        }
    }

    public Integer saveRegisteredUser(User user) {
        UserRepository userRepo = UserRepository.getInstance();
        int index = userRepo.create(user);
        if (index == 1) {
            List<User> users = userRepo.findByEmail(user.getEmail());
            userGlobal = users.get(0);
            return Constants.USER_SAVED;
        }
        return Constants.USER_NOT_SAVED;
    }

    public Integer setGlobalUser(String email) {
        UserRepository userRepo = UserRepository.getInstance();
        List<User> users = userRepo.findByEmail(email);
        if (null != users) {
            if (users.size() > 0) {
                userGlobal = users.get(0);
                return Constants.USER_SAVED;
            } else {
                return Constants.USER_NOT_SAVED;
            }
        }
        return Constants.NO_DB_RESULT;
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
