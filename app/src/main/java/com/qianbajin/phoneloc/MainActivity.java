package com.qianbajin.phoneloc;

import android.Manifest;
import android.app.Activity;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Switch;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        SharedPreferences sp = getSharedPreferences(Constant.HOOK, MODE_WORLD_READABLE);
        sp.edit().putString(Constant.APK_DIR, getApplicationInfo().sourceDir).apply();
        Switch aSwitch = findViewById(R.id.sw);
        aSwitch.setOnCheckedChangeListener((buttonView, isChecked) ->
            sp.edit().putBoolean(Constant.FLOAT_VIEW, isChecked).apply());
        aSwitch.setChecked(sp.getBoolean(Constant.FLOAT_VIEW, true));
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            int i = checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE);
            if (i != PackageManager.PERMISSION_GRANTED) {
                requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 101);
            }
        }
    }

    public void btn1(View view){
        try {
            PhoneDao phoneDao = new PhoneDao(this);
            SQLiteDatabase readableDatabase = phoneDao.getReadableDatabase();
            Cursor cursor = readableDatabase.rawQuery("select sp from PHONE where _id=10007", null);
            if (cursor.moveToNext()) {
                String string = cursor.getString(0);
                Log.d("MainActivity", "sp:" + string);
            }
            cursor.close();
            SQLiteDatabase writableDatabase = phoneDao.getWritableDatabase();
            writableDatabase.execSQL("delete from PHONE where _id>10006");
            writableDatabase.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void btn2(View view){
        PhoneDao phoneDao = new PhoneDao(this);
        SQLiteDatabase readableDatabase = phoneDao.getReadableDatabase();

        
    }
}