package com.example.datn.fragment;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.telecom.Call;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.datn.R;
import com.example.datn.adapter.ApartmentItemHomeAdapter;
import com.example.datn.adapter.ApartmentItemWishListAdapter;
import com.example.datn.api.APIClient;
import com.example.datn.api.APIservice;
import com.example.datn.model.AccountUser;
import com.example.datn.model.ResultApartment;
import com.example.datn.viewmodel.WishListViewModel;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import retrofit2.Callback;
import retrofit2.Response;

public class FragmentWishlist extends Fragment {
    TextView tv_wishlist_result_count;
    WishListViewModel wishListViewModel;
    RecyclerView recycler_wishlist;
    SharedPreferences sharedPreferences;
    String email;
    ArrayList<ResultApartment> listResultApartment = new ArrayList<>();
    ApartmentItemWishListAdapter apartmentItemWishListAdapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_wishlist, container, false);
        initView(view);
        initData();
        return view;
    }

    public void initView(View view) {
        recycler_wishlist = view.findViewById(R.id.recycler_wishlist);
        tv_wishlist_result_count = view.findViewById(R.id.tv_wishlist_result_count);
    }

    public void initData() {
        sharedPreferences = getActivity().getPreferences(Context.MODE_PRIVATE);
        Gson gson = new Gson();
        String json = sharedPreferences.getString(FragmentSignin.KEY_ACCOUNTUSER, "");
        AccountUser user = gson.fromJson(json, AccountUser.class);
        email = user.getEmail();
        wishListViewModel = new ViewModelProvider(getActivity(), getDefaultViewModelProviderFactory()).get(WishListViewModel.class);
        apartmentItemWishListAdapter = new ApartmentItemWishListAdapter(getActivity(), listResultApartment);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        linearLayoutManager.scrollToPosition(0);
        recycler_wishlist.setLayoutManager(linearLayoutManager);
        recycler_wishlist.setAdapter(apartmentItemWishListAdapter);
        wishListViewModel.postToCallWishListLiveData(email).observe(getActivity(), listApartment -> {
            if (listApartment != null) {
                List<ResultApartment> resultApartment = listApartment;
                listResultApartment.addAll(resultApartment);
                Log.i("TAG", "postToCallWishList: " + listResultApartment.size());
                tv_wishlist_result_count.setText(listResultApartment.size() + " apartment");
                apartmentItemWishListAdapter.notifyDataSetChanged();
            }
        });

    }

    public void postToCallWishList() {

    }
}
