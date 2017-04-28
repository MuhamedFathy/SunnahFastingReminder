package net.mEmoZz.FastingReminder.utilities;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import java.util.Calendar;
import net.mEmoZz.FastingReminder.broadcasts.AlarmReceiver;

/**
 * Authored by Mohamed Fathy on 29 Apr, 2017.
 * Contact: muhamed.gendy@gmail.com
 */

public class AlarmUtils {

  public static void setAlarm(Context context) {
    AlarmManager am = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
    Intent intent = new Intent(context, AlarmReceiver.class);
    int flag = PendingIntent.FLAG_UPDATE_CURRENT;
    long everyday = 24 * 60 * 60 * 1000;
    PendingIntent pi = PendingIntent.getBroadcast(context, 0x333, intent, flag);
    am.setRepeating(AlarmManager.RTC_WAKEUP, getTimeInMillis(), everyday, pi);
  }

  private static long getTimeInMillis() {
    Calendar calendar = Calendar.getInstance();
    calendar.setTimeInMillis(System.currentTimeMillis());
    calendar.set(Calendar.HOUR_OF_DAY, 21);
    calendar.set(Calendar.MINUTE, 0);
    calendar.set(Calendar.SECOND, 0);
    return calendar.getTimeInMillis();
  }
}
