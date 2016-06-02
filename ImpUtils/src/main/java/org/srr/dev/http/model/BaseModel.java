package org.srr.dev.http.model;

import org.srr.dev.http.base.IViewModelInterface;
import org.srr.dev.http.response.BaseResponse;

public abstract class BaseModel<T extends BaseResponse>{
	
	private IViewModelInterface mViewModelInterface;
	
	/**
	 * 与网络交互并加载数据
	 * @param params
	 * @return
	 * @throws Exception
	 */
	protected abstract void onLoad(Object... params) throws Exception;
	
	/**
	 * 标识唯一Model
	 * @return
	 */
	public int getTag(){
		return this.hashCode();
	}
	
	/**
	 * 开始启动加载数据
	 * @param params
	 */
	public void start(Object... params){
		if(mViewModelInterface != null){
			mViewModelInterface.onPreLoad(getTag());
		}
		Exception exception = null;
		try {
			onLoad(params);
		} catch (Exception e) {
			exception = e;
			e.printStackTrace();
		}
		
		if(exception != null){
			if(mViewModelInterface != null){
				mViewModelInterface.onExceptionLoad(getTag(), exception);
			}
			return;
		}
		
	}


	public void setViewModelInterface(IViewModelInterface mViewModelInterface) {
		this.mViewModelInterface = mViewModelInterface;
	}
	
	public IViewModelInterface getViewModelInterface(){
		return mViewModelInterface;
	}
	
	

}
