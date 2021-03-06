package ar.edu.itba.mygymapp.ui.scheduler;
import android.app.PendingIntent;
import android.content.Context;
import androidx.core.app.NotificationCompat;
import android.content.Intent;
import android.net.Uri;
import android.content.BroadcastReceiver;
import androidx.core.app.TaskStackBuilder;
import ar.edu.itba.mygymapp.R;
import android.app.NotificationManager;

public class NotifyHandlerReceiver extends BroadcastReceiver {
    static final private String DAY_EXTRA = "ar.edu.itba.mygymapp.DAY";
    static final private String ID_EXTRA = "ar.edu.itba.mygymapp.ID";
    static final private String MESSAGE_EXTRA ="ar.edu.itba.mygymapp.MESSAGE";
    public void onReceive(Context context, Intent intent) {
        // Create an Intent for the activity you want to start
        Intent i = new Intent(Intent.ACTION_VIEW);
        i.setData(Uri.parse("http://mygym.com/routine?id=" + intent.getIntExtra(ID_EXTRA,1)));
        i.setPackage("ar.edu.itba.mygymapp");
        // Create the TaskStackBuilder and add the intent, which inflates the back stack
        TaskStackBuilder stackBuilder = TaskStackBuilder.create(context);
        stackBuilder.addNextIntentWithParentStack(i);
        // Get the PendingIntent containing the entire back stack
        PendingIntent resultPendingIntent = stackBuilder.getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT);

        int id = intent.getIntExtra(DAY_EXTRA,42);
        String message = intent.getStringExtra(MESSAGE_EXTRA);
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, String.valueOf(id))
                .setContentTitle("MyGym")
                .setContentText(message)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .setSmallIcon(R.drawable.logo)
                .setAutoCancel(true)
                .setContentIntent(resultPendingIntent);
        NotificationManager managerCompat  = (NotificationManager)context.getSystemService(Context.NOTIFICATION_SERVICE );
        managerCompat.notify(id,builder.build());
    }
}