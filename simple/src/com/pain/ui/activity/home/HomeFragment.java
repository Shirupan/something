
package com.pain.ui.activity.home;

import com.pain.publics.utils.ResourceUtil;
import com.pain.simple.R;
import com.pain.ui.activity.base.BaseFragment;
import com.pain.ui.view.TabPageIndicator;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class HomeFragment extends BaseFragment {

	public ViewPager mViewPager;
	private TabPageIndicator indicator;
	private HomeFragmentPagerAdapter homeFragmentPagerAdapter;

	public static Fragment newInstance(Bundle bundle) {
		HomeFragment fragment = new HomeFragment();
		if (bundle != null)
			fragment.setArguments(bundle);
		return fragment;
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}

	@Override
	public void onResume() {
		super.onResume();
	}

	@Override
	public void onPause() {
		super.onPause();
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View converView = inflater.inflate(R.layout.fragment_home, null);
		initViewPager(converView);

		return converView;
	}

	private void initViewPager(View view) {
		String[] titles = { ResourceUtil.getString(mActivity, R.string.home_recommend),
				ResourceUtil.getString(mActivity, R.string.home_collection),
				ResourceUtil.getString(mActivity, R.string.home_must_have) };
		homeFragmentPagerAdapter = new HomeFragmentPagerAdapter(this.getChildFragmentManager(), titles);

		mViewPager = (ViewPager) view.findViewById(R.id.fh_content_pager);
		mViewPager.setAdapter(homeFragmentPagerAdapter);
		mViewPager.setOffscreenPageLimit(3);

		indicator = (TabPageIndicator) view.findViewById(R.id.fh_indicator);
		indicator.setViewPager(mViewPager);
		mViewPager.setCurrentItem(0);
	}

	public class HomeFragmentPagerAdapter extends FragmentPagerAdapter {

		String titles[];

		public HomeFragmentPagerAdapter(FragmentManager fm, String[] titles) {
			super(fm);
			this.titles = titles;
		}

		@Override
		public Fragment getItem(int position) {
			Fragment fragment = null;
			switch (position) {
			case 0:
				fragment = HomeRecommendFragment.newInstance(null);
				break;
			case 1:
				fragment = HomeCollectionFragment.newInstance(null);
				break;
			case 2:
				fragment = HomeMustHaveFragment.newInstance(null);
				break;
			}
			return fragment;
		}

		@Override
		public int getCount() {
			return titles.length;
		}

		@Override
		public CharSequence getPageTitle(int position) {
			return titles[position];
		}
	}
}
