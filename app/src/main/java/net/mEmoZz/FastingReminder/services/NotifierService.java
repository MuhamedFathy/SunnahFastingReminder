package net.mEmoZz.FastingReminder.services;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.os.Handler;
import android.os.IBinder;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.support.v4.content.ContextCompat;
import android.widget.Toast;
import java.util.Date;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import net.mEmoZz.FastingReminder.R;
import net.mEmoZz.FastingReminder.utilities.PreferencesUtils;
import org.arabeyes.itl.hijri.HijriCalc;
import org.arabeyes.itl.hijri.SimpleHijriDate;

/**
 * Authored by Mohamed Fathy on 23 Feb, 2017.
 * Contact: muhamed.gendy@gmail.com
 */

public class NotifierService extends Service {

  private static final int ITHNAIN = 1;
  private static final int KHAMEES = 4;

  private Context context;
  private ScheduledExecutorService executorService;
  private Handler handler;

  @Override public void onCreate() {
    context = this;
    handler = new Handler();
    super.onCreate();
  }

  @Override public int onStartCommand(Intent intent, int flags, int startId) {
    if (new PreferencesUtils(context).isAppEnabled()) {
      executorService = pushNotify();
      runOnUiThread(
          () -> Toast.makeText(this, "NotifierService started", Toast.LENGTH_LONG).show());
    }
    return START_STICKY;
  }

  private ScheduledExecutorService pushNotify() {
    ScheduledExecutorService executorService = Executors.newScheduledThreadPool(5);
    executorService.scheduleAtFixedRate(() -> runOnUiThread(() -> {
          SimpleHijriDate date = HijriCalc.toHijri(new Date());
          if (date.getDayOfWeek() == ITHNAIN) {
            Toast.makeText(this, "الإثنين", Toast.LENGTH_SHORT).show();
          } else if (date.getDayOfWeek() == KHAMEES) {
            pushNotification("الخميس");
          }
        }),
        0, 5, TimeUnit.SECONDS
    );
    return executorService;
  }

  private void runOnUiThread(Runnable runnable) {
    handler.post(runnable);
  }

  private void pushNotification(String msg) {
    NotificationCompat.Builder builder = new NotificationCompat.Builder(context)
        .setSmallIcon(R.drawable.ic_stat_moon)
        .setContentTitle("Test title")
        .setContentText(msg)
        .setAutoCancel(true)
        .setSound(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION))
        .setLights(ContextCompat.getColor(context, R.color.colorPrimary), 1500, 1000)
        .setColor(ContextCompat.getColor(context, R.color.colorPrimary))
        .setPriority(NotificationCompat.PRIORITY_HIGH);

    //builder.setContentIntent(pendingIntent);

    //        builder.addAction(new NotificationCompat.Action());

    NotificationManagerCompat.from(context)
        .notify((int) System.currentTimeMillis(), builder.build());
  }

  @Override public void onDestroy() {
    if (executorService != null) executorService.shutdownNow();
    super.onDestroy();
  }

  @Override
  public IBinder onBind(Intent intent) {
    return null;
  }
}
