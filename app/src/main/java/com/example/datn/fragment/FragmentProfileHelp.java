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
    int content_1;
    int content_2;
    int content_3;
    int content_4;
    int content_5;
    int content_6;
    int content_7;
    int content_8;
    int content_9;
    public View recentView;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile_help, container, false);
        bundle.putString("Callback", "Profile");
        tv_drop_1 = new TextView(getActivity());

        initView(view);
        initDataView(dropdown_1,ll_help_1,tv_drop_1,content_1);
        initDataView(dropdown_2,ll_help_2,tv_drop_1,content_2);
        initDataView(dropdown_3,ll_help_3,tv_drop_1,content_3);
        initDataView(dropdown_4,ll_help_4,tv_drop_1,content_4);
        initDataView(dropdown_5,ll_help_5,tv_drop_1,content_5);
        initDataView(dropdown_6,ll_help_6,tv_drop_1,content_6);
        initDataView(dropdown_7,ll_help_7,tv_drop_1,content_7);
        initDataView(dropdown_8,ll_help_8,tv_drop_1,content_8);
        initDataView(dropdown_9,ll_help_9,tv_drop_1,content_9);
        return view;
    }

    public void initView(View view) {
        recentView = tv_drop_1;
        face = ResourcesCompat.getFont(getActivity(), R.font.source_sans_pro);
        ll_help_1 = view.findViewById(R.id.help_ll_1);
        ll_help_2 = view.findViewById(R.id.help_ll_2);
        ll_help_3 = view.findViewById(R.id.help_ll_3);
        ll_help_4 = view.findViewById(R.id.help_ll_4);
        ll_help_5 = view.findViewById(R.id.help_ll_5);
        ll_help_6 = view.findViewById(R.id.help_ll_6);
        ll_help_7 = view.findViewById(R.id.help_ll_7);
        ll_help_8 = view.findViewById(R.id.help_ll_8);
        ll_help_9 = view.findViewById(R.id.help_ll_9);
        dropdown_1 = view.findViewById(R.id.dropview_1);
        dropdown_2 = view.findViewById(R.id.dropview_2);
        dropdown_3 = view.findViewById(R.id.dropview_3);
        dropdown_4 = view.findViewById(R.id.dropview_4);
        dropdown_5 = view.findViewById(R.id.dropview_5);
        dropdown_6 = view.findViewById(R.id.dropview_6);
        dropdown_7 = view.findViewById(R.id.dropview_7);
        dropdown_8 = view.findViewById(R.id.dropview_8);
        dropdown_9 = view.findViewById(R.id.dropview_9);
        content_1 = R.string.content_help_1;
        content_2 = R.string.content_help_2;
        content_3 = R.string.content_help_3;
        content_4 = R.string.content_help_4;
        content_5 = R.string.content_help_5;
        content_6 = R.string.content_help_6;
        content_7 = R.string.content_help_7;
        content_8 = R.string.content_help_8;
        content_9 = R.string.content_help_9;
    }

    public void initDataView(AutoCompleteTextView dropdown, LinearLayout linearLayout ,TextView textView ,int content ) {
        dropdown.setInputType(InputType.TYPE_NULL);
        dropdown.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dropdown.setBackgroundResource(R.drawable.shape_line_bottom);
                if (textView.getParent() != null) {
                    ((ViewGroup) textView.getParent()).removeView(textView);
                }
                linearLayout.addView(textView);
                textView.setTextSize(14);
                textView.setTextColor(ContextCompat.getColor(getActivity(), R.color.text_color_black_xtiny_));
                textView.setTypeface(face);
                textView.setPadding(0, 5, 0, 15);
                textView.setText(content);
                dropdown.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_dropup, 0);
            }
        });
        dropdown.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                textView.setText(null);
                dropdown.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_dropdown, 0);
                dropdown.setBackgroundResource(R.drawable.shape_none);
                textView.setPadding(0, 0, 0, 0);
                textView.setTextSize(0);
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
