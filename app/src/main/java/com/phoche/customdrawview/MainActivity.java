package com.phoche.customdrawview;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.phoche.customdrawview.customview.CountDownView;

import butterknife.Bind;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {

    @Bind(R.id.countdown)
    CountDownView mCountdown;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
    }
}
