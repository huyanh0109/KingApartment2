package com.example.datn.fragment;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import com.example.datn.R;

import java.util.Locale;

public class FragmentSignin extends Fragment {
    Button btn_signin;
    SharedPreferences sharedPreferences;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_signin, container, false);
        btn_signin = view.findViewById(R.id.btn_signin);
        sharedPreferences = getActivity().getSharedPreferences("dark", Context.MODE_PRIVATE);
        Boolean bl = sharedPreferences.getBoolean("dark_mode", false);
        if (bl == true) {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        }
        btn_signin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Navigation.findNavController(view).navigate(R.id.action_fragmentSignin_to_fragmentDaddy);
            }
        });
        return view;
    }
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        OnBackPressedCallback callback = new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
                Intent setIntent = new Intent(Intent.ACTION_MAIN);
                setIntent.addCategory(Intent.CATEGORY_HOME);
                setIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(setIntent);
            }
        };
        requireActivity().getOnBackPressedDispatcher().addCallback(this, callback);
        ;
        super.onCreate(savedInstanceState);
        sharedPreferences = getActivity().getSharedPreferences("language", Context.MODE_PRIVATE);
        String language = sharedPreferences.getString("language", "en");
        String languageToLoad = language;
        Locale locale = new Locale(languageToLoad);
        Locale.setDefault(locale);
        Configuration config = new Configuration();
        config.locale = locale;
        getActivity().getResources().updateConfiguration(config,
                getActivity().getResources().getDisplayMetrics());
    }

}
