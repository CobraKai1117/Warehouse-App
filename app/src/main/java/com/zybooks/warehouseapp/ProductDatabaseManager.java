package com.zybooks.warehouseapp;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteStatement;
import android.media.Image;
import android.widget.ImageView;

import java.sql.Blob;


public  class ProductDatabaseManager extends SQLiteOpenHelper
{


    public ProductDatabaseManager(Context context,String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
        SQLiteDatabase db = this.getWritableDatabase();
    }

    ProductDatabaseManager(Context context)
    {
        super(context,DATABASE_NAME,null,DATABASE_VERSION);
        SQLiteDatabase db = this.getWritableDatabase();
    }


    private static  Integer ID = 1;
    private static final String DATABASE_NAME = "Product";
    private static final int DATABASE_VERSION = 1;
    private static final String PRODUCT_NAME = "Product_Name";
    private static final String SKU_CODE = "SKU_Code";
    private static final int STOCK_ON_HAND = 0;
    private static final Image  PRODUCT_IMAGE = null;


    public void queryData(String sql)
    {
        SQLiteDatabase database = this.getWritableDatabase();
        database.execSQL(sql);
    }

    public void insertData(String name, String SKUCode, int stock, byte[] image)
    {
        SQLiteDatabase database = getWritableDatabase();
        String sql = "INSERT INTO PRODUCTS VALUES (null,?,?,?,?)";
        // Increment the database ID every time an element is added

        SQLiteStatement statement = database.compileStatement(sql);
        statement.clearBindings();

        statement.bindString(1,name);
        statement.bindString(2,SKUCode);
        statement.bindLong(3,stock);
        statement.bindBlob(4,image);

        statement.executeInsert();
    }

    public void updateData(String name, String SKUCode, int stock, byte[] image)
    {
        SQLiteDatabase database = getWritableDatabase();

        String sql = "UPDATE PRODUCTS SET name = ?, SKUCode = ?, stock = ?, image = ?";

        SQLiteStatement SQLStatement = database.compileStatement(sql);
        SQLStatement.bindString(1,name);
        SQLStatement.bindString(2,SKUCode);
        SQLStatement.bindLong(3,stock);
        SQLStatement.bindBlob(4,image);

        SQLStatement.execute();

        database.close();

    }

    public void updateInventoryStock(int stock,int position) // Used to update the stock quantity for the desired item in the database
    {
        SQLiteDatabase database = getWritableDatabase();
        String sql = "UPDATE PRODUCTS SET STOCK_ON_HAND = ? WHERE ID = ?";
        SQLiteStatement updateStatement = database.compileStatement(sql);


        updateStatement.bindLong(1,stock);
        updateStatement.bindDouble(2,(double)position);

        updateStatement.execute();
        database.close();

    }

    public void deleteData(int id)
    {
        SQLiteDatabase database = getWritableDatabase();

        String sql = "DELETE FROM PRODUCTS WHERE ID = ?";

        SQLiteStatement deleteStatement = database.compileStatement(sql);
        deleteStatement.clearBindings();
        deleteStatement.bindDouble(1,(double)id);
        deleteStatement.execute();
        database.close();

    }

    public Cursor getData(String sql)
    {
    SQLiteDatabase database = getReadableDatabase();
    return database.rawQuery(sql,null);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase)
    {

        String sql = "CREATE TABLE PRODUCTS (\n" +
                " " + "ID" + " INTEGER PRIMARY KEY AUTOINCREMENT,\n" +
                " " + "PRODUCT_NAME" + " STRING NOT NULL, \n" +
                " " +  "SKU_CODE" + " STRING NOT NULL, \n" +
                " " +  "STOCK_ON_HAND" +" INTEGER,\n" +
                " " + "PRODUCT_IMAGE" + " BLOB\n" + ");";

        sqLiteDatabase.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1)
    {
        String sql = "DROP TABLE IF EXISTS " + "PRODUCTS" + ";";
        sqLiteDatabase.execSQL(sql);
        onCreate(sqLiteDatabase);
    }
}
