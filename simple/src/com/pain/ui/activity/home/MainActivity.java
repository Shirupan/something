
package com.pain.ui.activity.home;

import com.pain.publics.utils.SharedPrefsUtil;
import com.pain.publics.utils.Utils;
import com.pain.simple.R;
import com.pain.ui.activity.base.BaseActivity;
import com.pain.ui.view.drag.SlideDragLayout;
import com.pain.ui.view.drag.SlideDragLayout.DragListener;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;
import butterknife.ButterKnife;
import butterknife.InjectView;

public class MainActivity extends BaseActivity implements OnClickListener {
	
	@InjectView(R.id.am_slde_drag_layout) SlideDragLayout slideDragLayout;
	@InjectView(R.id.mf_tab_rg) RadioGroup mRgHost;
	@InjectView(R.id.mf_home_tab_rb) RadioButton homeTabBtn;
//	@InjectView(R.id.mf_apps_tab_rb) RadioButton appTabBtn;
	@InjectView(R.id.mf_games_tab_rb) RadioButton gameTabBtn;
	@InjectView(R.id.mf_ringtones_tab_rb) RadioButton RingtoneTabBtn;
	@InjectView(R.id.mf_wallpaper_tab_rb) RadioButton wallpaperTabBtn;
	@InjectView(R.id.rl_title_bar) View mTitleView;
	@InjectView(R.id.title_pendant) View mTitlePendant;
	@InjectView(R.id.mh_notification_iv) TextView mTipsView;
	@InjectView(R.id.mh_navigate_ll) View mSlideView;
	@InjectView(R.id.mh_search_ll) View mMenuView;
	@InjectView(R.id.ld_my_apps_tips) TextView myAppsTips;
	@InjectView(R.id.ld_my_contents_tips) ImageView myContentsTips;
	@InjectView(R.id.ld_settings_tips) ImageView settingsTips;
	@InjectView(R.id.ld_tools_tips) ImageView toolsTips;
	@InjectView(R.id.tv_account) TextView mAccountTv;
	@InjectView(R.id.account_head) ImageView accountHead;
	
	private final String TAG="MainActivity";
	public static final int HANDLER_MENU_ID = 1100;
	public static final int HANDLER_MY_APPS_ID = 1101;
	public static final int HANDLER_SETTINGS_ID = 1102;
	public static final int HANDLER_MY_CONTENTS_ID = 1103;
	public static final int HANDLER_RAM_PROGRESS_ID = 1104;
	public static final int HANDLER_STORAGE_PROGRESS_ID = 1105;
	public static final int HANDLER_EQUALLY_PROGRESS_ID = 1106;
	public static final int HANDLER_CLEAR_STORAGE_ID = 1107;
	public static final int HANDLER_LOADING_COMPLETE_ID = 1108;
	public static final int HANDLER_STOP_ANIMATION_ID = 1109;
	public static final int HANDLER_TOOLS_ID = 1110;

	private MainActivity mActivity;
	private final DisplayMetrics displayMetrics = new DisplayMetrics();
	private Fragment mCurrentPage, homeFragement, appsFragment, gamesFragment, ringtonesFragment, wallpaperFragment;
	private int tabNum;

	private static final int[] btns = { R.id.btn_account_ll, R.id.btn_my_apps_ll, R.id.btn_my_contents_ll,
			R.id.btn_tools_ll, R.id.btn_settings_ll };

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		mActivity = this;
		getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
		setContentView(R.layout.activity_main);
		ButterKnife.inject(this);
		initUi();
	}

