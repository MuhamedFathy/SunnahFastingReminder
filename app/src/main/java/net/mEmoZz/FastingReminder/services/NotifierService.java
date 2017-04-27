package net.mEmoZz.FastingReminder.services;

import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.os.IBinder;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.support.v4.content.ContextCompat;
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

  private static final int RAMADAN = 8;

  private static final int SUNDAY = 0;
  private static final int WEDNESDAY = 4;

  private static final int TYPE_WHITES = 1;
  private static final int TYPE_ASHURA = 2;

  private Context context;
  private ScheduledExecutorService executorService;
  private SimpleHijriDate date;

  @Override public void onCreate() {
    context = this;
    date = HijriCalc.toHijri(new Date());
    super.onCreate();
  }

  @Override public int onStartCommand(Intent intent, int flags, int startId) {
    if (new PreferencesUtils(context).isAppEnabled()) executorService = notifier();
    return START_STICKY_COMPATIBILITY;
  }

  private ScheduledExecutorService notifier() {
    ScheduledExecutorService executorService = Executors.newScheduledThreadPool(0);
    executorService.scheduleAtFixedRate(this::notifyUser, 0, 13, TimeUnit.HOURS);
    return executorService;
  }

  private void notifyUser() {
    if (date != null && date.getMonth() != RAMADAN) {
      if (getPrefs().isFastMonday()) {
        if (date.getDayOfWeek() == SUNDAY) {
          pushSingleFastingNotify(
              getString(R.string.fast_monday),
              getString(R.string.do_not_forget));
        }
      }
      if (getPrefs().isFastThursday()) {
        if (date.getDayOfWeek() == WEDNESDAY) {
          pushSingleFastingNotify(
              getString(R.string.fast_thursday),
              getString(R.string.do_not_forget));
        }
      }
      if (getPrefs().isFastWhites()) {
        if (date.getDayOfMonth() == 12) {
          pushMultipleFastingNotify(TYPE_WHITES, "13");
        } else if (date.getDayOfMonth() == 13) {
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
        pushSingleFastingNotify(
            getString(R.string.fast_whites, day),
            getString(R.string.do_not_forget)
        );
        break;
      case TYPE_ASHURA:
        pushSingleFastingNotify(
            getString(R.string.fast_ashura, day),
            getString(R.string.do_not_forget)
        );
        break;
    }
  }

  private void pushSingleFastingNotify(String title, String msg) {
    NotificationCompat.Builder builder = new NotificationCompat.Builder(context)
        .setSmallIcon(R.drawable.ic_stat_moon)
        .setContentTitle(title)
        .setContentText(msg)
        .setAutoCancel(true)
        .setOngoing(true)
        .setVibrate(new long[] { 400, 400, 400, 400 })
        .setSound(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION))
        .setLights(ContextCompat.getColor(context, R.color.colorPrimary), 1500, 1000)
        .setColor(ContextCompat.getColor(context, R.color.colorPrimary))
        .setPriority(NotificationCompat.PRIORITY_HIGH)
        .setContentIntent(getDismissIntent());
    NotificationManagerCompat.from(context).notify(0, builder.build());
  }

  private PendingIntent getDismissIntent() {
    Intent intent = new Intent();
    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
    return PendingIntent.getActivity(context, 0, intent, PendingIntent.FLAG_CANCEL_CURRENT);
  }

  private PreferencesUtils getPrefs() {
    return new PreferencesUtils(this);
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
