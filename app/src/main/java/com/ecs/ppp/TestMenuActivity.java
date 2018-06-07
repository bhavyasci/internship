package com.ecs.ppp;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageButton;

import com.ecs.ppp.utils.Constants;
import com.ecs.ppp.utils.Utility;

public class TestMenuActivity extends Activity implements OnClickListener{

	ImageButton imgBtnInfo;
	Button imgBtnWelcomeStart;
	ImageButton imgBtnBack;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.testmenu);
		
		imgBtnInfo=(ImageButton)findViewById(R.id.imgBtnInfo);
		imgBtnBack=(ImageButton)findViewById(R.id.imgBtnBack);
		imgBtnWelcomeStart=(Button)findViewById(R.id.imgBtnWelcomeStart);
		
		imgBtnInfo.setOnClickListener(this);
		imgBtnBack.setOnClickListener(this);
		imgBtnWelcomeStart.setOnClickListener(this);
		
		Typeface externalFont = Typeface.createFromAsset(getAssets(),
		"MTCORSVA.TTF");
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		if(v==imgBtnBack){
			
			 final AlertDialog.Builder alert = new AlertDialog.Builder(this);
		     alert.setTitle("Are you sure you want to logout");
		     alert.setPositiveButton("Ok", new DialogInterface.OnClickListener() 
		     {
		      public void onClick(DialogInterface dialog, int whichButton) 
		      {
		    	  finish();
		    	  Utility.ShowMessageBox(TestMenuActivity.this,"Successfully LogOut");
		    	  startActivity(new Intent(TestMenuActivity.this,HomeActivity.class));
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
			if(v==imgBtnInfo){
			Constants.ACTIVITY_CALL=2;
				startActivity(new Intent(TestMenuActivity.this,InfoActivity.class));
				this.finish();
			}
			if(v==imgBtnWelcomeStart){
				startActivity(new Intent(TestMenuActivity.this,EmotionalAcuityActivity.class));
				this.finish();
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
