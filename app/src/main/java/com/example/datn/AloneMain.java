package com.example.datn;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import com.example.datn.fragment.FragmentDaddy;
public class AloneMain extends AppCompatActivity {
    public final static String NAME_NIGHTLIGHT = "dark";
    public final static String NAME_LANGUAGE = "language";
    public final static String NAME_INTRO = "intro";
    public final static String NAME_AUTOLOGIN = "autologin";
    public static final String NAME_NOTIFICATION = "notifi";
    public static final String NAME_RING= "ring";
    public final static String KEY_BL_NIGHTLIGHT = "dark_mode";
    public final static String KEY_BL_LANGUAGE = "language";
    public final static String KEY_BL_FINISHINTRO = "finish";
    public final static String KEY_BL_AUTOLOGIN = "auto";
    public static final String KEY_ACCOUNTUSER= "accountUser";
    public static final String KEY_BL_NOTIFICATION= "notification";
    public static final String KEY_BL_RING= "blring";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        SharedPreferences sharedPreferences = getSharedPreferences(NAME_NIGHTLIGHT, Context.MODE_PRIVATE);
        Boolean night_mode = sharedPreferences.getBoolean(KEY_BL_NIGHTLIGHT, false);
        if (night_mode == true) {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
        }
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        /**
         * Start 18/09/22
         * Dev : Quang Huy
         * Email: huyanh0109@gmail.com
         * Copyright by Quang Huy
         * Application only use one MainActivity is AloneMain(^_^) based on Navigation Component
         * {@link https://developer.android.com/guide/navigation/navigation-getting-started}
         */
    }
}