package net.mEmoZz.FastingReminder.utilities;

import android.app.Activity;
import android.os.Build;
import android.support.v7.widget.Toolbar;
import android.view.WindowManager;
import net.mEmoZz.FastingReminder.R;
import uk.co.chrisjenx.calligraphy.CalligraphyConfig;

/**
 * Authored by Mohamed Fathy on 20 Feb, 2017.
 * Contact: muhamed.gendy@gmail.com
 */

public class Utils {

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

  /**
   * Make status bar transparent with little shade and remove toolbar padding
   *
   * Note: Make sure to set toolbar height as wrap_content
   *
   * @param context of screen
   * @param toolbar to remove padding
   */
  public static void setTranslucentBarWithPadding(Activity context, Toolbar toolbar) {
    if (Utils.isAboveLollipop()) {
      context.getWindow()
          .setFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS,
              WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
      if (toolbar != null) toolbar.setPadding(0, getStatusBarHeight(context), 0, 0);
    }
  }

  private static int getStatusBarHeight(Activity context) {
    int result = 0;
    int resourceId = context.getResources().getIdentifier("status_bar_height", "dimen", "android");
    return resourceId > 0 ? context.getResources().getDimensionPixelSize(resourceId) : result;
  }
}
