package com.kimeeo.kAndroid.core.fragment;

import android.app.Application;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v4.app.Fragment;
import android.util.Log;

import com.gun0912.tedpermission.PermissionListener;
import com.kimeeo.kAndroid.core.R;
import com.kimeeo.kAndroid.core.app.BaseApplication;
import com.kimeeo.kAndroid.core.app.IFragmentWatcher;
import com.kimeeo.kAndroid.core.permissions.PermissionsHelper;

import java.io.Serializable;
import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.Map;

/**
 * Created by BhavinPadhiyar on 27/04/16.
 */
public class BaseFragment extends Fragment implements IApplicationAware
{
    private static final String LOG_TAG= "BaseFragment";
    private static final String DATA= "data";
    @Override
    public BaseApplication getApp(){
        if(getActivity().getApplication() instanceof BaseApplication)
            return (BaseApplication)getActivity().getApplication();
        return null;
    }
    @Override
    public Application getApplication() {
        return getActivity().getApplication();
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(getHasOptionsMenu());

        if(getActivity()!=null && getActivity() instanceof IFragmentWatcher)
            ((IFragmentWatcher)getActivity()).onFragmentCreate(this);

        if(getApp()!=null)
            getApp().onFragmentCreate(this);
        else if(getApplication()!=null && getApplication() instanceof IFragmentWatcher)
            ((IFragmentWatcher)getApplication()).onFragmentCreate(this);

        if (getAutoHandlePermissions())
            handlePermissions();
    }
    @Override
    public void onDestroy() {
        super.onDestroy();
        garbageCollectorCall();
        if(getActivity()!=null && getActivity() instanceof IFragmentWatcher)
            ((IFragmentWatcher)getActivity()).onFragmentDestroy(this);

        if(getApp()!=null)
            getApp().onFragmentDestroy(this);
        else if(getApplication()!=null && getApplication() instanceof IFragmentWatcher)
            ((IFragmentWatcher)getApplication()).onFragmentDestroy(this);

    }
    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        Bundle bundle = getArguments();
        if(bundle!=null && bundle.keySet().size()!=0) {
            for (String key : bundle.keySet()) {
                Object data = bundle.get(key);
                if (data instanceof String)
                    outState.putString(key, (String) data);
                else if (data instanceof String[])
                    outState.putStringArray(key, (String[]) data);
                else if (data instanceof Integer)
                    outState.putInt(key, (Integer) data);
                else if (data instanceof int[])
                    outState.putIntArray(key, (int[]) data);
                else if (data instanceof Boolean)
                    outState.putBoolean(key, (Boolean) data);
                else if (data instanceof boolean[])
                    outState.putBooleanArray(key, (boolean[]) data);
                else if (data instanceof Bundle)
                    outState.putBundle(key, (Bundle) data);
                else if (data instanceof Byte)
                    outState.putByte(key, (Byte) data);
                else if (data instanceof byte[])
                    outState.putByteArray(key, (byte[]) data);
                else if (data instanceof Character)
                    outState.putChar(key, (Character) data);
                else if (data instanceof char[])
                    outState.putCharArray(key, (char[]) data);
                else if (data instanceof CharSequence)
                    outState.putCharSequence(key, (CharSequence) data);
                else if (data instanceof CharSequence[])
                    outState.putCharSequenceArray(key, (CharSequence[]) data);
                else if (data instanceof Serializable)
                    outState.putSerializable(key, (Serializable) data);
                else if (data instanceof Short)
                    outState.putShort(key, (Short) data);
                else if (data instanceof short[])
                    outState.putShortArray(key, (short[]) data);
                else if (data instanceof Double)
                    outState.putDouble(key, (Double) data);
                else if (data instanceof double[])
                    outState.putDoubleArray(key, (double[]) data);
                else if (data instanceof Float)
                    outState.putFloat(key, (Float) data);
                else if (data instanceof float[])
                    outState.putFloatArray(key, (float[]) data);
                else if (data instanceof Long)
                    outState.putLong(key, (Long) data);
                else if (data instanceof long[])
                    outState.putLongArray(key, (long[]) data);
                else if (data instanceof Parcelable)
                    outState.putParcelable(key, (Parcelable) data);
                else if (data instanceof Parcelable[])
                    outState.putParcelableArray(key, (Parcelable[]) data);
                else {
                    Log.e(LOG_TAG, "This type is not supported. Key:" + key + ",   Value:" + data);
                    outState.putString(key, data.toString());
                }
            }
        }
    }
    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }
    protected void garbageCollectorCall(){}
    //PARAMS Starts
    protected boolean getHasOptionsMenu()
    {
        return false;
    }
    public boolean allowedBack()
    {
        return true;
    }
    //PARAMS Ends
    //Permissions Starts
    PermissionListener onPermission = new PermissionListener() {
        @Override
        public void onPermissionGranted() {
            permissionGranted();
        }
        @Override
        public void onPermissionDenied(ArrayList<String> arrayList) {
            permissionDenied(arrayList);
        }
    };
    protected boolean getAutoHandlePermissions() {
        return true;
    }
    protected String[] requirePermissions() {
        return null;
    }
    protected String[] getFriendlyPermissionsMeaning() {return null;}
    protected void handlePermissions() {
        PermissionsHelper permissionsHelper = createPermissionsHelper();
        permissionsHelper.check(requirePermissions(),getFriendlyPermissionsMeaning());
    }
    protected PermissionsHelper createPermissionsHelper() {
        PermissionsHelper permissionsHelper = new PermissionsHelper(getContext());
        permissionsHelper.setShowRationaleConfirm(getShowRationaleConfirm());
        permissionsHelper.setOnPermission(onPermission);
        permissionsHelper.setRationaleConfirmText(getRationaleConfirmText());
        permissionsHelper.setRationaleMessage(getRationaleMessage());
        permissionsHelper.setShowDeniedMessage(getShowDeniedMessage());
        permissionsHelper.setDeniedCloseButtonText(getDeniedCloseButtonText());
        permissionsHelper.setDeniedMessage(getDeniedMessage());

        return permissionsHelper;
    }
    protected boolean getShowDeniedMessage() {
        return true;
    }
    protected boolean getShowRationaleConfirm() {
        return true;
    }
    protected void permissionGranted() {

    }
    protected void permissionDenied(ArrayList<String> arrayList) {

    }
    protected String getDeniedCloseButtonText() {
        return getString(R.string._permission_denied_close_button_text);
    }
    protected String getRationaleConfirmText() {
        return getString(R.string._permission_rationale_confirm_text);
    }
    protected String getRationaleMessage() {
        return getString(R.string._permission_rationale_message);
    }
    protected String getDeniedMessage() {
        return getString(R.string._permission_denied_message);
    }
    //Permissions ENDS

    //New Instance Starts
    public static Fragment newInstance(Class clazz,Bundle bundle) {
        try {
            Constructor constructor = clazz.getConstructor();
            Fragment baseFragment = (Fragment) constructor.newInstance();
            if(bundle!=null)
                baseFragment.setArguments(bundle);
            return baseFragment;

        } catch (Exception e) {
            System.out.println(e);
            Log.e(LOG_TAG, "BaseFragment creation fail. " + clazz);
        }
        return null;
    }
    public static Fragment newInstance(Class clazz) {
        try {
            Constructor constructor = clazz.getConstructor();
            Fragment baseFragment = (Fragment) constructor.newInstance();
            return baseFragment;
        } catch (Exception e) {
            System.out.println(e);
            Log.e(LOG_TAG, "BaseFragment creation fail. " + clazz);
        }

        return null;
    }
    public static Fragment newInstance(Class clazz,Map<String, Object> data) {
        Bundle args = new Bundle();
        for (Map.Entry<String, Object> entry : ((Map<String, Object>) data).entrySet()) {
            if (entry.getValue() != null) {
                if (entry.getValue() instanceof String)
                    args.putString(entry.getKey(), (String) entry.getValue());
                else if (entry.getValue() instanceof String[])
                    args.putStringArray(entry.getKey(), (String[]) entry.getValue());
                else if (entry.getValue() instanceof Integer)
                    args.putInt(entry.getKey(), (Integer) entry.getValue());
                else if (entry.getValue() instanceof int[])
                    args.putIntArray(entry.getKey(), (int[]) entry.getValue());
                else if (entry.getValue() instanceof Boolean)
                    args.putBoolean(entry.getKey(), (Boolean) entry.getValue());
                else if (entry.getValue() instanceof boolean[])
                    args.putBooleanArray(entry.getKey(), (boolean[]) entry.getValue());
                else if (entry.getValue() instanceof Bundle)
                    args.putBundle(entry.getKey(), (Bundle) entry.getValue());
                else if (entry.getValue() instanceof Byte)
                    args.putByte(entry.getKey(), (Byte) entry.getValue());
                else if (entry.getValue() instanceof byte[])
                    args.putByteArray(entry.getKey(), (byte[]) entry.getValue());
                else if (entry.getValue() instanceof Character)
                    args.putChar(entry.getKey(), (Character) entry.getValue());
                else if (entry.getValue() instanceof char[])
                    args.putCharArray(entry.getKey(), (char[]) entry.getValue());
                else if (entry.getValue() instanceof CharSequence)
                    args.putCharSequence(entry.getKey(), (CharSequence) entry.getValue());
                else if (entry.getValue() instanceof CharSequence[])
                    args.putCharSequenceArray(entry.getKey(), (CharSequence[]) entry.getValue());
                else if (entry.getValue() instanceof Serializable)
                    args.putSerializable(entry.getKey(), (Serializable) entry.getValue());
                else if (entry.getValue() instanceof Short)
                    args.putShort(entry.getKey(), (Short) entry.getValue());
                else if (entry.getValue() instanceof short[])
                    args.putShortArray(entry.getKey(), (short[]) entry.getValue());
                else if (entry.getValue() instanceof Double)
                    args.putDouble(entry.getKey(), (Double) entry.getValue());
                else if (entry.getValue() instanceof double[])
                    args.putDoubleArray(entry.getKey(), (double[]) entry.getValue());
                else if (entry.getValue() instanceof Float)
                    args.putFloat(entry.getKey(), (Float) entry.getValue());
                else if (entry.getValue() instanceof float[])
                    args.putFloatArray(entry.getKey(), (float[]) entry.getValue());
                else if (entry.getValue() instanceof Long)
                    args.putLong(entry.getKey(), (Long) entry.getValue());
                else if (entry.getValue() instanceof long[])
                    args.putLongArray(entry.getKey(), (long[]) entry.getValue());
                else if (entry.getValue() instanceof Parcelable)
                    args.putParcelable(entry.getKey(), (Parcelable) entry.getValue());
                else if (entry.getValue() instanceof Parcelable[])
                    args.putParcelableArray(entry.getKey(), (Parcelable[]) entry.getValue());
                else {
                    Log.e(LOG_TAG, "This type is not supported. Key:" + entry.getKey() + ",   Value:" + entry.getValue());
                    args.putString(entry.getKey(), entry.getValue().toString());
                }
            }
        }
        return newInstance(clazz,args);
    }
    public static Fragment newInstance(Class clazz,Object data) {
        if(data!=null) {
            Bundle args = new Bundle();
            if (data instanceof Map) {
                for (Map.Entry<String, Object> entry : ((Map<String, Object>) data).entrySet()) {
                    if (entry.getValue() != null) {
                        if (entry.getValue() instanceof String)
                            args.putString(entry.getKey(), (String) entry.getValue());
                        else if (entry.getValue() instanceof String[])
                            args.putStringArray(entry.getKey(), (String[]) entry.getValue());
                        else if (entry.getValue() instanceof Integer)
                            args.putInt(entry.getKey(), (Integer) entry.getValue());
                        else if (entry.getValue() instanceof int[])
                            args.putIntArray(entry.getKey(), (int[]) entry.getValue());
                        else if (entry.getValue() instanceof Boolean)
                            args.putBoolean(entry.getKey(), (Boolean) entry.getValue());
                        else if (entry.getValue() instanceof boolean[])
                            args.putBooleanArray(entry.getKey(), (boolean[]) entry.getValue());
                        else if (entry.getValue() instanceof Bundle)
                            args.putBundle(entry.getKey(), (Bundle) entry.getValue());
                        else if (entry.getValue() instanceof Byte)
                            args.putByte(entry.getKey(), (Byte) entry.getValue());
                        else if (entry.getValue() instanceof byte[])
                            args.putByteArray(entry.getKey(), (byte[]) entry.getValue());
                        else if (entry.getValue() instanceof Character)
                            args.putChar(entry.getKey(), (Character) entry.getValue());
                        else if (entry.getValue() instanceof char[])
                            args.putCharArray(entry.getKey(), (char[]) entry.getValue());
                        else if (entry.getValue() instanceof CharSequence)
                            args.putCharSequence(entry.getKey(), (CharSequence) entry.getValue());
                        else if (entry.getValue() instanceof CharSequence[])
                            args.putCharSequenceArray(entry.getKey(), (CharSequence[]) entry.getValue());
                        else if (entry.getValue() instanceof Serializable)
                            args.putSerializable(entry.getKey(), (Serializable) entry.getValue());
                        else if (entry.getValue() instanceof Short)
                            args.putShort(entry.getKey(), (Short) entry.getValue());
                        else if (entry.getValue() instanceof short[])
                            args.putShortArray(entry.getKey(), (short[]) entry.getValue());
                        else if (entry.getValue() instanceof Double)
                            args.putDouble(entry.getKey(), (Double) entry.getValue());
                        else if (entry.getValue() instanceof double[])
                            args.putDoubleArray(entry.getKey(), (double[]) entry.getValue());
                        else if (entry.getValue() instanceof Float)
                            args.putFloat(entry.getKey(), (Float) entry.getValue());
                        else if (entry.getValue() instanceof float[])
                            args.putFloatArray(entry.getKey(), (float[]) entry.getValue());
                        else if (entry.getValue() instanceof Long)
                            args.putLong(entry.getKey(), (Long) entry.getValue());
                        else if (entry.getValue() instanceof long[])
                            args.putLongArray(entry.getKey(), (long[]) entry.getValue());
                        else if (entry.getValue() instanceof Parcelable)
                            args.putParcelable(entry.getKey(), (Parcelable) entry.getValue());
                        else if (entry.getValue() instanceof Parcelable[])
                            args.putParcelableArray(entry.getKey(), (Parcelable[]) entry.getValue());
                        else {
                            Log.e(LOG_TAG, "This type is not supported. Key:" + entry.getKey() + ",   Value:" + entry.getValue());
                            args.putString(entry.getKey(), entry.getValue().toString());
                        }
                    }
                }
            }
            else if (data instanceof Bundle)
                args = (Bundle) data;
            else if (data instanceof String)
                args.putString(DATA, (String) data);
            else if (data instanceof String[])
                args.putStringArray(DATA, (String[]) data);
            else if (data instanceof Integer)
                args.putInt(DATA, (Integer) data);
            else if (data instanceof int[])
                args.putIntArray(DATA, (int[]) data);
            else if (data instanceof Boolean)
                args.putBoolean(DATA, (Boolean) data);
            else if (data instanceof boolean[])
                args.putBooleanArray(DATA, (boolean[]) data);
            else if (data instanceof Bundle)
                args.putBundle(DATA, (Bundle) data);
            else if (data instanceof Byte)
                args.putByte(DATA, (Byte) data);
            else if (data instanceof byte[])
                args.putByteArray(DATA, (byte[]) data);
            else if (data instanceof Character)
                args.putChar(DATA, (Character) data);
            else if (data instanceof char[])
                args.putCharArray(DATA, (char[]) data);
            else if (data instanceof CharSequence)
                args.putCharSequence(DATA, (CharSequence) data);
            else if (data instanceof CharSequence[])
                args.putCharSequenceArray(DATA, (CharSequence[]) data);
            else if (data instanceof Serializable)
                args.putSerializable(DATA, (Serializable) data);
            else if (data instanceof Short)
                args.putShort(DATA, (Short) data);
            else if (data instanceof short[])
                args.putShortArray(DATA, (short[]) data);
            else if (data instanceof Double)
                args.putDouble(DATA, (Double) data);
            else if (data instanceof double[])
                args.putDoubleArray(DATA, (double[]) data);
            else if (data instanceof Float)
                args.putFloat(DATA, (Float) data);
            else if (data instanceof float[])
                args.putFloatArray(DATA, (float[]) data);
            else if (data instanceof Long)
                args.putLong(DATA, (Long) data);
            else if (data instanceof long[])
                args.putLongArray(DATA, (long[]) data);
            else if (data instanceof Parcelable)
                args.putParcelable(DATA, (Parcelable) data);
            else if (data instanceof Parcelable[])
                args.putParcelableArray(DATA, (Parcelable[]) data);
            else {
                Log.e(LOG_TAG, "This type is not supported. Key:" + DATA + ",   Value:" + data);
                args.putString(DATA, data.toString());
            }
            return newInstance(clazz,args);
        }
        else
            return newInstance(clazz);
    }
    //New Instance Ends
}
