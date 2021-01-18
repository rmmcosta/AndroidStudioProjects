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
    private static final int DATABASE_VERSION = 2;
    private static final int DATABASE_VERSION_OLD = 1;
    private static final String DATABASE_DRINK_TABLE_NAME = "DRINK";
    private final Context context;
    private static final DrinkEntity[] drinks = new DrinkEntity[]{
            new DrinkEntity(0, "Latte", R.string.latte_description, R.drawable.latte, false),
            new DrinkEntity(0, "Cappuccino", R.string.cappuccino_description, R.drawable.cappuccino, false),
            new DrinkEntity(0, "Filter", R.string.filter_description, R.drawable.filter, false)
    };

    public SBuzzSQLiteHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        Log.d(LOG_TAG, "on create");
        assert sqLiteDatabase != null;
        String createDrinkSQL = String.format("CREATE TABLE %s (_id INTEGER PRIMARY KEY AUTOINCREMENT, NAME TEXT, DESCRIPTION INTEGER, IMAGE_RESOURCE_ID INTEGER, FAVORITE NUMERIC DEFAULT 0)", DATABASE_DRINK_TABLE_NAME);
        Log.d(LOG_TAG, "create table");
        sqLiteDatabase.execSQL(createDrinkSQL);
        Log.d(LOG_TAG, "table created");
        Cursor cursor = sqLiteDatabase.query(DATABASE_DRINK_TABLE_NAME, null, null, null, null, null, null, "1");
        if (cursor.getCount() == 0) {
            for (DrinkEntity each : drinks) {
                long id = insertDrink(sqLiteDatabase, each);
                String msg = String.format(context.getString(R.string.inserted_message), id);
                Log.d(LOG_TAG, msg);
            }
        }
        cursor.close();
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        if (i == DATABASE_VERSION_OLD) {
            String alterDrinkTable = String.format("ALTER TABLE %s ADD COLUMN FAVORITE NUMERIC DEFAULT 0", DATABASE_DRINK_TABLE_NAME);
            sqLiteDatabase.execSQL(alterDrinkTable);
            Log.d(LOG_TAG, "altered drink table");
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

    private long insertDrink(String name, int description, int imageResourceId) {
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        long id = insertDrink(sqLiteDatabase, name, description, imageResourceId);
        sqLiteDatabase.close();
        return id;
    }

    public DrinkEntity[] getDrinks() {
        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();
        DrinkEntity[] drinks = getDrinks(sqLiteDatabase, null, null);
        sqLiteDatabase.close();
        return drinks;
    }

    private DrinkEntity[] getDrinks(SQLiteDatabase sqLiteDatabase, String selection, String[] selectionArgs) {
        Cursor cursor = sqLiteDatabase.query(DATABASE_DRINK_TABLE_NAME, null, selection, selectionArgs, null, null, "NAME");
        DrinkEntity[] drinks = new DrinkEntity[cursor.getCount()];
        int count = 0;
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            drinks[count] = new DrinkEntity(cursor.getInt(0), cursor.getString(1), cursor.getInt(2), cursor.getInt(3), cursor.getInt(4) == 1);
            cursor.moveToNext();
            count++;
        }
        cursor.close();
        return drinks;
    }

    public DrinkEntity getDrink(int _id) {
        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();
        String whereClause = "_id=?";
        String[] whereArgs = new String[]{String.valueOf(_id)};
        Cursor cursor = sqLiteDatabase.query(DATABASE_DRINK_TABLE_NAME, null, whereClause, whereArgs, null, null, null);
        cursor.moveToFirst();
        DrinkEntity drinkEntity = new DrinkEntity(cursor.getInt(0), cursor.getString(1), cursor.getInt(2), cursor.getInt(3), cursor.getInt(4) == 1);
        cursor.close();
        sqLiteDatabase.close();
        return drinkEntity;
    }

    public DrinkEntity[] getFavoriteDrinks() {
        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();
        DrinkEntity[] drinks = getDrinks(sqLiteDatabase, "FAVORITE = ?", new String[]{String.valueOf(1)});
        sqLiteDatabase.close();
        return drinks;
    }

    public void updateFavorite(int id, boolean favorite) {
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("FAVORITE", favorite ? 1 : 0);
        sqLiteDatabase.update(DATABASE_DRINK_TABLE_NAME, contentValues, "_id=?", new String[]{String.valueOf(id)});
        sqLiteDatabase.close();
    }
}
