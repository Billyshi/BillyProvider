package com.op.billy.provider.dao;

import android.content.Context;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class DictDatabaseHelper extends SQLiteOpenHelper {

    private final String TAG = DictDatabaseHelper.class.getSimpleName();
    public static final String TABLE_DICT = "dict_table";
    public static final String COLUMN_WORD = "word";
    public static final String COLUMN_DDESCRIBE= "describe";

    private final String CREATE_TABLE_AQL = "create table "+TABLE_DICT
            +" (_id integer primary key autoincrement, "
            + COLUMN_WORD +", "
            +COLUMN_DDESCRIBE +")";
    public DictDatabaseHelper(@Nullable Context context,@Nullable String name,  @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, null, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        Log.d(TAG, "onCreate");
        //第一次使用数据库时建表
        db.execSQL(CREATE_TABLE_AQL);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.d(TAG, "onUpgrade, oldVersion = "+ oldVersion + ", newVersion = " + newVersion);
    }
}
