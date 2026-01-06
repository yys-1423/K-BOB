package com.SPISE.k_bob;// ProfileManager.java
import android.content.Context;
import android.content.SharedPreferences;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.Set;

public class ProfileManager {
    private static final String PROFILES_KEY = "profiles";
    private static final String PREFERENCES_PREFIX = "profile_";
    private static final String MAIN_PREFS = "MainPreferences";
    private static final String CURRENT_PROFILE_KEY = "current_profile";

    // Save a new profile or update existing preferences
    public static void saveProfile(Context context, String profileName, Map<String, Boolean> preferences) {
        SharedPreferences prefs = context.getSharedPreferences(PREFERENCES_PREFIX + profileName, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        for (Map.Entry<String, Boolean> entry : preferences.entrySet()) {
            editor.putBoolean(entry.getKey(), entry.getValue());
        }
        editor.apply();
        addProfileToList(context, profileName);
    }

    // Load preferences for a specific profile
    public static Map<String, Boolean> loadProfile(Context context, String profileName) {
        Map<String, Boolean> preferences = new HashMap<>();
        SharedPreferences prefs = context.getSharedPreferences(PREFERENCES_PREFIX + profileName, Context.MODE_PRIVATE);
        preferences.put("avoid_beef", prefs.getBoolean("avoid_beef", false));
        preferences.put("avoid_pork", prefs.getBoolean("avoid_pork", false));
        preferences.put("avoid_shellfish", prefs.getBoolean("avoid_shellfish", false));
        preferences.put("avoid_fish", prefs.getBoolean("avoid_fish", false));
        preferences.put("avoid_peanut", prefs.getBoolean("avoid_peanut", false));
        preferences.put("avoid_chicken", prefs.getBoolean("avoid_chicken", false));
        preferences.put("avoid_lamb", prefs.getBoolean("avoid_lamb", false));
        preferences.put("avoid_egg", prefs.getBoolean("avoid_egg", false));
        preferences.put("avoid_dairy", prefs.getBoolean("avoid_dairy", false));
        preferences.put("avoid_flour", prefs.getBoolean("avoid_flour", false));
        return preferences;
    }

    // Add a profile to the list of all profiles
    private static void addProfileToList(Context context, String profileName) {
        SharedPreferences prefs = context.getSharedPreferences(MAIN_PREFS, Context.MODE_PRIVATE);
        Set<String> profiles = prefs.getStringSet(PROFILES_KEY, new HashSet<>());
        profiles.add(profileName);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putStringSet(PROFILES_KEY, profiles);
        editor.apply();
    }

    // Retrieve a list of all profile names
    public static List<String> getAllProfiles(Context context) {
        SharedPreferences prefs = context.getSharedPreferences(MAIN_PREFS, Context.MODE_PRIVATE);
        Set<String> profiles = prefs.getStringSet(PROFILES_KEY, new HashSet<>());
        return new ArrayList<>(profiles);
    }

    // Save the current profile name
    public static void setCurrentProfile(Context context, String profileName) {
        SharedPreferences prefs = context.getSharedPreferences(MAIN_PREFS, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString(CURRENT_PROFILE_KEY, profileName);
        editor.apply();
    }

    // Retrieve the current profile name
    public static String getCurrentProfile(Context context) {
        SharedPreferences prefs = context.getSharedPreferences(MAIN_PREFS, Context.MODE_PRIVATE);
        return prefs.getString(CURRENT_PROFILE_KEY, null);
    }
}
