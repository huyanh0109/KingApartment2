package com.example.datn.fragment;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.datn.NetworkBroadcast;
import com.example.datn.R;
import com.google.android.material.badge.BadgeDrawable;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.Locale;

public class FragmentDaddy extends Fragment {
    BottomNavigationView navView;
    SharedPreferences sharedPreferences;
    Deque<Integer> integers = new ArrayDeque<>(4);
    boolean flag = true;
    BroadcastReceiver broadcastReceiver;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_daddy, container, false);
        navView = view.findViewById(R.id.bottom_nav);
        navView.setItemIconTintList(null);
        autoLogin();
        checkConnect();
        createView();
        BadgeDrawable badgeDrawable = navView.getOrCreateBadge(R.id.nav_notification);
        badgeDrawable.setVisible(true);
        badgeDrawable.setNumber(10);
//        val badgeDrawable = bottomNavigation.getBadge(menuItemId)
//        if (badgeDrawable != null) {
//            badgeDrawable.isVisible = false
//            badgeDrawable.clearNumber()
//        }
        sharedPreferences = getActivity().getSharedPreferences("dark", Context.MODE_PRIVATE);
        Boolean bl = sharedPreferences.getBoolean("dark_mode", false);
        if (bl == true) {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
        }
        return view;
    }

    private void createView() {
        Log.i("TAG", "getBoolean: "+getBoolean(getArguments(), "Callback"));
        if (getBoolean(getArguments(), "Callback") == null) {
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

        } else if (getBoolean(getArguments(), "Callback") != null && getBoolean(getArguments(), "Callback").equals("Home")) {
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
        } else if (getBoolean(getArguments(), "Callback") != null && getBoolean(getArguments(), "Callback").equals("Profile")) {
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
        }else if (getBoolean(getArguments(), "Callback") != null && getBoolean(getArguments(), "Callback").equals("Wishlist")) {
            integers.push(R.id.nav_favorite);
            replaceFragment(new FragmentWishlist());
            navView.setSelectedItemId(R.id.nav_favorite);
            navView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
                                                  @Override
                                                  public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                                                      int id = item.getItemId();
                                                      if (integers.contains(id)) {
                                                          if (id == R.id.nav_favorite) {
                                                              if (integers.size() != 1) {
                                                                  if (flag) {
                                                                      integers.addFirst(R.id.nav_favorite);
                                                                      flag = false;
                                                                  }
                                                              }
                                                          }
                                                          integers.remove(id);
                                                      }
                                                      integers.push(id);
                                                      replaceFragment(getFragmentWishlist(item.getItemId()));

                                                      return false;
                                                  }
                                              }
            );
        }
    }
    public static String getBoolean(Bundle arguments, String key) {
        if (arguments != null && arguments.containsKey("Callback")) {
            return arguments.getString(key);
        } else {
            return null;
        }
//        loadFragment(new FragmentHome());
//        navView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
//            @Override
//            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
//                switch (item.getItemId()) {
//                    case R.id.nav_home:
//                        loadFragment(new FragmentHome());
//                        break;
//                    case R.id.nav_favorite:
//                        loadFragment(new FragmentWishlist());
//                        break;
//                    case R.id.nav_notification:
//                        loadFragment(new FragmentNotification());
//                        break;
//                    case R.id.nav_profile:
//                        loadFragment(new FragmentProfile());
//                        break;
//                }
//                return true;
//            }
//        });
    }

    private Fragment getFragmentHome(int itemId) {
        switch (itemId) {
            case R.id.nav_home:
                navView.getMenu().getItem(0).setChecked(true);
                return new FragmentHome();
            case R.id.nav_favorite:
                navView.getMenu().getItem(1).setChecked(true);
                return new FragmentWishlist();
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
                return new FragmentWishlist();
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
    private Fragment getFragmentWishlist(int itemId) {
        switch (itemId) {
            case R.id.nav_home:
                navView.getMenu().getItem(0).setChecked(true);
                return new FragmentHome();
            case R.id.nav_favorite:
                navView.getMenu().getItem(1).setChecked(true);
                return new FragmentWishlist();
            case R.id.nav_notification:
                navView.getMenu().getItem(2).setChecked(true);
                return new FragmentNotification();
            case R.id.nav_profile:
                navView.getMenu().getItem(3).setChecked(true);
                return new FragmentProfile();
        }
        navView.getMenu().getItem(1).setChecked(true);
        return new FragmentWishlist();
    }
    private void loadFragment(Fragment fragment) {
        // load fragment
        FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.frame_container, fragment);
        transaction.addToBackStack(fragment.getClass().getSimpleName());
        transaction.commit();
    }
    private void replaceFragment(Fragment fragment) {
        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frame_container, fragment, fragment.getClass().getSimpleName());
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


    private void autoLogin() {
        SharedPreferences preferences = getActivity().getSharedPreferences("autologin", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putBoolean("auto", true);
        editor.apply();
    }

    private void checkConnect() {
        broadcastReceiver = new NetworkBroadcast();
        requireActivity().registerReceiver(broadcastReceiver, new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION));
    }
}


