package com.shivam.solarcalculator.data;

import com.shivam.solarcalculator.data.db.DbHelper;
import com.shivam.solarcalculator.data.models.PlaceInfo;
import java.util.List;


public interface DataManager extends DbHelper{

    void destroy();
}