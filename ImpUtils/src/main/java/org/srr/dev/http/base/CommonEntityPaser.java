package org.srr.dev.http.base;

import android.text.TextUtils;

import com.google.gson.Gson;

public class CommonEntityPaser {

	/**
	 * 解析Json数据，通过Gson库转换成对象
	 * 
	 * @param result
	 * @param c
	 * @return
	 * @throws Exception
	 */
	public static <T extends Object> T parseObjectEntity(String result,
			Class<T> c) throws Exception {
		T newObject = null;
		if (TextUtils.isEmpty(result)) {
			throw new Exception();
		}
		Gson gson = new Gson();
		newObject = gson.fromJson(result, c);
		return newObject;
	}

}
