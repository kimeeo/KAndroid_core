package com.kimeeo.kAndroid.core.app;

import android.app.Application;
import android.content.Context;
import android.provider.Settings;
import android.support.v4.app.Fragment;
import android.util.DisplayMetrics;
import android.view.WindowManager;

import com.kimeeo.kAndroid.core.utils.DeviceUtilities;
import com.kimeeo.kAndroid.core.utils.NetworkUtilities;

/**
 * Created by bhavinpadhiyar on 12/23/15.
 */
abstract public class BaseApplication extends Application implements IFragmentWatcher {
    private String uuid;
    private boolean isTablet = false;
    private boolean is7inchTablet = false;
    private boolean is10inchTablet = false;
    private boolean isPhone = true;
    public void onFragmentDestroy(Fragment fragment) {

    }
    public void onFragmentCreate(Fragment fragment) {

    }
    public void onLowMemory() {
        super.onLowMemory();
    }
    public boolean isConnected(){
        try
        {
            return NetworkUtilities.isConnected(this);
        }catch(Exception e)
        {

        }
        return false;
    }
    private boolean watchLeak = false;
    public boolean isWatchLeak() {
        return watchLeak;
    }
    final public void onCreate() {
        super.onCreate();

        uuid = Settings.Secure.getString(getContentResolver(), Settings.Secure.ANDROID_ID);

        if (DeviceUtilities.isTablet(this)) {
            isTablet = true;
            isPhone = false;
        } else {
            isTablet = false;
            isPhone = true;
        }
        if (isTablet) {
            DisplayMetrics metrics = new DisplayMetrics();
            WindowManager windowManager = (WindowManager) getSystemService(Context.WINDOW_SERVICE);
            windowManager.getDefaultDisplay().getMetrics(metrics);
            float widthInInches = metrics.widthPixels / metrics.xdpi;
            float heightInInches = metrics.heightPixels / metrics.ydpi;
            double sizeInInches = Math.sqrt(Math.pow(widthInInches, 2) + Math.pow(heightInInches, 2));
            is7inchTablet = sizeInInches >= 6.5 && sizeInInches <= 8;

            is10inchTablet = !is7inchTablet;
        } else {
            is7inchTablet = false;
            is10inchTablet = false;
        }
        configApplication();
    }
    abstract public void configApplication();
    public String getAccountPhone() {
        return "";
    }
    public boolean isTablet() {
        return isTablet;
    }
    public boolean isIs7inchTablet() {
        return is7inchTablet;
    }
    public boolean isIs10inchTablet() {
        return is10inchTablet;
    }
    public boolean isPhone() {
        return isPhone;
    }
    public String getUUID() {
        return uuid;
    }
}
