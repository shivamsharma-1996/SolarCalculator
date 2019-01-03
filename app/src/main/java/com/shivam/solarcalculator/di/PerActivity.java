package com.shivam.solarcalculator.di;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import javax.inject.Scope;

/**
 * Created by Shivam Sharma on 3-1-2019.
 */

@Scope
@Retention(RetentionPolicy.RUNTIME)
public @interface PerActivity {
}

