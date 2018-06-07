package com.ecs.ppp;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;
import android.widget.TextView;

import com.ecs.ppp.db.QuestAdapter;
import com.ecs.ppp.utils.Constants;
import com.ecs.ppp.utils.Utility;

//import com.flurry.android.FlurryAgent;

public class CalculateResultActivity extends Activity implements OnClickListener{
	
	
	TextView txtEmotionalResult,txtMentalResult,txtSelfResult,txtCustom1Result,txtCustom2Result,txtCustom3Result,txtFinalScore;
	TextView txtCustomTitle1,txtCustomTitle2,txtCustomTitle3;
	ImageButton imgBtnSave;
	QuestAdapter mDbHelper;
	TextView t;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.calculate);


		t=(TextView)findViewById(R.id.namedisplay);



		txtEmotionalResult=(TextView)findViewById(R.id.txtEmotionalResult);
		txtMentalResult=(TextView)findViewById(R.id.txtMentalResult);
		txtSelfResult=(TextView)findViewById(R.id.txtSelfResult);
		txtCustom1Result=(TextView)findViewById(R.id.txtCustom1Result);
		txtCustom2Result=(TextView)findViewById(R.id.txtCustom2Result);
		txtCustom3Result=(TextView)findViewById(R.id.txtCustom3Result);
		txtFinalScore=(TextView)findViewById(R.id.txtFinalScore);
		imgBtnSave=(ImageButton)findViewById(R.id.imgBtnSave);
		
		txtCustomTitle1=(TextView)findViewById(R.id.txtCustom1Title);
		txtCustomTitle2=(TextView)findViewById(R.id.txtCustom2Title);
		txtCustomTitle3=(TextView)findViewById(R.id.txtCustom3Title);

		txtCustom1Result.setVisibility(View.INVISIBLE);
		txtCustom2Result.setVisibility(View.INVISIBLE);
		txtCustom3Result.setVisibility(View.INVISIBLE);

		txtCustomTitle1.setVisibility(View.INVISIBLE);
		txtCustomTitle2.setVisibility(View.INVISIBLE);
		txtCustomTitle3.setVisibility(View.INVISIBLE);
		//txtFinalScore.setVisibility(View.INVISIBLE);

		txtEmotionalResult.setText(Constants.EMOTIONAL_ACUITY_RESULT+"");
		txtMentalResult.setText(Constants.MENTAL_ACUITY_RESULT+"");
		txtSelfResult.setText(Constants.SELF_ACUITY_RESULT+"");
		txtCustom1Result.setText(Constants.CUSTOM_ACUITY_1_RESULT+"");
		txtCustom2Result.setText(Constants.CUSTOM_ACUITY_2_RESULT+"");
		txtCustom3Result.setText(Constants.CUSTOM_ACUITY_3_RESULT + "");
		
		double totalResult=5*Constants.EMOTIONAL_ACUITY_RESULT+3*Constants.MENTAL_ACUITY_RESULT+2*Constants.SELF_ACUITY_RESULT+
		Constants.CUSTOM_ACUITY_1_RESULT+Constants.CUSTOM_ACUITY_2_RESULT+Constants.CUSTOM_ACUITY_3_RESULT;

		txtFinalScore.setText((totalResult/10)+"");
		
		txtCustomTitle1.setText("  "+Constants.CUSTOM_TITLE_1);
		txtCustomTitle2.setText("  "+Constants.CUSTOM_TITLE_2);
		txtCustomTitle3.setText("  "+Constants.CUSTOM_TITLE_3);

		t.setText("Username is:"+HomeActivity.spinUserId.getSelectedItem().toString());
		
		imgBtnSave.setOnClickListener(this);
		
	}
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub

			startActivity(new Intent(CalculateResultActivity.this,HomeActivity.class));
			finish();
		
	}
	public void saveResult(){
		try{
		mDbHelper = new QuestAdapter(this);
		mDbHelper.createDatabase();
		mDbHelper.open();
		int userInput=0;
		int adminInput=0;
		if(Constants.SELECTED_INPUTTYPE_CREATOR.equals("A")){
						adminInput=Constants.SELECTED_INPUTTYPE;
		}else{
						userInput=Constants.SELECTED_INPUTTYPE;
		}
		String timeStamp = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(Calendar.getInstance().getTime());
		int result=mDbHelper.saveData(Constants.SELECTED_USERID, userInput,adminInput, Integer.parseInt(txtMentalResult.getText().toString()), Integer.parseInt(txtEmotionalResult.getText().toString()), Integer.parseInt(txtSelfResult.getText().toString()), 
		Integer.parseInt(txtCustom1Result.getText().toString()), Integer.parseInt(txtCustom2Result.getText().toString()), Integer.parseInt(txtCustom3Result.getText().toString()), Integer.parseInt(txtFinalScore.getText().toString()),timeStamp);	
		mDbHelper.close();
		
					if(result==1){
					Utility.ShowMessageBox(this, "Saved Successfully...");
					Constants.EMOTIONAL_ACUITY_STATUS=0;
					Constants.SELF_ACUITY_STATUS=0;
					Constants.MENTAL_ACUITY_STATUS=0;
					Constants.CUSTOM1_ACUITY_STATUS=0;
					Constants.CUSTOM2_ACUITY_STATUS=0;
					Constants.CUSTOM3_ACUITY_STATUS=0;
					Constants.EMOTIONAL_ACUITY_RESULT=0;
					Constants.SELF_ACUITY_RESULT=0;
					Constants.MENTAL_ACUITY_RESULT=0;
					Constants.CUSTOM_ACUITY_1_RESULT=0;
					Constants.CUSTOM_ACUITY_2_RESULT=0;
					Constants.CUSTOM_ACUITY_3_RESULT=0;
					startActivity(new Intent(CalculateResultActivity.this,TestMenuActivity.class));
					
				}
				else{	
					Utility.ShowMessageBox(this, "Saving Failed...");
				}
		
		}catch(Exception e)
		{
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
