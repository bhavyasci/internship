package com.ecs.ppp.db;

import java.io.IOException;
import java.util.Random;
import com.ecs.ppp.utils.Constants;

import android.R.bool;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

public class QuestAdapter {
	protected static final String TAG = "DataAdapter";

	private final Context mContext;
	private SQLiteDatabase mDb;
	private DataBaseHelper mDbHelper;

	public QuestAdapter(Context context) {
		this.mContext = context;
		mDbHelper = new DataBaseHelper(mContext);
	}

	public QuestAdapter createDatabase() throws SQLException {
		try {
			mDbHelper.createDataBase();
		} catch (IOException mIOException) {
			Log.e(TAG, mIOException.toString() + "  UnableToCreateDatabase");
			throw new Error("UnableToCreateDatabase");
		}
		return this;
	}

	public QuestAdapter open() throws SQLException {
		try {
			mDbHelper.openDataBase();
			mDb = mDbHelper.getReadableDatabase();
		} catch (SQLException mSQLException) {
			Log.e(TAG, "open >>" + mSQLException.toString());
			throw mSQLException;
		}
		return this;
	}

	public void close() {
		mDbHelper.close();
	}

	public Cursor getAllInputType() {
		try {
			String sql = "SELECT typeId,typeName FROM typeDetail where issync='FALSE'";
			Log.e(TAG, sql);
			Cursor mCur = mDb.rawQuery(sql, null);
			if (mCur != null) {
				mCur.moveToNext();
			}
			return mCur;
		} catch (SQLException mSQLException) {
			Log.e(TAG, "getTestData >>" + mSQLException.toString());
			throw mSQLException;
		}
	}
	public Cursor getAllInputs() {
		try {
			String sql = "SELECT typeId,typeName,actualTypeId FROM typeDetail";
			Log.e(TAG, sql);
			Cursor mCur = mDb.rawQuery(sql, null);
			if (mCur != null) {
				mCur.moveToNext();
			}
			return mCur;
		} catch (SQLException mSQLException) {
			Log.e(TAG, "getTestData >>" + mSQLException.toString());
			throw mSQLException;
		}
	}
	
	public Cursor getAllSaveRecords() {
		try {
			String sql = "SELECT userid,userinputtypeid,admininputtypeid,mentalacuity,emoacuity,selfacuity,test1,test2,test3,sum,createdon FROM userDetail where issync='FALSE'";
			Log.e(TAG, sql);
			Cursor mCur = mDb.rawQuery(sql, null);
			if (mCur != null) {
				mCur.moveToNext();
			}
			return mCur;
		} catch (SQLException mSQLException) {
			Log.e(TAG, "getTestData >>" + mSQLException.toString());
			throw mSQLException;
		}
	}
	
