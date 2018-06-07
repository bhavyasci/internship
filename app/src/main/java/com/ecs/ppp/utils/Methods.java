package com.ecs.ppp.utils;

import android.telephony.TelephonyManager;

public class Methods {
	public static String getDeviceID(TelephonyManager phonyManager) {

		String id = phonyManager.getDeviceId();
		if (id == null) {
			id = "not available";
		}

		int phoneType = phonyManager.getPhoneType();
		switch (phoneType) {
		case TelephonyManager.PHONE_TYPE_NONE:
			return "NONE: " + id;

		case TelephonyManager.PHONE_TYPE_GSM:
			return "GSM: IMEI=" + id;

		case TelephonyManager.PHONE_TYPE_CDMA:
			return "CDMA: MEID/ESN=" + id;

		default:
			return "UNKNOWN: ID=" + id;
		}
	}

	/**
	 * Computes the distance in kilometers between two points on Earth.
	 * 
	 * @param lat1
	 *            Latitude of the first point
	 * @param lon1
	 *            Longitude of the first point
	 * @param lat2
	 *            Latitude of the second point
	 * @param lon2
	 *            Longitude of the second point
	 * @return Distance between the two points in kilometers.
	 */

	public static double distanceKm(double lat1, double lon1, double lat2,
			double lon2) {
		int EARTH_RADIUS_KM = 6371;
		double lat1Rad = Math.toRadians(lat1);
		double lat2Rad = Math.toRadians(lat2);
		double deltaLonRad = Math.toRadians(lon2 - lon1);

		return Math
				.acos(Math.sin(lat1Rad) * Math.sin(lat2Rad) + Math.cos(lat1Rad)
						* Math.cos(lat2Rad) * Math.cos(deltaLonRad))
				* EARTH_RADIUS_KM;
	}
}
