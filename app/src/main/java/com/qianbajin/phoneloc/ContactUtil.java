package com.qianbajin.phoneloc;

import android.app.AndroidAppHelper;
import android.content.Context;
import android.util.Log;

import java.lang.reflect.Method;

import de.robv.android.xposed.XposedHelpers;
/**
 * @author qianbajin
 * @date at 2021/4/24 0024  21:31
 */
public class ContactUtil {

    private static final String TAG = ContactUtil.class.getSimpleName();
    private static Method sParse;
    private static Method sGetLocation;
    private static Method sGetOperator;
    private static Context sContext = AndroidAppHelper.currentApplication().getApplicationContext();

    public static PhoneLoc getLoc(String phone) {
        Log.d(TAG, "phone:" + phone);
        try {
            Method parse = getParse();
            Object invoke = parse.invoke(null, phone, true, "00");
            String loc = (String) sGetLocation.invoke(invoke, sContext);
            String sp = (String) sGetOperator.invoke(invoke, sContext);
            PhoneLoc phoneLoc = new PhoneLoc(loc, sp);
            Log.d(TAG, "phoneLocs:" + phoneLoc);
            return phoneLoc;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    private static Method getParse() throws Exception {
        if (sParse == null) {
            Class<?> aClass = Util.getClassLoader().loadClass("miui.telephony.PhoneNumberUtilsCompat$PhoneNumberCompat");
            sParse = XposedHelpers.findMethodExact(aClass, "parse", CharSequence.class, boolean.class, String.class);
            sGetLocation = XposedHelpers.findMethodExact(aClass, "getLocation", Context.class);
            sGetOperator = XposedHelpers.findMethodExact(aClass, "getOperator", Context.class);
            Log.d("ContactUtil", "sParse:" + sParse + " \nsGetLocation:" + sGetLocation + " \nsGetOperator:" + sGetOperator);
        }
        return sParse;
    }

}
