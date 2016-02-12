package com.automaticvolumecontrol;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.Switch;

public class MainActivity extends AppCompatActivity {

    private static final int EDIT_REQUEST = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ListView lv = (ListView)findViewById(R.id.listView);
        lv.setAdapter(new EventAdapter(this));
    }

    public void buttonPress(View view) {
        ListView lv = (ListView)findViewById(R.id.listView);
        switch(view.getId()) {
            case R.id.button_add:
                Intent intent = new Intent(this, EditActivity.class);
                intent.putExtra(VolumeControlEvent.INDEX, -1);
                startActivityForResult(intent, EDIT_REQUEST);
                break;
            case R.id.button_name:
                intent = new Intent(this, EditActivity.class);
                intent.putExtra(VolumeControlEvent.INDEX, lv.getPositionForView(view));
                startActivityForResult(intent, EDIT_REQUEST);
                break;
            case R.id.switch_enabled:
                Switch s = (Switch)view;
                VolumeControlEvent vce = VolumeControlEvent.getEvent(lv.getPositionForView(view));
                vce.changeEnabled(s.isChecked());
                //TODO: check to make sure no event conflicts
                vce.commitChanges();
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        ListView lv = (ListView)findViewById(R.id.listView);
        EventAdapter ea = (EventAdapter)lv.getAdapter();
        switch(requestCode) {
            case EDIT_REQUEST:
                if(resultCode == RESULT_OK) {
                    ea.notifyDataSetChanged();
                }
                break;
        }
    }

}
