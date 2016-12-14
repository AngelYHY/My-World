package freestar.myapp;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by freestar on 2016/12/13.
 */

public class MySql extends SQLiteOpenHelper {

    public MySql(Context context) {
        super(context, "mydb.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql = "create table User(id integer primary key autoincrement , account text , psw text , sex text , birthday text , sign text)";
        String sql2 = "create table Diary(id integer primary key autoincrement , account text , time text , title text , content text)";
        String sql3 = "create table Birthday(id integer primary key autoincrement , account text , name text , birthday text)";
        db.execSQL(sql);
        db.execSQL(sql2);
        db.execSQL(sql3);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}
