/**   
 * @Title: ResourceUtil.java
 * @Package com.x.util
 * @Description: TODO 
 
 * @date 2014-2-17 下午01:17:32
 * @version V1.0   
 */

package com.pain.publics.utils;

import android.content.Context;
import android.graphics.drawable.Drawable;

/**
 * @ClassName: ResourceUtil
 * @Description: TODO
 
 * @date 2014-2-17 下午01:17:32
 * 
 */

public class ResourceUtil {

	public static String getString(Context context, int id) {
		return context.getResources().getString(id);
	}

	public static String getString(Context context, int id, String... formatArgs) {
		return context.getResources().getString(id, formatArgs);
	}

	public static int getInteger(Context context, int id) {
		return context.getResources().getInteger(id);
	}

	public static int getDimensionPixelSize(Context context, int id) {
		return context.getResources().getDimensionPixelSize(id);
	}

	public static float getDimension(Context context, int id) {
		return context.getResources().getDimension(id);
	}
	
	public static Drawable getDrawable(Context context, int id){
		return context.getResources().getDrawable(id);
	}
}
