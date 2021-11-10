package ar.edu.itba.mygymapp.ui.scheduler;

import android.app.AlarmManager;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;


import java.util.Calendar;

import ar.edu.itba.mygymapp.databinding.ActivityRoutineBinding;
import ar.edu.itba.mygymapp.databinding.ActivitySchedulerBinding;

public class SchedulerActivity extends AppCompatActivity {
    static final private String CHANNEL_ID = "Routine Notif";
    static final private String DAY_EXTRA = "com.example.fithub_mobile.DAY";
    static final private String ID_EXTRA = "com.example.fithub_mobile.ID";
    static final private String ID_PARENT_EXTRA = "com.example.fithub_mobile.ID_PARENT";
    public static final int REQUEST_CODE_NOTIFY = 1;

    private ActivitySchedulerBinding binding;
    private CheckBox[] days;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySchedulerBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            for (int i = 0; i < 7; i++) {
                int importance = NotificationManager.IMPORTANCE_DEFAULT;
                NotificationChannel channel = new NotificationChannel(String.valueOf(42+i), String.valueOf(42+i), importance);
                NotificationManager notificationManager = getSystemService(NotificationManager.class);
                notificationManager.createNotificationChannel(channel);
            }
        };

        TimePicker timePicker = binding.timePicker;
        days = new CheckBox[7];
        days[0] = binding.checkBoxSun;
        days[1] = binding.checkBoxMon;
        days[2] = binding.checkBoxTue;
        days[3] = binding.checkBoxWed;
        days[4] = binding.checkBoxThu;
        days[5] = binding.checkBoxFri;
        days[6] = binding.checkBoxSat;


        binding.acceptBtnSch.setOnClickListener(v->{
            Calendar calendar = Calendar.getInstance();
            AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);

            for (int i = 0; i < 7; i++){
                if (days[i].isChecked()){
                    calendar.setTimeInMillis(System.currentTimeMillis());
                    int today = calendar.get(Calendar.DAY_OF_WEEK);
                    int diff = (7 + (i+1) - today ) % 7;
                    calendar.add(Calendar.DATE,diff);
                    calendar.set(Calendar.HOUR_OF_DAY, timePicker.getHour());
                    calendar.set(Calendar.MINUTE, timePicker.getMinute());
                    Log.d("CALENDAR",calendar.toString());
                    Intent pending = new Intent( this, NotifyHandlerReceiver.class );
                    pending.putExtra(DAY_EXTRA,42+i);
                    pending.putExtra(ID_EXTRA,getIntent().getIntExtra(ID_PARENT_EXTRA,0));
                    PendingIntent pendingNotifyIntent = PendingIntent.getBroadcast(
                            this,
                            42+i,
                            pending,
                            PendingIntent.FLAG_UPDATE_CURRENT);
                    alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(),
                            AlarmManager.INTERVAL_DAY * 7, pendingNotifyIntent);
                }
            }

            finish();
//            Toast.makeText(getApplicationContext(),getString(R.string.reminder_created),Toast.LENGTH_SHORT).show();

        });

        binding.cancelBtnSch.setOnClickListener(v->{
            finish();
        });
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }
}
