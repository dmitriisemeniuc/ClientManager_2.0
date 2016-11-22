package com.semeniuc.dmitrii.clientmanager.repository;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.semeniuc.dmitrii.clientmanager.data.local.AppointmentsDataSource;
import com.semeniuc.dmitrii.clientmanager.data.local.AppointmentsLocalDataSource;
import com.semeniuc.dmitrii.clientmanager.data.local.DatabaseHelper;
import com.semeniuc.dmitrii.clientmanager.model.Appointment;
import com.semeniuc.dmitrii.clientmanager.utils.Constants;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import static com.google.common.base.Preconditions.checkNotNull;

public final class AppointmentRepository implements AppointmentsDataSource{

    public static final String LOG_TAG = AppointmentRepository.class.getSimpleName();
    public static final boolean DEBUG = Constants.DEBUG;

    private DatabaseHelper dbHelper;
    private AppointmentsLocalDataSource localDataSource;

    /**
     * This variable has package local visibility so it can be accessed from tests.
     */
    Map<Integer, Appointment> cachedAppointments;

    /**
     * Marks the cache as invalid, to force an update the next time data is requested. This variable
     * has package local visibility so it can be accessed from tests.
     */
    boolean cacheIsDirty = false;

    public AppointmentRepository(DatabaseHelper dbHelper, AppointmentsLocalDataSource lDataSource){
        this.dbHelper = dbHelper;
        localDataSource = lDataSource;
    }

    public void getAppointments(@NonNull AppointmentsDataSource.LoadAppointmentsCallback callback) {
        checkNotNull(callback);

        // Respond immediately with cache if available and not dirty
        if (cachedAppointments != null && !cacheIsDirty) {
            callback.onAppointmentsLoaded(new ArrayList<>(cachedAppointments.values()));
            return;
        }

        if (cacheIsDirty) {
            // If the cache is dirty we need to fetch new data from the network.
            //getTasksFromRemoteDataSource(callback);
        } else {
            // Query the local storage if available. If not, query the network.
            localDataSource.getAppointments(new LoadAppointmentsCallback() {
                @Override
                public void onAppointmentsLoaded(List<Appointment> appointments) {
                    refreshCache(appointments);
                    callback.onAppointmentsLoaded(new ArrayList<>(cachedAppointments.values()));
                }

                @Override
                public void onDataNotAvailable() {
                   // getAppointmentsFromRemoteDataSource(callback);
                }
            });
        }
    }

    private void refreshCache(List<Appointment> appointments) {
        if (cachedAppointments == null) {
            cachedAppointments = new LinkedHashMap<>();
        }
        cachedAppointments.clear();
        for (Appointment appointment : appointments) {
            cachedAppointments.put(appointment.getId(), appointment);
        }
        cacheIsDirty = false;
    }

    public void getAppointment(@NonNull Integer appointmentId,
                               @NonNull AppointmentsDataSource.GetAppointmentCallback callback) {
        checkNotNull(appointmentId);
        checkNotNull(callback);

        Appointment cachedAppointment = getAppointmentWithId(appointmentId);

        // Respond immediately with cache if available
        if (cachedAppointment != null) {
            callback.onAppointmentLoaded(cachedAppointment);
            return;
        }

        // Load from server/persisted if needed.

        // Is the appointment in the local data source? If not, query the network.
        localDataSource.getAppointment(appointmentId, new GetAppointmentCallback() {
            @Override
            public void onAppointmentLoaded(Appointment appointment) {
                // Do in memory cache update to keep the app UI up to date
                if (cachedAppointments == null) {
                    cachedAppointments = new LinkedHashMap<>();
                }
                cachedAppointments.put(appointment.getId(), appointment);
                callback.onAppointmentLoaded(appointment);
            }

            @Override
            public void onDataNotAvailable() {
                // Here we can get data from network
                callback.onDataNotAvailable();
            }
        });
    }

    @Nullable
    private Appointment getAppointmentWithId(Integer appointmentId) {
        if (cachedAppointments == null || cachedAppointments.isEmpty()) {
            return null;
        } else {
            return cachedAppointments.get(appointmentId);
        }
    }

