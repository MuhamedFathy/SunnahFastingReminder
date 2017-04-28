package net.mEmoZz.FastingReminder.broadcasts;

import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.support.v4.content.ContextCompat;
import java.util.Date;
import net.mEmoZz.FastingReminder.R;
import net.mEmoZz.FastingReminder.utilities.PreferencesUtils;
import org.arabeyes.itl.hijri.HijriCalc;
import org.arabeyes.itl.hijri.SimpleHijriDate;

/**
 * Authored by Mohamed Fathy on 23 Feb, 2017.
 * Contact: muhamed.gendy@gmail.com
 */

public class AlarmReceiver extends BroadcastReceiver {

  private static final int MUHARRAM = 0;

  private static final int RAMADAN = 8;

  private static final int SUNDAY = 0;
  private static final int WEDNESDAY = 3;

  private static final int TYPE_WHITES = 1;
  private static final int TYPE_ASHURA = 2;

  private Context context;

  @Override public void onReceive(Context context, Intent intent) {
    this.context = context;

    notifyUser();
  }

  private void notifyUser() {
    SimpleHijriDate date = HijriCalc.toHijri(new Date());
    if (date != null && date.getMonth() != RAMADAN) setupNotifies(date);
  }

  private void setupNotifies(SimpleHijriDate date) {
    monday(date);
    thursday(date);
    whites(date);
    ashura(date);
  }

  private void monday(SimpleHijriDate date) {
    if (getPrefs().isFastMonday()) {
      if (date.getDayOfWeek() == SUNDAY) {
        pushSingleFastingNotify(
            context.getString(R.string.fast_monday),
            context.getString(R.string.do_not_forget));
      }
    }
  }

  private void thursday(SimpleHijriDate date) {
    if (getPrefs().isFastThursday()) {
      if (date.getDayOfWeek() == WEDNESDAY) {
        pushSingleFastingNotify(
            context.getString(R.string.fast_thursday),
            context.getString(R.string.do_not_forget)
        );
      }
    }
  }

  private void whites(SimpleHijriDate date) {
    if (getPrefs().isFastWhites()) {
      if (date.getDayOfMonth() == 12) {
        pushMultipleFastingNotify(TYPE_WHITES, "13");
      } else if (date.getDayOfMonth() == 13) {
        pushMultipleFastingNotify(TYPE_WHITES, "14");
      } else if (date.getDayOfMonth() == 14) {
        pushMultipleFastingNotify(TYPE_WHITES, "15");
      }
    }
  }

  private void ashura(SimpleHijriDate date) {
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

  private void pushMultipleFastingNotify(int type, String day) {
    switch (type) {
      case TYPE_WHITES:
        pushSingleFastingNotify(
            context.getString(R.string.fast_whites, day),
            context.getString(R.string.do_not_forget)
        );
        break;
      case TYPE_ASHURA:
        pushSingleFastingNotify(
            context.getString(R.string.fast_ashura, day),
            context.getString(R.string.do_not_forget)
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
    return PendingIntent.getActivity(context, 1, intent, PendingIntent.FLAG_CANCEL_CURRENT);
  }

  private PreferencesUtils getPrefs() {
    return new PreferencesUtils(context);
  }
}
