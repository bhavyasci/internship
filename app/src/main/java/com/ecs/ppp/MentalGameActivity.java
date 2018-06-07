package com.ecs.ppp;


import android.app.Activity;

import android.app.AlertDialog;

import android.content.DialogInterface;

import android.content.Intent;

import android.graphics.Typeface;

import android.media.MediaPlayer;

import android.os.Bundle;

import android.os.Handler;

import android.util.Log;

import android.view.KeyEvent;

import android.view.Menu;

import android.view.View;

import android.view.View.OnClickListener;

import android.widget.Button;

import android.widget.ImageButton;

import android.widget.TextView;

import android.widget.Toast;


import com.ecs.ppp.utils.Constants;

import com.ecs.ppp.utils.Utility;


public class MentalGameActivity extends Activity implements OnClickListener {

	Button btnStart;
	ImageButton imgBtnGreen, imgBtnSound;
	ImageButton imgBtnEmoBack, imgBtnEmoNext;
	ImageButton imgBtnRed;
	ImageButton imgBtnBlue;
	ImageButton imgBtnPink;
	String randomValue = "";
	String actualValue = "";
	private TextView txtLevelVal, txtLevelAch, textView2, textView1;
	int levelNo = 0;
	private Handler mHandler = new Handler();
	MediaPlayer mp;

	// method for initializing all methods
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.mentaltest);
		mp = MediaPlayer.create(this, R.raw.beep);
		txtLevelVal = (TextView) findViewById(R.id.txtLevelVal);
		txtLevelAch = (TextView) findViewById(R.id.txtLevelAch);
		textView1 = (TextView) findViewById(R.id.textView1);
		textView2 = (TextView) findViewById(R.id.textView2);

		imgBtnGreen = (ImageButton) findViewById(R.id.imgBtnGreen);
		imgBtnRed = (ImageButton) findViewById(R.id.imgBtnRed);
		imgBtnBlue = (ImageButton) findViewById(R.id.imgBtnBlue);
	
	imgBtnPink = (ImageButton) findViewById(R.id.imgBtnPink);
		
imgBtnSound = (ImageButton) findViewById(R.id.imgBtnSound);
		
imgBtnEmoNext = (ImageButton) findViewById(R.id.imgBtnNextEmo);
		
imgBtnEmoBack = (ImageButton) findViewById(R.id.imgBtnEmoBack);
		
btnStart = (Button) findViewById(R.id.btnStart);
		
btnStart.setOnClickListener(this);

	
	Typeface externalFont = Typeface.createFromAsset(getAssets(),
				"BKANT.TTF");
		// textView1.setTypeface(externalFont);
		// 
textView2.setTypeface(externalFont);
		
txtLevelAch.setTypeface(externalFont);
		
txtLevelVal.setTypeface(externalFont);

		
imgBtnGreen.setOnClickListener(this);
		
imgBtnRed.setOnClickListener(this);
		
imgBtnBlue.setOnClickListener(this);
		
imgBtnPink.setOnClickListener(this);
		
imgBtnSound.setOnClickListener(this);
		
imgBtnEmoNext.setOnClickListener(this);
	
	imgBtnEmoBack.setOnClickListener(this);
	
	disableButtons();

	}

	
