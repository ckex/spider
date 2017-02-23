package com.mljr.operators.common.utils;

import com.mljr.operators.entity.LoginResponse;

import javax.script.Invocable;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * Created by songchi on 17/2/10.
 */
public class JsUtils {

    public String invoke(String functionName, Object... args) {
        InputStream in = this.getClass().getResourceAsStream("/static/js/des.js");
        ScriptEngineManager manager = new ScriptEngineManager();
        ScriptEngine engine = manager.getEngineByName("javascript");

        BufferedReader reader = null;   // 执行指定脚本
        String ret = "";
        try {
            reader = new BufferedReader(new InputStreamReader(in));
            engine.eval(reader);

            if (engine instanceof Invocable) {
                Invocable invoke = (Invocable) engine;

                ret = (String) invoke.invokeFunction(functionName, args);

            }
            reader.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return ret;

    }

    public static String enString(String data) {
        JsUtils jsUtils = new JsUtils();
        return jsUtils.invoke("enString", data);
    }

    public static String getJumpUrl(LoginResponse response) {
        JsUtils jsUtils = new JsUtils();
        return jsUtils.invoke("getJumpUrl", response.getUid(), response.getArtifact(), response.getTransactionID());
    }
}
