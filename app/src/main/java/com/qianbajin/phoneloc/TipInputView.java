package com.qianbajin.phoneloc;

import android.content.Context;
import android.text.InputType;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
/**
 * ----------------------
 * 代码千万行
 * 注释第一行
 * 代码不注释
 * 改bug两行泪
 * -----------------------
 *
 * @author qianbajin
 * @date at 2020/12/21 0021  22:35
 */
public class TipInputView extends LinearLayout {

    private TextView mTextView;
    private EditText mEditText;

    public TipInputView(Context context) {
        super(context);
        init(context);
    }

    public TipInputView(Context context, String tip) {
        super(context);
        init(context);
        setTip(tip);
    }

    private void init(Context context) {
        mTextView = new TextView(context);
        mEditText = new EditText(context);
        mEditText.setTextSize(14);
        mEditText.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        mEditText.setInputType(InputType.TYPE_CLASS_NUMBER);
        addView(mTextView);
        addView(mEditText);
    }

    public void setTip(String text) {
        mTextView.setText(text);
    }

    public String getText() {
        return mEditText.getText().toString();
    }
}
