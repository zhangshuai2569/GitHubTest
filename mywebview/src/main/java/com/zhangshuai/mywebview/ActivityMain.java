package com.zhangshuai.mywebview;

import android.graphics.Bitmap;
import android.net.http.SslError;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Layout;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.webkit.ClientCertRequest;
import android.webkit.HttpAuthHandler;
import android.webkit.RenderProcessGoneDetail;
import android.webkit.SafeBrowsingResponse;
import android.webkit.SslErrorHandler;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebResourceResponse;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.EditText;

/**
 * @Description: <br>
 * <p>
 * 〈webview 的学习〉
 * @Author: 张帅
 * @CreateDate: 2019/2/17 21:08
 * @UpdateUser: 张帅
 * @UpdateDate: 2019/2/17 21:08
 * @UpdateRemark: 修改内容
 * @Version: 1.0
 */

public class ActivityMain extends AppCompatActivity {
	/**
	 * 文本框网络地址
	 */
	private EditText et_url;
	/**
	 * webView 控件
	 */
	private WebView webview;
	/**
	 * webview加载的网络地址
	 */
	private String DEFAULT_URL = "http://192.168.50.1:8080/";
	/**
	 * webview 设置类
	 */
	private WebSettings settings;
	private String url;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		et_url = (EditText) findViewById(R.id.et_url);
		webview = (WebView) findViewById(R.id.webview);
		init();
	}

	/**
	 * 功能描述: <br>
	 * 〈初始化webview设置〉
	 *
	 * @param
	 * @return:
	 * @since: 1.0.0
	 * @Author: 张帅
	 * @Date: 2019/2/17 21:58
	 */

	private void init() {
		//		获取webview的设置封装类
		settings = webview.getSettings();
		//		启动js脚本的支持
		settings.setJavaScriptEnabled(true);
		//支持缩放
		settings.setSupportZoom(true);
		//		设置缩放方式
		settings.setDefaultZoom(WebSettings.ZoomDensity.FAR);
		//		启用插件支持（4.0以下版本）
		//		settings.setpluginsEnable(true);
		//		设置webview内容怎么显示，当前设置为单列
		//		settings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
		//设置缩放控制（显示缩放按钮）
		//		settings.setBuiltInZoomControls(true);
		//		webview触摸事件
		webview.setOnTouchListener(new View.OnTouchListener() {
			@Override
			public boolean onTouch(View v, MotionEvent event) {

				switch (event.getAction()) {
					case MotionEvent.ACTION_DOWN:
						Log.e(getClass().getName().toString(), "按下了");
						break;
					case MotionEvent.ACTION_MOVE:
						Log.e(getClass().getName().toString(), "移动中");
						break;
					case MotionEvent.ACTION_UP:
						Log.e(getClass().getName().toString(), "抬起了");
						break;
				}

				return false;//返回true事件被拦截，无法向下传递。界面无法滑动
			}
		});

		//webview 客户端  【防止网页在手机浏览器中打开】
		webview.setWebViewClient(new WebViewClient(){
			@Override
			public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
				Log.e(getClass().getName().toString(), "shouldOverrideUrlLoading"+url);
				if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
					//代码逻辑
					view.loadUrl(request.getUrl().toString());
				}

				return super.shouldOverrideUrlLoading(view, request);
			}

			/**
			 * 开始加载页面(可以显示一个进度条)
			 * @param view
			 * @param url
			 * @param favicon
			 */

			@Override
			public void onPageStarted(WebView view, String url, Bitmap favicon) {
				Log.e(getClass().getName().toString(), "onPageStarted");
				super.onPageStarted(view, url, favicon);
			}

			/**
			 * 页面加载完成（可以将进度条关闭）
			 * @param view
			 * @param url
			 */
			@Override
			public void onPageFinished(WebView view, String url) {
				Log.e(getClass().getName().toString(), "onPageFinished");
				super.onPageFinished(view, url);
			}

			/**
			 * 加载网页资源
			 * @param view
			 * @param url
			 */
			@Override
			public void onLoadResource(WebView view, String url) {
				Log.e(getClass().getName().toString(), "onLoadResource");
				super.onLoadResource(view, url);
			}

			@Override
			public void onPageCommitVisible(WebView view, String url) {
				Log.e(getClass().getName().toString(), "onPageCommitVisible");
				super.onPageCommitVisible(view, url);
			}

			@Override
			public void onReceivedError(WebView view, WebResourceRequest request, WebResourceError error) {
				Log.e(getClass().getName().toString(), "onReceivedError");
				super.onReceivedError(view, request, error);
			}

			@Override
			public void onReceivedHttpError(WebView view, WebResourceRequest request, WebResourceResponse errorResponse) {
				Log.e(getClass().getName().toString(), "onReceivedHttpError");
				super.onReceivedHttpError(view, request, errorResponse);
			}

			@Override
			public void onFormResubmission(WebView view, Message dontResend, Message resend) {
				Log.e(getClass().getName().toString(), "onFormResubmission");
				super.onFormResubmission(view, dontResend, resend);
			}

			/**
			 * 更新浏览的历史记录
			 * @param view
			 * @param url
			 * @param isReload
			 */
			@Override
			public void doUpdateVisitedHistory(WebView view, String url, boolean isReload) {
				Log.e(getClass().getName().toString(), "doUpdateVisitedHistory");
				super.doUpdateVisitedHistory(view, url, isReload);
			}

			@Override
			public void onReceivedSslError(WebView view, SslErrorHandler handler, SslError error) {
				Log.e(getClass().getName().toString(), "onReceivedSslError");
				super.onReceivedSslError(view, handler, error);
			}

			@Override
			public void onReceivedClientCertRequest(WebView view, ClientCertRequest request) {
				Log.e(getClass().getName().toString(), "onReceivedClientCertRequest");
				super.onReceivedClientCertRequest(view, request);
			}

			@Override
			public void onReceivedHttpAuthRequest(WebView view, HttpAuthHandler handler, String host, String realm) {
				Log.e(getClass().getName().toString(), "onReceivedHttpAuthRequest");
				super.onReceivedHttpAuthRequest(view, handler, host, realm);
			}

			@Override
			public boolean shouldOverrideKeyEvent(WebView view, KeyEvent event) {
				Log.e(getClass().getName().toString(), "shouldOverrideKeyEvent");
				return super.shouldOverrideKeyEvent(view, event);
			}

			@Override
			public void onUnhandledKeyEvent(WebView view, KeyEvent event) {
				Log.e(getClass().getName().toString(), "onUnhandledKeyEvent");
				super.onUnhandledKeyEvent(view, event);
			}

			/**
			 * 页面缩放发生变化时调用
			 * @param view
			 * @param oldScale
			 * @param newScale
			 */
			@Override
			public void onScaleChanged(WebView view, float oldScale, float newScale) {
				Log.e(getClass().getName().toString(), "onScaleChanged");
				super.onScaleChanged(view, oldScale, newScale);
			}

			@Override
			public void onReceivedLoginRequest(WebView view, String realm, @Nullable String account, String args) {
				Log.e(getClass().getName().toString(), "onReceivedLoginRequest");
				super.onReceivedLoginRequest(view, realm, account, args);
			}

			@Override
			public boolean onRenderProcessGone(WebView view, RenderProcessGoneDetail detail) {
				Log.e(getClass().getName().toString(), "onRenderProcessGone");
				return super.onRenderProcessGone(view, detail);
			}

			@Override
			public void onSafeBrowsingHit(WebView view, WebResourceRequest request, int threatType, SafeBrowsingResponse callback) {
				Log.e(getClass().getName().toString(), "onSafeBrowsingHit");
				super.onSafeBrowsingHit(view, request, threatType, callback);
			}
		});
	}

	public void OnClick(View view) {
		url = et_url.getText().toString().trim();
		if (TextUtils.isEmpty(url)) {
			url = DEFAULT_URL;
		}
		/**加载地址*/
		webview.loadUrl(url);
		switch (view.getId()) {
			case R.id.btn_go:

				break;
		}
	}
}
