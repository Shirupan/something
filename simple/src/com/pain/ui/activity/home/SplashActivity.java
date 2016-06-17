package com.pain.ui.activity.home;

import java.util.Timer;
import java.util.TimerTask;

import butterknife.InjectViews;

import com.pain.publics.utils.SharedPrefsUtil;
import com.pain.publics.utils.Utils;
import com.pain.simple.R;
import com.pain.ui.activity.guide.GuideActivity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.SystemClock;
import android.util.Log;
import android.widget.ImageView;

public class SplashActivity extends Activity{
	private final String TAG="SplashActivity";
	private Context context = this;
	private boolean initFinished=true, insertFinished=true;
	private ImageView imageView;
	private boolean isFirst = true;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		showActivitySplash();
	}
	private void showActivitySplash() {
		setContentView(R.layout.activity_splash);
		imageView = (ImageView) findViewById(R.id.img_splash_logo);

		// 设置背景图片
		Utils.setBackgroundResource(imageView, R.drawable.start, context);
		Timer timer = new Timer();
		timer.schedule(new TimerTask() {

			@Override
			public void run() {
				while (true) {
					SystemClock.sleep(200);
					if (insertFinished && initFinished)
						break;
				}
				enterActivity();
			}
		}, 2000);
	}
	
	private void enterActivity() {
		isFirst = SharedPrefsUtil.getValue(SplashActivity.this, "isFirst2", true);
		if (isFirst == true)// 第一次进入应用，显示新手引导界面
		{
			Intent guideIntent = new Intent(SplashActivity.this, GuideActivity.class);
			startActivity(guideIntent);
			isFirst = false;
			SharedPrefsUtil.putValue(SplashActivity.this, "isFirst2", isFirst);
			SplashActivity.this.finish();
		} else {// 进入MainActivity
			context.startActivity(new Intent(context, MainActivity.class));
			finish();
		}
	}
	
}
