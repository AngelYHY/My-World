package freestar.myapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by Administrator on 2016/4/11 0011.
 * 数据库操作类
 */
public class MyDB {
    private Context context;
    private MySql helper;
    private SQLiteDatabase database;
    private Cursor cursor;

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
        boolean flag = false;
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
            close();
            return false;
        }

        ContentValues values = new ContentValues();
        values.put("account", account);
        values.put("psw", psw);
        database.insert("User", null, values);
        close();
        return true;
    }

    //更新性别
    public void updateSex(String sex) {
        open();
        database = helper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("sex", sex);
        database.update("User", values, "account=?", new String[]{MyApp.account});
        close();
    }

    //更新个性签名
    public void updateSign(String sign) {
        open();
        database = helper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("sign", sign);
        database.update("User", values, "account=?", new String[]{MyApp.account});
        close();
    }

    //更新帐号
    public void updateAccount(String newAccount) {
        open();
        database = helper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("account", newAccount);
        database.update("User", values, "account=?", new String[]{MyApp.account});
        close();
    }

    //更新密码
    public void updatePsw(String psw) {
        open();
        database = helper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("psw", psw);
        database.update("User", values, "account=?", new String[]{MyApp.account});
        close();
    }

    //获得当前登录的用户信息
    public User getUser() {
        open();
        database = helper.getWritableDatabase();
        User user = new User();
        cursor = database.query("User", null, "account=?", new String[]{MyApp.account}, null, null, null);
        if (cursor.moveToFirst()) {
            user.setPsw(cursor.getString(cursor.getColumnIndex("psw")));
            user.setBirthday(cursor.getString(cursor.getColumnIndex("birthday")));
            user.setSign(cursor.getString(cursor.getColumnIndex("sign")));
            user.setSex(cursor.getString(cursor.getColumnIndex("sex")));
        }
        close();
        return user;
    }

    //更新生日日期
    public void updateBirthday(String birthday) {
        open();
        database = helper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("birthday", birthday);
        database.update("User", values, "account=?", new String[]{MyApp.account});
        close();
    }

    //查询帐号是否存在
    public boolean queryAccount(String account) {
        open();
        database = helper.getWritableDatabase();
        cursor = database.rawQuery("select * from User where account=?", new String[]{account});
        if (cursor.getCount() != 0) {
            close();
            return false;
        }
        close();
        return true;
    }

    //添加日记
    public void addDiary(String title, String content) {
        open();
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式
        database = helper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("account", MyApp.account);
        values.put("title", title);
        values.put("content", content);
        values.put("time", df.format(new Date()));
        database.insert("Diary", null, values);
        close();
    }

    //添加生日记录
    public void addBirthday(String name, String birthday) {
        open();
        database = helper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("account", MyApp.account);
        values.put("name", name);
        values.put("birthday", birthday);
        database.insert("Birthday", null, values);
        close();
    }

    //获得生日记录集合
    public List<Birthday> getBirthdayList() {
        List<Birthday> list = new ArrayList<>();
        open();
        database = helper.getWritableDatabase();
        Birthday birthday;
        cursor = database.query("Birthday", null, "account=?", new String[]{MyApp.account}, null, null, null);
        if (cursor.moveToFirst()) {
            do {
                birthday = new Birthday();
                birthday.setId(cursor.getString(cursor.getColumnIndex("id")));
                birthday.setName(cursor.getString(cursor.getColumnIndex("name")));
                birthday.setTime(cursor.getString(cursor.getColumnIndex("birthday")));
                list.add(birthday);
            } while (cursor.moveToNext());
        }
        close();
        return list;
    }

    //获得日记集合
    public List<Diary> getDiaryList() {
        List<Diary> list = new ArrayList<>();
        open();
        database = helper.getWritableDatabase();
        Diary diary;
        cursor = database.query("Diary", null, "account=?", new String[]{MyApp.account}, null, null, null);
        if (cursor.moveToFirst()) {
            do {
                diary = new Diary();
                diary.setTitle(cursor.getString(cursor.getColumnIndex("title")));
                diary.setContent(cursor.getString(cursor.getColumnIndex("content")));
                diary.setTime(cursor.getString(cursor.getColumnIndex("time")));

                list.add(diary);
            } while (cursor.moveToNext());
        }
        close();
        return list;
    }

    //更新生日记录中的生日日期
    public void updateDate(String birthday, String id) {
        open();
        database = helper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("birthday", birthday);
        database.update("Birthday", values, "id=?", new String[]{id});
        close();
    }

    //删除一条生日记录
    public void deleteBirthday(String id) {
        open();
        database = helper.getWritableDatabase();
        database.delete("Birthday", "id=?", new String[]{id});
        close();
    }
}