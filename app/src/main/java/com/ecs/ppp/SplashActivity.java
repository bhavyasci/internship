package com.ecs.ppp;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.util.Log;

import com.ecs.ppp.db.QuestAdapter;
import com.ecs.ppp.utils.NetConnection;
import com.ecs.ppp.utils.WebServerCall;

//import com.flurry.android.FlurryAgent;

public class SplashActivity extends Activity {

	 List<String> listTypeDB = new ArrayList<String>();
	 List<String> userListDB = new ArrayList<String>();
	 List<String> pwdListDB = new ArrayList<String>();
	 List<String> listTypeServer = new ArrayList<String>();
	 List<String> userListServer = new ArrayList<String>();
	
	 List<String> updateUserList = new ArrayList<String>();
	 List<String> UpdatePwdList = new ArrayList<String>();
	 List<String> updateTypeList = new ArrayList<String>();
	
	 List<String> listDeviceRecordsDB = new ArrayList<String>();
	 List<String> listServerRecordsDB = new ArrayList<String>();
	
	 List<String> updateServerRecordsDB = new ArrayList<String>();
	
	 QuestAdapter mDbHelper;
	 NetConnection internet;
	 Boolean netConnection;
	 int callData = 0;
	 private ProgressDialog progDialog;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.splash);

		Thread splashTread = new Thread() {
			@Override
			public void run() {
				try {
					sleep(2000);
				} catch (InterruptedException e) {
				} finally {
					startActivity(new Intent(SplashActivity.this,
							UpgradationActivity.class));
					SplashActivity.this.finish();
				}
			}

		};
		splashTread.start();

