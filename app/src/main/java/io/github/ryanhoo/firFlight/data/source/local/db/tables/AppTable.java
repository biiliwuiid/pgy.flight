package io.github.ryanhoo.firFlight.data.source.local.db.tables;

import android.content.ContentValues;
import android.database.Cursor;
import android.provider.BaseColumns;
import android.util.Log;
import io.github.ryanhoo.firFlight.data.model.App;
import io.github.ryanhoo.firFlight.data.model.AppEntity;
import io.github.ryanhoo.firFlight.data.model.AppPgy;
import io.github.ryanhoo.firFlight.data.model.Release;
import io.github.ryanhoo.firFlight.util.ObjectUtils;

import java.io.IOException;
import java.util.Date;

/**
 * Created with Android Studio.
 * User: ryan.hoo.j@gmail.com
 * Date: 6/1/16
 * Time: 12:59 PM
 * Desc: AppTable
 */
public final class AppTable implements BaseColumns, BaseTable<AppEntity> {

    private static final String TAG = "AppTable";

    // Table Name
    public static final String TABLE_NAME = "app";

    // Columns
    public static final String COLUMN_ID = _ID; // "_id"
    public static final String COLUMN_USER_ID = "user_id";
    public static final String COLUMN_NAME = "name";
    public static final String COLUMN_SHORT_URL = "short_url";
    public static final String COLUMN_BUNDLE_ID = "bundle_id";
    public static final String COLUMN_CUSTOM_MARKET_URL = "custom_market_url";
    public static final String COLUMN_CREATED_AT = "created_at";
    public static final String COLUMN_ICON_URL = "icon_url";
    public static final String COLUMN_TYPE = "type";
    public static final String COLUMN_RELEASE_ID = "release_id";

    // Create & Delete
    public static final String CREATE_TABLE =
            "CREATE TABLE " + TABLE_NAME +
                " ( " +
                    COLUMN_ID + " TEXT PRIMARY KEY UNIQUE, " +
                    COLUMN_USER_ID + " TEXT, " +
                    COLUMN_NAME + " TEXT, " +
                    COLUMN_SHORT_URL + " TEXT, " +
                    COLUMN_BUNDLE_ID + " TEXT, " +
                    COLUMN_CUSTOM_MARKET_URL + " TEXT, " +
                    COLUMN_CREATED_AT + " INTEGER, " +
                    COLUMN_ICON_URL + " TEXT , " +
                    COLUMN_TYPE + " TEXT , " +
                    COLUMN_RELEASE_ID + " INTEGER" +
                    // The fucking foreign key doesn't work, replacing it with trigger
                    // "FOREIGN KEY(" + COLUMN_RELEASE_ID + ") REFERENCES " +
                    // ReleaseTable.TABLE_NAME + "(" + ReleaseTable._ID + ") ON DELETE CASCADE" +
                " );";

    public static final String DELETE_TABLE =
            "DROP TABLE IF EXISTS " + TABLE_NAME + ";";

    // Delete the related Release when app is being deleted
    public static final String DELETE_APP_TRIGGER =
            "CREATE TRIGGER IF NOT EXISTS delete_app_trigger AFTER DELETE ON " + AppTable.TABLE_NAME + "\n" +
                    "FOR EACH ROW\n" +
                    "BEGIN\n" +
                    "DELETE FROM " + ReleaseTable.TABLE_NAME + " " +
                    "WHERE " + ReleaseTable._ID + " = old." + AppTable.COLUMN_RELEASE_ID + ";\n" +
                    "END";

    public static final String QUERY_ALL_APPS = "SELECT * FROM " + TABLE_NAME + ";";

    public static final String WHERE_ID_EQUALS = COLUMN_ID + "=?";

    @Override
    public String createTableSql() {
        return CREATE_TABLE;
    }

    @Override
    public String deleteTableSql() {
        return DELETE_TABLE;
    }

    @Override
    public ContentValues toContentValues(AppEntity app) {
        ContentValues contentValues = new ContentValues();
//        contentValues.put(COLUMN_ID, app.getId());
//        contentValues.put(COLUMN_USER_ID, app.getUserId());
//        contentValues.put(COLUMN_NAME, app.getName());
//        contentValues.put(COLUMN_BUNDLE_ID, app.getBundleId());
//        contentValues.put(COLUMN_SHORT_URL, app.getShortUrl());
//        contentValues.put(COLUMN_ICON_URL, app.getIconUrl());
//        contentValues.put(COLUMN_CUSTOM_MARKET_URL, app.getCustomMarketUrl());
//        contentValues.put(COLUMN_TYPE, app.getType());
//        contentValues.put(COLUMN_CREATED_AT, app.getCreatedAt() == null ? -1 : app.getCreatedAt().getTime());
//        if (app.getMasterRelease() != null) {
//            try {
//                byte[] releaseInBytes = ObjectUtils.objectToByte(app.getMasterRelease());
//                contentValues.put(COLUMN_RELEASE_ID, releaseInBytes);
//            } catch (IOException e) {
//                e.printStackTrace();
//
//            }
//        }
        return contentValues;
    }

    @Override
    public AppEntity parseCursor(Cursor c) {
        if(true){
            return new AppEntity();
        }
        App app = new App();
        app.setId(c.getString(c.getColumnIndexOrThrow(COLUMN_ID)));
        app.setName(c.getString(c.getColumnIndexOrThrow(COLUMN_USER_ID)));
        app.setName(c.getString(c.getColumnIndexOrThrow(COLUMN_NAME)));
        app.setBundleId(c.getString(c.getColumnIndexOrThrow(COLUMN_BUNDLE_ID)));
        app.setShortUrl(c.getString(c.getColumnIndexOrThrow(COLUMN_SHORT_URL)));
        app.setIconUrl(c.getString(c.getColumnIndexOrThrow(COLUMN_ICON_URL)));
        app.setCustomMarketUrl(c.getString(c.getColumnIndexOrThrow(COLUMN_CUSTOM_MARKET_URL)));
        app.setType(c.getString(c.getColumnIndexOrThrow(COLUMN_TYPE)));
        long createdAt = c.getLong(c.getColumnIndexOrThrow(COLUMN_CREATED_AT));
        if (createdAt != -1) {
            app.setCreatedAt(new Date(createdAt));
        }
        byte[] releaseInBytes = c.getBlob(c.getColumnIndexOrThrow(COLUMN_RELEASE_ID));
        if (releaseInBytes != null) {
            try {
                app.setMasterRelease((Release) ObjectUtils.byteToObject(releaseInBytes));
            } catch (IOException | ClassNotFoundException e) {
                Log.e(TAG, "parseCursor: ", e);
            }
        }
        return null;
    }
}
