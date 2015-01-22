package com.galis.demo.utils;

import android.app.ActivityManager;
import android.app.ActivityManager.RunningTaskInfo;
import android.content.ComponentName;
import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;

import java.util.List;

/**
 * @author galis
 * @date: 2014-2-22-下午12:52:12
 */
public class AppUtil {
    /**
     * dip转px
     * @param context
     * @param dipValue
     * @return
     */
    public static int dip2px(Context context, float dipValue){
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int)(dipValue * scale + 0.5f);
    }

    /**
     * px转dip
     * @param context
     * @param pxValue
     * @return
     */
    public static int px2dip(Context context, float pxValue){
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int)(pxValue / scale + 0.5f);
    }

    /**
     * 将px值转换为sp值，保证文字大小不变
     *
     *            （DisplayMetrics类中属性scaledDensity）
     * @return
     */
    public static int px2sp(Context context, float pxValue) {
        final float fontScale = context.getResources().getDisplayMetrics().scaledDensity;
        return (int) (pxValue / fontScale + 0.5f);
    }

    /**
     * 将sp值转换为px值，保证文字大小不变
     *
     *            （DisplayMetrics类中属性scaledDensity）
     * @return
     */
    public static int sp2px(Context context, float spValue) {
        final float fontScale = context.getResources().getDisplayMetrics().scaledDensity;
        return (int) (spValue * fontScale + 0.5f);
    }

    public static float px2dip_returnF(Context context, float pxValue){
        final float scale = context.getResources().getDisplayMetrics().density;
        return (pxValue * scale);
    }
    /**
     * 判断字符是否为空
     * @param result
     * @return
     */
    public static boolean isNull(String result)
    {
    	if("".equals(result))
    	{
    		return true;
    	}
    	else if(result == null) 
    	{
    		return true;
    	}
    	else 
    	{
    		return false;
    	}
    	
    }

    /**
     * 获取版本号，版本名
     * @param context
     * @return
     */
    public static String getVersionName(Context context){
        try{

            PackageInfo packageInfo = context.getPackageManager().getPackageInfo(context.getPackageName(),0);
            return packageInfo.versionName;
        }catch (Exception e){
            e.printStackTrace();
            return "";
        }

    }
    public static String getVersionCode(Context context){
        try{

            PackageInfo packageInfo = context.getPackageManager().getPackageInfo(context.getPackageName(),0);
            return packageInfo.versionCode+"";
        }catch (Exception e){
            e.printStackTrace();
            return "";
        }

    }


    /**
     * 将版本1.1.1转为111
     *
     * @param version
     * @return
     */
    public static int getVersion(String version) {
        StringBuilder builder = new StringBuilder(version);
        //将1.1补充成1.1.0用来比较
        if (builder.length() < 5) {
            builder.append(".0");
        }
        for (int i = 0; i < builder.length(); i++) {
            if (builder.charAt(i) == '.') {
                builder.deleteCharAt(i);
            }
        }
        return Integer.parseInt(builder.toString());
    }


    /**
     * 获取应用下载渠道id
     */
    public static String getChannelId(Context context){
        String channelId = getMetaData(context,"UMENG_CHANNEL");
        if(channelId != null){
            return channelId;
        }

        return "Yingyongbao";

    }

    private static String getMetaData(Context context, String key) {
        try{

            ApplicationInfo applicationInfo = context.getPackageManager().getApplicationInfo(context.getPackageName(),
                    PackageManager.GET_META_DATA);
            Object value = applicationInfo.metaData.get(key);
            if(value != null){
                return value.toString();
            }

        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }


    /****
     * 判断是否有网络
     */
    public static boolean isNetworkAvailable(Context context) {
  	  ConnectivityManager connectivity = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
  	  if (connectivity == null) {  
  		  return false;  
  	  } else {  
  	      NetworkInfo[] info = connectivity.getAllNetworkInfo();
  	      if (info != null) {  
  	        for (int i = 0; i < info.length; i++) {  
  	            if (info[i].getState() == NetworkInfo.State.CONNECTED) {
  	              return true;  
  	            }  
  	        }  
  	      }  
  	  }  
  	  return false;  
  	}
    
    /**
     * 判断应用程序在前台还是后台运行
     */
    public static boolean isRunningForeground(Context mContext) {

    	String packageName = mContext.getPackageName();
    	ActivityManager activityManager = (ActivityManager) mContext.getSystemService(Context.ACTIVITY_SERVICE);
    	List<RunningTaskInfo> taskInfo = activityManager.getRunningTasks(1);
    	if(taskInfo.size() > 0){
    		System.out.println("应用包名packageName：："+packageName+"\ntopActivityClassName::"+taskInfo.get(0).topActivity.getPackageName());
    		if(packageName.equals(taskInfo.get(0).topActivity.getPackageName())){
    			Log.i("info", "应用程序-----》》isRunningForeGround");
    			return true;
    		}
    	}
    	Log.i("info", "应用程序-----》》isNotRunningForeGround");
    	return false;
    	
		
	}
    
    public static String getTopActivityName(Context mContext2) {
		String topActivityClassName = null;
		ActivityManager activityManager = (ActivityManager) mContext2.getSystemService(Context.ACTIVITY_SERVICE);
		List<RunningTaskInfo> runningTaskInfos = activityManager.getRunningTasks(100);
		if(runningTaskInfos != null){
			ComponentName f = runningTaskInfos.get(0).topActivity;
			topActivityClassName = f.getClassName();
		}
		return topActivityClassName;
	}
    

}
