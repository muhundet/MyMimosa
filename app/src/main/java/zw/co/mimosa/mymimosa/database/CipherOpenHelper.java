package zw.co.mimosa.mymimosa.database;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import net.sqlcipher.database.SQLiteDatabase;

import java.io.File;
import java.io.IOException;

public class CipherOpenHelper extends net.sqlcipher.database.SQLiteOpenHelper {
    private static net.sqlcipher.database.SQLiteOpenHelper mOpenHelperInstance = null;
    public static final String DATABASE_NAME = "Mimdb.db";
    public static final int DATABASE_VERSION = 1;
    private static Context mContext;

    public CipherOpenHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        mContext = context;
    }

    public static CipherOpenHelper getCipherOpenHelperInstance(){
        if(mOpenHelperInstance == null){
            mOpenHelperInstance = new CipherOpenHelper(mContext);
        }
        return (CipherOpenHelper) mOpenHelperInstance;
    }

    public Cursor getLoginUser(String employeeId) {
        net.sqlcipher.database.SQLiteDatabase db = getReadableDatabase("ruby100");
        String sql = "SELECT * FROM  users  WHERE EMPLOYEEID = '" + employeeId + "' ;";
        Cursor c = db.rawQuery(sql, null);
        return c;
    }


    @Override
    public void onCreate(net.sqlcipher.database.SQLiteDatabase db) {
//        net.sqlcipher.database.SQLiteDatabase.loadLibs(mContext);
//        File originalFile = mContext.getDatabasePath("Mimdb.db");
//        System.out.println(String.valueOf(originalFile));
//
//        File newFile = null;
//        try {
//            newFile = File.createTempFile("sqlcipherutils", "tmp", mContext.getCacheDir());
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//
//        net.sqlcipher.database.SQLiteDatabase existing_db = net.sqlcipher.database.SQLiteDatabase.openDatabase(String.valueOf(originalFile), "", null, SQLiteDatabase.OPEN_READWRITE);
//
//        existing_db.rawExecSQL("ATTACH DATABASE '" + newFile.getPath() + "' AS plaintext KEY '" + "ruby100" + "';");
//        existing_db.rawExecSQL("SELECT sqlcipher_export('plaintext');");
//        existing_db.rawExecSQL("DETACH DATABASE plaintext;");
//
//        existing_db.close();

    }

    @Override
    public void onUpgrade(net.sqlcipher.database.SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
