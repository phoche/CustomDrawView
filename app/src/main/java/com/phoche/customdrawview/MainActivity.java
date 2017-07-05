package com.phoche.customdrawview;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.phoche.customdrawview.customview.ArrowView;
import com.phoche.customdrawview.customview.CountDownView;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {

    @Bind(R.id.countdown)
    CountDownView mCountdown;

    @Bind(R.id.av1)
    ArrowView mUpArrow;

    @Bind(R.id.av2)
    ArrowView mDownArrow;

    @Bind(R.id.av3)
    ArrowView mLeftArrow;

    @Bind(R.id.av4)
    ArrowView mRightArrow;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
    }

    @OnClick({R.id.av1, R.id.av2, R.id.av3, R.id.av4})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.av1:
                mUpArrow.setLineColor(ArrowView.COLOR_HIGH_LIGHT);
                mDownArrow.setLineColor(ArrowView.COLOR_NORMAL);
                mLeftArrow.setLineColor(ArrowView.COLOR_NORMAL);
                mRightArrow.setLineColor(ArrowView.COLOR_NORMAL);
                break;
            case R.id.av2:
                mUpArrow.setLineColor(ArrowView.COLOR_NORMAL);
                mDownArrow.setLineColor(ArrowView.COLOR_HIGH_LIGHT);
                mLeftArrow.setLineColor(ArrowView.COLOR_NORMAL);
                mRightArrow.setLineColor(ArrowView.COLOR_NORMAL);
                break;
            case R.id.av3:
                mUpArrow.setLineColor(ArrowView.COLOR_NORMAL);
                mDownArrow.setLineColor(ArrowView.COLOR_NORMAL);
                mLeftArrow.setLineColor(ArrowView.COLOR_HIGH_LIGHT);
                mRightArrow.setLineColor(ArrowView.COLOR_NORMAL);
                break;
            case R.id.av4:
                mUpArrow.setLineColor(ArrowView.COLOR_NORMAL);
                mDownArrow.setLineColor(ArrowView.COLOR_NORMAL);
                mLeftArrow.setLineColor(ArrowView.COLOR_NORMAL);
                mRightArrow.setLineColor(ArrowView.COLOR_HIGH_LIGHT);
                break;
        }

    }
}
