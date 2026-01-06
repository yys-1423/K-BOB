package com.SPISE.k_bob;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.fragment.app.Fragment;

public class SettingsFragment extends Fragment {
    private static final String PREFS_NAME = "profiles_preferences";
    private static final String ALL_PROFILES_PREFS = "all_profiles";
    private static final String ACTIVE_PROFILE = "active_profile";
    private static final String DARK_MODE_PREF = "dark_mode";
    private static final String PREFS_NAME1 = "language_preferences";
    private static final String LANGUAGE_KEY = "language";
    private TextView profileNameText;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_settings, container, false);
        view.setClickable(true); // Ensure the fragment intercepts touch events

        // Initialize profile name TextView
        profileNameText = view.findViewById(R.id.text_current_profile_name);

        // Retrieve current profile name and display it
        SharedPreferences preferences = requireActivity().getSharedPreferences(PREFS_NAME, getContext().MODE_PRIVATE);
        SharedPreferences allProfilesPrefs = requireActivity().getSharedPreferences(ALL_PROFILES_PREFS, getContext().MODE_PRIVATE);
        String activeProfileId = preferences.getString(ACTIVE_PROFILE, "");
        String currentProfileName = allProfilesPrefs.getString(activeProfileId + "_name", "No Profile Selected");

        profileNameText.setText("Current Profile: " + currentProfileName);

        // Initialize buttons
        LinearLayout manageProfileButton = view.findViewById(R.id.button_manage_profile);
        LinearLayout personalSettingsButton = view.findViewById(R.id.button_personal_settings);
        LinearLayout darkModeButton = view.findViewById(R.id.button_dark_mode);
        LinearLayout languageButton = view.findViewById(R.id.button_language);

        // Manage Profile
        manageProfileButton.setOnClickListener(v -> {
            Intent intent = new Intent(getActivity(), ManageProfileActivity.class);
            startActivity(intent);
        });

        // Personal Settings
        personalSettingsButton.setOnClickListener(v -> {
            Intent intent = new Intent(getActivity(), PersonalSettingsActivity.class);
            intent.putExtra("add", "False");
            startActivity(intent);
        });

        // Enable Dark Mode
        darkModeButton.setOnClickListener(v -> {
            // Toggle dark mode functionality
            boolean isDarkMode = preferences.getBoolean(DARK_MODE_PREF, false);
            SharedPreferences.Editor editor = preferences.edit();
            if (isDarkMode) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
                editor.putBoolean(DARK_MODE_PREF, false);
            } else {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
                editor.putBoolean(DARK_MODE_PREF, true);
            }
            editor.apply();
        });

        // Change Language
        languageButton.setOnClickListener(v -> toggleLanguage());

        return view;
    }

    private void toggleLanguage() {
        SharedPreferences preferences = getActivity().getSharedPreferences(PREFS_NAME1, Context.MODE_PRIVATE);
        String currentLanguage = preferences.getString(LANGUAGE_KEY, "en"); // Default to English
        String newLanguage = currentLanguage.equals("en") ? "ko" : "en"; // Toggle between English and Korean

        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(LANGUAGE_KEY, newLanguage);
        editor.apply();

        LocaleHelper.setLocale(getActivity(), newLanguage);
        getActivity().recreate(); // Recreate the current activity to reflect the language change
    }

    @Override
    public void onResume() {
        super.onResume();
        // Retrieve current profile name from shared preferences
        SharedPreferences preferences = requireActivity().getSharedPreferences(PREFS_NAME, getContext().MODE_PRIVATE);
        SharedPreferences allProfilesPrefs = requireActivity().getSharedPreferences(ALL_PROFILES_PREFS, getContext().MODE_PRIVATE);
        String activeProfileId = preferences.getString(ACTIVE_PROFILE, "");
        String currentProfileName = allProfilesPrefs.getString(activeProfileId + "_name", "No Profile Selected");

        // Update profile name text view
        String currentProfileText = getString(R.string.current_profile, currentProfileName);
        profileNameText.setText(currentProfileText);
    }
}
