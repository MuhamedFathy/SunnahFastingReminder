package net.mEmoZz.FastingReminder.utilities;

import android.content.Context;
import android.content.res.Configuration;
import android.os.Build;
import android.view.View;
import net.mEmoZz.FastingReminder.R;
import uk.co.chrisjenx.calligraphy.CalligraphyConfig;

/**
 * Authored by Mohamed Fathy on 20 Feb, 2017.
 * Contact: muhamed.gendy@gmail.com
 */

@SuppressWarnings({ "WeakerAccess" }) public class Utils {

  /**
   * Check if system version equal or bigger than android v5+ or not
   *
   * @return true if equal or bigger than android v5+
   */
  public static boolean isAboveLollipop() {
    return Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP;
  }

  /**
   * Check if system version equal or bigger than android v7+ or not
   *
   * @return true if equal or bigger than android v7+
   */
  public static boolean isAboveNougat() {
    return Build.VERSION.SDK_INT >= Build.VERSION_CODES.N;
  }

  /**
   * Check app direction
   *
   * @param context of screen
   *
   * @return true if direction from right to left,
   * Will always return false if system version under sdk 17.
   */
  @SuppressWarnings("unused") public static boolean isRTL(Context context) {
    Configuration config = context.getResources().getConfiguration();
    return Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1
        && config.getLayoutDirection() == View.LAYOUT_DIRECTION_RTL;
  }

  /**
   * To change app font
   *
   * @param name of font
   */
  public static void initCalligraphy(String name) {
    CalligraphyConfig.initDefault(
        new CalligraphyConfig.Builder().setDefaultFontPath("fonts/" + name)
            .setFontAttrId(R.attr.fontPath)
            .build());
  }
}