    @Override
    public void saveAppointment(@NonNull Appointment appointment, @NonNull SaveAppointmentCallBack callback) {
        checkNotNull(appointment);
        localDataSource.saveAppointment(appointment, callback);
        // Do in memory cache update to keep the app UI up to date
        if (cachedAppointments == null) {
            cachedAppointments = new LinkedHashMap<>();
        }
        cachedAppointments.put(appointment.getId(), appointment);
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
        cacheIsDirty = true;
    }

    @Override public void deleteAllAppointments() {

    }

    @Override public void deleteAppointment(@NonNull String appointmentId) {

    }

    /*private static final AppointmentRepository instance = new AppointmentRepository();
    public static final String LOG_TAG = AppointmentRepository.class.getSimpleName();
    public static final boolean DEBUG = Constants.DEBUG;
    private static final DatabaseHelper dbHelper;

    static{
        DatabaseManager.init(MyApplication.getInstance().getApplicationContext());
        dbHelper = DatabaseManager.getInstance().getHelper();
    }

    private AppointmentRepository() {
    }

    public static AppointmentRepository getInstance() {
        return instance;
    }

    @Override
    public int create(Object item) {
        int index = -1;
        Appointment appointment = (Appointment) item;
        try {
            index = dbHelper.getAppointmentDao().create(appointment);
            if (DEBUG) Log.i(LOG_TAG, "created: " + index);
        } catch (java.sql.SQLException e) {
            e.printStackTrace();
        }
        return index;
    }

    @Override
    public int update(Object item) {
        int index = -1;
        Appointment appointment = (Appointment) item;
        try {
            Dao<Appointment, Integer> appointmentDAO = dbHelper.getAppointmentDao();
            QueryBuilder<Appointment, Integer> queryBuilder = appointmentDAO.queryBuilder();
            queryBuilder.where().eq(Appointment.ID_FIELD_NAME, appointment.getId());
            PreparedQuery<Appointment> preparedQuery = queryBuilder.prepare();
            Appointment appointmentEntry = appointmentDAO.queryForFirst(preparedQuery);
            appointmentEntry = new Utils().updateAppointmentData(appointment, appointmentEntry);
            index = appointmentDAO.update(appointmentEntry);
            if (DEBUG) Log.i(LOG_TAG, "updated: " + index);
        } catch (java.sql.SQLException e) {
            e.printStackTrace();
        }
        return index;
    }

    @Override
    public int delete(Object item) {
        int index = -1;
        Appointment appointment = (Appointment) item;
        try {
            index = dbHelper.getAppointmentDao().delete(appointment);
            if (DEBUG) Log.i(LOG_TAG, "deleted: " + index);
        } catch (java.sql.SQLException e) {
            e.printStackTrace();
        }
        return index;
    }

    @Override
    public Object findById(int id) {
        Appointment appointment = null;
        try {
            appointment = dbHelper.getAppointmentDao().queryForId(id);
        } catch (java.sql.SQLException e) {
            e.printStackTrace();
        }
        return appointment;
    }

    @Override
    public List<Appointment> findAll() {
        List<Appointment> items = null;
        long id = MyApplication.getInstance().getUser().getId();
        try {
            Dao<Appointment, Integer> appointmentDAO = dbHelper.getAppointmentDao();
            QueryBuilder<Appointment, Integer> queryBuilder = appointmentDAO.queryBuilder();
            Where<Appointment, Integer> where = queryBuilder.where();
            where.eq(Appointment.USER_FIELD_NAME, id);
            queryBuilder.orderBy(Appointment.CLIENT_FIELD_NAME, true); // true for ascending
            items = appointmentDAO.query(queryBuilder.prepare());
        } catch (java.sql.SQLException e) {
            e.printStackTrace();
        }
        return items;
    }

    public List<Appointment> findAllOrderedByDate() {
        List<Appointment> items = null;
        long id = MyApplication.getInstance().getUser().getId();
        try {
            Dao<Appointment, Integer> appointmentDAO = dbHelper.getAppointmentDao();
            QueryBuilder<Appointment, Integer> queryBuilder = appointmentDAO.queryBuilder();
            Where<Appointment, Integer> where = queryBuilder.where();
            where.or(
                    where.and(
                            where.eq(Appointment.USER_FIELD_NAME, id),
                            where.eq(Appointment.DONE_FIELD_NAME, false),
                            where.eq(Appointment.PAID_FIELD_NAME, true)),
                    where.and(
                            where.eq(Appointment.USER_FIELD_NAME, id),
                            where.eq(Appointment.DONE_FIELD_NAME, true),
                            where.eq(Appointment.PAID_FIELD_NAME, false)),
                    where.and(
                            where.eq(Appointment.USER_FIELD_NAME, id),
                            where.eq(Appointment.DONE_FIELD_NAME, false),
                            where.eq(Appointment.PAID_FIELD_NAME, false)));
            queryBuilder.orderBy(Appointment.DATE_FIELD_NAME, true);
            items = appointmentDAO.query(queryBuilder.prepare());
        } catch (java.sql.SQLException e) {
            e.printStackTrace();
        }
        return items;
    }

    public List<Appointment> findAllOrderedByClient() {
        List<Appointment> items = null;
        long id = MyApplication.getInstance().getUser().getId();
        try {
            Dao<Appointment, Integer> appointmentDAO = dbHelper.getAppointmentDao();
            QueryBuilder<Appointment, Integer> queryBuilder = appointmentDAO.queryBuilder();
            Where<Appointment, Integer> where = queryBuilder.where();
            where.or(
                    where.and(
                            where.eq(Appointment.USER_FIELD_NAME, id),
                            where.eq(Appointment.DONE_FIELD_NAME, false),
                            where.eq(Appointment.PAID_FIELD_NAME, true)),
                    where.and(
                            where.eq(Appointment.USER_FIELD_NAME, id),
                            where.eq(Appointment.DONE_FIELD_NAME, true),
                            where.eq(Appointment.PAID_FIELD_NAME, false)),
                    where.and(
                            where.eq(Appointment.USER_FIELD_NAME, id),
                            where.eq(Appointment.DONE_FIELD_NAME, false),
                            where.eq(Appointment.PAID_FIELD_NAME, false)));
            queryBuilder.orderBy(Appointment.CLIENT_FIELD_NAME, true);
            items = appointmentDAO.query(queryBuilder.prepare());
        } catch (java.sql.SQLException e) {
            e.printStackTrace();
        }
        return items;
    }

    public List<Appointment> findDoneAndPaidOrderedByDate() {
        List<Appointment> items = null;
        long id = MyApplication.getInstance().getUser().getId();
        try {
            Dao<Appointment, Integer> appointmentDAO = dbHelper.getAppointmentDao();
            QueryBuilder<Appointment, Integer> queryBuilder = appointmentDAO.queryBuilder();
            Where<Appointment, Integer> where = queryBuilder.where();
            where.and(
                    where.eq(Appointment.USER_FIELD_NAME, id),
                    where.eq(Appointment.DONE_FIELD_NAME, true),
                    where.eq(Appointment.PAID_FIELD_NAME, true));
            queryBuilder.orderBy(Appointment.DATE_FIELD_NAME, true); // true for ascending
            items = appointmentDAO.query(queryBuilder.prepare());
        } catch (java.sql.SQLException e) {
            e.printStackTrace();
        }
        return items;
    }

    public List<Appointment> findDoneAndPaidOrderedByClient() {
        List<Appointment> items = null;
        long id = MyApplication.getInstance().getUser().getId();
        try {
            Dao<Appointment, Integer> appointmentDAO = dbHelper.getAppointmentDao();
            QueryBuilder<Appointment, Integer> queryBuilder = appointmentDAO.queryBuilder();
            Where<Appointment, Integer> where = queryBuilder.where();
            where.and(
                    where.eq(Appointment.USER_FIELD_NAME, id),
                    where.eq(Appointment.DONE_FIELD_NAME, true),
                    where.eq(Appointment.PAID_FIELD_NAME, true));
            queryBuilder.orderBy(Appointment.CLIENT_FIELD_NAME, true); // true for ascending
            items = appointmentDAO.query(queryBuilder.prepare());
        } catch (java.sql.SQLException e) {
            e.printStackTrace();
        }
        return items;
    }*/
}
