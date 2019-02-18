package com.zhangshuai.mywebview;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.net.Uri;
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
import android.webkit.ConsoleMessage;
import android.webkit.GeolocationPermissions;
import android.webkit.HttpAuthHandler;
import android.webkit.JsPromptResult;
import android.webkit.JsResult;
import android.webkit.PermissionRequest;
import android.webkit.RenderProcessGoneDetail;
import android.webkit.SafeBrowsingResponse;
import android.webkit.SslErrorHandler;
import android.webkit.ValueCallback;
import android.webkit.WebBackForwardList;
import android.webkit.WebChromeClient;
import android.webkit.WebHistoryItem;
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
//	private String DEFAULT_URL = "http://192.168.50.1:8080/";
	private String DEFAULT_URL = "file:////android_asset/sample.html";
	/**
	 * webview 设置类
	 */
	private WebSettings settings;
	private String url;

	private ProgressDialog progressDialog;
	private WebBackForwardList webBackForwardList;
	private WebHistoryItem itemAtIndex;

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

	@SuppressLint("JavascriptInterface")
	private void init() {
		progressDialog=new ProgressDialog(this);
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
			/**
			 * 当一个新的url即将加载到当前的WebView中时，让主机应用程序有机会接管控制权。
			 * @param view
			 * @param request
			 * @return
			 */
			@Override
			public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
				Log.e(getClass().getName().toString(), "shouldOverrideUrlLoading"+url);
				if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
					//代码逻辑
					view.loadUrl(request.getUrl().toString());
				}

				return true;
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
				progressDialog.setTitle("拼命加载中.....");
				progressDialog.show();
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
				progressDialog.dismiss();
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

			/**
			 * 通知主机应用程序将不再绘制从上一页导航遗留的WebView内容
			 * @param view
			 * @param url
			 */
			@Override
			public void onPageCommitVisible(WebView view, String url) {
				Log.e(getClass().getName().toString(), "onPageCommitVisible");
				super.onPageCommitVisible(view, url);
			}

			/**
			 * 向主机应用程序报告Web资源加载错误
			 * @param view
			 * @param request
			 * @param error
			 */
			@Override
			public void onReceivedError(WebView view, WebResourceRequest request, WebResourceError error) {
				Log.e(getClass().getName().toString(), "onReceivedError");
				super.onReceivedError(view, request, error);
			}

			/**
			 * 通知主机应用程序在加载资源时从服务器收到HTTP错误。
			 * @param view
			 * @param request
			 * @param errorResponse
			 */
			@Override
			public void onReceivedHttpError(WebView view, WebResourceRequest request, WebResourceResponse errorResponse) {
				Log.e(getClass().getName().toString(), "onReceivedHttpError");
				super.onReceivedHttpError(view, request, errorResponse);
			}

			/**
			 * 作为主机应用程序，如果浏览器应该重新发送数据，因为请求的页面是POST的结果
			 * @param view
			 * @param dontResend
			 * @param resend
			 */
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

			/**
			 * 当网页加载资源过程中发现SSL错误时回调
			 * @param view
			 * @param handler
			 * @param error
			 */
			@Override
			public void onReceivedSslError(WebView view, SslErrorHandler handler, SslError error) {
				Log.e(getClass().getName().toString(), "onReceivedSslError");
				super.onReceivedSslError(view, handler, error);
			}

			/**
			 * 通知主机应用程序来处理SSL客户端证书请求
			 * @param view
			 * @param request
			 */
			@Override
			public void onReceivedClientCertRequest(WebView view, ClientCertRequest request) {
				Log.e(getClass().getName().toString(), "onReceivedClientCertRequest");
				super.onReceivedClientCertRequest(view, request);
			}

			/**
			 * 通知应用程序WebView接收到了一个Http auth的请求
			 * @param view
			 * @param handler
			 * @param host
			 * @param realm
			 */
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

			/**
			 * 通知主机应用程序，一个键未被WebView处理
			 * @param view
			 * @param event
			 */
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

			/**
			 * 通知应用程序有个自动登录帐号过程（通知主程序执行了自动登录请求）
			 * @param view
			 * @param realm
			 * @param account
			 * @param args
			 */
			@Override
			public void onReceivedLoginRequest(WebView view, String realm, @Nullable String account, String args) {
				Log.e(getClass().getName().toString(), "onReceivedLoginRequest");
				super.onReceivedLoginRequest(view, realm, account, args);
			}

			/**
			 * 通知主机应用程序，给定的Webview渲染进程已退出。
			 * @param view
			 * @param detail
			 * @return
			 */
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



		/**webview 第二个客户端*/
		webview.setWebChromeClient(new WebChromeClient(){

			/**
			 * 当前网页加载的进度
			 * @param view
			 * @param newProgress
			 */
			@Override
			public void onProgressChanged(WebView view, int newProgress) {
				super.onProgressChanged(view, newProgress);
			}

			/**
			 * title变化的回调
			 * @param view
			 * @param title
			 */
			@Override
			public void onReceivedTitle(WebView view, String title) {
				super.onReceivedTitle(view, title);
			}

			/**
			 * 图片变化的回调
			 * @param view
			 * @param icon
			 */
			@Override
			public void onReceivedIcon(WebView view, Bitmap icon) {
				super.onReceivedIcon(view, icon);
			}

			/**
			 * 图片资源回调
			 * @param view
			 * @param url
			 * @param precomposed
			 */
			@Override
			public void onReceivedTouchIconUrl(WebView view, String url, boolean precomposed) {
				super.onReceivedTouchIconUrl(view, url, precomposed);
			}

			@Override
			public void onShowCustomView(View view, CustomViewCallback callback) {
				super.onShowCustomView(view, callback);
			}

			@Override
			public void onHideCustomView() {
				super.onHideCustomView();
			}

			@Override
			public boolean onCreateWindow(WebView view, boolean isDialog, boolean isUserGesture, Message resultMsg) {
				return super.onCreateWindow(view, isDialog, isUserGesture, resultMsg);
			}

			/**
			 * webview请求得到focus，发生这个主要是当前webview不是前台状态，是后台webview
			 * @param view
			 */
			@Override
			public void onRequestFocus(WebView view) {
				super.onRequestFocus(view);
			}

			@Override
			public void onCloseWindow(WebView window) {
				super.onCloseWindow(window);
			}

			/**
			 * 覆盖默认的window.alert展示界面
			 * @param view
			 * @param url
			 * @param message
			 * @param result
			 * @return
			 */
			@Override
			public boolean onJsAlert(WebView view, String url, String message, JsResult result) {

				Log.e("onJsAlert",message+"----"+result);

				/*final AlertDialog.Builder builder = new AlertDialog.Builder(
						view.getContext());

				builder.setTitle("对话框").setMessage(message)
						.setPositiveButton("确定", null);
				builder.setOnKeyListener(new DialogInterface.OnKeyListener() {
					public boolean onKey(DialogInterface dialog, int keyCode,
										 KeyEvent event) {
						Log.v("onJsAlert", "keyCode==" + keyCode + "event=" + event);
						return true;
					}
				});
				// 禁止响应按back键的事件
				builder.setCancelable(false);
				AlertDialog dialog = builder.create();
				dialog.show();
				result.confirm();// 因为没有绑定事件，需要强行confirm,否则页面会变黑显示不了内容。*/
//				return true;



				return super.onJsAlert(view, url, message, result);
			}

			/**
			 * 覆盖默认的window.confirm展示界面(带确定和取消按钮的对话框)
			 * @param view
			 * @param url
			 * @param message
			 * @param result
			 * @return
			 */
			@Override
			public boolean onJsConfirm(WebView view, String url, String message, JsResult result) {
				Log.e("onJsConfirm",message+"----"+result);
				return super.onJsConfirm(view, url, message, result);
			}

			/**
			 * 覆盖默认的window.prompt展示界面(带输入框和选择按钮的对话框)
			 * @param view
			 * @param url
			 * @param message
			 * @param defaultValue
			 * @param result
			 * @return
			 */
			@Override
			public boolean onJsPrompt(WebView view, String url, String message, String defaultValue, JsPromptResult result) {
				Log.e("onJsPrompt",message+"----"+result);
				return super.onJsPrompt(view, url, message, defaultValue, result);
			}

			@Override
			public boolean onJsBeforeUnload(WebView view, String url, String message, JsResult result) {
				return super.onJsBeforeUnload(view, url, message, result);
			}

			@Override
			public void onGeolocationPermissionsShowPrompt(String origin, GeolocationPermissions.Callback callback) {
				super.onGeolocationPermissionsShowPrompt(origin, callback);
			}

			@Override
			public void onGeolocationPermissionsHidePrompt() {
				super.onGeolocationPermissionsHidePrompt();
			}

			@Override
			public void onPermissionRequest(PermissionRequest request) {
				super.onPermissionRequest(request);
			}

			@Override
			public void onPermissionRequestCanceled(PermissionRequest request) {
				super.onPermissionRequestCanceled(request);
			}

			/**
			 * 处理了javascript的console的功能，js可以使用console打印调试信息
			 * @param consoleMessage
			 * @return
			 */
			@Override
			public boolean onConsoleMessage(ConsoleMessage consoleMessage) {
				return super.onConsoleMessage(consoleMessage);
			}

			@Nullable
			@Override
			public Bitmap getDefaultVideoPoster() {
				return super.getDefaultVideoPoster();
			}

			@Nullable
			@Override
			public View getVideoLoadingProgressView() {
				return super.getVideoLoadingProgressView();
			}

			@Override
			public void getVisitedHistory(ValueCallback<String[]> callback) {
				super.getVisitedHistory(callback);
			}

			@Override
			public boolean onShowFileChooser(WebView webView, ValueCallback<Uri[]> filePathCallback, FileChooserParams fileChooserParams) {
				return super.onShowFileChooser(webView, filePathCallback, fileChooserParams);
			}
		});

		webview.addJavascriptInterface(new Object(){
			@android.webkit.JavascriptInterface
			public void callFromJs(final String str){
				//通过js中的方法来控制android界面
				runOnUiThread(new Runnable() {
					@Override
					public void run() {
						Log.e("callfromjs",str);
					}
				});

			}
		},"zs");



	}


	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
