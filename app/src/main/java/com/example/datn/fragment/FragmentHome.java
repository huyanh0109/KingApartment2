package com.example.datn.fragment;

import android.Manifest;
import android.app.Dialog;
import android.content.BroadcastReceiver;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.datn.BroadcastReload;
import com.example.datn.R;
import com.example.datn.adapter.ApartmentItemHomeAdapter;
import com.example.datn.model.AccountUser;
import com.example.datn.model.ResultPopular;
import com.example.datn.viewmodel.AccountUserViewModel;
import com.example.datn.viewmodel.ApartmentPopulateViewModel;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class FragmentHome extends Fragment {
    Button btnhome;
    TextView tv_home_location;
    ArrayList<ResultPopular> listResultPopular = new ArrayList<>();
    RecyclerView rcv_apartment_home;
    ApartmentItemHomeAdapter adapter;
    ApartmentPopulateViewModel apartmentPopulateViewmodelModel;
    Dialog dialog;
    public final static int REQUEST_CODE = 100;
    BroadcastReceiver broadcastReceiver;
    FusedLocationProviderClient fusedLocationProviderClient;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        btnhome = view.findViewById(R.id.btn_home);
        tv_home_location = view.findViewById(R.id.tv_home_location);

        dialog = new Dialog(getActivity());
        loadingApartment();
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(getActivity());
        if (ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            getLocation();
        } else {
            askPermission();
        }

        rcv_apartment_home = view.findViewById(R.id.rcv_home_populate);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
        layoutManager.scrollToPosition(0);
        rcv_apartment_home.setLayoutManager(layoutManager);
        apartmentPopulateViewmodelModel = new ViewModelProvider(getActivity(), getDefaultViewModelProviderFactory()).get(ApartmentPopulateViewModel.class);
        adapter = new ApartmentItemHomeAdapter(getActivity(), listResultPopular);
        rcv_apartment_home.setAdapter(adapter);
        getApartmentPopulate();
        btnhome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Navigation.findNavController(view).navigate(R.id.action_fragmentDaddy_to_fragmentIndex);
            }
        });
        return view;
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
    private void getApartmentPopulate() {
        apartmentPopulateViewmodelModel.getliveDataPopulate().observe(getActivity(), listApartment -> {
            if (listApartment != null && listApartment.getListApartmentPopulate().getApartmentResult() != null
                    && !listApartment.getListApartmentPopulate().getApartmentResult().isEmpty()) {
                List<ResultPopular> apartmentList = listApartment.getListApartmentPopulate().getApartmentResult();
                Log.i("TAG", "getApartment: " + apartmentList);
                listResultPopular.addAll(apartmentList);
                adapter.notifyDataSetChanged();

            }
            if (listApartment == null) {
                reloadConnect();
            }
            dialog.dismiss();
        });
    }
    private void loadingApartment() {
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_loading);
        dialog.show();
        dialog.setCancelable(false);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
    }

    private void reloadConnect() {
        broadcastReceiver = new BroadcastReload();
        requireActivity().registerReceiver(broadcastReceiver, new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION));
    }
    private void getLocation() {

        if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED
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
                        tv_home_location.setText(addresses.get(0).getAddressLine(0));
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
