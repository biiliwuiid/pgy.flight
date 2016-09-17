package com.moji.daypack.data.source.local;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.annotation.NonNull;

import com.moji.daypack.data.model.AppEntity;
import com.moji.daypack.data.source.AppContract;
import com.moji.daypack.data.source.local.db.tables.AppTable;

import java.util.List;

import rx.Observable;
import rx.functions.Func1;

/**
 * Created with Android Studio.
 * User: ryan.hoo.j@gmail.com
 * Date: 6/26/16
 * Time: 12:25 AM
 * Desc: LocalAppDataSource
 */
public class LocalAppDataSource extends AbstractLocalDataSource<AppTable> implements AppContract.Local {

    public LocalAppDataSource(Context context) {
        super(context);
    }

    @NonNull
    @Override
    protected AppTable instantiateTable() {
        return new AppTable();
    }

    @Override
    public Observable<List<AppEntity>> apps() {
        return mDatabaseHelper.createQuery(AppTable.TABLE_NAME, AppTable.QUERY_ALL_APPS)
                .mapToList(new Func1<Cursor, AppEntity>() {
                    @Override
                    public AppEntity call(Cursor cursor) {
                        return mTable.parseCursor(cursor);
                    }
                });
    }

    @Override
    public boolean save(AppEntity app) {
        mDatabaseHelper.insert(AppTable.TABLE_NAME, mTable.toContentValues(app), SQLiteDatabase.CONFLICT_REPLACE);
        return true;
    }

    @Override
    public int save(List<AppEntity> apps) {
        for (AppEntity app : apps) {
            save(app);
        }
        return 0;
    }

    @Override
    public boolean delete(AppEntity app) {
        mDatabaseHelper.delete(AppTable.TABLE_NAME, AppTable.WHERE_ID_EQUALS, app.appKey);
        return true;
    }

    @Override
    public int delete(List<AppEntity> apps) {
        for (AppEntity app : apps) {
            delete(app);
        }
        return 0;
    }

    @Override
    public int deleteAll() {
        mDatabaseHelper.delete(AppTable.TABLE_NAME, null);
        return 0;
    }
}
