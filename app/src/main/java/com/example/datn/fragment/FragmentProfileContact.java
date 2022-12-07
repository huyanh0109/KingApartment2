package com.example.datn.fragment;

import android.content.IntentFilter;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputFilter;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.OnBackPressedCallback;
import androidx.activity.OnBackPressedDispatcher;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;

import com.example.datn.R;

public class FragmentProfileContact extends Fragment {
    Bundle bundle = new Bundle();
    EditText edt_profile_contact_fullname,edt_profile_contact_email,edt_profile_contact_message;
    Button btn_profile_contact;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile_contact, container, false);
        bundle.putString("Callback", "Profile");
        initView(view);
        initData();
        return view;
    }
    public void initView(View view){
        edt_profile_contact_fullname = view.findViewById(R.id.edt_profile_contact_fullname);
        edt_profile_contact_email = view.findViewById(R.id.edt_profile_contact_email);
        edt_profile_contact_message= view.findViewById(R.id.edt_profile_contact_message);
        btn_profile_contact = view.findViewById(R.id.btn_profile_contact);
    }
    public void initData() {

        int maxLengthemailfullname = 25;
        int maxLength = 250;
        InputFilter[] filters = new InputFilter[1];
        filters[0] = new InputFilter.LengthFilter(maxLength);
        InputFilter[] inputFilter = new InputFilter[1];
        inputFilter[0] = new InputFilter.LengthFilter(maxLengthemailfullname);
        edt_profile_contact_message.setFilters(filters);
        edt_profile_contact_email.setFilters(inputFilter);
        edt_profile_contact_fullname.setFilters(inputFilter);
        btn_profile_contact.setBackgroundResource(R.drawable.shape_button_siliver);
        edt_profile_contact_message.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (!TextUtils.isEmpty(edt_profile_contact_message.getText().toString())
                        &&!TextUtils.isEmpty(edt_profile_contact_fullname.getText().toString())
                &&!TextUtils.isEmpty(edt_profile_contact_email.getText().toString())){
                    btn_profile_contact.setBackgroundResource(R.drawable.shape_button_gradient);
                    btn_profile_contact.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Toast.makeText(getContext(), "Thanks for feedback! Have a nice day >.<", Toast.LENGTH_SHORT).show();
                            edt_profile_contact_message.setText("");
                            edt_profile_contact_fullname.setText("");
                            edt_profile_contact_email.setText("");
                        }
                    });
                } else {
                    btn_profile_contact.setBackgroundResource(R.drawable.shape_button_siliver);
                    btn_profile_contact.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                        }
                    });
                }
            }
        });
        edt_profile_contact_fullname.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (!TextUtils.isEmpty(edt_profile_contact_message.getText().toString())
                        &&!TextUtils.isEmpty(edt_profile_contact_fullname.getText().toString())
                        &&!TextUtils.isEmpty(edt_profile_contact_email.getText().toString())){
                    btn_profile_contact.setBackgroundResource(R.drawable.shape_button_gradient);
                    btn_profile_contact.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Toast.makeText(getContext(), "Thanks for feedback! Have a nice day >.<", Toast.LENGTH_SHORT).show();
                            edt_profile_contact_message.setText("");
                            edt_profile_contact_fullname.setText("");
                            edt_profile_contact_email.setText("");
                        }
                    });
                } else {
                    btn_profile_contact.setBackgroundResource(R.drawable.shape_button_siliver);
                    btn_profile_contact.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                        }
                    });
                }
            }
        });
        edt_profile_contact_email.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (!TextUtils.isEmpty(edt_profile_contact_message.getText().toString())
                        &&!TextUtils.isEmpty(edt_profile_contact_fullname.getText().toString())
                        &&!TextUtils.isEmpty(edt_profile_contact_email.getText().toString())){
                    btn_profile_contact.setBackgroundResource(R.drawable.shape_button_gradient);
                    btn_profile_contact.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Toast.makeText(getContext(), "Thanks for feedback! Have a nice day >.<", Toast.LENGTH_SHORT).show();
                            edt_profile_contact_message.setText("");
                            edt_profile_contact_fullname.setText("");
                            edt_profile_contact_email.setText("");
                        }
                    });
                } else {
                    btn_profile_contact.setBackgroundResource(R.drawable.shape_button_siliver);
                    btn_profile_contact.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                        }
                    });
                }
            }
        });
    }
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        OnBackPressedCallback callback = new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
                NavHostFragment.findNavController(FragmentProfileContact.this).navigate(R.id.action_fragmentProfileContact_to_fragmentDaddy,bundle);
            }
        };
        requireActivity().getOnBackPressedDispatcher().addCallback(this, callback);
        ;
    }

}
