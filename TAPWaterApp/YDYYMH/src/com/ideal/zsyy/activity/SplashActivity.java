package com.ideal.zsyy.activity;

import java.util.UUID;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.search.wtapp.R;
import com.ideal.wdm.tools.DataCache;
import com.ideal.zsyy.service.PreferencesService;
import com.ideal.zsyy.utils.DeviceHelper;

public class SplashActivity extends Activity {

	private final int SPLASH_DISPLAY_LENGHT = 1000; // 延迟三秒
	private PreferencesService preferencesService;
	private Button btn_reg = null;
	String deviceId = "";
	private Dialog dialog;
	private EditText et_ip = null;
	private DataCache dCache;
	private String []accounts;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);
		getWindow().requestFeature(Window.FEATURE_PROGRESS); // 去标题栏
		super.onCreate(savedInstanceState);
		setContentView(R.layout.splash);
		preferencesService = new PreferencesService(getApplicationContext());
		dCache = DataCache.getCache(SplashActivity.this);
		deviceId=preferencesService.GetDeviceId();
		if(deviceId==null||deviceId=="")
		{
			deviceId = DeviceHelper.ObtainDeviceID(SplashActivity.this);
			if(deviceId==null||deviceId=="")
			{
				deviceId=UUID.randomUUID().toString().replace("-","");
			}
		}
		btn_reg = (Button) findViewById(R.id.btn_reg);
		btn_reg.setVisibility(View.GONE);
		PostDealy("");
	}

	
	private void PostDealy(final String userName) {
		new Handler().postDelayed(new Runnable() {
			public void run() {
				
				Intent intent = new Intent(SplashActivity.this,
						LoginActivity.class);
				intent.putExtra("userName", userName);
				startActivity(intent);
				SplashActivity.this.finish();
			}
		}, SPLASH_DISPLAY_LENGHT);
	}
	
	private void inputIP() {
		dialog = new Dialog(SplashActivity.this, R.style.dialog);
		dialog.setContentView(R.layout.ip_dialog);
		Button btn_ok = (Button) dialog.findViewById(R.id.ok);
		Button btn_cancel = (Button) dialog.findViewById(R.id.cancel);
		et_ip = (EditText) dialog.findViewById(R.id.et_ip);
		et_ip.setText(dCache.getServerIP());
		btn_ok.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				
				String ip = et_ip.getText().toString().trim();
				String regex = "^(1\\d{2}|2[0-4]\\d|25[0-5]|[1-9]\\d|[1-9])\\.(1\\d{2}|2[0-4]\\d|25[0-5]|[1-9]\\d|\\d)\\.(1\\d{2}|2[0-4]\\d|25[0-5]|[1-9]\\d|\\d)\\.(1\\d{2}|2[0-4]\\d|25[0-5]|[1-9]\\d|\\d)$";
				String regexPort = "^(1\\d{2}|2[0-4]\\d|25[0-5]|[1-9]\\d|[1-9])\\.(1\\d{2}|2[0-4]\\d|25[0-5]|[1-9]\\d|\\d)\\.(1\\d{2}|2[0-4]\\d|25[0-5]|[1-9]\\d|\\d)\\.(1\\d{2}|2[0-4]\\d|25[0-5]|[1-9]\\d|\\d):(\\d{1,5})$";
				boolean ipMatch = ip.matches(regex) || ip.matches(regexPort);
				if (ipMatch || ip.startsWith("http://")
						|| ip.startsWith("https://")) {
					String url = ip;
					if (ipMatch) {
						url = "http://" + ip;
					}
					url = url + "/service/AndroidService.ashx";
					DataCache dCache = DataCache.getCache(SplashActivity.this);
					dCache.setServerIP(ip);
					dCache.setUrl(url);
					dCache.commit(SplashActivity.this);
					// mHandler.sendEmptyMessage(1);
					SplashActivity.this.dialog.dismiss();
				} else {
					Toast.makeText(SplashActivity.this, R.string.ip_wrongful,
							Toast.LENGTH_LONG).show();
				}
			}
		});

		btn_cancel.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				SplashActivity.this.dialog.dismiss();
				// mHandler.sendEmptyMessageDelayed(0, 500);
			}
		});

		this.dialog.show();
	}
}
