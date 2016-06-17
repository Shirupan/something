package com.pain.ui.activity.home;

import com.pain.simple.R;
import com.pain.ui.activity.base.BaseFragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class GamesNewFragment extends BaseFragment {
	public static Fragment newInstance(Bundle bundle) {
		GamesNewFragment fragment = new GamesNewFragment();
		if (bundle != null)
			fragment.setArguments(bundle);
		return fragment;
	}

	public ViewPager getPargentViewPager() {
		return ((GamesFragment) getParentFragment()).mViewPager;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View rootView = null;
		rootView = inflater.inflate(R.layout.fragment_game_new,
				container, false);
		// errorView = inflater.inflate(R.layout.error, null);
		return rootView;
	}

}
