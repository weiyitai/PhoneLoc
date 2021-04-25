package com.qianbajin.phoneloc;

import android.content.Context;
import android.graphics.Color;
import android.graphics.PixelFormat;
import android.os.Build;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;
/**
 * @author qianbajin
 * @date at 2021/4/24 0024  21:21
 */
public class FloatView {

    private WindowManager mWm;
    private Context mContext;

    private static final WindowManager.LayoutParams LAYOUT_PARAMS;
    static {
        WindowManager.LayoutParams params = new WindowManager.LayoutParams();
        params.width = WindowManager.LayoutParams.WRAP_CONTENT;
        params.height = WindowManager.LayoutParams.WRAP_CONTENT;
        params.gravity = Gravity.START | Gravity.TOP;
        params.type = Build.VERSION.SDK_INT >= Build.VERSION_CODES.O ?
            WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY : WindowManager.LayoutParams.TYPE_SYSTEM_ALERT;
        params.format = PixelFormat.RGBA_8888;
        params.flags = WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL
            | WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE;

        LAYOUT_PARAMS = params;
    }

    public FloatView(Context context) {
        init(context);
    }

    private void init(Context context) {
        mContext = context;
        mWm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);

    }

    public void addView() {
        TextView textView = new TextView(mContext);
        textView.setText("点击弹出菜单");
        textView.setTextColor(mContext.getResources().getColor(android.R.color.holo_blue_bright));
        textView.setSingleLine();
        textView.setBackgroundColor(Color.BLACK);
        textView.setAlpha(0.7f);
        textView.setPadding(12, 8, 12, 8);
        textView.setTextSize(12);
        textView.measure(0, 0);
        textView.setWidth(textView.getMeasuredWidth() + 10);
        textView.setOnClickListener(this::showMenu);
        mWm.addView(textView, LAYOUT_PARAMS);
    }

    private void showMenu(View view) {
        String[] array = {"弹框", "查询"};
        PopHelper.showPop(view, array, which -> {
            if (which == 0) {
                DialogHelper.show();
            } else {
                new PhoneDao(mContext).query();
            }
        });
    }
}
