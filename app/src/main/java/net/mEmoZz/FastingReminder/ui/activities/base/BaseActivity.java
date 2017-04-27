package net.mEmoZz.FastingReminder.ui.activities.base;

import android.app.ActivityManager;
import android.content.Context;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewTreeObserver;
import net.mEmoZz.FastingReminder.R;
import net.mEmoZz.FastingReminder.utilities.Constants;
import net.mEmoZz.FastingReminder.utilities.PreferencesUtils;
import net.mEmoZz.FastingReminder.utilities.Utils;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

/**
 * Authored by Mohamed Fathy on 25 Feb, 2017.
 * Contact: muhamed.gendy@gmail.com
 */

public class BaseActivity extends AppCompatActivity {

  @Override public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);

    applyTaskTheme();
    applyFont();
    setupAppAnimations();
  }

  private void applyTaskTheme() {
    if (Utils.isAboveLollipop()) {
      ActivityManager.TaskDescription description = new ActivityManager.TaskDescription(
          getString(R.string.app_name),
          BitmapFactory.decodeResource(getResources(), R.drawable.ic_logo_moon),
          ContextCompat.getColor(this, R.color.colorPrimary)
      );
      setTaskDescription(description);
    }
  }

  @Override public boolean onOptionsItemSelected(MenuItem item) {
    switch (item.getItemId()) {
      case android.R.id.home:
        supportFinishAfterTransition();
        break;
    }
    return super.onOptionsItemSelected(item);
  }

  @Override public void onBackPressed() {
    supportFinishAfterTransition();
  }

  @Override protected void attachBaseContext(Context newBase) {
    super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
  }

  private void applyFont() {
    switch (new PreferencesUtils(this).getLanguage()) {
      case Constants.LOCALE.LANG_ARABIC:
        Utils.initCalligraphy(Constants.FONTS.ARABIC_FONT);
        break;
      case Constants.LOCALE.LANG_ENGLISH:
        Utils.initCalligraphy(Constants.FONTS.ENGLISH_FONT);
        break;
    }
  }

  private void setupAppAnimations() {
    if (Utils.isAboveLollipop()) fixStatusNavigationBarsFromBlinking();
  }

  @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
  private void fixStatusNavigationBarsFromBlinking() {
    postponeEnterTransition();
    final View decor = getWindow().getDecorView();
    decor.getViewTreeObserver().addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener() {
      @Override
      public boolean onPreDraw() {
        startPostponedEnterTransition();
        decor.getViewTreeObserver().removeOnPreDrawListener(this);
        return true;
      }
    });
  }
}
