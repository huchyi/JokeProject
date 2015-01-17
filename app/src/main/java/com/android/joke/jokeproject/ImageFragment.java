package com.android.joke.jokeproject;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.joke.jokeproject.utils.ImageUtils;

public class ImageFragment extends Fragment{

private View fragmentView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        fragmentView = inflater.inflate(R.layout.fragment_main, container, false);
        ImageUtils.initImageLoader(getActivity(),ImageUtils.getDisplayImageOptions(true,
                R.drawable.refresh_image, R.drawable.refresh_image, R.drawable.refresh_image, -1, -1));


        return fragmentView;
    }




}
