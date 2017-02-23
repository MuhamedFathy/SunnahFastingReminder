package net.mEmoZz.FastingReminder.utilities;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.preference.PreferenceManager;
import net.mEmoZz.FastingReminder.R;

/**
 * Authored by Mohamed Fathy on 20 Feb, 2017.
 * Contact: muhamed.gendy@gmail.com
 */

public class PreferencesUtils {

  private Context context;
  private Integer language;
  private Boolean enableApp;

  public PreferencesUtils(Context context) {
    this.context = context;
  }

  public int getLanguage() {
    return language == null ? language = getLanguage(Constants.LOCALE.LANG_ARABIC + "") : language;
  }

  private int getLanguage(String defaultValue) {
    String key = context.getString(R.string.pref_key_lang_dialog);
    return Integer.parseInt(getPrefs().getString(key, defaultValue));
  }

  public boolean isAppEnabled() {
    return enableApp == null ? enableApp = isAppEnabled(false) : enableApp;
  }

  private boolean isAppEnabled(boolean defaultValue) {
    String key = context.getString(R.string.pref_key_enable_app);
    return getPrefs().getBoolean(key, defaultValue);
  }

  private SharedPreferences getPrefs() {
    return PreferenceManager.getDefaultSharedPreferences(context);
  }
}
