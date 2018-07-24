package com.example.sveto.zavrsnirad.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

public class PreferenceUtils {

    private static String KEY_PASSWORD = "password";
    public static String KEY_EMAIL = "email";
    private static String KEY_WEIGHT = "weight";
    public static String KEY_HEIGHT = "height";
    private static String KEY_YEARS = "years";
    private static String KEY_COUNTER="counter";

    public PreferenceUtils() {
    }


    public static boolean saveCounter(int counter,Context context){
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor prefsEditor = prefs.edit();
        prefsEditor.putInt(KEY_COUNTER, counter);
        prefsEditor.apply();
        return true;
    }

    public static int getCounter(Context context) {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        return prefs.getInt(KEY_COUNTER, 0);
    }



    public static boolean saveEmail(String email, Context context) {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor prefsEditor = prefs.edit();
        prefsEditor.putString(KEY_EMAIL, email);
        prefsEditor.apply();
        return true;
    }

    public static String getEmail(Context context) {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        return prefs.getString(KEY_EMAIL, null);
    }

    public static boolean savePassword(String password, Context context) {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor prefsEditor = prefs.edit();
        prefsEditor.putString(KEY_PASSWORD, password);
        prefsEditor.apply();
        return true;
    }


    public static boolean saveWeight(float weight, Context context) {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor prefsEditor = prefs.edit();
        prefsEditor.putFloat(KEY_WEIGHT, weight);
        prefsEditor.apply();
        return true;
    }

    public static float getWeight(Context context) {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        return prefs.getFloat(KEY_WEIGHT, 0);
    }

    public static boolean saveHeight(float height, Context context) {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor prefsEditor = prefs.edit();
        prefsEditor.putFloat(KEY_HEIGHT, height);
        prefsEditor.apply();
        return true;
    }

    public static float getHeight(Context context) {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        return prefs.getFloat(KEY_HEIGHT, 0);
    }


    public static boolean saveYears(int years, Context context) {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor prefsEditor = prefs.edit();
        prefsEditor.putInt(KEY_YEARS, years);
        prefsEditor.apply();
        return true;
    }

    public static int getYears(Context context) {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        return prefs.getInt(KEY_YEARS, 0);
    }
}
