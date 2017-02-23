package net.mEmoZz.FastingReminder.ui.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

/**
 * Authored by Mohamed Fathy on 22 Feb, 2017.
 * Contact: muhamed.gendy@gmail.com
 */

public class SplashScreen extends AppCompatActivity {

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    startActivity();
  }

  private void startActivity() {
    startActivity(new Intent(this, MainScreen.class));
    finish();
  }
}
