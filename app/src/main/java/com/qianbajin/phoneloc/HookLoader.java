package com.qianbajin.phoneloc;

import android.util.Log;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import dalvik.system.PathClassLoader;
import de.robv.android.xposed.IXposedHookLoadPackage;
import de.robv.android.xposed.XSharedPreferences;
import de.robv.android.xposed.XposedBridge;
import de.robv.android.xposed.callbacks.XC_LoadPackage;
/**
 * ----------------------
 * 代码千万行
 * 注释第一行
 * 代码不注释
 * 改bug两行泪
 * -----------------------
 *
 * @author weiyitai
 * @date 2019/7/10 0010  15:03
 */
public class HookLoader implements IXposedHookLoadPackage {

    /**
     * 实际处理hook逻辑的类
     */
    private static final String HANDLE_HOOK_CLASS = HookPhone.class.getName();

    /**
     * 需要hook的应用包名
     */
    private static final List<String> hookPkg = new ArrayList<>();

    /**
     * 添加需要hook的程序包名
     */
    static {
        hookPkg.add(BuildConfig.APPLICATION_ID);
        hookPkg.add(Constant.APK_CONTACTS);
    }

    @Override
    public void handleLoadPackage(XC_LoadPackage.LoadPackageParam lpparam) throws Throwable {
        try {
            if (hookPkg.contains(lpparam.packageName)) {
                XSharedPreferences preferences = new XSharedPreferences(BuildConfig.APPLICATION_ID, Constant.HOOK);
                preferences.reload();
                boolean aBoolean = preferences.getBoolean(Constant.HOOK, true);
                Logger.d("HookLoader", "aBoolean:" + aBoolean + "  " + lpparam.packageName);
                if (aBoolean) {
                    String apkPath = preferences.getString(Constant.APK_DIR, "");
                    PathClassLoader pathClassLoader = new PathClassLoader(apkPath, XposedBridge.BOOTCLASSLOADER);
                    Class<?> handleHookClass = pathClassLoader.loadClass(HANDLE_HOOK_CLASS);
                    Logger.d("HookLoader", "handleHookClass:" + handleHookClass + " " + pathClassLoader);
                    Object instance = handleHookClass.newInstance();
                    Method handleLoadPackage = handleHookClass.getDeclaredMethod("handleLoadPackage", XC_LoadPackage.LoadPackageParam.class);
                    handleLoadPackage.invoke(instance, lpparam);
                }
            } else {
                Logger.d("HookLoader", "不hook此应用:" + lpparam.packageName);
            }
        } catch (Throwable e) {
            Logger.d("HookLoader", Log.getStackTraceString(e));

        }
    }
}
