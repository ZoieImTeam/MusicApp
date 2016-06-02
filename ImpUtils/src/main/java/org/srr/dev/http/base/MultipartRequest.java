package org.srr.dev.http.base;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.HttpHeaderParser;

import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.entity.mime.content.StringBody;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class MultipartRequest extends Request<String> {

	private MultipartEntity entity = new MultipartEntity();

	private final Response.Listener<String> mListener;

	private List<File> mFileParts;
	private String mFilePartName;
	private RequestEntity requestEntity;

	/**
	 * 单个文件
	 *
	 * @param errorListener
	 * @param listener
	 * @param filePartName
	 * @param file
	 */
	public MultipartRequest(RequestEntity requestEntity, String filePartName, File file,Listener<String> listener, ErrorListener errorListener
			) {
		super(Method.POST, requestEntity.url.toString(), errorListener);
		this.requestEntity = requestEntity;
		mFileParts = new ArrayList<File>();
		if (file != null) {
			mFileParts.add(file);
		}
		mFilePartName = filePartName;
		mListener = listener;
		buildMultipartEntity();
	}
	
	/**
	 * 多个文件，对应一个key
	 *
	 * @param errorListener
	 * @param listener
	 * @param filePartName
	 * @param files
	 */
	public MultipartRequest(RequestEntity requestEntity, Response.ErrorListener errorListener,
			Response.Listener<String> listener, String filePartName,
			List<File> files) {
		super(Method.POST, requestEntity.url, errorListener);
		this.requestEntity = requestEntity;
		mFilePartName = filePartName;
		mListener = listener;
		mFileParts = files;
		buildMultipartEntity();
	}

	private void buildMultipartEntity() {
		if (mFileParts != null && mFileParts.size() > 0) {
			for (File file : mFileParts) {
				entity.addPart(mFilePartName, new FileBody(file));
			}
			long l = entity.getContentLength();
		}

		try {
			if (requestEntity.requestMap != null && requestEntity.requestMap.size() > 0) {
				for (Map.Entry<String, String> entry : requestEntity.requestMap.entrySet()) {
					entity.addPart(entry.getKey(),new StringBody(entry.getValue(), Charset.forName("UTF-8")));
				}
			}
		} catch (UnsupportedEncodingException e) {
			VolleyLog.e("UnsupportedEncodingException");
		}

	}

	@Override
	public String getBodyContentType() {
		return entity.getContentType().getValue();
	}


	@Override
	public byte[] getBody() throws AuthFailureError {
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		try {
			entity.writeTo(bos);
		} catch (IOException e) {
			VolleyLog.e("IOException writing to ByteArrayOutputStream");
		}
		return bos.toByteArray();
	}

	@Override
	protected Response<String> parseNetworkResponse(NetworkResponse response) {
		if (VolleyLog.DEBUG) {
			if (response.headers != null) {
				for (Map.Entry<String, String> entry : response.headers
						.entrySet()) {
					VolleyLog.d(entry.getKey() + "=" + entry.getValue());
				}
			}
		}

		String parsed;
		try {
			parsed = new String(response.data,
					HttpHeaderParser.parseCharset(response.headers));
		} catch (UnsupportedEncodingException e) {
			parsed = new String(response.data);
		}
		return Response.success(parsed,
				HttpHeaderParser.parseCacheHeaders(response));
	}


	@Override
	public Map<String, String> getHeaders() throws AuthFailureError {
		VolleyLog.d("getHeaders");
		Map<String, String> headers = null;
		if(requestEntity.header == null){
			headers = super.getHeaders();
		}else{
			headers = requestEntity.header;
		}


		if (headers == null || headers.equals(Collections.emptyMap())) {
			headers = new HashMap<String, String>();
		}
		return headers;
	}

	@Override
	protected void deliverResponse(String response) {
		mListener.onResponse(response);
	}
}