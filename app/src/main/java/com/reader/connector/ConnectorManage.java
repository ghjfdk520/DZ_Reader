package com.reader.connector;

import android.content.Context;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.apkfuns.logutils.LogUtils;
import com.google.gson.Gson;
import com.reader.components.BaseApplication;
import com.reader.utils.CommonUtil;
import com.reader.utils.Utils;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;

/**
 * Created by Administrator on 2015/9/9.
 */
public class ConnectorManage {
    private AtomicLong mHttpCount = new AtomicLong(0);
    private final String TAG = ConnectorManage.class.getName();
    private RequestQueue rQueue;
    private Context mContext;
    private static ConnectorManage sInstance;
    private Gson gson;
    private JSONObject jsonObject;

    private ConnectorManage() {
        mContext = BaseApplication.mContext;
        rQueue = Volley.newRequestQueue(mContext);
        gson = new Gson();
    }

    public static ConnectorManage getInstance() {
        if (sInstance == null) {
            synchronized (ConnectorManage.class) {
                if (sInstance == null)
                    sInstance = new ConnectorManage();
            }
        }
        return sInstance;
    }

    public long Get(final String url,final HttpCallBack callback){
        final long flag = mHttpCount.incrementAndGet();
        if (!CommonUtil.isNetworkAvailable(mContext)) {
            Utils.toastMsg(mContext, "无法连接网络");

            if (callback != null) {
                callback.onGeneralError("数据为空", flag);
            }
            return -1;
        }
        StringRequest stringRequest = new StringRequest(Request.Method.GET,url,new Response.Listener<String>(){

            @Override
            public void onResponse(String response) {
                LogUtils.d("RESULT  " + flag+" "+response);
            }
        },new Response.ErrorListener(){
            @Override
            public void onErrorResponse(VolleyError error) {
                if (error.getMessage() != null && !error.getMessage().equals("")) {
                    Utils.toastMsg(mContext, error.getMessage());
                    LogUtils.d("RESULT Error", error.getMessage());
                }
                if (callback != null) {
                    callback.onGeneralError(error.getMessage(), flag);
                }
            }
        });

        rQueue.add(stringRequest).setTag(flag);
        return flag;
    }

    public long xmlPost(final String url, final Map map,
                        final HttpCallBack callback) {
        final long flag = mHttpCount.incrementAndGet();
        if (map != null)
            LogUtils.d("REQUEST  " + flag+ " url {" + url + "}  " + "params:{ " + map.toString() + " }");
        else
            LogUtils.d("REQUEST  " + flag+ " url {" + url + "}");

        if (!CommonUtil.isNetworkAvailable(mContext)) {
            Utils.toastMsg(mContext, "无法连接网络");

            if (callback != null) {
                callback.onGeneralError("数据为空", flag);
            }
            return -1;
        }
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        LogUtils.d("RESULT  " + flag+" "+response);

                            if (response == null) {
                                if (callback != null) {
                                    callback.onGeneralError("数据为空", flag);
                                }
                            } else {
                                    if (callback != null) {
                                        callback.onGeneralSuccess(response, flag);
                                    }
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                if (error.getMessage() != null && !error.getMessage().equals("")) {
                    Utils.toastMsg(mContext, error.getMessage());
                    LogUtils.d("RESULT Error", error.getMessage());
                }
                if (callback != null) {
                    callback.onGeneralError("404", flag);
                }
            }
        }) {

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                // TODO Auto-generated method stub
                return map;
            }
        };

        rQueue.add(stringRequest).setTag(flag);
        return flag;
    }

    public long Post(final String url, final Map map,
                     final HttpCallBack callback) {
        final long flag = mHttpCount.incrementAndGet();
        if (map != null)
            LogUtils.d("REQUEST  " + flag+ " url {" + url + "}  " + "params:{ " + map.toString() + " }");
        else
            LogUtils.d("REQUEST  " + flag+ " url {" + url + "}");

        if (!CommonUtil.isNetworkAvailable(mContext)) {
            Utils.toastMsg(mContext, "无法连接网络");

            if (callback != null) {
                callback.onGeneralError("数据为空", flag);
            }
            return -1;
        }
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        LogUtils.d("RESULT  " + flag+" "+response);
                        try {
                            jsonObject = new JSONObject(response);

                            if (response == null) {
                                if (callback != null) {
                                    callback.onGeneralError("数据为空", flag);
                                }
                            } else {
                                if (!jsonObject.optString("flag").equals("error")) {
                                    if (callback != null) {
                                        callback.onGeneralSuccess(response, flag);
                                    }
                                } else {
                                    if (callback != null) {
                                        callback.onGeneralError("error", flag);
                                    }
                                }
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                            callback.onGeneralError("解析异常", flag);
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                if (error.getMessage() != null && !error.getMessage().equals("")) {
                    Utils.toastMsg(mContext, error.getMessage());
                    LogUtils.d("RESULT Error", error.getMessage());
                }
                if (callback != null) {
                    callback.onGeneralError(error.getMessage(), flag);
                }
            }
        }) {

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                // TODO Auto-generated method stub
                return map;
            }
        };

        rQueue.add(stringRequest).setTag(flag);
        return flag;
    }

    public void cancleRequest(long flag) {
        rQueue.cancelAll(flag);
    }

}
