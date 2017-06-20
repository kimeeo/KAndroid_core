package com.kimeeo.kAndroid.core.utils;

import android.annotation.SuppressLint;
import android.os.Build;
import android.view.View;

import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by BhavinPadhiyar on 13/08/16.
 */
public class IDUtil
{
    private static final AtomicInteger sNextGeneratedId = new AtomicInteger(11111111);
    @SuppressLint("NewApi")
    public static int generateViewId() {
        if (Build.VERSION.SDK_INT < 17) {
            for (;;) {
                final int result = sNextGeneratedId.get();
                int newValue = result + 1;
                if (newValue > 0x00FFFFFF)
                    newValue = 1; // Roll over to 1, not 0.
                if (sNextGeneratedId.compareAndSet(result, newValue)) {
                    return result;
                }
            }
        } else {
            return View.generateViewId();
        }
    }

    public static Random r = new Random();
    public static int generateRandomId(int min,int max) {
        if(min==0)
            min=1;
        return r.nextInt(max - min + 1) + min;
    }

}
