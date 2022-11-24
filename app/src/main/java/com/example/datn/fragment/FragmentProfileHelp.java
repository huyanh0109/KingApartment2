package com.example.datn.fragment;

import android.graphics.Typeface;
import android.os.Bundle;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AutoCompleteTextView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.core.content.res.ResourcesCompat;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import com.example.datn.R;

public class FragmentProfileHelp extends Fragment {
    Bundle bundle = new Bundle();
    TextView tv_drop_1;
    AutoCompleteTextView dropdown_1, dropdown_2, dropdown_3, dropdown_4, dropdown_5, dropdown_6, dropdown_7, dropdown_8, dropdown_9;
    LinearLayout ll_help_1, ll_help_2, ll_help_3, ll_help_4, ll_help_5, ll_help_6, ll_help_7, ll_help_8, ll_help_9;
    Typeface face;
    public View recentView;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile_help, container, false);
        bundle.putString("Callback", "Profile");
        tv_drop_1 = new TextView(getActivity());

        initView(view);
        initData();
        return view;
    }

    public void initView(View view) {
        recentView = tv_drop_1;
        face = ResourcesCompat.getFont(getActivity(), R.font.source_sans_pro);
        ll_help_1 = view.findViewById(R.id.help_ll_1);
        dropdown_1 = view.findViewById(R.id.dropview_1);
    }

    public void initData() {
        dropdown_1.setInputType(InputType.TYPE_NULL);
        dropdown_1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dropdown_1.setBackgroundResource(R.drawable.shape_line_bottom);
                if (tv_drop_1.getParent() != null) {
                    ((ViewGroup) tv_drop_1.getParent()).removeView(tv_drop_1);
                }
                ll_help_1.addView(tv_drop_1);
                tv_drop_1.setTextSize(14);
                tv_drop_1.setTextColor(ContextCompat.getColor(getActivity(), R.color.text_color_black_xtiny_));
                tv_drop_1.setTypeface(face);
                tv_drop_1.setPadding(0, 5, 0, 15);
                tv_drop_1.setText(R.string.content_help_1);
                dropdown_1.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_dropup, 0);
            }
        });
        dropdown_1.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                tv_drop_1.setText(null);
                dropdown_1.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_dropdown, 0);
                dropdown_1.setBackgroundResource(R.drawable.shape_none);
                tv_drop_1.setPadding(0, 0, 0, 0);
                tv_drop_1.setTextSize(0);
                return true;
            }
        });
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        OnBackPressedCallback callback = new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
                NavHostFragment.findNavController(FragmentProfileHelp.this).navigate(R.id.action_fragmentProfileHelp_to_fragmentDaddy, bundle);
            }
        };
        requireActivity().getOnBackPressedDispatcher().addCallback(this, callback);
        ;

    }

}
