package com.example.datn;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.datn.fragment.FragmentDaddy;

public class AloneMain extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
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