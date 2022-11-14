package com.examproject.myapplication.config

import android.app.Application

class MoviesApp : Application(){
    val applicationComponent : ApplicationComponent = DaggerApplicationComponent.create()
}