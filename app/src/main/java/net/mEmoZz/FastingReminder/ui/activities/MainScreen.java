package net.mEmoZz.FastingReminder.ui.activities;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import butterknife.BindString;
import butterknife.BindView;
import butterknife.ButterKnife;
import java.util.Date;
import java.util.Locale;
import net.mEmoZz.FastingReminder.R;
import net.mEmoZz.FastingReminder.ui.fragments.SettingsFragment;
import net.mEmoZz.FastingReminder.utilities.Constants;
import net.mEmoZz.FastingReminder.utilities.PreferencesUtils;
import net.mEmoZz.FastingReminder.utilities.Utils;
import org.arabeyes.itl.hijri.HijriCalc;
import org.arabeyes.itl.hijri.SimpleHijriDate;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

/**
 * Authored by Mohamed Fathy on 20 Feb, 2017.
 * Contact: muhamed.gendy@gmail.com
 */

public class MainScreen extends AppCompatActivity {

  @BindView(R.id.toolbar) Toolbar toolbar;

  @BindString(R.string.app_name) String appName;

  private Activity context;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    context = this;
    applyFont();
    setContentView(R.layout.activity_main_screen);
    ButterKnife.bind(this);
    initToolbar();
    replaceFragment(new SettingsFragment());

    SimpleHijriDate date = HijriCalc.toHijri(new Date());
    Log.wtf("hijriDayName", date.getDayOfWeekName(Locale.getDefault()));
    Log.wtf("hijriDayOfWeek", date.getDayOfWeek() + "");
    Log.wtf("hijriDayOfMonth", date.getDayOfMonth() + 1 + "");
    Log.wtf("hijriMonth", date.getMonthName(Locale.getDefault()));
    Log.wtf("hijriYear", date.getYear() + "");

    if (date.getDayOfWeek() == 1) {
      System.out.println("الإثنين");
    } else if (date.getDayOfWeek() == 4) {
      System.out.println("الخميس");
    }
  }

  private void initToolbar() {
    setSupportActionBar(toolbar);
    ActionBar actionBar = getSupportActionBar();
    if (actionBar != null) actionBar.setTitle(appName);
  }

  private FragmentManager replaceFragment(Fragment fragment) {
    FragmentManager manager = ((FragmentActivity) context).getSupportFragmentManager();
    FragmentTransaction transaction = manager.beginTransaction();
    transaction.replace(R.id.content_main, fragment).commit();
    return manager;
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

  @Override protected void attachBaseContext(Context newBase) {
    super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
  }
}
