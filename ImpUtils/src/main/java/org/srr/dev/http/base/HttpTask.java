package org.srr.dev.http.base;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request.Method;
import com.android.volley.Response;
import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.JsonRequest;
import org.srr.dev.base.BaseApplication;
import org.srr.dev.http.model.BaseModel;
import org.srr.dev.http.response.BaseResponse;
import org.json.JSONObject;
import org.srr.dev.util.Loger;
import java.io.File;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * 网络任务
 *
 * @author WeiQi
 * @email 57890940@qq.com
 * @date 2014-9-23
 */
public class HttpTask implements IHttpTaskInterface {

    /**
     * 1男 2女 0未知
     */
    private static final int REQUEST_TIME = 30000;

    private static HttpTask mInstance;

    protected Context mContext;

    private HttpTask() {
        mContext = BaseApplication.mContext;
    }

    public static HttpTask getInstance() {
        if (mInstance == null) {
            initSync();
        }
        return mInstance;
    }

    private synchronized static void initSync() {
        if (mInstance == null) {
            mInstance = new HttpTask();
        }
    }

    @SuppressWarnings("rawtypes")
    private <T extends Object> void commonParseHandle(final Class<T> c,
                                                      RequestEntity entity, final BaseModel model) {

        /**
         * 若是GET方法，自动拼接URL
         */
        if (entity.requestType == Method.GET) {

            if (entity.requestMap != null && !entity.requestMap.isEmpty()) {
                StringBuilder encodedParams = new StringBuilder(entity.url);
                boolean isFirst = true;
                for (Map.Entry<String, String> entry : entity.requestMap
                        .entrySet()) {
                    // 如果参数传null或者“”就跳过本次拼接
                    if (entry.getValue() == null || entry.getValue().equals("")) {
                        continue;
                    }
                    if (isFirst) {
                        if (!entity.url.endsWith("?")) {
                            encodedParams.append('?');
                        }
                        isFirst = false;
                    } else {
                        encodedParams.append('&');
                    }
                    encodedParams.append(entry.getKey());
                    encodedParams.append('=');
                    encodedParams.append(entry.getValue());
                }
                entity.url = encodedParams.toString();
            }
        }
        Loger.i("vicky", entity.url);

        HttpRequest mRequest = new HttpRequest(mContext, entity,
                new Listener<String>() {

                    @Override
                    public void onResponse(String response) {
                        Loger.i("HttpResponse", "response=" + response);
                        try {
                            BaseResponse result = null;
                            if (TextUtils.isEmpty(response)) {
                                throw new Exception();
                            }
                            result = (BaseResponse) CommonEntityPaser
                                    .parseObjectEntity(response, c);

                            if (result != null) {
                                int code = result.getCode();
                                String codeMsg = result.getMessage();
                                if ((code == BaseResponse.CODE_SUCCESS)
                                        && model.getViewModelInterface() != null) {
                                    // 加载成功
                                    if (model.getViewModelInterface() != null) {
                                        model.getViewModelInterface()
                                                .onSuccessLoad(model.getTag(),
                                                        result);
                                    }

                                } else {
                                    // 加载失败
                                    if (model.getViewModelInterface() != null) {
                                        model.getViewModelInterface()
                                                .onFailLoad(model.getTag(),
                                                        code, codeMsg);
                                    }

                                }
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                            if (model.getViewModelInterface() != null) {
                                model.getViewModelInterface().onExceptionLoad(
                                        model.getTag(), e);
                            }
                        } finally {
                            // HttpRequestManager.getInstance().removeRequest(entity.current_taskId);
                        }

                    }
                }, new ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                // HttpRequestManager.getInstance().removeRequest(entity.current_taskId);
                if (model.getViewModelInterface() != null) {
                    model.getViewModelInterface().onExceptionLoad(
                            model.getTag(), error);
                }
            }
        });

