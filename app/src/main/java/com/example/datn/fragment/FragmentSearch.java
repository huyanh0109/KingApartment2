package com.example.datn.fragment;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.datn.R;
import com.example.datn.adapter.SearchItemAdapter;
import com.example.datn.model.ResultApartment;
import com.example.datn.viewmodel.SearchViewModel;

import java.util.ArrayList;
import java.util.List;

public class FragmentSearch extends Fragment {
    RecyclerView recyclerSearch;
    List<ResultApartment> resultApartments = new ArrayList<>();
    public SearchItemAdapter searchItemAdapter;
    public SearchViewModel searchViewModel;
    EditText inputSearch;
    String pattern;
    Dialog dialog;
    TextView tv_search_result_count, tv_search_something, tv_wishlist_result_title;
    ImageView image_search_null;
    LinearLayoutManager linearLayoutManager;
    private final Runnable runnableGetApartment = new Runnable() {
        @Override
        public void run() {
            getDataSearch();
        }
    };

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_search, container, false);
        initView(view);
        initData();
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initListeners();
    }

    private void initListeners() {
        searchViewModel.searchLiveData.observe(getActivity(), listSearch -> {
            loadingApartment();
            if (listSearch != null) {
                resultApartments.clear();
                List<ResultApartment> list = listSearch;
                resultApartments.addAll(list);
                tv_search_result_count.setText(resultApartments.size() + " apartments");
                searchItemAdapter.notifyDataSetChanged();

            }
            if (resultApartments.size() == 0) {
                image_search_null.setVisibility(View.VISIBLE);
                tv_search_something.setVisibility(View.VISIBLE);
                tv_search_something.setText("404 not found...");
                image_search_null.setBackgroundResource(R.drawable.ic_no_results);
            } else {
                image_search_null.setVisibility(View.INVISIBLE);
                tv_search_something.setVisibility(View.INVISIBLE);
            }
            dialog.dismiss();
            tv_search_result_count.setVisibility(View.VISIBLE);
            tv_wishlist_result_title.setVisibility(View.VISIBLE);
        });
        inputSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void afterTextChanged(Editable editable) {
                inputSearch.removeCallbacks(runnableGetApartment);
                if (!editable.toString().isEmpty()) {
                    inputSearch.postDelayed(runnableGetApartment, 500L);
                }
            }
        });
    }

    //    private Parcelable recyclerViewState;
//    @Override
//    public void onCreate(@Nullable Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        recyclerViewState = recyclerSearch.getLayoutManager().onSaveInstanceState();
//    }
//
//    @Override
//    public void onResume() {
//        super.onResume();
//        recyclerSearch.getLayoutManager().onRestoreInstanceState(recyclerViewState);
//    }
//
//    @Override
//    public void onPause() {
//        super.onPause();
//        recyclerViewState = recyclerSearch.getLayoutManager().onSaveInstanceState();
//    }

    public void initView(View view) {
        recyclerSearch = view.findViewById(R.id.recycler_detail_search);
        inputSearch = view.findViewById(R.id.input_search);
        tv_search_result_count = view.findViewById(R.id.tv_search_result_count);
        image_search_null = view.findViewById(R.id.image_search_null);
        tv_search_something = view.findViewById(R.id.tv_search_something);
        tv_wishlist_result_title = view.findViewById(R.id.tv_wishlist_result_title);
        searchViewModel = new ViewModelProvider(getActivity(), getDefaultViewModelProviderFactory()).get(SearchViewModel.class);
        searchItemAdapter = new SearchItemAdapter(getContext(), resultApartments);
        recyclerSearch.setAdapter(searchItemAdapter);
        linearLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        recyclerSearch.setLayoutManager(linearLayoutManager);
    }

    @SuppressLint("ClickableViewAccessibility")
    public void initData() {
        tv_search_result_count.setVisibility(View.INVISIBLE);
        tv_wishlist_result_title.setVisibility(View.INVISIBLE);
        image_search_null.setBackgroundResource(R.drawable.ic_find);
        inputSearch.requestFocus();
    }

    public void getDataSearch() {
        image_search_null.setVisibility(View.INVISIBLE);
        tv_search_something.setVisibility(View.INVISIBLE);
        loadingApartment();
        pattern = String.valueOf(inputSearch.getText());
        searchViewModel.getSearchLiveData(pattern);
    }

    private void loadingApartment() {
        if (dialog == null) {
            dialog = new Dialog(getActivity());
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            dialog.setContentView(R.layout.dialog_loading);
            dialog.setCancelable(false);
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        }
        dialog.show();
    }
//    private void loadFragment() {
//        // load fragment
//        FragmentDetailApartment fragment = new FragmentDetailApartment();
//        FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
//        transaction.replace(R.id.fragmentContainerView, fragment);
//        transaction.addToBackStack(fragment.getClass().getSimpleName());
//        transaction.commit();
//    }
}
