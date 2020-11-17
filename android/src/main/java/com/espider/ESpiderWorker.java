package com.espider;

import android.app.Activity;
import android.content.Context;
import android.os.Handler;
import android.util.Log;
import com.facebook.react.bridge.*;
import com.facebook.react.modules.core.DeviceEventManagerModule;
import com.heenam.espider.Engine;
import com.heenam.espider.EngineInterface;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

import static com.heenam.espider.Engine.ENGINE_JOB_MODULE_KEY;

public class ESpiderWorker implements EngineInterface {

    private ReactContext context;
    private Engine mEngine;
    private Promise promise;
    private int _size;

    private WritableArray _success;

    private Handler mHandler;

    private ArrayList<HashMap<String, HashMap<String, String>>> mJobList;

    public ESpiderWorker(Engine mEngine, ReactContext reactContext) {

        this.context = reactContext;

        this.mEngine = mEngine;
        this.mEngine.setInterface(this);

        this._success = Arguments.createArray();
        this._size = 0;

        mHandler = new Handler();
    }

    public void dispose() {
        if (mEngine != null) {
            mEngine = null;
        }
    }

    public void runJobs(ArrayList<HashMap<String, HashMap<String, String>>> mJobList, final Promise promise) {

        this.mJobList = mJobList;
        this.promise = promise;

        try {
            mEngine.stopEngine();
            mEngine.setThreadCount(8);
            mEngine.setAutoStop(true);
            mEngine.startEngine(); //엔진 기동
            mEngine.startJob(); //추가된 모듈리스트 조회

        } catch (Exception e) {
            promise.reject("RNESpider", "작업이 실패하였습니다.", new Throwable());
            dispose();
            e.printStackTrace();
        }

    }

    private void sendEvent(String eventName, WritableMap params) {
        this.context
                .getJSModule(DeviceEventManagerModule.RCTDeviceEventEmitter.class)
                .emit(eventName, params);
    }

    /**
     * 엔진에 실행중인 모듈List의 갯수를 엔진에 전달하는 함수.
     */
    @Override
    public int numberOfJobInEngine(Engine engine) {
        return mJobList.size();
    }


    /**
     * 실행할 모듈정보(moduleInfo)를 엔진에 전달하는 Callback 함수
     */
    @Override
    public HashMap<String, String> engineGetJob(Engine engine, int jobIndex) {

        Log.i(this.getClass().getName(), "engineGetJob jobIndex[" + String.valueOf(jobIndex) + "]");

        if (mJobList.size() <= jobIndex) return null;

        HashMap<String, HashMap<String, String>> element = mJobList.get(jobIndex);
        if (element.containsKey(Engine.ENGINE_JOB_MODULE_KEY)) {
            return element.get(Engine.ENGINE_JOB_MODULE_KEY);
        }

        return null;
    }

