package com.sidd.royalfitnessclub.SplashScreen;

import android.animation.ValueAnimator;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ProgressBar;

import com.jaredrummler.android.widget.AnimatedSvgView;
import com.sidd.royalfitnessclub.R;

import butterknife.BindView;
import butterknife.BindViews;
import butterknife.ButterKnife;

public class LoadingActivity extends AppCompatActivity {

    @BindViews({R.id.dynamic_background_progress})
    ProgressBar[] progressBar;

    private ValueAnimator valueAnimator;
    AnimatedSvgView animatedSvgView;

    @Override
    public void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.loading_activity);

        ButterKnife.bind(this);

    }
}
