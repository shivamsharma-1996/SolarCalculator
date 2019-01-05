package com.shivam.solarcalculator.data.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.shivam.solarcalculator.data.models.Address;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String TAG = "DatabaseHelper";

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "address_db";

    private static DatabaseHelper self;

    public static DatabaseHelper getInstance(Context context) {
        if (self == null)
            self = new DatabaseHelper(context);
        return self;
    }

    private DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        self = this;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(Address.CREATE_TABLE);     //Creating Tables
    }


    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + Address.TABLE_NAME);     //Upgrading database
        onCreate(db);
    }

    public void insertAddress(Address address) {
        SQLiteDatabase db = this.getWritableDatabase();   //get writable database as we want to write data
        ContentValues values = new ContentValues();

        values.put(Address.COLUMN_ADDRESS, address.getAddress());     //`id` will be inserted automatically.
        values.put(Address.COLUMN_LATLONG, address.getLatlong());
        //values.put(Address.COLUMN_LONGITUDE, tvAddress.getLongitude());

        long id = db.insert(Address.TABLE_NAME, null, values);  // insert row
        Log.i(TAG,"insertAddress:" + id);
        db.close();  // close db connection
    }

    public List<Address> getAllAddresses() {
        List<Address> addressList = new ArrayList<>();
        String selectQuery = "SELECT  * FROM " + Address.TABLE_NAME;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                Address address = new Address();
                address.setId(cursor.getInt(cursor.getColumnIndex(Address.COLUMN_ID)));
                address.setAddress(cursor.getString(cursor.getColumnIndex(Address.COLUMN_ADDRESS)));
                address.setLatlong(cursor.getString(cursor.getColumnIndex(Address.COLUMN_LATLONG)));

                addressList.add(address);
            } while (cursor.moveToNext());
        }
        db.close();
        return addressList;
    }
}