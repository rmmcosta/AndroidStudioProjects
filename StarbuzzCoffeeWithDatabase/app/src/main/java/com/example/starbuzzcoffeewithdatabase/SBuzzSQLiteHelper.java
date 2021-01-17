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
    private static SBuzzSQLiteHelper sBuzzSQLiteHelper;
    private Context context;
    private static final DrinkEntity[] drinks = new DrinkEntity[]{
            new DrinkEntity(0, "Latte", R.string.latte_description, R.drawable.latte),
            new DrinkEntity(0, "Cappuccino", R.string.cappuccino_description, R.drawable.cappuccino),
            new DrinkEntity(0, "Filter", R.string.filter_description, R.drawable.filter)
    };

    private SBuzzSQLiteHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context = context;
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
        assert sqLiteDatabase != null;
        String createDrinkSQL = String.format("CREATE TABLE %s (_id INTEGER PRIMARY KEY AUTOINCREMENT, NAME TEXT, DESCRIPTION INTEGER, IMAGE_RESOURCE_ID INTEGER)", DATABASE_DRINK_TABLE_NAME);
        Log.d(LOG_TAG, "create table");
        sqLiteDatabase.execSQL(createDrinkSQL);
        Log.d(LOG_TAG, "table created");
        Cursor cursor = sqLiteDatabase.query(DATABASE_DRINK_TABLE_NAME, null, null, null, null, null, null, "1");
        if (cursor.getCount() == 0) {
            //sqLiteDatabase.beginTransaction();
            for (DrinkEntity each : drinks) {
                long id = insertDrink(sqLiteDatabase, each);
                String msg = String.format(context.getString(R.string.inserted_message), id);
                Log.d(LOG_TAG, msg);
            }
            //sqLiteDatabase.endTransaction();
        }
    }

    private long insertDrink(SQLiteDatabase sqLiteDatabase, DrinkEntity drink) {
        return insertDrink(sqLiteDatabase, drink.getName(), drink.getDescriptionId(), drink.getDrawableId());
    }

    private long insertDrink(SQLiteDatabase sqLiteDatabase, String name, int description, int imageResourceId) {
        ContentValues drinkContentValues = new ContentValues();
        drinkContentValues.put("NAME", name);
        drinkContentValues.put("DESCRIPTION", description);
        drinkContentValues.put("IMAGE_RESOURCE_ID", imageResourceId);
        return sqLiteDatabase.insert(DATABASE_DRINK_TABLE_NAME, "_id", drinkContentValues);
    }

    public DrinkEntity[] getDrinks(SQLiteDatabase sqLiteDatabase) {
        Cursor cursor = sqLiteDatabase.query(DATABASE_DRINK_TABLE_NAME, null, null, null, null, null, "NAME");
        DrinkEntity[] drinks = new DrinkEntity[cursor.getCount()];
        int count = 0;
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            drinks[count] = new DrinkEntity(cursor.getInt(0), cursor.getString(1), cursor.getInt(2), cursor.getInt(3));
            cursor.moveToNext();
            count++;
        }
        cursor.close();
        return drinks;
    }

    public DrinkEntity getDrink(SQLiteDatabase sqLiteDatabase, int _id) {
        String whereClause = "_id=?";
        String[] whereArgs = new String[]{String.valueOf(_id)};
        Cursor cursor = sqLiteDatabase.query(DATABASE_DRINK_TABLE_NAME, null, whereClause, whereArgs, null, null, null);
        cursor.moveToFirst();
        return new DrinkEntity(cursor.getInt(0), cursor.getString(1), cursor.getInt(2), cursor.getInt(3));
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}
