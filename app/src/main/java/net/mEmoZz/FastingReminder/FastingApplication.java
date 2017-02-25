package net.mEmoZz.FastingReminder;

import android.app.Application;
import net.mEmoZz.FastingReminder.language.Localization;
import net.mEmoZz.FastingReminder.utilities.Constants.FONTS;
import net.mEmoZz.FastingReminder.utilities.Constants.LOCALE;
import net.mEmoZz.FastingReminder.utilities.PreferencesUtils;
import net.mEmoZz.FastingReminder.utilities.Utils;

/**
 * Authored by Mohamed Fathy on 20 Feb, 2017.
 * Contact: muhamed.gendy@gmail.com
 */

public class FastingApplication extends Application {

  @Override public void onCreate() {
    super.onCreate();
    validateLanguage();
  }

  /**
   * Check for language
   */
  private void validateLanguage() {
    switch (new PreferencesUtils(this).getLanguage()) {
      case LOCALE.LANG_ARABIC:
        Localization.setLanguage(this, LOCALE.LANG_ARABIC);
        Utils.initCalligraphy(FONTS.ARABIC_FONT);
        break;
      case LOCALE.LANG_ENGLISH:
        Localization.setLanguage(this, LOCALE.LANG_ENGLISH);
        Utils.initCalligraphy(FONTS.ENGLISH_FONT);
        break;
    }
  }
}
