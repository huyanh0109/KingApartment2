package com.example.datn.fragment;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.IntentFilter;
import android.content.SharedPreferences;
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
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.datn.BroadcastReload;
import com.example.datn.ItemClickListener;
import com.example.datn.R;
import com.example.datn.adapter.ListOfListHomeApdapter;
import com.example.datn.model.AccountUser;
import com.example.datn.model.ListRecyclerApartmentHome;
import com.example.datn.model.ResultApartment;
import com.example.datn.model.UserLocation;
import com.example.datn.viewmodel.ApartmentNearYouViewModel;
import com.example.datn.viewmodel.ApartmentPopulateViewModel;
import com.example.datn.viewmodel.WishListViewModel;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.gson.Gson;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class FragmentHome extends Fragment {
    public static final String KEY_LOCATION = "locationUser";
    public static final int VIEWTYPE_NORMAL = 1;
    public static final int VIEWTYPE_LOCATION = 2;
    TextView tv_home_location;
    RecyclerView rcv_home_list;
    EditText edt_home_search;
    SharedPreferences sharedPreferences;
    ArrayList<ResultApartment> listResultPopular = new ArrayList<>();
    ArrayList<ResultApartment> listResultNearYou = new ArrayList<>();
    ArrayList<ResultApartment> listResultWishlist = new ArrayList<>();
    ListOfListHomeApdapter listApdapter;
    ApartmentPopulateViewModel apartmentPopulateViewmodelModel;
    ApartmentNearYouViewModel apartmentNearYouViewModel;
    WishListViewModel wishListViewModel;
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
                    wishListViewModel = new ViewModelProvider(getActivity(), getDefaultViewModelProviderFactory()).get(WishListViewModel.class);
                    listApdapter = new ListOfListHomeApdapter(getActivity(), getlistdata(listResultPopular, listResultNearYou, listResultWishlist),
                            longitude, latitude);
                    LinearLayoutManager layoutManagerlist = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
                    layoutManagerlist.scrollToPosition(0);
                    rcv_home_list = view.findViewById(R.id.rcv_home);
                    rcv_home_list.setLayoutManager(layoutManagerlist);
                    rcv_home_list.setAdapter(listApdapter);
                    getApartmentPopulate();
                    getApartmentWishlist();
                }
            });
        } else {
            askPermission();
        }
        goToSearchDetail();
        return view;
    }

    private List<ListRecyclerApartmentHome> getlistdata(ArrayList<ResultApartment> resultApartmentPopular
            , ArrayList<ResultApartment> resultApartmentNearYou, ArrayList<ResultApartment> resultApartmentWishlist) {
        List<ListRecyclerApartmentHome> list = new ArrayList<>();
        list.add(new ListRecyclerApartmentHome(getResources().getString(R.string.home_type_popular), resultApartmentPopular, VIEWTYPE_NORMAL));
        list.add(new ListRecyclerApartmentHome(getResources().getString(R.string.home_type_nearyou), resultApartmentNearYou, VIEWTYPE_LOCATION));
        if (resultApartmentWishlist != null) {
            list.add(new ListRecyclerApartmentHome(getResources().getString(R.string.home_type_wishlist), resultApartmentWishlist, VIEWTYPE_NORMAL));
        }
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
                return;
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

    private void getApartmentWishlist() {
        sharedPreferences = getActivity().getPreferences(Context.MODE_PRIVATE);
        Gson gson = new Gson();
        String json = sharedPreferences.getString(FragmentSignin.KEY_ACCOUNTUSER, "");
        AccountUser user = gson.fromJson(json, AccountUser.class);
        wishListViewModel.postToCallWishListLiveData(user.getEmail()).observe(getActivity(), listApartment -> {
            if (listApartment != null && !listApartment.isEmpty()) {
                List<ResultApartment> apartmentWishlist = listApartment;
                listResultWishlist.addAll(apartmentWishlist);
                listApdapter.notifyDataSetChanged();
            } else {
                return;
            }
        });
    }

    private void getApartmentNearYou() {
        apartmentNearYouViewModel.getliveDataNearYou(longitude, latitude).observe(getActivity(), listApartment -> {
            if (listApartment != null && listApartment.getListApartmentPopulate().getApartmentResult() != null
                    && !listApartment.getListApartmentPopulate().getApartmentResult().isEmpty()) {
                List<ResultApartment> apartmentList = listApartment.getListApartmentPopulate().getApartmentResult();
                listResultNearYou.addAll(apartmentList);
                listApdapter.notifyDataSetChanged();
            }
            if (listApartment == null) {
            }
        });
    }

    private void goToSearchDetail() {
        edt_home_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Navigation.findNavController(v).navigate(R.id.action_fragmentDaddy_to_fragmentSearch);
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
                Double longitude, Double latitude);
    }

    private void getLocation(CallLocation callLocation) {
        sharedPreferences = getActivity().getPreferences(Context.MODE_PRIVATE);

        if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED
        ) {
            fusedLocationProviderClient.getLastLocation().addOnSuccessListener(new OnSuccessListener<Location>() {
                @Override
                public void onSuccess(Location location) {
                    Geocoder geocoder = new Geocoder(getContext(), Locale.getDefault());
                    List<Address> addresses = null;
                    longitude = 10.0;
                    latitude = 10.0;
//                    if (location != null) {
//                        UserLocation userLocation = new UserLocation(location.getLongitude(), location.getLatitude());
//                        SharedPreferences.Editor prefsEditor = sharedPreferences.edit();
//                        Gson gson = new Gson();
//                        String json = gson.toJson(userLocation);
//                        prefsEditor.putString(KEY_LOCATION, json);
//                        prefsEditor.commit();
//                        longitude = userLocation.getLongitdute();
//                        latitude = userLocation.getLatitude();
//                    }else {
//                        sharedPreferences = getActivity().getPreferences(Context.MODE_PRIVATE);
//                        Gson gson = new Gson();
//                        String json = sharedPreferences.getString(KEY_LOCATION, "");
//                        UserLocation locationUser = gson.fromJson(json, UserLocation.class);
//                        if (locationUser != null) {
//                            longitude = locationUser.getLongitdute();
//                            latitude = locationUser.getLatitude();
//                        } else {
//                            UserLocation userLocation = new UserLocation(21.037974, 105.746920);
//                            longitude = userLocation.getLongitdute();
//                            latitude = userLocation.getLatitude();
//                        }
//                    }

//                    try {
//                        addresses = geocoder.getFromLocation(location.getLatitude(), location.getLongitude(), 1);
//                        Log.i("TAG", "onSuccess: latidue" + addresses.get(0).getLatitude());
//                        Log.i("TAG", "onSuccess: longtidue" + addresses.get(0).getLongitude());
//                        Log.i("TAG", "onSuccess: address" + addresses.get(0).getAddressLine(0));
//                        Log.i("TAG", "onSuccess: city" + addresses.get(0).getLocality());
//                        Log.i("TAG", "onSuccess: country" + addresses.get(0).getCountryName());
//
//                    } catch (IOException e) {
//                        e.printStackTrace();
//                    }
                    callLocation.callBackLocation(longitude, latitude);
                    try {
                        addresses = geocoder.getFromLocation(latitude, longitude, 1);

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
