package com.ecs.ppp;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import com.ecs.ppp.db.QuestAdapter;
import com.ecs.ppp.utils.Constants;
import com.ecs.ppp.utils.Utility;

public class AdminHomeActivity extends Activity implements OnClickListener,
		OnItemClickListener {

	ImageButton imgBtnBack;
	private TextView textView1;
	ListView listUsers;
	List<String> userList = new ArrayList<String>();
	List<String> userIdList = new ArrayList<String>();
	QuestAdapter mDbHelper;
	private ArrayAdapter arrayAdapter;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.adminhome);
		loadUserRecords();
		Typeface externalFont = Typeface.createFromAsset(getAssets(),
				"BKANT.TTF");

		textView1 = (TextView)findViewById(R.id.textView1);
		textView1.setTypeface(externalFont);
		listUsers = (ListView) findViewById(R.id.listUsersRecords);
		arrayAdapter = new ArrayAdapter(this, R.layout.userlistlayout,
				R.id.label, userList);
		listUsers.setAdapter(arrayAdapter);
		listUsers.setOnItemClickListener(this);

		imgBtnBack = (ImageButton) findViewById(R.id.imgBtnBack);
		imgBtnBack.setOnClickListener(this);

	}

	public void loadUserRecords() {

		userList.clear();
		userIdList.clear();
		openDB();
		Cursor users = mDbHelper.getAllUsers();
		Log.d("Total User Records for graph : " + users.getCount(),
				"+++++++++++++");
		Log.d("UserList Lenght:", users.getCount() + "");
		if (users.getCount() >= 1)
			addData(users, userList, userIdList);
		users.close();
		closeDB();
	}

	public void openDB() {
		try {
			mDbHelper = new QuestAdapter(this);
			mDbHelper.createDatabase();
			mDbHelper.open();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void closeDB() {
		try {
			mDbHelper.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void addData(Cursor cursor, List<String> data, List<String> iddata) {
		do {
			iddata.add(cursor.getString(0));
			data.add(cursor.getString(1));
		} while (cursor.moveToNext());
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		if (v == imgBtnBack) {

			final AlertDialog.Builder alert = new AlertDialog.Builder(this);
			alert.setTitle("Are you sure you want to logout");
			alert.setPositiveButton("Ok",
					new DialogInterface.OnClickListener() {
						public void onClick(DialogInterface dialog,
								int whichButton) {
							finish();
							Utility.ShowMessageBox(AdminHomeActivity.this,
									"Successfully LogOut");
							startActivity(new Intent(AdminHomeActivity.this,
									HomeActivity.class));
						}
					});
			alert.setNegativeButton("Cancel",
					new DialogInterface.OnClickListener() {
						@Override
						public void onClick(DialogInterface dialog, int which) {
							// TODO Auto-generated method stub
							alert.show().dismiss();
						}
					});
			alert.show();
		}
	}

	@SuppressWarnings("deprecation")
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (Integer.valueOf(android.os.Build.VERSION.SDK) < 7 // Instead use
																// android.os.Build.VERSION.SDK_INT
																// <
																// android.os.Build.VERSION_CODES.ECLAIR
				&& keyCode == KeyEvent.KEYCODE_BACK
				&& event.getRepeatCount() == 0) {
			// Take care of calling this method on earlier versions of
			// the platform where it doesn't exist.
			onBackPressed();
		}
		return super.onKeyDown(keyCode, event);
	}

	@Override
	public void onBackPressed() {
		// This will be called either automatically for you on 2.0
		// or later, or by the code above on earlier versions of the
		// platform.
		return;
	}

	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
		// TODO Auto-generated method stub
		Constants.SELECTED_USERID = Integer.parseInt(userIdList.get(arg2));
		startActivity(new Intent(AdminHomeActivity.this,
				GenerateGraphActivity.class));
	}
}
