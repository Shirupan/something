package com.pain.ui.activity.home;

import com.pain.publics.utils.ImageUtils;
import com.pain.simple.R;
import com.pain.ui.activity.base.BaseFragment;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

public class HomeRecommendFragment extends BaseFragment {

	private View errorView;

	public static Fragment newInstance(Bundle bundle) {
		HomeRecommendFragment fragment = new HomeRecommendFragment();
		if (bundle != null)
			fragment.setArguments(bundle);
		return fragment;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View rootView = null;
		rootView = inflater.inflate(R.layout.fragment_home_recommend, container, false);
//		errorView = inflater.inflate(R.layout.error, null);
		ImageView head=(ImageView)rootView.findViewById(R.id.head);
		try {
			Bitmap bm = BitmapFactory.decodeResource(getActivity().getResources(), R.drawable.head01);
			BitmapDrawable bd = new BitmapDrawable(ImageUtils.toRoundBitmap(bm));
			head.setBackgroundDrawable(bd);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return rootView;
	}
	
	public ViewPager getPargentViewPager() {
		return ((HomeFragment) getParentFragment()).mViewPager;
	}

}
