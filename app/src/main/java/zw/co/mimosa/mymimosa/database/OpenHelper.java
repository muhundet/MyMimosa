package zw.co.mimosa.mymimosa.database;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.util.ArrayList;

public class OpenHelper extends SQLiteOpenHelper {
    private static SQLiteOpenHelper mOpenHelperInstance = null;
    public static final String DATABASE_NAME = "Mimdb.db";
    public static final int DATABASE_VERSION = 1;
    private static Context mContext;

    public OpenHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        mContext = context;
    }

    public static OpenHelper getOpenHelperInstance(){
        if(mOpenHelperInstance == null){
            mOpenHelperInstance = new OpenHelper(mContext);
        }
        return (OpenHelper) mOpenHelperInstance;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
//        db.execSQL(DatabaseContract.Entry.SQL_CREATE_TABLE);
//        db.execSQL(DatabaseContract.Entry.SQL_CREATE_TABLE);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public Cursor getLoginUser(String employeeId) {
        SQLiteDatabase db = this.getReadableDatabase();
        String sql = "SELECT * FROM  users  WHERE EMPLOYEEID = '" + employeeId + "' ;";
        Cursor c = db.rawQuery(sql, null);
        return c;
    }



}