//		 loadAllUsers();
//		 loadInputTypes();
		 
		 
		// callProgressBar("Loading.....");
		
		 }
	
	private void progressBar()
	{
		try {

			internet = new NetConnection(SplashActivity.this);
			netConnection = internet.HaveNetworkConnection();

			if (netConnection == true) {

				try {

					callData = 1;
					getRequestData();
					getAllData2BeSynched();

					callData = 4;
					getRequestData();

					callData = 5;
					getRequestData();

					callData = 3;
					JSONArray asyntaskServerRecords = getRequestData();
					if (asyntaskServerRecords.length() >= 1) {
						for (int i = 0; i < asyntaskServerRecords.length(); i++) {
							JSONObject store;
							store = asyntaskServerRecords.getJSONObject(i);
							String createdOn = store.get("createdon")
									.toString();
							listServerRecordsDB.add(createdOn);
						}
					}

					callData = 7;
					JSONArray asyntaskdata7 = getRequestData();
					Log.d("Webservice InpuType ",
							"Length: " + asyntaskdata7.length());
					if (asyntaskdata7.length() >= 1) {
						for (int i = 0; i < asyntaskdata7.length(); i++) {
							JSONObject store;

							store = asyntaskdata7.getJSONObject(i);
							String id = store.get("id").toString();
							String type = store.get("type").toString();
							String userType = store.get("usertype").toString();

							Log.d("Updating Id or inserting : " + id, " Type: "
									+ type);
							openDB();
							int checkAvail = checkTypeAvaibility(type);
							String updateUserType = "";
							if (userType.equalsIgnoreCase("admin"))
								updateUserType = "A";
							else
								updateUserType = "U";
							if (checkAvail == 1)
								mDbHelper.saveTypeFromServer(type,
										updateUserType, Integer.parseInt(id));
							else
								mDbHelper
										.saveTypeId(type, Integer.parseInt(id));
							closeDB();
						}

					}

					callData = 8;

					JSONArray asyntaskdata8 = getRequestData();
					Log.d("Webservices Users ",
							"Length: " + asyntaskdata8.length());
					if (asyntaskdata8.length() >= 1) {
						for (int i = 0; i < asyntaskdata8.length(); i++) {
							JSONObject store;

							store = asyntaskdata8.getJSONObject(i);
							String userId = store.get("id").toString();
							String username = store.get("username").toString();
							String password = store.get("password").toString();

							openDB();

							int checkAvail = checkUserAvaibility(username);
							if (checkAvail == 1)
								mDbHelper.registerDataFromServer(username,
										password);
							else
								mDbHelper.saveUserId(username,
										Integer.parseInt(userId));
							closeDB();
						}

					}

					loadAllSaveRecords();
					getSaveResultRecords2besync();
					callData = 6; // Sync Save Result Records
					getRequestData();

				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();

				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		 startActivity(new Intent(SplashActivity.this, HomeActivity.class));
		 SplashActivity.this.finish();
	}
	
	private void callProgressBar(String msg) {

		progDialog = ProgressDialog.show(this, null, msg, true);

		new Thread() {

			@Override
			public void run() {
				try {
					// just doing some long operation
					sleep(1000);
					runOnUiThread(new Runnable() {
						@Override
						public void run() {
							// here you can modify UI here
							// modifiying UI element happen here and at the
							// end you cancel the progress dialog
							
							progressBar();

						}
					}); // runOnUIthread

				} catch (Exception e) {
				} finally {
					progDialog.dismiss();
				}
			}
		}.start();

	}
		
		 public void loadAllUsers() {
		
		 userListDB.clear();
		 openDB();
		 Cursor users = mDbHelper.getAllUserName();
		 if (users.getCount() >= 1) {
		 do {
		 userListDB.add(users.getString(1));
		 pwdListDB.add(users.getString(2));
		 } while (users.moveToNext());
		 }
		 users.close();
		 closeDB();
		 }
		
		 public void loadInputTypes() {
		
		 listTypeDB.clear();
		 openDB();
		 Cursor totInputType = mDbHelper.getAllInputType();
		 if (totInputType.getCount() >= 1)
		 addData(totInputType, listTypeDB);
		
		 totInputType.close();
		 closeDB();
		 }
		
		 public void loadAllSaveRecords() {
		
		 listDeviceRecordsDB.clear();
		 openDB();
		 Cursor saveRecords = mDbHelper.getAllSaveRecords();
		 Log.d("Save Records : ", saveRecords.getCount() + "");
		 if (saveRecords.getCount() >= 1) {
		 do {
		 String data = "";
		 for (int i = 0; i < 11; i++) {
		 if (i == 0) { // getting user acutual id
		 Log.d("i==0:", "userId: " + saveRecords.getString(i));
		 Cursor getUserAcutalId = mDbHelper
		 .getUserAcutalId(Integer.parseInt(saveRecords
		 .getString(i)));
		 int id = 0;
		
		 do {
		 id = Integer.parseInt(getUserAcutalId.getString(0));
		 } while (getUserAcutalId.moveToNext());
		
		 getUserAcutalId.close();
		 data = data + id + "/";
		
		 Log.d("Data:0 values: ", "" + data);
		 } else if (i == 1) { // getting user input type acutual id
		 Log.d("i==1:",
		 "actualTypeId(USER): "
		 + saveRecords.getString(i));
		 if (saveRecords.getString(i).equalsIgnoreCase("0")) {
		 data = data + saveRecords.getString(i) + "/";
		 } else {
		 Cursor getUInputAcutalId = mDbHelper
		 .getUserInputTypeAcutalId(Integer
		 .parseInt(saveRecords.getString(i)));
		 int id = 0;
		
		 do {
		 id = Integer.parseInt(getUInputAcutalId
		 .getString(0));
		 } while (getUInputAcutalId.moveToNext());
		
		 Log.d("UserInputId: ", "" + id);
		 getUInputAcutalId.close();
		 data = data + id + "/";
		 }
		 Log.d("Data:1 values: ", "" + data);
		 } else if (i == 2) { // getting admin input type acutual id
		 Log.d("i==2:",
		 "actualTypeId(Admin): "
		 + saveRecords.getString(i));
		 if (saveRecords.getString(i).equalsIgnoreCase("0")) {
		 data = data + saveRecords.getString(i) + "/";
		 } else {
		 Cursor getAAcutalId = mDbHelper
		 .getAdminInputTypeAcutalId(Integer
		 .parseInt(saveRecords.getString(i)));
		 int id = 0;
		 do {
		 id = Integer
		 .parseInt(getAAcutalId.getString(0));
		 } while (getAAcutalId.moveToNext());
		
		 Log.d("AdminInputId: ", "" + id);
		 getAAcutalId.close();
		
		 data = data + id + "/";
		 }
		 Log.d("Data:2 values: ", "" + data);
		 } else {
		
		 data = data + saveRecords.getString(i) + "/";
		 Log.d("Data:" + i + " values: ", "" + data);
		 }
		 }
		 listDeviceRecordsDB.add(data);
		 } while (saveRecords.moveToNext());
		 }
		 Log.d("Save Records Data: ", listDeviceRecordsDB + "");
		 saveRecords.close();
		 closeDB();
		 }
		
		 public void addData(Cursor cursor, List<String> data) {
		 do {
		 data.add(cursor.getString(1));
		 } while (cursor.moveToNext());
		 cursor.close();
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
		
		 public int checkTypeAvaibility(String typeName) {
		 int flag = 1;
		 Cursor checkType = mDbHelper.checkTypeExist(typeName);
		 if (checkType.getCount() >= 1) {
		 do {
		 if (checkType.getString(0).equalsIgnoreCase(typeName)) {
		 flag = 0;
		 }
		 } while (checkType.moveToNext());
		 }
		 checkType.close();
		 return flag;
		 }
		
		 public int checkUserAvaibility(String userName) {
		 int flag = 1;
		 Cursor checkUser = mDbHelper.checkUserExist(userName);
		 if (checkUser.getCount() >= 1) {
		 do {
		 if (checkUser.getString(0).equalsIgnoreCase(userName)) {
		 flag = 0;
		 }
		 } while (checkUser.moveToNext());
		 }
		 checkUser.close();
		 return flag;
		 }
		
		 public JSONArray getRequestData(){
		
		 TelephonyManager telephonyManager;
		 telephonyManager = (TelephonyManager)
		 getSystemService(Context.TELEPHONY_SERVICE);
		 String deviceID = com.ecs.ppp.utils.Methods
		 .getDeviceID(telephonyManager);
		 String deviceName = android.os.Build.MODEL;
		 try {
		 JSONObject requestJson = new JSONObject();
		 if (callData == 1) {
		 JSONObject request = new JSONObject();
		 request.put("from", deviceID);
		
		 JSONObject with = new JSONObject();
		 with.put("action", "REGISTER_DEVICE");
		
		 JSONObject device = new JSONObject();
		 device.put("id", "deviceID");
		 device.put("time", new Date());
		 with.put("device", device);
		
		 JSONObject reqdata = new JSONObject();
		 reqdata.put("deviceid", deviceID);
		 reqdata.put("devicename", deviceName);
		 with.put("data", reqdata);
		
		 request.put("with", with);
		
		 requestJson.put("request", request);
		 }
		 if (callData == 3) {// getAll result save on server
		 JSONObject request = new JSONObject();
		 request.put("from", deviceID);
		
		 JSONObject with = new JSONObject();
		 with.put("action", "GET_DEVICE_RESULT");
		
		 JSONObject device = new JSONObject();
		 device.put("id", "deviceID");
		 device.put("time", new Date());
		 with.put("device", device);
		
		 JSONObject reqdata = new JSONObject();
		 reqdata.put("deviceid", deviceID);
		 reqdata.put("devicename", deviceName);
		 with.put("data", reqdata);
		
		 request.put("with", with);
		 requestJson.put("request", request);
		 }
		 if (callData == 4) {// save all input types to server
		 Log.d("Update typeList:", "" + updateTypeList);
		 JSONObject request = new JSONObject();
		 request.put("from", deviceID);
		
		 JSONObject with = new JSONObject();
		 with.put("action", "SAVE_ALL_INPUT_TYPE");
		
		 JSONObject device = new JSONObject();
		 device.put("id", "deviceID");
		 device.put("time", new Date());
		 with.put("device", device);
		
		 JSONObject data = new JSONObject();
		 JSONObject userDevice = new JSONObject();
		 userDevice.put("deviceid", deviceID);
		 userDevice.put("devicename", deviceName);
		 data.put("userdevice", userDevice);
		
		 JSONObject types = new JSONObject();
		 for (int i = 0; i < updateTypeList.size(); i++) {
		 JSONObject typeData = new JSONObject();
		 typeData.put("type", updateTypeList.get(i));
		 String val = i + 1 + "";
		 types.put(val.trim(), typeData);
		 data.put("types", types);
		 }
		
		 with.put("data", data);
		
		 request.put("with", with);
		 requestJson.put("request", request);
		 }
		 if (callData == 5) {// save all users to the server
		 Log.d("Update userList:", "" + updateUserList);
		 Log.d("Update pwdList:", "" + UpdatePwdList);
		 JSONObject request = new JSONObject();
		 request.put("from", deviceID);
		
		 JSONObject with = new JSONObject();
		 with.put("action", "REGISTER_ALL_USER");
		
		 JSONObject device = new JSONObject();
		 device.put("id", "deviceID");
		 device.put("time", new Date());
		 with.put("device", device);
		
		 JSONObject data = new JSONObject();
		 JSONObject userDevice = new JSONObject();
		 userDevice.put("deviceid", deviceID);
		 userDevice.put("devicename", deviceName);
		 data.put("userdevice", userDevice);
		
		 JSONObject users = new JSONObject();
		 for (int i = 0; i < updateUserList.size(); i++) {
		 JSONObject userData = new JSONObject();
		 userData.put("username", updateUserList.get(i));
		 userData.put("password", UpdatePwdList.get(i));
		 String val = i + 1 + "";
		 users.put(val.trim(), userData);
		 data.put("users", users);
		 }
		 with.put("data", data);
		
		 request.put("with", with);
		 requestJson.put("request", request);
		 }
		 if (callData == 6) {// save all result data to server
		 Log.d("Update serverRecordList:", ""
		 + updateServerRecordsDB);
		 JSONObject request = new JSONObject();
		 request.put("from", deviceID);
		
		 JSONObject with = new JSONObject();
		 with.put("action", "SAVE_USER_ALL_SCORE");
		
		 JSONObject device = new JSONObject();
		 device.put("id", deviceID);
		 device.put("time", new Date());
		 with.put("device", device);
		
		 JSONObject data = new JSONObject();
		
		 JSONObject users = new JSONObject();
		
		 for (int i = 0; i < updateServerRecordsDB.size(); i++) {
		 JSONObject userData = new JSONObject();
		 String recData[] = updateServerRecordsDB.get(i).split(
		 "/");
		 userData.put("userid", recData[0]);
		
		 if (recData[1].equalsIgnoreCase("0")) {
		 userData.put("userinputtypeid", "null");
		 } else {
		 userData.put("userinputtypeid", recData[1]);
		 }
		 if (recData[2].equalsIgnoreCase("0")) {
		 userData.put("admininputtypeid", "null");
		 } else {
		 userData.put("admininputtypeid", recData[2]);
		 }
		 userData.put("mentalacuity", recData[3]);
		 userData.put("emoacuity", recData[4]);
		 userData.put("selfacuity", recData[5]);
		 userData.put("test1", recData[6]);
		 userData.put("test2", recData[7]);
		 userData.put("test3", recData[8]);
		 userData.put("sum", recData[9]);
		 userData.put("createdon", recData[10]);
		
		 // Update Sync Records to True
		 // mDbHelper.updateSyncRecordStatus(Integer.parseInt(recData[0]),
		 // recData[10]);
		
		 String val = i + 1 + "";
		 users.put(val.trim(), userData);
		
		 }
		 with.put("data", users);
		
		 request.put("with", with);
		 requestJson.put("request", request);
		 }
		 if (callData == 7) { // get all input types for updation
		 JSONObject request = new JSONObject();
		 request.put("from", deviceID);
		
		 JSONObject with = new JSONObject();
		 with.put("action", "GET_INPUT_TYPE");
		
		 JSONObject device = new JSONObject();
		 device.put("id", "deviceID");
		 device.put("time", new Date());
		 with.put("device", device);
		
		 JSONObject reqdata = new JSONObject();
		 reqdata.put("deviceid", deviceID);
		 reqdata.put("devicename", deviceName);
		 with.put("data", reqdata);
		
		 request.put("with", with);
		 requestJson.put("request", request);
		 }
		 if (callData == 8) {// get all users for updation
		 JSONObject request = new JSONObject();
		 request.put("from", deviceID);
		
		 JSONObject with = new JSONObject();
		 with.put("action", "GET_USERS");
		
		 JSONObject device = new JSONObject();
		 device.put("id", "deviceID");
		 device.put("time", new Date());
		 with.put("device", device);
		
		 JSONObject reqdata = new JSONObject();
		 reqdata.put("deviceid", deviceID);
		 reqdata.put("devicename", deviceName);
		 with.put("data", reqdata);
		
		 request.put("with", with);
		 requestJson.put("request", request);
		 }
		 Log.d("Request String", "" + requestJson.toString());
		 String responseString = WebServerCall
		 .putDataToServer(requestJson);
		
		 JSONObject responseJson = new JSONObject(responseString);
		 JSONObject response = responseJson.getJSONObject("response");
		 JSONObject responseWith = response.getJSONObject("with");
		 JSONArray data = responseWith.getJSONArray("data");
		 Log.d("Response Data:= ", data.toString());
		
		 return data;
		 } catch (Throwable e) {
		 // TODO Auto-generated catch block
		 e.printStackTrace();
		 }
		 // ////////////////////////
		 return null;
		 }
		
		 public void getAllData2BeSynched() {
		
		 for (int i = 0; i < userListDB.size(); i++) {
		 updateUserList.add(userListDB.get(i));
		 UpdatePwdList.add(pwdListDB.get(i));
		 }
		
		 for (int i = 0; i < listTypeDB.size(); i++) {
		 updateTypeList.add(listTypeDB.get(i));
		 }
		 Log.d("Total DB Records: ", "" + listDeviceRecordsDB.size());
		 Log.d("Total Server Records: ", "" + listServerRecordsDB.size());
		
		 }
		
		 public void getSaveResultRecords2besync() {
		 for (int i = 0; i < listDeviceRecordsDB.size(); i++) {
		 int flag = 0;
		 for (int j = 0; j < listServerRecordsDB.size(); j++) {
		 String data[] = listDeviceRecordsDB.get(i).split("/");
		 Log.d("Data[length-1]:= " + data[data.length - 1], "----------");
		 if (data[data.length - 1].equalsIgnoreCase(listServerRecordsDB
		 .get(j))) {
		 flag = 1;
		 break;
		 }
		 }
		 if (flag == 0) {
		 Log.d("Update device records:= ",
		 "" + listDeviceRecordsDB.get(i));
		 updateServerRecordsDB.add(listDeviceRecordsDB.get(i));
		 }
		 }
		 Log.d("Total Update Records: ", "" + updateServerRecordsDB.size());
	}
}
