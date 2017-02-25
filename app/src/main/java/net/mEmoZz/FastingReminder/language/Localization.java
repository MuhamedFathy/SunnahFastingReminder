package net.mEmoZz.FastingReminder.language;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.Configuration;
import android.os.Build;
import java.util.Locale;
import net.mEmoZz.FastingReminder.utilities.Constants;
import net.mEmoZz.FastingReminder.utilities.Utils;

/**
 * Authored by Mohamed Fathy on 20 Feb, 2017.
 * Contact: muhamed.gendy@gmail.com
 */

public class Localization {

  @SuppressWarnings("deprecation") public static void setLanguage(Context context, int lang) {
    Configuration config = context.getResources().getConfiguration();
    if (lang == Constants.LOCALE.LANG_ENGLISH) {
      Locale mLocale = new Locale(Constants.LOCALE.English);
      Locale.setDefault(mLocale);
      if (!getLocale(config).equals(mLocale)) {
        setLocale(config, mLocale);
        context.getResources().updateConfiguration(config, null);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
          config.setLayoutDirection(mLocale);
        }
      }
    } else {
      Locale mLocale = new Locale(Constants.LOCALE.ARABIC);
      Locale.setDefault(mLocale);

      if (!getLocale(config).equals(mLocale)) {
        setLocale(config, mLocale);
        context.getResources().updateConfiguration(config, null);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
          config.setLayoutDirection(mLocale);
        }
      }
    }
  }

  @SuppressLint("NewApi") @SuppressWarnings("deprecation")
  private static Locale getLocale(Configuration config) {
    return Utils.isAboveNougat() ? config.getLocales().get(0) : config.locale;
  }

  @SuppressWarnings("deprecation")
  private static void setLocale(Configuration config, Locale locale) {
    if (Utils.isAboveNougat()) {
      config.setLocale(locale);
    } else {
      config.locale = locale;
    }
  }
}
