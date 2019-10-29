
package com.espider;

import com.facebook.react.bridge.*;
import com.heenam.espider.Engine;

import java.util.ArrayList;
import java.util.HashMap;

import static com.heenam.espider.Engine.*;

public class RNEspiderModule extends ReactContextBaseJavaModule {

    private final ReactApplicationContext reactContext;
    private ESpiderWorker _worker;
    private Engine mEngine;

    public RNEspiderModule(ReactApplicationContext reactContext) {
        super(reactContext);
        this.reactContext = reactContext;

        mEngine = Engine.getInstatnce(reactContext);
        mEngine.setLicenseKey("2c7eb0ce-238f-11e9-83f8-b4b52f640ef8");
    }

    @Override
    public String getName() {
        return "RNEspider";
    }

    @ReactMethod
    public void cancelJob(Promise promise) {
        mEngine.stopEngine();
        if (_worker != null) {
            _worker.dispose();
            _worker = null;
        }
    }

    @ReactMethod
    public void execute(ReadableMap readableMap, Promise promise) {

        HashMap<String, HashMap<String, String>> jobInfo = new HashMap<String, HashMap<String, String>>();
        try {

            ReadableMapKeySetIterator iterator = readableMap.keySetIterator();
            while (iterator.hasNextKey()) {
                String key = iterator.nextKey();
                if (readableMap.getType(key) != ReadableType.Map) continue;

                HashMap<String, String> writeMap = new HashMap<String, String>();

                ReadableMap map = readableMap.getMap(key);
                ReadableMapKeySetIterator i2 = map.keySetIterator();
                while (i2.hasNextKey()) {
                    String key2 = i2.nextKey();
                    switch (readableMap.getType(key)) {
                        case Boolean:
                            writeMap.put(key, map.getString(key));
                            break;
                        case Number:
                            writeMap.put(key, map.getString(key));
                            break;
                        case String:
                            writeMap.put(key, map.getString(key));
                            break;
                    }
                }

                jobInfo.put(key, writeMap);
            }
        }
        catch(Exception e) {
        }

        ArrayList<HashMap<String, HashMap<String, String>>> jobs = new ArrayList<>();
        jobs.add(jobInfo);

        if (_worker != null) {
            _worker.dispose();
            _worker = null;
        }

        _worker = new ESpiderWorker(mEngine, reactContext);
        _worker.runJobs(jobs, promise);
    }

    @ReactMethod
    public void execute2(ReadableArray array, Promise promise) {

        ArrayList<HashMap<String, HashMap<String, String>>> jobs = new ArrayList<>();
        for (int i = 0; i < array.size(); i++) {
            HashMap<String, HashMap<String, String>> jobInfo = new HashMap<String, HashMap<String, String>>();
            try {
                ReadableMap readableMap = array.getMap(i);
                ReadableMapKeySetIterator iterator = readableMap.keySetIterator();
                while (iterator.hasNextKey()) {
                    String key = iterator.nextKey();
                    if (readableMap.getType(key) != ReadableType.Map) continue;

                    HashMap<String, String> writeMap = new HashMap<String, String>();

                    ReadableMap map = readableMap.getMap(key);
                    ReadableMapKeySetIterator i2 = map.keySetIterator();
                    while (i2.hasNextKey()) {
                        String key2 = i2.nextKey();
                        switch (readableMap.getType(key)) {
                            case Boolean:
                                writeMap.put(key, map.getString(key));
                                break;
                            case Number:
                                writeMap.put(key, map.getString(key));
                                break;
                            case String:
                                writeMap.put(key, map.getString(key));
                                break;
                        }
                    }

                    jobInfo.put(key, writeMap);
                }
            }
            catch(Exception e) {
            }
            jobs.add(jobInfo);
        }

        if (_worker != null) {
            _worker.dispose();
            _worker = null;
        }

        _worker = new ESpiderWorker(mEngine, reactContext);
        _worker.runJobs(jobs, promise);
    }

    @ReactMethod
    public void getS4AccountsByLogin(String s4code, String username, String password, Promise promise) {

        ArrayList<HashMap<String, HashMap<String, String>>> jobs = new ArrayList<>();

        HashMap<String, String> module = new HashMap<String, String>();
        module.put("country", "KR");
        module.put("organization", "ST");
        module.put("suborganization", s4code);
        module.put("code", "311040");
        module.put("module_disply_name", "전계좌조회");

        HashMap<String, String> paramLogin = new HashMap<String, String>();
        paramLogin.put("reqUserId", username);
        paramLogin.put("reqUserPass", password);

        HashMap<String, String> paramInfo = new HashMap<String, String>();
        paramInfo.put("reqAccount", "");
        paramInfo.put("reqAccountPass", "");
        paramInfo.put("reqSearchGbn", "2");

        HashMap<String, HashMap<String, String>> jobInfo = new HashMap<String, HashMap<String, String>>();
        jobInfo.put(ENGINE_JOB_MODULE_KEY, module);
        jobInfo.put(ENGINE_JOB_PARAM_LOGIN_KEY, paramLogin);
        jobInfo.put(ENGINE_JOB_PARAM_INFO_KEY, paramInfo);
        jobInfo.put(ENGINE_JOB_PARAMEXT_INFO_KEY, new HashMap<String, String>());

        jobs.add(jobInfo);

        if (_worker != null) {
            _worker.dispose();
            _worker = null;
        }

        _worker = new ESpiderWorker(mEngine, reactContext);
        _worker.runJobs(jobs, promise);
    }

