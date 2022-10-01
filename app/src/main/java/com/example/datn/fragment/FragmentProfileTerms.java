package com.example.datn.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import com.example.datn.R;

public class FragmentProfileTerms extends Fragment {
    Bundle bundle = new Bundle();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile_terms, container, false);
        bundle.putString("Callback", "Profile");
        return view;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        OnBackPressedCallback callback = new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
                NavHostFragment.findNavController(FragmentProfileTerms.this).navigate(R.id.action_fragmentProfileTerms_to_fragmentDaddy, bundle);
            }
        };
        requireActivity().getOnBackPressedDispatcher().addCallback(this, callback);
        ;

    }

}

