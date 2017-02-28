package com.ideal2.networkStatus;

import com.ideal2.components.MyToast;

import android.R.integer;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo.State;
import android.util.Log;
import android.widget.Toast;

public class NetworkStatus{

	private final static String TAG = "NetworkStatusReceiver";
	private OnNetworkStatusListener onNetworkStatusListener;;
	private Context mContext;
	private NetworkStatusConfig networkStatusConfig;
	public NetworkStatus(Context context){
		this.mContext = context;
		networkStatusConfig =NetworkStatusConfig.create(mContext);
	} 
	
	
	public OnNetworkStatusListener getOnNetworkStatusListener() {
		return onNetworkStatusListener;
	}

	public void setOnNetworkStatusListener(
			OnNetworkStatusListener onNetworkStatusListener) {
		this.onNetworkStatusListener = onNetworkStatusListener;
	}
	
	public void doNetworkStatus(){
		doNetworkStatus(null);
	}
	public void doNetworkStatus(OnNetworkStatusListener onNetworkStatusListener) {
		Log.e(TAG, "����״̬�ı�");
		boolean success = false;

		// ����������ӷ���
		ConnectivityManager connManager = (ConnectivityManager) mContext
				.getSystemService(mContext.CONNECTIVITY_SERVICE);
		// State state = connManager.getActiveNetworkInfo().getState();
		State state = connManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI)
				.getState(); // ��ȡ��������״̬
		
		Log.d(TAG, "����:" + State.CONNECTED);
		if (State.CONNECTED == state) { // �ж��Ƿ�����ʹ��WIFI����
			Log.d(TAG, "������ʹ��WIFI����" + state+"��ַ��"+networkStatusConfig.getNetworkConfig(NetworkStatusConfig.WIFI_CLIENT_URL));
		
			if(null!=onNetworkStatusListener){
				SharedPreferences sharedPreference = mContext.getSharedPreferences(NetworkStatusConfig.FILENAME_NETWORKSTATUSCONFIG, Context.MODE_PRIVATE);
				onNetworkStatusListener.onWifi(sharedPreference.getString(NetworkStatusConfig.WIFI_CLIENT_URL, ""),sharedPreference.getString(NetworkStatusConfig.WIFI_CLIENT_XMLNAME, "")
						,sharedPreference.getString(NetworkStatusConfig.WIFI_IDEAL_URL, ""),sharedPreference.getString(NetworkStatusConfig.WIFI_IDEAL_XMLNAME, ""));
			}
//			Toast.makeText(context, "������ʹ��WIFI����", Toast.LENGTH_LONG).show();
			success = true;
		} else {
			state = connManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE)
					.getState(); // ��ȡ��������״̬
			if (State.CONNECTED == state) { // �ж��Ƿ�����ʹ��GPRS����
				Log.d(TAG, "������ʹ��GPRS����" + state+"��ַ��"+networkStatusConfig.getNetworkConfig(NetworkStatusConfig.GPRS_CLIENT_URL));
				if(null!=onNetworkStatusListener){
					SharedPreferences sharedPreference = mContext.getSharedPreferences(NetworkStatusConfig.FILENAME_NETWORKSTATUSCONFIG, Context.MODE_PRIVATE);
					onNetworkStatusListener.onGrps(sharedPreference.getString(NetworkStatusConfig.GPRS_CLIENT_URL, ""),sharedPreference.getString(NetworkStatusConfig.GPRS_CLIENT_XMLNAME, "")
							,sharedPreference.getString(NetworkStatusConfig.GPRS_IDEAL_URL, ""),sharedPreference.getString(NetworkStatusConfig.GPRS_IDEAL_XMLNAME, ""));
				}
//				Toast.makeText(context, "������ʹ��GPRS����", Toast.LENGTH_LONG).show();
				success = true;
			}
		}

		if (!success) {
			Toast.makeText(mContext, "���������������ж�", Toast.LENGTH_LONG).show();
		}
	}
	
	public interface OnNetworkStatusListener{
		public void onWifi(String userUrl,String userUrlname,String idealUrl,String idealName);
		public void onGrps(String userUrl,String userUrlname,String idealUrl,String idealName);
	}
	

}