    @ReactMethod
    public void getS4AccountsByCert(String s4code, String certPath, String keyPath, String username, String password, Promise promise) {

        ArrayList<HashMap<String, HashMap<String, String>>> jobs = new ArrayList<>();

        HashMap<String, String> module = new HashMap<String, String>();
        module.put("country", "KR");
        module.put("organization", "ST");
        module.put("suborganization", s4code);
        module.put("code", "310040");
        module.put("module_disply_name", "전계좌조회");

        HashMap<String, String> paramLogin = new HashMap<String, String>();
        paramLogin.put("reqCertFile", certPath);
        paramLogin.put("reqKeyFile", keyPath);
        paramLogin.put("reqCertPass", password);
        paramLogin.put("reqUserId", username);

        HashMap<String, String> paramInfo = new HashMap<String, String>();
        paramInfo.put("reqAccount", "");
        paramInfo.put("reqAccountPass", "");
        paramInfo.put("reqSearchGbn", "2");

        HashMap<String, HashMap<String, String>> jobInfo = new HashMap<String, HashMap<String, String>>();
        jobInfo.put(ENGINE_JOB_MODULE_KEY, module);
        jobInfo.put(ENGINE_JOB_PARAM_LOGIN_KEY, paramLogin);
        jobInfo.put(ENGINE_JOB_PARAM_INFO_KEY, paramInfo);
        jobInfo.put(ENGINE_JOB_PARAMEXT_INFO_KEY, new HashMap<String, String>());

        jobs.add(jobInfo);

        if (_worker != null) {
            _worker.dispose();
            _worker = null;
        }

        _worker = new ESpiderWorker(mEngine, reactContext);
        _worker.runJobs(jobs, promise);
    }

    @ReactMethod
    public void oneclickByCert(ReadableArray rows, String certPath, String keyPath, String password, Promise promise) {

        ArrayList<HashMap<String, HashMap<String, String>>> jobs = new ArrayList<>();

        for (Object o : rows.toArrayList()) {
            HashMap<String, Object> row = (HashMap<String, Object>) o;
            String s4code = row.get("key").toString();

            HashMap<String, String> module = new HashMap<String, String>();
            module.put("country", "KR");
            module.put("organization", "ST");
            module.put("suborganization", s4code);
            module.put("code", "310040");
            module.put("module_disply_name", "전계좌조회");

            HashMap<String, String> paramLogin = new HashMap<String, String>();
            paramLogin.put("reqCertFile", certPath);
            paramLogin.put("reqKeyFile", keyPath);
            paramLogin.put("reqCertPass", password);

            HashMap<String, String> paramInfo = new HashMap<String, String>();
            paramInfo.put("reqAccount", "");
            paramInfo.put("reqAccountPass", "");
            paramInfo.put("reqSearchGbn", "2");

            HashMap<String, HashMap<String, String>> jobInfo = new HashMap<String, HashMap<String, String>>();
            jobInfo.put(ENGINE_JOB_MODULE_KEY, module);
            jobInfo.put(ENGINE_JOB_PARAM_LOGIN_KEY, paramLogin);
            jobInfo.put(ENGINE_JOB_PARAM_INFO_KEY, paramInfo);
            jobInfo.put(ENGINE_JOB_PARAMEXT_INFO_KEY, new HashMap<String, String>());

            jobs.add(jobInfo);
        }

//
//        if (_worker != null) {
//            _worker.dispose();
//            _worker = null;
//        }

        _worker = new ESpiderWorker(mEngine, reactContext);
        _worker.runJobs(jobs, promise);
    }

