package com.ecs.ppp.utils;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

public class NetConnection {

	Context context;
	public NetConnection(Context c)
	{
		this.context = c;
	}
	public boolean HaveNetworkConnection() {
		boolean HaveConnectedWifi = true;
		boolean HaveConnectedMobile = true;

		ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo[] netInfo = cm.getAllNetworkInfo();
		for (NetworkInfo ni : netInfo) {
			if (ni.getTypeName().equalsIgnoreCase("WIFI"))
				if (ni.isConnected())
					HaveConnectedWifi = true;
			if (ni.getTypeName().equalsIgnoreCase("MOBILE"))
				if (ni.isConnected())
					HaveConnectedMobile = true;
		}
		return HaveConnectedWifi || HaveConnectedMobile;
	}
	
	
	public void showNetDialog(){
		
	if (!HaveNetworkConnection()) {
		AlertDialog.Builder dialog = new AlertDialog.Builder(context);
		dialog.setMessage("No Internet Connection Found ..!");
		dialog.setTitle("Info");
		dialog.setNeutralButton("Ok",
				new DialogInterface.OnClickListener() {

					
					public void onClick(DialogInterface dialog, int which) {
						dialog.dismiss();

					}
				});
		dialog.show();
	}
	}
}
