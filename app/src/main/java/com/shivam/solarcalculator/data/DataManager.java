package com.shivam.solarcalculator.data;

import com.shivam.solarcalculator.data.db.DbHelper;


public interface DataManager extends DbHelper{

    void destroy();
}