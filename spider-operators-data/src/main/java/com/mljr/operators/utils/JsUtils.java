package com.mljr.operators.utils;

import com.mljr.operators.entity.LoginResponse;

import javax.script.Invocable;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import java.io.FileReader;

/**
 * Created by songchi on 17/2/10.
 */
public class JsUtils {

    public static String jsFileName = ClassLoader.getSystemClassLoader().getResource("static/js/des.js").getPath();

    public static String invoke(String jsFileName, String functionName, Object... args) {
        ScriptEngineManager manager = new ScriptEngineManager();
        ScriptEngine engine = manager.getEngineByName("javascript");

        FileReader reader = null;   // 执行指定脚本
        String ret = "";
        try {
            reader = new FileReader(jsFileName);
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
        return invoke(jsFileName, "enString", data);
    }

    public static String getJumpUrl(LoginResponse response) {
        return invoke(jsFileName, "getJumpUrl", response.getUid(), response.getArtifact(), response.getTransactionID());
    }
}
