package org.srr.dev.http.base;

import android.os.Handler;

/**
 * Model与界面View交互的接口
 * @author WeiQi
 * @email 57890940@qq.com
 * @date 2014-9-23
 */
public interface IViewModelInterface {

	/**
	 * 获得刷新界面的Handler
	 * @return
	 */
	public Handler getHandler();
	
	/**
	 * 加载数据之前的操作
	 * @param tag
	 */
	public void onPreLoad(int tag);
	
	/**
	 * 加载数据成功返回数据
	 * @param tag
	 * @param result
	 */
	public void onSuccessLoad(int tag, Object result);
	
	/**
	 * 加载数据失败，返回错误结果
	 * @param tag
	 * @param code
	 */
	public void onFailLoad(int tag, int code, String codeMsg);
	
	/**
	 * 加载数据出现异常
	 * @param tag
	 * @param exception
	 */
	public void onExceptionLoad(int tag, Exception exception);
	
}
