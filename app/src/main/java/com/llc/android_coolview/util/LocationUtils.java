package com.llc.android_coolview.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationListener;
import com.amap.api.location.LocationManagerProxy;
import com.amap.api.location.LocationProviderProxy;

import android.content.Context;
import android.location.Address;
import android.location.Criteria;
import android.location.Geocoder;
import android.location.GpsSatellite;
import android.location.GpsStatus;
import android.location.GpsStatus.Listener;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.telephony.gsm.GsmCellLocation;
import android.util.Log;

public class LocationUtils {

	/**
	 * 基于基站的定位方式
	 */
	public static String getLocationByGsmCell(Context context) {
		TelephonyManager manager = (TelephonyManager) context
				.getSystemService(Context.TELEPHONY_SERVICE);
		GsmCellLocation mGsmCellLocation = (GsmCellLocation) manager
				.getCellLocation();
		int cid = mGsmCellLocation.getCid();
		int lac = mGsmCellLocation.getLac();
		String netOper = manager.getNetworkOperator();
		int mcc = Integer.parseInt(netOper.substring(0, 3));
		int mnc = Integer.parseInt(netOper.substring(3, 5));
		JSONObject holder = new JSONObject();
		JSONArray array = new JSONArray();
		JSONObject data = new JSONObject();
		try {
			holder.put("version", "1.1.0");
			holder.put("host", "maps.google.com");
			holder.put("address_language", "zh_CN");
			holder.put("request_address", true);
			holder.put("radio_type", "gsm");
			holder.put("carrier", "HTC");
			data.put("cell_id", cid);
			data.put("location_area_code", lac);
			data.put("mobile_countyr_code", mcc);
			data.put("mobile_network_code", mnc);
			array.put(data);
			holder.put("cell_towers", array);
			DefaultHttpClient client = new DefaultHttpClient();
			HttpPost post = new HttpPost("http://www.google.com/loc/json");
			StringEntity stringEntity = new StringEntity(holder.toString());
			post.setEntity(stringEntity);
			HttpResponse response = client.execute(post);
			HttpEntity entity = response.getEntity();
			InputStream stream = entity.getContent();
			InputStreamReader reader = new InputStreamReader(stream);
			BufferedReader br = new BufferedReader(reader);
			StringBuffer sb = new StringBuffer();
			String result = "";
			while ((result = br.readLine()) != null) {
				sb.append(result);
			}
			return sb.toString();
		} catch (JSONException e) {
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 基于GPS的定位实现
	 *
	 */
	private static GpsStatus mGpsStatus;

	public static HashMap<String, Object> getLocationByGPS(Context context) {
		HashMap<String, Object> items = new HashMap<String, Object>();
		// 获取到LocationManager对象
		final LocationManager manager = (LocationManager) context
				.getSystemService(Context.LOCATION_SERVICE);
		// 根据设置的Criteria对象，获取最符合此标准的provider对象
		String currentProvider = manager
				.getProvider(LocationManager.GPS_PROVIDER).getName();
		// 根据当前provider对象获取最后一次位置信息
		Location currentLocation = manager
				.getLastKnownLocation(currentProvider);
		// 如果位置信息为null，则请求更新位置信息
		if (currentLocation == null) {
			manager.requestLocationUpdates(currentProvider, 0, 0,
					new LocationListener() {
						// 状态改变时调用
						@Override
						public void onStatusChanged(String provider, int status,
													Bundle extras) {

						}

						// provider启用时调用
						@Override
						public void onProviderEnabled(String provider) {

						}

						// provider失效时调用
						@Override
						public void onProviderDisabled(String provider) {

						}

						// 位置发生改变时调用
						@Override
						public void onLocationChanged(Location location) {

						}
					});
		}
		manager.addGpsStatusListener(new Listener() {

			@Override
			public void onGpsStatusChanged(int event) {
				//
				mGpsStatus = manager.getGpsStatus(null);
				switch (event) {
					// 第一次定位时的事件
					case GpsStatus.GPS_EVENT_FIRST_FIX:
						break;
					// 开始定位的事件
					case GpsStatus.GPS_EVENT_STARTED:
						break;
					// 发送GPS卫星状态事件
					case GpsStatus.GPS_EVENT_SATELLITE_STATUS:
						Iterable<GpsSatellite> allSatellites = mGpsStatus
								.getSatellites();
						Iterator<GpsSatellite> it = allSatellites.iterator();
						@SuppressWarnings("unused")
						int count = 0;
						while (it.hasNext()) {
							count++;
						}
						break;
					// 停止定位事件
					case GpsStatus.GPS_EVENT_STOPPED:
						break;
					default:
						break;
				}

			}
		});
		// 直到获得最后一次位置信息为止，如果未获得最后一次位置信息，则显示默认经纬度
		// 每隔10秒获取一次位置信息
		while (true) {
			currentLocation = manager.getLastKnownLocation(currentProvider);
			if (currentLocation != null) {
				Log.d("Location", "Latitude: " + currentLocation.getLatitude());
				Log.d("Location",
						"location: " + currentLocation.getLongitude());
				items = getLoactionByLatitudeandLongitude(context,
						currentLocation.getLatitude(),
						currentLocation.getLongitude());
				break;
			} else {
				Log.d("Location", "currentLocation为null获取到的Latitude: " + 0);
				Log.d("Location", "currentLocation为null获取到的location: " + 0);
				items = getLoactionByLatitudeandLongitude(context, 0, 0);
				break;
			}

		}
		return items;
	}

	/**
	 * 基于网络形式的实现
	 *
	 */
	public static HashMap<String, Object> getLocationByNetWork(
			Context context) {
		HashMap<String, Object> items = new HashMap<String, Object>();
		// 获取到LocationManager对象
		LocationManager locationManager = (LocationManager) context
				.getSystemService(Context.LOCATION_SERVICE);
		// 创建一个Criteria对象
		Criteria criteria = new Criteria();
		// 设置粗略精确度
		criteria.setAccuracy(Criteria.ACCURACY_COARSE);
		// 设置是否需要返回海拔信息
		criteria.setAltitudeRequired(false);
		// 设置是否需要返回方位信息
		criteria.setBearingRequired(false);
		// 设置是否允许付费服务
		criteria.setCostAllowed(false);
		// 设置电量消耗等级
		criteria.setPowerRequirement(Criteria.POWER_HIGH);
		// 设置是否需要返回速度信息
		criteria.setSpeedRequired(false);
		// 根据设置的Criteria对象，获取最符合此标准的provider对象
		String currentProvider = locationManager.getBestProvider(criteria,
				true);
		// 根据当前provider对象获取最后一次位置信息
		Location currentLocation = locationManager
				.getLastKnownLocation(currentProvider);
		// 如果位置信息为null，则请求更新位置信息
		if (currentLocation == null) {
			locationManager.requestLocationUpdates(currentProvider, 0, 0,
					new LocationListener() {

						// 位置发生改变时调用
						@Override
						public void onLocationChanged(Location location) {
							Log.d("Location", "onLocationChanged");
							Log.d("Location", "onLocationChanged Latitude"
									+ location.getLatitude());
							Log.d("Location", "onLocationChanged location"
									+ location.getLongitude());
						}

						// provider失效时调用
						@Override
						public void onProviderDisabled(String provider) {
							Log.d("Location", "onProviderDisabled");
						}

						// provider启用时调用
						@Override
						public void onProviderEnabled(String provider) {
							Log.d("Location", "onProviderEnabled");
						}

						// 状态改变时调用
						@Override
						public void onStatusChanged(String provider, int status,
													Bundle extras) {
							Log.d("Location", "onStatusChanged");
						}
					});
		}
		// 直到获得最后一次位置信息为止，如果未获得最后一次位置信息，则显示默认经纬度
		// 每隔10秒获取一次位置信息
		while (true) {
			currentLocation = locationManager
					.getLastKnownLocation(currentProvider);
			if (currentLocation != null) {
				Log.d("Location", "Latitude: " + currentLocation.getLatitude());
				Log.d("Location",
						"location: " + currentLocation.getLongitude());
				items = getLoactionByLatitudeandLongitude(context,
						currentLocation.getLatitude(),
						currentLocation.getLongitude());
				break;
			} else {
				Log.d("Location", "Latitude: " + 0);
				Log.d("Location", "location: " + 0);
			}
			try {
				Thread.sleep(10000);
			} catch (InterruptedException e) {
				Log.e("Location", e.getMessage());
			}
		}
		return items;
	}

	/**
	 * 基于第三方SDK的实现 使用高德地图的SDK
	 *
	 */
	public static HashMap<String, Object> getLocationBySDK(Context context) {

		final HashMap<String, Object> items = new HashMap<String, Object>();
		AMapLocationListener mAMapLocationListener = null;
		// amap
		LocationManagerProxy aMapManager = LocationManagerProxy
				.getInstance(context);
		/*
		 * mAMapLocManager.setGpsEnable(false);
		 * 1.0.2版本新增方法，设置true表示混合定位中包含gps定位，false表示纯网络定位，默认是true Location
		 * API定位采用GPS和网络混合定位方式
		 * ，第一个参数是定位provider，第二个参数时间最短是2000毫秒，第三个参数距离间隔单位是米，第四个参数是定位监听者
		 */
		aMapManager.requestLocationUpdates(LocationProviderProxy.AMapNetwork,2000, 10, mAMapLocationListener);
		mAMapLocationListener = new AMapLocationListener() {

			@Override
			public void onStatusChanged(String provider, int status,
										Bundle extras) {

			}

			@Override
			public void onProviderEnabled(String provider) {

			}

			@Override
			public void onProviderDisabled(String provider) {

			}

			@Override
			public void onLocationChanged(Location location) {

			}

			@SuppressWarnings("deprecation")
			@Override
			public void onLocationChanged(AMapLocation location) {
				if (location != null) {
					Double geoLat = location.getLatitude();
					Double geoLng = location.getLongitude();
					String cityCode = "";
					String desc = "";
					Bundle locBundle = location.getExtras();
					if (locBundle != null) {
						cityCode = locBundle.getString("citycode");
						desc = locBundle.getString("desc");
					}
					String str = ("定位成功:(" + geoLng + "," + geoLat + ")"
							+ "\n精    度    :" + location.getAccuracy() + "米"
							+ "\n定位方式:" + location.getProvider() + "\n定位时间:"
							+ new Date(location.getTime()).toLocaleString()
							+ "\n城市编码:" + cityCode + "\n位置描述:" + desc + "\n省:"
							+ location.getProvince() + "\n市:" + location.getCity()
							+ "\n区(县):" + location.getDistrict() + "\n区域编码:"
							+ location.getAdCode());
					Log.d("LocationUtil", "高德地图："+str);
					items.put("City", location.getCity());
					items.put("Pinpoint", location.getDistrict());
				}
			}
		};

		return items;
	}


	/**
	 * 根据经纬度获取地理位置
	 *
	 * @param 上下文对象
	 * @param 经度
	 * @param 纬度
	 */
	private static HashMap<String, Object> getLoactionByLatitudeandLongitude(
			Context context, double latitude, double longitude) {
		Geocoder geocoder = new Geocoder(context, Locale.getDefault());
		HashMap<String, Object> items = new HashMap<String, Object>();
		try {
			List<Address> list = geocoder.getFromLocation(latitude, longitude,
					1);
			if (list.size() > 0) {
				Address address = list.get(0);
				int index = (int) (Math.random()
						* address.getMaxAddressLineIndex());
				if (null != address.getLocality()) {
					items.put("City", address.getLocality());
				}
				if (null != address.getAddressLine(index)) {
					items.put("Pinpoint", address.getAddressLine(index));
				}
				// StringBuilder builder=new StringBuilder();
				// builder.append(address.getAddressLine(index)).append("\n");
				// builder.append(address.getLocality());
				// for(int i=0;i<address.getMaxAddressLineIndex();i++){
				// builder.append(address.getAddressLine(i)).append("\n");
				// }
				// builder.append(address.getPostalCode()).append("_");
				// builder.append(address.getCountryCode()).append("_");
				// builder.append(address.getCountryName()).append("_");
				return items;
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
}
