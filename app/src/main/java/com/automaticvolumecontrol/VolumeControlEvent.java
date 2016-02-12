package com.automaticvolumecontrol;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

/**
 * Created by nbp184 on 2016/02/09.
 */
public class VolumeControlEvent {

    public static final String INDEX = "index";
    private static SimpleDateFormat timeFormat = null;
    private static SimpleDateFormat dateFormt = null;

    private static ArrayList<VolumeControlEvent> events;

    public static String getTime(Calendar cal) {
        if(timeFormat == null) {
            timeFormat = new SimpleDateFormat("h:mm aa");
        }
        return timeFormat.format(cal.getTime());
    }

    public static String getDate(Calendar cal) {
        if(dateFormt == null) {
            dateFormt = new SimpleDateFormat("EEE, MMM d, yyyy");
        }
        return dateFormt.format(cal.getTime());
    }

    public static int countEvents() {
        if(events == null) {
            return 0;
        } else {
            return events.size();
        }
    }

    public static VolumeControlEvent getEvent(int index) {
        if(events == null) {
            return null;
        } else {
            return events.get(index);
        }
    }

    public static void addEvent(VolumeControlEvent event) {
        if(events == null) {
            events = new ArrayList<>();
        }
        events.add(event);
    }

    public static final int SUNDAY = 0;
    public static final int MONDAY = 1;
    public static final int TUESDAY = 2;
    public static final int WEDNESDAY = 3;
    public static final int THURSDAY = 4;
    public static final int FRIDAY = 5;
    public static final int SATURDAY = 6;

    private String name;
    private boolean enabled;
    private Calendar startDate;
    private Calendar endDate;
    private boolean repeat;
    private boolean[] repeatsOn;
    private boolean endRepeat;
    private int volume;

    private VolumeControlEvent changeCopy;

    public VolumeControlEvent() {
        name = "";
        enabled = true;
        startDate = Calendar.getInstance();
        endDate = Calendar.getInstance();
        endDate.add(Calendar.HOUR_OF_DAY, 1);
        repeat = true;
        repeatsOn = new boolean[7];
        for(int i = 0; i < repeatsOn.length; i++) {
            repeatsOn[i] = true;
        }
        endRepeat = false;
        volume = 0;
        changeCopy = new VolumeControlEvent(this);
    }

    private VolumeControlEvent(VolumeControlEvent parent) {
        name = parent.name;
        enabled = parent.enabled;
        startDate = (Calendar)parent.startDate.clone();
        endDate = (Calendar)parent.endDate.clone();
        repeat = parent.repeat;
        endRepeat = parent.endRepeat;
        repeatsOn = new boolean[parent.repeatsOn.length];
        System.arraycopy(parent.repeatsOn, 0, repeatsOn, 0, repeatsOn.length);
        volume = parent.volume;
        changeCopy = null;
    }

    public boolean isChanged() {
        if(startDate.compareTo(changeCopy.startDate) != 0) {
            return true;
        } else if(endDate.compareTo(changeCopy.endDate) != 0) {
            return true;
        } else if(name.compareTo(changeCopy.name) != 0) {
            return true;
        } else if(repeat != changeCopy.repeat) {
            return true;
        } else if(enabled != changeCopy.enabled) {
            return true;
        } else if(endRepeat != changeCopy.endRepeat) {
            return true;
        } else if(volume != changeCopy.volume) {
            return true;
        } else {
            for(int i = 0; i < repeatsOn.length; i++) {
                if(repeatsOn[i] != changeCopy.repeatsOn[i]) {
                    return true;
                }
            }
            return false;
        }
    }

    public void commitChanges() {
        name = changeCopy.name;
        enabled = changeCopy.enabled;
        startDate.setTime(changeCopy.startDate.getTime());
        endDate.setTime(changeCopy.endDate.getTime());
        repeat = changeCopy.repeat;
        System.arraycopy(changeCopy.repeatsOn, 0, repeatsOn, 0, repeatsOn.length);
        volume = changeCopy.volume;
        endRepeat = changeCopy.endRepeat;
    }

    public void discardChanges() {
        changeCopy.name = name;
        changeCopy.enabled = enabled;
        changeCopy.startDate.setTime(startDate.getTime());
        changeCopy.endDate.setTime(endDate.getTime());
        changeCopy.repeat = repeat;
        System.arraycopy(repeatsOn, 0, changeCopy.repeatsOn, 0, repeatsOn.length);
        changeCopy.volume = volume;
        changeCopy.endRepeat = endRepeat;
    }

    public String getName() {
        return changeCopy.name;
    }

    public void changeName(String name) {
        changeCopy.name = name;
    }

    public String printStartTime() {
        return getTime(changeCopy.startDate);
    }

    public String printEndTime() {
        return getTime(changeCopy.endDate);
    }

    public Calendar getStart() {
        return changeCopy.startDate;
    }

    public Calendar getEnd() {
        return changeCopy.endDate;
    }

    public void changeStartTime(int hourOfDay, int minute) {
        changeCopy.startDate.set(Calendar.HOUR_OF_DAY, hourOfDay);
        changeCopy.startDate.set(Calendar.MINUTE, minute);
    }

    public void changeEndTime(int hourOfDay, int minute) {
        changeCopy.endDate.set(Calendar.HOUR_OF_DAY, hourOfDay);
        changeCopy.endDate.set(Calendar.MINUTE, minute);
    }

    public String printStartDate() {
        return getDate(changeCopy.startDate);
    }

    public void changeStartDate(int year, int monthOfYear, int dayOfMonth) {
        changeCopy.startDate.set(Calendar.YEAR, year);
        changeCopy.startDate.set(Calendar.MONTH, monthOfYear);
        changeCopy.startDate.set(Calendar.DAY_OF_MONTH, dayOfMonth);
    }

    public String printEndDate() {
        return getDate(changeCopy.endDate);
    }

    public void changeEndDate(int year, int monthOfYear, int dayOfMonth) {
        changeCopy.endDate.set(Calendar.YEAR, year);
        changeCopy.endDate.set(Calendar.MONTH, monthOfYear);
        changeCopy.endDate.set(Calendar.DAY_OF_MONTH, dayOfMonth);
    }

    public boolean isEnabled() {
        return changeCopy.enabled;
    }

    public boolean repeatsOn(int dayOfWeek) {
        return changeCopy.repeatsOn[dayOfWeek];
    }

    public boolean isRepeat() {
        return changeCopy.repeat;
    }

    public boolean isRepeatEnd() {
        return changeCopy.endRepeat;
    }

    public int getVolume() {
        return changeCopy.volume;
    }

    public void changeRepeat(boolean repeat) {
        changeCopy.repeat = repeat;
    }

    public void changeRepeatOn(int dayOfWeek, boolean repeatOn) {
        changeCopy.repeatsOn[dayOfWeek] = repeatOn;
    }

    public void changeEnabled(boolean enabled) {
        changeCopy.enabled = enabled;
    }

    public void changeEndRepeat(boolean endRepeat) {
        changeCopy.endRepeat = endRepeat;
    }

    public void changeVolume(int volume) {
        changeCopy.volume = volume;
    }
}
