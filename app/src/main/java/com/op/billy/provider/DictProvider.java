package com.op.billy.provider;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.op.billy.provider.dao.DictDatabaseHelper;

import java.io.File;

public class DictProvider extends ContentProvider {
    private final String TAG = DictProvider.class.getSimpleName();
    private static final String AUTHORITY = "com.op.billy.dict.provider";
    private static UriMatcher mUriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
    private static final String  DICT_PATH = "dict";
    private static final String  DICT__DESCRIBE_PATH = "describe";
    private static final int  CODE_DICT = 1;
    private static final int  CODE_DICTS= 1 << 1;
    private static final int  CODE_DESCRIBE = CODE_DICT << 2;
    private static final int  CODE_DESCRIBES= CODE_DICT << 3;
    private static final Uri DICT_URI = Uri.parse("content://" + AUTHORITY + File.separator + DICT_PATH);
    private static final Uri DICT_DESCRIBE_URI = Uri.parse("content://" + AUTHORITY + File.separator + DICT__DESCRIBE_PATH);
    static {
        mUriMatcher.addURI(AUTHORITY, DICT_PATH, CODE_DICT);
        mUriMatcher.addURI(AUTHORITY, DICT__DESCRIBE_PATH, CODE_DESCRIBE);
    }

    private DictDatabaseHelper mDbHelper;

    @Override
    public boolean onCreate() {
        Log.d(TAG, "onCreate");
        mDbHelper = new DictDatabaseHelper(getContext(), "dict_db", null, 1);
        return true;
    }

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] projection, @Nullable String selection, @Nullable String[] selectionArgs, @Nullable String sortOrder) {
        Log.d(TAG, "query");
        SQLiteDatabase db = mDbHelper.getReadableDatabase();
        switch (mUriMatcher.match(uri)) {
            case CODE_DICT:
                Log.d(TAG, "CODE_DICT");
                //执行查询
                return db.query(DictDatabaseHelper.TABLE_DICT, projection, selection, selectionArgs,   null, null, sortOrder);
            case CODE_DESCRIBE:
                Log.d(TAG, "CODE_DESCRIBE");
                return db.query(DictDatabaseHelper.TABLE_DICT, projection, selection, selectionArgs, null, null, sortOrder);
            default:
        }
        return null;
    }


    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        Log.d(TAG, "getType");
        //返回指定Uri参数对应的数据的MIME TYPE 类型
        switch (mUriMatcher.match(uri)) {
            case CODE_DICT:
                return "vnd.android.cursor.item/op.dict";
            case CODE_DESCRIBE:
                return "vnd.android.cursor.item/op.describe";
            case CODE_DICTS:
                return "vnd.android.cursor.dir/op.dicts";
            case CODE_DESCRIBES:
                return "vnd.android.cursor.dir/op.describes";
        }
        return null;
    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues values) {
        Log.d(TAG, "insert");
        SQLiteDatabase db = mDbHelper.getWritableDatabase();
        switch (mUriMatcher.match(uri)) {
            case CODE_DICT:
                long rowId = db.insert(DictDatabaseHelper.TABLE_DICT, DictDatabaseHelper.COLUMN_WORD, values);
                if(rowId > 0) {
                    Log.d(TAG, "rowId = "+rowId);
                    Uri wordUri = ContentUris.withAppendedId(uri, rowId);
                    notifyDataChanged(uri);
                }
            case CODE_DESCRIBE:
            case CODE_DICTS:
            case CODE_DESCRIBES:
        }
        return null;
    }

    @Override
    public int delete(@NonNull Uri uri, @Nullable String selection, @Nullable String[] selectionArgs) {
        SQLiteDatabase db = mDbHelper.getWritableDatabase();
        db.delete(DictDatabaseHelper.TABLE_DICT, selection, selectionArgs);
        notifyDataChanged(uri);
        Log.d(TAG, "delete");
        return 0;
    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues values, @Nullable String selection, @Nullable String[] selectionArgs) {
        SQLiteDatabase db = mDbHelper.getWritableDatabase();
        db.update(DictDatabaseHelper.TABLE_DICT, values, selection, selectionArgs);
        notifyDataChanged(uri);
        Log.d(TAG, "update");
        return 0;
    }

    private void notifyDataChanged(Uri uri) {
        getContext().getContentResolver().notifyChange(uri, null);
    }
}
