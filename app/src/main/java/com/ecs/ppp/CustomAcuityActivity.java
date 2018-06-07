package com.ecs.ppp;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.ecs.ppp.utils.Constants;

public class CustomAcuityActivity extends Activity implements OnClickListener {

	ImageButton imgBtnEmoBack, imgBtnEmoNext;
	EditText editTest, editScore;
	long scoreLength;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.customtest);

		editTest = (EditText) findViewById(R.id.editTestName);
		editScore = (EditText) findViewById(R.id.editScore);
		editTest.setText("Test: " + Constants.CUSTOM_BUTTON_CLICK);
		editScore.setInputType(InputType.TYPE_CLASS_NUMBER);

		imgBtnEmoNext = (ImageButton) findViewById(R.id.imgBtnNextEmo);
		imgBtnEmoBack = (ImageButton) findViewById(R.id.imgBtnEmoBack);
		imgBtnEmoBack.setOnClickListener(this);
		imgBtnEmoNext.setOnClickListener(this);

	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub

		if (v == imgBtnEmoNext) {
			if (Constants.CUSTOM_BUTTON_CLICK == 1) {

				scoreLength = Long.parseLong(editScore.getText().toString());
				if (scoreLength >= 10000) {
					Toast.makeText(this,
							"Score value should be less than 10000.",
							Toast.LENGTH_SHORT).show();
				} else {
					Constants.CUSTOM_BUTTON_CLICK = 2;
					Constants.CUSTOM_ACUITY_1_RESULT = Integer
							.parseInt(editScore.getText().toString());
					Constants.CUSTOM_TITLE_1 = editTest.getText().toString();
					Constants.CUSTOM1_ACUITY_STATUS = 1;
					Toast.makeText(
							this,
							editTest.getText().toString() + " Acuity Completed",
							Toast.LENGTH_SHORT).show();
					startActivity(new Intent(CustomAcuityActivity.this,
							CustomAcuityActivity.class));
				}

			} else if (Constants.CUSTOM_BUTTON_CLICK == 2) {

				scoreLength = Long.parseLong(editScore.getText().toString());
				if (scoreLength >= 10000) {
					Toast.makeText(this,
							"Score value should be less than 10000.",
							Toast.LENGTH_SHORT).show();
				} else {
					Constants.CUSTOM_BUTTON_CLICK = 3;
					Constants.CUSTOM_ACUITY_2_RESULT = Integer
							.parseInt(editScore.getText().toString());
					Constants.CUSTOM_TITLE_2 = editTest.getText().toString();
					Constants.CUSTOM2_ACUITY_STATUS = 1;
					Toast.makeText(
							this,
							editTest.getText().toString() + " Acuity Completed",
							Toast.LENGTH_SHORT).show();
					startActivity(new Intent(CustomAcuityActivity.this,
							CustomAcuityActivity.class));
				}
			} else if (Constants.CUSTOM_BUTTON_CLICK == 3) {

				scoreLength = Long.parseLong(editScore.getText().toString());
				if (scoreLength >= 10000) {

					Toast.makeText(this,
							"Score value should be less than 10000.",
							Toast.LENGTH_SHORT).show();
				} else {
					Constants.CUSTOM_ACUITY_3_RESULT = Integer
							.parseInt(editScore.getText().toString());
					Constants.CUSTOM_TITLE_3 = editTest.getText().toString();
					Constants.CUSTOM3_ACUITY_STATUS = 1;
					Toast.makeText(
							this,
							editTest.getText().toString() + " Acuity Completed",
							Toast.LENGTH_SHORT).show();
					startActivity(new Intent(CustomAcuityActivity.this,
							CalculateResultActivity.class));
				}
			}

		}
		if (v == imgBtnEmoBack) {
			if (Constants.CUSTOM_BUTTON_CLICK == 1) {
				startActivity(new Intent(CustomAcuityActivity.this,
						SelfAcuityActivity.class));
			} else {
				Constants.CUSTOM_BUTTON_CLICK = Constants.CUSTOM_BUTTON_CLICK - 1;
				startActivity(new Intent(CustomAcuityActivity.this,
						CustomAcuityActivity.class));
			}
		}

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
