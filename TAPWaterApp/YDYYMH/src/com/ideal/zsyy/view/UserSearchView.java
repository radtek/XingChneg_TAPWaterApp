package com.ideal.zsyy.view;

import com.search.wtapp.R;
import com.ideal.zsyy.service.PreferencesService;
import android.content.Context;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.TextView;

public class UserSearchView extends BaseLayout {

	private TextView tv_title;
	private WebView webUserSearch;
	PreferencesService preferencesService = null;
	private Context _cContext;

	public UserSearchView(Context context) {
		super(context, R.layout.w_cb_search_new);
		this._cContext = context;
		// TODO Auto-generated constructor stub
		this.initView();
		initData();
	}

	private void initView() {
		tv_title=(TextView)findViewById(R.id.tv_title);
		webUserSearch=(WebView)findViewById(R.id.web_main);
		webUserSearch.getSettings().setJavaScriptEnabled(true);
		webUserSearch.loadUrl("http://m.hujiang.com/");
		
		webUserSearch.setWebViewClient(new WebViewClient(){
		      @Override
		      public boolean shouldOverrideUrlLoading(WebView view, String url) {
		          view.loadUrl(url);
		          return true;
		      }
		  });
	}

	private void initData() {
		tv_title.setText("用户查询");
	}
}