        mRequest.setShouldCache(false);
        /**设置重复请求时间*/
        mRequest.setRetryPolicy(new DefaultRetryPolicy(REQUEST_TIME,//默认超时时间，应设置一个稍微大点儿的，例如本处的500000
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,//默认最大尝试次数
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        HttpRequestManager.getInstance().addQueue(mRequest);

    }

    @SuppressWarnings({"rawtypes", "unused"})
    private <T extends Object> void commonSignParseHandle(final Class<T> c,
                                                          RequestEntity entity, final BaseModel model) {

        HttpRequest mRequest = new HttpRequest(mContext, entity,
                new Listener<String>() {

                    @Override
                    public void onResponse(String response) {
                        Loger.i("HttpResponse", "response=" + response);
                        try {
                            BaseResponse result = null;
                            result = (BaseResponse) CommonEntityPaser
                                    .parseObjectEntity(response, c);
                            if (result != null) {
                                int code = result.getCode();
                                if ((code == BaseResponse.CODE_SUCCESS)
                                        && model.getViewModelInterface() != null) {
                                    // 加载成功
                                    if (model.getViewModelInterface() != null) {
                                        model.getViewModelInterface()
                                                .onSuccessLoad(model.getTag(),
                                                        result);
                                    }

                                } else {
                                    // 加载失败
                                    if (model.getViewModelInterface() != null) {
                                        model.getViewModelInterface()
                                                .onFailLoad(model.getTag(),
                                                        code, "");
                                    }

                                }
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                            if (model.getViewModelInterface() != null) {
                                model.getViewModelInterface().onExceptionLoad(
                                        model.getTag(), e);
                            }
                        } finally {
                            // HttpRequestManager.getInstance().removeRequest(entity.current_taskId);
                        }

                    }
                }, new ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                // HttpRequestManager.getInstance().removeRequest(entity.current_taskId);
            }
        });
        HttpRequestManager.getInstance().addQueue(mRequest);

    }

