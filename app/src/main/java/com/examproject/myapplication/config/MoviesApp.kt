package com.examproject.myapplication.config

import android.app.Application

/**
 * Created by Alfin Muhammad Nurdin on 12/12/22.
 * alfinmuhammadnurdin@gmail.com
 */

class MoviesApp : Application(){
    val applicationComponent : ApplicationComponent = DaggerApplicationComponent.create()
}