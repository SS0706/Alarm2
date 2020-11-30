package com.example.alarmproject;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;

import androidx.annotation.IntDef;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

public final class CreateAlarmActivity extends AppCompatActivity {
    public final String TAG = "Logcat";

    public static final String ALARM_EXTRA = "alarm_extra";
    public static final String MODE_EXTRA = "mode_extra";

    @Retention(RetentionPolicy.SOURCE)
    @IntDef({EDIT_ALARM,ADD_ALARM,UNKNOWN})
    @interface Mode{}
    public static final int EDIT_ALARM = 1;
    public static final int ADD_ALARM = 2;
    public static final int UNKNOWN = 0;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.create_alarms_sub);
        Log.i(TAG, "onCreate");

        Intent intent = new Intent(this, MainActivity.class);


        //noinspection ConstantConditions
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(getToolbarTitle());

        final Alarm alarm = getAlarm();

        if(getSupportFragmentManager().findFragmentById(R.id.edit_alarm_sub) == null) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .add(R.id.edit_alarm_sub, CreateAlarmFrag.newInstance(alarm))
                    .commit();
        }

    }

    private Alarm getAlarm() {
        switch (getMode()) {
            case EDIT_ALARM:
                return getIntent().getParcelableExtra(ALARM_EXTRA);
            case ADD_ALARM:
                final long id = DataBase.getInstance(this).addAlarm();
                AlarmServiceLoad.launchLoadAlarmsService(this);
                return new Alarm(id);
            case UNKNOWN:
            default:
                throw new IllegalStateException("Mode supplied as intent extra for " +
                        CreateAlarmActivity.class.getSimpleName() + " must match value in " +
                        Mode.class.getSimpleName());
        }
    }

    private @Mode int getMode() {
        final @Mode int mode = getIntent().getIntExtra(MODE_EXTRA, UNKNOWN);
        return mode;
    }

    private String getToolbarTitle() {
        int titleResId;
        switch (getMode()) {
            case EDIT_ALARM:
                titleResId = R.string.edit_alarm;
                break;
            case ADD_ALARM:
                titleResId = R.string.add_alarm;
                break;
            case UNKNOWN:
            default:
                throw new IllegalStateException("Mode supplied as intent extra for " +
                        CreateAlarmActivity.class.getSimpleName() + " must match value in " +
                        Mode.class.getSimpleName());
        }
        return getString(titleResId);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    public static Intent buildAddEditAlarmActivityIntent(Context context, @Mode int mode) {
        final Intent i = new Intent(context, CreateAlarmActivity.class);
        i.putExtra(MODE_EXTRA, mode);
        return i;
    }

}
