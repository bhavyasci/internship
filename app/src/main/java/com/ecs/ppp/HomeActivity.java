package com.ecs.ppp;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.StrictMode;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.ecs.ppp.db.QuestAdapter;
import com.ecs.ppp.utils.Constants;
import com.ecs.ppp.utils.Methods;
import com.ecs.ppp.utils.NetConnection;
import com.ecs.ppp.utils.WebServerCall;

import com.ecs.ppp.utils.Utility;

public class HomeActivity extends Activity implements OnClickListener,OnItemSelectedListener{

	private Spinner spinUserType;
	public static Spinner spinUserId;
	List<String> listType = new ArrayList<String>();
	List<String> userList = new ArrayList<String>();
	
	QuestAdapter mDbHelper;
	Button btnLogin,btnSignUp;
	ImageButton imgBtnInfo,imgBtnQuit;
	EditText userPwd;
	String id,type;
	BroadcastReceiver receiver;
	NetConnection internet;
	Boolean netConnection;
	int callData=0;
	String inputType="";
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.home);
		
		Constants.EMOTIONAL_ACUITY_RESULT=0;
		Constants.SELF_ACUITY_RESULT=0;
		Constants.MENTAL_ACUITY_RESULT=0;
		Constants.CUSTOM_ACUITY_1_RESULT=0;
		Constants.CUSTOM_ACUITY_2_RESULT=0;
		Constants.CUSTOM_ACUITY_3_RESULT=0;
		

		
		loadInputTypes();
		loadAllUsers();
		
		spinUserId=(Spinner)findViewById(R.id.spinUserId);
		spinUserId.setAdapter(setAdapData(userList));
		spinUserId.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1,
					int arg2, long arg3) {
				// TODO Auto-generated method stub
			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
				// TODO Auto-generated method stub
			}
		});
		
		userPwd=(EditText)findViewById(R.id.editUserPassword);

		spinUserType = (Spinner) findViewById(R.id.spinInputType);
		spinUserType.setAdapter(setAdapData(listType));
		spinUserType.setOnItemSelectedListener(this);
		spinUserType.setVisibility(View.INVISIBLE);
		
		imgBtnQuit=(ImageButton)findViewById(R.id.imgBtnQuit);
		imgBtnQuit.setOnClickListener(this);
		btnSignUp=(Button)findViewById(R.id.btnSignUp);
		btnSignUp.setOnClickListener(this);
		btnLogin=(Button)findViewById(R.id.btnLogin);
		btnLogin.setOnClickListener(this);
		imgBtnInfo=(ImageButton)findViewById(R.id.imgBtnInfo);
		imgBtnInfo.setOnClickListener(this);
		
		Typeface externalFont = Typeface.createFromAsset(getAssets(),"BKANT.TTF");
		
	}

	public void loadInputTypes()
	{
		
		listType.clear();
		openDB();
		Cursor totInputType = mDbHelper.getAllInputs();
		listType.add("Input Type");
		listType.add("<New>");

		
		Log.d("Input Type Lenght:",totInputType.getCount()+"");
		if(totInputType.getCount()>=1)
		addData(totInputType, listType);
		
		totInputType.close();
		closeDB();
	}
	public void loadAllUsers()
	{
		
		userList.clear();
		openDB();
		Cursor users = mDbHelper.getAllUsers();
		userList.add("Select User");
		Log.d("UserList Lenght:",users.getCount()+"");
		if(users.getCount()>=1)
		addData(users, userList);
		//userList.add("admin");
		
		users.close();
		closeDB();
	}
	
	public void addData(Cursor cursor,List<String> data){
		do {
			data.add(cursor.getString(1));
		}
		while (cursor.moveToNext());
	}
	
	public ArrayAdapter<String> setAdapData(List<String> data){
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
				R.layout.my_spinner_style, data) {

			@Override
			public View getView(int position, View convertView, ViewGroup parent) {
				View v = super.getView(position, convertView, parent);

				Typeface externalFont = Typeface.createFromAsset(getAssets(),
						"BKANT.TTF");
				((TextView) v).setTypeface(externalFont);

				return v;
			}

			@Override
			public View getDropDownView(int position, View convertView,
					ViewGroup parent) {
				View v = super.getDropDownView(position, convertView, parent);

				Typeface externalFont = Typeface.createFromAsset(getAssets(),
						"BKANT.TTF");
				((TextView) v).setTypeface(externalFont);
				((TextView) v).setTextColor(Color.WHITE);
				((TextView) v).setTextSize(18);
				v.setBackgroundResource(R.drawable.dropsimple);
				return v;
			}
		};

		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		return adapter;
	}
	@Override
	protected void finalize() throws Throwable {
		// TODO Auto-generated method stub
		super.finalize();
		mDbHelper.close();
	}

	@Override
	public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2,
			long arg3) {
		// TODO Auto-generated method stub
		String selectedType = "a";//spinUserType.getSelectedItem().toString();
		
			if(arg2>1){
				try{
						
						openDB();
						Cursor getDetails=mDbHelper.getInputTypeCreater(selectedType);
						do{
							Constants.SELECTED_INPUTTYPE=Integer.parseInt(getDetails.getString(0));
							Constants.SELECTED_INPUTTYPE_CREATOR=getDetails.getString(1);
						}
						while (getDetails.moveToNext());
						Log.d("********************","**********************");
						Log.d("Input Type Creator: "+Constants.SELECTED_INPUTTYPE_CREATOR," Id:"+Constants.SELECTED_INPUTTYPE);
						Log.d("********************","**********************");
						
						getDetails.close();
						closeDB();
					}catch(Exception e){
							e.printStackTrace();
					}
			}
				Log.d("Selected InputypeID: "+Constants.SELECTED_INPUTTYPE,"Selected Inputype Creator: "+Constants.SELECTED_INPUTTYPE_CREATOR);
				
				if(arg2==1){
					  final AlertDialog.Builder alert = new AlertDialog.Builder(this);
					     final EditText input = new EditText(this);
					     alert.setTitle("Enter New InputType Name:");
					     alert.setView(input);
					     //final String s=input.getText().toString();
					     alert.setPositiveButton("Ok", new DialogInterface.OnClickListener() 
					     {
					      public void onClick(DialogInterface dialog, int whichButton) 
					      {
					        inputType= "a";//input.getText().toString().trim();
					        try{
								if(inputType.length()>0){
												
												openDB();
												Cursor totRec = mDbHelper.getTotalTypes();
												int totalRecords=0;
												totalRecords=Integer.parseInt(totRec.getString(0));
												int result=1;//mDbHelper.saveType(inputType,"U",totalRecords+1);
												callData=3;
												closeDB();
												if(result==1){
													Utility.ShowMessageBox(HomeActivity.this, "Saved Successfully...");
													loadInputTypes();
													spinUserType.setSelection(listType.size()-1);
												}
												else if(result==-1){
													Utility.ShowMessageBox(HomeActivity.this, "Input already Exist...");
												}
												else{	
													Utility.ShowMessageBox(HomeActivity.this, "Saving Failed...");
												}
										
								}
								else{

										Utility.ShowMessageBox(HomeActivity.this, "All Fields are compulsory...");
										alert.show();
								}
								}catch(Exception e){
									e.printStackTrace();
								}
					      }
					     });
					     alert.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
							
							@Override
							public void onClick(DialogInterface dialog, int which) {
								// TODO Auto-generated method stub
							}
						});
					    alert.show();
				}
	}

	@Override
	public void onNothingSelected(AdapterView<?> arg0) {
		// TODO Auto-generated method stub
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		if(v==imgBtnQuit){
			
			 final AlertDialog.Builder alert = new AlertDialog.Builder(this);
		     alert.setTitle("Are you sure you want to Exit");
		     alert.setPositiveButton("Ok", new DialogInterface.OnClickListener() 
		     {
		      public void onClick(DialogInterface dialog, int whichButton) 
		      {
		    	// do something on back.
		    	 finish();
		    	 HomeActivity.this.finish();
		      }
		     });
		     alert.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
				
				@Override
				public void onClick(DialogInterface dialog, int which) {
					// TODO Auto-generated method stub
					alert.show().dismiss();
				}
			});
		    alert.show();
				
			}
		if(v==btnLogin){
		
			if(spinUserId.getSelectedItem().toString().trim().equalsIgnoreCase("admin") &&userPwd.getText().toString().trim().equalsIgnoreCase("admin")){
				startActivity(new Intent(HomeActivity.this,AdminHomeActivity.class));
			}
			else if(spinUserId.getSelectedItemPosition()!=0 && userPwd.getText().toString().length()>0 && Constants.SELECTED_INPUTTYPE!=1){
				openDB();
				Cursor checkUser = mDbHelper.checkUserAvaibility(spinUserId.getSelectedItem().toString().trim(), userPwd.getText().toString().trim());
				int id=0;
				if(Utility.GetColumnValue(checkUser,"userId").length()>0){
					id = Integer.parseInt(Utility.GetColumnValue(checkUser,"userId"));
				}
				if(id>=1){
						Constants.SELECTED_USERID=id;
						Log.d("Selected UserId: ",""+Constants.SELECTED_USERID);
						
						startActivity(new Intent(HomeActivity.this,TestMenuActivity.class));
				}else{
					Utility.ShowMessageBox(HomeActivity.this, "Login Failed...");
				}
				checkUser.close();
			}
			else{
				//startActivity(new Intent(HomeActivity.this,TestMenuActivity.class));


					Utility.ShowMessageBox(HomeActivity.this, "All Fields are compulsory...");
			}
			
			openDB();
		}	
		if(v==btnSignUp){
			startActivity(new Intent(HomeActivity.this,RegistrationActivity.class));
		}
		if(v==imgBtnInfo){
			Constants.ACTIVITY_CALL=1;
			startActivity(new Intent(HomeActivity.this,EmotionalAcuityActivity.class));
		}
		
	}
	public void openDB(){
		try{
			mDbHelper = new QuestAdapter(this);
			mDbHelper.createDatabase();
			mDbHelper.open();
		}catch(Exception e){
			e.printStackTrace();
		}
		
	}
	public void closeDB(){
		try{
			mDbHelper.close();
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event)  {
	    if (  Integer.valueOf(android.os.Build.VERSION.SDK) < 7 //Instead use android.os.Build.VERSION.SDK_INT < android.os.Build.VERSION_CODES.ECLAIR
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
		

}
//android:visibility="gone"