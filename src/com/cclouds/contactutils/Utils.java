package com.cclouds.contactutils;

import android.os.Build;

public class Utils {
	/**
     * Uses static final constants to detect if the device's platform version is Honeycomb or
     * later.
     */
    public static boolean hasHoneycomb() {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB;
    }
}
