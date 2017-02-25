package net.mEmoZz.FastingReminder.utilities;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.preference.PreferenceManager;
import net.mEmoZz.FastingReminder.R;
import net.mEmoZz.FastingReminder.utilities.Constants.LOCALE;

/**
 * Authored by Mohamed Fathy on 20 Feb, 2017.
 * Contact: muhamed.gendy@gmail.com
 */

public class PreferencesUtils {

  public static final String KEY_ENABLE_SERVICE = "enable_app";

  private Context context;
  private Integer language;
  private Boolean enableApp, fastMonday, fastThursday, fastWhites, fastAshura;

  public PreferencesUtils(Context context) {
    this.context = context;
  }

  public int getLanguage() {
    return language == null ? language = getLanguage(LOCALE.LANG_ARABIC + "") : language;
  }

  private int getLanguage(String defaultValue) {
    String key = context.getString(R.string.pref_key_lang_dialog);
    return Integer.parseInt(getPrefs().getString(key, defaultValue));
  }

  public boolean isAppEnabled() {
    return enableApp == null ? enableApp = isAppEnabled(true) : enableApp;
  }

  private boolean isAppEnabled(boolean defaultValue) {
    return getPrefs().getBoolean(KEY_ENABLE_SERVICE, defaultValue);
  }

  public boolean isFastMonday() {
    return fastMonday == null ? fastMonday = isFastMonday(false) : fastMonday;
  }

  private boolean isFastMonday(boolean defaultValue) {
    String key = context.getString(R.string.pref_key_fasting_monday);
    return getPrefs().getBoolean(key, defaultValue);
  }

  public boolean isFastThursday() {
    return fastThursday == null ? fastThursday = isFastThursday(false) : fastThursday;
  }

  private boolean isFastThursday(boolean defaultValue) {
    String key = context.getString(R.string.pref_key_fasting_thursday);
    return getPrefs().getBoolean(key, defaultValue);
  }

  public boolean isFastWhites() {
    return fastWhites == null ? fastWhites = isFastWhites(false) : fastWhites;
  }

  private boolean isFastWhites(boolean defaultValue) {
    String key = context.getString(R.string.pref_key_fasting_white_days);
    return getPrefs().getBoolean(key, defaultValue);
  }

  public boolean isFastAshura() {
    return fastAshura == null ? fastAshura = isFastAshura(false) : fastAshura;
  }

  private boolean isFastAshura(boolean defaultValue) {
    String key = context.getString(R.string.pref_key_fasting_ashura);
    return getPrefs().getBoolean(key, defaultValue);
  }

  private SharedPreferences getPrefs() {
    return PreferenceManager.getDefaultSharedPreferences(context);
  }
}