	public Cursor getAllUserName() {
		try {
			String sql = "SELECT userId,userName,userPassword FROM userMaster where issync='FALSE'";
			Log.e(TAG, sql);
			Cursor mCur = mDb.rawQuery(sql, null);
			if (mCur != null) {
				mCur.moveToNext();
			}
			return mCur;
		} catch (SQLException mSQLException) {
			Log.e(TAG, "getTestData >>" + mSQLException.toString());
			throw mSQLException;
		}
	}
	public Cursor getAllUsers() {
		try {
			String sql = "SELECT userId,userName,userPassword,userActualId FROM userMaster";
			Log.e(TAG, sql);
			Cursor mCur = mDb.rawQuery(sql, null);
			if (mCur != null) {
				mCur.moveToNext();
			}
			return mCur;
		} catch (SQLException mSQLException) {
			Log.e(TAG, "getTestData >>" + mSQLException.toString());
			throw mSQLException;
		}
	}
	public Cursor getAllResultsOfUser(int userId) {
		try {
			String sql = "SELECT emoacuity,mentalacuity,selfacuity,test1,test2,test3 FROM userDetail where userid="+userId;
			Log.e(TAG, sql);
			Cursor mCur = mDb.rawQuery(sql, null);
			if (mCur != null) {
				mCur.moveToNext();
			}
			return mCur;
		} catch (SQLException mSQLException) {
			Log.e(TAG, "getTestData >>" + mSQLException.toString());
			throw mSQLException;
		}
	}
	public Cursor getAllUsersGraphRecords() {
		try {
			String sql = "SELECT a.userName,a.userActualId FROM userMaster a,userDetail b where a.userActualId=b.userid";
			Log.e(TAG, sql);
			Cursor mCur = mDb.rawQuery(sql, null);
			if (mCur != null) {
				mCur.moveToNext();
			}
			return mCur;
		} catch (SQLException mSQLException) {
			Log.e(TAG, "getTestData >>" + mSQLException.toString());
			throw mSQLException;
		}
	}
	public Cursor checkUserExist(String userName) {
		try {
			String sql = "SELECT userName FROM userMaster where upper(userName)=upper('"+userName+"')";			
			Log.e(TAG, sql);
			Cursor mCur = mDb.rawQuery(sql, null);
			if (mCur != null) {
				mCur.moveToNext();
			}
			return mCur;
		} catch (SQLException mSQLException) {
			Log.e(TAG, "getTestData >>" + mSQLException.toString());
			throw mSQLException;
		}
	}
	public Cursor checkTypeExist(String typeName) {
		try {
			String sql = "SELECT typeName FROM typeDetail where upper(typeName)=upper('"+typeName+"')";			
			Log.e(TAG, sql);
			Cursor mCur = mDb.rawQuery(sql, null);
			if (mCur != null) {
				mCur.moveToNext();
			}
			return mCur;
		} catch (SQLException mSQLException) {
			Log.e(TAG, "getTestData >>" + mSQLException.toString());
			throw mSQLException;
		}
	}
	public Cursor checkUserAvaibility(String userName,String password) {
		try {
			String sql = "SELECT userName,userId FROM userMaster where upper(userName)=upper('"+userName+"') and upper(userPassword)=upper('"+password+"')";			
			Log.e(TAG, sql);
			Cursor mCur = mDb.rawQuery(sql, null);
			if (mCur != null) {
				mCur.moveToNext();
			}
			return mCur;
		} catch (SQLException mSQLException) {
			Log.e(TAG, "getTestData >>" + mSQLException.toString());
			throw mSQLException;
		}
	}
	public Cursor getAllEmotionalAcuityQuest(){
		try {
			String sql = "SELECT questId,questA,questB,questC FROM questDetail where acuityId="+Constants.EMOTIONAL_ACUITY;
			Log.e(TAG, sql);
			Cursor mCur = mDb.rawQuery(sql, null);
			if (mCur != null) {
				mCur.moveToNext();
			}
			return mCur;
		} catch (SQLException mSQLException) {
			Log.e(TAG, "getTestData >>" + mSQLException.toString());
			throw mSQLException;
		}
	}
	public Cursor getQuestion(int a){
		try {
			String sql = "SELECT questions FROM myQuestions where id="+a;
			Log.e(TAG, sql);
			Cursor mCur = mDb.rawQuery(sql, null);
			if (mCur != null) {
				mCur.moveToNext();
			}
			return mCur;
		} catch (SQLException mSQLException) {
			Log.e(TAG, "getTestData >>" + mSQLException.toString());
			throw mSQLException;
		}
	}
	public Cursor getSelfAcuityQuest(int a){
		try {
			String sql = "SELECT scenario FROM myScenario where id="+a;
			Log.e(TAG, sql);
			Cursor mCur = mDb.rawQuery(sql, null);
			if (mCur != null) {
				mCur.moveToNext();
			}
			return mCur;
		} catch (SQLException mSQLException) {
			Log.e(TAG, "getTestData >>" + mSQLException.toString());
			throw mSQLException;
		}
	}
	public Cursor getTotalSaveEntry(){
		try {
			String sql = "SELECT count(*) as totalEntry from userDetail";
			Log.e(TAG, sql);
			Cursor mCur = mDb.rawQuery(sql, null);
			if (mCur != null) {
				mCur.moveToNext();
			}
			return mCur;
		} catch (SQLException mSQLException) {
			Log.e(TAG, "getTestData >>" + mSQLException.toString());
			throw mSQLException;
		}
	}
	public int saveData(int userId,int userTypeId,int adminTypeId,int mentalAcuity,int emoAcuity,int selfAcuity,int Test1,int Test2,int Test3,int sum,String date){
		int flag=0;
		int totalRecords=0;
		try{
		Cursor totRec = getTotalSaveEntry();
		totalRecords=Integer.parseInt(totRec.getString(0));
		
		String sql="insert into userDetail(id,userId,userinputtypeid,admininputtypeid,mentalAcuity,emoAcuity,selfAcuity,Test1,Test2,Test3,sum,createdon,issync)" 
			+ "values("+(totalRecords+1)+","+userId+","+userTypeId+","+adminTypeId+","+mentalAcuity+","+emoAcuity+","+selfAcuity+","+Test1+","+Test2+","+Test3+","
			+sum+",'"+date+"','FALSE')";
		Log.e(TAG, sql);
		mDb.execSQL(sql); 
					
        flag=1;  
        totRec.close();
        return flag;
		}catch(Exception e){
			e.printStackTrace();
			flag=0;
			return flag;
			
		}
	}
	
