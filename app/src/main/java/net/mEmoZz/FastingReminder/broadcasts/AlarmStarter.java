package net.mEmoZz.FastingReminder.broadcasts;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import net.mEmoZz.FastingReminder.utilities.AlarmUtils;
import net.mEmoZz.FastingReminder.utilities.PreferencesUtils;

/**
 * Authored by Mohamed Fathy on 22 Feb, 2017.
 * Contact: muhamed.gendy@gmail.com
 */

public class AlarmStarter extends BroadcastReceiver {

  @Override public void onReceive(Context context, Intent intent) {
    if (new PreferencesUtils(context).isAppEnabled()) AlarmUtils.setAlarm(context);
  }
}
