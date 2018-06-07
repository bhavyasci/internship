package com.ecs.ppp;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;

import com.ecs.ppp.db.QuestAdapter;
import com.ecs.ppp.utils.NetConnection;
import com.ecs.ppp.utils.Utility;

public class RegistrationActivity extends Activity implements OnClickListener{
	
	QuestAdapter mDbHelper;
	Button btnRegister;
	ImageButton btnBack;
	EditText userName,userPwd,userConfPwd;
	NetConnection internet;
	Boolean netConnection;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.registration);
		btnBack=(ImageButton)findViewById(R.id.imgBtnBack);
		
		userName=(EditText)findViewById(R.id.editUserId);
		userPwd=(EditText)findViewById(R.id.editUserPassword);
		userConfPwd=(EditText)findViewById(R.id.editUserConfPassword);
		
		btnRegister=(Button)findViewById(R.id.btnRegister);
		btnRegister.setOnClickListener(this);
		btnBack.setOnClickListener(this);
		
		Typeface externalFont = Typeface.createFromAsset(getAssets(),
		"MTCORSVA.TTF");
		
	}

	
	
	
	@Override
	protected void finalize() throws Throwable {
		// TODO Auto-generated method stub
		super.finalize();
		mDbHelper.close();
	}

	


	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		if(v==btnRegister){
			try{
				if(userName.getText().toString().length()>0 && userPwd.getText().toString().length()<=4 && userConfPwd.getText().toString().length()<=4){
					Utility.ShowMessageBox(this, "Password must contain 6 characters...");
				}
				else if(userName.getText().toString().length()>0 && userPwd.getText().toString().length()>0 && userConfPwd.getText().toString().length()>0){
							
					if(userPwd.getText().toString().equals(userConfPwd.getText().toString())){
							mDbHelper = new QuestAdapter(this);
							mDbHelper.createDatabase();
							mDbHelper.open();
							int result=mDbHelper.registerData(userName.getText().toString(), userPwd.getText().toString());	
							mDbHelper.close();
							
							if(result==1){
								Utility.ShowMessageBox(this, "Registered Successfully...");
								startActivity(new Intent(RegistrationActivity.this,HomeActivity.class));
							}
							else if(result==-1){
								Utility.ShowMessageBox(this, "User Already Exist...");
							}
							else{	
								Utility.ShowMessageBox(this, "Registration Failed...");
							}
					}
					else{
						Utility.ShowMessageBox(RegistrationActivity.this, "Password does not match...");
					}
			}
			else{
					Utility.ShowMessageBox(RegistrationActivity.this, "All Fields are compulsory...");
			}
			}catch(Exception e){
				e.printStackTrace();
			}
		}	
		
		if(v==btnBack)
			startActivity(new Intent(RegistrationActivity.this,HomeActivity.class));
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
	
//	class MyAsyncTask extends AsyncTask<Void, Boolean, JSONArray> {
//		ProgressDialog pdiDialog;
//		TelephonyManager telephonyManager;
//		@Override
//		protected void onPreExecute() {
//			super.onPreExecute();
//			pdiDialog = new ProgressDialog(RegistrationActivity.this);
//			pdiDialog.setMessage("Loading...");
//			pdiDialog.show();
//		}
//
//		@TargetApi(9)
//		@Override
//		protected JSONArray doInBackground(Void... params) {
//
//			// /////////////////////////
//			if (Build.VERSION.SDK_INT >= 9) {
//				StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
//						.permitAll().build();
//
//				StrictMode.setThreadPolicy(policy);
//			}
//			// create JSON request object
//			/*
//			 * { "request" : { "from" : "$UDID", "with" : { "action" :
//			 * "GET_CATEGORY", "device" : { "id" : "$UDID", "time" :
//			 * "1234567890" } } } }
//			 */
//			
//			telephonyManager = (TelephonyManager)getSystemService(Context.TELEPHONY_SERVICE);
//			String deviceID = com.ecs.ppp.utils.Methods.getDeviceID(telephonyManager);
//			String deviceName=android.os.Build.MODEL;
//			try {
//				JSONObject request = new JSONObject();
//				request.put("from", deviceID);
//
//				JSONObject with = new JSONObject();
//				with.put("action", "REGISTER_USER");
//
//				JSONObject device = new JSONObject();
//				device.put("id", "deviceID");
//				device.put("time", new Date());
//				with.put("device", device);
//				
//				JSONObject reqdata=new JSONObject();
//				reqdata.put("deviceid", deviceID);
//				reqdata.put("devicename", deviceName);
//				reqdata.put("username", userName.getText());
//				reqdata.put("password", userPwd.getText());
//				with.put("data",reqdata);
//				
//				request.put("with", with);
//
//				JSONObject requestJson = new JSONObject();
//				requestJson.put("request", request);
//				
//				Log.d("Request String",""+requestJson.toString());
//				String responseString = WebServerCall
//						.putDataToServer(requestJson);
//
//				JSONObject responseJson = new JSONObject(responseString);
//				JSONObject response = responseJson.getJSONObject("response");
//				JSONObject responseWith = response.getJSONObject("with");
//				JSONArray data = responseWith.getJSONArray("data");
//				Log.d("Response Data:= ",data.toString());
//				
//				return data;
//			} catch (Throwable e) {
//				// TODO Auto-generated catch block
//
//				e.printStackTrace();
//			}
//			// ////////////////////////
//
//			return null;
//		}
//
//		@Override
//		protected void onPostExecute(JSONArray result) {
//			super.onPostExecute(result);
//			pdiDialog.dismiss();
//
//		}
//
//	}
}
