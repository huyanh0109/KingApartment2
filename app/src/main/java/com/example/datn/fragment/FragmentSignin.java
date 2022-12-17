package com.example.datn.fragment;

import android.Manifest;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
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
import android.media.RingtoneManager;
import android.net.ConnectivityManager;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.fragment.NavHostFragment;

import com.example.datn.AloneMain;
import com.example.datn.broadcast.NetworkBroadcast;
import com.example.datn.R;
import com.example.datn.model.Account;
import com.example.datn.model.AccountUser;
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
import java.util.List;
import java.util.Locale;

public class FragmentSignin extends Fragment {

    private static final String CHANNEL_WELCOME = "CHANNEL_WELCOME";
    private static final String CHANNEL_UPDATE = "CHANNEL_UPDATE";
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
        createNotificationChannel();
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(getActivity());
        if (ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
//            getLocation();
        } else {
            askPermission();
        }

        login_gmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                signIn();
            }
        });
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
        super.onCreate(savedInstanceState);
        sharedPreferences = getActivity().getSharedPreferences(AloneMain.NAME_LANGUAGE, Context.MODE_PRIVATE);
        String language = sharedPreferences.getString(AloneMain.KEY_BL_LANGUAGE, "en");
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
    private void createNotificationChannel() {
        String channel_name = "channel_name";
        // Create the NotificationChannel, but only on API 26+ because
        // the NotificationChannel class is new and not in the support library
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            String description = getString(R.string.channel_description);
            int importance = NotificationManager.IMPORTANCE_HIGH;
            NotificationChannel channelWelcome = new NotificationChannel(CHANNEL_WELCOME, channel_name, importance);
            channelWelcome.setDescription(description);
            NotificationChannel channelUpdate = new NotificationChannel(CHANNEL_UPDATE, channel_name, importance);
            channelUpdate.setDescription(description);
            // Register the channel with the system; you can't change the importance
            // or other notification behaviors after this
            NotificationManager notificationManager = getActivity().getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channelWelcome);
            notificationManager.createNotificationChannel(channelUpdate);
        }
    }
    private void pushNotificationMax(String name){
        Intent fullScreenIntent = new Intent(getActivity(), FragmentSignin.class);
        PendingIntent pendingIntent;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            pendingIntent = PendingIntent.getActivity(getActivity(),
                    0, fullScreenIntent, PendingIntent.FLAG_UPDATE_CURRENT | PendingIntent.FLAG_IMMUTABLE);

        }else {
            pendingIntent = PendingIntent.getActivity(getActivity(),
                    0, fullScreenIntent, PendingIntent.FLAG_UPDATE_CURRENT);

        }
        sharedPreferences = getActivity().getSharedPreferences(AloneMain.NAME_RING, Context.MODE_PRIVATE);
        Boolean ring = sharedPreferences.getBoolean(AloneMain.KEY_BL_RING, true);
        if (ring == true) {
            NotificationCompat.Builder builder = new NotificationCompat.Builder(getContext(), CHANNEL_WELCOME)
                    .setSmallIcon(R.drawable.ic_logo)
                    .setColor(getResources().getColor(R.color.textcolor_blue_blod))
//                .setLargeIcon(bitmap)
                    .setContentTitle("Welcome!!!")
//                .setContentText("Welcome " + name)
                    .setAutoCancel(true)
                    .setStyle(new NotificationCompat.BigTextStyle().bigText("Chào mừng " + name + " đến với KingApartment"))
                    .setSound(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION))
                    .setPriority(NotificationCompat.PRIORITY_MAX)
                    .setFullScreenIntent(pendingIntent, true);
            NotificationManagerCompat notificationManager = NotificationManagerCompat.from(getContext());
            notificationManager.notify(NotificationManagerCompat.IMPORTANCE_HIGH, builder.build());
            createNotificationChannel();
        }else {
            NotificationCompat.Builder builder = new NotificationCompat.Builder(getContext(), CHANNEL_WELCOME)
                    .setSmallIcon(R.drawable.ic_logo)
                    .setColor(getResources().getColor(R.color.textcolor_blue_blod))
//                .setLargeIcon(bitmap)
                    .setContentTitle("Welcome!!!")
//                .setContentText("Welcome " + name)
                    .setStyle(new NotificationCompat.BigTextStyle().bigText("Chào mừng " + name + " đến với KingApartment"))
                    .setPriority(NotificationCompat.PRIORITY_MAX)
                    .setFullScreenIntent(pendingIntent, true);
            NotificationManagerCompat notificationManager = NotificationManagerCompat.from(getContext());
            notificationManager.notify(NotificationManagerCompat.IMPORTANCE_HIGH, builder.build());
            createNotificationChannel();
        }
    }
    private void pushNotificationMin(){
        NotificationCompat.Builder builder = new NotificationCompat.Builder(getContext(), CHANNEL_UPDATE)
                .setSmallIcon(R.drawable.ic_logo)
                .setColor(getResources().getColor(R.color.textcolor_blue_blod))
//                .setLargeIcon(bitmap)
                .setContentTitle("Update new!!!")
//                .setContentText("Welcome ")
                .setAutoCancel(true)
                .setStyle(new NotificationCompat.BigTextStyle().bigText("Cập nhật thêm nhiều mẫu chung cư mới độc quyền thảo sức lựa chọn"))
                .setPriority(NotificationCompat.PRIORITY_MIN);
        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(getContext());
        notificationManager.notify(NotificationManagerCompat.IMPORTANCE_MIN, builder.build());
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
                    prefsEditor.putString(AloneMain.KEY_ACCOUNTUSER, json);
                    prefsEditor.commit();
                }else {
                    AccountUser users = new AccountUser(account.getPhotoUrl().toString(), account.getDisplayName(), account.getEmail());
                    SharedPreferences.Editor prefsEditor = sharedPreferences.edit();
                    Gson gson = new Gson();
                    String json = gson.toJson(users);
                    prefsEditor.putString(AloneMain.KEY_ACCOUNTUSER, json);
                    prefsEditor.commit();
                }
                sharedPreferences = getActivity().getSharedPreferences(AloneMain.NAME_NOTIFICATION, Context.MODE_PRIVATE);
                Boolean notification = sharedPreferences.getBoolean(AloneMain.KEY_BL_NOTIFICATION, true);
                if (notification == true) {
                    pushNotificationMin();
                    pushNotificationMax(account.getDisplayName());
                } else {
                }
                NavHostFragment.findNavController(FragmentSignin.this).navigate(R.id.action_fragmentSignin_to_fragmentDaddy);
            } else {
                Log.i("TAG", "login: fail");
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == REQUEST_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
//                getLocation();
            } else {
            }
        }
    }
    private void checkConnect() {
        broadcastReceiver = new NetworkBroadcast();
        requireActivity().registerReceiver(broadcastReceiver, new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION));
    }

    private void autoLogin() {
        SharedPreferences preferences = getActivity().getSharedPreferences(AloneMain.NAME_AUTOLOGIN, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putBoolean(AloneMain.KEY_BL_AUTOLOGIN, false);
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

