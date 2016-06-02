package org.srr.dev.http.base;

import android.content.Context;

import com.android.volley.Request.Method;

import java.util.HashMap;

/**
 * 联网请求数据模型类，主要用于保存联网需要的参数
 */
public class RequestEntity {

	/** 联网url */
	public String url;
	/** 请求数据 */
	public byte[] requestData;
	/** Context实例 */
	public Context context;
	/** get参数 */
	public HashMap<String, String> requestMap;
	/** 请求类型 get或者post 默认get */
	public int requestType = Method.GET;
	
	public HashMap<String, String> header;
	
	public byte[] body;


}
