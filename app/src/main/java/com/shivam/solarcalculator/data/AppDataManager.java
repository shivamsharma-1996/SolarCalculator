package com.shivam.solarcalculator.data;

import android.content.Context;
import com.shivam.solarcalculator.data.db.DbHelper;
import com.shivam.solarcalculator.di.ApplicationContext;

import javax.inject.Inject;
import javax.inject.Singleton;

/**
 * Created by Shivam Sharma on 3-1-2019.
 */
@Singleton
public class AppDataManager implements DataManager {

    private final Context mContext;
    private final DbHelper mDbHelper;

    @Inject
    public AppDataManager(@ApplicationContext Context context,
                          DbHelper dbHelper) {
        this.mContext = context;
        this.mDbHelper = dbHelper;
    }


    @Override
    public void destroy() {
        mDbHelper.destroy();
    }

}