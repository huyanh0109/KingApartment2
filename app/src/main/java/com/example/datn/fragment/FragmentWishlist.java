package com.example.datn.fragment;

import android.app.Dialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.telecom.Call;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.datn.R;
import com.example.datn.adapter.ApartmentItemHomeAdapter;
import com.example.datn.adapter.ApartmentItemWishListAdapter;
import com.example.datn.api.APIClient;
import com.example.datn.api.APIservice;
import com.example.datn.model.AccountUser;
import com.example.datn.model.Message;
import com.example.datn.model.ResultApartment;
import com.example.datn.viewmodel.RemoveWishListViewModel;
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
    List<ResultApartment> listResultApartment = new ArrayList<>();
    ApartmentItemWishListAdapter apartmentItemWishListAdapter;
    Dialog dialog;
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
        apartmentItemWishListAdapter = new ApartmentItemWishListAdapter(getActivity(), listResultApartment, user.getEmail(),
                new ApartmentItemWishListAdapter.OnClickItemApartment() {
                    @Override
                    public void onClickDetailItemApartment(Bundle bundle) {
                        NavHostFragment.findNavController(FragmentWishlist.this).navigate(R.id.action_fragmentDaddy_to_fragmentIndex,bundle);
                    }
                }
        );
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        linearLayoutManager.scrollToPosition(0);
        recycler_wishlist.setLayoutManager(linearLayoutManager);
        recycler_wishlist.setAdapter(apartmentItemWishListAdapter);

        apartmentItemWishListAdapter.registerAdapterDataObserver(new RecyclerView.AdapterDataObserver() {
            @Override
            public void onItemRangeRemoved(int positionStart, int itemCount) {
                super.onItemRangeRemoved(positionStart, itemCount);
                tv_wishlist_result_count.setText(apartmentItemWishListAdapter.getItemCount() + " apartments");
            }
        });
        postToCallWishList();
    }

    private void loadingApartment() {
        dialog = new Dialog(getActivity());
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_loading);
        dialog.show();
        dialog.setCancelable(false);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
    }
   private void postToCallWishList(){
       loadingApartment();
        APIservice apIservice = APIClient.getClient().create(APIservice.class);
        retrofit2.Call<List<ResultApartment>> call  = apIservice.postToCallWishListData(email);
        call.enqueue(new Callback<List<ResultApartment>>() {
            @Override
            public void onResponse(retrofit2.Call<List<ResultApartment>> call, Response<List<ResultApartment>> response) {
                List<ResultApartment> resultApartment  = response.body();
                listResultApartment.addAll(resultApartment);
                tv_wishlist_result_count.setText(apartmentItemWishListAdapter.getItemCount() + " apartment");
                apartmentItemWishListAdapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(retrofit2.Call<List<ResultApartment>> call, Throwable t) {

            }
        });
        dialog.dismiss();
    }
}
