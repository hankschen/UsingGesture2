package com.example.using_gesture2;

import java.util.ArrayList;

import android.app.Activity;
import android.gesture.Gesture;
import android.gesture.GestureLibraries;
import android.gesture.GestureLibrary;
import android.gesture.GestureOverlayView;
import android.gesture.Prediction;
import android.gesture.GestureOverlayView.OnGesturePerformedListener;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class MainActivity extends Activity
{
	GestureOverlayView gestureOverlay;
	GestureLibrary gestureLibrary;
	TextView textView1;
	ImageView imageView1;
	LinearLayout linearLayout1,linearLayout3 ,linearLayout4;
	
	private void findGestureLib()
	{
		gestureLibrary = GestureLibraries.fromRawResource(this, R.raw.gestures);
		if (!gestureLibrary.load())
		{
			finish();
		}
	}
	
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		findGestureLib();
		findViews();
	}

	void findViews()
	{
		linearLayout1 = (LinearLayout) findViewById(R.id.linearLayout1);
		linearLayout3 = (LinearLayout) findViewById(R.id.linearLayout3);
		linearLayout4 = (LinearLayout) findViewById(R.id.linearLayout4);
		textView1 = (TextView) findViewById(R.id.textView1);
		imageView1 = (ImageView) findViewById(R.id.imageView1);
		gestureOverlay = (GestureOverlayView) findViewById(R.id.gestureOverlay);
		gestureOverlay.addOnGesturePerformedListener(gestureListener);
		linearLayout1.setOnClickListener(helpListener);
	}
	
	OnClickListener helpListener = new OnClickListener(){

		int clickCount;
		@Override
		public void onClick(View v)
		{
			clickCount++;
			if(clickCount ==1)
				linearLayout3.setVisibility(View.VISIBLE);
			if(clickCount ==2)
				linearLayout4.setVisibility(View.VISIBLE);
			if(clickCount ==3){
				linearLayout1.setVisibility(View.GONE);
				clickCount = 0;
			}
		}
	};

	OnGesturePerformedListener gestureListener = new OnGesturePerformedListener()
	{
		@Override
		public void onGesturePerformed(GestureOverlayView overlay, Gesture gesture)
		{
			ArrayList<Prediction> predictions = gestureLibrary.recognize(gesture);
			System.out.println(predictions);
			if (predictions.size() > 0 && predictions.get(0).score > 5)
			{
				String gestureName = predictions.get(0).name;
				if(gestureName.equals("toRight"))
					changeText();
				else if(gestureName.equals("toLeft"))
					changeImage();
				else if(gestureName.equals("toRestore"))
					restore();
			}
		}
	};

	public void changeImage()
	{
		int[] iconResID = {R.drawable.backup,R.drawable.browser,R.drawable.calculator,R.drawable.contacts,R.drawable.document};
		imageView1.setImageResource(iconResID[(int) (Math.random() * 5)]);
		textView1.setText("變更圖片了");
	}

	public void changeText()
	{
		int r = (int) (Math.random() * 255);
		int g = (int) (Math.random() * 255);
		int b = (int) (Math.random() * 255);
		System.out.println("r = " + r);
		textView1.setText("變更文字了");
		textView1.setTextColor(Color.rgb(r, g, b));
		textView1.setTextSize(50);
	}

	public void restore()
	{
		setContentView(R.layout.activity_main);
		findViews();
	}
}
