package com.kimeeo.kAndroid.core.fragment;

import android.app.Application;

import com.kimeeo.kAndroid.core.app.BaseApplication;

/**
 * Created by BhavinPadhiyar on 27/04/16.
 */
public interface IApplicationAware {
    Application getApplication();
    BaseApplication getApp();
}
