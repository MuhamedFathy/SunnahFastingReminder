package net.mEmoZz.FastingReminder.ui.fragments;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.preference.ListPreference;
import android.support.v7.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.takisoft.fix.support.v7.preference.PreferenceFragmentCompat;
import net.mEmoZz.FastingReminder.R;
import net.mEmoZz.FastingReminder.tools.language.Localization;

/**
 * Authored by Mohamed Fathy on 20 Feb, 2017.
 * Contact: muhamed.gendy@gmail.com
 */

public class SettingsFragment extends PreferenceFragmentCompat
    implements SharedPreferences.OnSharedPreferenceChangeListener {

  private Activity context;
  private ListPreference languagePrefs;

  @Override
  public void onCreatePreferencesFix(@Nullable Bundle savedInstanceState, String rootKey) {
    addPreferencesFromResource(R.xml.pref_general);
  }

  @Override public View onCreateView(LayoutInflater inflater, ViewGroup container,
      Bundle savedInstanceState) {
    if (isAdded()) initPrefs(getPreferenceManager());
    //text.setText("Hi " + new String(Character.toChars(0x1F60A)));
    return super.onCreateView(inflater, container, savedInstanceState);
  }

  @Override public void onAttach(Context context) {
    super.onAttach(context);
    this.context = getActivity();
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
    // Applying default prefs values
    PreferenceManager.setDefaultValues(context, R.xml.pref_general, false);

    languagePrefs =
        (ListPreference) manager.findPreference(getString(R.string.pref_key_lang_dialog));

    languagePrefs.setOnPreferenceChangeListener((preference, newValue) -> {
      Localization.setLanguage(context, Integer.parseInt(newValue.toString()));
      context.recreate();
      return true;
    });

    updateSummary();
  }

  private void updateSummary() {
    languagePrefs.setSummary(languagePrefs.getEntry());
  }

  @Override public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
    updateSummary();
  }
}
