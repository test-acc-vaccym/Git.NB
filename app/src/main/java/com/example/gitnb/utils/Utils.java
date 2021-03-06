package com.example.gitnb.utils;

import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v4.widget.SwipeRefreshLayout;

public class Utils {
	private static SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
	private static DecimalFormat decimalFormat = new DecimalFormat(".00");

	public static int dpToPx(Context context, int dp) {
		return (int) (context.getResources().getDisplayMetrics().density * dp + 0.5f);
	}

	public static Date getDate(String date) {
		try {
			return format.parse(date);
		} catch (ParseException e) {
			return new Date();
		}
	}

	public static String now() {
		return format.format(new Date());
	}

	public static int fromNow(String date) {
		Date from = getDate(date);
		Calendar cal = Calendar.getInstance();
		cal.setTime(from);
		long time1 = cal.getTimeInMillis();
		cal.setTime(new Date());
		long time2 = cal.getTimeInMillis();
		long between_days = (time2 - time1) / (1000 * 60);

		return Integer.parseInt(String.valueOf(between_days));
	}

	public static void setRefreshing(final SwipeRefreshLayout swipeRefreshLayout, final boolean isRefreshing) {
		if (swipeRefreshLayout != null) {
			swipeRefreshLayout.post(new Runnable() {
				@Override
				public void run() {
					swipeRefreshLayout.setRefreshing(isRefreshing);
				}
			});
		}
	}

	public static String getTimeFromNow(String time) {
		int minute = Utils.fromNow(time);
		int hours = minute / 60;
		int days = hours / 24;
		int months = days / 30;
		int years = months / 12;
		if (years == 1) {
			return years + " year ago";
		} else if (years > 1) {
			return years + " years ago";
		} else if (months == 1) {
			return months + " month ago";
		} else if (months > 1) {
			return months + " months ago";
		} else if (days == 1) {
			return days + " day ago";
		} else if (days > 1) {
			return days + " days ago";
		} else if (hours > 1) {
			return hours + " hours ago";
		} else {
			return hours + " hour ago";
		}
	}

	public static String getSoftValue(int count) {
		if (count < 1000) {
			return String.valueOf(count);
		} else {
			return decimalFormat.format(count / 1000.0f) + "K";
		}
	}

	private String getLastModified() {
		Calendar cd = Calendar.getInstance();
		cd.add(Calendar.DAY_OF_YEAR, -1);
		SimpleDateFormat sdf = new SimpleDateFormat("EEE d MMM yyyy HH:mm:ss 'GMT'", Locale.US);
		sdf.setTimeZone(TimeZone.getTimeZone("GMT")); // 设置时区为GMT
		return sdf.format(cd.getTime());
	}

	public static boolean isNetworkAvailable(Context context) {
		ConnectivityManager connectivity = (ConnectivityManager) context
				.getSystemService(Context.CONNECTIVITY_SERVICE);
		if (connectivity != null) {
			NetworkInfo info = connectivity.getActiveNetworkInfo();
			if (info != null && info.isConnected())
			{
				// 当前网络是连接的
				if (info.getState() == NetworkInfo.State.CONNECTED)
				{
					// 当前所连接的网络可用
					return true;
				}
			}
		}
		return false;
	}
}
