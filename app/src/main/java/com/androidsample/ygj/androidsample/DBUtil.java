package com.androidsample.ygj.androidsample;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by YGJ on 2015/10/20 0020.
 */
public class DBUtil extends SQLiteOpenHelper {
    private static  final String DBName = "sample.db";
    private static final  int Version = 1;
    /**
     * Create a helper object to create, open, and/or manage a database.
     * This method always returns very quickly.  The database is not actually
     * created or opened until one of {@link #getWritableDatabase} or
     * {@link #getReadableDatabase} is called.
     *
     * @param context to use to open or create the database
     */
    public DBUtil(Context context) {
        super(context, DBName, null, Version);
    }

    /**
     * Called when the database is created for the first time. This is where the
     * creation of tables and the initial population of the tables should happen.
     *
     * @param db The database.
     */
    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql = "CREATE TABLE IF NOT EXISTS location (" +
                " [id] int NOT NULL," +
                "[longitude] FLOAT," +
                "[latitude] FLOAT," +
                "[altitude] FLOAT," +
                "[speed] FLOAT," +
                "[bearin] FLOAT," +
                "[add_time] VARCHAR(50)," +
                "CONSTRAINT [] PRIMARY KEY ([id]));";
        db.execSQL(sql);
    }

    /**
     * Called when the database needs to be upgraded. The implementation
     * should use this method to drop tables, add tables, or do anything else it
     * needs to upgrade to the new schema version.
     * <p/>
     * <p>
     * The SQLite ALTER TABLE documentation can be found
     * <a href="http://sqlite.org/lang_altertable.html">here</a>. If you add new columns
     * you can use ALTER TABLE to insert them into a live table. If you rename or remove columns
     * you can use ALTER TABLE to rename the old table, then create the new table and then
     * populate the new table with the contents of the old table.
     * </p><p>
     * This method executes within a transaction.  If an exception is thrown, all changes
     * will automatically be rolled back.
     * </p>
     *
     * @param db         The database.
     * @param oldVersion The old database version.
     * @param newVersion The new database version.
     */
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
    /**
    * 添加一条数据
    * @param longitude 经度
    * @param latitude 维度
    * @param altitude 高度
    * @param speed 速度
    * @param bearin 方向
    * @param add_time 添加时间
    * */
    public int Insert(float longitude,
                      float latitude,
                      float altitude,
                      float speed,
                      float bearin,
                      String add_time){
        return 0;
    }
}
