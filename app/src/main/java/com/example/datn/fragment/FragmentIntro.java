package com.example.datn.fragment;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.Preference;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.activity.OnBackPressedCallback;
import androidx.activity.OnBackPressedDispatcher;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.viewpager2.widget.ViewPager2;

import com.example.datn.IntroAdapter;
import com.example.datn.R;

import me.relex.circleindicator.CircleIndicator3;

public class FragmentIntro extends Fragment {
    public static ViewPager2 vpg_intro;
    Button btn_next_intro;
    TextView btn_skip_intro;
    IntroAdapter introAdapter;
    CircleIndicator3 circleIndicator3;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_sheet_intro, container, false);
        btn_next_intro = view.findViewById(R.id.btn_intro);
        btn_skip_intro = view.findViewById(R.id.tv_skip_intro);
        vpg_intro = view.findViewById(R.id.vpg_intro);
        introAdapter = new IntroAdapter(getActivity());
        vpg_intro.setAdapter(introAdapter);
        circleIndicator3 = view.findViewById(R.id.icon_next);
        circleIndicator3.setViewPager(vpg_intro);
        btn_next_intro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                vpg_intro.setCurrentItem(getItem(+1), true);
                if (getItem(+1) == 3) {
                    btn_next_intro.setText(R.string.discover);
                    btn_next_intro.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            Navigation.findNavController(view).navigate(R.id.action_fragmentIntro_to_fragmentSignin);
                            finishedIntro();
                        }
                    });
                }
            }
        });
        btn_skip_intro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Navigation.findNavController(view).navigate(R.id.action_fragmentIntro_to_fragmentSignin);
            }
        });
        return view;
    }
    private int getItem(int i) {
        return vpg_intro.getCurrentItem() + i;
    }
    private void finishedIntro(){
        SharedPreferences preferences = getActivity().getSharedPreferences("intro",Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putBoolean("finish",true);
        editor.apply();
    }

}
