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
import butterknife.BindString;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import com.takisoft.fix.support.v7.preference.PreferenceFragmentCompat;
import net.mEmoZz.FastingReminder.R;
import net.mEmoZz.FastingReminder.language.Localization;
import net.mEmoZz.FastingReminder.utilities.PreferencesUtils;

/**
 * Authored by Mohamed Fathy on 20 Feb, 2017.
 * Contact: muhamed.gendy@gmail.com
 */

public class SettingsFragment extends PreferenceFragmentCompat
    implements SharedPreferences.OnSharedPreferenceChangeListener {

  @BindString(R.string.pref_key_fasting_monday) String monday;
  @BindString(R.string.pref_key_fasting_thursday) String thursday;
  @BindString(R.string.pref_key_fasting_white_days) String whites;
  @BindString(R.string.pref_key_fasting_ashura) String ashura;

  private Activity context;
  private Unbinder unbinder;

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

    updateLangSummary(
        (ListPreference) manager.findPreference(getString(R.string.pref_key_lang_dialog))
    );
  }

  private void updateLangSummary(ListPreference languagePrefs) {
    languagePrefs.setSummary(languagePrefs.getEntry());
  }

  @Override public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
    if (key.equals(getString(R.string.pref_key_lang_dialog))) {
      Localization.setLanguage(context, new PreferencesUtils(context).getLanguage());
      context.setResult(Activity.RESULT_OK);
      context.recreate();
    }
  }
}