    @ReactMethod
    public void getStocksByJobs(ReadableArray rows, Promise promise) {

        ArrayList<HashMap<String, HashMap<String, String>>> jobs = new ArrayList<>();

        for (Object o : rows.toArrayList()) {
            HashMap<String, Object> row = (HashMap<String, Object>) o;

            String method = row.get("method").toString().equals("username") ? "311010" : "310010";

            String s4code = row.get("s4code").toString();
            String username = row.get("id").toString();
            String password = row.get("pw").toString();

            String reqCertFile = row.get("certPath").toString();
            String reqKeyFile  = row.get("keyPath").toString();
            String reqCertPass = row.get("certPass").toString();

            String account = row.get("account").toString();
            String accountPass = row.get("accountPass").toString();

            HashMap<String, String> module = new HashMap<String, String>();
            module.put("country", "KR");
            module.put("organization", "ST");
            module.put("suborganization", s4code);
            module.put("code", method);
            module.put("module_disply_name", "details");

            HashMap<String, String> paramLogin = new HashMap<String, String>();
            paramLogin.put("reqUserId", username);
            paramLogin.put("reqUserPass", password);
            paramLogin.put("reqKeyFile", reqKeyFile);
            paramLogin.put("reqCertFile", reqCertFile);
            paramLogin.put("reqCertPass", reqCertPass);

            HashMap<String, String> paramInfo = new HashMap<String, String>();
            paramInfo.put("reqAccount", account);
            paramInfo.put("reqAccountPass", accountPass);
            paramInfo.put("reqSearchGbn", "2");

            HashMap<String, HashMap<String, String>> jobInfo = new HashMap<String, HashMap<String, String>>();
            jobInfo.put(ENGINE_JOB_MODULE_KEY, module);
            jobInfo.put(ENGINE_JOB_PARAM_LOGIN_KEY, paramLogin);
            jobInfo.put(ENGINE_JOB_PARAM_INFO_KEY, paramInfo);
            jobInfo.put(ENGINE_JOB_PARAMEXT_INFO_KEY, new HashMap<String, String>());

            jobs.add(jobInfo);
        }


        if (_worker != null) {
            _worker.dispose();
            _worker = null;
        }

        _worker = new ESpiderWorker(mEngine, reactContext);
        _worker.runJobs(jobs, promise);
    }

    @ReactMethod
    public void validPassByLogin(String s4code, String account, String accountPass, String username, String password, Promise promise) {

        ArrayList<HashMap<String, HashMap<String, String>>> jobs = new ArrayList<>();

        HashMap<String, String> module = new HashMap<String, String>();
        module.put("country", "KR");
        module.put("organization", "ST");
        module.put("suborganization", s4code);
        module.put("code", "311010");
        module.put("module_disply_name", "details");

        HashMap<String, String> paramLogin = new HashMap<String, String>();
        paramLogin.put("reqUserId", username);
        paramLogin.put("reqUserPass", password);

        HashMap<String, String> paramInfo = new HashMap<String, String>();
        paramInfo.put("reqAccount", account);
        paramInfo.put("reqAccountPass", accountPass);
        paramInfo.put("reqSearchGbn", "1");

        HashMap<String, HashMap<String, String>> jobInfo = new HashMap<String, HashMap<String, String>>();
        jobInfo.put(ENGINE_JOB_MODULE_KEY, module);
        jobInfo.put(ENGINE_JOB_PARAM_LOGIN_KEY, paramLogin);
        jobInfo.put(ENGINE_JOB_PARAM_INFO_KEY, paramInfo);
        jobInfo.put(ENGINE_JOB_PARAMEXT_INFO_KEY, new HashMap<String, String>());

        jobs.add(jobInfo);

        if (_worker != null) {
            _worker.dispose();
            _worker = null;
        }

        _worker = new ESpiderWorker(mEngine, reactContext);
        _worker.runJobs(jobs, promise);
    }

    @ReactMethod
    public void validPassByCert(String s4code, String account, String accountPass, String certPath, String keyPath, String username, String password, Promise promise) {

        ArrayList<HashMap<String, HashMap<String, String>>> jobs = new ArrayList<>();

        HashMap<String, String> module = new HashMap<String, String>();
        module.put("country", "KR");
        module.put("organization", "ST");
        module.put("suborganization", s4code);
        module.put("code", "310010");
        module.put("module_disply_name", "details");

        HashMap<String, String> paramLogin = new HashMap<String, String>();
        paramLogin.put("reqKeyFile", keyPath);
        paramLogin.put("reqCertFile", certPath);
        paramLogin.put("reqCertPass", password);
        paramLogin.put("reqUserId", username);

        HashMap<String, String> paramInfo = new HashMap<String, String>();
        paramInfo.put("reqAccount", account);
        paramInfo.put("reqAccountPass", accountPass);
        paramInfo.put("reqSearchGbn", "1");

        HashMap<String, HashMap<String, String>> jobInfo = new HashMap<String, HashMap<String, String>>();
        jobInfo.put(ENGINE_JOB_MODULE_KEY, module);
        jobInfo.put(ENGINE_JOB_PARAM_LOGIN_KEY, paramLogin);
        jobInfo.put(ENGINE_JOB_PARAM_INFO_KEY, paramInfo);
        jobInfo.put(ENGINE_JOB_PARAMEXT_INFO_KEY, new HashMap<String, String>());

        jobs.add(jobInfo);

        if (_worker != null) {
            _worker.dispose();
            _worker = null;
        }

        _worker = new ESpiderWorker(mEngine, reactContext);
        _worker.runJobs(jobs, promise);
    }

}
