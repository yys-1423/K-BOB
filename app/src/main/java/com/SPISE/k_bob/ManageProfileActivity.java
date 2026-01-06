package com.SPISE.k_bob;// ManageProfileActivity.java
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

public class ManageProfileActivity extends AppCompatActivity {
    private ListView listViewProfiles;
    private List<Profile> profiles;
    private ArrayAdapter<String> adapter;
    private static final String ALL_PROFILES_PREFS = "all_profiles";
    private static final String PREFS_NAME = "profiles_preferences";
    private static final String ACTIVE_PROFILE = "active_profile";
    private Button addProfileButton;
    private String currentProfileId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_profile);

        listViewProfiles = findViewById(R.id.list_profiles);
        addProfileButton = findViewById(R.id.button_add_profile);

        // Set up "Add Profile" button listener
        addProfileButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Start AddProfileActivity to create a new profile
                Log.d("tttttt", "dd"+profiles.size());
                if (profiles.size() <=4) {
                    Intent intent = new Intent(ManageProfileActivity.this, PersonalSettingsActivity.class);
                    intent.putExtra("add", "True");
                    startActivity(intent);
                }
                else{
                    Toast.makeText(ManageProfileActivity.this, "Cannot create more than 5 profiles", Toast.LENGTH_SHORT).show();
                }
            }
        });

        // Retrieve the current active profile
        SharedPreferences preferences = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
        currentProfileId = preferences.getString(ACTIVE_PROFILE, "");

        // Retrieve existing profiles and update the list
        profiles = getProfilesList();
        updateProfileList();
    }

    @Override
    protected void onResume() {
        super.onResume();
        SharedPreferences preferences = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
        currentProfileId = preferences.getString(ACTIVE_PROFILE, "");
        profiles = getProfilesList();
        updateProfileList();
    }

    private List<Profile> getProfilesList() {
        List<Profile> profiles = new ArrayList<>();
        SharedPreferences allProfilesPrefs = getSharedPreferences(ALL_PROFILES_PREFS, MODE_PRIVATE);

        for (String key : allProfilesPrefs.getAll().keySet()) {
            if (key.endsWith("_name")) {
                String id = key.replace("_name", "");
                String name = allProfilesPrefs.getString(key, "");
                profiles.add(new Profile(id, name));
            }
        }
        return profiles;
    }

    private void updateProfileList() {
        List<String> profileNames = new ArrayList<>();
        for (Profile profile : profiles) {
            profileNames.add(profile.getName());
        }

        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, profileNames);
        listViewProfiles.setAdapter(adapter);

        // Handle long click for deleting a profile
        listViewProfiles.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                Profile selectedProfile = profiles.get(position);
                confirmDeleteProfile(selectedProfile);
                return true;
            }
        });
        listViewProfiles.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Profile selectedProfile = profiles.get(position);
                switchToProfile(selectedProfile);
                finish(); // Return to the ManageProfileActivity
            }
        });
    }

    // Confirm profile deletion
    private void confirmDeleteProfile(final Profile profile) {
        if (profile.getId().equals(currentProfileId)) {
            // Prevent deleting the active profile
            Toast.makeText(this, "Cannot delete the currently active profile.", Toast.LENGTH_SHORT).show();
            return;
        }

        // Show confirmation dialog
        new AlertDialog.Builder(this)
                .setTitle("Delete Profile")
                .setMessage("Are you sure you want to delete this profile?")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        deleteProfile(profile);
                    }
                })
                .setNegativeButton("No", null)
                .show();
    }

    // Delete the profile and update the list
    private void deleteProfile(Profile profile) {
        SharedPreferences allProfilesPrefs = getSharedPreferences(ALL_PROFILES_PREFS, MODE_PRIVATE);
        SharedPreferences.Editor allProfilesEditor = allProfilesPrefs.edit();
        allProfilesEditor.remove(profile.getId() + "_name");
        allProfilesEditor.apply();

        SharedPreferences preferences = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
        SharedPreferences.Editor prefsEditor = preferences.edit();
        prefsEditor.remove(profile.getId() + "_avoid_beef");
        prefsEditor.remove(profile.getId() + "_avoid_pork");
        prefsEditor.remove(profile.getId() + "_avoid_shellfish");
        prefsEditor.remove(profile.getId() + "_avoid_fish");
        prefsEditor.remove(profile.getId() + "_avoid_peanut");
        prefsEditor.remove(profile.getId() + "_avoid_chicken");
        prefsEditor.remove(profile.getId() + "_avoid_lamb");
        prefsEditor.remove(profile.getId() + "_avoid_egg");
        prefsEditor.remove(profile.getId() + "_avoid_dairy");
        prefsEditor.remove(profile.getId() + "_avoid_flour");
        prefsEditor.remove(profile.getId() + "_is_vegan");
        prefsEditor.remove(profile.getId() + "_is_lacto");
        prefsEditor.remove(profile.getId() + "_is_ovo");
        prefsEditor.remove(profile.getId() + "_is_lacto-ovo");
        prefsEditor.remove(profile.getId() + "_is_pollotarian");
        prefsEditor.remove(profile.getId() + "_is_pescatarian");
        prefsEditor.remove(profile.getId() + "_avoid_tree_nuts");
        prefsEditor.remove(profile.getId() + "_avoid_soy");
        prefsEditor.remove(profile.getId() + "_avoid_sesame");
        prefsEditor.remove(profile.getId() + "_avoid_wheat");
        prefsEditor.remove(profile.getId() + "_avoid_corn");
        prefsEditor.remove(profile.getId() + "_avoid_gluten");
        prefsEditor.remove(profile.getId() + "_avoid_mustard");
        prefsEditor.remove(profile.getId() + "_avoid_celery");
        prefsEditor.remove(profile.getId() + "_avoid_sulfites");
        prefsEditor.remove(profile.getId() + "_avoid_lupin");
        prefsEditor.apply();

        profiles = getProfilesList(); // Refresh profiles list
        updateProfileList(); // Update ListView

        Toast.makeText(this, "Profile deleted successfully.", Toast.LENGTH_SHORT).show();
    }

    // Switch to the specified profile by storing the active profile's ID
    private void switchToProfile(Profile profile) {
        SharedPreferences preferences = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(ACTIVE_PROFILE, profile.getId());
        editor.apply();

        Toast.makeText(this, "Switched to profile: " + profile.getName(), Toast.LENGTH_SHORT).show();
    }
}
