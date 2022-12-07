package com.example.datn.fragment;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import com.example.datn.R;

public class FragmentSplash extends Fragment {
    public final static String KEY_FINISHINTRO = "finish";
    public final static String KEY_AUTOLOGIN = "auto";
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_splash, container, false);
        Handler handler = new Handler();


            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    if (finishIntro()) {
                        if (autoLogin()) {
                            Navigation.findNavController(view).navigate(R.id.action_fragmentSplash_to_fragmentDaddy);
                        } else {
                            Navigation.findNavController(view).navigate(R.id.action_fragmentSplash_to_fragmentSignin);
                        }
                    } else {
                        Navigation.findNavController(view).navigate(R.id.action_fragmentSplash_to_fragmentIntro);
                    }
                }
            }, 2000);

        return view;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        OnBackPressedCallback  callback = new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
                getActivity().finishAffinity();
            }
        };
        requireActivity().getOnBackPressedDispatcher().addCallback(this, callback);
        super.onCreate(savedInstanceState);
    }

    private boolean finishIntro() {
        SharedPreferences preferences = getActivity().getSharedPreferences("intro", Context.MODE_PRIVATE);
        return preferences.getBoolean(KEY_FINISHINTRO, false);
    }

    private boolean autoLogin() {
        SharedPreferences preferences = getActivity().getSharedPreferences("autologin", Context.MODE_PRIVATE);
        return preferences.getBoolean(KEY_AUTOLOGIN, false);
    }

}