    /**
     * 실행할 로그인정보(loginInfo) 및 파라메터(paramInfo)를 엔진에 전달하는 Callback 함수
     */
    @Override
    public String engineGetParam(Engine engine, int threadIndex, int jobIndex, String requireJSONString, boolean bSynchronous) {

        Log.i(this.getClass().getName(), "engineGetParam threadIdx[" + String.valueOf(threadIndex) + "] jobIndex[" + String.valueOf(jobIndex)
                + "] requireJSONString[" + requireJSONString + "] bSynchronous [" + String.valueOf(bSynchronous) + "]");

        try {
            JSONObject requireJson = new JSONObject(requireJSONString);

            if (bSynchronous) {

                if (requireJson.has(Engine.ENGINE_JOB_PARAM_LOGIN_KEY)) { //로그인정보(loginInfo) 엔진에 전달
                    JSONObject reqJobItem = (JSONObject) requireJson.get(Engine.ENGINE_JOB_PARAM_LOGIN_KEY);
                    HashMap<String, String> jobSourceItem = (HashMap<String, String>) ((HashMap<String, HashMap<String, String>>) mJobList.get(jobIndex))
                            .get(Engine.ENGINE_JOB_PARAM_LOGIN_KEY);

                    Iterator<String> itr = jobSourceItem.keySet().iterator();
                    while (itr.hasNext()) {
                        String key = (String) itr.next();
                        reqJobItem.put(key, jobSourceItem.get(key));
                    }
                }

                if (requireJson.has(Engine.ENGINE_JOB_PARAM_INFO_KEY)) { //파라메터(paramInfo) 엔진에 전달
                    JSONObject reqJobItem = (JSONObject) requireJson.get(Engine.ENGINE_JOB_PARAM_INFO_KEY);
                    HashMap<String, String> jobSourceItem = (HashMap<String, String>) ((HashMap<String, HashMap<String, String>>) mJobList.get(jobIndex))
                            .get(Engine.ENGINE_JOB_PARAM_INFO_KEY);

                    Iterator<String> itr = jobSourceItem.keySet().iterator();
                    while (itr.hasNext()) {
                        String key = (String) itr.next();
                        reqJobItem.put(key, jobSourceItem.get(key));
                    }
                }

                if (requireJson.has(Engine.ENGINE_JOB_PARAMEXT_INFO_KEY)) { //파라메터(paramInfo) 엔진에 전달
                    JSONObject reqJobItem = (JSONObject) requireJson.get(Engine.ENGINE_JOB_PARAMEXT_INFO_KEY);
                    HashMap<String, String> jobSourceItem = (HashMap<String, String>) ((HashMap<String, HashMap<String, String>>) mJobList.get(jobIndex))
                            .get(Engine.ENGINE_JOB_PARAMEXT_INFO_KEY);

                    Iterator<String> itr = jobSourceItem.keySet().iterator();
                    while (itr.hasNext()) {
                        String key = (String) itr.next();
                        reqJobItem.put(key, jobSourceItem.get(key));
                    }
                }

                JSONObject o = new JSONObject(mJobList.get(jobIndex));
                WritableMap map = Arguments.createMap();
                map.putString("json", o.toString());
                this.sendEvent("WorkStart", map);

                return requireJson.toString();
            }
            return null;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 엔진이 모듈을 실행 후 결과를 가져올 때 호출됩니다.(각 조회된 데이터를 처리합니다.)
     */
    @Override
    public void engineResult(Engine engine, int threadIndex, final int jobIndex, int error, String userError, final String errorMessage, String resultJsonString) {
        Log.i(this.getClass().getName(), "engineResult threadIdx[" + String.valueOf(threadIndex) + "] jobIndex[" + String.valueOf(jobIndex) + "] error["
                + String.valueOf(error & 0xFFFF) + "] userError[" + userError + "] errorMessage [" + errorMessage + "] resultJsonString [" + resultJsonString
                + "]");
        // Error 처리

        try {
            HashMap<String, HashMap<String, String>> curr = mJobList.get(jobIndex);
            if (error == 0) {

                WritableMap row2 = Arguments.createMap();
                row2.putBoolean("success", true);
                row2.putString("data", resultJsonString);

                WritableMap jobModule2 = Arguments.createMap();
                for (String key : curr.get(ENGINE_JOB_MODULE_KEY).keySet()) {
                    jobModule2.putString(key, curr.get(ENGINE_JOB_MODULE_KEY).get(key));
                }
                row2.putMap(ENGINE_JOB_MODULE_KEY, jobModule2);
                this.sendEvent("WorkFinish", row2);

                WritableMap row = Arguments.createMap();
                row.putBoolean("success", true);
                row.putString("data", resultJsonString);

                WritableMap jobModule = Arguments.createMap();
                for (String key : curr.get(ENGINE_JOB_MODULE_KEY).keySet()) {
                    jobModule.putString(key, curr.get(ENGINE_JOB_MODULE_KEY).get(key));
                }
                row.putMap(ENGINE_JOB_MODULE_KEY, jobModule);
                _success.pushMap(row);

            }
            else {
                WritableMap row2 = Arguments.createMap();
                row2.putBoolean("success", false);
                row2.putString("userError", userError);
                row2.putString("errorMessage", errorMessage);
                row2.putInt("error", error & 0xfff);
                WritableMap jobModule2 = Arguments.createMap();
                for (String key : curr.get(ENGINE_JOB_MODULE_KEY).keySet()) {
                    jobModule2.putString(key, curr.get(ENGINE_JOB_MODULE_KEY).get(key));
                }
                row2.putMap(ENGINE_JOB_MODULE_KEY, jobModule2);
                this.sendEvent("WorkFinish", row2);

                WritableMap row = Arguments.createMap();
                row.putBoolean("success", false);
                row.putString("userError", userError);
                row.putString("errorMessage", errorMessage);
                row.putInt("error", error & 0xfff);
                WritableMap jobModule = Arguments.createMap();
                for (String key : curr.get(ENGINE_JOB_MODULE_KEY).keySet()) {
                    jobModule.putString(key, curr.get(ENGINE_JOB_MODULE_KEY).get(key));
                }
                row.putMap(ENGINE_JOB_MODULE_KEY, jobModule);
                _success.pushMap(row);
            }

            if (_size >= 0) _size++;
            if (mJobList.size() <= _size) {
                _size = -100;
                if (this.promise != null) {
                    this.promise.resolve(_success);
                }
                this.promise = null;
                dispose();
            }
        }
        catch (Exception ex) {
            ex.printStackTrace();
            if (this.promise != null) {
                this.promise.reject("RNESpider", "B 작업이 실패하였습니다.", ex);
            }
            this.promise = null;
            dispose();
        }

    }


    /**
     * 엔진시스템의 Error가 발생시호출됩니다.
     */
    @Override
    public void engineSystemError(Engine engine, int error, String errorMessage) {
        Log.i(this.getClass().getName(), "engineSystemError error[" + String.valueOf(error & 0xFFFF) + "] errormessage [" + errorMessage + "]");
    }


    /**
     * 엔진의 상태를 가져옵니다. (status 값이 0 이면 모든 작업이 완료상태)
     */

    @Override
    public void engineStatus(Engine engine, int status) {
        if (status == 0) {
            //모듈리스트의 업무조회 완료 후 에러가 있을 경우 Error를 출력하는 alertDialog를 실행합니다.
            mHandler.post(new Runnable() {
                @Override
                public void run() {
                    dispose();
                }
            });
        }
    }


    /**
     * 실행중인 모듈의 진행중인 상태를 status로 확인할 수 있습니다.(시작, 진행중, 완료 등등)
     */
    @Override
    public void engineJobStatus(Engine engine, int threadIndex, final int jobIndex, final int status) {
        Log.i(this.getClass().getName(), "engineJobStatus threadIdx[" + String.valueOf(threadIndex) + "] jobIndex[" + String.valueOf(jobIndex) + "] status["
                + String.valueOf(status) + "]");
    }

    /**
     * 실행중인 모듈의 진행상태를 percent로 확인할 수 있습니다.(Progress를 처리 가능.)
     */
    @Override
    public void engineJobPercent(Engine engine, int threadIndex, int jobIndex, final int percent) {
        Log.i(this.getClass().getName(), "engineJobPercent threadIdx[" + String.valueOf(threadIndex) + "] jobIndex[" + String.valueOf(jobIndex) + "] percent["
                + String.valueOf(percent) + "]");
    }


}
