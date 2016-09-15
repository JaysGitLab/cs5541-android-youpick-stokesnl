package com.bignerdranch.android.criminalintent;

import java.util.UUID;

/**
 * Created by stokesnl on 9/13/2016.
 */
public class Crime
{
    private UUID mId;
    private String mTitle;

    public Crime()
    {
        //Generate Unique Identifier
        mId = UUID.randomUUID();
    }

    public UUID getId() {
        return mId;
    }

    public String getTitle() {
        return mTitle;
    }

    public void setTitle(String mTitle) {
        this.mTitle = mTitle;
    }
}
