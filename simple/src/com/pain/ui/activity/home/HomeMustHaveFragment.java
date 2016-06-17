package com.pain.ui.activity.home;
import com.pain.simple.R;
import com.pain.ui.activity.base.BaseFragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class HomeMustHaveFragment  extends BaseFragment {
	public static Fragment newInstance(Bundle bundle) {
		HomeMustHaveFragment fragment = new HomeMustHaveFragment();
		if (bundle != null)
			fragment.setArguments(bundle);
		return fragment;
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View rootView = null;
		rootView = inflater.inflate(R.layout.fragment_home_must_have, container, false);
		return rootView;
	}
	public ViewPager getPargentViewPager() {
		return ((HomeFragment) getParentFragment()).mViewPager;
	}
}
