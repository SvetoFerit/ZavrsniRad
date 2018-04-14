package com.example.sveto.zavrsnirad.models;

import android.provider.BaseColumns;

/**
 * Created by Sveto on 12.4.2018..
 */

public class UserContract {
    public UserContract() {
    }

    public static class UserTable implements BaseColumns{
        public UserTable() {
        }

        public static final String TABLE_NAME = "user_signup_data";
        public static final String COLUMN_USERNAME = "user_singup_name";
        public static final String COLUMN_EMAIL = "user_singup_email";
        public static final String COLUMN_PASSWORD = "user_singup_password";


    }
}
