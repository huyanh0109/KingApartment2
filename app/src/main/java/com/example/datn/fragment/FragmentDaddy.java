package com.example.datn.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.ui.AppBarConfiguration;

import com.example.datn.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

public class FragmentDaddy extends Fragment {
    FragmentHome fragmentHome = new FragmentHome();
    FragmentFavorite fragmentFavorite = new FragmentFavorite();
    FragmentNotification fragmentNotification = new FragmentNotification();
    FragmentProfile fragmentProfile = new FragmentProfile();
    BottomNavigationView navView;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.fragment_daddy,container,false);
        navView = view.findViewById(R.id.bottom_nav);
        navView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.nav_home:
                    getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.main_container,fragmentHome).commit();
                    return true;
                    case R.id.nav_favorite:
                        getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.main_container,fragmentFavorite).commit();
                        return true;
                    case R.id.nav_notification:
                        getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.main_container,fragmentNotification).commit();
                        return true;
                    case R.id.nav_profile:
                        getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.main_container,fragmentProfile).commit();
                        return true;
                }
                return false;
            }
        });

        return view;
    }

}
