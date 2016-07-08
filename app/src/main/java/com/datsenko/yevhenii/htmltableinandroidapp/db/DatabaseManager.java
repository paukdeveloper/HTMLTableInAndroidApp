package com.datsenko.yevhenii.htmltableinandroidapp.db;

import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

/**
 * Created by Yevhenii on 6/17/2016.
 */
public class DatabaseManager {
    private static final String TAG = "MY_TAG_LOG";
    private int mOpenCounter = 0;

    private static DatabaseManager instance;
    private static DBHelper mDatabaseHelper;
    private SQLiteDatabase mDatabase;

    public static synchronized void initializeInstance(DBHelper helper) {
        Log.d(TAG, "DATAMANAGER_initializeInstance: ");
        if (instance == null) {
            instance = new DatabaseManager();
            mDatabaseHelper = helper;
        }
    }

    public static synchronized DatabaseManager getInstance() {
        Log.d(TAG, "DATAMANAGER_getInstance: ");
        if (instance == null) {
            throw new IllegalStateException(DatabaseManager.class.getSimpleName() +
                    " is not initialized, call initializeInstance(..) method first.");
        }

        return instance;
    }

    public synchronized SQLiteDatabase openDatabase() {
        mOpenCounter++;
        Log.d(TAG, "DATAMANAGER_openDatabase: now == " + mOpenCounter);
        if(mOpenCounter == 1) {
            // Opening new database
            mDatabase = mDatabaseHelper.getWritableDatabase();
        }
        return mDatabase;
    }

    public synchronized void closeDatabase() {
        mOpenCounter--;
        Log.d(TAG, "DATAMANAGER_closeDatabase: count down. now ==  " + mOpenCounter);
        if(mOpenCounter == 0) {
            Log.d(TAG, "DATAMANAGER_closeDatabase: FINAL CLOSE");
            mDatabase.close();

        }
    }

}
