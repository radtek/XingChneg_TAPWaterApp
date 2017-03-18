package com.ideal.zsyy.view;

import com.search.wtapp.R;
import android.content.Context;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class WMapFjyhView extends BaseLayout {

	private Context context;
	boolean isFirstLoc = true;// 是否首次定位
	private WebView webUserSearch;
	public WMapFjyhView(Context context) {
		super(context, R.layout.w_map_fjyh);
		// TODO Auto-generated constructor stub
		this.context = context;
		
		webUserSearch=(WebView)findViewById(R.id.web_main);
		webUserSearch.getSettings().setJavaScriptEnabled(true);
		webUserSearch.loadUrl("https://sina.cn/?from=web");
		
		webUserSearch.setWebViewClient(new WebViewClient(){
		      @Override
		      public boolean shouldOverrideUrlLoading(WebView view, String url) {
		          view.loadUrl(url);
		          return true;
		      }
		  });
	}
}
