package com.examproject.myapplication.utils

import android.util.Log
/**
 * Created by Alfin Muhammad Nurdin on 11/12/22.
 * alfinmuhammadnurdin@gmail.com
 */

class MessageLog {

    companion object{

        /**
         * Set Log
         *
         * @param tag TAG of logcat
         * @param msg Message of Logcat
         */
        fun setLogCat(tag: String, msg: String?) {
            Log.i("$tag :", msg!!)
        }
    }
}