package com.yoprogramo.isspasses.util;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.text.TextUtils;


public final class NativePopups {
    private static final String TAG = NativePopups.class.getSimpleName();

    private NativePopups() {
        // Hide constructor
    }

    public static void showDialog(Context context, String msg, String title,
                                  String okButtonTitle, String cancelButtonTitle,
                                  DialogInterface.OnClickListener okButtonClickListener,
                                  DialogInterface.OnClickListener cancelButtonClickListener) {
        showDialog(true, context, 0, msg, title, okButtonTitle, cancelButtonTitle,
                okButtonClickListener, cancelButtonClickListener);
    }

    public static void showDialog(final boolean isCancellable,
                                  Context context, int theme, String msg, String title,
                                  String okButtonTitle, String cancelButtonTitle,
                                  DialogInterface.OnClickListener okButtonClickListener,
                                  DialogInterface.OnClickListener cancelButtonClickListener) {
        if (context != null) {
            final AlertDialog.Builder builder = theme == 0 ?
                    new AlertDialog.Builder(context) :
                    new AlertDialog.Builder(context, theme);
            // running through html parser in case string from API that has HTML embedded.
            builder.setCancelable(isCancellable);
            if (!TextUtils.isEmpty(title)) {
                builder.setTitle(title);
            }
            if (!TextUtils.isEmpty(okButtonTitle) && okButtonClickListener != null) {
                builder.setNegativeButton(okButtonTitle, okButtonClickListener);
            }
            if (!TextUtils.isEmpty(cancelButtonTitle) && cancelButtonClickListener != null) {
                builder.setPositiveButton(cancelButtonTitle, cancelButtonClickListener);
            }

            final AlertDialog dialog = builder.create();
            showDialogIfActivityExists(dialog, context);
        }
    }

    private static void showDialogIfActivityExists(AlertDialog dialog, Context context) {
        if (context instanceof Activity) {
            if (!((Activity) context).isFinishing()) {
                dialog.show();
            }
        } else {
            dialog.show();
        }
    }

    public static void showDialog(Context context, String msg, String okButtonTitle, String cancelButtonTitle,
                                  DialogInterface.OnClickListener okButtonClickListener,
                                  DialogInterface.OnClickListener cancelButtonClickListener) {
        showDialog(context, msg, null, okButtonTitle, cancelButtonTitle,
                okButtonClickListener, cancelButtonClickListener);
    }
}