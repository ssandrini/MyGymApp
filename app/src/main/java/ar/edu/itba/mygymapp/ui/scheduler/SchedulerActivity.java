package ar.edu.itba.mygymapp.ui.scheduler;
import android.annotation.SuppressLint;
import android.app.AlarmManager;
import android.os.Bundle;
import android.app.NotificationChannel;
import android.content.Intent;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.util.Log;
import android.os.Build;
import android.view.MenuItem;
import android.widget.CheckBox;
import java.util.Calendar;
import java.util.List;

import ar.edu.itba.mygymapp.R;
import ar.edu.itba.mygymapp.databinding.ActivitySchedulerBinding;
import ca.antonious.materialdaypicker.MaterialDayPicker;
import io.github.muddz.styleabletoast.StyleableToast;

import android.widget.TimePicker;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;


public class SchedulerActivity extends AppCompatActivity {
    static final private String DAY_EXTRA = "ar.edu.itba.mygymapp.DAY";
    static final private String ID_EXTRA = "ar.edu.itba.mygymapp.ID";
    static final private String ID_PARENT_EXTRA = "ar.edu.itba.mygymapp.ID_PARENT";
    static final private String MESSAGE_EXTRA ="ar.edu.itba.mygymapp.MESSAGE";
    private ActivitySchedulerBinding binding;
    private List<MaterialDayPicker.Weekday> days;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySchedulerBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        for (int i = 0; i < 7; i++) {
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel(String.valueOf(42+i), String.valueOf(42+i), importance);
            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }

        TimePicker timePicker = binding.timePicker;
        MaterialDayPicker materialDayPicker = binding.dayPicker;

        binding.acceptBtnSch.setOnClickListener(v->{
            Calendar calendar = Calendar.getInstance();
            AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
            days = materialDayPicker.getSelectedDays();
            if(!days.isEmpty()) {
                for(MaterialDayPicker.Weekday weekday : days) {
                    int i = weekday.ordinal();
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
                    pending.putExtra(MESSAGE_EXTRA, getText(R.string.notif_text));
                    @SuppressLint("UnspecifiedImmutableFlag") PendingIntent pendingNotifyIntent = PendingIntent.getBroadcast(this, 42+i, pending, PendingIntent.FLAG_UPDATE_CURRENT);
                    alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), AlarmManager.INTERVAL_DAY * 7, pendingNotifyIntent);
                }
                StyleableToast.makeText(getApplicationContext(), getText(R.string.success_notif).toString(), Toast.LENGTH_LONG, R.style.successToast).show();
                finish();
            }
            else {
                StyleableToast.makeText(getApplicationContext(), getText(R.string.error_notif).toString(), Toast.LENGTH_LONG, R.style.errorToast).show();
            }
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
