package org.srr.dev.http.base;

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.HttpClientStack;
import com.android.volley.toolbox.HttpStack;
import com.android.volley.toolbox.JsonRequest;
import com.android.volley.toolbox.Volley;

import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.client.CookieStore;
import org.json.JSONObject;

import java.util.HashMap;

/**
 * Volley网络请求管理
 * 
 * @author yangwq contact :57890940@qq.com
 * @date 2014-8-21
 */
public class HttpRequestManager {

	/**** Volley请求队列 ****/
	private RequestQueue mQueue;

	/**** 自定义请求队列Map ****/
	private HashMap<Integer, Request<String>> mRequestMap;

	private static HttpRequestManager mInstance = null;

	private synchronized static void syncInit() {
		mInstance = new HttpRequestManager();
	}

	public static HttpRequestManager getInstance() {
		if (mInstance == null) {
			syncInit();
		}
		return mInstance;
	}

	/**
	 * 初始化Volley网络请求队列
	 * 
	 * @param context
	 */
	public void initRequestQueue(Context context) {
		DefaultHttpClient httpclient = new DefaultHttpClient();
		// 非持久化存储(内存存储) BasicCookieStore | 持久化存储 PreferencesCookieStore
		CookieStore cookieStore = new PreferencesCookieStore(context);
		httpclient.setCookieStore(cookieStore);
		HttpStack httpStack = new HttpClientStack(httpclient);
		mQueue = Volley.newRequestQueue(context, httpStack, true);

		mRequestMap = new HashMap<Integer, Request<String>>();
		startQueue();
	}

	/**
	 * 获得Volley网络请求队列实例
	 * 
	 * @return
	 */
	public RequestQueue getRequestQueue() {
		return mQueue;
	}

	/**
	 * 添加网络请求到请求队列中
	 * 
	 * @param request
	 *            请求Request
	 */
	public void addQueue(Request<String> request) {
		mQueue.add(request);
		mRequestMap.put(request.getSequence(), request);
	}

	/**
	 * 添加网络请求到请求队列中
	 * 
	 * @param request
	 *            请求Request
	 */
	public void addQueue(JsonRequest<JSONObject> request) {
		mQueue.add(request);
	}

	/**
	 * 根据taskId查询是否还存在请求, 若还在请求列表则取消请求并返回true, 若不存在则返回false
	 * 注：在某个界面生命周期结束时调用（例如在Activity的Stop生命周期时调用，以免影响请求队列的效率）
	 * 
	 * @param taskId
	 *            请求TaskId
	 */
	public boolean cancelRequest(int taskId) {
		if (mRequestMap.containsKey(taskId)) {
			Request<String> temp = mRequestMap.remove(taskId);
			temp.cancel();
			temp = null;
			return true;
		}
		return false;

	}

	/**
	 * 移除请求队列 注：在请求结束时调用
	 * 
	 * @param taskId
	 */
	public void removeRequest(int taskId) {
		if (mRequestMap.containsKey(taskId)) {
			mRequestMap.remove(taskId);
		}

	}

	/**
	 * 开启请求队列
	 */
	public void startQueue() {
		mQueue.start();
	}

}
