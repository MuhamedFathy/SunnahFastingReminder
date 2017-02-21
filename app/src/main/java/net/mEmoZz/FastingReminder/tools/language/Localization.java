package net.mEmoZz.FastingReminder.tools.language;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Build;
import android.support.v7.app.AlertDialog;
import java.util.Locale;
import net.mEmoZz.FastingReminder.ui.activities.MainScreen;
import net.mEmoZz.FastingReminder.utilities.Constants;
import net.mEmoZz.FastingReminder.utilities.PreferencesUtils;
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

  public static int getCurrentLanguageID(Context context) {
    return new PreferencesUtils(context).getLanguage();
  }

  public static String getCurrentLanguageName(Context context) {
    int lang = getCurrentLanguageID(context);
    if (lang == 0) {
      return Constants.LOCALE.ARABIC;
    } else {
      return Constants.LOCALE.English;
    }
  }

  public static void languageDialog(final Activity activity) {
    AlertDialog.Builder builder = new AlertDialog.Builder(activity);
    builder.setTitle("");
    final int inputSelection;
    switch (Localization.getCurrentLanguageID(activity)) {
      case Constants.LOCALE.LANG_ENGLISH:
        inputSelection = 1;
        break;
      case Constants.LOCALE.LANG_ARABIC:
        inputSelection = 0;
        break;
      default:
        inputSelection = 1;
        break;
    }
    CharSequence[] titles = new CharSequence[] {
        "", ""
    };
    builder.setSingleChoiceItems(titles, inputSelection, new DialogInterface.OnClickListener() {
      public void onClick(DialogInterface dialog, int item) {
        dialog.dismiss();
        switch (item) {
          case 0:
            if (inputSelection == item) return;
            Localization.setLanguage(activity, Constants.LOCALE.LANG_ARABIC);
            Utils.initCalligraphy(Constants.FONTS.ARABIC_FONT);
            activity.startActivity(new Intent(activity, MainScreen.class));
            activity.finish();
            break;
          case 1:
            if (inputSelection == item) return;
            Localization.setLanguage(activity, Constants.LOCALE.LANG_ENGLISH);
            activity.setResult(Activity.RESULT_OK);
            Utils.initCalligraphy(Constants.FONTS.ENGLISH_FONT);
            activity.startActivity(new Intent(activity, MainScreen.class));
            activity.finish();
            break;
        }
      }
    });
    builder.create();
    builder.show();
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
