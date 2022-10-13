package com.example.datn.fragment;

import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.datn.R;
import com.example.datn.adapter.ApartmentItemHomeAdapter;
import com.example.datn.model.ResultPopulate;
import com.example.datn.viewmodel.ApartmentPopulateViewModel;

import java.util.ArrayList;
import java.util.List;

public class FragmentHome extends Fragment {
    Button btnhome;
    ArrayList<ResultPopulate> listResultPopulate = new ArrayList<>();
    RecyclerView rcv_apartment_home;
    ApartmentItemHomeAdapter adapter;
    ApartmentPopulateViewModel apartmentPopulateViewmodelModel;
    Dialog dialog;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        btnhome = view.findViewById(R.id.btn_home);
        dialog = new Dialog(getActivity());
        loadingApartment();
        rcv_apartment_home = view.findViewById(R.id.rcv_home_populate);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
        layoutManager.scrollToPosition(0);
        rcv_apartment_home.setLayoutManager(layoutManager);
        apartmentPopulateViewmodelModel = new ViewModelProvider(getActivity(), getDefaultViewModelProviderFactory()).get(ApartmentPopulateViewModel.class);

        adapter = new ApartmentItemHomeAdapter(getActivity(), listResultPopulate);
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

    private void getApartmentPopulate() {
        apartmentPopulateViewmodelModel.getliveDataPopulate().observe(getActivity(), listApartment -> {
            List<ResultPopulate> apartmentList = listApartment.getListApartmentPopulate().getApartmentResult();
            Log.i("TAG", "getApartment: " + apartmentList);
            listResultPopulate.addAll(apartmentList);
            Log.i("TAG", "getApartment a: " + listResultPopulate.size());
            adapter.notifyDataSetChanged();
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
}
