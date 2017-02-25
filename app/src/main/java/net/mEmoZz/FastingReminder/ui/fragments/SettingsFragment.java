package net.mEmoZz.FastingReminder.ui.fragments;

import android.app.Activity;
import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.preference.ListPreference;
import android.support.v7.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import butterknife.BindString;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import com.takisoft.fix.support.v7.preference.PreferenceFragmentCompat;
import net.mEmoZz.FastingReminder.R;
import net.mEmoZz.FastingReminder.language.Localization;
import net.mEmoZz.FastingReminder.services.NotifierService;
import net.mEmoZz.FastingReminder.utilities.PreferencesUtils;

/**
 * Authored by Mohamed Fathy on 20 Feb, 2017.
 * Contact: muhamed.gendy@gmail.com
 */

public class SettingsFragment extends PreferenceFragmentCompat
    implements SharedPreferences.OnSharedPreferenceChangeListener {

  @BindString(R.string.pref_key_lang_dialog) String language;
  @BindString(R.string.pref_key_enable_app) String enableApp;
  @BindString(R.string.pref_key_fasting_monday) String monday;
  @BindString(R.string.pref_key_fasting_thursday) String thursday;
  @BindString(R.string.pref_key_fasting_white_days) String whites;
  @BindString(R.string.pref_key_fasting_ashura) String ashura;

  private Activity context;
  private Unbinder unbinder;
  private boolean switchEnabled;

  @Override
  public void onCreatePreferencesFix(@Nullable Bundle savedInstanceState, String rootKey) {
    addPreferencesFromResource(R.xml.pref_general);
  }

  @SuppressWarnings("ConstantConditions") @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container,
      Bundle savedInstanceState) {
    View root = super.onCreateView(inflater, container, savedInstanceState);
    unbinder = ButterKnife.bind(this, root);
    if (isAdded()) initPrefs(getPreferenceManager());
    //text.setText("Hi " + new String(Character.toChars(0x1F60A)));
    return root;
  }

  @Override public void onAttach(Context context) {
    super.onAttach(context);
    this.context = getActivity();
  }

  @Override public void onDestroyView() {
    if (unbinder != null) unbinder.unbind();
    super.onDestroyView();
  }

  @Override public void onStart() {
    super.onStart();
    getPreferenceScreen().getSharedPreferences().registerOnSharedPreferenceChangeListener(this);
  }

  @Override public void onStop() {
    super.onStop();
    getPreferenceScreen().getSharedPreferences().unregisterOnSharedPreferenceChangeListener(this);
  }

  private void initPrefs(PreferenceManager manager) {
    PreferenceManager.setDefaultValues(context, R.xml.pref_general, false);

    switchEnabled = new PreferencesUtils(context).isAppEnabled();
    if (switchEnabled) startNotifierService();
    
    updateLangSummary((ListPreference) manager.findPreference(language));
  }

  private void updateLangSummary(ListPreference languagePrefs) {
    languagePrefs.setSummary(languagePrefs.getEntry());
  }

  private void startNotifierService() {
    Class notifier = NotifierService.class;
    if (!isServiceRunning(notifier)) context.startService(new Intent(context, notifier));
  }

  private void stopNotifierService() {
    context.stopService(new Intent(context, NotifierService.class));
  }

  private boolean isServiceRunning(Class<?> serviceClass) {
    ActivityManager manager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
    for (
        ActivityManager.RunningServiceInfo service : manager.getRunningServices(Integer.MAX_VALUE)
        ) {
      if (serviceClass.getName().equals(service.service.getClassName())) {
        return true;
      }
    }
    return false;
  }

  @Override public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
    if (key.equals(language)) {
      Localization.setLanguage(context, new PreferencesUtils(context).getLanguage());
      context.recreate();
    } else if (key.equals(enableApp)) {
      switchEnabled = new PreferencesUtils(context).isAppEnabled();
      if (switchEnabled) {
        startNotifierService();
      } else {
        stopNotifierService();
      }
    }
  }
}
