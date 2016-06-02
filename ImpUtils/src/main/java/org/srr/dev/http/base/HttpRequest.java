package org.srr.dev.http.base;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.android.volley.toolbox.HttpHeaderParser;

import org.apache.http.HttpStatus;
import org.srr.dev.util.Loger;

import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Map;
import java.util.zip.GZIPInputStream;

/**
 * Volley自定义网络请求类
 * （保留原本工程请求的Gzip和统计）
 * @author yangwq
 * contact :57890940@qq.com
 * @date 2014-8-21
 */
public class HttpRequest extends Request<String>{
	private Listener<String> mListener;
	public Context context;
	private SQLiteDatabase write;
	private RequestEntity mRequestEntity;
	
	public static final String TAG = HttpRequest.class.getName();

	public HttpRequest(Context context, RequestEntity requestEntity, Listener<String> listener, ErrorListener errorListener) {
		super(requestEntity.requestType, requestEntity.url, errorListener);
		init(context, requestEntity, listener);
	}
	
	private void init(Context context, RequestEntity requestEntity, Listener<String> listener){
		mListener = listener;
		this.context = context;
		mRequestEntity = requestEntity;
	}
	
	
	/**
	 * 返回Post所需参数Map对象
	 * 注：若getBody已设置则无需重写此方法
	 */
	@Override
	protected Map<String, String> getParams() throws AuthFailureError {
		if(mRequestEntity == null || mRequestEntity.requestMap == null){
			return super.getParams();
		}else{
			Log.i(TAG, mRequestEntity.requestMap.toString());
			return mRequestEntity.requestMap;
		}
	}
	
	

	@Override
	protected void deliverResponse(String response) {
		
		Loger.i(TAG, "response---" + response);
		mListener.onResponse(response);
		
		
	}

	@Override
	protected Response<String> parseNetworkResponse(NetworkResponse response) {
		
		 /**
		  * 避免OOM的BUG
		  */
//		 setShouldCache(false);
		
		 String parsed;
		 int method = getMethod();
		 if(response.statusCode == HttpStatus.SC_OK){
			 
		 }
		 try {
//				if (GlobalConfig.CURRENT_NETWORK_STATE_TYPE != GlobalConfig.NETWORK_STATE_CTWAP) {
//					parsed = new String(getByteArrayFromGZIP(response.data));
//				} else {
					parsed = new String(response.data, HttpHeaderParser.parseCharset(response.headers));// 取出应答字符串
//				}
			 } catch (Exception e) {
				 parsed = new String(response.data);
			 }
		 return Response.success(parsed, HttpHeaderParser.parseCacheHeaders(response));
	}
	
	@Override
	public Map<String, String> getHeaders() throws AuthFailureError {
		Map<String, String> map = mRequestEntity.header;
		if(map == null){
			return super.getHeaders();
		}else{
			return map;
		}
	}
	
//	/**
//	 * 获取Post请求参数Byte对象
//	 */
//	@Override
//	public byte[] getBody() throws AuthFailureError {
//		if(mRequestEntity == null || mRequestEntity.requestData == null){
//			return super.getBody();
//		}else{
//			return mRequestEntity.requestData;
//		}
//	}
	
	@Override
	public byte[] getBody() throws AuthFailureError {
		if(mRequestEntity == null || mRequestEntity.body == null){
			return super.getBody();
		}else{
			return mRequestEntity.body;
		}
	}
	
	/**
	 * 拦截Url
	 * 获取TAG为"HttpRequest"的Log
	 * 即可查看Url，每次访问会调用该方法5次
	 */
	@Override
	public String getUrl() {
		Loger.d(TAG, "url---" + super.getUrl());
		return super.getUrl();
		
	}
	
	/**
	 * 解析Gzip压缩的数据
	 * @param data
	 * @return
	 * @throws IOException
	 */
	private byte[] getByteArrayFromGZIP(byte[] data)
			throws IOException {
		ByteArrayOutputStream out = null;
		InputStream is = null;
		BufferedInputStream bis = null;
		try {
			is = new ByteArrayInputStream(data);
			bis = new BufferedInputStream(is);
			// 取前两个字节
			bis.mark(2);
			byte[] header = new byte[2];
			int result = bis.read(header);
			// reset输入流到开始位置
			bis.reset();
			// 判断是否是GZIP格式
			int headerData = getShort(header);
			// Gzip 流 的前两个字节是 0x1f8b
			if (result != -1 && headerData == 0x1f8b) {
				Loger.i("HttpResp", " is GZIPInputStream  ");
				is = new GZIPInputStream(bis);
			} else {
				Loger.i("HttpResp", " not GZIPInputStream");
				is = bis;
			}
			out = new ByteArrayOutputStream();
			byte[] buffer = new byte[100];
			int readSize;
			while ((readSize = is.read(buffer)) > 0) {
				out.write(buffer, 0, readSize);
			}
			out.flush();
			out.close();
		} catch (Exception e) {
			Loger.e("HttpResp", e.toString(), e);
		} finally {
			if (bis != null) {
				bis.close();
			}
			if (is != null) {
				is.close();
			}
		}
		// Loger.i("HttpResp", "getJsonStringFromGZIP net output : " +
		// out.toString());
		return out.toByteArray();
	}

	private int getShort(byte[] data) {
		return (int) ((data[0] << 8) | data[1] & 0xFF);
	}
	

}
