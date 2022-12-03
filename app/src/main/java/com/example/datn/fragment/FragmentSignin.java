package com.example.datn.fragment;

import android.Manifest;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.fragment.NavHostFragment;

import com.example.datn.NetworkBroadcast;
import com.example.datn.R;
import com.example.datn.api.APIClient;
import com.example.datn.api.APIservice;
import com.example.datn.model.Account;
import com.example.datn.model.AccountUser;
import com.example.datn.model.ResultApartment;
import com.example.datn.viewmodel.AccountUserViewModel;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.gson.Gson;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import okhttp3.Connection;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FragmentSignin extends Fragment {
    public static final String KEY_ACCOUNTUSER= "accountUser";
    SharedPreferences sharedPreferences;
    GoogleSignInClient mGoogleSignInClient;
    LinearLayout login_gmail;
    private BroadcastReceiver broadcastReceiver;
    AccountUserViewModel accountUserViewModel;
    FusedLocationProviderClient fusedLocationProviderClient;
    public final static int REQUEST_CODE = 100;
    Account user = new Account();
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_signin, container, false);
        login_gmail = view.findViewById(R.id.ll_signin_top);
        checkConnect();
        autoLogin();
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(getActivity());
        if (ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            getLocation();
        } else {
            askPermission();
        }

        login_gmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                signIn();
            }
        });
        sharedPreferences = getActivity().getSharedPreferences("dark", Context.MODE_PRIVATE);
        Boolean bl = sharedPreferences.getBoolean("dark_mode", false);
        if (bl == true) {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        }
        return view;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();
        Log.i("TAG", "onCreate: gso "+gso);
        mGoogleSignInClient = GoogleSignIn.getClient(getActivity(), gso);
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

    @Override
    public void onStart() {
        GoogleSignInAccount account = GoogleSignIn.getLastSignedInAccount(getActivity());
        //updateUI(account);
        super.onStart();

    }

    private void signIn() {
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        Log.i("TAG", "signIn: " + signInIntent);
        startActivityForResult(signInIntent, 1000);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        requireActivity().unregisterReceiver(broadcastReceiver);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        // Result returned from launching the Intent from GoogleSignInClient.getSignInIntent(...);
        if (requestCode == 1000) {
            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            // The Task returned from this call is always completed, no need to attach
            if (result.isSuccess()) {
                GoogleSignInAccount account = GoogleSignIn.getLastSignedInAccount(getActivity());
                Log.i("TAG", "" + account.getIdToken());
                Log.i("TAG", "" + account.getId());
                Log.i("TAG", "" + account.getEmail());
                Log.i("TAG", "" + account.getDisplayName());
                user.setAccountUser(new AccountUser("none", account.getDisplayName(), account.getEmail()));
                postAccountUserData();
                sharedPreferences = getActivity().getPreferences(Context.MODE_PRIVATE);
                if (account.getPhotoUrl() == null) {
                    AccountUser users = new AccountUser("none", account.getDisplayName(), account.getEmail());
                    SharedPreferences.Editor prefsEditor = sharedPreferences.edit();
                    Gson gson = new Gson();
                    String json = gson.toJson(users);
                    prefsEditor.putString(KEY_ACCOUNTUSER, json);
                    prefsEditor.commit();
                }else {
                    AccountUser users = new AccountUser(account.getPhotoUrl().toString(), account.getDisplayName(), account.getEmail());
                    SharedPreferences.Editor prefsEditor = sharedPreferences.edit();
                    Gson gson = new Gson();
                    String json = gson.toJson(users);
                    prefsEditor.putString(KEY_ACCOUNTUSER, json);
                    prefsEditor.commit();
                }
                NavHostFragment.findNavController(FragmentSignin.this).navigate(R.id.action_fragmentSignin_to_fragmentDaddy);
            } else {
                Log.i("TAG", "onActivityResult: fail");
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == REQUEST_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                getLocation();
            } else {
                Log.i("TAG", "onRequestPermissionsResult: +required");
            }
        }
    }
    private void checkConnect() {
        broadcastReceiver = new NetworkBroadcast();
        requireActivity().registerReceiver(broadcastReceiver, new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION));
    }

    private void autoLogin() {
        SharedPreferences preferences = getActivity().getSharedPreferences("autologin", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putBoolean("auto", false);
        editor.apply();
    }
    private void postAccountUserData(){
        accountUserViewModel = new ViewModelProvider(getActivity(),getDefaultViewModelProviderFactory()).get(AccountUserViewModel.class);
        accountUserViewModel.setAccountUserLiveData(user).observe(getActivity(), listAccountUser -> {
            if (listAccountUser != null && listAccountUser.getAccountUser() != null) {
                AccountUser accountUser = listAccountUser.getAccountUser();
        }
    });
    }
    private void getLocation() {

        if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED
//                && ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED
        ) {
            fusedLocationProviderClient.getLastLocation().addOnSuccessListener(new OnSuccessListener<Location>() {
                @Override
                public void onSuccess(Location location) {
                    Geocoder geocoder = new Geocoder(getContext(), Locale.getDefault());
                    List<Address> addresses = null;
                    try {
                        addresses = geocoder.getFromLocation(location.getLatitude(), location.getLongitude(), 1);
                        Log.i("TAG", "onSuccess: latidue" + addresses.get(0).getLatitude());
                        Log.i("TAG", "onSuccess: longtidue" + addresses.get(0).getLongitude());
                        Log.i("TAG", "onSuccess: address" + addresses.get(0).getAddressLine(0));
                        Log.i("TAG", "onSuccess: city" + addresses.get(0).getLocality());
                        Log.i("TAG", "onSuccess: country" + addresses.get(0).getCountryName());

                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                }
            });
        }
    }

    private void askPermission() {
        ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.ACCESS_FINE_LOCATION
        }, REQUEST_CODE);
    }
}

