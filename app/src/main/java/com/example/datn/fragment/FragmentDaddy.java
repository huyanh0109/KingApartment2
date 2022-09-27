package com.example.datn.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.datn.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

import java.util.ArrayDeque;
import java.util.Deque;

public class FragmentDaddy extends Fragment {
    BottomNavigationView navView;
    Deque<Integer> integers = new ArrayDeque<>(4);
    boolean flag = true;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_daddy, container, false);
        navView = view.findViewById(R.id.bottom_nav);
        if (getBoolean(getArguments(), "ProfileTerms") != null) {
            integers.push(R.id.nav_profile);
            replaceFragment(new FragmentProfile());
            navView.setSelectedItemId(R.id.nav_profile);
            navView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
                                                  @Override
                                                  public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                                                      int id = item.getItemId();
                                                      if (integers.contains(id)) {
                                                          if (id == R.id.nav_profile) {
                                                              if (integers.size() != 1) {
                                                                  if (flag) {
                                                                      integers.addFirst(R.id.nav_profile);
                                                                      flag = false;
                                                                  }
                                                              }
                                                          }
                                                          integers.remove(id);
                                                      }
                                                      integers.push(id);
                                                      replaceFragment(getFragmentProfile(item.getItemId()));

                                                      return false;
                                                  }
                                              }
            );
        } else if (getBoolean(getArguments(), "ProfileTerms") == null) {
            integers.push(R.id.nav_home);
            replaceFragment(new FragmentHome());
            navView.setSelectedItemId(R.id.nav_home);
            navView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
                                                  @Override
                                                  public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                                                      int id = item.getItemId();
                                                      if (integers.contains(id)) {
                                                          if (id == R.id.nav_home) {
                                                              if (integers.size() != 1) {
                                                                  if (flag) {
                                                                      integers.addFirst(R.id.nav_home);
                                                                      flag = false;
                                                                  }
                                                              }
                                                          }
                                                          integers.remove(id);
                                                      }
                                                      integers.push(id);
                                                      replaceFragment(getFragmentHome(item.getItemId()));

                                                      return false;
                                                  }
                                              }
            );
        }
        return view;
    }

    public static Boolean getBoolean(Bundle arguments, String key) {
        if (arguments != null && arguments.containsKey("ProfileTerms")) {
            return arguments.getBoolean(key);
        } else {
            return null;
        }
    }

    private Fragment getFragmentHome(int itemId) {
        switch (itemId) {
            case R.id.nav_home:
                navView.getMenu().getItem(0).setChecked(true);
                return new FragmentHome();
            case R.id.nav_favorite:
                navView.getMenu().getItem(1).setChecked(true);
                return new FragmentFavorite();
            case R.id.nav_notification:
                navView.getMenu().getItem(2).setChecked(true);
                return new FragmentNotification();
            case R.id.nav_profile:
                navView.getMenu().getItem(3).setChecked(true);
                return new FragmentProfile();
        }
        navView.getMenu().getItem(0).setChecked(true);
        return new FragmentHome();
    }

    private Fragment getFragmentProfile(int itemId) {
        switch (itemId) {
            case R.id.nav_home:
                navView.getMenu().getItem(0).setChecked(true);
                return new FragmentHome();
            case R.id.nav_favorite:
                navView.getMenu().getItem(1).setChecked(true);
                return new FragmentFavorite();
            case R.id.nav_notification:
                navView.getMenu().getItem(2).setChecked(true);
                return new FragmentNotification();
            case R.id.nav_profile:
                navView.getMenu().getItem(3).setChecked(true);
                return new FragmentProfile();
        }
        navView.getMenu().getItem(3).setChecked(true);
        return new FragmentProfile();
    }

    private void replaceFragment(Fragment fragment) {
        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.main_container, fragment, fragment.getClass().getSimpleName());
        fragmentTransaction.commit();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        OnBackPressedCallback callback = new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
                integers.pop();
                if (!integers.isEmpty()) {
                    replaceFragment(getFragmentHome(integers.peek()));
                } else {
                    Intent setIntent = new Intent(Intent.ACTION_MAIN);
                    setIntent.addCategory(Intent.CATEGORY_HOME);
                    setIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(setIntent);
                }
            }
        };
        requireActivity().getOnBackPressedDispatcher().addCallback(this, callback);
        ;
        super.onCreate(savedInstanceState);
    }
}


