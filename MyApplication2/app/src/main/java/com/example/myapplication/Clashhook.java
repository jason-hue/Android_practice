package com.example.myapplication;

import android.content.Context;
import android.util.Log;
import android.view.ViewParent;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;
import de.robv.android.xposed.IXposedHookLoadPackage;
import de.robv.android.xposed.XC_MethodHook;
import de.robv.android.xposed.XposedBridge;
import de.robv.android.xposed.XposedHelpers;
import de.robv.android.xposed.callbacks.XC_LoadPackage;

import java.lang.reflect.Field;

//MyHook类名可以修改，但一定要和新建的类名一致
public class Clashhook implements IXposedHookLoadPackage {


String TAG="mydebug";

    public void handleLoadPackage(XC_LoadPackage.LoadPackageParam lpparam) throws Throwable {
        if(lpparam.packageName.equals("com.droider.crackme0201")) {
            Log.d("mydebug", "找到目标程序");
            XposedBridge.log("hook成功");
            XposedHelpers.findAndHookMethod("com.droider.crackme0201.MainActivity", lpparam.classLoader, "checkSN", java.lang.String.class, java.lang.String.class, new XC_MethodHook() {
                @Override
                protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
                    super.beforeHookedMethod(param);
                }
                @Override
                protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                    super.afterHookedMethod(param);
                    param.setResult(true);
                   try {
                       Log.d(TAG, "1 success");
                       Object context = param.thisObject;
                       Class<?> clazz = context.getClass();
                       Field UserName = clazz.getDeclaredField("edit_userName");
                       UserName.setAccessible(true);
                       EditText username = (EditText) UserName.get(context);
                       ViewParent parent = username.getParent();
                       LinearLayout parent1 = (LinearLayout) parent;
                       EditText ve = new EditText((Context) context);
                       parent1.addView(ve);
                   }catch (Exception e){
                       Log.e(TAG, String.valueOf(e));
                   }
                    Log.d(TAG, "succ");


                }
            });



        };
    }
}
