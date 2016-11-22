/*
 * Copyright 2016, The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.semeniuc.dmitrii.clientmanager.appointments;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.LinearInterpolator;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.semeniuc.dmitrii.clientmanager.R;
import com.semeniuc.dmitrii.clientmanager.adapter.AppointmentAdapter;
import com.semeniuc.dmitrii.clientmanager.addeditappointment.AddEditAppointmentActivity;
import com.semeniuc.dmitrii.clientmanager.login.LoginActivity;
import com.semeniuc.dmitrii.clientmanager.model.Appointment;

import java.util.List;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Display a grid of {@link com.semeniuc.dmitrii.clientmanager.model.Appointment}s.
 * User can choose to view all, active or close appointments.
 */
public class AppointmentsFragment extends Fragment implements AppointmentsView {

    private AppointmentsPresenter presenter;

    private AppointmentAdapter listAdapter;

    private View noAppointmentsView;

    private ImageView noAppointmentIcon;

    private TextView noAppointmentMainView;

    private TextView noAppointmentAddView;

    private LinearLayout appointmentsView;

    private TextView filteringLabelView;

    private View root;

    public AppointmentsFragment() {
        // Requires empty public constructor
    }

    public static AppointmentsFragment newInstance() {
        return new AppointmentsFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //listAdapter = new AppointmentAdapter(new ArrayList<>(0), itemListener);
    }

    @Override
    public void onResume() {
        super.onResume();
        if (presenter != null) presenter.start();
    }

