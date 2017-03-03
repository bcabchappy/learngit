package com.common.argis;

public class Coordinate {

	static double M_PI = Math.PI;

	// 经纬度转墨卡托
	// 经度(lon)，纬度(lat)
	public static double[] lonLat2Mercator(double lon, double lat) {
		double[] xy = new double[2];
		double x = lon * 20037508.342789 / 180;
		double y = Math.log(Math.tan((90 + lat) * M_PI / 360)) / (M_PI / 180);
		y = y * 20037508.34789 / 180;
		xy[0] = x;
		xy[1] = y;
		return xy;
	}

	// 墨卡托转经纬度
	public static double[] Mercator2lonLat(double mercatorX, double mercatorY) {
		double[] xy = new double[2];
		double x = mercatorX / 20037508.34 * 180;
		double y = mercatorY / 20037508.34 * 180;
		y = 180 / M_PI * (2 * Math.atan(Math.exp(y * M_PI / 180)) - M_PI / 2);
		xy[0] = x;
		xy[1] = y;
		return xy;
	}

	public static void main(String[] args) {
		double[] num;
		num = lonLat2Mercator(118.1009197375,24.6037836473);
		for (int i = 0; i < num.length; i++) {
			java.text.NumberFormat nf = java.text.NumberFormat.getInstance();   
			nf.setGroupingUsed(false);  
			System.out.println(nf.format(num[i]));  
		}
		// num = Mercator2lonLat(13401221.612075035,4309075.414032666);
		// for(int i=0;i<num.length;i++)
		// {
		// System.out.println(num[i]);
		// }
	}

}
