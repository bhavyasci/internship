package com.ecs.ppp;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Random;

import com.ecs.ppp.db.QuestAdapter;
import com.ecs.ppp.utils.Constants;
import com.ecs.ppp.utils.Utility;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

public class EmotionalAcuityActivity extends Activity implements OnClickListener{

	ImageButton imgBtnNext,imgBtnBack;
	Cursor myCursor = null;
	QuestAdapter mDbHelper;
	List<String> questId = new ArrayList<String>();
	List<String> questA = new ArrayList<String>();
	List<String> questB = new ArrayList<String>();
	List<String> questC = new ArrayList<String>();
	int currentQuestno=0;
	RadioButton rdBtn1,rdBtn2,rdBtn3;
	RadioGroup rdAllBtn;
	TextView txtQuestNo;
	int currentAns=-1;
	int backCount=1;
	HashSet<Integer> hset = new HashSet<Integer>();
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.emotionaltest);
		Constants.EMOTIONAL_ACUITY_RESULT=0;
		Typeface externalFont = Typeface.createFromAsset(getAssets(),
				"BKANT.TTF");
		
		imgBtnNext=(ImageButton)findViewById(R.id.imgBtnNext);
		imgBtnBack=(ImageButton)findViewById(R.id.imgBtnBackEmo);
		rdAllBtn=(RadioGroup)findViewById(R.id.rdAllBtn);
		rdBtn1=(RadioButton)findViewById(R.id.rdBtn1);
		rdBtn2=(RadioButton)findViewById(R.id.rdBtn2);
		rdBtn3=(RadioButton)findViewById(R.id.rdBtn3);
		txtQuestNo=(TextView)findViewById(R.id.txtQuestionNo);
		
		txtQuestNo.setTypeface(externalFont);
		rdBtn1.setTypeface(externalFont);
		rdBtn2.setTypeface(externalFont);
		rdBtn3.setTypeface(externalFont);
		
		mDbHelper = new QuestAdapter(this);
		mDbHelper.createDatabase();
		mDbHelper.open();
		Cursor emoAllQuest = mDbHelper.getAllEmotionalAcuityQuest();
		addData(emoAllQuest);
		emoAllQuest.close();
		imgBtnNext.setOnClickListener(this);
		imgBtnBack.setOnClickListener(this);
		
		loadQuestData(currentQuestno);
		
		rdBtn1.setOnClickListener(this);
		rdBtn2.setOnClickListener(this);
		rdBtn3.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		
		if(v==imgBtnNext){
			backCount=1;
			if(currentAns==-1){
			Utility.ShowMessageBox(EmotionalAcuityActivity.this, "Select your answer first");	
			}
			else{
			Constants.EMOTIONAL_ACUITY_RESULT=Constants.EMOTIONAL_ACUITY_RESULT+currentAns;
			loadQuestData(currentQuestno);
			rdAllBtn.clearCheck();
			currentAns=-1;
			}
		}
		if(v==imgBtnBack){
			backCount+=1;
			Log.d("Back No: ",""+backCount);
			Log.d("Current Question No: ",""+currentQuestno);
			loadLastData(currentQuestno-backCount);
		}
		if(v==rdBtn1){
			currentAns=Constants.OPTION_A_VALUE;
		}
		if(v==rdBtn2){
			currentAns=Constants.OPTION_B_VALUE;
		}
		if(v==rdBtn3){
			currentAns=Constants.OPTION_C_VALUE;
		}
		
}
	public void addData(Cursor cursor){
		
		do {
			questId.add(cursor.getString(0));
			questA.add(cursor.getString(1));
			questB.add(cursor.getString(2));
			questC.add(cursor.getString(3));
		}
		while (cursor.moveToNext());
		
	}
	public void loadQuestData(int questNo){
		
		if(questNo>=5){
		Toast.makeText(this, "Emotional Acuity Completed", Toast.LENGTH_SHORT).show();	
		Constants.EMOTIONAL_ACUITY_STATUS=1;
		startActivity(new Intent(EmotionalAcuityActivity.this,MentalGameActivity.class));
		}
		else{
			Random r = new Random();
			int i = r.nextInt(52);
			while(i == 0)
			{i = r.nextInt(52);}
			while(hset.contains(i)){
				i = r.nextInt(52);
				while(i == 0)
				{i = r.nextInt(52);}
			}
				hset.add(i);
				System.out.println("i = "+i);
			myCursor = mDbHelper.getQuestion(i);
		txtQuestNo.setText("Question: "+questId.get(questNo).toString()+" "+myCursor.getString(0).toString());
		rdBtn1.setText("A. Agree   ");
		rdBtn2.setText("B. Neutral  ");
		rdBtn3.setText("C. Disagree  ");
		currentQuestno++;
		}
	}
	public void loadLastData(int questNo){
		
		if(questNo==-1){
		startActivity(new Intent(EmotionalAcuityActivity.this,TestMenuActivity.class));
		}
		else{
			Random r = new Random();
			int i = r.nextInt(52);
			while(i == 0)
			{i = r.nextInt(52);}
			while(hset.contains(i)){
				i = r.nextInt(52);
				while(i == 0)
				{i = r.nextInt(52);}
			}
				hset.add(i);
			System.out.println("i = " + i);
			myCursor = mDbHelper.getQuestion(i);
		txtQuestNo.setText("Question: "+questId.get(questNo).toString()+" "+myCursor.getString(0).toString());
			rdBtn1.setText("A. Agree   ");
			rdBtn2.setText("B. Neutral  ");
			rdBtn3.setText("C. Disagree  ");
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
