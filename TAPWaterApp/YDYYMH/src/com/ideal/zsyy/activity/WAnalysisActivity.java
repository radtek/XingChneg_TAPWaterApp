package com.ideal.zsyy.activity;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import com.jijiang.wtapp.R;
import com.ideal.zsyy.db.WdbManager;
import com.ideal.zsyy.entity.ActionItem;
import com.ideal.zsyy.entity.ChargeInfoItem;
import com.ideal.zsyy.entity.WAnalysisItem;
import com.ideal.zsyy.entity.WBBItem;
import com.ideal.zsyy.request.WChargeInfoReq;
import com.ideal.zsyy.response.WChargeInfoRes;
import com.ideal.zsyy.service.BluetoothPrintService;
import com.ideal.zsyy.service.BluetoothService;
import com.ideal.zsyy.service.PreferencesService;
import com.ideal.zsyy.service.BluetoothService.bondFinishListener;
import com.ideal.zsyy.view.TitlePopup;
import com.ideal.zsyy.view.TitlePopup.OnItemOnClickListener;
import com.ideal2.base.gson.GsonServlet;
import com.ideal2.base.gson.GsonServlet.OnResponseEndListening;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemSelectedListener;

public class WAnalysisActivity extends Activity {

	private Spinner spinnerBBH = null;
	private TextView tv_yhzs = null, tv_ycyhs = null, tv_wcyhs = null, tv_sbgz = null, tv_zsl = null, tv_zfy = null,
			tv_ysje = null, tv_wsje = null, tv_ysfyhs = null, tv_ysfsl = null;
	private RadioGroup rgDays = null;
	private RadioButton rb_month;
	private Button btn_back = null;
	private Map<String, Object>userInfo;
	private WdbManager wManager;
	private TitlePopup titlePopup;
	private LinearLayout ll_top_menu;
	private BluetoothService bService;
	private BluetoothPrintService printService;
	private String[] printData;
	private String PrTotalCharge="",PrTotalUser="",PrTotalUnUser="",PrTotalWater="",PrDayCharge="",PrDayUser="",PrDayUnUser="",PrArea="";
	private int CBMonth=0;
	private String ysje="",ysfyhs="",ysfsl="";
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.w_static_analysis);
		wManager = new WdbManager(WAnalysisActivity.this);
		bService = new BluetoothService(this);
		PreferencesService pService=new PreferencesService(WAnalysisActivity.this);
		userInfo=pService.getLoginInfo();
		titlePopup = new TitlePopup(WAnalysisActivity.this);
		titlePopup.addAction(new ActionItem(getResources().getDrawable(R.drawable.wcb_print), "打印小票", 1));
		titlePopup.addAction(new ActionItem(getResources().getDrawable(R.drawable.wcb_update_24), "刷新数据", 2));
		this.initView();
		this.setEventListener();
	}

	private void initView() {
		spinnerBBH = (Spinner) findViewById(R.id.sp_bbh);
		tv_yhzs = (TextView) findViewById(R.id.tv_yhzs);
		tv_ycyhs = (TextView) findViewById(R.id.tv_ycyhs);
		tv_wcyhs = (TextView) findViewById(R.id.tv_wcyhs);
		tv_sbgz = (TextView) findViewById(R.id.tv_sbgz);
		tv_zsl = (TextView) findViewById(R.id.tv_zsl);
		tv_zfy = (TextView) findViewById(R.id.tv_zfy);
		tv_ysje = (TextView) findViewById(R.id.tv_ysje);
		tv_wsje = (TextView) findViewById(R.id.tv_wsje);
		tv_ysfyhs = (TextView) findViewById(R.id.tv_ysfyhs);
		rgDays = (RadioGroup) findViewById(R.id.rg_search_type);
		btn_back = (Button) findViewById(R.id.btn_back);
		rb_month = (RadioButton) findViewById(R.id.rb_month);
		tv_ysfsl = (TextView) findViewById(R.id.tv_ysfsl);
		ll_top_menu = (LinearLayout) findViewById(R.id.ll_top_menu);

		if (ll_top_menu != null) {
			ll_top_menu.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					titlePopup.show(v);
				}
			});
		}

		titlePopup.setItemOnClickListener(new OnItemOnClickListener() {

			@Override
			public void onItemClick(ActionItem item, int position) {
				switch (item.operateId) {
				case 1:
					BluePrint();
					break;
				case 2:
					UpdateChargeInfo();
					break;
				}
			}
		});
	}

	private void UpdateChargeInfo()
	{
		WChargeInfoReq req = new WChargeInfoReq();
		req.setLoginid(userInfo.get("use_id").toString());
		req.setOperType("22");
		req.setTJType(rb_month.isChecked());

		GsonServlet<WChargeInfoReq, WChargeInfoRes> gServlet = new GsonServlet<WChargeInfoReq, WChargeInfoRes>(this);

		gServlet.request(req, WChargeInfoRes.class);
		gServlet.setOnResponseEndListening(new OnResponseEndListening<WChargeInfoReq, WChargeInfoRes>() {
			@Override
			public void onResponseEnd(WChargeInfoReq commonReq, WChargeInfoRes commonRes, boolean result,
					String errmsg, int responseCode) {
			}

			@Override
			public void onResponseEndSuccess(WChargeInfoReq commonReq, WChargeInfoRes commonRes, String errmsg,
					int responseCode) {
				
			if (commonRes != null ) {
				commonRes.getChargeCount();
				if (tv_ysje != null) {
					tv_ysje.setText(ysje+"/"+commonRes.getChargeFee());
				}
				if (tv_ysfyhs != null) {
					tv_ysfyhs.setText(ysfyhs+"/"+commonRes.getChargeCount());
				}
				if (tv_ysfsl != null) {
					tv_ysfsl.setText(ysfsl+"/"+commonRes.getChargeWater());
				}
			}
			else
			{
				if (tv_ysje != null) {
					tv_ysje.setText(ysje+"/--");
				}
				if (tv_ysfyhs != null) {
					tv_ysfyhs.setText(ysfyhs+"/--");
				}
				if (tv_ysfsl != null) {
					tv_ysfsl.setText(ysfsl+"/--");
				}
			}
				
				Toast.makeText(WAnalysisActivity.this, "更新成功！", Toast.LENGTH_SHORT).show();
			}

			@Override
			public void onResponseEndErr(WChargeInfoReq commonReq, WChargeInfoRes commonRes, String errmsg,
					int responseCode) {
				Toast.makeText(getApplicationContext(), errmsg, Toast.LENGTH_SHORT).show();

			}
		});
	}
	
	// 蓝牙打印机打印
	private void BluePrint() {
		printData = GetPrintData();
		if (!bService.isOpen()) {
			bService.open();
		}

		if (printService == null) {
			printService = new BluetoothPrintService(WAnalysisActivity.this);
		}
		if (printService.connect()) {
			if (printService.send(printData) == 0) {
				handler.sendEmptyMessage(1);
			}
		} else {
			bService.searchDevice();
		}
		bService.setBonListener(new bondFinishListener() {

			@Override
			public void doOperate() {
				if (printService == null) {
					printService = new BluetoothPrintService(WAnalysisActivity.this);
				}
				if (printService.connect()) {
					if (printService.send(printData) == 0) {
						handler.sendEmptyMessage(1);
					}
				}
			}
		});
	}

	private Handler handler = new Handler() {

		@Override
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			switch (msg.what) {
			case 1:

				break;
			}
		}

	};

	private String[] GetPrintData() {
		if(PrDayCharge.isEmpty())
		{
			WAnalysisItem wItem = new WAnalysisItem();
			wItem = wManager.GetAnalysisItemByDay("0");
			PrDayCharge=wItem.getYsje();
			PrDayUser=wItem.getYsfyhs();
			PrDayUnUser=wItem.getYcb();
		}
		Date currDate = new Date();
		List<String> arrList = new ArrayList<String>();
		String strDate = new SimpleDateFormat("yyyy年MM月dd日  HH:mm:ss ").format(currDate);
		arrList.add("\n兴城市自来水公司");
		arrList.add("抄表统计凭条\n");
		arrList.add("\n\r");
		arrList.add("\n\r");
		arrList.add("抄表员："+userInfo.get("userName").toString()+"\n");
		arrList.add("抄表月份："+CBMonth+" 月\n");
		arrList.add("用户总数："+tv_yhzs.getText()+"\n");
		arrList.add("打印时间："+strDate+"\n");
		arrList.add("\n----------------------\n");
		arrList.add("本日收费："+PrDayCharge+"\n");
		arrList.add("收费用户："+PrDayUser+"\n");
		arrList.add("抄表用户："+PrDayUnUser+"\n");
		arrList.add("\n----------------------\n");
		arrList.add("本月收费："+PrTotalCharge+"\n");
		arrList.add("收费用户："+PrTotalUser+"\n");
		arrList.add("抄表用户："+PrTotalUnUser+"\n");
		arrList.add("总水量："+PrTotalWater+"\n");
		arrList.add("\n\r");
		arrList.add("\n----------------------\n");
		arrList.add("\n\r");
		return (String[]) arrList.toArray(new String[arrList.size()]);
	}

	// 初始化事件监听
	private void setEventListener() {

		if (spinnerBBH != null) {
			List<WBBItem> bbList = wManager.GetBiaoBenInfo();
			if (bbList != null && bbList.size() > 0) {
				CBMonth=bbList.get(0).getCBMonth();
				WBBItem bbItem = new WBBItem();
				bbItem.setNoteNo("全部");
				bbItem.setBId(0);
				bbList.add(0, bbItem);
			}
			ArrayAdapter<WBBItem> adapter = new ArrayAdapter<WBBItem>(this, android.R.layout.simple_spinner_item,
					bbList);
			adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
			spinnerBBH.setAdapter(adapter);
			spinnerBBH.setOnItemSelectedListener(new OnItemSelectedListener() {

				@Override
				public void onItemSelected(AdapterView<?> arg0, View arg1, int position, long id) {
					GetAnaResult();
				}

				@Override
				public void onNothingSelected(AdapterView<?> arg0) {

				}
			});

			if (rgDays != null) {
				rgDays.setOnCheckedChangeListener(new OnCheckedChangeListener() {

					@Override
					public void onCheckedChanged(RadioGroup group, int checkedId) {
						GetAnaResult();
					}
				});
			}
		}

		if (rgDays != null) {
			rgDays.setOnCheckedChangeListener(new OnCheckedChangeListener() {

				@Override
				public void onCheckedChanged(RadioGroup group, int checkedId) {
					GetAnaResult();
				}
			});
		}

		if (btn_back != null) {
			btn_back.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					finish();
				}
			});
		}
	}

	private void GetAnaResult() {
		String noteNO = "";
		boolean isMonthCheck = false;
		WAnalysisItem wItem = new WAnalysisItem();
		if (this.spinnerBBH != null) {
			WBBItem bbitem = (WBBItem) this.spinnerBBH.getSelectedItem();
			noteNO = bbitem.getBId() == 0 ? "0" : bbitem.getNoteNo();
		}
		if (rb_month != null) {
			isMonthCheck = rb_month.isChecked();
		}
		if (isMonthCheck) {
			wItem = wManager.GetAnalysisItemByMonth(noteNO);
			ysje=PrTotalCharge=wItem.getYsje();
			ysfyhs=PrTotalUser=wItem.getYsfyhs();
			PrTotalUnUser=wItem.getYcb();
			ysfsl=PrTotalWater=wItem.getYsfsl();
			
		} else {
			wItem = wManager.GetAnalysisItemByDay(noteNO);
			ysje=PrDayCharge=wItem.getYsje();
			ysfyhs=PrDayUser=wItem.getYsfyhs();
			PrDayUnUser=wItem.getYcb();
			ysfsl=wItem.getYsfsl();
		}
		if (wItem != null) {
			if (tv_yhzs != null) {
				tv_yhzs.setText(wItem.getTotle());
			}
			if (tv_ycyhs != null) {
				tv_ycyhs.setText(wItem.getYcb());
			}
			if (tv_wcyhs != null) {
				tv_wcyhs.setText(wItem.getWcb());
			}
			if (tv_sbgz != null) {
				tv_sbgz.setText(wItem.getSbgz());
			}
			if (tv_zsl != null) {
				tv_zsl.setText(wItem.getZsl());
			}
			if (tv_zfy != null) {
				tv_zfy.setText(wItem.getZfy());
			}
//			if (tv_ysje != null) {
//				tv_ysje.setText(wItem.getYsje());
//			}
			if (tv_wsje != null) {
				tv_wsje.setText(wItem.getWsje());
			}
//			if (tv_ysfyhs != null) {
//				tv_ysfyhs.setText(wItem.getYsfyhs());
//			}
//			if (tv_ysfsl != null) {
//				tv_ysfsl.setText(wItem.getYsfsl());
//			}
		}
		UpdateChargeInfo();
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		if (wManager != null) {
			wManager.closeDB();
		}
		if (bService != null) {
			bService.unRegisterBlue();
		}
		if (printService != null) {
			printService.disconnect();
		}
	}

}
