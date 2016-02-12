package com.automaticvolumecontrol;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.widget.DatePicker;

import java.util.Calendar;

/**
 * Created by nbp184 on 2016/02/09.
 */
public class DatePickerFragment extends DialogFragment implements DatePickerDialog.OnDateSetListener {

    public interface OnDateSetListener {
        void onDateSet(String tag, int year, int monthOfYear, int dayOfMonth);
    }

    private Calendar date;
    private OnDateSetListener listener;

    public DatePickerFragment() {
        date = Calendar.getInstance();
        listener = null;
    }

    public DatePickerFragment setDate(Calendar date) {
        this.date = date;
        return this;
    }

    public DatePickerFragment setOnDateSetListener(OnDateSetListener listener) {
        this.listener = listener;
        return this;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        return new DatePickerDialog(getActivity(), this, date.get(Calendar.YEAR), date.get(Calendar.MONTH), date.get(Calendar.DAY_OF_MONTH));
    }

    @Override
    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
        if(listener != null) {
            listener.onDateSet(getTag(), year, monthOfYear, dayOfMonth);
        }
    }

}
