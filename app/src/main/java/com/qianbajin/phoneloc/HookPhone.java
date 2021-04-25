package com.qianbajin.phoneloc;

import de.robv.android.xposed.IXposedHookLoadPackage;
import de.robv.android.xposed.callbacks.XC_LoadPackage;
/**
 * @author qianbajin
 * @date at 2021/4/24 0024  21:05
 */
public class HookPhone implements IXposedHookLoadPackage {

    @Override
    public void handleLoadPackage(XC_LoadPackage.LoadPackageParam lpparam) throws Throwable {
        Util.setClassLoader(lpparam.classLoader);
        new HookAddView().hook();
    }
}
