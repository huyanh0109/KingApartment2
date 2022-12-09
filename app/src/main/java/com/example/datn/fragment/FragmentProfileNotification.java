package com.example.datn.fragment;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.appcompat.widget.SwitchCompat;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import com.example.datn.AloneMain;
import com.example.datn.R;

public class FragmentProfileNotification extends Fragment {
    Bundle bundle = new Bundle();
    SwitchCompat sw_dark;
    SharedPreferences sharedPreferences = null;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile_notification, container, false);
        bundle.putString("Callback", "Profile");
        initView(view);
        dark();
        return view;
    }

    public void initView(View view) {
        sw_dark = view.findViewById(R.id.switch_dark);
    }

    public void dark() {
        sharedPreferences = getActivity().getSharedPreferences(AloneMain.NAME_NIGHTLIGHT, Context.MODE_PRIVATE);
        Boolean bl = sharedPreferences.getBoolean(AloneMain.KEY_BL_NIGHTLIGHT, false);
        if (bl == true) {
            sw_dark.setChecked(true);
        }
        sw_dark.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean ba) {
                if (ba == true) {
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
                    sw_dark.setChecked(true);
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putBoolean("dark_mode", true);
                    editor.commit();
                } else {
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
                    sw_dark.setChecked(false);
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putBoolean("dark_mode", false);
                    editor.commit();
                }
            }
        });
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        OnBackPressedCallback callback = new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
                NavHostFragment.findNavController(FragmentProfileNotification.this).navigate(R.id.action_fragmentProfileNotification_to_fragmentDaddy, bundle);
            }
        };
        requireActivity().getOnBackPressedDispatcher().addCallback(this, callback);
        ;

    }
}
