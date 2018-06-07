package com.ecs.ppp.utils;

import java.util.Random;

import android.content.Context;
import android.database.Cursor;
import android.view.Gravity;
import android.widget.Toast;

public class Utility {
	
	//function that returns the random numbers
	public static int getRandomNumber() {
		Random rand = new Random();
		int num = rand.nextInt(4);
		return num;
	}
	
	//function for splitting the data that are stored in string with , separated.
	public static String[] splitData(String values){
		
		String val[]=values.split(",");
		return val;
	}
	
	public static int getLength(String data[]){
		int count=0;
		for(int i=0;i<data.length;i++)
		{
			if(data[i]!="" && data[i]!=" ")
			count=count+1;
		}
		return count;
	}
	
	public static String GetColumnValue(Cursor cur, String ColumnName) {
		try {
			return cur.getString(cur.getColumnIndex(ColumnName));
		} catch (Exception ex) {
			return "";
		}
	}

	
	public static void ShowMessageBox(Context cont, String msg) {
		Toast toast = Toast.makeText(cont, msg, Toast.LENGTH_SHORT);
		// toast.setGravity(Gravity.CENTER, 0, 0);
		toast.setGravity(Gravity.CENTER | Gravity.CENTER, 0, 0);
		toast.show();
	}
	public static void ShowWaitMessageBox(Context cont, String msg) {
		Toast toast = Toast.makeText(cont, msg, Toast.LENGTH_LONG);
		// toast.setGravity(Gravity.CENTER, 0, 0);
		toast.setGravity(Gravity.CENTER | Gravity.CENTER, 0, 0);
		toast.show();
	}
}
