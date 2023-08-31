package com.calvin.a360hook;


import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.SharedPreferences;
import android.text.Editable;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.webkit.JavascriptInterface;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.google.android.material.textfield.TextInputEditText;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.zip.GZIPInputStream;

import de.robv.android.xposed.IXposedHookLoadPackage;
import de.robv.android.xposed.XC_MethodHook;
import de.robv.android.xposed.XposedBridge;
import de.robv.android.xposed.XposedHelpers;
import de.robv.android.xposed.callbacks.XC_LoadPackage;

public class hook implements IXposedHookLoadPackage {
    //ClassLoader classLoader;

    public class AndroidtoJs extends Object {
        // 定义JS需要调用的方法
        // 被JS调用的方法必须加入@JavascriptInterface注解
        @SuppressLint("JavascriptInterface")
        @JavascriptInterface
        public void hello(String msg) {
            System.out.println("JS调用了Android的hello方法");
            Log.d(TAG, "hello: "+msg);
        }
    }
   static Object ywl;
    static   String TAG="ywl ywl";
   static Object webview;
   static Object CookieManager;
    Object getInstance;
    static Activity activity;

    public static boolean  isJH=false;

    StringBuffer message ;
    EditText editText;
    Context ZYhd;
    EditText a;
    @Override
    public void handleLoadPackage(XC_LoadPackage.LoadPackageParam loadPackageParam) throws Throwable {
        ClassLoader classLoader = loadPackageParam.classLoader;
        if(loadPackageParam.packageName.equals("com.lysoft.android.lyyd.report.mobile.hbmz")){
            Log.d(TAG, "找到目标程序！");
            try {



                XposedHelpers.findAndHookMethod("com.lysoft.android.report.mobile_campus.module.launch.view.MobileCampusLoginNewActivity", classLoader, "c", new XC_MethodHook() {
                    @Override
                    protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
                        super.beforeHookedMethod(param);
                    }
                    @Override
                    protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                        super.afterHookedMethod(param);
                        Object context = param.thisObject;

                        a=(EditText) XposedHelpers.getObjectField(context, "a");


                        View b = (View)XposedHelpers.getObjectField(context, "b");

                        LinearLayout parentLayout = (LinearLayout) b.getParent();
                        editText= new EditText((Context) context);
                        editText.setHint("输入激活码可获得全部答案");
                        parentLayout.addView(editText);
                        Log.d(TAG, "动态添加view成功！ ");


                    }
                });

                XposedHelpers.findAndHookMethod("com.lysoft.android.lyyd.report.baselibrary.framework.common.activity.BaseActivity", classLoader, "onCreate", android.os.Bundle.class, new XC_MethodHook() {
                    @Override
                    protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
                        super.beforeHookedMethod(param);
                    }
                    @Override
                    protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                        super.afterHookedMethod(param);


                        Log.d(TAG, "BaseActivity: ");
                        Object context = param.thisObject;
                        ZYhd=(Context) (context);
                        Toast.makeText(ZYhd,"免费解析单选题，使用方法直接打开做题界面等待3秒，你就已经复制答案了", Toast.LENGTH_LONG).show();
                        SharedPreferences sharedPreferences =ZYhd.getSharedPreferences("my_preferences", Context.MODE_PRIVATE);
                        boolean jh = sharedPreferences.getBoolean("jh", false);
                        isJH=jh;
                        Log.d(TAG, "is jh -->"+isJH);
                    }
                });


                XposedHelpers.findAndHookMethod("com.lysoft.android.report.mobile_campus.module.launch.view.MobileCampusLoginNewActivity$6", classLoader, "onClick", android.view.View.class, new XC_MethodHook() {
                    @Override
                    protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
                        super.beforeHookedMethod(param);
                        Editable text = editText.getText();
                        String key= text.toString();
                        String user = a.getText().toString();
                        Check(user,key);
                        //Log.d(TAG, "按钮被点击: "+key+"账号："+a.getText().toString());

                    }
                    @Override
                    protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                        super.afterHookedMethod(param);
                    }
                });












                Log.d(TAG, ": 开启cookie");
                XposedHelpers.findAndHookMethod("com.lysoft.android.report.mobile_campus.module.app.view.X5LightActivity", classLoader, "c", new XC_MethodHook() {
                    @Override
                    protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
                        super.beforeHookedMethod(param);
                    }
                    @Override
                    protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                        super.afterHookedMethod(param);
                        Log.d(TAG, "afterHookedMethod: 开始获取CookieManager");
                        activity=(Activity) param.thisObject;
                        CookieManager = XposedHelpers.callStaticMethod(XposedHelpers.findClass("com.tencent.smtt.sdk.CookieManager", classLoader), "getInstance");


//                Method setAcceptCookie = XposedHelpers.findMethodExact(getInstance.getClass(), "setAcceptCookie", boolean.class);

                        XposedHelpers.callMethod(getInstance, "setAcceptCookie", true);
                        Object thisObject1 = param.thisObject;
                        webview = XposedHelpers.getObjectField(thisObject1, "b");

                        Log.d(TAG, "拿到webview");

                        XposedHelpers.callMethod(CookieManager,"setAcceptThirdPartyCookies",webview,true);
                        Log.d(TAG, "绑定完成: ");

                    }
                });
                XposedHelpers.findAndHookMethod("com.tencent.smtt.sdk.SystemWebViewClient$e", classLoader, "getUrl", new XC_MethodHook() {
                    @Override
                    protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
                        super.beforeHookedMethod(param);
                    }
                    @Override
                    protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                        super.afterHookedMethod(param);
                        Object result = param.getResult();
                        Log.d(TAG, "获取到加载的 url :"+result);
                        String pattern = ".*getTopicByWj.*";
                        if(result.toString().matches(pattern)){
                            Object getCookie = XposedHelpers.callMethod(CookieManager, "getCookie", result.toString());
                            Log.d(TAG, "获取的cookie: "+getCookie);
                            if(result.toString().length()>0&&getCookie.toString().length()>0){
                                new Thread(new Runnable() {
                                    @Override
                                    public void run() {
                                        try {


                                            String s = postWithCookie(result.toString(), getCookie.toString());
                                            Log.d(TAG, "获取的数据："+s);
                                            message =new StringBuffer();
                                            try {
                                                // System.out.println(sb.toString().replaceAll(" ",""));
                                                JSONObject jsonObject = JSONObject.parseObject(s.replaceAll(" ",""));

                                                JSONArray data = jsonObject.getJSONArray("data");
                                                // System.out.println(data);
                                                for (int i = 0; i < data.size(); i++) {
                                                    JSONObject jsonObject1 = data.getJSONObject(i);
                                                    JSONArray answerVOs = jsonObject1.getJSONArray("answerVOs");
                                                    for (int i1 = 0; i1 < answerVOs.size(); i1++) {
                                                        JSONObject jsonObject2 = answerVOs.getJSONObject(i1);
                                                        Object answerContent = jsonObject2.get("answerContent");

                                                        Log.d(TAG, "答案第"+(i+1)+" "+answerContent);
                                                        if(isJH||i<20){
                                                            message.append("答案第"+(i+1)+" "+answerContent+"\n");
                                                        }

                                                    }

                                                }
                                                message.append("如果你没有激活软件 你只能获取答案的一半,需要激活联系 QQ：1518936272");




                                                // 获取系统的 ClipboardManager
                                                ClipboardManager clipboardManager = (ClipboardManager) activity.getSystemService(Context.CLIPBOARD_SERVICE);

// 设置要复制的文本
                                                String textToCopy =message.toString();

// 创建一个 ClipData 对象
                                                ClipData clipData = ClipData.newPlainText("Label", textToCopy);

// 将 ClipData 设置到 ClipboardManager 中
                                                clipboardManager.setPrimaryClip(clipData);

// 输出提示信息
//                                                    Toast.makeText(activity, "已复制到剪贴板", Toast.LENGTH_SHORT).show();


                                            } catch (Exception e) {
                                                throw new RuntimeException(e);
                                            }


                                        }catch (Exception e){
                                            Log.d(TAG, "run: "+e);
                                        }
                                    }
                                }).start();
                            }
                        }



                    }
                });
                XposedHelpers.findAndHookMethod("com.lysoft.android.report.mobile_campus.module.app.view.X5LightActivity$2", classLoader, "onPageFinished", XposedHelpers.findClass("com.tencent.smtt.sdk.WebView",classLoader), java.lang.String.class, new XC_MethodHook() {
                    @Override
                    protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
                        Log.d(TAG, "onPageFinished hook "+param.args[1]);


//                        if ((int)param.args[1]==100){




                            // 找到 com.tencent.smtt.sdk.WebView 类
                            Class<?> webViewClass = XposedHelpers.findClass("com.tencent.smtt.sdk.WebView", classLoader);

                            // 获取 evaluateJavascript 方法
                            Method evaluateJavascriptMethod = XposedHelpers.findMethodExact(webViewClass, "evaluateJavascript",
                                    String.class, XposedHelpers.findClass("com.tencent.smtt.sdk.ValueCallback", classLoader));

                            // 创建 ValueCallback<String> 实例
                            Object valueCallback = Proxy.newProxyInstance(classLoader,
                                    new Class<?>[]{XposedHelpers.findClass("com.tencent.smtt.sdk.ValueCallback", classLoader)},
                                    new InvocationHandler() {
                                        @Override
                                        public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                                            Log.d(TAG, "invoke: "+"回调执行");
                                            if(args.length>0){
                                                for (Object arg : args) {
                                                    Log.d(TAG, "invoke: "+arg);

                                                }
                                            }
                                            if (method.getName().equals("onReceiveValue")) {
                                                String result = (String) args[0]; // 获取 JavaScript 执行结果的返回值
                                                // 处理 JavaScript 执行结果
                                                // ...

                                                System.out.println("注入结果："+result);
                                            }
                                            return null;
                                        }
                                    });

                        Class X5CacheJsInteractionClass=classLoader.loadClass("com.lysoft.android.lyyd.report.baseapp.work.multimodule.webview.X5CacheJsInteraction");
                        Constructor constructor = X5CacheJsInteractionClass.getConstructor(param.args[0].getClass(), String.class);

                        ywl = constructor.newInstance(param.args[0], "ywl");


                       //XposedHelpers.callMethod(param.args[0], "addJavascriptInterface",ywl , "test");
                       // XposedHelpers.callMethod(param.args[0],"loadUrl","");



                        //javascript:window.Xposed.getSource(document.getElementsByTagName('html')[0].innerHTML);





                            // 调用 evaluateJavascript 方法
                           // XposedHelpers.callMethod(param.args[0], "evaluateJavascript", "alert();", valueCallback);




//                            WebView webView =(WebView) param.args[0];
//                            webView.loadUrl("javascript: alter('hello world');");

//                            Method declaredMethod = webView.getClass().getDeclaredMethod("loadUrl", String.class);
//                            declaredMethod.setAccessible(true);
//                            declaredMethod.invoke(webView,"javascript: "+hook);
                            Log.d(TAG, "js注入完成！");
//                        }
                        super.beforeHookedMethod(param);
                    }
                    @Override
                    protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                        super.afterHookedMethod(param);
                    }
                });




























            }catch (Exception e){
                Log.d(TAG, "报错"+e);
            }

        }


    }

    private void Check(String user, String key) {
        if(key.equals("")){
            key="0";
        }
      try {
          int num=0;
          for (byte aByte : user.getBytes()) {
              num+=aByte;
          }
          Integer integer = new Integer(key);
          int num2=integer;
          Log.d(TAG, "Check: "+num+"\t"+num2);
          SharedPreferences sharedPreferences =ZYhd.getSharedPreferences("my_preferences", Context.MODE_PRIVATE);
          if(num2==num){
              // 获取 SharedPreferences 对象


// 创建一个 SharedPreferences 编辑器
              SharedPreferences.Editor editor = sharedPreferences.edit();

// 存储数据e
              editor.putBoolean("jh",true);
// 提交保存数据
              editor.apply();



              isJH=true;
          }else{
              SharedPreferences.Editor editor = sharedPreferences.edit();

// 存储数据e
              editor.putBoolean("jh",false);
// 提交保存数据
              editor.apply();



              isJH=false;
          }
      }catch (Exception e){
          SharedPreferences sharedPreferences =ZYhd.getSharedPreferences("my_preferences", Context.MODE_PRIVATE);
          SharedPreferences.Editor editor = sharedPreferences.edit();

// 存储数据e
          editor.putBoolean("jh",false);
// 提交保存数据
          editor.apply();



          isJH=false;
      }
    }

    public static String postWithCookie(String url,String cookie) throws Exception {
        URL url1 = new URL(url);

        HttpURLConnection urlConnection = (HttpURLConnection)url1.openConnection();
        urlConnection.setRequestMethod("GET");

        //String cookie = "cid=54b2f684-2565-46cc-828e-6d5f232ea900";
        urlConnection.setRequestProperty("Cookie", cookie);

        urlConnection.setRequestProperty("Host","yx.hbmzu.edu.cn:6521");
        urlConnection.setRequestProperty("Connection","keep-alive");
        urlConnection.setRequestProperty("Pragma","no-cache");
        urlConnection.setRequestProperty("Cache-Control","no-cache");
        urlConnection.setRequestProperty("Authorization","eyJ0eXAiOiJqd3QiLCJhbGciOiJIUzI1NiJ9.eyJzdWIiOiIyMDIyMTg0MTEiLCJhdWQiOiJwYyIsImlzcyI6IkxJQU5ZSSIsImlhdCI6MTY5MjU5MjIzOTEzNSwianRpIjoiNTRiMmY2ODQtMjU2NS00NmNjLTgyOGUtNmQ1ZjIzMmVhOTAwIn0.A7ixjF0L40whi9aPMqISUI4zFnWwhBIbGtjDvODZxC0");
        urlConnection.setRequestProperty("User-Agent","Mozilla/5.0 (Linux; Android 13; M2012K11AC Build/TKQ1.220829.002; wv) AppleWebKit/537.36 (KHTML, like Gecko) Version/4.0 Chrome/104.0.5112.97 Mobile Safari/537.36");
        urlConnection.setRequestProperty("Content-Type","application/x-www-form-urlencoded;charset=UTF-8");
        urlConnection.setRequestProperty("Accept","*/*");
        urlConnection.setRequestProperty("X-Requested-With","com.lysoft.android.lyyd.report.mobile.hbmz");
        urlConnection.setRequestProperty("Referer","http://yx.hbmzu.edu.cn:6521/");
        urlConnection.setRequestProperty("Accept-Encoding","gzip, deflate");
        urlConnection.setRequestProperty("Accept-Language","zh-CN,zh;q=0.9,en-US;q=0.8,en;q=0.7");

        int responseCode = urlConnection.getResponseCode();
        if (responseCode == HttpURLConnection.HTTP_OK) {


            String contentType = urlConnection.getContentEncoding();

            if("gzip".equals(contentType)){
                GZIPInputStream gzipInputStream = new GZIPInputStream(urlConnection.getInputStream());
                StringBuffer sb = new StringBuffer();
                byte[] bytes = new byte[1024];
                int len=0;
                while((len=gzipInputStream.read(bytes))!=-1){
                    sb.append(new String(bytes, 0, len));
                }
                return sb.toString();
            }


            BufferedReader reader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
            String line;
            StringBuilder response = new StringBuilder();

            while ((line = reader.readLine()) != null) {
                response.append(line);
            }
            reader.close();

            // 处理响应数据
            String responseData = response.toString();
            return responseData;
        } else {
            // 请求未成功，处理错误
            return "null";
        }

    }

}
