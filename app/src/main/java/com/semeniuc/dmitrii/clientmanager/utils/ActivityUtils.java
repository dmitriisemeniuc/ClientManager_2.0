package com.semeniuc.dmitrii.clientmanager.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.support.v7.widget.AppCompatEditText;
import android.support.v7.widget.AppCompatTextView;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;

import com.semeniuc.dmitrii.clientmanager.R;
import com.semeniuc.dmitrii.clientmanager.model.Appointment;
import com.semeniuc.dmitrii.clientmanager.model.User;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class ActivityUtils implements Utils.Activity {

    public Context context;

    public ActivityUtils(Context context) {
        this.context = context;
    }

    public String getMessage() {
        return "Hello from Utils";
    }

    public boolean isValidEditText(AppCompatEditText editText) {
        boolean empty = isEditTextEmpty(editText);
        if (empty) editText.setError(context.getResources().getString(R.string.field_is_required));
        return !empty;
    }

    public boolean isValidTextView(AppCompatTextView textView) {
        boolean empty = isTextViewEmpty(textView);
        if (empty) textView.setError(context.getResources().getString(R.string.field_is_required));
        return !empty;
    }

    public boolean isEditTextEmpty(AppCompatEditText et) {
        return et.getText().toString().isEmpty();
    }

    public boolean isTextViewEmpty(AppCompatTextView tv) {
        return tv.getText().toString().isEmpty();
    }

    public String convertDateToString(Date date, String pattern, Context context) {
        Locale locale = getLocale(context);
        SimpleDateFormat dateFormat = new SimpleDateFormat(pattern, locale);
        return dateFormat.format(date);
    }

    public Date convertStringToDate(String dateString, String pattern) {
        Locale locale = getLocale(context);
        SimpleDateFormat format = new SimpleDateFormat(pattern, locale);
        Date date = null;
        try {
            date = format.parse(dateString);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date;
    }

    public Calendar getDateForDialog(String date) {
        final Calendar calendar = Calendar.getInstance();
        Date dateForDialog = convertStringToDate(date, Const.Date.DATE_FORMAT);
        calendar.setTime(dateForDialog);
        return calendar;
    }

    public Calendar getTimeForDialog(String time) {
        final Calendar calendar = Calendar.getInstance();
        Date dateForDialog = convertStringToDate(time, Const.Date.TIME_FORMAT);
        calendar.setTime(dateForDialog);
        return calendar;
    }

    public Locale getLocale(Context context) {
        Locale locale;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            locale = context.getResources().getConfiguration().getLocales().get(0);
        } else {
            locale = context.getResources().getConfiguration().locale;
        }
        return locale;
    }

    public Appointment updateAppointmentData(Appointment fromAppointment, Appointment toAppointment) {
        toAppointment.setClient(fromAppointment.getClient());
        toAppointment.getClient().setContact(fromAppointment.getClient().getContact());
        toAppointment.setService(fromAppointment.getService());
        toAppointment.setTools(fromAppointment.getTools());
        toAppointment.setSum(fromAppointment.getSum());
        toAppointment.setDone(fromAppointment.isDone());
        toAppointment.setPaid(fromAppointment.isPaid());
        toAppointment.setInfo(fromAppointment.getInfo());
        toAppointment.setDate(fromAppointment.getDate());
        return toAppointment;
    }

    public String getCorrectDateFormat(int year, int month, int day) {
        String monthStr = String.valueOf(++month);
        String dayStr = String.valueOf(day);
        if (month < 10) monthStr = "0" + monthStr;
        if (day < 10) dayStr = "0" + dayStr;
        return dayStr + "/" + monthStr + "/" + year;
    }

    public String getCorrectTimeFormat(int hour, int minute) {
        String hourStr = String.valueOf(hour);
        String minuteStr = String.valueOf(minute);
        if (hour < 10) hourStr = "0" + hourStr;
        if (minute < 10) minuteStr = "0" + minuteStr;
        return hourStr + ":" + minuteStr;
    }

    public String getUserFromPrefs() {
        SharedPreferences settings = context.getSharedPreferences(
                Const.LOGIN_PREFS, Context.MODE_PRIVATE);
        return settings.getString(Const.USER, Const.NEW_USER);
    }

    public SharedPreferences getSharedPreferences(String prefs) {
        return context.getSharedPreferences(prefs, Context.MODE_PRIVATE);
    }

    public void setUserInPrefs(String userString, User user) {
        SharedPreferences.Editor editor = getEditor(Const.LOGIN_PREFS);
        if (userString.equals(Const.NEW_USER)) {
            editor.putString(Const.USER, Const.NEW_USER);
            editor.putString(Const.EMAIL, Const.EMPTY);
            editor.putBoolean(Const.LOGGED, Const.Status.LOGGED_OUT);
        } else {
            editor.putString(Const.USER, userString);
            editor.putString(Const.EMAIL, user.getEmail());
            editor.putBoolean(Const.LOGGED, Const.Status.LOGGED_IN);
        }
        editor.commit();
    }

    public SharedPreferences.Editor getEditor(String prefs) {
        SharedPreferences settings = context.getSharedPreferences(prefs, Context.MODE_PRIVATE);
        return settings.edit();
    }

    public boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnectedOrConnecting();
    }

    public void hideKeyboard(ViewGroup mainLayout) {
        InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(mainLayout.getWindowToken(), 0);
    }
}