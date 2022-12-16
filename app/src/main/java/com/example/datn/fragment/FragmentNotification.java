package com.example.datn.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.datn.R;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class FragmentNotification extends Fragment {
    TextView tv_notification_date_today,tv_notification_date_tomorrow;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.fragment_notification,container,false);
        tv_notification_date_today = view.findViewById(R.id.tv_notification_date_today);
        tv_notification_date_tomorrow = view.findViewById(R.id.tv_notification_date_tomorrow);
        Calendar calendar = Calendar.getInstance();
        Date today = calendar.getTime();

        calendar.add(Calendar.DAY_OF_YEAR, -1);
        Date tomorrow = calendar.getTime();
        String currentDateToday = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).format(today);
        tv_notification_date_today.setText("Hôm nay, " + currentDateToday);
        String currentDateTomorrow = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).format(tomorrow);
        tv_notification_date_tomorrow.setText("Hôm qua, " + currentDateTomorrow);
        return view;
    }
}
