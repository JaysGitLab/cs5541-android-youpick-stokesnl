package com.bignerdranch.android.criminalintent.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import com.bignerdranch.android.criminalintent.database.CrimeDbSchema.CrimeTable;
/**
 * Created by Nathan on 10/10/2016.
 */
public class CrimeBaseHelper extends SQLiteOpenHelper
{
    private static final int VERSION = 1;
    private static final String DATABASE_NAME = "crimebase.db";

    public CrimeBaseHelper(Context context)
    {
        super(context, DATABASE_NAME, null, VERSION);
    }

    public void onCreate(SQLiteDatabase db)
    {
        db.execSQL("create table " + CrimeDbSchema.CrimeTable.NAME + "(" +
                " _id integer primary key autoincrement, " +
                CrimeTable.Cols.UUID + ", " +
                CrimeTable.Cols.TITLE + ", " +
                CrimeTable.Cols.DATE +", " +
                CrimeTable.Cols.SOLVED  + ", " +
                CrimeTable.Cols.SUSPECT + ")"
        );
    }

    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)
    {

    }
}
