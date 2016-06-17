package com.pain.ui.activity.home;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.pain.simple.R;
import com.pain.ui.activity.base.BaseFragment;

public class WallpaperAlbumFragment extends BaseFragment {

	public static Fragment newInstance(Bundle bundle) {
		WallpaperAlbumFragment fragment = new WallpaperAlbumFragment();
		if (bundle != null)
			fragment.setArguments(bundle);
		return fragment;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View rootView = null;
		rootView = inflater.inflate(R.layout.fragment_wallpaper_album, container, false);
		return rootView;
	}

	public ViewPager getPargentViewPager() {
		return ((WallpaperFragment) getParentFragment()).mViewPager;
	}
}
