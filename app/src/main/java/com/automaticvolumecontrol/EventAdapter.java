package com.automaticvolumecontrol;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.Switch;

/**
 * Created by nbp184 on 2016/02/12.
 */
public class EventAdapter extends BaseAdapter {

    private Context context;

    public EventAdapter(Context context) {
        this.context = context;
    }

    @Override
    public int getCount() {
        return VolumeControlEvent.countEvents();
    }

    @Override
    public Object getItem(int position) {
        return VolumeControlEvent.getEvent(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;
        if(view == null) {
            LayoutInflater inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.list_item_event, parent);
        }
        VolumeControlEvent vce = VolumeControlEvent.getEvent(position);
        Button b = (Button)view.findViewById(R.id.button_name);
        b.setText(vce.getName());
        Switch s = (Switch)view.findViewById(R.id.switch_enabled);
        s.setChecked(vce.isEnabled());
        return view;
    }
}
