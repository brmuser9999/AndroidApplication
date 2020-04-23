package com.example.pictotext;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
public class DatabaseHelper extends SQLiteOpenHelper
{

    public DatabaseHelper(Context context)
    {
        super(context, "Login.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table user(email text primary key,password test)");
        ;
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)
    {
        db.execSQL("drop table if exists user");
    }

    public boolean insert(String email, String password) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        //for insrting data in db use contentvalues
        contentValues.put("Email", email);
        contentValues.put("password", password);
        //user is the table
        long ins = db.insert("user", null, contentValues);
        if (ins == -1) {
            return false;
        } else {
            return true;
        }
    }

    public Boolean chkmail(String email) {
        SQLiteDatabase db=this.getWritableDatabase();
        //access data row by row
        Cursor cursor =db.rawQuery("select *from user where email=?",new String[]{email});
        if(cursor.getCount()>0){
            return false;
        }
        else{
            return true;
        }
    }
}



