package com.yoprogramo.isspasses.util;


import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.support.v4.app.ActivityCompat;
import android.text.TextUtils;

import com.yoprogramo.isspasses.R;
import com.yoprogramo.isspasses.constants.Commons;
import com.yoprogramo.isspasses.controller.Bus;

import java.util.Arrays;
import java.util.List;

public final class PermissionUtil {

    private PermissionUtil() {
        // hide constructor
    }

    public interface PermissionListener {
        void cancelFromRationale();
    }

    public static boolean appHasPermission(final Context context, final String permission) {
        return ActivityCompat.checkSelfPermission(context, permission) == PackageManager.PERMISSION_GRANTED;
    }

    public static boolean appHasPermissions(final Context context, final List<String> permissions) {
        boolean hasPermissions = false;

        if (!CollectionUtils.isEmpty(permissions)) {
            hasPermissions = true;

            for (String permission : permissions) {
                hasPermissions &= appHasPermission(context, permission);
            }
        }

        return hasPermissions;
    }

    public static boolean appHasPermission(final Activity activity,
                                           final String permission,
                                           final int requestCode,
                                           final String rationale,
                                           final PermissionListener listener) {
        boolean hasPermission = false;
        if (activity != null && listener != null) {
            if (appHasPermission(activity, permission)) {
                hasPermission = true;
            } else {
                if (ActivityCompat.shouldShowRequestPermissionRationale(activity, permission)) {
                    showPermissionRationale(activity, permission, requestCode, rationale, listener);
                } else {
                    requestPermission(activity, permission, requestCode);
                }
            }
        }
        return hasPermission;
    }

    private static void showPermissionRationale(final Activity activity,
                                                final String permission,
                                                final int requestCode,
                                                final String rationale,
                                                final PermissionListener listener) {
        final DialogInterface.OnClickListener okListener = new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (activity != null) {
                    requestPermission(activity, permission, requestCode);
                }
            }
        };

        final DialogInterface.OnClickListener cancelListener = new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                listener.cancelFromRationale();
            }
        };

        final Resources res = activity.getResources();
        NativePopups.showDialog(activity, rationale, res.getString(R.string.Ok), res.getString(R.string.Cancel),
                okListener, cancelListener);
    }

    private static void requestPermission(final Activity activity,
                                          final String permission,
                                          final int requestCode) {
        ActivityCompat.requestPermissions(activity, new String[]{permission}, requestCode);
    }

    public static boolean hasPermissionToLocateUser(final Context context) {
        return appHasPermissions(context,
                Arrays.asList(Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION));
    }

    public static void openSettingsIfDeniedPermission(final Activity activity, final int permissionRequestCode,
                                                      String subTitle, String title) {
        final Resources res = activity.getResources();

        NativePopups.showDialog(activity, subTitle,
                title,
                res.getString(R.string.Ok), res.getString(R.string.settings),
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                }, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        activity.startActivityForResult(IntentUtils.createOpenSettingsScreen(activity),
                                permissionRequestCode);
                    }
                });
    }

    public static void handlePermissionsResult(int requestCode, String[] permissions, int[] grantResults,
                                               Activity activity) {
        if (!ArrayUtils.isEmpty(permissions) && grantResults != null) {
            final boolean granted = grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED;
            boolean showRationale = false;
            if (!TextUtils.isEmpty(permissions[0])) {
                showRationale = ActivityCompat.shouldShowRequestPermissionRationale(activity,
                        permissions[0]);
            }

            switch (requestCode) {
                case Commons.LOCATION_REQUEST_PERMISSIONS:
                    if (!showRationale) {
                        Prefs.getInstance().setShowLocationSettings(false);
                    }
                    Bus.onLocationPermissionGranted(granted);
                    break;
                default:
                    break;
            }
        }
    }

}
