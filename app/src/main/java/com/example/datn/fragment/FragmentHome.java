package com.example.datn.fragment;

import android.Manifest;
import android.annotation.SuppressLint;
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
import android.os.Looper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.datn.BroadcastReload;
import com.example.datn.ItemClickListener;
import com.example.datn.R;
import com.example.datn.adapter.ListOfListHomeApdapter;
import com.example.datn.model.ListRecyclerApartmentHome;
import com.example.datn.model.ResultApartment;
import com.example.datn.viewmodel.ApartmentNearYouViewModel;
import com.example.datn.viewmodel.ApartmentPopulateViewModel;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class FragmentHome extends Fragment {
    TextView tv_home_location;
    RecyclerView rcv_home_list;
    EditText edt_home_search;
    ArrayList<ResultApartment> listResultPopular = new ArrayList<>();
    ArrayList<ResultApartment> listResultNearYou = new ArrayList<>();
    ListOfListHomeApdapter listApdapter;
    ApartmentPopulateViewModel apartmentPopulateViewmodelModel;
    ApartmentNearYouViewModel apartmentNearYouViewModel;
    Dialog dialog;
    Double longitude, latitude;
    public final static int REQUEST_CODE = 100;
    BroadcastReceiver broadcastReceiver;
    FusedLocationProviderClient fusedLocationProviderClient;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        tv_home_location = view.findViewById(R.id.tv_home_location);
        edt_home_search = view.findViewById(R.id.edt_home_search);
        edt_home_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            }
        });

        dialog = new Dialog(getActivity());
        loadingApartment();
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(getActivity());
        if (ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            getLocation(new CallLocation() {
                @Override
                public void callBackLocation(Double longitude, Double latitude) {
                    apartmentPopulateViewmodelModel = new ViewModelProvider(getActivity(), getDefaultViewModelProviderFactory()).get(ApartmentPopulateViewModel.class);
                    apartmentNearYouViewModel = new ViewModelProvider(getActivity(), getDefaultViewModelProviderFactory()).get(ApartmentNearYouViewModel.class);
                    listApdapter = new ListOfListHomeApdapter(getActivity(), getlistdata(listResultPopular, listResultNearYou),
                            longitude, latitude);
                    LinearLayoutManager layoutManagerlist = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
                    layoutManagerlist.scrollToPosition(0);
                    rcv_home_list = view.findViewById(R.id.rcv_home);
                    rcv_home_list.setLayoutManager(layoutManagerlist);
                    rcv_home_list.setAdapter(listApdapter);
                    getApartmentPopulate();
                }
            });
        } else {
            askPermission();
        }
        return view;
    }

    private List<ListRecyclerApartmentHome> getlistdata(ArrayList<ResultApartment> resultApartmentPopular
            ,ArrayList<ResultApartment> resultApartmentNearYou) {
        List<ListRecyclerApartmentHome> list = new ArrayList<>();
        list.add(new ListRecyclerApartmentHome(getResources().getString(R.string.home_type_popular), resultApartmentPopular,1));
        list.add(new ListRecyclerApartmentHome(getResources().getString(R.string.home_type_nearyou), resultApartmentNearYou,2));
        return list;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == REQUEST_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                getLocation(new CallLocation() {
                    @Override
                    public void callBackLocation(Double longitute, Double latitude) {

                    }
                });
            } else {
            }
        }
    }

    private void getApartmentPopulate() {
        apartmentPopulateViewmodelModel.getliveDataPopulate().observe(getActivity(), listApartment -> {
            if (listApartment != null && listApartment.getListApartmentPopulate().getApartmentResult() != null
                    && !listApartment.getListApartmentPopulate().getApartmentResult().isEmpty()) {
                List<ResultApartment> apartmentList = listApartment.getListApartmentPopulate().getApartmentResult();
                listResultPopular.addAll(apartmentList);
                listApdapter.notifyDataSetChanged();
            }
            if (listApartment == null) {
                reloadConnect();
            }
            dialog.dismiss();
        });
    }
    private void getApartmentNearYou(){
        apartmentNearYouViewModel.getliveDataNearYou(longitude, latitude).observe(getActivity(), listApartment -> {
            if (listApartment != null && listApartment.getListApartmentPopulate().getApartmentResult() != null
                    && !listApartment.getListApartmentPopulate().getApartmentResult().isEmpty()) {
                List<ResultApartment> apartmentList = listApartment.getListApartmentPopulate().getApartmentResult();
                listResultNearYou.addAll(apartmentList);
                listApdapter.notifyDataSetChanged();
            }
            if (listApartment == null) {
                reloadConnect();
            }
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
    public interface CallLocation {
        void callBackLocation(
                Double longitude,Double latitude);
    }

    private void getLocation(CallLocation callLocation) {
        if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED
        ) {
            fusedLocationProviderClient.getLastLocation().addOnSuccessListener(new OnSuccessListener<Location>() {
                @Override
                public void onSuccess(Location location) {
                    Geocoder geocoder = new Geocoder(getContext(), Locale.getDefault());
                    List<Address> addresses = null;
                    longitude = location.getLongitude();
                    latitude = location.getLatitude();
                    callLocation.callBackLocation(location.getLongitude(),location.getLatitude());
                    try {
                        addresses = geocoder.getFromLocation(location.getLatitude(), location.getLongitude(), 1);

                        getApartmentNearYou();
                        tv_home_location.setText(addresses.get(0).getAddressLine(0));
                    } catch (IOException e) {
                        e.printStackTrace();
                    }


                }
            });
        }
    }
    @Override
    public void onResume() {
        super.onResume();
        getLocation(new CallLocation() {
            @Override
            public void callBackLocation(Double longitude, Double latitude) {

            }
        });
    }

    private void askPermission() {
        ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.ACCESS_FINE_LOCATION
        }, REQUEST_CODE);
    }
}
