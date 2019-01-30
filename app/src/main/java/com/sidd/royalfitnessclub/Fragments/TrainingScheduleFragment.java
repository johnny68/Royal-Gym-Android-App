package com.sidd.royalfitnessclub.Fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.sidd.royalfitnessclub.R;

public class TrainingScheduleFragment extends Fragment {

    private View view;
    @Override
    public View onCreateView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle savedInstanceState) {
        view = layoutInflater.inflate(R.layout.fragment_training_schedule, viewGroup, false);
        return view;
    }
}
