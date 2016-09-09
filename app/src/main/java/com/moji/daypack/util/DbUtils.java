package com.moji.daypack.util;

import android.content.Context;

import com.moji.daypack.data.source.local.db.DatabaseHelper;

/**
 * Created with Android Studio.
 * User: ryan.hoo.j@gmail.com
 * Date: 7/3/16
 * Time: 10:23 PM
 * Desc: DbUtils
 */
public class DbUtils {

    public static void clearDataBase(Context context) {
        new DatabaseHelper(context).clearDatabase();
    }
}
