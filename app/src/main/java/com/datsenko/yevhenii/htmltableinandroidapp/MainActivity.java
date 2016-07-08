package com.datsenko.yevhenii.htmltableinandroidapp;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.webkit.WebView;

import com.datsenko.yevhenii.htmltableinandroidapp.db.DBHelper;
import com.datsenko.yevhenii.htmltableinandroidapp.db.DatabaseManager;

import static com.datsenko.yevhenii.htmltableinandroidapp.db.DBHelper.TABLE_USERS;
import static com.datsenko.yevhenii.htmltableinandroidapp.db.DBHelper.TAB_USERS_NAME;
import static com.datsenko.yevhenii.htmltableinandroidapp.db.DBHelper.TAB_USERS_REG;

public class MainActivity extends AppCompatActivity {
    private SQLiteDatabase db;
    private WebView mWebView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        DatabaseManager.initializeInstance(new DBHelper(this));
        mWebView = (WebView) findViewById(R.id.web_view_table);
//        initData();

//        StringBuffer buf = new StringBuffer();
//        buf.append(getString(R.string.table_row));

//        Toast.makeText(MainActivity.this, buf.toString(), Toast.LENGTH_SHORT).show();

//        mWebView.loadDataWithBaseURL("", html, mimeType, encoding, "");

    new MakeHTMLTable().execute(4);
    }
    private void initData() {
        SQLiteDatabase db = DatabaseManager.getInstance().openDatabase();
        for (int i =0; i < 3; i++) {
            ContentValues value = new ContentValues();
            value.put(TAB_USERS_REG,"reg " + 1);
            value.put(TAB_USERS_NAME,"name " + 1);
            db.insert(TABLE_USERS,null,value);
        }
        DatabaseManager.getInstance().closeDatabase();
    }

    private String getHTMLTable(int countCol) {
        StringBuffer buffer = new StringBuffer();
        int indexCol = 0;
        buffer.append("<table>");
        SQLiteDatabase db = DatabaseManager.getInstance().openDatabase();
        Cursor cursorTableUsers = db.query(TABLE_USERS, null, null, null, null, null, null);
        if (cursorTableUsers.moveToFirst()) {
            do {
                if (indexCol == 0) {
                    buffer.append("<tr>");
                    buffer.append(makeRow(cursorTableUsers.getString(0),cursorTableUsers.getString(1),cursorTableUsers.getString(2)));
                    indexCol++;
                } else if (indexCol < countCol) {
                    buffer.append(makeRow(cursorTableUsers.getString(0),cursorTableUsers.getString(1),cursorTableUsers.getString(2)));
                    indexCol++;
                } else {
                    buffer.append("</tr>");
                    indexCol = 0;
                    buffer.append("<tr>");
                    buffer.append(makeRow(cursorTableUsers.getString(0),cursorTableUsers.getString(1),cursorTableUsers.getString(2)));
                    indexCol++;
                }
            } while (cursorTableUsers.moveToNext());
        }
        cursorTableUsers.close();
        DatabaseManager.getInstance().closeDatabase();
        buffer.append("</table>");
        return buffer.toString();
    }

    public String makeRow(String id, String reg, String name) {
        String row = "<td>" + getString(R.string.table_row, id, reg, name) + "</td>";
        return row;
    }

    private class MakeHTMLTable extends AsyncTask<Integer,Void,String> {

        @Override
        protected String doInBackground(Integer... integers) {
            return getHTMLTable(integers[0]);
        }

        @Override
        protected void onPostExecute(String htmlTable) {
            final String mimeType = "text/html";
            final String encoding = "UTF-8";
            mWebView.loadDataWithBaseURL("", htmlTable, mimeType, encoding, "");

        }
    }

}
