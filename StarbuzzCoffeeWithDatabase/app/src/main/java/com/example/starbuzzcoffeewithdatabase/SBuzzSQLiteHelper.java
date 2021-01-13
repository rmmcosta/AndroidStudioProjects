package com.example.starbuzzcoffeewithdatabase;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

public class SBuzzSQLiteHelper extends SQLiteOpenHelper {
    private static final String LOG_TAG = "LOG_SBuzzSQLiteHelper";
    private static final String DATABASE_NAME = "starbuzz";
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_DRINK_TABLE_NAME = "DRINK";
    private SQLiteDatabase sqLiteDatabase;
    private static SBuzzSQLiteHelper sBuzzSQLiteHelper;
    private static final DrinkEntity[] drinks = new DrinkEntity[]{
            new DrinkEntity(0, "Latte", R.string.latte_description, R.drawable.latte),
            new DrinkEntity(0, "Cappuccino", R.string.cappuccino_description, R.drawable.cappuccino),
            new DrinkEntity(0, "Filter", R.string.filter_description, R.drawable.filter)
    };

    private SBuzzSQLiteHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public static SBuzzSQLiteHelper getInstance(Context context) {
        synchronized (SBuzzSQLiteHelper.class) {
            if (sBuzzSQLiteHelper == null) {
                Log.d(LOG_TAG, "create singleton");
                sBuzzSQLiteHelper = new SBuzzSQLiteHelper(context);
            }
        }
        return sBuzzSQLiteHelper;
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        Log.d(LOG_TAG, "on create");
        this.sqLiteDatabase = sqLiteDatabase;
        createAndPopulateDrinkTable();
    }

    private void createAndPopulateDrinkTable() {
        createDrinkTable();
        populateDrinkTable();
    }

    private void createDrinkTable() {
        assert sqLiteDatabase != null;
        String createDrinkSQL = String.format("CREATE TABLE %s (_id INTEGER PRIMARY KEY AUTOINCREMENT, NAME TEXT, DESCRIPTION INTEGER, IMAGE_RESOURCE_ID INTEGER)", DATABASE_DRINK_TABLE_NAME);
        sqLiteDatabase.execSQL(createDrinkSQL);
    }

    private void populateDrinkTable() {
        assert sqLiteDatabase != null;
        Cursor cursor = sqLiteDatabase.query(DATABASE_DRINK_TABLE_NAME, null, null, null, null, null, null, "1");
        if (cursor.getCount() == 0) {
            sqLiteDatabase.beginTransaction();
            for (DrinkEntity each : drinks) {
                insertDrink(each);
            }
            sqLiteDatabase.endTransaction();
        }
    }

    private void insertDrink(DrinkEntity drink) {
        insertDrink(drink.getName(), drink.getDescriptionId(), drink.getDrawableId());
    }

    private void insertDrink(String name, int description, int imageResourceId) {
        ContentValues drinkContentValues = new ContentValues();
        drinkContentValues.put("NAME", name);
        drinkContentValues.put("DESCRIPTION", description);
        drinkContentValues.put("IMAGE_RESOURCE_ID", imageResourceId);
        sqLiteDatabase.insert(DATABASE_DRINK_TABLE_NAME, "_id", drinkContentValues);
    }

    public DrinkEntity[] getDrinks() {
        Cursor cursor = sqLiteDatabase.query(DATABASE_DRINK_TABLE_NAME, null, null, null, null, null, "NAME");
        DrinkEntity[] drinks = new DrinkEntity[cursor.getCount()];
        int count = 0;
        while (!cursor.isAfterLast()) {
            drinks[count] = new DrinkEntity(cursor.getInt(0), cursor.getString(1), cursor.getInt(2), cursor.getInt(3));
            cursor.moveToNext();
            count++;
        }
        return drinks;
    }

    public DrinkEntity getDrink(int _id) {
        String whereClause = "_id=?";
        String[] whereArgs = new String[]{String.valueOf(_id)};
        Cursor cursor = sqLiteDatabase.query(DATABASE_DRINK_TABLE_NAME, null, whereClause, whereArgs, null, null, null);
        return new DrinkEntity(cursor.getInt(0), cursor.getString(1), cursor.getInt(2), cursor.getInt(3));
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}
