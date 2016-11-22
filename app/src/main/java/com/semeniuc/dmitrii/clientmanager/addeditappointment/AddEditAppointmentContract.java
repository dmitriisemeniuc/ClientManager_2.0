package com.semeniuc.dmitrii.clientmanager.addeditappointment;

import com.semeniuc.dmitrii.clientmanager.BasePresenter;
import com.semeniuc.dmitrii.clientmanager.BaseView;
import com.semeniuc.dmitrii.clientmanager.model.Tools;

import java.util.Date;

public interface AddEditAppointmentContract {

    interface View extends BaseView<Presenter> {

        boolean isActive();

        void setClient(String client);

        void setService(String service);

        void setTools(Tools tools);

        void setInfo(String info);

        void setDate(Date date);

        void setSum(String sum);

        void setPaid(boolean paid);

        void setCompleted(boolean done);

        void setPhone(String clientContactPhone);

        void setAddress(String clientContactAddress);

        void showAppointmentSavingFailedMessage();

        void showAppointmentSavedMessage();

        void showDataNotAvailableMessage();
    }

    interface Presenter extends BasePresenter {
     //   void saveTask(String title, String description);

        void populateAppointment();
    }
}
