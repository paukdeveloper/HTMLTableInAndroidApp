package com.datsenko.yevhenii.htmltableinandroidapp.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Женя on 08.07.2016.
 */
public class DBHelper extends SQLiteOpenHelper {
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "database_users";

    public static final String TABLE_USERS = "users";
    public static final String TAB_USERS_ID = "_id";
    public static final String TAB_USERS_REG = "reg";
    public static final String TAB_USERS_NAME = "name";

    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String createTableUsers = "create table "
                + TABLE_USERS + " ("
                + TAB_USERS_ID + " integer primary key autoincrement, "
                + TAB_USERS_REG + " text, "
                + TAB_USERS_NAME + " text );";
        sqLiteDatabase.execSQL(createTableUsers);

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_USERS);
        onCreate(sqLiteDatabase);
    }

}