    @Override
    public void setPresenter(@NonNull AppointmentsPresenterImpl presenter) {
        this.presenter = checkNotNull(presenter);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (presenter != null) presenter.result(requestCode, resultCode);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        root = inflater.inflate(R.layout.fragment_appointments, container, false);

        // Set up appointments view
        /*ListView listView = (ListView) root.findViewById(R.id.appointments_list);
        listView.setAdapter(listAdapter);*/
        filteringLabelView = (TextView) root.findViewById(R.id.filteringLabel);
        appointmentsView = (LinearLayout) root.findViewById(R.id.appointmentsLL);

        // Set up  no appointments view
        noAppointmentsView = root.findViewById(R.id.noAppointments);
        noAppointmentIcon = (ImageView) root.findViewById(R.id.noAppointmentsIcon);
        noAppointmentMainView = (TextView) root.findViewById(R.id.noAppointmentsMain);
        noAppointmentAddView = (TextView) root.findViewById(R.id.noAppointmentsAdd);
        noAppointmentAddView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showAddAppointment();
            }
        });

        // Set up floating action button
        FloatingActionButton fab =
                (FloatingActionButton) getActivity().findViewById(R.id.fab_add_appointment);

        fab.setImageResource(R.mipmap.ic_plus);
        fab.setOnClickListener(v -> {
            if (presenter != null) presenter.addNewAppointment();
        });

        setHasOptionsMenu(true);

        return root;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_clear:
                presenter.clearCompletedAppointments();
                break;
            case R.id.menu_filter:
                showFilteringPopUpMenu();
                break;
            case R.id.menu_refresh:
                presenter.loadAppointments(true);
                break;
        }
        return true;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.appointments_fragment_menu, menu);
    }

    @Override
    public void showFilteringPopUpMenu() {
        PopupMenu popup = new PopupMenu(getContext(), getActivity().findViewById(R.id.menu_filter));
        popup.getMenuInflater().inflate(R.menu.filter_appointments, popup.getMenu());

        popup.setOnMenuItemClickListener(item -> {
            switch (item.getItemId()) {
                case R.id.active:
                    presenter.setFiltering(AppointmentsFilterType.ACTIVE_APPOINTMENTS);
                    break;
                case R.id.completed:
                    presenter.setFiltering(AppointmentsFilterType.COMPLETED_APPOINTMENTS);
                    break;
                default:
                    presenter.setFiltering(AppointmentsFilterType.ALL_APPOINTMENTS);
                    break;
            }
            presenter.loadAppointments(false);
            return true;
        });

        popup.show();
    }

    /**
     * Listener for clicks on appointments in the ListView.
     */
    /*AppointmentItemListener itemListener = new AppointmentItemListener() {
        @Override
        public void onAppointmentClick(Appointment clickedAppointment) {
            if (presenter != null) presenter.openAppointmentDetails(clickedAppointment);
        }

        @Override
        public void onCompleteAppointmentClick(Appointment completedAppointment) {
            if (presenter != null) presenter.completeAppointment(completedAppointment);
        }

        @Override
        public void onActivateAppointmentClick(Appointment activatedAppointment) {
            if (presenter != null) presenter.activateAppointment(activatedAppointment);
        }
    };*/
    @Override
    public void setLoadingIndicator(final boolean active) {

        if (getView() == null) {
            return;
        }
        final SwipeRefreshLayout srl =
                (SwipeRefreshLayout) getView().findViewById(R.id.refresh_layout);

        // Make sure setRefreshing() is called after the layout is done with everything else.
        srl.post(() -> srl.setRefreshing(active));
    }

    private void showNoAppointmentsViews(String mainText, int iconRes, boolean showAddView) {
        appointmentsView.setVisibility(View.GONE);
        noAppointmentsView.setVisibility(View.VISIBLE);

        noAppointmentMainView.setText(mainText);
        noAppointmentIcon.setImageDrawable(getResources().getDrawable(iconRes));
        noAppointmentAddView.setVisibility(showAddView ? View.VISIBLE : View.GONE);
    }

    @Override public void showAppointments(List<Appointment> appointments) {
        //listAdapter.replaceData(appointments);
        RecyclerView recyclerView = getRecyclerView();
        recyclerView.setAdapter(new AppointmentAdapter(appointments,
                appointment -> reviewAppointment(appointment),
                phoneNumber -> callToNumber(phoneNumber)));

        // Set up progress indicator
        final ScrollChildSwipeRefreshLayout swipeRefreshLayout =
                (ScrollChildSwipeRefreshLayout) getActivity().findViewById(R.id.refresh_layout);
        swipeRefreshLayout.setColorSchemeColors(
                ContextCompat.getColor(getActivity(), R.color.colorPrimary),
                ContextCompat.getColor(getActivity(), R.color.colorAccent),
                ContextCompat.getColor(getActivity(), R.color.colorPrimaryDark)
        );
        // Set the scrolling view in the custom SwipeRefreshLayout.
        swipeRefreshLayout.setScrollUpChild(recyclerView);

        swipeRefreshLayout.setOnRefreshListener(() -> presenter.loadAppointments(false));

        appointmentsView.setVisibility(View.VISIBLE);
        noAppointmentsView.setVisibility(View.GONE);
    }

    private void reviewAppointment(Appointment appointment) {
    }

    private void callToNumber(String phoneNumber) {
        AppointmentActivity.phoneNumber = phoneNumber;
        presenter.requestCallPhonePermission(phoneNumber, getContext(), getActivity());
    }

    /**
     * Get Recycler View with itemAnimation and LayoutManager setting
     */
    private RecyclerView getRecyclerView() {
        RecyclerView recyclerView = (RecyclerView) getActivity().findViewById(R.id.appointments_list);
        recyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                FloatingActionButton button = (FloatingActionButton) getActivity().findViewById(R.id.fab_add_appointment);
                if (dy > 0) {
                    CoordinatorLayout.LayoutParams layoutParams =
                            (CoordinatorLayout.LayoutParams) button.getLayoutParams();
                    int fab_bottomMargin = layoutParams.bottomMargin;
                    button.animate().translationY(button.getHeight() +
                            fab_bottomMargin).setInterpolator(new LinearInterpolator()).start();
                } else if (dy < 0)
                    button.animate().translationY(0).setInterpolator(new LinearInterpolator()).start();
            }
        });
        return recyclerView;
    }

    @Override public void showAddAppointment() {
        Intent intent = new Intent(getContext(), AddEditAppointmentActivity.class);
        startActivityForResult(intent, AddEditAppointmentActivity.REQUEST_ADD_APPOINTMENT);
    }

    @Override public void showAppointmentDetailsUi(String appointmentId) {
        // in it's own Activity, since it makes more sense that way and it gives us the flexibility
        // to show some Intent stubbing.
        /*Intent intent = new Intent(getContext(), TaskDetailActivity.class);
        intent.putExtra(TaskDetailActivity.EXTRA_TASK_ID, taskId);
        startActivity(intent);*/
    }

    @Override public void showAppointmentMarkedComplete() {
        showMessage(getString(R.string.appointment_marked_complete));
    }

    @Override public void showAppointmentMarkedActive() {
        showMessage(getString(R.string.appointment_marked_active));
    }

    @Override public void showCompletedAppointmentCleared() {
        showMessage(getString(R.string.completed_appointments_cleared));
    }

    @Override public void showLoadingAppointmentsError() {
        showMessage(getString(R.string.loading_appointments_error));
    }

    @Override public void showCompletedAppointmentsCleared() {

    }

    @Override public void goToLoginActivity() {
        startActivity(new Intent(getContext(), LoginActivity.class));
    }

    @Override public void makeCallToNumber(String phoneNumber) {
        Intent intent = new Intent(Intent.ACTION_CALL, Uri.fromParts("tel", phoneNumber, null));
        startActivity(intent);
    }

    @Override public void showNoAppointments() {
        showNoAppointmentsViews(
                getResources().getString(R.string.no_appointments_all),
                R.drawable.ic_assignment_turned_in_24dp,
                false
        );
    }

    @Override
    public void showSuccessfullySavedMessage() {
        showMessage(getString(R.string.successfully_saved_appointment_message));
    }

    @Override
    public void showActiveFilterLabel() {
        filteringLabelView.setText(getResources().getString(R.string.label_active));
    }

    @Override
    public void showCompletedFilterLabel() {
        filteringLabelView.setText(getResources().getString(R.string.label_completed));
    }

    @Override
    public void showAllFilterLabel() {
        filteringLabelView.setText(getResources().getString(R.string.label_all));
    }

    @Override public void showNoActiveAppointments() {
        showNoAppointmentsViews(
                getResources().getString(R.string.no_appointments_active),
                R.drawable.ic_check_circle_24dp,
                false
        );
    }

    @Override public void showNoCompletedAppointments() {
        showNoAppointmentsViews(
                getResources().getString(R.string.no_appointments_completed),
                R.drawable.ic_verified_user_24dp,
                false
        );
    }

    private void showMessage(String message) {
        Snackbar.make(getView(), message, Snackbar.LENGTH_LONG).show();
    }

    @Override
    public boolean isActive() {
        return isAdded();
    }

