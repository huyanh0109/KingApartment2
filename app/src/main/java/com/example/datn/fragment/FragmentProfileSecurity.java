package com.example.datn.fragment;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.Toast;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.SwitchCompat;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import com.example.datn.AloneMain;
import com.example.datn.R;

import java.io.File;

public class FragmentProfileSecurity extends Fragment {
    Bundle bundle = new Bundle();
    private SharedPreferences sharedPreferences;
    SwitchCompat sw_ads_target;
    Button btn_clear_cache;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile_security, container, false);
        sw_ads_target = view.findViewById(R.id.switch_ads_target);
        btn_clear_cache = view.findViewById(R.id.btn_clear_cache);
        btn_clear_cache.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteCache(getContext());
                Toast.makeText(getContext(), "Clear finish!", Toast.LENGTH_SHORT).show();
            }
        });
        bundle.putString("Callback", "Profile");
        Ads();
        return view;
    }
    public void Ads(){
        sharedPreferences = getActivity().getSharedPreferences(AloneMain.NAME_ADS , Context.MODE_PRIVATE);
        Boolean ads = sharedPreferences.getBoolean(AloneMain.KEY_BL_ADS,true);
        if (ads == true){
            sw_ads_target.setChecked(true);
        }
        sw_ads_target.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked == true){
                    sw_ads_target.setChecked(true);
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putBoolean(AloneMain.KEY_BL_ADS, true);
                    editor.commit();
                }else {
                    sw_ads_target.setChecked(false);
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putBoolean(AloneMain.KEY_BL_ADS, false);
                    editor.commit();
                }
            }
        });
    }
    public static void deleteCache(Context context) {
        try {
            File dir = context.getCacheDir();
            deleteDir(dir);
        } catch (Exception e) {}
    }

    public static boolean deleteDir(File dir) {
        if (dir != null && dir.isDirectory()) {
            String[] children = dir.list();
            for (int i = 0; i < children.length; i++) {
                boolean success = deleteDir(new File(dir, children[i]));
                if (!success) {
                    return false;
                }
            }
            return dir.delete();
        }
        else if(dir!= null && dir.isFile())
            return dir.delete();
        else {
            return false;
        }
    }
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        OnBackPressedCallback callback = new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
                NavHostFragment.findNavController(FragmentProfileSecurity.this).navigate(R.id.action_fragmentProfileSecurity_to_fragmentDaddy, bundle);
            }
        };
        requireActivity().getOnBackPressedDispatcher().addCallback(this, callback);
        ;

    }
}
