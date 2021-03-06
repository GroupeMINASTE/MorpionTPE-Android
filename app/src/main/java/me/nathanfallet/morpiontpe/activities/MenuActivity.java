package me.nathanfallet.morpiontpe.activities;

import android.content.Intent;
import android.preference.PreferenceManager;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatDelegate;
import android.view.View;
import android.view.Window;
import android.widget.Button;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import me.nathanfallet.morpiontpe.R;
import me.nathanfallet.morpiontpe.models.NotificationName;

public class MenuActivity extends AppCompatActivity {

    @Override
    public void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
    }

    @Override
    public void onStop() {
        super.onStop();
        EventBus.getDefault().unregister(this);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Read dark mode from preferences
        if (PreferenceManager.getDefaultSharedPreferences(this).getBoolean("isDarkMode", false)) {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
        }

        // Update the theme
        updateTheme();

        // Remove the title bar
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getSupportActionBar().hide();

        // Set content view
        setContentView(R.layout.activity_menu);

        // Listener for game
        View.OnClickListener gameListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MenuActivity.this, GameActivity.class);
                intent.putExtra("id", v.getId());
                startActivity(intent);
            }
        };

        // Listener for settings
        View.OnClickListener settingsListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MenuActivity.this, SettingsActivity.class);
                startActivity(intent);
            }
        };

        // Add actions to buttons
        Button button1 = findViewById(R.id.button1);
        Button button2 = findViewById(R.id.button2);
        Button button3 = findViewById(R.id.button3);
        Button settings = findViewById(R.id.settings);

        button1.setOnClickListener(gameListener);
        button2.setOnClickListener(gameListener);
        button3.setOnClickListener(gameListener);
        settings.setOnClickListener(settingsListener);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onThemeUpdated(NotificationName.ThemeUpdated updated) {
        updateTheme();
    }

    public void updateTheme() {
        // Check for dark mode
        if (AppCompatDelegate.getDefaultNightMode() == AppCompatDelegate.MODE_NIGHT_YES) {
            setTheme(R.style.AppThemeDark);
        } else {
            setTheme(R.style.AppThemeLight);
        }
    }

}
