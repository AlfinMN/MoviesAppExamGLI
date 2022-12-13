package com.examproject.myapplication.utils

import android.Manifest
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkInfo
import androidx.annotation.RequiresPermission

/**
 * Created by Alfin Muhammad Nurdin on 12/12/22.
 * alfinmuhammadnurdin@gmail.com
 */

class CheckValidation {

    companion object{

        /**
         * Get the network info
         *
         * @param context the context
         * @return network info
         */
        @RequiresPermission(Manifest.permission.ACCESS_NETWORK_STATE)
        private fun getNetworkInfo(context: Context): NetworkInfo? {
            val cm = (context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager)
            return cm.activeNetworkInfo
        }

        /**
         * Check if there is any connectivity
         *
         * @param context the context
         * @return boolean boolean
         */
        @RequiresPermission(Manifest.permission.ACCESS_NETWORK_STATE)
        fun isConnected(context: Context): Boolean {
            val info = getNetworkInfo(context)
            return info != null && info.isConnected
        }
    }
}