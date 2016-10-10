package com.bignerdranch.android.criminalintent;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.bignerdranch.android.criminalintent.database.CrimeBaseHelper;
import com.bignerdranch.android.criminalintent.database.CrimeCursorWrapper;
import com.bignerdranch.android.criminalintent.database.CrimeDbSchema;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Created by Nathan on 9/25/2016.
 */

import com.bignerdranch.android.criminalintent.database.CrimeDbSchema.CrimeTable;

public class CrimeLab
{
    private static CrimeLab sCrimeLab;

    //private List<Crime> mCrimes;
    private Context mContext;
    private SQLiteDatabase mDatabase;

    public static CrimeLab get(Context context)
    {
        if (sCrimeLab == null)
        {
            sCrimeLab = new CrimeLab(context);
        }
        return sCrimeLab;
    }

    private CrimeLab (Context context)
    {
        mContext = context.getApplicationContext();
        mDatabase = new CrimeBaseHelper(mContext).getWritableDatabase();
    //    mCrimes = new ArrayList<>();
    //    for (int i = 0; i < 100; i++)
    //    {
    //        Crime crime = new Crime();
    //       crime.setTitle("Crime #" + i);
    //        crime.setSolved(i % 2 == 0); // Every other one
    //        mCrimes.add(crime);
    //    }
    }

    public List<Crime> getCrimes() {
        //return mCrimes;
        List<Crime> crimes = new ArrayList<>();

        CrimeCursorWrapper cursor = queryCrimes(null, null);
        try
        {
            cursor.moveToFirst();
            while(!cursor.isAfterLast())
            {
                crimes.add(cursor.getCrime());
                cursor.moveToNext();
            }
        }
        finally
        {
            cursor.close();
        }

        return crimes;
    }

    public Crime getCrime(UUID id) {
        CrimeCursorWrapper cursor = queryCrimes(CrimeTable.Cols.UUID + " = ?", new String[] {id.toString()});
        try
        {
            if (cursor.getCount() == 0)
            {
                return null;
            }
            cursor.moveToFirst();
            return cursor.getCrime();

        }
        finally
        {
            cursor.close();
        }
        //for (Crime crime : mCrimes)
        //{
        //    if (crime.getId().equals(id))
        //    {
        //        return crime;
        //    }
        //}
        //return null;
    }

    private static ContentValues getContentValues(Crime crime)
    {
        ContentValues values = new ContentValues();
        values.put(CrimeTable.Cols.UUID, crime.getId().toString());
        values.put(CrimeTable.Cols.TITLE, crime.getTitle());
        values.put(CrimeTable.Cols.DATE, crime.getDate().getTime());
        values.put(CrimeTable.Cols.SOLVED, crime.isSolved() ? 1 : 0);
        values.put(CrimeTable.Cols.SUSPECT, crime.getSuspect());

        return values;
    }

    private CrimeCursorWrapper queryCrimes(String whereClause, String[] whereArgs)
    {
        Cursor cursor = mDatabase.query(
                CrimeTable.NAME,
                null,
                whereClause,
                whereArgs,
                null,
                null,
                null
        );

        return new CrimeCursorWrapper(cursor);
        //return cursor;
    }
    public void addCrime(Crime c)
    {
        ContentValues values = getContentValues(c);

        mDatabase.insert(CrimeTable.NAME, null, values);
        //mCrimes.add(c);
    }

    public void updateCrime(Crime crime)
    {
        String uuidString = crime.getId().toString();
        ContentValues values = getContentValues(crime);

        mDatabase.update(CrimeTable.NAME, values, CrimeTable.Cols.UUID + " = ?", new String[]{uuidString});
    }
}