    @SuppressWarnings({"unused", "rawtypes"})
    private <T extends Object> void commonNoParseHandle(final Class<T> c,
                                                        RequestEntity entity, final BaseModel model) {

        HttpRequest mRequest = new HttpRequest(mContext, entity,
                new Listener<String>() {

                    @Override
                    public void onResponse(String response) {
                        Loger.i("HttpResponse", "response=" + response);
                        try {
                            if (model.getViewModelInterface() != null) {
                                // 加载成功
                                if (model.getViewModelInterface() != null) {
                                    model.getViewModelInterface()
                                            .onSuccessLoad(model.getTag(),
                                                    response);
                                }
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                            if (model.getViewModelInterface() != null) {
                                model.getViewModelInterface().onExceptionLoad(
                                        model.getTag(), e);
                            }
                        } finally {
                            // HttpRequestManager.getInstance().removeRequest(entity.current_taskId);
                        }

                    }
                }, new ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                // HttpRequestManager.getInstance().removeRequest(entity.current_taskId);
            }
        });
        HttpRequestManager.getInstance().addQueue(mRequest);

    }

    private <T extends Object> void commonJsonParseHandle(final Class<T> c,
                                                          RequestEntity entity, final BaseModel model) {
        PostProcess(entity.requestMap);

        JSONObject jsonObject = new JSONObject(entity.requestMap);
        JsonRequest<JSONObject> mRequest = new JsonObjectRequest(
                Method.POST, entity.url, jsonObject,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            BaseResponse result = null;
                            result = (BaseResponse) CommonEntityPaser
                                    .parseObjectEntity(String.valueOf(response), c);
                            if (result != null) {
                                int code = result.getCode();
                                String codeMsg = result.getMessage();
                                if (code == BaseResponse.CODE_SUCCESS
                                        && model.getViewModelInterface() != null) {
                                    // 加载成功
                                    if (model.getViewModelInterface() != null) {
                                        model.getViewModelInterface()
                                                .onSuccessLoad(model.getTag(),
                                                        result);
                                    }
                                } else {
                                    // 加载失败
                                    if (model.getViewModelInterface() != null) {
                                        model.getViewModelInterface()
                                                .onFailLoad(model.getTag(),
                                                        code, codeMsg);
                                    }

                                }
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                            if (model.getViewModelInterface() != null) {
                                model.getViewModelInterface().onExceptionLoad(
                                        model.getTag(), e);
                            }
                        } finally {
                            // HttpRequestManager.getInstance().removeRequest(entity.current_taskId);
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                if (model.getViewModelInterface() != null) {
                    model.getViewModelInterface().onExceptionLoad(
                            model.getTag(), error);
                }
                // HttpRequestManager.getInstance().removeRequest(entity.current_taskId);
            }
        }) {
            // 注意此处override的getParams()方法,在此处设置post需要提交的参数根本不起作用
            // 必须象上面那样,构成JSONObject当做实参传入JsonObjectRequest对象里
            // 所以这个方法在此处是不需要的
            // @Override
            // protected Map<String, String> getParams() {
            // Map<String, String> map = new HashMap<String, String>();
            // map.put("name1", "value1");
            // map.put("name2", "value2");

            // return params;
            // }

            @Override
            public Map<String, String> getHeaders() {
                HashMap<String, String> headers = new HashMap<String, String>();
                headers.put("Accept", "application/json");
                headers.put("Content-Type", "application/json; charset=UTF-8");
                return headers;
            }
        };
        mRequest.setShouldCache(false);
        /**设置重复请求时间*/
        mRequest.setRetryPolicy(new DefaultRetryPolicy(REQUEST_TIME,//默认超时时间，应设置一个稍微大点儿的，例如本处的500000
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,//默认最大尝试次数
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        HttpRequestManager.getInstance().addQueue(mRequest);
    }

    @SuppressWarnings("rawtypes")
    private <T extends Object> void commonParseFileHandle(final Class<T> c,
                                                          RequestEntity entity, String fileName, File file, final BaseModel model) {

        PostProcess(entity.requestMap);
        Loger.i("vicky", entity.url);

        MultipartRequest mRequest = new MultipartRequest(entity, fileName, file, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                Loger.i("HttpResponse", "response=" + response);
                try {
                    BaseResponse result = null;
                    result = (BaseResponse) CommonEntityPaser
                            .parseObjectEntity(String.valueOf(response), c);
                    if (result != null) {
                        int code = result.getCode();
                        String codeMsg = result.getMessage();
                        if (code == BaseResponse.CODE_SUCCESS
                                && model.getViewModelInterface() != null) {
                            // 加载成功
                            if (model.getViewModelInterface() != null) {
                                model.getViewModelInterface()
                                        .onSuccessLoad(model.getTag(),
                                                result);
                            }
                        } else {
                            // 加载失败
                            if (model.getViewModelInterface() != null) {
                                model.getViewModelInterface()
                                        .onFailLoad(model.getTag(),
                                                code, codeMsg);
                            }

                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    if (model.getViewModelInterface() != null) {
                        model.getViewModelInterface().onExceptionLoad(
                                model.getTag(), e);
                    }
                } finally {
                    // HttpRequestManager.getInstance().removeRequest(entity.current_taskId);
                }

            }


        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError arg0) {
                // TODO Auto-generated method stub

            }
        });
        mRequest.setShouldCache(false);
        /**设置重复请求时间*/
        mRequest.setRetryPolicy(new DefaultRetryPolicy(REQUEST_TIME,//默认超时时间，应设置一个稍微大点儿的，例如本处的500000
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,//默认最大尝试次数
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        HttpRequestManager.getInstance().addQueue(mRequest);

    }

    @SuppressWarnings("rawtypes")
    private <T extends Object> void commonParseMultiFileHandle(final Class<T> c,
                                                               RequestEntity entity, String fileName, List<File> files, final BaseModel model) {
        Loger.i("vicky", entity.url);
        PostProcess(entity.requestMap);
        MultipartRequest mRequest = new MultipartRequest(entity, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError arg0) {
                // TODO Auto-generated method stub

            }
        }, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                Loger.i("HttpResponse", "response=" + response);
                try {
                    BaseResponse result = null;
                    result = (BaseResponse) CommonEntityPaser
                            .parseObjectEntity(String.valueOf(response), c);
                    if (result != null) {
                        int code = result.getCode();
                        String codeMsg = result.getMessage();
                        if (code == BaseResponse.CODE_SUCCESS
                                && model.getViewModelInterface() != null) {
                            // 加载成功
                            if (model.getViewModelInterface() != null) {
                                model.getViewModelInterface()
                                        .onSuccessLoad(model.getTag(),
                                                result);
                            }
                        } else {
                            // 加载失败
                            if (model.getViewModelInterface() != null) {
                                model.getViewModelInterface()
                                        .onFailLoad(model.getTag(),
                                                code, codeMsg);
                            }

                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    if (model.getViewModelInterface() != null) {
                        model.getViewModelInterface().onExceptionLoad(
                                model.getTag(), e);
                    }
                } finally {
                    // HttpRequestManager.getInstance().removeRequest(entity.current_taskId);
                }

            }


        }, fileName, files);

        mRequest.setShouldCache(false);
        /**设置重复请求时间*/
        mRequest.setRetryPolicy(new DefaultRetryPolicy(REQUEST_TIME,//默认超时时间，应设置一个稍微大点儿的，例如本处的500000
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,//默认最大尝试次数
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        HttpRequestManager.getInstance().addQueue(mRequest);

    }

    private void PostProcess(HashMap<String, String> map) {
        // 移除没有值或空值的
        Iterator<Map.Entry<String, String>> it = map.entrySet().iterator();
        while (it.hasNext()) {
            if (org.srr.dev.util.TextUtils.isEmpty(it.next().getValue())) {
                it.remove();
            }
        }
    }
    @SuppressWarnings("unused")
    private HashMap<String, String> getHeaderParamsMap(byte[] data,
                                                       String charSet) {
        HashMap<String, String> headers = new HashMap<String, String>();
        headers.put("Content-Type", "text/xml; charset=" + charSet);
        headers.put("Content-Length", data.length + "");
        headers.put("X-ClientType", "2");
        return headers;
    }




}