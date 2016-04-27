package com.kimeeo.kAndroid.core.utils;

import android.content.Context;
import android.content.res.Configuration;

/**
 * Created by bhavinpadhiyar on 9/9/15.
 */
public class DeviceUtilities {
    public static boolean isTablet(Context context) {
        return (context.getResources().getConfiguration().screenLayout
                & Configuration.SCREENLAYOUT_SIZE_MASK)
                >= Configuration.SCREENLAYOUT_SIZE_LARGE;
    }
    public static boolean isPhone(Context context) {
        boolean isTab = isTablet(context);
        return isTab==true?false:true;
    }
}
