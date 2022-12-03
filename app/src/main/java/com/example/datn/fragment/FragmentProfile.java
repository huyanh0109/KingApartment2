package com.example.datn.fragment;

import android.app.Dialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;

import com.bumptech.glide.Glide;
import com.example.datn.R;
import com.example.datn.model.Account;
import com.example.datn.model.AccountUser;
import com.example.datn.viewmodel.AccountUserViewModel;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.material.imageview.ShapeableImageView;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.List;
import java.util.Locale;

public class FragmentProfile extends Fragment {
    LinearLayout btn_signout, btn_profile, btn_notification, btn_security, btn_term, btn_help, btn_contact;
    ImageView btn_language,image_profile_avataruser;
    TextView tv_profile_fullname, tv_profile_email;
    String language;
    SharedPreferences sharedPreferences;
    Bundle bundle = new Bundle();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile, container, false);
        bundle.putString("Callback", "Profile");
        initView(view);
        postAccountUserData();
        signOut();
        termS();
        contact();
        help();
        security();
        notification();
        user();
        changeLanguage();
        return view;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        sharedPreferences = getActivity().getSharedPreferences("language", Context.MODE_PRIVATE);
        language = sharedPreferences.getString("language", "en");
        String languageToLoad = language;
        Locale locale = new Locale(languageToLoad);
        Locale.setDefault(locale);
        Configuration config = new Configuration();
        config.locale = locale;
        getActivity().getResources().updateConfiguration(config,
                getActivity().getResources().getDisplayMetrics());
    }

    public void initView(View view) {
        btn_language = view.findViewById(R.id.btn_language);
        btn_profile = view.findViewById(R.id.ll_setting_profile);
        btn_security = view.findViewById(R.id.ll_setting_security);
        btn_signout = view.findViewById(R.id.ll_setting_signout);
        btn_help = view.findViewById(R.id.ll_setting_help);
        btn_notification = view.findViewById(R.id.ll_setting_notification);
        btn_contact = view.findViewById(R.id.ll_setting_contact);
        btn_term = view.findViewById(R.id.ll_setting_term);
        tv_profile_fullname = view.findViewById(R.id.tv_profile_fullname);
        tv_profile_email = view.findViewById(R.id.tv_profile_email);
        image_profile_avataruser = view.findViewById(R.id.image_profile_avataruser);
    }

    private void postAccountUserData() {
        sharedPreferences = getActivity().getPreferences(Context.MODE_PRIVATE);
        Gson gson = new Gson();
        String json = sharedPreferences.getString(FragmentSignin.KEY_ACCOUNTUSER, "");
        AccountUser user = gson.fromJson(json, AccountUser.class);
        tv_profile_email.setText(user.getEmail());
        tv_profile_fullname.setText(user.getFullname());
        Glide.with(getContext()).load(user.getAvatar()).centerCrop().placeholder(
                R.drawable.animation_loading).error(R.drawable.avatar_user).circleCrop().into(image_profile_avataruser);
    }


    public void changeLanguage() {
        sharedPreferences = getActivity().getSharedPreferences("language", Context.MODE_PRIVATE);
        language = sharedPreferences.getString("language", "en");
        if (language.equals("vie")) {
            btn_language.setBackgroundResource(R.drawable.us_flag);
        } else {
            btn_language.setBackgroundResource(R.drawable.vn_flag);
        }
        btn_language.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (language.equals("en")) {
                    language = "vie";
                    btn_language.setBackgroundResource(R.drawable.us_flag);
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putString("language", "vie");
                    editor.commit();
                    Navigation.findNavController(view).navigate(R.id.action_fragmentDaddy_self, bundle);
                } else {
                    language = "en";
                    btn_language.setBackgroundResource(R.drawable.vn_flag);
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putString("language", "en");
                    editor.commit();
                    Navigation.findNavController(view).navigate(R.id.action_fragmentDaddy_self, bundle);
                }
            }
        });
    }

    private void termS() {
        btn_term.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NavHostFragment.findNavController(FragmentProfile.this).navigate(R.id.action_fragmentDaddy_to_fragmentProfileTerms2);
            }
        });
    }

    private void security() {
        btn_security.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NavHostFragment.findNavController(FragmentProfile.this).navigate(R.id.action_fragmentDaddy_to_fragmentProfileSecurity);
            }
        });
    }

    private void help() {
        btn_help.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NavHostFragment.findNavController(FragmentProfile.this).navigate(R.id.action_fragmentDaddy_to_fragmentProfileHelp);
            }
        });
    }

    private void contact() {
        btn_contact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NavHostFragment.findNavController(FragmentProfile.this).navigate(R.id.action_fragmentDaddy_to_fragmentProfileContact);
            }
        });
    }

    private void notification() {
        btn_notification.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NavHostFragment.findNavController(FragmentProfile.this).navigate(R.id.action_fragmentDaddy_to_fragmentProfileNotification);
            }
        });
    }

    private void user() {
        btn_profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NavHostFragment.findNavController(FragmentProfile.this).navigate(R.id.action_fragmentDaddy_to_fragmentProfileUser);
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
                        GoogleSignInOptions gso = new GoogleSignInOptions.
                                Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).
                                build();

                        GoogleSignInClient googleSignInClient = GoogleSignIn.getClient(getActivity(), gso);
                        googleSignInClient.signOut();
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
