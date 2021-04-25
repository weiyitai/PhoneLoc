package com.qianbajin.phoneloc;

import android.annotation.TargetApi;
import android.os.Build;
import android.view.Menu;
import android.view.View;
import android.widget.PopupMenu;
/**
 * ----------------------
 * 代码千万行
 * 注释第一行
 * 代码不注释
 * 改bug两行泪
 * -----------------------
 *
 * @author weiyitai
 * @date 2019/8/12 0012  15:38
 */
public class PopHelper {

    public static void showPop(View anchorView, int arrayIds, PopItemClickListener listener) {
        String[] array = anchorView.getContext().getResources().getStringArray(arrayIds);
        showPop(anchorView, array, listener);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP_MR1)
    static void showPop(View anchorView, String[] array, PopItemClickListener listener) {
        PopupMenu popupMenu = new PopupMenu(anchorView.getContext(), anchorView);
        Menu menu = popupMenu.getMenu();
        for (int i = 0; i < array.length; i++) {
            menu.add(0, i, 0, array[i]);
        }
        popupMenu.setOnMenuItemClickListener(item -> {
            if (listener != null) {
                listener.onPopItemClick(item.getItemId());
            }
            return true;
        });
        popupMenu.show();
    }

    public interface PopItemClickListener {

        /**
         * 点击
         *
         * @param which 位置
         */
        void onPopItemClick(int which);
    }

}
