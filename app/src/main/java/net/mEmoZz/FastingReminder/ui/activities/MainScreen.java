package net.mEmoZz.FastingReminder.ui.activities;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.util.Pair;
import android.support.v7.preference.PreferenceManager;
import android.support.v7.widget.SwitchCompat;
import android.view.View;
import android.view.Window;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnCheckedChanged;
import butterknife.OnClick;
import java.util.ArrayList;
import java.util.List;
import net.mEmoZz.FastingReminder.R;
import net.mEmoZz.FastingReminder.ui.activities.base.BaseActivity;
import net.mEmoZz.FastingReminder.utilities.AlarmUtils;
import net.mEmoZz.FastingReminder.utilities.PreferencesUtils;
import net.mEmoZz.FastingReminder.utilities.Utils;

/**
 * Authored by Mohamed Fathy on 25 Feb, 2017.
 * Contact: muhamed.gendy@gmail.com
 */

public class MainScreen extends BaseActivity {

  private static final int REQUEST_LANGUAGE_CHANGE = 1;

  @BindView(R.id.enableService) SwitchCompat enableService;

  private Activity context;

  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    context = this;
    setContentView(R.layout.activity_main_screen);
    ButterKnife.bind(this);
    Utils.setTranslucentBarWithPadding(context, null);

    initSwitch();
  }

  private void initSwitch() {
    if (new PreferencesUtils(context).isAppEnabled()) enableService.setChecked(true);
  }

  @OnCheckedChanged(R.id.enableService) public void onChecked(boolean isChecked) {
    SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
    prefs.edit().putBoolean(PreferencesUtils.KEY_ENABLE_SERVICE, isChecked).apply();

    if (isChecked) AlarmUtils.setAlarm(context);
  }

  @OnClick(R.id.btnSettings) public void onClick() {
    startActivity();
  }

  @SuppressWarnings("unchecked") private void startActivity() {
    Intent intent = new Intent(context, SettingsScreen.class);
    if (Utils.isAboveLollipop()) {
      View navigationBar = context.findViewById(android.R.id.navigationBarBackground);
      List<Pair<View, String>> pairs = new ArrayList<>();
      pairs.add(Pair.create(navigationBar, Window.NAVIGATION_BAR_BACKGROUND_TRANSITION_NAME));
      ActivityOptionsCompat options = ActivityOptionsCompat.makeSceneTransitionAnimation(
          context,
          pairs.toArray(new Pair[pairs.size()])
      );
      startActivityForResult(intent, REQUEST_LANGUAGE_CHANGE, options.toBundle());
    } else {
      startActivityForResult(intent, REQUEST_LANGUAGE_CHANGE);
    }
  }

  @Override protected void onActivityResult(int requestCode, int resultCode, Intent data) {
    switch (requestCode) {
      case REQUEST_LANGUAGE_CHANGE:
        if (resultCode == RESULT_OK) recreate();
        break;
    }
  }
}
