package com.ideal.zsyy.view;

import com.ideal.zsyy.service.PreferencesService;
import com.search.wtapp.R;

import android.content.Context;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class WCBMenuView extends BaseLayout {

	private Context _context;
	private PreferencesService preService;
	private WebView webUserSearch;
	public WCBMenuView(Context context) {
		super(context,R.layout.w_cb_menu);
		// TODO Auto-generated constructor stub
		this._context=context;
		this.initView();
		preService=new PreferencesService(_context);
	}
	
	private void initView()
	{
		webUserSearch=(WebView)findViewById(R.id.web_main);
		webUserSearch.getSettings().setJavaScriptEnabled(true);
		webUserSearch.loadUrl("http://xw.qq.com/index.htm");
		
		webUserSearch.setWebViewClient(new WebViewClient(){
		      @Override
		      public boolean shouldOverrideUrlLoading(WebView view, String url) {
		          view.loadUrl(url);
		          return true;
		      }
		  });
	}
}
