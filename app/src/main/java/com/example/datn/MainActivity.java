package com.example.datn;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import me.relex.circleindicator.CircleIndicator3;

public class MainActivity extends AppCompatActivity {
    public  static  ViewPager2 vpg_intro;
    Button btn_next_intro;
    IntroAdapter introAdapter;
    CircleIndicator3 circleIndicator3;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sheet_intro);
        getSupportActionBar().hide();
        btn_next_intro = findViewById(R.id.btn_intro);
        vpg_intro = findViewById(R.id.vpg_intro);
        introAdapter = new IntroAdapter(this);
        vpg_intro.setAdapter(introAdapter);
        circleIndicator3 = findViewById(R.id.icon_next);
        circleIndicator3.setViewPager(vpg_intro);
        btn_next_intro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                vpg_intro.setCurrentItem(getItem(+1),true);
            }
        });
    }
    private int getItem(int i ){
        return vpg_intro.getCurrentItem()+i;
    }
}