	public int registerData(String userName,String userPassword){
		int flag=0;
		int checkFlag=0;
		int totalRecords=0;
		try{
			Cursor checkUser = checkUserExist(userName);
			if (checkUser.getCount() >= 1) {
				do {
					if (checkUser.getString(0).equalsIgnoreCase(userName)) {
						checkFlag = 1;
						flag=-1;
					}
				} while (checkUser.moveToNext());
			}
			checkUser.close();
			
			if(checkFlag==0){
					Cursor totRec = getTotalUserRegister();
					totalRecords=Integer.parseInt(totRec.getString(0));
					String sql="insert into userMaster(userId,userName,userPassword,userActualId,issync)" 
						+ "values("+(totalRecords+1)+",'"+userName+"','"+userPassword+"',"+(totalRecords+1)+",'FALSE')";
					Log.e(TAG, sql);
					mDb.execSQL(sql); 
					Constants.SELECTED_USERID=totalRecords+1;			
			        flag=1;  
			        totRec.close();
			}
        return flag;
		}catch(Exception e){
			e.printStackTrace();
			flag=0;
			return flag;
			
		}
	}
	public int registerDataFromServer(String userName,String userPassword){
		int flag=0;
		int totalRecords=0;
		try{
		Cursor totRec = getTotalUserRegister();
		totalRecords=Integer.parseInt(totRec.getString(0));
		String sql="insert into userMaster(userId,userName,userPassword,userActualId,issync)" 
			+ "values("+(totalRecords+1)+",'"+userName+"','"+userPassword+"',"+(totalRecords+1)+",'TRUE')";
		Log.e(TAG, sql);
		mDb.execSQL(sql); 
		Constants.SELECTED_USERID=totalRecords+1;			
        flag=1;  
        totRec.close();
        return flag;
		}catch(Exception e){
			e.printStackTrace();
			flag=0;
			return flag;
			
		}
	}	
	public int saveType(String typeName,String creater,int actualTypeId){
		int flag=0;
		int totalRecords=0;
		int checkFlag=0;
		try{
			
			Cursor checkType = checkTypeExist(typeName);
			if (checkType.getCount() >= 1) {
				do {
					if (checkType.getString(0).equalsIgnoreCase(typeName)) {
						checkFlag = 1;
						flag=-1;
					}
				} while (checkType.moveToNext());
			}
			checkType.close();
			
			if(checkFlag==0){
						Cursor totRec = getTotalTypes();
						totalRecords=Integer.parseInt(totRec.getString(0));
						String sql="insert into typeDetail(typeId,typeName,typeCreater,actualTypeId,issync)" 
							+ "values("+(totalRecords+1)+",'"+typeName+"','"+creater+"',"+actualTypeId+",'FALSE')";
						Log.e(TAG, sql);
						mDb.execSQL(sql); 
						flag=1;  
			}
        return flag;
		}catch(Exception e){
			e.printStackTrace();
			flag=0;
			return flag;
			
		}
	}
	public int saveTypeFromServer(String typeName,String creater,int actualTypeId){
		int flag=0;
		int totalRecords=0;
		try{
			Cursor totRec = getTotalTypes();
			totalRecords=Integer.parseInt(totRec.getString(0));
		String sql="insert into typeDetail(typeId,typeName,typeCreater,actualTypeId,issync)" 
			+ "values("+(totalRecords+1)+",'"+typeName+"','"+creater+"',"+actualTypeId+",'TRUE')";
		Log.e(TAG, sql);
		mDb.execSQL(sql); 
		flag=1;  
        
        return flag;
		}catch(Exception e){
			e.printStackTrace();
			flag=0;
			return flag;
			
		}
	}
	public int saveTypeId(String typeName,int actualTypeId){
		int flag=0;
		try{
		String sql="update typeDetail set issync='TRUE',actualTypeId="+actualTypeId+" where typeName='"+typeName+"'";
		Log.e(TAG, sql);
		mDb.execSQL(sql); 
		flag=1;  
        return flag;
		}catch(Exception e){
			e.printStackTrace();
			flag=0;
			return flag;
			
		}
	}
	public int updateSyncRecordStatus(int userid,String createdOn){
		int flag=0;
		try{
		String sql="update userDetail set issync='TRUE' where userid="+userid+" and createdon='"+createdOn+"'";
		Log.e(TAG, sql);
		mDb.execSQL(sql); 
		flag=1;  
        return flag;
		}catch(Exception e){
			e.printStackTrace();
			flag=0;
			return flag;
			
		}
	}
	public int saveUserId(String userName,int actualUserId){
		int flag=0;
		try{
		String sql="update userMaster set issync='TRUE',userActualId="+actualUserId+" where userName='"+userName+"'";
		Log.e(TAG, sql);
		mDb.execSQL(sql); 
		flag=1;  
        return flag;
		}catch(Exception e){
			e.printStackTrace();
			flag=0;
			return flag;
			
		}
	}
	public Cursor getUserAcutalId(int userId){
		try {
			String sql = "SELECT userActualId from userMaster where userId="+userId;
			Log.e(TAG, sql);
			Cursor mCur = mDb.rawQuery(sql, null);
			if (mCur != null) {
				mCur.moveToNext();
			}
			return mCur;
		} catch (SQLException mSQLException) {
			Log.e(TAG, "getTestData >>" + mSQLException.toString());
			throw mSQLException;
		}
	}
	public Cursor getUserInputTypeAcutalId(int inputId){
		try {
			String sql = "SELECT actualTypeId from typeDetail where typeId="+inputId+" and typeCreater='U'";
			Log.e(TAG, sql);
			Cursor mCur = mDb.rawQuery(sql, null);
			if (mCur != null) {
				mCur.moveToNext();
			}
			return mCur;
		} catch (SQLException mSQLException) {
			Log.e(TAG, "getTestData >>" + mSQLException.toString());
			throw mSQLException;
		}
	}
	public Cursor getAdminInputTypeAcutalId(int inputId){
		try {
			String sql = "SELECT actualTypeId from typeDetail where typeId="+inputId+" and typeCreater='A'";
			Log.e(TAG, sql);
			Cursor mCur = mDb.rawQuery(sql, null);
			if (mCur != null) {
				mCur.moveToNext();
			}
			return mCur;
		} catch (SQLException mSQLException) {
			Log.e(TAG, "getTestData >>" + mSQLException.toString());
			throw mSQLException;
		}
	}
	public Cursor getAcuityId(String acuity){
		try {
			String sql = "SELECT acuityId from acuityMaster where acuityName='"+acuity+"'";
			Log.e(TAG, sql);
			Cursor mCur = mDb.rawQuery(sql, null);
			if (mCur != null) {
				mCur.moveToNext();
			}
			return mCur;
		} catch (SQLException mSQLException) {
			Log.e(TAG, "getTestData >>" + mSQLException.toString());
			throw mSQLException;
		}
	}
	public Cursor getInputTypeCreater(String inputType){
		try {
			String sql = "SELECT typeId,typeCreater from typeDetail where typeName='"+inputType+"'";
			Log.e(TAG, sql);
			Cursor mCur = mDb.rawQuery(sql, null);
			if (mCur != null) {
				mCur.moveToNext();
			}
			return mCur;
		} catch (SQLException mSQLException) {
			Log.e(TAG, "getTestData >>" + mSQLException.toString());
			throw mSQLException;
		}
	}
	
	public Cursor getTotalUserRegister(){
		try {
			String sql = "SELECT count(*) as totalUsers from userMaster";
			Log.e(TAG, sql);
			Cursor mCur = mDb.rawQuery(sql, null);
			if (mCur != null) {
				mCur.moveToNext();
			}
			return mCur;
		} catch (SQLException mSQLException) {
			Log.e(TAG, "getTestData >>" + mSQLException.toString());
			throw mSQLException;
		}
	}
	public Cursor getTotalTypes(){
		try {
			String sql = "SELECT count(*) as totalTypes from typeDetail";
			Log.e(TAG, sql);
			Cursor mCur = mDb.rawQuery(sql, null);
			if (mCur != null) {
				mCur.moveToNext();
			}
			return mCur;
		} catch (SQLException mSQLException) {
			Log.e(TAG, "getTestData >>" + mSQLException.toString());
			throw mSQLException;
		}
	}
	
}
