package com.ecs.ppp;

import java.util.ArrayList;
import java.util.List;

import org.achartengine.ChartFactory;
import org.achartengine.GraphicalView;
import org.achartengine.chart.PointStyle;
import org.achartengine.model.XYMultipleSeriesDataset;
import org.achartengine.model.XYSeries;
import org.achartengine.renderer.XYMultipleSeriesRenderer;
import org.achartengine.renderer.XYSeriesRenderer;

import com.ecs.ppp.db.QuestAdapter;
import com.ecs.ppp.utils.Constants;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ImageButton;
import android.widget.LinearLayout;

public class GenerateGraphActivity extends Activity implements OnClickListener{
	
	private GraphicalView mChart;
	ImageButton imgBtnClose;
	QuestAdapter mDbHelper;
	private String[] numbers = new String[] {
				"1", "2" , "3", "4", "5", "6"
			};
	List<String> userDatas= new ArrayList<String>();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.graph_show);        
        imgBtnClose=(ImageButton)findViewById(R.id.imgBtnClose);
		imgBtnClose.setOnClickListener(this);
		
        loadUserRecords();
        openChart();
        
    }
	public void loadUserRecords(){
		
		userDatas.clear();
		openDB();
		Cursor records = mDbHelper.getAllResultsOfUser(Constants.SELECTED_USERID);
		Log.d("Total User Records for graph : "+records.getCount(),"+++++++++++++");
		Log.d("UserList Lenght:",records.getCount()+"");
		if(records.getCount()>=1)
		addData(records, userDatas);
		records.close();
		closeDB();
	}
	public void openDB(){
		try{
			mDbHelper = new QuestAdapter(this);
			mDbHelper.createDatabase();
			mDbHelper.open();
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	public void closeDB(){
		try{
			mDbHelper.close();
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	public void addData(Cursor cursor,List<String> data){
		String datas="";
		do {
			datas="";
			datas=cursor.getString(0)+","+cursor.getString(1)+","+cursor.getString(2)+","+cursor.getString(3)+","+cursor.getString(4)+","+cursor.getString(5);
			data.add(datas);
		}
		while (cursor.moveToNext());
	}
	public void displayArray(List<String> test){
		
		Log.d("TEST","*****");
		for(int i=0;i<test.size();i++){
			Log.d("Datas : ",""+test.get(i));
		}
	}
    private void openChart(){
    	int[] x = { 1,2,3,4,5,6};
    	
    	List<String> test1=new ArrayList<String>();
    	List<String> test2=new ArrayList<String>();
    	List<String> test3=new ArrayList<String>();
    	List<String> test4=new ArrayList<String>();
    	List<String> test5=new ArrayList<String>();
    	List<String> test6=new ArrayList<String>();

    	for(int i=0;i<userDatas.size();i++){
    		Log.d("Data Come: ", ""+userDatas.get(i));
    		String[] data=userDatas.get(i).split(",");
    		test1.add(data[0]);
    		test2.add(data[1]);
    		test3.add(data[2]);
    		test4.add(data[3]);
    		test5.add(data[4]);
    		test6.add(data[5]);
    	}
    	
    	XYSeries test1Series = new XYSeries("EA");
    	XYSeries test2Series = new XYSeries("MA");
    	XYSeries test3Series = new XYSeries("SA");
    	XYSeries test4Series = new XYSeries("t4");
    	XYSeries test5Series = new XYSeries("t5");
    	XYSeries test6Series = new XYSeries("t6");
    	
    	
    	displayArray(test1);
    	displayArray(test2);
    	displayArray(test3);
    	displayArray(test4);
    	displayArray(test5);
    	displayArray(test6);
    	
    	for(int i=0;i<test1.size();i++){
    		test1Series.add(x[i],Integer.parseInt(test1.get(i)));
    		test2Series.add(x[i],Integer.parseInt(test2.get(i)));
    		test3Series.add(x[i],Integer.parseInt(test3.get(i)));
    		test4Series.add(x[i],Integer.parseInt(test4.get(i)));
    		test5Series.add(x[i],Integer.parseInt(test5.get(i)));
    		test6Series.add(x[i],Integer.parseInt(test6.get(i)));
    	}
    	
    	XYMultipleSeriesDataset dataset = new XYMultipleSeriesDataset();
    	dataset.addSeries(test1Series);
    	dataset.addSeries(test2Series);
    	dataset.addSeries(test3Series);
    	dataset.addSeries(test4Series);
    	dataset.addSeries(test5Series);
    	dataset.addSeries(test6Series);
    	
    	// Creating XYSeriesRenderer to customize incomeSeries
    	XYSeriesRenderer test1Renderer = new XYSeriesRenderer();
    	test1Renderer.setColor(Color.GREEN);
    	test1Renderer.setPointStyle(PointStyle.CIRCLE);
    	test1Renderer.setFillPoints(true);
    	test1Renderer.setLineWidth(2);
    	test1Renderer.setDisplayChartValues(true);
    	
    	XYSeriesRenderer test2Renderer = new XYSeriesRenderer();
    	test1Renderer.setColor(Color.YELLOW);
    	test1Renderer.setPointStyle(PointStyle.CIRCLE);
    	test1Renderer.setFillPoints(true);
    	test1Renderer.setLineWidth(2);
    	test1Renderer.setDisplayChartValues(true);
    	
    	XYSeriesRenderer test3Renderer = new XYSeriesRenderer();
    	test1Renderer.setColor(Color.WHITE);
    	test1Renderer.setPointStyle(PointStyle.CIRCLE);
    	test1Renderer.setFillPoints(true);
    	test1Renderer.setLineWidth(2);
    	test1Renderer.setDisplayChartValues(true);
    	
    	XYSeriesRenderer test4Renderer = new XYSeriesRenderer();
    	test1Renderer.setColor(Color.WHITE);
    	test1Renderer.setPointStyle(PointStyle.CIRCLE);
    	test1Renderer.setFillPoints(true);
    	test1Renderer.setLineWidth(2);
    	test1Renderer.setDisplayChartValues(true);
    	
    	XYSeriesRenderer test5Renderer = new XYSeriesRenderer();
    	test1Renderer.setColor(Color.WHITE);
    	test1Renderer.setPointStyle(PointStyle.CIRCLE);
    	test1Renderer.setFillPoints(true);
    	test1Renderer.setLineWidth(2);
    	test1Renderer.setDisplayChartValues(true);
    	
    	XYSeriesRenderer test6Renderer = new XYSeriesRenderer();
    	test1Renderer.setColor(Color.WHITE);
    	test1Renderer.setPointStyle(PointStyle.CIRCLE);
    	test1Renderer.setFillPoints(true);
    	test1Renderer.setLineWidth(2);
    	test1Renderer.setDisplayChartValues(true);
    	
    	XYMultipleSeriesRenderer multiRenderer = new XYMultipleSeriesRenderer();
    	multiRenderer.setXLabels(0);
    	multiRenderer.setChartTitle("Test Result");
    	multiRenderer.setXTitle("Test Data");
    	multiRenderer.setYTitle("Data Values");
    	multiRenderer.setZoomButtonsVisible(true);    	    	
    	for(int i=0;i<x.length;i++){
    		multiRenderer.addXTextLabel(i+1, numbers[i]);    		
    	}    	
    	
    	
    	multiRenderer.addSeriesRenderer(test1Renderer);
    	multiRenderer.addSeriesRenderer(test2Renderer);
    	multiRenderer.addSeriesRenderer(test3Renderer);
    	multiRenderer.addSeriesRenderer(test4Renderer);
    	multiRenderer.addSeriesRenderer(test5Renderer);
    	multiRenderer.addSeriesRenderer(test6Renderer);
    	
    	LinearLayout chartContainer = (LinearLayout) findViewById(R.id.chart_container);
    	
   		mChart = (GraphicalView) ChartFactory.getLineChartView(getBaseContext(), dataset, multiRenderer);    	
    		
   		chartContainer.addView(mChart);
    	
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
		startActivity(new Intent(GenerateGraphActivity.this,AdminHomeActivity.class));
	    return;
	}
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		if(v==imgBtnClose){
				startActivity(new Intent(GenerateGraphActivity.this,AdminHomeActivity.class));
		}
	}
}