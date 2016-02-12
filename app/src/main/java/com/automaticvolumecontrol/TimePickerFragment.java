package com.automaticvolumecontrol;

import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.widget.TimePicker;

import java.util.Calendar;

/**
 * Created by nbp184 on 2016/02/09.
 */
public class TimePickerFragment extends DialogFragment implements TimePickerDialog.OnTimeSetListener {

    public interface OnTimeSetListener {
        void onTimeSet(String tag, int hourOfDay, int minute);
    }

    private Calendar date;
    private OnTimeSetListener listener;

    public TimePickerFragment() {
        date = Calendar.getInstance();
        listener = null;
    }

    public TimePickerFragment setDate(Calendar date) {
        this.date = date;
        return this;
    }

    public TimePickerFragment setOnTimeSetListener(OnTimeSetListener listener) {
        this.listener = listener;
        return this;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        return new TimePickerDialog(getActivity(), this, date.get(Calendar.HOUR_OF_DAY), date.get(Calendar.MINUTE), false);
    }

    @Override
    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
        if(listener != null) {
            listener.onTimeSet(getTag(), hourOfDay, minute);
        }
    }
}
