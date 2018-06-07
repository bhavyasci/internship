package com.ecs.ppp.utils;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.widget.Toast;

public class MyReceiver extends BroadcastReceiver {

	

    /**
     * This method receives message for any application status(Install/ Uninstall) and display details.
     */
    @Override
    public void onReceive(Context context, Intent intent) {

        // Get application status(Install/ Uninstall)
        boolean applicationStatus = intent.getBooleanExtra(Intent.EXTRA_REPLACING, false);
        String toastMessage = null;

        // Check if the application is install or uninstall and display the message accordingly
        if(intent.getAction().equals("android.intent.action.PACKAGE_ADDED")){
            // Application Install
            toastMessage = "PACKAGE_INSTALL: "+  intent.getData().toString() + getApplicationName(context, intent.getData().toString(), PackageManager.GET_UNINSTALLED_PACKAGES);
            Log.d("Install", "Called");
        }
        else if(intent.getAction().equals("android.intent.action.PACKAGE_REMOVED")){
            // Application Uninstall
            toastMessage = "PACKAGE_REMOVED: "+  intent.getData().toString() + getApplicationName(context, intent.getData().toString(), PackageManager.GET_UNINSTALLED_PACKAGES);
            Log.d("Uninstall", "Called");
        }else if(intent.getAction().equals("android.intent.action.PACKAGE_REPLACED")){
            // Application Replaced
        	toastMessage = "PACKAGE_REPLACED: "+  intent.getData().toString() + getApplicationName(context, intent.getData().toString(), PackageManager.GET_UNINSTALLED_PACKAGES);
        	Log.d("Replaced", "Called");
        }

        //Display Toast Message
        if(toastMessage != null){
            Toast.makeText(context, toastMessage, Toast.LENGTH_LONG).show();
        }
    }

    /**
     * This method get application name name from application package name
     */
    private String getApplicationName(Context context, String data, int flag) {

        final PackageManager pckManager = context.getPackageManager();
        ApplicationInfo applicationInformation;
        try {
            applicationInformation = pckManager.getApplicationInfo(data, flag);
        } catch (PackageManager.NameNotFoundException e) {
            applicationInformation = null;
        }
        final String applicationName = (String) (applicationInformation != null ? pckManager.getApplicationLabel(applicationInformation) : "(unknown)");

        return applicationName;
    }
}