@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.home, menu);

		return true;
	}

	// Code for creating random blinking images as per the level with using
	// thread
	public void callOnStart() {

		
try {
			disableButtons();
		
	loadOriginal();
		
	levelNo = Integer.parseInt(txtLevelVal.getText().toString());
	
		int lastRandomno = 0;
		
	int totalCall = 0;
		
	totalCall = levelNo;
		
	int cnt = 0;
			
int seconds = 150;

	
		// This loop is used for logic of blinking images randomly according
			// to the level

			while (totalCall != 0)
 {
				int btnNumber = Utility.getRandomNumber();

				btnNumber += 1;
			
	cnt = cnt + 2;
			
	randomValue = randomValue + btnNumber + ",";
	
			Log.d("Call number:-> " + totalCall, "===btnNumber:-> "
						+ btnNumber);



				switch (btnNumber)
 {
				case 1:
					mHandler.postDelayed(new Runnable() {
						public void run() {
							if (Constants.SOUND_FLAG == 1)
								mp.start();
							imgBtnGreen.setImageResource(R.drawable.greenact);
						}
					}, seconds * cnt);
					break;
		
				case 2:
					mHandler.postDelayed(new Runnable() {
						public void run() {
							if (Constants.SOUND_FLAG == 1)
								mp.start();
							imgBtnRed.setImageResource(R.drawable.redact);
						}
					}, seconds * cnt);
					break;
			
				case 3:
					mHandler.postDelayed(new Runnable() {
						public void run() {
							if (Constants.SOUND_FLAG == 1)
								mp.start();
							imgBtnBlue.setImageResource(R.drawable.blueact);
						}
					}, seconds * cnt);
					break;
				
				case 4:
					mHandler.postDelayed(new Runnable() {
						public void run() {
							if (Constants.SOUND_FLAG == 1)
								mp.start();
							imgBtnPink.setImageResource(R.drawable.pinkact);
						}
					}, seconds * cnt);
					break;
				}

				mHandler.postDelayed(new Runnable() {
					public void run() {
						loadOriginal();
					}
				}, seconds * (cnt + 1));

				lastRandomno = btnNumber;
				totalCall--;
				if (totalCall == 0) {
					enableButtons();
				}

			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void onClick(View v) {

		// Code for Start button + logic for levels
		if (v == imgBtnSound) {

			if (Constants.SOUND_FLAG == 1) {
				Constants.SOUND_FLAG = 0;
				imgBtnSound.setImageResource(R.drawable.sound);
			} else {
				Constants.SOUND_FLAG = 1;
				imgBtnSound.setImageResource(R.drawable.soundact);
			}
		}
		if (v == btnStart) {
			callOnStart();
		}
		if (v == imgBtnEmoBack) {
			startActivity(new Intent(MentalGameActivity.this,
					EmotionalAcuityActivity.class));
		}
		if (v == imgBtnEmoNext) {
			startActivity(new Intent(MentalGameActivity.this,
					SelfAcuityActivity.class));
		}

		// code for images button + logic of users answers
		if (v == imgBtnGreen || v == imgBtnRed || v == imgBtnBlue
				|| v == imgBtnPink) {
			int len = Utility.getLength(Utility.splitData(actualValue));

			if (len == levelNo) {
				onCompleteProcess();
			} else {
				if (v == imgBtnGreen) {
					actualValue = actualValue + "1,";
				} else if (v == imgBtnRed) {
					actualValue = actualValue + "2,";
				} else if (v == imgBtnBlue) {
					actualValue = actualValue + "3,";
				} else if (v == imgBtnPink) {
					actualValue = actualValue + "4,";
				}

				if (len + 1 == levelNo) {
					onCompleteProcess();
				}
			}
		}

	}

	public void onCompleteProcess() {
		int flag = -1;
		String questData[] = Utility.splitData(randomValue);
		String ansData[] = Utility.splitData(actualValue);

		for (int i = 0; i < questData.length; i++) {
			Log.d("QuestData:- " + questData[i], "==AnsData:- " + ansData[i]);
			if (questData[i].equalsIgnoreCase(ansData[i])) {
				flag = 1;
			} else {
				flag = 0;
				break;
			}
		}

		if (flag == 0) {

			txtLevelVal.setText(1 + "");
			actualValue = "";
			randomValue = "";
			loadOriginal();
			Constants.MENTAL_ACUITY_STATUS = 1;
			Toast.makeText(this, "Mental Acuity Completed", Toast.LENGTH_SHORT)
					.show();

			AlertDialog.Builder myAlertDialog = new AlertDialog.Builder(
					MentalGameActivity.this);
			myAlertDialog.setTitle("Mental Acuity Test");
			myAlertDialog.setMessage("Level Failed: " + levelNo);
			myAlertDialog.setPositiveButton("OK",
					new DialogInterface.OnClickListener() {

						public void onClick(DialogInterface arg0, int arg1) {
							// do something when the OK button is clicked
							startActivity(new Intent(MentalGameActivity.this,
									SelfAcuityActivity.class));
						}
					});

			myAlertDialog.show();

			// Toast.makeText(this,"Level no: "+levelNo+" failed.  ",
			// Toast.LENGTH_SHORT).show();

		} else {
			int result = levelNo + 1;
			txtLevelVal.setText(result + "");
			txtLevelAch.setText(levelNo + "");
			Constants.MENTAL_ACUITY_RESULT = levelNo;
			actualValue = "";
			randomValue = "";

			// Code for showing alert boxes starts
			AlertDialog.Builder myAlertDialog = new AlertDialog.Builder(
					MentalGameActivity.this);
			myAlertDialog.setTitle("Mental Acuity Test");
			myAlertDialog
					.setMessage("Successfully Completed Level: " + levelNo);
			myAlertDialog.setPositiveButton("OK",
					new DialogInterface.OnClickListener() {

						public void onClick(DialogInterface arg0, int arg1) {
							// do something when the OK button is clicked
							callOnStart();
						}
					});
			myAlertDialog.setNegativeButton("Cancel",
					new DialogInterface.OnClickListener() {

						public void onClick(DialogInterface arg0, int arg1) {
							// do something when the Cancel button is clicked
							loadOriginal();
							disableButtons();
							actualValue = "";
							randomValue = "";
						}
					});
			myAlertDialog.show();
			// Code Ends Here for alert
		}
	}

	// Code for loading original images of buttons
	public void loadOriginal() {
		imgBtnGreen.setImageResource(R.drawable.green);
		imgBtnRed.setImageResource(R.drawable.red);
		imgBtnBlue.setImageResource(R.drawable.blue);
		imgBtnPink.setImageResource(R.drawable.pink);
	}

	// code for disabling the buttons
	public void disableButtons() {
		imgBtnGreen.setEnabled(false);
		imgBtnRed.setEnabled(false);
		imgBtnBlue.setEnabled(false);
		imgBtnPink.setEnabled(false);
	}

	// code for enabling the buttons
	public void enableButtons() {
		imgBtnGreen.setEnabled(true);
		imgBtnRed.setEnabled(true);
		imgBtnBlue.setEnabled(true);
		imgBtnPink.setEnabled(true);
	}

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
}
