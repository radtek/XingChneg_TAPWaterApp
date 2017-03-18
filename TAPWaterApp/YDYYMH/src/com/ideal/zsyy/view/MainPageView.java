package com.ideal.zsyy.view;

import com.search.wtapp.R;
import com.ideal.zsyy.activity.LoginActivity;
import com.ideal.zsyy.entity.ActionItem;
import com.ideal.zsyy.view.TitlePopup.OnItemOnClickListener;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.LinearLayout;

public class MainPageView extends BaseLayout {

	private LinearLayout img_top_menu;
	private Context vContext;
	private TitlePopup popup;
	private WebView webViewMain;

	public MainPageView(Context context) {
		super(context, R.layout.w_main);
		this.vContext = context;
		popup = new TitlePopup(vContext);
		webViewMain = (WebView) findViewById(R.id.web_main);
		this.initData();
	}

	private void initData() {
		popup.addAction(new ActionItem(getResources().getDrawable(R.drawable.wcb_switch_24), "退出登录", 1));
		webViewMain.getSettings().setJavaScriptEnabled(true);
		webViewMain.loadUrl("http://m.baidu.com/");
		
		webViewMain.setWebViewClient(new WebViewClient(){
		      @Override
		      public boolean shouldOverrideUrlLoading(WebView view, String url) {
		          view.loadUrl(url);
		          return true;
		      }
		  });
		
		popup.setItemOnClickListener(new OnItemOnClickListener() {

			@Override
			public void onItemClick(ActionItem item, int position) {
				// TODO Auto-generated method stub
				if (item.operateId == 1) {
					LogOut();
				}
			}
		});
	}

	private void LogOut() {
		Intent nextIntent = new Intent(this.vContext, LoginActivity.class);
		this.vContext.startActivity(nextIntent);
		Activity activity = (Activity) this.vContext;
		if (activity != null) {
			activity.finish();
		}
	}
}
