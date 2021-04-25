package com.qianbajin.phoneloc;

import android.app.Activity;
import android.app.AndroidAppHelper;
import android.app.Application;
import android.os.Build;
import android.os.Bundle;
import android.os.Process;
import android.provider.Settings;
import android.widget.Toast;

import java.lang.ref.WeakReference;
import java.lang.reflect.Method;

import de.robv.android.xposed.XC_MethodHook;
import de.robv.android.xposed.XposedBridge;
import de.robv.android.xposed.XposedHelpers;
/**
 * @author qianbajin
 * @date at 2021/4/24 0024  21:14
 */
public class HookAddView {

    private static final String TAG = HookAddView.class.getSimpleName();

    public void hook() {
        Method onCreate = XposedHelpers.findMethodExact(Application.class, "onCreate");
        XposedBridge.hookMethod(onCreate, new XC_MethodHook() {
            private FloatView mFloatView;

            @Override
            protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                super.afterHookedMethod(param);
                Application app = (Application) param.thisObject;
                addCallBack(app);
                if (Constant.APK_CONTACTS.equals(AndroidAppHelper.currentProcessName())) {
                    Logger.d(TAG, "thisObject:" + app + "  " + AndroidAppHelper.currentProcessName() + "  " + Process.myPid());
                    if (Build.VERSION.SDK_INT > Build.VERSION_CODES.M && !Settings.canDrawOverlays(app)) {
                        Toast.makeText(app, "请到设置里允许支付宝在应用上层显示", Toast.LENGTH_SHORT).show();
                    } else {
                        if (mFloatView == null) {
                            mFloatView = new FloatView(app);
                            mFloatView.addView();
                        }
                    }
                }
            }
        });
    }

    private static WeakReference<Activity> sReference;
    private void addCallBack(Application app) {
        app.registerActivityLifecycleCallbacks(new Application.ActivityLifecycleCallbacks() {
            @Override
            public void onActivityCreated(Activity activity, Bundle savedInstanceState) {
                sReference = new WeakReference<>(activity);
            }

            @Override
            public void onActivityStarted(Activity activity) {

            }

            @Override
            public void onActivityResumed(Activity activity) {

            }

            @Override
            public void onActivityPaused(Activity activity) {

            }

            @Override
            public void onActivityStopped(Activity activity) {

            }

            @Override
            public void onActivitySaveInstanceState(Activity activity, Bundle outState) {

            }

            @Override
            public void onActivityDestroyed(Activity activity) {

            }
        });
    }

    public static Activity getActivity() {
        return sReference != null ? sReference.get() : null;
    }
}
