package com.example.myapp;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.preference.PreferenceManager;

import java.util.Locale;

public class Language {
    public static final String LANGUAGE_KEY = "language";

    public static void setLocale(Context context, String lang) {
        Locale locale = new Locale(lang);
        Locale.setDefault(locale);

        Configuration configuration = new Configuration();
        configuration.setLocale(locale);

        Resources resources = context.getResources();
        resources.updateConfiguration(configuration, resources.getDisplayMetrics());

        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(LANGUAGE_KEY, lang);
        editor.apply();
    }

    public static void updateLanguage(Context context) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        String language = preferences.getString(LANGUAGE_KEY, "");

        if (!language.isEmpty()) {
            setLocale(context, language);
        }
    }
}
