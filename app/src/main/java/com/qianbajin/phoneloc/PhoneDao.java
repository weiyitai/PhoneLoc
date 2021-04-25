package com.qianbajin.phoneloc;

import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Environment;
import android.util.Log;

import java.io.File;
import java.util.List;
/**
 * @author qianbajin
 * @date at 2021/4/24 0024  22:55
 */
public class PhoneDao extends SQLiteOpenHelper {

    private static final String TAG = PhoneDao.class.getSimpleName();

    private static final int DB_VERSION = 1;
    String CREATE_SQL = "CREATE TABLE IF NOT EXISTS PHONE" +
        "(_id INTEGER PRIMARY KEY AUTOINCREMENT," +
        "province varchar (10)," +
        "city varchar (10)," +
        "province_city varchar (10)," +
        "sp varchar (10)," +
        "phone Long)";
    public PhoneDao(Context context) {
        super(context, getDbFile(), null, DB_VERSION);
    }

    public static String getDbFile() {
        return new File(Environment.getExternalStorageDirectory(),"1/phone.db").getAbsolutePath();
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        Log.d(TAG, "onCreate:" + db);
        db.execSQL(CREATE_SQL);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    }

    public String query() {
        SQLiteDatabase readableDatabase = getReadableDatabase();
        StringBuilder builder = new StringBuilder();
        Cursor cursor = readableDatabase.rawQuery("select province,city,province_city,sp,phone from PHONE limit 20", null);
        if (cursor != null) {
            while (cursor.moveToNext()) {
                String city = cursor.getString(1);
                Log.d(TAG, "city:" + city);
                builder.append(city).append("   ");
            }
            cursor.close();
        }
        return builder.toString();
    }

    public void insert(List<PhoneLoc> list) {
        if (list.isEmpty()) {
            Log.d(TAG, "list.isEmpty()");
            return;
        }
        StringBuilder builder = new StringBuilder(512);
        builder.append("insert into PHONE(province,city,province_city,sp,phone) values ");
        for (PhoneLoc result : list) {
            builder
                .append('(').append('\'').append('\'').append(',')
                .append('\'').append(result.getCity()).append('\'').append(',')
                .append('\'').append(result.getCity()).append('\'').append(',')
                .append('\'').append(result.getSp()).append('\'').append(',')
                .append('\'').append(result.getPhone()).append('\'').append(')').append(',');
        }
        String sql = builder.substring(0, builder.length() - 1);
        Log.d(TAG, "sql:" + sql);
        try {
            getWritableDatabase().execSQL(sql);
        } catch (SQLException e) {
            e.printStackTrace();
            Logger.d(TAG, "SQLException:" + e + "\n " + sql);
        }
//        try {
//            Class.forName("org.sqlite.JDBC");
//            Connection c = DriverManager.getConnection("jdbc:sqlite:phone.db");
//            System.out.println("Opened database successfully");
//            Statement statement = c.createStatement();
//            statement.execute(sql);
//            statement.close();
//            c.close();
//            System.out.println("finish");
//        } catch (ClassNotFoundException | SQLException e) {
//            e.printStackTrace();
//        }

    }
}
