package com.zybooks.warehouseapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.Toast;


public class DatabaseManager extends SQLiteOpenHelper
{
    private static final String DATABASE_NAME = "UserDatabase";
    private static final int DATABASE_VERSION = 1;
    private static final String TABLE_NAME = "employees";
    private static final String COLUMN_ID = "userID";
    private static final String COLUMN_FIRST_NAME = "First_Name";
    private static final String COLUMN_LAST_NAME = "Last_Name";
    private static final String COLUMN_TITLE = "Title";
    private static final String COLUMN_USERNAME = "Username";
    private static final String COLUMN_PASSWORD = "Password";
    private static final String ACCESS_LEVEL = "Access_Level";

DatabaseManager(Context context)
{
    super(context,DATABASE_NAME,null,DATABASE_VERSION);
    SQLiteDatabase db = this.getWritableDatabase();


}

@Override
    public void onCreate(SQLiteDatabase sqLiteDatabase)
{

    String sql = "CREATE TABLE " + TABLE_NAME + "(\n" +
        " " + COLUMN_ID + " INTEGER NOT NULL CONSTRAINT employees_pk PRIMARY KEY AUTOINCREMENT,\n" +
        " " + COLUMN_FIRST_NAME + " varchar(50) NOT NULL,\n" +
        " " + COLUMN_LAST_NAME  + " " + " varchar(100) NOT NULL,\n" +
        "  " + COLUMN_TITLE  + "  " + "varchar(100) NOT NULL, \n" +
            " " + COLUMN_USERNAME + " " + "varchar(50) NOT NULL UNIQUE, \n" +
        " " + COLUMN_PASSWORD + " " + "varchar(50) NOT NULL UNIQUE, \n" +
        " " + ACCESS_LEVEL + "  " + "varchar(1) NOT NULL\n" + ");";

    sqLiteDatabase.execSQL(sql);
}


    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1)
    {
        String sql = "DROP TABLE IF EXISTS " + TABLE_NAME + ";";
        sqLiteDatabase.execSQL(sql);
        onCreate(sqLiteDatabase);

    }

    public boolean addEmployee(String firstName, String lastName, String jobTitle, String username, String password, String accessLevel)
    {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_FIRST_NAME, firstName);
        contentValues.put(COLUMN_LAST_NAME, lastName);
        contentValues.put(COLUMN_TITLE, jobTitle);
        contentValues.put(COLUMN_USERNAME,username);
        contentValues.put(COLUMN_PASSWORD,password);
        contentValues.put(ACCESS_LEVEL, accessLevel);
        //long rowInserted =
        long insertResult = db.insert(TABLE_NAME,null,contentValues);
        if(insertResult == -1) {return false;}

        else {return true;}

    }

    Cursor getAllEmployees()
    {
        SQLiteDatabase db = getReadableDatabase();
        return db.rawQuery("SELECT * FROM " + TABLE_NAME,null);
    }

    boolean searchForEmployee(String UserName, String Password)
    {
        String query = "SELECT * FROM " + TABLE_NAME + " WHERE "+ COLUMN_USERNAME +" =? AND " + COLUMN_PASSWORD + " =?";
        SQLiteDatabase db = this.getReadableDatabase();
        String[] whereArgs = {UserName,Password};
        Cursor cursor = db.rawQuery(query,whereArgs);
        int userCount = cursor.getCount();

        if(userCount == 1) {return true;}

        else{return false;}
    }

    boolean updateEmployee(int id, String firstName, String lastName, String title, String accessLevel)
    {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_FIRST_NAME, firstName);
        contentValues.put(COLUMN_LAST_NAME,lastName);
        contentValues.put(COLUMN_TITLE, title);
        contentValues.put(ACCESS_LEVEL,accessLevel);
        return db.update(TABLE_NAME,contentValues,COLUMN_ID + "=?", new String[]{String.valueOf(id)}) == 1;
    }


    boolean deleteEmployee(int id)
    {
        SQLiteDatabase db = getWritableDatabase();
        return db.delete(TABLE_NAME, COLUMN_ID + "=?" , new String[]{String.valueOf(id)}) == 1;
    }

}
