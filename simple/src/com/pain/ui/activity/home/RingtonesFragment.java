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

public class RingtonesFragment extends BaseFragment {

	public ViewPager mViewPager;
	private TabPageIndicator indicator;
	private RingtonesFragmentPagerAdapter tingtonesFragmentPagerAdapter;

	public static Fragment newInstance(Bundle bundle) {
		RingtonesFragment fragment = new RingtonesFragment();
		if (bundle != null)
			fragment.setArguments(bundle);
		return fragment;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View converView = inflater.inflate(R.layout.fragment_home, null);
		initViewPager(converView);
		return converView;
	}

	private void initViewPager(View view) {
		String[] titles = { ResourceUtil.getString(mActivity, R.string.ringtone_new),
				ResourceUtil.getString(mActivity, R.string.ringtone_hot),

				ResourceUtil.getString(mActivity, R.string.ringtone_categories) };
		tingtonesFragmentPagerAdapter = new RingtonesFragmentPagerAdapter(this.getChildFragmentManager(), titles);

		mViewPager = (ViewPager) view.findViewById(R.id.fh_content_pager);
		mViewPager.setAdapter(tingtonesFragmentPagerAdapter);
		mViewPager.setOffscreenPageLimit(4);

		indicator = (TabPageIndicator) view.findViewById(R.id.fh_indicator);
		indicator.setViewPager(mViewPager);
		mViewPager.setCurrentItem(0);
	}

	public class RingtonesFragmentPagerAdapter extends FragmentPagerAdapter {

		String titles[];

		public RingtonesFragmentPagerAdapter(FragmentManager fm, String[] titles) {
			super(fm);
			this.titles = titles;
		}

		@Override
		public Fragment getItem(int position) {
			Fragment fragment = null;
			switch (position) {
			case 0:
				fragment = RingtonesNewFragment.newInstance(null);
				break;
			case 1:
				fragment = RingtonesHotFragment.newInstance(null);
				break;
			case 2:
				fragment = RingtonesCategoriesFragment.newInstance(null);
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