//	private void findByID() {
//		// TODO Auto-generated method stub
//		slideDragLayout=(SlideDragLayout)findViewById(R.id.am_slde_drag_layout);
//		mRgHost=(RadioGroup)findViewById(R.id.mf_tab_rg);
//		homeTabBtn=(RadioButton)findViewById(R.id.mf_home_tab_rb);
//		appTabBtn=(RadioButton)findViewById(R.id.mf_apps_tab_rb);
//		gameTabBtn=(RadioButton)findViewById(R.id.mf_games_tab_rb);
//		RingtoneTabBtn=(RadioButton)findViewById(R.id.mf_ringtones_tab_rb);
//		wallpaperTabBtn=(RadioButton)findViewById(R.id.mf_wallpaper_tab_rb);
//		mTitleView=(View)findViewById(R.id.rl_title_bar);
//		mTitlePendant=(View)findViewById(R.id.title_pendant);
//		mTipsView=(TextView)findViewById(R.id.mh_notification_iv);
//		mSlideView=(View)findViewById(R.id.mh_navigate_ll);
//		mMenuView=(View)findViewById(R.id.mh_search_ll);
//		myAppsTips=(TextView)findViewById(R.id.ld_my_apps_tips);
//		myContentsTips=(ImageView)findViewById(R.id.ld_my_contents_tips);
//		settingsTips=(ImageView)findViewById(R.id.ld_settings_tips);
//		toolsTips=(ImageView)findViewById(R.id.ld_tools_tips);
//		mAccountTv=(TextView)findViewById(R.id.tv_account);
//	}

	@Override
	public void onResume() {
		super.onResume();
		closeLeftDrawer();
	}

	@Override
	public void onPause() {
		super.onPause();
		if (mCurrentPage != null) {
		}
	}

	private void getHomeTab() {
		if (mRgHost != null) {
			mRgHost.getChildAt(0).performClick();
		}
	}

	private void initUi() {
	  if (slideDragLayout==null){
	    Log.d(WINDOW_SERVICE, "slideDragLayout is null");
	  }
		slideDragLayout.setDisplayMetrics(displayMetrics);
		slideDragLayout.setDragListener(mDragListener);
		initMenuView();
		initHomeView();
	}

	private void initHomeView() {
		mMenuView.setVisibility(View.VISIBLE);
		mMenuView.setOnClickListener(this);
		mSlideView.setOnClickListener(this);
		mRgHost.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(RadioGroup group, int checkedId) {
				switch (checkedId) {

				// Homes
				case R.id.mf_home_tab_rb:
					if (homeFragement == null) {
						homeFragement = HomeFragment.newInstance(null);
					}
					switchFragments(homeFragement);
					break;
//				// Apps
//				case R.id.mf_apps_tab_rb:
//					if (appsFragment == null) {
//						appsFragment = AppsFragment.newInstance(null);
//					}
//					switchFragments(appsFragment);
//					break;
				// Games
				case R.id.mf_games_tab_rb:
					if (gamesFragment == null) {
						gamesFragment = GamesFragment.newInstance(null);
					}
					switchFragments(gamesFragment);
					break;
				// Ringtones
				case R.id.mf_ringtones_tab_rb:
					if (ringtonesFragment == null) {
						ringtonesFragment = RingtonesFragment.newInstance(null);
					}
					switchFragments(ringtonesFragment);
					break;

				// Wallpapers
				case R.id.mf_wallpaper_tab_rb:
					if (wallpaperFragment == null) {
						wallpaperFragment = WallpaperFragment.newInstance(null);
					}
					switchFragments(wallpaperFragment);
					break;
				// Me
//				case R.id.mf_apps_tab_rb:
//					if (slideDragLayout.isOpened()) {
//						slideDragLayout.close();
//					} else {
//						slideDragLayout.open();
//					}							
//					break;
				}
			}
		});
		mRgHost.getChildAt(tabNum).performClick();
	}

	private void initMenuView() {
		// listener buttons events
		for (int btn : btns) {
			findViewById(btn).setOnClickListener(this);
		}
		Utils.setBackgroundResource(accountHead, R.drawable.ic_head01, mActivity);
	}

	private DragListener mDragListener = new DragListener() {

		@Override
		public void onOpen() {
		}

		@Override
		public void onDrag(float percent) {

		}

		@Override
		public void onClose() {
		}
	};

	public void switchFragments(Fragment fragment) {
		FragmentManager manager = getSupportFragmentManager();
		FragmentTransaction ft = manager.beginTransaction();
		if (mCurrentPage == null && !isAlreadyAddFragment(fragment)) {
			ft.add(R.id.fsh_content_fl, fragment, fragment.getClass().toString()).commitAllowingStateLoss();
		} else {
			if (isAlreadyAddFragment(fragment)) {
				ft.hide(mCurrentPage).show(fragment).commitAllowingStateLoss();
			} else {
				ft.hide(mCurrentPage).add(R.id.fsh_content_fl, fragment, fragment.getClass().toString())
						.commitAllowingStateLoss();
			}
		}
		mCurrentPage = fragment;
	}

	private boolean isAlreadyAddFragment(Fragment fragment) {
		return fragment.isAdded()
		/*|| getChildFragmentManager().findFragmentByTag(fragment.getClass().toString()) != null*/;
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.mh_navigate_ll:
			if (slideDragLayout.isOpened()) {
				slideDragLayout.close();
			} else {
				slideDragLayout.open();
			}
			break;

		case R.id.mh_search_ll:
			
			break;
		case R.id.btn_account_ll:
			
			break;

		case R.id.btn_my_apps_ll:
			
			break;

		case R.id.btn_my_contents_ll:
			
			break;

		case R.id.btn_tools_ll:
			break;

		case R.id.btn_settings_ll:
			break;

		}
	}

	@Override
	public void finish() {
		super.finish();
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			exit();
			return true;
		} else if (keyCode == KeyEvent.KEYCODE_MENU) {
			showLeftDrawer();
			return true;
		}
		return super.onKeyDown(keyCode, event);
	}

	private void showLeftDrawer() {
		if (slideDragLayout != null) {
			if (slideDragLayout.isOpened()) {
				slideDragLayout.close();
			} else {
				slideDragLayout.open();
			}
		}
	}

	private void closeLeftDrawer() {
		if (SharedPrefsUtil.getValue(this, "isGoHome", false)) {
			SharedPrefsUtil.putValue(this, "isGoHome", false);
			getHomeTab();
			slideDragLayout.close();
		}
	}

	private static Boolean isExit = false;
	static Handler exitHandler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			isExit = false;
		}
	};

	protected void exit() {
		if (slideDragLayout.isOpened()) {
			slideDragLayout.close();
		} else {
			if (isExit == false) {
				isExit = true;
				Toast.makeText(this, this.getResources().getString(R.string.exit_tips), Toast.LENGTH_SHORT).show();
				exitHandler.sendEmptyMessageDelayed(0, 2000);
			} else {
				this.finish();
			}
		}	
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
	}
}
