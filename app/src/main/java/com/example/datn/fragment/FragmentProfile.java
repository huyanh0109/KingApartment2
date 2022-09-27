package com.example.datn.fragment;

import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import com.example.datn.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class FragmentProfile extends Fragment {
    LinearLayout btn_signout, btn_profile, btn_notification, btn_security, btn_term, btn_help, btn_contact;
    BottomNavigationView navView;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile, container, false);
        navView = view.findViewById(R.id.bottom_nav_profile);
        initView(view);
        signOut();
        termS();
        return view;
    }

    public void initView(View view) {
        btn_profile = view.findViewById(R.id.ll_setting_profile);
        btn_security = view.findViewById(R.id.ll_setting_security);
        btn_signout = view.findViewById(R.id.ll_setting_signout);
        btn_help = view.findViewById(R.id.ll_setting_help);
        btn_notification = view.findViewById(R.id.ll_setting_notification);
        btn_contact = view.findViewById(R.id.ll_setting_contact);
        btn_term = view.findViewById(R.id.ll_setting_term);
    }

    private void termS() {
        btn_term.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NavHostFragment.findNavController(FragmentProfile.this).navigate(R.id.action_fragmentDaddy_to_fragmentProfileTerms2);
            }
        });
    }

    private void signOut() {
        btn_signout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Dialog dialog = new Dialog(getContext());
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialog.setContentView(R.layout.dialog_bottom_sheet);
                Button btn_cancel_dialog = dialog.findViewById(R.id.btn_cancel);
                Button btn_signout_dialog = dialog.findViewById(R.id.btn_signout);

                btn_cancel_dialog.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialog.cancel();
                    }
                });
                btn_signout_dialog.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        NavHostFragment.findNavController(FragmentProfile.this).navigate(R.id.action_fragmentDaddy_to_fragmentSignin);
                        dialog.dismiss();
                    }
                });
                dialog.show();
                dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.getWindow().getAttributes().windowAnimations = R.style.dialogAnimation;
                dialog.getWindow().setGravity(Gravity.BOTTOM);
            }
        });
    }
}
