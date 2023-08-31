package com.example.myapplication;
import de.robv.android.xposed.IXposedHookLoadPackage;
import de.robv.android.xposed.XC_MethodHook;
import de.robv.android.xposed.XposedBridge;
import de.robv.android.xposed.XposedHelpers;
import de.robv.android.xposed.callbacks.XC_LoadPackage;

public class Clashhook implements IXposedHookLoadPackage{

    public void handleLoadPackage(XC_LoadPackage.LoadPackageParam lpparam) throws Throwable {
        if(lpparam.packageName.equals("")) {
            XposedHelpers.findAndHookMethod("类名", lpparam.classLoader,"函数名", new XC_MethodHook() {
                @Override
                protected void afterHookedMethod(MethodHookParam param)
                        throws Throwable {


                    Boolean result = true;//默认返回true
                    param.setResult(result);
                }
            });
        };
    }
}
