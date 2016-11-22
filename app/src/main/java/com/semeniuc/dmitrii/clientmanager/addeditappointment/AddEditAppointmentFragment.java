package com.semeniuc.dmitrii.clientmanager.addeditappointment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.AppCompatEditText;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.AppCompatTextView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ScrollView;

import com.semeniuc.dmitrii.clientmanager.App;
import com.semeniuc.dmitrii.clientmanager.R;
import com.semeniuc.dmitrii.clientmanager.fragment.DateDialogFragment;
import com.semeniuc.dmitrii.clientmanager.fragment.TimeDialogFragment;
import com.semeniuc.dmitrii.clientmanager.model.Appointment;
import com.semeniuc.dmitrii.clientmanager.model.Tools;
import com.semeniuc.dmitrii.clientmanager.utils.ActivityUtils;
import com.semeniuc.dmitrii.clientmanager.utils.Constants;

import java.util.Calendar;
import java.util.Date;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class AddEditAppointmentFragment extends Fragment implements AddEditAppointmentContract.View{

    @Inject ActivityUtils utils;

    private Appointment appointment;
    private AddEditAppointmentContract.Presenter presenter;
    private View root;

    @BindView(R.id.appointment_client_name)
    AppCompatEditText editTextClientName;
    @BindView(R.id.appointment_client_phone)
    AppCompatEditText editTextClientPhone;
    @BindView(R.id.appointment_client_address)
    AppCompatEditText editTextAddress;
    @BindView(R.id.appointment_service)
    AppCompatEditText editTextService;
    @BindView(R.id.appointment_info)
    AppCompatEditText editTextInfo;
    @BindView(R.id.appointment_calendar_date)
    AppCompatTextView textViewDate;
    @BindView(R.id.appointment_time)
    AppCompatTextView textViewTime;
    @BindView(R.id.appointment_cash)
    AppCompatEditText editTextSum;
    @BindView(R.id.appointment_paid_icon)
    AppCompatImageView imageViewPaid;
    @BindView(R.id.appointment_done_icon)
    AppCompatImageView imageViewDone;
    @BindView(R.id.appointment_service_hair_coloring_icon)
    AppCompatImageView imageViewHairColoring;
    @BindView(R.id.appointment_service_hairdo_icon)
    AppCompatImageView imageViewHairdo;
    @BindView(R.id.appointment_service_haircut_icon)
    AppCompatImageView imageViewHaircut;
    @BindView(R.id.appointment_tools_brush_icon)
    AppCompatImageView imageViewBrush;
    @BindView(R.id.appointment_tools_hair_brush_icon)
    AppCompatImageView imageViewHairBrush;
    @BindView(R.id.appointment_tools_hair_dryer_icon)
    AppCompatImageView imageViewHairDryer;
    @BindView(R.id.appointment_tools_oxy_icon)
    AppCompatImageView imageViewOxy;
    @BindView(R.id.appointment_tools_cut_set_icon)
    AppCompatImageView imageViewCutSet;
    @BindView(R.id.appointment_tools_hair_band_icon)
    AppCompatImageView imageViewHairBand;
    @BindView(R.id.appointment_tools_spray_icon)
    AppCompatImageView imageViewSpray;
    @BindView(R.id.appointment_tools_tube_icon)
    AppCompatImageView imageViewTube;
    @BindView(R.id.appointment_tools_trimmer_icon)
    AppCompatImageView imageViewTrimmer;
    @BindView(R.id.appointment_layout)
    ScrollView mainLayout;

    @OnClick(R.id.appointment_calendar_icon)
    void onCalendarIconClicked() {
        utils.hideKeyboard(mainLayout);
        showDatePickerDialog(Calendar.getInstance());
    }

    @OnClick(R.id.appointment_calendar_date)
    void onCalendarDateClicked() {
        utils.hideKeyboard(mainLayout);
        showDatePickerDialog(Calendar.getInstance());
    }

    @OnClick(R.id.appointment_time_icon)
    void onClockIconClicked() {
        utils.hideKeyboard(mainLayout);
        showTimePickerDialog(Calendar.getInstance());
    }

    @OnClick(R.id.appointment_time)
    void onClockClicked() {
        utils.hideKeyboard(mainLayout);
        showTimePickerDialog(Calendar.getInstance());
    }

    @OnClick(R.id.appointment_paid_icon)
    void onPaidIconClicked() {
        appointment.setPaid(!appointment.isPaid());
        utils.changeImage(Constants.PAID, appointment, imageViewPaid);
    }

    @OnClick(R.id.appointment_done_icon)
    void onDoneIconClicked() {
        appointment.setCompleted(!appointment.isCompleted());
        utils.changeImage(Constants.DONE, appointment, imageViewDone);
    }

    @OnClick(R.id.appointment_service_hair_coloring_icon)
    void onHairColoringIconClicked() {
        appointment.getService().setHairColoring(!appointment.getService().isHairColoring());
        utils.changeImage(Constants.HAIR_COLORING, appointment, imageViewHairColoring);
    }

    @OnClick(R.id.appointment_service_hairdo_icon)
    void onHairdoIconClicked() {
        appointment.getService().setHairdo(!appointment.getService().isHairdo());
        utils.changeImage(Constants.HAIRDO, appointment, imageViewHairdo);
    }

    @OnClick(R.id.appointment_service_haircut_icon)
    void onHaircutIconClicked() {
        appointment.getService().setHaircut(!appointment.getService().isHaircut());
        utils.changeImage(Constants.HAIR_CUT, appointment, imageViewHaircut);
    }

    @OnClick(R.id.appointment_tools_brush_icon)
    void onBrushIconClicked() {
        appointment.getTools().setBrush(!appointment.getTools().isBrush());
        utils.changeImage(Constants.BRUSH, appointment, imageViewBrush);
    }

    @OnClick(R.id.appointment_tools_hair_brush_icon)
    void onHairBrushIconClicked() {
        appointment.getTools().setHairBrush(!appointment.getTools().isHairBrush());
        utils.changeImage(Constants.HAIR_BRUSH, appointment, imageViewHairBrush);
    }

    @OnClick(R.id.appointment_tools_hair_dryer_icon)
    void onHairDryerIconClicked() {
        appointment.getTools().setHairDryer(!appointment.getTools().isHairDryer());
        utils.changeImage(Constants.HAIR_DRAYER, appointment, imageViewHairDryer);
    }

    @OnClick(R.id.appointment_tools_oxy_icon)
    void onOxyIconClicked() {
        appointment.getTools().setOxy(!appointment.getTools().isOxy());
        utils.changeImage(Constants.OXY, appointment, imageViewOxy);
    }

    @OnClick(R.id.appointment_tools_cut_set_icon)
    void onCutSetIconClicked() {
        appointment.getTools().setCutSet(!appointment.getTools().isCutSet());
        utils.changeImage(Constants.CUT_SET, appointment, imageViewCutSet);
    }

    @OnClick(R.id.appointment_tools_hair_band_icon)
    void onHairBandIconClicked() {
        appointment.getTools().setHairBand(!appointment.getTools().isHairBand());
        utils.changeImage(Constants.HAIR_BAND, appointment, imageViewHairBand);
    }

    @OnClick(R.id.appointment_tools_spray_icon)
    void onSprayIconClicked() {
        appointment.getTools().setSpray(!appointment.getTools().isSpray());
        utils.changeImage(Constants.SPRAY, appointment, imageViewSpray);
    }

    @OnClick(R.id.appointment_tools_tube_icon)
    void onTubeClicked() {
        appointment.getTools().setTube(!appointment.getTools().isTube());
        utils.changeImage(Constants.TUBE, appointment, imageViewTube);
    }

    @OnClick(R.id.appointment_tools_trimmer_icon)
    void onTrimmerIconClicked() {
        appointment.getTools().setTrimmer(!appointment.getTools().isTrimmer());
        utils.changeImage(Constants.TRIMMER, appointment, imageViewTrimmer);
    }

    public static AddEditAppointmentFragment newInstance() {
        return new AddEditAppointmentFragment();
    }

    @Override public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        App.getInstance().getComponent().inject(this); // Dagger
        Bundle bundle = this.getArguments();
        if (bundle != null) {
            appointment = bundle.getParcelable(Constants.APPOINTMENT_PATH);
        }
    }

    @Override public void setPresenter(AddEditAppointmentContract.Presenter presenter) {
        this.presenter = presenter;
    }

    @Override
    public void onResume() {
        super.onResume();
        presenter.start();
    }

    @Nullable @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ButterKnife.bind(getActivity()); // Butterknife
        root = inflater.inflate(R.layout.addeditappointment_fragment, container, false);
        /*mTitle = (TextView) root.findViewById(R.id.add_task_title);
        mDescription = (TextView) root.findViewById(R.id.add_task_description);*/
        setHasOptionsMenu(true);
        setRetainInstance(true);
        return root;
    }

    /**
     * Open time picker
     */
    private void showDatePickerDialog(Calendar calendar) {
        DateDialogFragment dateDialogFragment = DateDialogFragment.newInstance(getContext(), calendar);
        dateDialogFragment.setDateDialogFragmentListener(date1 -> {
            String formattedDate = utils.getCorrectDateFormat(
                    date1.get(Calendar.YEAR),
                    date1.get(Calendar.MONTH),
                    date1.get(Calendar.DAY_OF_MONTH));
            textViewDate.setText(formattedDate);
            textViewDate.setError(null);
        });
        dateDialogFragment.show(getActivity().getSupportFragmentManager(), "date picker dialog fragment");
    }

    /**
     * Open time picker dialog
     */
    private void showTimePickerDialog(Calendar calendar) {
        TimeDialogFragment timeDialogFragment = TimeDialogFragment.newInstance(getContext(), calendar);
        timeDialogFragment.setTimeDialogFragmentListener(time1 -> {
            String formattedTime = utils.getCorrectTimeFormat(
                    time1.get(Calendar.HOUR_OF_DAY),
                    time1.get(Calendar.MINUTE));
            textViewTime.setText(formattedTime);
            textViewTime.setError(null);
        });
        timeDialogFragment.show(getActivity().getSupportFragmentManager(), "time picker dialog fragment");
    }

    @Override public boolean isActive() {
        return isAdded();
    }

    @Override public void setClient(String client) {
        editTextClientName.setText(client);
    }

    @Override public void setService(String service) {
        editTextService.setText(service);
    }

    @Override public void setTools(Tools tools) {

    }

    @Override public void setInfo(String info) {

    }

    @Override public void setDate(Date date) {

    }

    @Override public void setSum(String sum) {

    }

    @Override public void setPaid(boolean paid) {

    }

    @Override public void setCompleted(boolean done) {

    }

    @Override public void setPhone(String phone) {
        editTextClientPhone.setText(phone);
    }

    @Override public void setAddress(String address) {
        editTextAddress.setText(address);
    }

    @Override public void showAppointmentSavingFailedMessage() {

    }

    @Override public void showAppointmentSavedMessage() {

    }

    @Override public void showDataNotAvailableMessage() {

    }
}