/*    private static class AppointmentAdapter extends BaseAdapter {

        private List<Appointment> appointments;
        private AppointmentItemListener itemListener;

        public AppointmentAdapter(List<Appointment> appointments, AppointmentItemListener itemListener) {
            setList(appointments);
            this.itemListener = itemListener;
        }

        public void replaceData(List<Appointment> appointments) {
            setList(appointments);
            notifyDataSetChanged();
        }

        private void setList(List<Appointment> appointments) {
            this.appointments = checkNotNull(appointments);
        }

        @Override
        public int getCount() {
            return appointments.size();
        }

        @Override
        public Appointment getItem(int i) {
            return appointments.get(i);
        }

        @Override
        public long getItemId(int i) {
            return i;
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {
            View rowView = view;
            if (rowView == null) {
                LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
                rowView = inflater.inflate(R.layout.appointment_item, viewGroup, false);
            }

            final Appointment appointment = getItem(i);

            TextView titleTV = (TextView) rowView.findViewById(R.id.title);
            titleTV.setText("Mock title");

            CheckBox completeCB = (CheckBox) rowView.findViewById(R.id.complete);

            // Active/completed task UI
            completeCB.setChecked(appointment.isCompleted());
            if (appointment.isCompleted()) {
                rowView.setBackgroundDrawable(viewGroup.getContext()
                        .getResources().getDrawable(R.drawable.list_completed_touch_feedback));
            } else {
                rowView.setBackgroundDrawable(viewGroup.getContext()
                        .getResources().getDrawable(R.drawable.touch_feedback));
            }

            completeCB.setOnClickListener(v -> {
                if (!appointment.isCompleted()) {
                    itemListener.onCompleteAppointmentClick(appointment);
                } else {
                    itemListener.onActivateAppointmentClick(appointment);
                }
            });

            rowView.setOnClickListener(view1 -> itemListener.onAppointmentClick(appointment));

            return rowView;
        }
    }*/

    public interface AppointmentItemListener {

        void onAppointmentClick(Appointment clickedAppointment);

        void onCompleteAppointmentClick(Appointment completedAppointment);

        void onActivateAppointmentClick(Appointment activatedAppointment);
    }

    @Override public void onDestroyView() {
        super.onDestroyView();
        presenter = null;
    }
}
