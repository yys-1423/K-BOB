package com.SPISE.k_bob;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.content.res.Resources;
import java.util.Locale;

public class LocaleHelper {
    public static void loadLocale(Context context) {
        SharedPreferences preferences = context.getSharedPreferences("language_preferences", Context.MODE_PRIVATE);
        String language = preferences.getString("language", "en");  // Default to English
        setLocale(context, language);
    }

    public static void setLocale(Context context, String languageCode) {
        Locale locale = new Locale(languageCode);
        Locale.setDefault(locale);
        Resources resources = context.getResources();
        Configuration config = resources.getConfiguration();
        config.setLocale(locale);
        resources.updateConfiguration(config, resources.getDisplayMetrics());
    }
}
