package com.example.android.pets.data;

import android.provider.BaseColumns;

/**
 * Created by Mason on 5/14/2017.
 */

public final class PetContract {

    public class PetEntry implements BaseColumns {
        public static final String _ID = BaseColumns._ID;
        public static final String TABLE_NAME = "pets";
        public static final String COLUMN_NAME = "name";
        public static final String COLUMN_BREED = "breed";
        public static final String COLUMN_WEIGHT = "weight";
        public static final String COLUMN_GENDER = "gender";

        /* Constants for Gender column */
        public static final int GENDER_FEMALE = 0;
        public static final int GENDER_MALE = 1;
        public static final int GENDER_UNKNOWN = 2;
    }
}
