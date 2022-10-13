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
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import com.example.datn.NetworkBroadcast;
import com.example.datn.R;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;

import java.util.Locale;

public class FragmentSignin extends Fragment {
    SharedPreferences sharedPreferences;
    GoogleSignInClient mGoogleSignInClient;
    LinearLayout login_gmail;
    private BroadcastReceiver broadcastReceiver;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_signin, container, false);
        login_gmail = view.findViewById(R.id.ll_signin_top);
        checkConnect();
        autoLogin();
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

    @Override
    public void onStart() {
        GoogleSignInAccount account = GoogleSignIn.getLastSignedInAccount(getActivity());
        //updateUI(account);
        super.onStart();

    }

    private void signIn() {
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
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
                NavHostFragment.findNavController(FragmentSignin.this).navigate(R.id.action_fragmentSignin_to_fragmentDaddy);
            } else {
                Log.i("TAG", "onActivityResult: fail");
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
}

