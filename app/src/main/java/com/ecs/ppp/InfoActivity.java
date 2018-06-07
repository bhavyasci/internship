package com.ecs.ppp;

import com.ecs.ppp.utils.Constants;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageButton;
import android.widget.TextView;

//import com.flurry.android.FlurryAgent;

public class InfoActivity extends Activity implements OnClickListener{
	
	ImageButton imgBtnClose;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.info);
	
		imgBtnClose=(ImageButton)findViewById(R.id.imgBtnClose);
		imgBtnClose.setOnClickListener(this);
		
		Typeface externalFont = Typeface.createFromAsset(getAssets(),
		"MTCORSVA.TTF");
		
	}
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
	
		if(v==imgBtnClose){
			if(Constants.ACTIVITY_CALL==1)
				startActivity(new Intent(InfoActivity.this,HomeActivity.class));
			else
				startActivity(new Intent(InfoActivity.this,TestMenuActivity.class));
			 android.os.Process.killProcess(android.os.Process.myPid());
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