//		判断当前按下的是返回键，并且webview可以返回，那就执行返回操作
		if (keyCode==KeyEvent.KEYCODE_BACK&&webview.canGoBack()){
				webview.goBack();
				return  true;//如果不返回true，事件就无法起效
		}
		return super.onKeyDown(keyCode, event);
	}

	public void OnClick(View view) {

		switch (view.getId()) {
			case R.id.btn_go://加载网页地址
				url = et_url.getText().toString().trim();
				if (TextUtils.isEmpty(url)) {
					url = DEFAULT_URL;
				}
				/**加载地址*/
				webview.loadUrl(url);
				break;
			case R.id.btn_gethistory://获取网页历史记录
				webBackForwardList = webview.copyBackForwardList();//获取历史记录
				for (int i = 0; i < webBackForwardList.getSize(); i++) {
					itemAtIndex = webBackForwardList.getItemAtIndex(i);//获取指定的历史记录
					Log.e(getClass().getName().toString(),"---====url="+itemAtIndex.getUrl()+"---===originalurl"+itemAtIndex.getOriginalUrl()+"--====title"+itemAtIndex.getTitle());
				}
				break;
			case R.id.btn_load:
				//加载HTML中的js脚本
				webview.loadUrl("javascript:testAlert();");
				break;
		}
	}
}
