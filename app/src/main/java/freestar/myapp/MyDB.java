package freestar.myapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

/**
 * Created by Administrator on 2016/4/11 0011.
 */
public class MyDB {
    private Context context;
    private MySql helper;
    private SQLiteDatabase database;
    private Cursor cursor;
    private boolean flag;

    public MyDB(Context context) {
        this.context = context;
    }

    //指定一个数据库，如果数据库不存在就创建
    public void open() {
        helper = new MySql(context);
    }

    //关闭数据库
    private void close() {
        if (cursor != null) {
            cursor.close();
        }
        if (helper != null) {
            helper.close();
        }
    }

    //  验证帐号密码
    public boolean confirm(String account, String psw) {
        flag = false;
        open();
        database = helper.getReadableDatabase();
        cursor = database.rawQuery("select * from User where account=? and psw=?", new String[]{account, psw});
        if (cursor.getCount() == 1) {
            flag = true;
        }
        close();
        return flag;
    }

    //  注册
    public boolean add(String account, String psw) {
        open();
        database = helper.getWritableDatabase();
        cursor = database.rawQuery("select * from User where account=?", new String[]{account});
        if (cursor.getCount() != 0) {
            return false;
        }

        ContentValues values = new ContentValues();
        values.put("account", account);
        values.put("psw", psw);
        database.insert("User", null, values);
        close();
        return true;
    }

}