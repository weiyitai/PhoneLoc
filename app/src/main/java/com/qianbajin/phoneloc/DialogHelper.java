package com.qianbajin.phoneloc;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.AndroidAppHelper;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.AsyncTask;
import android.text.TextUtils;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
/**
 * @author qianbajin
 * @date at 2021/4/24 0024  21:45
 */
public class DialogHelper {

    public static void show() {
        Activity activity = HookAddView.getActivity();
        if (activity == null) {
            return;
        }

        LinearLayout container = new LinearLayout(activity);
        container.setBackgroundColor(Color.GRAY);
        container.setOrientation(LinearLayout.VERTICAL);
        TextView textView = new TextView(activity);
        textView.setText("请输入开头的3位手机号或7位手机号");
        container.addView(textView);
        TipInputView phone = new TipInputView(activity, "开头3位");
        TipInputView userId = new TipInputView(activity, "中间4位");
        TipInputView beginFirst = new TipInputView(activity, "校验次数");
        container.addView(phone);
        container.addView(userId);
        container.addView(beginFirst);

        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        builder.setView(container)
            .setNegativeButton(android.R.string.cancel, (dialog, which) -> destroyDialog(dialog))
            .setCancelable(false)
            .setPositiveButton(android.R.string.ok, (dialog, which) -> {
                String start = phone.getText().trim();
                String middle = userId.getText().trim();
                String count = beginFirst.getText().trim();
                keepDialog(dialog);
                onConfirm(parseInt(start), parseInt(middle), parseInt(count));
            });
        try {
            builder.show();
        } catch (WindowManager.BadTokenException e) {
            e.printStackTrace();
        }
    }

    public static void onConfirm(int start, int middle, int count) {
        start = middle == 0 ? start * 10000 : start * 10000 + middle;
        int end = start + 10000;
        int finalStart = start;
        AsyncTask.execute(() -> {
            int c = 0;
            List<PhoneLoc> list = new ArrayList<>();
            for (int i = finalStart; i < end; i++) {
                c++;
                if (c > count) {
                    break;
                }
                PhoneLoc phoneLoc = ContactUtil.getLoc(i + "9999");
                if (!TextUtils.isEmpty(phoneLoc.getCity())) {
                    phoneLoc.setPhone(i);
                    list.add(phoneLoc);
                }
            }
            new PhoneDao(AndroidAppHelper.currentApplication()).insert(list);
        });
    }

    private static int parseInt(String count) {
        if (TextUtils.isEmpty(count)) {
            return 0;
        }
        try {
            return Integer.parseInt(count);
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public static void keepDialog(DialogInterface dialog) {
        try {
            Field field = dialog.getClass().getSuperclass().getDeclaredField("mShowing");
            field.setAccessible(true);
            field.set(dialog, false);
        } catch (Exception ex) {
            dialog.dismiss();
        }
    }

    public static void destroyDialog(DialogInterface dialog) {
        try {
            Field field = dialog.getClass().getSuperclass().getDeclaredField("mShowing");
            field.setAccessible(true);
            field.set(dialog, true);
        } catch (Exception ex) {
            dialog.dismiss();
        }
    }

}
