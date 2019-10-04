package com.op.billy.provider;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.net.Uri;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.io.File;

public class DictProvider extends ContentProvider {
    private final String TAG = DictProvider.class.getSimpleName();
    private static final String AUTHORITY = "com.op.billy.dict.provider";
    private static UriMatcher mUriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
    private static final String  DICT_PATH = "dict";
    private static final String  DICT__DESCRIBE_PATH = "describe";
    private static final int  CODE_DICT = 1;
    private static final int  CODE_DESCRIBE = CODE_DICT << 1;
    private static final Uri DICT_URI = Uri.parse("content://" + AUTHORITY + File.separator + DICT_PATH);
    private static final Uri DICT_DESCRIBE_URI = Uri.parse("content://" + AUTHORITY + File.separator + DICT__DESCRIBE_PATH);
    static {
        mUriMatcher.addURI(AUTHORITY, DICT_PATH, CODE_DICT);
        mUriMatcher.addURI(AUTHORITY, DICT_PATH, CODE_DESCRIBE);
    }


    @Override
    public boolean onCreate() {
        Log.d(TAG, "onCreate");
        return false;
    }

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] projection, @Nullable String selection, @Nullable String[] selectionArgs, @Nullable String sortOrder) {
        Log.d(TAG, "query");
        switch (mUriMatcher.match(uri)) {
            case CODE_DICT:
                Log.d(TAG, "CODE_DICT");
                break;
            case CODE_DESCRIBE:
                Log.d(TAG, "CODE_DESCRIBE");
                break;
            default:
                notifyDataChanged(uri);
        }
        return null;
    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        notifyDataChanged(uri);
        Log.d(TAG, "getType");
        return null;
    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues values) {
        notifyDataChanged(uri);
        Log.d(TAG, "insert");
        return null;
    }

    @Override
    public int delete(@NonNull Uri uri, @Nullable String selection, @Nullable String[] selectionArgs) {
        notifyDataChanged(uri);
        Log.d(TAG, "delete");
        return 0;
    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues values, @Nullable String selection, @Nullable String[] selectionArgs) {
        notifyDataChanged(uri);
        Log.d(TAG, "update");
        return 0;
    }

    private void notifyDataChanged(Uri uri) {
        getContext().getContentResolver().notifyChange(uri, null);
    }
}
