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

  private static final int MUHARRAM = 0;
  private static final int SUNDAY = 0;
  private static final int WEDNESDAY = 3;
  private static final int TYPE_WHITES = 1;
  private static final int TYPE_ASHURA = 2;

  private Context context;
  private ScheduledExecutorService executorService;
  private Handler handler;
  private SimpleHijriDate date;

  @Override public void onCreate() {
    context = this;
    handler = new Handler();
    date = HijriCalc.toHijri(new Date());
    super.onCreate();
  }

  @Override public int onStartCommand(Intent intent, int flags, int startId) {
    if (new PreferencesUtils(context).isAppEnabled()) {
      executorService = notifier();
      runOnUiThread(() -> Toast.makeText(this, "Service started", Toast.LENGTH_LONG).show());
    }
    return START_STICKY;
  }

  private ScheduledExecutorService notifier() {
    ScheduledExecutorService executorService = Executors.newScheduledThreadPool(0);
    executorService.scheduleAtFixedRate(this::notifyUser, 0, 13, TimeUnit.HOURS);
    return executorService;
  }

  private void notifyUser() {
    if (date != null) {
      if (getPrefs().isFastMonday()) {
        if (date.getDayOfWeek() == SUNDAY) {
          pushNotification(getString(R.string.fast_monday), getString(R.string.do_not_forget));
        }
      }
      if (getPrefs().isFastThursday()) {
        if (date.getDayOfWeek() == WEDNESDAY) {
          pushNotification(getString(R.string.fast_thursday), getString(R.string.do_not_forget));
        }
      }
      if (getPrefs().isFastWhites()) {
        if (date.getDayOfMonth() == 12) {
          pushMultipleFastingNotify(TYPE_WHITES, "13");
        } else if (date.getDayOfMonth() == 28) {
          pushMultipleFastingNotify(TYPE_WHITES, "14");
        } else if (date.getDayOfMonth() == 14) {
          pushMultipleFastingNotify(TYPE_WHITES, "15");
        }
      }
      if (getPrefs().isFastAshura()) {
        if (date.getMonth() == MUHARRAM) {
          if (date.getDayOfMonth() == 8) {
            pushMultipleFastingNotify(TYPE_ASHURA, "9");
          } else if (date.getDayOfMonth() == 9) {
            pushMultipleFastingNotify(TYPE_ASHURA, "10");
          }
        }
      }
    }
  }

  private void pushMultipleFastingNotify(int type, String day) {
    switch (type) {
      case TYPE_WHITES:
        pushNotification(getString(R.string.fast_whites, day), getString(R.string.do_not_forget));
        break;
      case TYPE_ASHURA:
        pushNotification(getString(R.string.fast_ashura, day), getString(R.string.do_not_forget));
        break;
    }
  }

  private void pushNotification(String title, String msg) {
    NotificationCompat.Builder builder = new NotificationCompat.Builder(context)
        .setSmallIcon(R.drawable.ic_stat_moon)
        .setContentTitle(title)
        .setContentText(msg)
        .setAutoCancel(true)
        .setSound(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION))
        .setLights(ContextCompat.getColor(context, R.color.colorPrimary), 1500, 1000)
        .setColor(ContextCompat.getColor(context, R.color.colorPrimary))
        .setPriority(NotificationCompat.PRIORITY_HIGH);
    NotificationManagerCompat.from(context).notify(0, builder.build());
  }

  private PreferencesUtils getPrefs() {
    return new PreferencesUtils(this);
  }

  private void runOnUiThread(Runnable runnable) {
    handler.post(runnable);
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
