package net.mEmoZz.FastingReminder.ui.activities;

import android.app.Activity;
import android.app.ActivityManager;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import butterknife.BindBitmap;
import butterknife.BindColor;
import butterknife.BindString;
import butterknife.BindView;
import butterknife.ButterKnife;
import net.mEmoZz.FastingReminder.R;
import net.mEmoZz.FastingReminder.ui.activities.base.BaseActivity;
import net.mEmoZz.FastingReminder.ui.fragments.SettingsFragment;
import net.mEmoZz.FastingReminder.utilities.Utils;

/**
 * Authored by Mohamed Fathy on 20 Feb, 2017.
 * Contact: muhamed.gendy@gmail.com
 */

public class SettingsScreen extends BaseActivity {

  @BindView(R.id.toolbar) Toolbar toolbar;

  @BindBitmap(R.drawable.ic_logo_moon) Bitmap logo;

  @BindString(R.string.app_name) String appName;

  @BindColor(R.color.colorPrimary) int colorPrimary;

  private Activity context;

  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    context = this;
    setContentView(R.layout.activity_settings_screen);
    ButterKnife.bind(this);
    initToolbar();
    replaceFragment(new SettingsFragment());
  }

  private void initToolbar() {
    if (Utils.isAboveLollipop()) {
      setTaskDescription(new ActivityManager.TaskDescription(appName, logo, colorPrimary));
    }
    setSupportActionBar(toolbar);
    ActionBar actionBar = getSupportActionBar();
    if (actionBar != null) actionBar.setTitle(appName);
    Utils.setTranslucentBarWithPadding(context, toolbar);
  }

  private FragmentManager replaceFragment(Fragment fragment) {
    FragmentManager manager = ((FragmentActivity) context).getSupportFragmentManager();
    FragmentTransaction transaction = manager.beginTransaction();
    transaction.replace(R.id.content_main, fragment).commit();
    return manager;
  }
}
