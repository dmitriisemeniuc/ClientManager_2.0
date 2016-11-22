package com.semeniuc.dmitrii.clientmanager.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.AppCompatEditText;
import android.support.v7.widget.AppCompatImageView;
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

   /** The {@code fragment} is added to the container view with id {@code frameId}. The operation is
    * performed by the {@code fragmentManager}
    */
    public static void addFragmentToActivity (@NonNull FragmentManager fragmentManager,
                                              @NonNull Fragment fragment, int frameId) {
            FragmentTransaction transaction = fragmentManager.beginTransaction();
            transaction.add(frameId, fragment);
            transaction.commit();
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
        Date dateForDialog = convertStringToDate(date, Constants.DATE_FORMAT);
        calendar.setTime(dateForDialog);
        return calendar;
    }

    public Calendar getTimeForDialog(String time) {
        final Calendar calendar = Calendar.getInstance();
        Date dateForDialog = convertStringToDate(time, Constants.TIME_FORMAT);
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
        toAppointment.setCompleted(fromAppointment.isCompleted());
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
                Constants.LOGIN_PREFS, Context.MODE_PRIVATE);
        return settings.getString(Constants.USER, Constants.NEW_USER);
    }

    public SharedPreferences getSharedPreferences(String prefs) {
        return context.getSharedPreferences(prefs, Context.MODE_PRIVATE);
    }

    public void setUserInPrefs(String userString, User user) {
        SharedPreferences.Editor editor = getEditor(Constants.LOGIN_PREFS);
        if (userString.equals(Constants.NEW_USER)) {
            editor.putString(Constants.USER, Constants.NEW_USER);
            editor.putString(Constants.EMAIL, Constants.EMPTY);
            editor.putBoolean(Constants.LOGGED, Constants.LOGGED_OUT);
        } else {
            editor.putString(Constants.USER, userString);
            editor.putString(Constants.EMAIL, user.getEmail());
            editor.putBoolean(Constants.LOGGED, Constants.LOGGED_IN);
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

    public void changeImage(int image, Appointment appointment, AppCompatImageView imageView) {
        switch (image) {
            case Constants.PAID:
                if (appointment.isPaid()) imageView.setImageResource(R.mipmap.ic_money_paid_yes);
                else imageView.setImageResource(R.mipmap.ic_money_paid_no);
                break;
            case Constants.DONE:
                if (appointment.isCompleted()) imageView.setImageResource(R.mipmap.ic_ok_yes);
                else imageView.setImageResource(R.mipmap.ic_ok_no);
                break;
            case Constants.HAIR_COLORING:
                if (appointment.getService().isHairColoring())
                    imageView.setImageResource(R.mipmap.ic_paint_yes);
                else imageView.setImageResource(R.mipmap.ic_paint_no);
                break;
            case Constants.HAIRDO:
                if (appointment.getService().isHairdo())
                    imageView.setImageResource(R.mipmap.ic_womans_hair_yes);
                else imageView.setImageResource(R.mipmap.ic_womans_hair_no);
                break;
            case Constants.HAIR_CUT:
                if (appointment.getService().isHaircut())
                    imageView.setImageResource(R.mipmap.ic_scissors_yes);
                else imageView.setImageResource(R.mipmap.ic_scissors_no);
                break;
            case Constants.BRUSH:
                if (appointment.getTools().isBrush())
                    imageView.setImageResource(R.mipmap.ic_brush_yes);
                else imageView.setImageResource(R.mipmap.ic_brush_no);
                break;
            case Constants.HAIR_BRUSH:
                if (appointment.getTools().isHairBrush())
                    imageView.setImageResource(R.mipmap.ic_hair_brush_yes);
                else imageView.setImageResource(R.mipmap.ic_hair_brush_no);
                break;
            case Constants.HAIR_DRAYER:
                if (appointment.getTools().isHairDryer())
                    imageView.setImageResource(R.mipmap.ic_hair_dryer_yes);
                else imageView.setImageResource(R.mipmap.ic_hair_dryer_no);
                break;
            case Constants.OXY:
                if (appointment.getTools().isOxy())
                    imageView.setImageResource(R.mipmap.ic_soap_yes);
                else imageView.setImageResource(R.mipmap.ic_soap_no);
                break;
            case Constants.CUT_SET:
                if (appointment.getTools().isCutSet())
                    imageView.setImageResource(R.mipmap.ic_cutset_yes);
                else imageView.setImageResource(R.mipmap.ic_cutset_no);
                break;
            case Constants.HAIR_BAND:
                if (appointment.getTools().isHairBand())
                    imageView.setImageResource(R.mipmap.ic_hair_band_yes);
                else imageView.setImageResource(R.mipmap.ic_hair_band_no);
                break;
            case Constants.SPRAY:
                if (appointment.getTools().isSpray())
                    imageView.setImageResource(R.mipmap.ic_spray_yes);
                else imageView.setImageResource(R.mipmap.ic_spray_no);
                break;
            case Constants.TUBE:
                if (appointment.getTools().isTube())
                    imageView.setImageResource(R.mipmap.ic_tube_yes);
                else imageView.setImageResource(R.mipmap.ic_tube_no);
                break;
            case Constants.TRIMMER:
                if (appointment.getTools().isTrimmer())
                    imageView.setImageResource(R.mipmap.ic_trimmer_yes);
                else imageView.setImageResource(R.mipmap.ic_trimmer_no);
                break;
        }
    }
}