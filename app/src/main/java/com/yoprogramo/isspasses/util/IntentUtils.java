package com.yoprogramo.isspasses.util;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.provider.Settings;


public class IntentUtils {

    private static final String PACKAGE = "package";

    private IntentUtils() {
        // hide constructor
    }

    public static Intent createOpenSettingsScreen(final Context context) {
        final Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
        final Uri uri = Uri.fromParts(PACKAGE, context.getPackageName(), null);
        intent.setData(uri);
        return intent;
    }

}
