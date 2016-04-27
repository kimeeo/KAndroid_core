package com.kimeeo.kAndroid.core.app;

import android.support.v4.app.Fragment;

/**
 * Created by bhavinpadhiyar on 9/4/15.
 */
public interface IFragmentWatcher {
    void onFragmentDestroy(Fragment baseFragment);
    void onFragmentCreate(Fragment baseFragment);
}