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
    SwitchCompat sw_dark,sw_notifi,sw_ring;
    SharedPreferences sharedPreferencesDark = null;
    SharedPreferences sharedPreferencesNotifi = null;
    SharedPreferences sharedPreferencesRing = null;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile_notification, container, false);
        bundle.putString("Callback", "Profile");
        initView(view);
        dark();
        notifi();
        ring();
        return view;
    }

    public void initView(View view) {
        sw_dark = view.findViewById(R.id.switch_dark);
        sw_notifi = view.findViewById(R.id.switch_ads);
        sw_ring= view.findViewById(R.id.switch_bell);
    }

    public void dark() {
        sharedPreferencesDark = getActivity().getSharedPreferences(AloneMain.NAME_NIGHTLIGHT, Context.MODE_PRIVATE);
        Boolean dark = sharedPreferencesDark.getBoolean(AloneMain.KEY_BL_NIGHTLIGHT, false);
        if (dark == true) {
            sw_dark.setChecked(true);
        }
        sw_dark.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean ba) {
                if (ba == true) {
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
                    sw_dark.setChecked(true);
                    SharedPreferences.Editor editor = sharedPreferencesDark.edit();
                    editor.putBoolean(AloneMain.KEY_BL_NIGHTLIGHT, true);
                    editor.commit();
                } else {
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
                    sw_dark.setChecked(false);
                    SharedPreferences.Editor editor = sharedPreferencesDark.edit();
                    editor.putBoolean(AloneMain.KEY_BL_NIGHTLIGHT, false);
                    editor.commit();
                }
            }
        });
    }
    public void notifi(){
        sharedPreferencesNotifi = getActivity().getSharedPreferences(AloneMain.NAME_NOTIFICATION , Context.MODE_PRIVATE);
        Boolean notifi = sharedPreferencesNotifi.getBoolean(AloneMain.KEY_BL_NOTIFICATION,true);
        if (notifi == true){
            sw_notifi.setChecked(true);
        }
        sw_notifi.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked == true){
                    sw_notifi.setChecked(true);
                    SharedPreferences.Editor editor = sharedPreferencesNotifi.edit();
                    editor.putBoolean(AloneMain.KEY_BL_NOTIFICATION, true);
                    editor.commit();
                }else {
                    sw_notifi.setChecked(false);
                    SharedPreferences.Editor editor = sharedPreferencesNotifi.edit();
                    editor.putBoolean(AloneMain.KEY_BL_NOTIFICATION, false);
                    editor.commit();
                }
            }
        });
    }
    public void ring(){
        sharedPreferencesRing = getActivity().getSharedPreferences(AloneMain.NAME_RING , Context.MODE_PRIVATE);
        Boolean ring = sharedPreferencesRing.getBoolean(AloneMain.KEY_BL_RING,true);
        if (ring == true){
            sw_ring.setChecked(true);
        }
        sw_ring.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked == true){
                    sw_ring.setChecked(true);
                    SharedPreferences.Editor editor = sharedPreferencesRing.edit();
                    editor.putBoolean(AloneMain.KEY_BL_RING, true);
                    editor.commit();
                }else {
                    sw_ring.setChecked(false);
                    SharedPreferences.Editor editor = sharedPreferencesRing.edit();
                    editor.putBoolean(AloneMain.KEY_BL_RING, false);
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
