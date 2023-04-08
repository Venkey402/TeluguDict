package com.telugu.apr4_teldict;
import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class DBHelper extends SQLiteOpenHelper {
    private static final String DB_NAME = "telugu.db";
    private static String DB_PATH= "data/data/com.telugu.apr4_teldict/databases/";
    private static final int DB_VERSION = 1;
    private SQLiteDatabase dbObj;
    private final Context context;
    Cursor cursor ;

    public DBHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
        this.context = context;
    }
    @Override
    public void onCreate(SQLiteDatabase DB) {
       try {
            Log.i("Oncreate", "Entered on create");
            InputStream inputStream = context.getAssets().open("telugu.db");
            String outFileName = context.getDatabasePath(DB_NAME).getPath();
            Log.i("outFileName is :",outFileName);
            OutputStream outputStream = new FileOutputStream(outFileName);
            byte[] buffer = new byte[1024];
            int length;
            while ((length = inputStream.read(buffer)) > 0) {
                outputStream.write(buffer, 0, length);
            }
            outputStream.flush();
            outputStream.close();
            inputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    @Override
    public void onUpgrade(SQLiteDatabase DB, int i, int ii) {
//        DB.execSQL("drop Table if exists words");
    }

    public Cursor getdata ()
    {
        SQLiteDatabase DB = this.getWritableDatabase();
        cursor = DB.rawQuery("select word from words UNION select meaning from words_meanings order by 1 ", null);
        return cursor;
    }

    @SuppressLint("LongLogTag")
    public void createDB() throws IOException {
        this.getReadableDatabase();
        Log.i("Readable ends....................","end");
        try {
            copyDB();
            Log.i("copy db ends....................","end");
        } catch (IOException e) {
            throw new Error("Error copying database");
        }
    }

    private boolean checkDB(){
        SQLiteDatabase checkDB = null;
        try{
            String path = DB_PATH + DB_NAME;
            checkDB = SQLiteDatabase.openDatabase(path, null, SQLiteDatabase.OPEN_READONLY);
            Log.i("myPath ......",path);
            if (checkDB!=null)
            {
            }
            else
            {
                return false;
            }

        }catch(SQLiteException e){
            e.printStackTrace();
        }

        if(checkDB != null){
            checkDB.close();
        }
        return checkDB != null ? true : false;
    }

    @SuppressLint("LongLogTag")
    public void copyDB() throws IOException{
        try {
            Log.i("inside copyDB....................","start");
            Log.i("Open db name is ..",DB_NAME+".db");
            InputStream ip =  context.getAssets().open(DB_NAME);
            Log.i("Input Stream....",ip+"");
            String op=  DB_PATH  +  DB_NAME ;
            OutputStream output = new FileOutputStream( op);
            byte[] buffer = new byte[1024];
            int length;
            while ((length = ip.read(buffer))>0){
                output.write(buffer, 0, length);
                Log.i("Content.... ",length+"");
            }
            output.flush();
            output.close();
            ip.close();
        }
        catch (IOException e) {
            Log.v("error", e.toString());
        }
    }

    public void openDB() throws SQLException {
        String myPath = DB_PATH + DB_NAME;
        dbObj = SQLiteDatabase.openDatabase(myPath, null, SQLiteDatabase.OPEN_READWRITE);
        Log.i("open DB......",dbObj.toString());
    }
}