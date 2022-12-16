package com.example.datn.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.TextView;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.datn.R;
import com.example.datn.adapter.ApartmentItemAllApartmentAdapter;
import com.example.datn.adapter.ApartmentItemWishListAdapter;
import com.example.datn.adapter.ListOfListHomeApdapter;
import com.example.datn.model.ListRecyclerApartmentHome;
import com.example.datn.model.ResultApartment;

import java.util.ArrayList;
import java.util.List;

public class FragmentAllApartment extends Fragment {
    RecyclerView recyclerAll;
    TextView tv_all_apartment_result_count;
    Bundle bundle = new Bundle();
    List<ResultApartment> resultApartmentList = new ArrayList<>();
    TextView tv_title_all_apartment;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_all_apartment, container, false);
        bundle.putString("Callback", "Home");
        recyclerAll = view.findViewById(R.id.recycler_all_apartment);
        tv_title_all_apartment = view.findViewById(R.id.tv_title_all_apartment);
        tv_all_apartment_result_count = view.findViewById(R.id.tv_all_apartment_result_count);
        ListRecyclerApartmentHome listRecyclerApartmentHome = (ListRecyclerApartmentHome) getArguments().getSerializable(ListOfListHomeApdapter.KEY_DATAALLFRAGMENT);
        resultApartmentList = listRecyclerApartmentHome.getListresult();
        tv_title_all_apartment.setText(listRecyclerApartmentHome.getListTitle()+"");
        ApartmentItemAllApartmentAdapter apartmentItemAllApartmentAdapter = new ApartmentItemAllApartmentAdapter(getContext(), resultApartmentList, null,
                new ApartmentItemAllApartmentAdapter.OnClickItemApartment() {
                    @Override
                    public void onClickDetailItemApartment(Bundle bundle) {
                        NavHostFragment.findNavController(FragmentAllApartment.this).navigate(R.id.action_fragmentAllApartment_to_fragmentIndex,bundle);
                    }
                });
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext(),LinearLayoutManager.VERTICAL,false);
        recyclerAll.setLayoutManager(linearLayoutManager);
        recyclerAll.setAdapter(apartmentItemAllApartmentAdapter);
        tv_all_apartment_result_count.setText(resultApartmentList.size() + " apartments");
        return view;
    }
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getActivity().getWindow().setFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS, WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        OnBackPressedCallback callback = new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
                NavHostFragment.findNavController(FragmentAllApartment.this).navigate(R.id.action_fragmentAllApartment_to_fragmentDaddy, bundle);
            }
        };
        requireActivity().getOnBackPressedDispatcher().addCallback(this, callback);
        ;
    }
}
