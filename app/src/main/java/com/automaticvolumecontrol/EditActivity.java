package com.automaticvolumecontrol;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.media.AudioManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.Switch;

import java.util.Calendar;

public class EditActivity extends AppCompatActivity implements TimePickerFragment.OnTimeSetListener, DatePickerFragment.OnDateSetListener {

    private static final String START_TIME_DIALOG = "start time dialog";
    private static final String END_TIME_DIALOG = "end time dialog";
    private static final String START_DATE_DIALOG = "start date dialog";
    private static final String END_DATE_DIALOG = "end date dialog";

    private VolumeControlEvent vce;
    private int[] reapeatStuff;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);
        reapeatStuff = new int[]{R.id.layout_repeatStuff_1, R.id.layout_repeatStuff_2};
        int index = getIntent().getIntExtra(VolumeControlEvent.INDEX, -1);
        if(index < 0) {
            vce = new VolumeControlEvent();
        } else {
            vce = VolumeControlEvent.getEvent(index);
        }
        EditText et;
        Button b;
        Switch s;
        et = (EditText)findViewById(R.id.text_name);
        et.setText(vce.getName());
        b = (Button)findViewById(R.id.button_start_date);
        b.setText(vce.printStartDate());
        s = (Switch)findViewById(R.id.switch_enabled);
        s.setChecked(vce.isEnabled());
        b = (Button)findViewById(R.id.button_start_time);
        b.setText(vce.printStartTime());
        b = (Button)findViewById(R.id.button_end_time);
        b.setText(vce.printEndTime());
        CheckBox cb;
        HighlightButton hb;
        SeekBar sb;
        cb = (CheckBox)findViewById(R.id.checkbox_repeat);
        cb.setChecked(vce.isRepeat());
        if(!vce.isRepeat()) {
            for(int resID : reapeatStuff) {
                findViewById(resID).setVisibility(View.GONE);
            }
        }
        hb = (HighlightButton)findViewById(R.id.button_monday);
        hb.setHighlighted(vce.repeatsOn(VolumeControlEvent.MONDAY));
        hb = (HighlightButton)findViewById(R.id.button_tuesday);
        hb.setHighlighted(vce.repeatsOn(VolumeControlEvent.TUESDAY));
        hb = (HighlightButton)findViewById(R.id.button_wednesday);
        hb.setHighlighted(vce.repeatsOn(VolumeControlEvent.WEDNESDAY));
        hb = (HighlightButton)findViewById(R.id.button_thursday);
        hb.setHighlighted(vce.repeatsOn(VolumeControlEvent.THURSDAY));
        hb = (HighlightButton)findViewById(R.id.button_friday);
        hb.setHighlighted(vce.repeatsOn(VolumeControlEvent.FRIDAY));
        hb = (HighlightButton)findViewById(R.id.button_saturday);
        hb.setHighlighted(vce.repeatsOn(VolumeControlEvent.SATURDAY));
        hb = (HighlightButton)findViewById(R.id.button_sunday);
        hb.setHighlighted(vce.repeatsOn(VolumeControlEvent.SUNDAY));
        cb = (CheckBox)findViewById(R.id.checkbox_ends_on);
        cb.setChecked(vce.isRepeatEnd());
        b = (Button)findViewById(R.id.button_end_date);
        b.setText(vce.printEndDate());
        sb = (SeekBar)findViewById(R.id.seekBar);
        sb.setProgress(vce.getVolume());
    }

    public void buttonPress(View view) {
        switch(view.getId()) {
            case R.id.button_close:
                onBackPressed();
                break;
            case R.id.button_save:
                saveChanges();
                break;
            case R.id.button_start_time:
                TimePickerFragment tfrag = new TimePickerFragment();
                tfrag.setDate(vce.getStart())
                        .setOnTimeSetListener(this)
                        .show(getSupportFragmentManager(), START_TIME_DIALOG);
                break;
            case R.id.button_end_time:
                tfrag = new TimePickerFragment();
                tfrag.setDate(vce.getEnd())
                        .setOnTimeSetListener(this)
                        .show(getSupportFragmentManager(), END_TIME_DIALOG);
                break;
            case R.id.button_start_date:
                DatePickerFragment dfrag = new DatePickerFragment();
                dfrag.setDate(vce.getStart())
                        .setOnDateSetListener(this)
                        .show(getSupportFragmentManager(), START_DATE_DIALOG);
                break;
            case R.id.checkbox_repeat:
                int visibility;
                if(((CheckBox)view).isChecked()) {
                    visibility = View.VISIBLE;
                } else {
                    visibility = View.GONE;
                }
                for(int resID : reapeatStuff) {
                    findViewById(resID).setVisibility(visibility);
                }
                break;
            case R.id.button_sunday:
                HighlightButton hb = (HighlightButton)view;
                hb.switchHighlighted();
                break;
            case R.id.button_monday:
            case R.id.button_tuesday:
            case R.id.button_wednesday:
            case R.id.button_thursday:
            case R.id.button_friday:
            case R.id.button_saturday:
                ((HighlightButton)view).switchHighlighted();
                break;
            case R.id.checkbox_ends_on:
                findViewById(R.id.button_end_date).setEnabled(((CheckBox) view).isChecked());
                break;
            case R.id.button_end_date:
                dfrag = new DatePickerFragment();
                dfrag.setDate(vce.getEnd())
                        .setOnDateSetListener(this)
                        .show(getSupportFragmentManager(), END_DATE_DIALOG);
                break;
        }
    }

    private void saveChanges() {
        updateChangeCopy();
        vce.commitChanges();
    }

    private void updateChangeCopy() {
        EditText et = (EditText)findViewById(R.id.text_name);
        vce.changeName(et.getText().toString());
        Switch s = (Switch)findViewById(R.id.switch_enabled);
        vce.changeEnabled(s.isChecked());
        CheckBox cb = (CheckBox)findViewById(R.id.checkbox_repeat);
        vce.changeRepeat(cb.isChecked());
        HighlightButton hb = (HighlightButton)findViewById(R.id.button_sunday);
        vce.changeRepeatOn(VolumeControlEvent.SUNDAY, hb.isHighlighted());
        hb = (HighlightButton)findViewById(R.id.button_monday);
        vce.changeRepeatOn(VolumeControlEvent.MONDAY, hb.isHighlighted());
        hb = (HighlightButton)findViewById(R.id.button_tuesday);
        vce.changeRepeatOn(VolumeControlEvent.TUESDAY, hb.isHighlighted());
        hb = (HighlightButton)findViewById(R.id.button_wednesday);
        vce.changeRepeatOn(VolumeControlEvent.WEDNESDAY, hb.isHighlighted());
        hb = (HighlightButton)findViewById(R.id.button_thursday);
        vce.changeRepeatOn(VolumeControlEvent.THURSDAY, hb.isHighlighted());
        hb = (HighlightButton)findViewById(R.id.button_friday);
        vce.changeRepeatOn(VolumeControlEvent.FRIDAY, hb.isHighlighted());
        hb = (HighlightButton)findViewById(R.id.button_saturday);
        vce.changeRepeatOn(VolumeControlEvent.SATURDAY, hb.isHighlighted());
        cb = (CheckBox)findViewById(R.id.checkbox_ends_on);
        vce.changeEndRepeat(cb.isChecked());
        SeekBar sb = (SeekBar)findViewById(R.id.seekBar);
        vce.changeVolume(sb.getProgress());
    }

    @Override
    public void onBackPressed() {
        updateChangeCopy();
        if(vce.isChanged()) {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setMessage("Are you sure you want to discard this event?")
                    .setPositiveButton("Keep Editing", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            EditActivity.this.vce.commitChanges();
                            VolumeControlEvent.addEvent(EditActivity.this.vce);
                            EditActivity.this.setResult(RESULT_OK);
                            EditActivity.this.finish();
                        }
                    })
                    .setNegativeButton("Discard", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            EditActivity.this.vce.discardChanges();
                            EditActivity.this.setResult(RESULT_CANCELED);
                            EditActivity.this.finish();
                        }
                    });
            AlertDialog dialog = builder.create();
            dialog.show();
        } else {
            setResult(RESULT_CANCELED);
            finish();
        }
    }

    @Override
    public void onTimeSet(String tag, int hourOfDay, int minute) {
        if(START_TIME_DIALOG.equals(tag)) {
            vce.changeStartTime(hourOfDay, minute);
            Button b = (Button)findViewById(R.id.button_start_time);
            b.setText(vce.printStartTime());
        } else if(END_TIME_DIALOG.equals(tag)) {
            vce.changeEndTime(hourOfDay, minute);
            Button b = (Button)findViewById(R.id.button_end_time);
            b.setText(vce.printEndTime());
        } else {
            System.out.println("tag = " +tag);
        }
    }

    @Override
    public void onDateSet(String tag, int year, int monthOfYear, int dayOfMonth) {
        if(START_DATE_DIALOG.equals(tag)) {
            vce.changeStartDate(year, monthOfYear, dayOfMonth);
            Button b = (Button)findViewById(R.id.button_start_date);
            b.setText(vce.printStartDate());
        } else if(END_DATE_DIALOG.equals(tag)) {
            vce.changeEndDate(year, monthOfYear, dayOfMonth);
            Button b = (Button)findViewById(R.id.button_end_date);
            b.setText(vce.printEndDate());
        }
    }
}
