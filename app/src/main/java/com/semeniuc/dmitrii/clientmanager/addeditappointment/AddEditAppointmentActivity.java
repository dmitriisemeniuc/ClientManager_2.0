package com.semeniuc.dmitrii.clientmanager.addeditappointment;

import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import com.semeniuc.dmitrii.clientmanager.R;
import com.semeniuc.dmitrii.clientmanager.model.Appointment;
import com.semeniuc.dmitrii.clientmanager.utils.ActivityUtils;
import com.semeniuc.dmitrii.clientmanager.utils.Constants;

public class AddEditAppointmentActivity extends AppCompatActivity {

    public static final int REQUEST_ADD_APPOINTMENT = 1;
    private Appointment appointment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.addeditappointment_activity);
        initToolbar();

        AddEditAppointmentFragment addEditAppointmentFragment =
                (AddEditAppointmentFragment) getSupportFragmentManager().findFragmentById(R.id.contentFrame);

        if(getIntent().hasExtra(Constants.TYPE)){
            String type = getIntent().getExtras().getString(Constants.TYPE);
            if(Constants.EDIT_APPOINTMENT.equals(type)){
                appointment = getIntent().getExtras().getParcelable(Constants.APPOINTMENT_PATH);
            }
        }

        if (addEditAppointmentFragment == null) {
            addEditAppointmentFragment = AddEditAppointmentFragment.newInstance();

            if (appointment != null) {
                Bundle bundle = new Bundle();
                bundle.putParcelable(Constants.APPOINTMENT_PATH, appointment);
                addEditAppointmentFragment.setArguments(bundle);
            }

            ActivityUtils.addFragmentToActivity(getSupportFragmentManager(),
                    addEditAppointmentFragment, R.id.contentFrame);
        }

        // Create the presenter
      new AddEditAppointmentPresenter(addEditAppointmentFragment);
    }

    private void initToolbar() {
        // Set up the toolbar.
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setDisplayShowHomeEnabled(true);
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}
