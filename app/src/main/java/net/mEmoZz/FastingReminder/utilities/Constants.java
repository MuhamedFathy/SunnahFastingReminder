package net.mEmoZz.FastingReminder.utilities;

import android.content.SharedPreferences;

/**
 * Authored by Mohamed Fathy on 20 Feb, 2017.
 * Contact: muhamed.gendy@gmail.com
 */

@SuppressWarnings("SpellCheckingInspection") public class Constants {

  /**
   * Access language states
   */
  public static class LOCALE {

    public static final int LANG_ARABIC = 0;
    public static final int LANG_ENGLISH = 1;

    public static final String ARABIC = "ar";
    public static final String English = "en";
  }

  /**
   * {@link SharedPreferences} keys.
   */
  public static class SHARED_PREFS {

    public static final String MAIN_PREFS_NAME = "app_prefs";
    public static final String LANG_PREFS_NAME = "language_prefs";
  }

  /**
   * Fonts names
   */
  public static class FONTS {

    public static final String ENGLISH_FONT = "Lato-Regular.ttf";
    public static final String ARABIC_FONT = "NotoKufiArabic-Regular.ttf";
  }
}
