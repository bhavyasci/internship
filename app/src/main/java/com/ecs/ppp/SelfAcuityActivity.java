package com.ecs.ppp;

import java.util.ArrayList;
import java.util.List;

import com.ecs.ppp.db.QuestAdapter;
import com.ecs.ppp.utils.Constants;
import java.util.Random;
import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.TextView;
import android.widget.Toast;

public class SelfAcuityActivity extends Activity implements OnClickListener,OnSeekBarChangeListener{

	
	ImageButton imgBtnEmoBack,imgBtnEmoNext;
	QuestAdapter mDbHelper;
	List<String> selfQ= new ArrayList<String>();
	TextView txtSelfQuest,txtSelfAns;
	SeekBar seekSelf;
	Cursor selfQuest = null;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.selftest);
		
		txtSelfQuest=(TextView)findViewById(R.id.txtSelfQuest);
		txtSelfAns=(TextView)findViewById(R.id.txtUserAns);
		seekSelf=(SeekBar)findViewById(R.id.seekSelfAns);
		imgBtnEmoNext=(ImageButton)findViewById(R.id.imgBtnNextEmo);
		imgBtnEmoBack=(ImageButton)findViewById(R.id.imgBtnEmoBack);
		seekSelf.setOnSeekBarChangeListener(this);
		imgBtnEmoNext.setOnClickListener(this);
		imgBtnEmoBack.setOnClickListener(this);
		mDbHelper = new QuestAdapter(this);
		mDbHelper.createDatabase();
		mDbHelper.open();

		int tempRand;
		Random r = new Random();
		tempRand = r.nextInt(4);
		txtSelfQuest.setText(Constants.scenario[tempRand]);
		txtSelfAns.setText("0");
		/*selfQuest = mDbHelper.getSelfAcuityQuest(tempRand);

		addData(selfQuest);
		selfQuest.close();
		loadQuestData();*/
		
		
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		if(v==imgBtnEmoBack){
			startActivity(new Intent(SelfAcuityActivity.this,MentalGameActivity.class));
		}
		if(v==imgBtnEmoNext){
			Constants.SELF_ACUITY_STATUS=1;
			Constants.CUSTOM_BUTTON_CLICK=1;
			Toast.makeText(this, "Self Acuity Completed", Toast.LENGTH_SHORT).show();	
			Constants.SELF_ACUITY_RESULT=Integer.parseInt(txtSelfAns.getText().toString());
			startActivity(new Intent(SelfAcuityActivity.this,CalculateResultActivity.class));
		}
	}
	public void addData(Cursor cursor){
		do {
			selfQ.add(cursor.getString(1));
		}
		while (cursor.moveToNext());
	}
	public void loadQuestData(){
		txtSelfQuest.setText(selfQ.get(0));
		txtSelfAns.setText("0");
	}

	@Override
	public void onProgressChanged(SeekBar seekBar, int progress,
			boolean fromUser) {
		// TODO Auto-generated method stub
		String data=progress+"";
		txtSelfAns.setText(data);
	}

	@Override
	public void onStartTrackingTouch(SeekBar seekBar) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onStopTrackingTouch(SeekBar seekBar) {
		// TODO Auto-generated method stub
		
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
